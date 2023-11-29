package com.github.stachelbeere1248.zombiesutils.timer.recorder;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public abstract class SplitsFile extends File {
    public SplitsFile(String parent, @NotNull String child) {
        super(parent, child);
    }
    public SplitsFile(File category, String child) {
        super(category, child);
    }
    abstract public ISplitsData getData();
}
