package com.github.stachelbeere1248.zombiesutils.utils;

import com.github.stachelbeere1248.zombiesutils.timer.recorder.FileData;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.TimesFile;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileManager {
    private static FileData readDataFromFile(@NotNull File file) throws FileNotFoundException {
        if (!file.exists()) throw new FileNotFoundException();

        String dataJson;
        Gson gson = new Gson();

        try {
            dataJson = FileUtils.readFileToString(file, StandardCharsets.UTF_16);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return gson.fromJson(dataJson, FileData.class);
    }
    private static void createDataFile(FileData fileData, @NotNull File file) {
        try {
            //noinspection ResultOfMethodCallIgnored
            file.getParentFile().mkdirs();
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeDataToFile(fileData, file);
    }
    public static void writeDataToFile(@NotNull FileData fileData, File file) {
        try {
            FileUtils.writeStringToFile(file, fileData.getAsJsonString(), StandardCharsets.UTF_16);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static FileData readOrCreate(TimesFile file) {
        FileData data;
        try {
            data = FileManager.readDataFromFile(file);
        } catch (FileNotFoundException ignored) {
            data = new FileData(file.getGameMode().getMap());
            FileManager.createDataFile(data, file);
        }
        return data;
    }
}