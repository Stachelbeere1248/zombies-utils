package com.github.stachelbeere1248.zombiesutils.timer.recorder;

import com.github.stachelbeere1248.zombiesutils.game.GameMode;
import org.jetbrains.annotations.NotNull;
import scala.reflect.io.Directory;

import java.io.File;

public class TimesFile extends File {
    private final FileData fileData;
    private final GameMode gameMode;
    public TimesFile(String category, GameMode gameMode) {
        // Game-directory -> custom category -> file named "MAP_DIFFICULTY.times"
        // Content encoded in StandardCharsets.UTF_16
        super("zombies" + File.separator + category,gameMode.getMap() + "_" + gameMode.getDifficulty() + ".times");
        this.gameMode = gameMode;
        fileData = FileManager.readOrCreate(this);
    }

    public short getBestSegment(int round) {
        return fileData.getBestSegment(round-1);
    }
    public void setBestSegment(int round, short ticks) {
        fileData.setBestSegment(round-1, ticks);
        FileManager.writeDataToFile(fileData,this);
    }
    public int getPersonalBest(int round) {
        return fileData.getPersonalBest(round-1);
    }
    public void setPersonalBest(int round, int ticks) {
        fileData.setPersonalBest(round-1, ticks);
        FileManager.writeDataToFile(fileData,this);
    }

    GameMode getGameMode() {
        return gameMode;
    }
}
