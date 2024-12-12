package com.github.stachelbeere1248.zombiesutils.timer;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import org.jetbrains.annotations.Contract;

public class RecordMessageSender {
    private final StringBuilder recordMessage;
    private final int newTime;
    private final int oldTime;
    private final int round;
    private final String deltaString;
    private final String timeString;
    private String copyString;

    public RecordMessageSender(final String categoryName, final int round, final int newTime, final int oldTime) {
        this.recordMessage = new StringBuilder(
                "§l§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬\n§e Category: §d" + categoryName
        );
        this.newTime = newTime;
        this.oldTime = oldTime;
        this.deltaString = oldTime != 0 ? " " + formattedDelta(newTime, oldTime) : "";
        this.timeString = formattedTime(newTime);
        this.round = round;
    }

    public void sendRecordMessage() {
        final ChatComponentText message = new ChatComponentText(
                this.recordMessage
                        .append("\n§l§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬")
                        .toString()
        );
        message.setChatStyle(new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, this.copyString)));
        Minecraft.getMinecraft().thePlayer.addChatMessage(message);
    }

    public void gameSplit() {
        final String announcement = newTime < oldTime && ZombiesUtils.getInstance().getConfig().getAnnouncePB() ?
                "\n§e§l***§6§l NEW PERSONAL BEST! §e§l***" : "";
        this.recordMessage.append(announcement)
                .append("\n§cRound ")
                .append(round)
                .append("§e finished at §a")
                .append(timeString)
                .append("§9")
                .append(deltaString)
                .append("§e!");
        this.copyString = deltaString.isEmpty() ?
                String.format("Round %d finished at %s!", round, timeString) :
                String.format("Round %d finished at %s (%s)!", round, timeString, deltaString);
    }

    public void roundSplit() {
        final String announcement = newTime < oldTime && ZombiesUtils.getInstance().getConfig().getAnnouncePB() ?
                "\n§e§l***§6§l NEW BEST SEGMENT! §e§l***" : "";
        final String timeString = formattedTime(newTime);
        final String deltaString = oldTime != 0 ? formattedDelta(newTime, oldTime) : "";
        this.recordMessage.append(announcement)
                .append("\n§cRound ")
                .append(round)
                .append("§e took §a")
                .append(timeString)
                .append("§9")
                .append(deltaString)
                .append("§e!");
        this.copyString = deltaString.isEmpty() ?
                String.format("Round %d took %s!", round, timeString) :
                String.format("Round %d took %s (%s)!", round, timeString, deltaString);
    }

    public void helicopterSplit() {
        final String announcement = newTime < oldTime && ZombiesUtils.getInstance().getConfig().getAnnouncePB() ?
                "\n§e§l***§6§l NEW PERSONAL BEST! §e§l***" : "";
        this.recordMessage.append(announcement)
                .append("\n§8§lHelicopter §r§ecalled at §a")
                .append(timeString)
                .append("§9")
                .append(deltaString)
                .append("§e!");
        this.copyString = deltaString.isEmpty() ?
                String.format("Helicopter called at %s!", timeString) :
                String.format("Helicopter called at %s (%s)!", timeString, deltaString);
    }

    @Contract(pure = true)
    private String formattedTime(int time) {
        time *= 50;
        return String.format("%d:%02d.%d%d",
                time / 60000,
                (time % 60000) / 1000,
                (time % 1000) / 100,
                (time % 100) / 10
        );
    }

    @Contract(pure = true)
    private String formattedDelta(int newTime, int oldTime) {
        final double delta = (double) (newTime - oldTime) / 20;
        return String.format("%+.2f", delta);
    }
}
