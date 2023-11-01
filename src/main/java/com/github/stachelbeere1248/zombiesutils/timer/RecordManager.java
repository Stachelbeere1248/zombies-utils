package com.github.stachelbeere1248.zombiesutils.timer;

import com.github.stachelbeere1248.zombiesutils.game.GameMode;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.Category;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.TimesFile;
import net.minecraft.client.Minecraft;
import net.minecraft.util.*;

public class RecordManager {
    public static void compareSegment(byte round, short roundTime, Category category) {
        TimesFile timesFile = category.getByGameMode(GameMode.getCurrentGameMode());
        short bestSegment = timesFile.getBestSegment(round);
        if (roundTime<bestSegment) {
            timesFile.setBestSegment(round, roundTime);

            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                    "\u00a7l\u00a7e" + category.getName() + ": ***" + "\u00a7l\u00a76 NEW BEST SEGMENT! " + "\u00a7l\u00a7e***"
            ));

            final String timeString = getTimeString(roundTime);
            final String message = "\u00a7cRound " + round + "\u00a7e took \u00a7a" + timeString + " \u00a79\u03B4" + (double) (roundTime-bestSegment)/20;
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));

        } else if (bestSegment == (short) 0) {
            timesFile.setBestSegment(round, roundTime);

            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                    "\u00a7l\u00a7e" + category.getName() + ": ***" + "\u00a7l\u00a76 NEW BEST SEGMENT! " + "\u00a7l\u00a7e***"
            ));

            final String timeString = getTimeString(roundTime);
            final String message = "\u00a7cRound " + round + "\u00a7e took \u00a7a" + timeString + "\u00a7e!";
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));

        }
    }
    public static void compareBest(byte round, int gameTime, Category category) {
        TimesFile timesFile = category.getByGameMode(GameMode.getCurrentGameMode());
        int personalBest = timesFile.getPersonalBest(round);

        if (gameTime<personalBest) {
            timesFile.setPersonalBest(round, gameTime);

            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                    "\u00a7l\u00a7e" + category.getName() + ": ***" + "\u00a7l\u00a76 NEW PERSONAL BEST! " + "\u00a7l\u00a7e***"
            ));

            final String timeString = getTimeString(gameTime);
            final String message = "\u00a7cRound " + round + "\u00a7e took \u00a7a" + timeString + " \u00a79\u03B4" + (double) (gameTime-personalBest)/20;
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));

        } else if (personalBest == 0) {
            timesFile.setPersonalBest(round, gameTime);

            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                    "\u00a7l\u00a7e" + category.getName() + ": ***" + "\u00a7l\u00a76 NEW PERSONAL BEST! " + "\u00a7l\u00a7e***"
            ));

            final String timeString = getTimeString(gameTime);
            final String message = "\u00a7cRound " + round + "\u00a7e took \u00a7a" + timeString + "\u00a7e!";
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));

        }
    }
    private static String getTimeString(int gameTime) {
        return String.format("%d:%02d.%d",
                (gameTime *50) / 60000,
                ((gameTime *50) % 60000) / 1000,
                ((gameTime *50) % 1000) / 100
        );
    }
}
