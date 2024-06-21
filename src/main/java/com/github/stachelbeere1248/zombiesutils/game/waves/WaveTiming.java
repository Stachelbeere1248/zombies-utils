package com.github.stachelbeere1248.zombiesutils.game.waves;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import com.github.stachelbeere1248.zombiesutils.timer.Timer;
import com.github.stachelbeere1248.zombiesutils.utils.Scoreboard;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class WaveTiming {
    public static int rl = 0;

    public static byte[] getWaves(@NotNull Timer timer) {
        return Waves.get(
                timer.getGameMode().getMap(),
                timer.getRound()
        );
    }

    public static byte getLastWave(@NotNull Timer timer) {
        return Waves.getLastWave(
                timer.getGameMode().getMap(),
                timer.getRound()
        );
    }

    public static void onTick() {
        if (Scoreboard.isNotZombies()) return;
        //TODO: Assert correct server number!
        Timer.getInstance().ifPresent(timer -> {
            byte[] waves = getWaves(timer);
            final int roundTime = timer.roundTime();
            final int[] auditory = ZombiesUtils.getInstance().getConfig().getAuditory();
            for (int wave : waves) {
                wave = wave * 20 + rl;
                final Integer pre = roundTime - wave;
                if (Arrays.stream(auditory).anyMatch(pre::equals)) {
                    Minecraft.getMinecraft().thePlayer.playSound("note.pling", 1, 2);
                }
            }
        });
    }

    public static void toggleRL() {
        if (rl == 0) rl = ZombiesUtils.getInstance().getConfig().getOffset();
        else rl = 0;
    }
}
