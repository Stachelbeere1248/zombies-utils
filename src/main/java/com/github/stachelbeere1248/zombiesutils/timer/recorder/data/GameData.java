package com.github.stachelbeere1248.zombiesutils.timer.recorder.data;

import com.github.stachelbeere1248.zombiesutils.game.Map;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.ISplitsData;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class GameData implements ISplitsData {
    private final short[] segments;

    public GameData(@NotNull Map map) throws IllegalStateException {
        switch (map) {
            case ALIEN_ARCADIUM:
                segments = new short[105];
                break;
            case DEAD_END:
            case BAD_BLOOD:
                segments = new short[30];
                break;
            default:
                throw new IllegalStateException("Not a map: " + map);
        }
        Arrays.fill(segments, (short) 0);
    }

    @Override @NotNull
    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this.segments);
    }

    public void setSegment(int index, short ticks) {
        segments[index] = ticks;
    }
}
