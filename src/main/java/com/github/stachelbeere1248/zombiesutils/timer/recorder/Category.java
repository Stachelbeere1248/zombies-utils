package com.github.stachelbeere1248.zombiesutils.timer.recorder;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import com.github.stachelbeere1248.zombiesutils.config.ZombiesUtilsConfig;
import com.github.stachelbeere1248.zombiesutils.game.enums.Difficulty;
import com.github.stachelbeere1248.zombiesutils.game.GameMode;
import com.github.stachelbeere1248.zombiesutils.game.enums.Map;
import com.github.stachelbeere1248.zombiesutils.timer.Timer;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.files.CategoryFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Category {
    private static String selectedCategory = ZombiesUtilsConfig.getDefaultCategory();
    public final CategoryFile[] categoryFiles = new CategoryFile[7];
    private final String name;

    public Category() {
        final File category = new File(new File("zombies", "splits"), selectedCategory);
        categoryFiles[0] = new CategoryFile(category, new GameMode(Map.DEAD_END));
        categoryFiles[1] = new CategoryFile(category, new GameMode(Map.DEAD_END, Difficulty.HARD));
        categoryFiles[2] = new CategoryFile(category, new GameMode(Map.DEAD_END, Difficulty.RIP));

        categoryFiles[3] = new CategoryFile(category, new GameMode(Map.BAD_BLOOD));
        categoryFiles[4] = new CategoryFile(category, new GameMode(Map.BAD_BLOOD, Difficulty.HARD));
        categoryFiles[5] = new CategoryFile(category, new GameMode(Map.BAD_BLOOD, Difficulty.RIP));

        categoryFiles[6] = new CategoryFile(category, new GameMode(Map.ALIEN_ARCADIUM));
        this.name = Category.selectedCategory;
    }

    public static void setSelectedCategory(String selectedCategory) {
        if (!ZombiesUtils.isHypixel()) selectedCategory += "-practise";
        Category.selectedCategory = selectedCategory;
        Timer.getInstance().ifPresent(timer -> timer.setCategory(new Category()));
    }

    public static String[] getCategories() {
        File dir = new File("zombies" + File.separator + "splits");
        if (dir.isDirectory()) return dir.list();
        else return new String[0];
    }

    public CategoryFile getByGameMode(@NotNull GameMode gameMode) {
        if (gameMode.is(Map.DEAD_END, Difficulty.NORMAL)) return categoryFiles[0];
        else if (gameMode.is(Map.BAD_BLOOD, Difficulty.NORMAL)) return categoryFiles[3];
        else if (gameMode.is(Map.ALIEN_ARCADIUM, Difficulty.NORMAL)) return categoryFiles[6];

        else if (gameMode.is(Map.DEAD_END, Difficulty.HARD)) return categoryFiles[1];
        else if (gameMode.is(Map.DEAD_END, Difficulty.RIP)) return categoryFiles[2];

        else if (gameMode.is(Map.BAD_BLOOD, Difficulty.HARD)) return categoryFiles[4];
        else if (gameMode.is(Map.BAD_BLOOD, Difficulty.RIP)) return categoryFiles[5];
        else throw new IllegalStateException("Unexpected value: " + gameMode);
    }

    public String getName() {
        return name;
    }
}
