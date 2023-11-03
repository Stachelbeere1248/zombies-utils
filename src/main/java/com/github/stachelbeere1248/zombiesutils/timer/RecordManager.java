package com.github.stachelbeere1248.zombiesutils.timer;

import com.github.stachelbeere1248.zombiesutils.game.GameMode;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.Category;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.TimesFile;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import org.jetbrains.annotations.NotNull;

public class RecordManager {
    public static void compareSegment(byte round, short roundTime, @NotNull Category category) {
        sendBar();
        TimesFile timesFile = category.getByGameMode(GameMode.getCurrentGameMode());
        short bestSegment = timesFile.getBestSegment(round);
        if (bestSegment == (short) 0) {
            timesFile.setBestSegment(round, roundTime);

            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                    "\u00a7l\u00a7e Category: " + category.getName() + " - ***" + "\u00a7l\u00a76 NEW BEST SEGMENT! " + "\u00a7l\u00a7e***"
            ));
            final String timeString = formattedTime(roundTime);
            final String message = "\u00a7cRound " + round + "\u00a7e took \u00a7a" + timeString + "\u00a7e!";
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));

        } else {
            if (roundTime<bestSegment) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                        "\u00a7l\u00a7e Category: " + category.getName() + " - ***" + "\u00a7l\u00a76 NEW BEST SEGMENT! " + "\u00a7l\u00a7e***"
                ));
                timesFile.setBestSegment(round, roundTime);
            }
            final String timeString = formattedTime(roundTime);
            final String message = "\u00a7cRound " + round + "\u00a7e took \u00a7a" + timeString + " \u00a79" + formattedDelta(roundTime,bestSegment);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
        }
        sendBar();
    }
    public static void compareBest(byte round, int gameTime, @NotNull Category category) {
        sendBar();
        TimesFile timesFile = category.getByGameMode(GameMode.getCurrentGameMode());
        int personalBest = timesFile.getPersonalBest(round);
        if (personalBest == 0) {
            timesFile.setPersonalBest(round, gameTime);

            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                    "\u00a7l\u00a7e Category: " + category.getName() + " - ***" + "\u00a7l\u00a76 NEW PERSONAL BEST! " + "\u00a7l\u00a7e***"
            ));

            final String timeString = formattedTime(gameTime);
            final String message = "\u00a7cRound " + round + "\u00a7e finished at \u00a7a" + timeString + "\u00a7e!";
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));

        } else {
            if (gameTime<personalBest) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                        "\u00a7l\u00a7e Category: " + category.getName() + " - ***" + "\u00a7l\u00a76 NEW PERSONAL BEST! " + "\u00a7l\u00a7e***"
                ));
                timesFile.setPersonalBest(round, gameTime);
            }

            final String timeString = formattedTime(gameTime);
            final String message = "\u00a7cRound " + round + "\u00a7e finished at \u00a7a" + timeString + " \u00a79" + formattedDelta(gameTime, personalBest);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));

        }
        sendBar();
    }
    private static String formattedTime(int gameTime) {
        return String.format("%d:%02d.%d",
                (gameTime *50) / 60000,
                ((gameTime *50) % 60000) / 1000,
                ((gameTime *50) % 1000) / 100
        );
    }
    private static String formattedDelta(int newTime, int prevTime) {
        double delta = (double) (newTime - prevTime) / 20;
        if (delta<0) {
            return String.valueOf(delta);
        } else return ("+" + delta);
    }
    private static void sendBar() {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                "\u00a7l\u00a7a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"
        ));
    }
}
