package com.github.stachelbeere1248.zombiesutils.game.waves;

import com.github.stachelbeere1248.zombiesutils.game.enums.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("DuplicatedCode")
public class Waves {
    private static final byte[][]
            deadEndWaveTimes = {{10, 20}, {10, 20}, {10, 20, 35}, {10, 20, 35}, {10, 22, 37}, {10, 22, 44}, {10, 25, 47}, {10, 25, 50}, {10, 22, 38}, {10, 24, 45}, {10, 25, 48}, {10, 25, 50}, {10, 25, 50}, {10, 25, 45}, {10, 25, 46}, {10, 24, 47}, {10, 24, 47}, {10, 24, 47}, {10, 24, 47}, {10, 24, 49}, {10, 23, 44}, {10, 23, 45}, {10, 23, 42}, {10, 23, 43}, {10, 23, 43}, {10, 23, 36}, {10, 24, 44}, {10, 24, 42}, {10, 24, 42}, {10, 24, 45}},
            badBloodWaveTimes = {{10, 22}, {10, 22}, {10, 22}, {10, 22, 34}, {10, 22, 34}, {10, 22, 34}, {10, 22, 34}, {10, 22, 34}, {10, 22, 34}, {10, 22, 34}, {10, 22, 34}, {10, 22, 34}, {10, 22, 34}, {10, 22, 34}, {10, 22, 34}, {10, 22, 34}, {10, 22, 34}, {10, 22, 34}, {10, 22, 34}, {10, 22, 34}, {10, 22, 34}, {10, 22, 34}, {10, 22, 34}, {10, 22, 34}, {10, 22, 34}, {10, 24, 38}, {10, 24, 38}, {10, 22, 34}, {10, 24, 38}, {10, 22, 34}},
            alienArcadiumWaveTimes = {{10, 13, 16, 19}, {10, 14, 18, 22}, {10, 13, 16, 19}, {10, 14, 17, 21, 25, 28}, {10, 14, 18, 22, 26, 30}, {10, 14, 19, 23, 28, 32}, {10, 15, 19, 23, 27, 31}, {10, 15, 20, 25, 30, 35}, {10, 14, 19, 23, 28, 32}, {10, 16, 22, 27, 33, 38}, {10, 16, 21, 27, 32, 38}, {10, 16, 22, 28, 34, 40}, {10, 16, 22, 28, 34, 40}, {10, 16, 21, 26, 31, 36}, {10, 17, 24, 31, 38, 46}, {10, 16, 22, 27, 33, 38}, {10, 14, 19, 23, 28, 32}, {10, 14, 19, 23, 28, 32}, {10, 14, 18, 22, 26, 30}, {10, 15, 21, 26, 31, 36}, {10, 14, 19, 23, 28, 32}, {10, 14, 19, 23, 28, 34}, {10, 14, 18, 22, 26, 30}, {10, 14, 19, 23, 28, 32}, {10}, {10, 23, 36}, {10, 22, 34}, {10, 20, 30}, {10, 24, 38}, {10, 22, 34}, {10, 22, 34}, {10, 21, 32}, {10, 22, 34}, {10, 22, 34}, {10}, {10, 22, 34}, {10, 20, 31}, {10, 22, 34}, {10, 22, 34}, {10, 22, 34, 37, 45}, {10, 21, 32}, {10, 22, 34}, {10, 13, 22, 25, 34, 37}, {10, 22, 34}, {10, 22, 34, 35}, {10, 21, 32, 35}, {10, 20, 30}, {10, 20, 30, 33}, {10, 21, 32}, {10, 22, 34, 37}, {10, 20, 30, 33}, {10, 22, 34, 37}, {10, 22, 34, 37}, {10, 20, 32, 35, 39}, {10, 16, 22, 28, 34, 40}, {10, 14, 18}, {10, 14, 18}, {10, 22, 34, 37, 38}, {10, 14, 18, 22, 26, 30}, {10, 20, 30, 33}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 27, 32}, {10, 14, 18, 22, 27, 32}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {10, 14, 18, 22, 26, 30}, {5}, {5}, {5}, {5}, {5}},
            prisonWaveTimes = {{10, 20}, {10, 20, 30}, {10, 17, 24, 31}, {10, 17, 24, 31}, {10, 20, 30}, {10, 20, 30}, {10, 20, 30}, {10, 25, 40}, {10, 25, 35}, {10, 25, 45}, {10, 25, 40}, {10, 25, 37}, {10, 22, 34}, {10, 25, 37}, {10, 25, 40}, {10, 22, 37}, {10, 22, 42}, {10, 25, 45}, {10, 25, 45}, {10, 25, 40}, {10, 20, 35, 55, 75}, {10, 25, 40}, {10, 30, 50}, {10, 30, 50}, {10, 25, 45}, {10, 30, 50}, {10, 25, 45}, {10, 30, 50}, {10, 30, 55}, {10}, {0, 15, 30, 45, 60, 75, 90, 105}};

    @Contract(pure = true)
    public static byte[] get(@NotNull Map map, int round) {
        byte[] ret = new byte[]{0};
        try {
            switch (map) {
                case DEAD_END:
                    ret = deadEndWaveTimes[round - 1];
                    break;
                case BAD_BLOOD:
                    ret = badBloodWaveTimes[round - 1];
                    break;
                case ALIEN_ARCADIUM:
                    ret = alienArcadiumWaveTimes[round - 1];
                    break;
                case PRISON:
                    ret = prisonWaveTimes[round - 1];
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(
                    new ChatComponentText("Achievement get: Round " + round + " " + map)
            );
        }
        return ret;
    }

    public static byte getLastWave(@NotNull Map map, int round) {
        byte[] aByte = get(map, round);
        return aByte[aByte.length - 1];
    }

}