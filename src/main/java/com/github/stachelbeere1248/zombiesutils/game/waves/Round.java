package com.github.stachelbeere1248.zombiesutils.game.waves;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Round {
    private final Wave[] waves;

    public Round(final Wave[] waves) {
        this.waves = waves;
    }

    public Wave[] getWaves() {
        return waves;
    }

    public short[] getWaveTimes() {
        short[] ret = new short[waves.length];
        for (int i = 0; i < waves.length; i++) {
            ret[i] = waves[i].getTime();
        }
        return ret;
    }
}
