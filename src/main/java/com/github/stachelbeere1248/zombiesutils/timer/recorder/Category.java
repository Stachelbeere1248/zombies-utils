package com.github.stachelbeere1248.zombiesutils.timer.recorder;

import com.github.stachelbeere1248.zombiesutils.config.ZombiesUtilsConfig;
import com.github.stachelbeere1248.zombiesutils.game.Difficulty;
import com.github.stachelbeere1248.zombiesutils.game.GameMode;
import com.github.stachelbeere1248.zombiesutils.game.Map;
import com.github.stachelbeere1248.zombiesutils.timer.Timer;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Category {
    private static String selectedCategory = ZombiesUtilsConfig.getDefaultCategory();
    public final TimesFile[] timesFiles = new TimesFile[7];
    private final String name;
    public Category() {
        timesFiles[0] = new TimesFile(selectedCategory, new GameMode(Map.DEAD_END));
        timesFiles[1] = new TimesFile(selectedCategory, new GameMode(Map.DEAD_END, Difficulty.HARD));
        timesFiles[2] = new TimesFile(selectedCategory, new GameMode(Map.DEAD_END, Difficulty.RIP));

        timesFiles[3] = new TimesFile(selectedCategory, new GameMode(Map.BAD_BLOOD));
        timesFiles[4] = new TimesFile(selectedCategory, new GameMode(Map.BAD_BLOOD, Difficulty.HARD));
        timesFiles[5] = new TimesFile(selectedCategory, new GameMode(Map.BAD_BLOOD, Difficulty.RIP));

        timesFiles[6] = new TimesFile(selectedCategory, new GameMode(Map.ALIEN_ARCADIUM));
        name = selectedCategory;
    }



    public TimesFile getByGameMode(@NotNull GameMode gameMode) {
        if (gameMode.is(Map.DEAD_END, Difficulty.NORMAL)) return timesFiles[0];
        else if (gameMode.is(Map.BAD_BLOOD, Difficulty.NORMAL)) return timesFiles[3];
        else if (gameMode.is(Map.ALIEN_ARCADIUM, Difficulty.NORMAL)) return timesFiles[6];

        else if (gameMode.is(Map.DEAD_END, Difficulty.HARD)) return timesFiles[1];
        else if (gameMode.is(Map.DEAD_END, Difficulty.RIP)) return timesFiles[2];

        else if (gameMode.is(Map.BAD_BLOOD, Difficulty.HARD)) return timesFiles[4];
        else if (gameMode.is(Map.BAD_BLOOD, Difficulty.RIP)) return timesFiles[5];
        else throw new IllegalStateException("Unexpected value: " + gameMode);
    }

    public static void setSelectedCategory(String selectedCategory) {
        Category.selectedCategory = selectedCategory;
        Timer.getInstance().ifPresent(timer -> timer.setCategory(new Category()));
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
