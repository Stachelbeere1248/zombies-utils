package com.github.stachelbeere1248.zombiesutils.timer.recorder;

import com.github.stachelbeere1248.zombiesutils.timer.recorder.data.CategoryData;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.files.CategoryFile;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileManager {
    private static CategoryData readDataFromFile(@NotNull File file) throws FileNotFoundException {
        if (!file.exists()) throw new FileNotFoundException();

        String dataJson;
        Gson gson = new Gson();

        try {
            dataJson = FileUtils.readFileToString(file, StandardCharsets.US_ASCII);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return gson.fromJson(dataJson, CategoryData.class);
    }
    public static void createDataFile(ISplitsData splitsData, @NotNull SplitsFile splitsFile) {
        try {
            //noinspection ResultOfMethodCallIgnored
            splitsFile.getParentFile().mkdirs();
            //noinspection ResultOfMethodCallIgnored
            splitsFile.createNewFile();
            writeDataToFile(splitsData, splitsFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void writeDataToFile(@NotNull ISplitsData splitsData, SplitsFile splitsFile) throws IOException {
        FileUtils.writeStringToFile(splitsFile, splitsData.toJSON(), StandardCharsets.US_ASCII);
    }
    public static CategoryData categoryReadOrCreate(CategoryFile file) {
        CategoryData data;
        try {
            data = FileManager.readDataFromFile(file);
        } catch (FileNotFoundException ignored) {
            data = new CategoryData(file.getGameMode().getMap());
            FileManager.createDataFile(data, file);
        }
        return data;
    }
}
