package com.github.stachelbeere1248.zombiesutils.timer.recorder;

import com.github.stachelbeere1248.zombiesutils.game.Map;
import com.google.gson.Gson;

import java.util.Arrays;

public class FileData{
    private final short[] bestSegments; //in ticks, max ~27 min
    private final int[] personalBests; //in ticks,
    public FileData(Map map) {
        if (map == Map.ALIEN_ARCADIUM) {
            bestSegments = new short[105];
            personalBests = new int[105];
        } else {
            bestSegments = new short[30];
            personalBests = new int[30];
        }
        Arrays.fill(bestSegments, (short) 0);
        Arrays.fill(personalBests, 0);
    }
    String getAsJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this, FileData.class);
    }

    public short getBestSegment(int index) {
        return bestSegments[index];
    }
    public int getPersonalBest(int index) {
        return personalBests[index];
    }
    void setBestSegment(int index, short ticks) {
        bestSegments[index] = ticks;
    }
    void setPersonalBest(int index, int ticks) {
        personalBests[index] = ticks;
    }
}