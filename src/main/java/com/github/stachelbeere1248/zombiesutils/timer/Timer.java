package com.github.stachelbeere1248.zombiesutils.timer;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import com.github.stachelbeere1248.zombiesutils.config.ZombiesUtilsConfig;
import com.github.stachelbeere1248.zombiesutils.game.GameMode;
import com.github.stachelbeere1248.zombiesutils.game.enums.Map;
import com.github.stachelbeere1248.zombiesutils.game.SLA;
import com.github.stachelbeere1248.zombiesutils.handlers.Round1Correction;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.Category;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.files.GameFile;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class Timer {

    public static Timer instance;
    private final GameMode gameMode;
    private final String serverNumber;
    private final GameFile gameFile;
    public Category category;
    private long savedTotalWorldTime;
    private int passedRoundsTickSum = 0;
    private boolean pbTracking = false;
    private int round;
    private boolean r1Corrected = false;

    /**
     * @param serverNumber The game's server the timer should be bound to.
     * @param map          The map the timer should be started for.
     * @param round        If available, round to begin splitting.
     */
    public Timer(@NotNull String serverNumber, @NotNull Map map, byte round) throws TimerException.ServerNumberException {
        this.savedTotalWorldTime = getCurrentTotalWorldTime();
        if (!serverNumber.trim().isEmpty()) this.serverNumber = serverNumber.trim();
        else throw new Timer.TimerException.ServerNumberException();

        this.category = new Category();
        this.gameFile = new GameFile(serverNumber.trim(), map);

        this.gameMode = new GameMode(map);
        this.round = round;
        if (ZombiesUtilsConfig.isSlaToggled()) SLA.instance = new SLA(map);

        MinecraftForge.EVENT_BUS.register(new Round1Correction());
    }

    public static Optional<Timer> getInstance() {
        return Optional.ofNullable(instance);
    }

    /**
     * Call to invalidate {@link #instance} to trigger the garbage collector
     */
    public static void dropInstances() {
        instance = null;
    }

    /**
     * The main splitting function.
     * Cancels on the second occurring sound-effect, important for {@link RecordManager} to not override values incorrectly.
     *
     * @param passedRound The round that has been passed.
     */
    public void split(byte passedRound) {
        final int gameTime = gameTime();
        final short roundTime = (short) (gameTime - passedRoundsTickSum);

        if ((round == passedRound) || (passedRound == 0) || (roundTime < 100)) {
            ZombiesUtils.getInstance().getLogger().debug("SPLIT CANCELLED");
            return;
        }

        try {
            if (!ZombiesUtils.isHypixel()) record(passedRound, roundTime, gameTime);
        } catch (Exception e) {
            ZombiesUtils.getInstance().getLogger().error(ExceptionUtils.getStackTrace(e));
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Error saving splits"));
        }

        passedRoundsTickSum = gameTime;
        round = passedRound;
    }

    public void correctRn() {
        if (r1Corrected) return;
        savedTotalWorldTime = getCurrentTotalWorldTime() - 200L;
        r1Corrected = true;
    }

    private void record(byte passedRound, short roundTime, int gameTime) {
        if (passedRound == (byte) 1) pbTracking = true;

        try {
            RecordManager.compareSegment(passedRound, roundTime, category);
            if (pbTracking) RecordManager.compareBest(passedRound, gameTime, category);

            gameFile.setSegment(passedRound, roundTime);
        } catch (IndexOutOfBoundsException exception) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                    String.format("Split not recorded. (invalid round parsed from scoreboard: %s)", passedRound)
            ));
        }
    }

    private long getCurrentTotalWorldTime() {
        if (Minecraft.getMinecraft() == null) return 0;
        if (Minecraft.getMinecraft().theWorld == null) return 0;
        return Minecraft.getMinecraft().theWorld.getTotalWorldTime();
    }

    public int gameTime() {
        return (int) (getCurrentTotalWorldTime() - savedTotalWorldTime);
    }

    public short roundTime() {
        return (short) (gameTime() - passedRoundsTickSum);
    }

    /**
     * @param serverNumber Servernumber to be compared
     * @return false, if and only if input exists and is unequal to {@link #serverNumber}
     */
    public boolean equalsServerOrNull(String serverNumber) {
        return (serverNumber == null || serverNumber.equals(this.serverNumber) || serverNumber.isEmpty());
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public byte getRound() {
        return (byte) (round + 1);
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public static abstract class TimerException extends Exception {

        public static class MapException extends TimerException {
        }

        public static class ServerNumberException extends TimerException {
        }
    }
}
