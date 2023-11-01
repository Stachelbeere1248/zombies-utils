package com.github.stachelbeere1248.zombiesutils.handlers;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import com.github.stachelbeere1248.zombiesutils.game.Difficulty;
import com.github.stachelbeere1248.zombiesutils.game.GameMode;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Pattern;

public class ChatHandler {

    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("§[0-9A-FK-ORZ]", Pattern.CASE_INSENSITIVE);

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        String message = STRIP_COLOR_PATTERN.matcher(event.message.getUnformattedText()).replaceAll("").trim();
        GameMode gameMode = GameMode.getCurrentGameMode();

        if (message.contains(":")) return;
        if (gameMode == null) return;
        ZombiesUtils.getInstance().getLogger().debug("Chat-event: " + message);

        if (message.contains("Hard Difficulty") || message.contains("困难") || message.contains("困難")) {
            gameMode.changeDifficulty(Difficulty.HARD);
        } else if (message.contains("RIP Difficulty") || message.contains("安息") || message.contains("RIP")) {
            gameMode.changeDifficulty(Difficulty.RIP);
        }
    }

}
