package com.github.stachelbeere1248.zombiesutils.timer.recorder.files;

import com.github.stachelbeere1248.zombiesutils.game.GameMode;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.FileManager;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.ISplitsData;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.SplitsFile;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.data.CategoryData;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class CategoryFile extends SplitsFile {
    private final CategoryData data;
    private final GameMode gameMode;
    public CategoryFile(File category, @NotNull GameMode gameMode) {
        // Game-directory -> custom category -> file named "MAP_DIFFICULTY.times"
        // Content encoded in StandardCharsets.UTF_16
        super(category,gameMode.getMap() + "_" + gameMode.getDifficulty() + ".times");
        this.gameMode = gameMode;
        data = FileManager.categoryReadOrCreate(this);
    }

    public short getBestSegment(int round) {
        return data.getBestSegment(round-1);
    }
    public void setBestSegment(int round, short ticks) {
        data.setBestSegment(round-1, ticks);

        try { FileManager.writeDataToFile(this); }
        catch (IOException e) { throw new RuntimeException(e); }
    }
    public int getPersonalBest(int round) {
        return data.getPersonalBest(round-1);
    }
    public void setPersonalBest(int round, int ticks) {
        data.setPersonalBest(round-1, ticks);

        try { FileManager.writeDataToFile(this); }
        catch (IOException e) { throw new RuntimeException(e); }
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    @Override
    public ISplitsData getData() {
        return data;
    }
}
