package com.github.stachelbeere1248.zombiesutils.game.waves;

import com.github.stachelbeere1248.zombiesutils.config.ZombiesUtilsConfig;
import com.github.stachelbeere1248.zombiesutils.timer.Timer;
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
        Timer.getInstance().ifPresent(timer -> {
            int wave = (getLastWave(timer)*20)+rl;
            final int roundTime = timer.roundTime();
            final int[] auditory = ZombiesUtilsConfig.getAuditory();
            final Integer pre = roundTime-wave;
            if (Arrays.stream(auditory).anyMatch(pre::equals)) {
                Minecraft.getMinecraft().thePlayer.playSound("note.pling",1,2);
            }
        });
    }

    public static void toggleRL() {
        if (rl == 0) rl = ZombiesUtilsConfig.getWaveOffset();
        else rl = 0;
    }
}
