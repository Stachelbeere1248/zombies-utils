package com.github.stachelbeere1248.zombiesutils.timer.recorder;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import com.github.stachelbeere1248.zombiesutils.game.GameMode;
import com.github.stachelbeere1248.zombiesutils.timer.Timer;
import org.jetbrains.annotations.NotNull;
import scala.reflect.io.Directory;

import java.io.File;
import java.util.List;

public class Category {
    private static String selectedCategory = "default"; // read from config ?
    public final TimesFile[] timesFiles = new TimesFile[7];
    private final String name;
    public Category() {
        timesFiles[0] = new TimesFile(selectedCategory, GameMode.DEAD_END_NORMAL);
        timesFiles[1] = new TimesFile(selectedCategory, GameMode.DEAD_END_HARD);
        timesFiles[2] = new TimesFile(selectedCategory, GameMode.DEAD_END_RIP);

        timesFiles[3] = new TimesFile(selectedCategory, GameMode.BAD_BLOOD_NORMAL);
        timesFiles[4] = new TimesFile(selectedCategory, GameMode.BAD_BLOOD_HARD);
        timesFiles[5] = new TimesFile(selectedCategory, GameMode.BAD_BLOOD_RIP);

        timesFiles[6] = new TimesFile(selectedCategory, GameMode.ALIEN_ARCADIUM);
        name = selectedCategory;
    }



    public TimesFile getByGameMode(@NotNull GameMode gameMode) {
        if (gameMode.equals(GameMode.DEAD_END_NORMAL)) return timesFiles[0];
        if (gameMode.equals(GameMode.DEAD_END_HARD)) return timesFiles[1];
        if (gameMode.equals(GameMode.DEAD_END_RIP)) return timesFiles[2];
        if (gameMode.equals(GameMode.BAD_BLOOD_NORMAL)) return timesFiles[3];
        if (gameMode.equals(GameMode.BAD_BLOOD_HARD)) return timesFiles[4];
        if (gameMode.equals(GameMode.BAD_BLOOD_RIP)) return timesFiles[5];
        if (gameMode.equals(GameMode.ALIEN_ARCADIUM)) return timesFiles[6];
        throw new IllegalStateException("Unexpected value: " + gameMode);
    }

    public static void setSelectedCategory(String selectedCategory) {
        Category.selectedCategory = selectedCategory;
        if (!Timer.getInstance().isPresent()) return;
        Timer.getInstance().get().category = new Category();
    }
    public static String[] getCategories() {
        File dir = new File("zombies");
        if (dir.isDirectory()) return dir.list();
        else return new String[0];
    }

    public String getName() {
        return name;
    }
}
