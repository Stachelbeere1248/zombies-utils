package com.github.stachelbeere1248.zombiesutils.timer.recorder.data;

import com.github.stachelbeere1248.zombiesutils.game.Map;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.ISplitsData;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class GameData implements ISplitsData {
    private final short[] segments;

    public GameData(@NotNull Map map) {
        switch (map) {
            case ALIEN_ARCADIUM:
                segments = new short[105];
                break;
            case DEAD_END: case BAD_BLOOD:
                segments = new short[30];
                break;
            default:
                throw new IllegalStateException("Not a map: " + map);
        }
        Arrays.fill(segments, (short) 0);
    }

    @Override
    public String toJSON() {
        StringBuilder JSON = new StringBuilder("[");
        for (short segment: segments) {
            JSON.append(segment + ',');
        }
        JSON.setCharAt(JSON.length()-1,']');
        return JSON.toString();
    }
    public void setSegment(int index, short ticks) {
        segments[index] = ticks;
    }
}
