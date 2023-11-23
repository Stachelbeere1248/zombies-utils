package com.github.stachelbeere1248.zombiesutils.timer;

import com.github.stachelbeere1248.zombiesutils.timer.recorder.Category;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.TimesFile;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class RecordManager {
    private static final String bar = "§l§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬";
    public static void compareSegment(byte round, short roundTime, @NotNull Category category) throws IndexOutOfBoundsException {
        String segmentMessage = bar +
                "\n§e Category: §d" + category.getName()
        ;


        @SuppressWarnings("OptionalGetWithoutIsPresent")
        final TimesFile timesFile = category.getByGameMode(Timer.getInstance().get().getGameMode());
        short bestSegment = timesFile.getBestSegment(round);
        if (bestSegment == (short) 0) {
            timesFile.setBestSegment(round, roundTime);

            segmentMessage += "\n§e§l***§6§l NEW BEST SEGMENT! §e§l***";
            final String timeString = formattedTime(roundTime);
            segmentMessage += "\n§cRound " + round + "§e took §a" + timeString + "§e!";
        } else {
            if (roundTime<bestSegment) {
                segmentMessage += "\n§e§l***§6§l NEW BEST SEGMENT! §e§l***";
                timesFile.setBestSegment(round, roundTime);
            }
            final String timeString = formattedTime(roundTime);
            segmentMessage += "\n§cRound " + round + "§e took §a" + timeString + " §9" + formattedDelta(roundTime,bestSegment);
        }
        segmentMessage += "\n" + bar;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(segmentMessage));
    }
    public static void compareBest(byte round, int gameTime, @NotNull Category category) throws IndexOutOfBoundsException {
        String bestMessage = bar +
                "\n§e Category: §d" + category.getName()
        ;

        @SuppressWarnings("OptionalGetWithoutIsPresent")
        final TimesFile timesFile = category.getByGameMode(Timer.getInstance().get().getGameMode());
        int personalBest = timesFile.getPersonalBest(round);
        if (personalBest == 0) {
            timesFile.setPersonalBest(round, gameTime);

            bestMessage += "\n§e§l***§6§l NEW PERSONAL BEST! §e§l***";
            final String timeString = formattedTime(gameTime);
            bestMessage += "\n§cRound " + round + "§e finished at §a" + timeString + "§e!";
        } else {
            if (gameTime<personalBest) {
                bestMessage += "\n§e§l***§6§l NEW PERSONAL BEST! §e§l***";
                timesFile.setPersonalBest(round, gameTime);
            }

            final String timeString = formattedTime(gameTime);
            bestMessage += "\n§cRound " + round + "§e finished at §a" + timeString + " §9" + formattedDelta(gameTime, personalBest);
        }
        bestMessage += "\n" + bar;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(bestMessage));
    }
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
        double delta = (double) (newTime - prevTime) / 20;
        if (delta<0) {
            return String.valueOf(delta);
        } else return ("+" + delta);
    }
}
