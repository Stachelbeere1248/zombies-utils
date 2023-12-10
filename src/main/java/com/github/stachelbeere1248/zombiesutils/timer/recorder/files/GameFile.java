package com.github.stachelbeere1248.zombiesutils.timer.recorder.files;


import com.github.stachelbeere1248.zombiesutils.game.Map;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.FileManager;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.ISplitsData;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.SplitsFile;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.data.GameData;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class GameFile extends SplitsFile {
    private final GameData data;

    public GameFile(String serverNumber, Map map) {
        super(new File("zombies", "runs"), formattedTime() + "_" + serverNumber + ".seg");
        data = new GameData(map);
        FileManager.createDataFile(this);
    }

    private static @NotNull String formattedTime() {
        final LocalDateTime dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace(':','-').replaceFirst("T","_");
    }

    public void setSegment(int round, short ticks) {
        data.setSegment(round-1, ticks);

        try { FileManager.writeDataToFile(this); }
        catch (IOException e) { throw new RuntimeException(e); }
    }

    @Override
    public ISplitsData getData() {
        return data;
    }
}
