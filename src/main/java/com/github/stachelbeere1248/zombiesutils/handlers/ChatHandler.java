package com.github.stachelbeere1248.zombiesutils.handlers;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import com.github.stachelbeere1248.zombiesutils.game.enums.Difficulty;
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
    public void difficultyChange(@NotNull final ClientChatReceivedEvent event) {
        if (event.message.getUnformattedText().contains(":")) return;
        final String message = STRIP_COLOR_PATTERN.matcher(event.message.getUnformattedText()).replaceAll("").trim();

        if (LanguageSupport.containsHard(message)) {
            ZombiesUtils.getInstance().getGameManager().setDifficulty(Difficulty.HARD);
        } else if (LanguageSupport.containsRIP(message)) {
            ZombiesUtils.getInstance().getGameManager().setDifficulty(Difficulty.RIP);
        } else {
            ZombiesUtils.getInstance().getGameManager().getGame().ifPresent(
                    game -> {
                        if (LanguageSupport.isHelicopterIncoming(message)) game.helicopter();
                    }
            );
        }
    }
}
