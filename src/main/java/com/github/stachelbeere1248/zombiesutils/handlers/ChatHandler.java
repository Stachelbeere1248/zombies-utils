package com.github.stachelbeere1248.zombiesutils.handlers;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import com.github.stachelbeere1248.zombiesutils.game.Difficulty;
import com.github.stachelbeere1248.zombiesutils.game.GameMode;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Pattern;

public class ChatHandler {
    public ChatHandler () {
    }

    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("§[0-9A-FK-ORZ]", Pattern.CASE_INSENSITIVE);

    @SubscribeEvent
    public void difficultyChange(ClientChatReceivedEvent event) {
        String message = STRIP_COLOR_PATTERN.matcher(event.message.getUnformattedText()).replaceAll("").trim();
        GameMode gameMode = GameMode.getCurrentGameMode();

        if (message.contains(":")) return;
        if (gameMode == null) return;

        if (message.contains("Hard Difficulty") || message.contains("困难") || message.contains("困難")) {
            gameMode.changeDifficulty(Difficulty.HARD);
            ZombiesUtils.getInstance().getLogger().debug("Changed to Hard");
        } else if (message.contains("RIP Difficulty") || message.contains("安息") || message.contains("RIP")) {
            gameMode.changeDifficulty(Difficulty.RIP);
            ZombiesUtils.getInstance().getLogger().debug("Changed to RIP");
        }
    }

}
