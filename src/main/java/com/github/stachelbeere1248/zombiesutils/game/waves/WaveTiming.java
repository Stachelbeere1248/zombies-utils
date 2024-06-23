package com.github.stachelbeere1248.zombiesutils.game.waves;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import com.github.stachelbeere1248.zombiesutils.utils.Scoreboard;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class WaveTiming {
    public static int rl = 0;
    public static void onTick() {
        if (Scoreboard.isNotZombies()) return;
        ZombiesUtils.getInstance().getGameManager().getGame().ifPresent(
                game -> {
                    byte[] waves = Waves.get(game.getGameMode().getMap(), game.getRound());
                    final int roundTime = game.getTimer().getRoundTime();
                    final int[] auditory = ZombiesUtils.getInstance().getConfig().getAuditory();
                    for (int wave : waves) {
                        wave = wave * 20 + rl;
                        final Integer pre = roundTime - wave;
                        if (Arrays.stream(auditory).anyMatch(pre::equals)) {
                            Minecraft.getMinecraft().thePlayer.playSound("note.pling", 1, 2);
                        }
                    }
                }
        );
    }

    public static void toggleRL() {
        if (rl == 0) rl = ZombiesUtils.getInstance().getConfig().getOffset();
        else rl = 0;
    }
}
