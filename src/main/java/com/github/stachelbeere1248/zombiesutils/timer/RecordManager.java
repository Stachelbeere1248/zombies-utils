package com.github.stachelbeere1248.zombiesutils.timer;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.Category;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.files.CategoryFile;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class RecordManager {
    private static final String bar = "§l§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬";

    public static void compareSegment(byte round, short roundTime, @NotNull Category category) throws IndexOutOfBoundsException {
        @SuppressWarnings("OptionalGetWithoutIsPresent") final CategoryFile categoryFile = category.getByGameMode(Timer.getInstance().get().getGameMode());
        final short bestSegment = categoryFile.getBestSegment(round);

        final String timeString = formattedTime(roundTime);
        String segmentMessage = bar + "\n§e Category: §d" + category.getName();
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


        segmentMessage += "\n" + bar;
        final ChatComponentText message = new ChatComponentText(segmentMessage);

        String copyString = String.format("Round %s took %s%s!", round, timeString, deltaString);
        message.setChatStyle(new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, copyString)));

        Minecraft.getMinecraft().thePlayer.addChatMessage(message);
    }

    public static void compareBest(byte round, int gameTime, @NotNull Category category) throws IndexOutOfBoundsException {
        @SuppressWarnings("OptionalGetWithoutIsPresent") final CategoryFile categoryFile = category.getByGameMode(Timer.getInstance().get().getGameMode());
        final int personalBest = categoryFile.getPersonalBest(round);
        String deltaString = "";

        String bestMessage = bar + "\n§e Category: §d" + category.getName();
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
        bestMessage += "\n" + bar;
        final ChatComponentText message = new ChatComponentText(bestMessage);

        String copyString = String.format("Round %s finished at %s%s!", round, timeString, deltaString);
        message.setChatStyle(new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, copyString)));

        Minecraft.getMinecraft().thePlayer.addChatMessage(message);
    }

    @Contract(pure = true)
    private static String formattedTime(int gameTime) {
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
