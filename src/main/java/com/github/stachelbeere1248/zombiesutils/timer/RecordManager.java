package com.github.stachelbeere1248.zombiesutils.timer;

import com.github.stachelbeere1248.zombiesutils.game.GameMode;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.Category;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.TimesFile;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class RecordManager {
    public static void compareSegment(byte round, short roundTime, @NotNull Category category) throws IndexOutOfBoundsException {
        sendBar();
        TimesFile timesFile = category.getByGameMode(GameMode.getCurrentGameMode());
        short bestSegment = timesFile.getBestSegment(round);
        if (bestSegment == (short) 0) {
            timesFile.setBestSegment(round, roundTime);

            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                    "§l§e Category: " + category.getName() + " - ***" + "§l§6 NEW BEST SEGMENT! " + "§l§e***"
            ));
            final String timeString = formattedTime(roundTime);
            final String message = "§cRound " + round + "§e took §a" + timeString + "§e!";
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));

        } else {
            if (roundTime<bestSegment) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                        "§l§e Category: " + category.getName() + " - ***" + "§l§6 NEW BEST SEGMENT! " + "§l§e***"
                ));
                timesFile.setBestSegment(round, roundTime);
            }
            final String timeString = formattedTime(roundTime);
            final String message = "§cRound " + round + "§e took §a" + timeString + " §9" + formattedDelta(roundTime,bestSegment);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
        }
        sendBar();
    }
    public static void compareBest(byte round, int gameTime, @NotNull Category category) throws IndexOutOfBoundsException {
        sendBar();
        TimesFile timesFile = category.getByGameMode(GameMode.getCurrentGameMode());
        int personalBest = timesFile.getPersonalBest(round);
        if (personalBest == 0) {
            timesFile.setPersonalBest(round, gameTime);

            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                    "§l§e Category: " + category.getName() + " - ***" + "§l§6 NEW PERSONAL BEST! " + "§l§e***"
            ));

            final String timeString = formattedTime(gameTime);
            final String message = "§cRound " + round + "§e finished at §a" + timeString + "§e!";
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));

        } else {
            if (gameTime<personalBest) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                        "§l§e Category: " + category.getName() + " - ***" + "§l§6 NEW PERSONAL BEST! " + "§l§e***"
                ));
                timesFile.setPersonalBest(round, gameTime);
            }

            final String timeString = formattedTime(gameTime);
            final String message = "§cRound " + round + "§e finished at §a" + timeString + " §9" + formattedDelta(gameTime, personalBest);
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
    @Contract(pure = true)
    private static @NotNull String formattedDelta(int newTime, int prevTime) {
        double delta = (double) (newTime - prevTime) / 20;
        if (delta<0) {
            return String.valueOf(delta);
        } else return ("+" + delta);
    }
    private static void sendBar() {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                "§l§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"
        ));
    }
}
