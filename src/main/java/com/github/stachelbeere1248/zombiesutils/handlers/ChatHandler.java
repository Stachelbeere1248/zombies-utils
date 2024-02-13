package com.github.stachelbeere1248.zombiesutils.handlers;

import com.github.stachelbeere1248.zombiesutils.game.enums.Difficulty;
import com.github.stachelbeere1248.zombiesutils.game.GameMode;
import com.github.stachelbeere1248.zombiesutils.timer.Timer;
import com.github.stachelbeere1248.zombiesutils.utils.LanguageSupport;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class ChatHandler {
    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("§[0-9A-FK-ORZ]", Pattern.CASE_INSENSITIVE);

    public ChatHandler() {
    }

    @SubscribeEvent
    public void difficultyChange(@NotNull ClientChatReceivedEvent event) {
        if (!Timer.getInstance().isPresent()) return;
        String message = STRIP_COLOR_PATTERN.matcher(event.message.getUnformattedText()).replaceAll("").trim();
        GameMode gameMode = Timer.getInstance().get().getGameMode();

        if (message.contains(":")) return;
        if (LanguageSupport.containsHard(message)) {
            gameMode.changeDifficulty(Difficulty.HARD);
        } else if (LanguageSupport.containsRIP(message)) {
            gameMode.changeDifficulty(Difficulty.RIP);
        }
    }
}
