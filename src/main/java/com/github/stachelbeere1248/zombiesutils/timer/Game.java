package com.github.stachelbeere1248.zombiesutils.timer;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import com.github.stachelbeere1248.zombiesutils.game.GameMode;
import com.github.stachelbeere1248.zombiesutils.game.SLA;
import com.github.stachelbeere1248.zombiesutils.game.enums.Map;
import com.github.stachelbeere1248.zombiesutils.handlers.Round1Correction;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.Category;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.files.CategoryFile;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.files.GameFile;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Game {
    private final Timer TIMER;
    private final GameMode GAME_MODE;
    private final GameFile GAME_FILE;
    private final boolean roundOneRecorded;
    private int round;
    private Category category;

    public Game(@NotNull final Map map, final String serverNumber) {
        this.GAME_MODE = new GameMode(map);
        this.TIMER = new Timer();
        this.GAME_FILE = new GameFile(serverNumber, map);
        this.category = new Category();
        this.round = 1;
        this.roundOneRecorded = true;

        MinecraftForge.EVENT_BUS.register(new Round1Correction(TIMER));
        if (ZombiesUtils.getInstance().getConfig().isSlaToggled()) SLA.instance = new SLA(map);
    }
    public Game(@NotNull final Map map, final String serverNumber, final int round) {
        this.GAME_MODE = new GameMode(map);
        this.TIMER = new Timer();
        this.GAME_FILE = new GameFile(serverNumber, map);
        this.category = new Category();
        this.round = round;
        this.roundOneRecorded = (round == 1);

        MinecraftForge.EVENT_BUS.register(new Round1Correction(TIMER));
        if (ZombiesUtils.getInstance().getConfig().isSlaToggled()) SLA.instance = new SLA(map);

    }

    public Timer getTimer() {
      return this.TIMER;
    };

    public void setCategory(Category category) {
        this.category = category;
    }
    public int getRound() {
        return round;
    }
    public GameMode getGameMode() {
        return GAME_MODE;
    }

    public void pass(int round) {
        if ((round == 0) || (this.round == round + 1) || (this.TIMER.getRoundTime() < 100)) {
            ZombiesUtils.getInstance().getLogger().debug("SPLIT CANCELLED");
            return;
        }
        try {
            record();
        } catch (Exception e) {
            ZombiesUtils.getInstance().getLogger().error(ExceptionUtils.getStackTrace(e));
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Error recording splits"));
        }
        this.TIMER.split();
        this.round = round + 1;
    }

    private void record() {
            this.compareSegment();
            if (this.roundOneRecorded) this.compareBest();
            this.GAME_FILE.setSegment(this.round, this.TIMER.getRoundTime());
    }

    private static final String bar = "§l§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬";

    public void compareSegment() throws IndexOutOfBoundsException {
        final CategoryFile categoryFile = this.category.getByGameMode(this.GAME_MODE);
        final short bestSegment = categoryFile.getBestSegment(round);
        final int roundTime = this.getTimer().getRoundTime();

        final String timeString = formattedTime(roundTime);
        String segmentMessage = Game.bar + "\n§e Category: §d" + category.getName();
        String deltaString = "";


        if (bestSegment == (short) 0) {
            categoryFile.setBestSegment(round, roundTime);

            if (ZombiesUtils.getInstance().getConfig().getAnnouncePB())
                segmentMessage += "\n§e§l***§6§l NEW BEST SEGMENT! §e§l***";
            segmentMessage += "\n§cRound " + round + "§e took §a" + timeString + "§e!";
        } else {
            if (roundTime < bestSegment) {
                if (ZombiesUtils.getInstance().getConfig().getAnnouncePB())
                    segmentMessage += "\n§e§l***§6§l NEW BEST SEGMENT! §e§l***";
                categoryFile.setBestSegment(round, roundTime);
            }
            deltaString = formattedDelta(roundTime, bestSegment);
            segmentMessage += "\n§cRound " + round + "§e took §a" + timeString + " §9" + deltaString;
            if (ZombiesUtils.getInstance().getConfig().getCopyDelta()) deltaString = " (" + deltaString + ")";
        }


        segmentMessage += "\n" + Game.bar;
        final ChatComponentText message = new ChatComponentText(segmentMessage);

        String copyString = String.format("Round %s took %s%s!", round, timeString, deltaString);
        message.setChatStyle(new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, copyString)));

        Minecraft.getMinecraft().thePlayer.addChatMessage(message);
    }

    public void compareBest() throws IndexOutOfBoundsException {
        final CategoryFile categoryFile = this.category.getByGameMode(this.GAME_MODE);
        final int personalBest = categoryFile.getPersonalBest(round);
        final int gameTime = this.TIMER.getGameTime();
        String deltaString = "";

        String bestMessage = Game.bar + "\n§e Category: §d" + category.getName();
        final String timeString = formattedTime(gameTime);

        if (personalBest == 0) {
            categoryFile.setPersonalBest(round, gameTime);

            if (ZombiesUtils.getInstance().getConfig().getAnnouncePB())
                bestMessage += "\n§e§l***§6§l NEW PERSONAL BEST! §e§l***";
            bestMessage += "\n§cRound " + round + "§e finished at §a" + timeString + "§e!";
        } else {
            if (gameTime < personalBest) {
                if (ZombiesUtils.getInstance().getConfig().getAnnouncePB())
                    bestMessage += "\n§e§l***§6§l NEW PERSONAL BEST! §e§l***";
                categoryFile.setPersonalBest(round, gameTime);
            }
            deltaString = formattedDelta(gameTime, personalBest);
            bestMessage += "\n§cRound " + round + "§e finished at §a" + timeString + " §9" + deltaString;
            if (ZombiesUtils.getInstance().getConfig().getCopyDelta()) deltaString = " (" + deltaString + ")";
        }
        bestMessage += "\n" + Game.bar;
        final ChatComponentText message = new ChatComponentText(bestMessage);

        String copyString = String.format("Round %s finished at %s%s!", round, timeString, deltaString);
        message.setChatStyle(new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, copyString)));

        Minecraft.getMinecraft().thePlayer.addChatMessage(message);
    }

    @Contract(pure = true)
    private String formattedTime(int gameTime) {
        gameTime *= 50;
        return String.format("%d:%02d.%d%d",
                gameTime / 60000,
                (gameTime % 60000) / 1000,
                (gameTime % 1000) / 100,
                (gameTime % 100) / 10
        );
    }

    @Contract(pure = true)
    private static @NotNull String formattedDelta(int newTime, int prevTime) {
        final double delta = (double) (newTime - prevTime) / 20;
        return String.format("%+.2f", delta);
    }
}
