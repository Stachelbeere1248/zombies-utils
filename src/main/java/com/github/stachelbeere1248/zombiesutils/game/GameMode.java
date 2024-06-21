package com.github.stachelbeere1248.zombiesutils.game;

import com.github.stachelbeere1248.zombiesutils.game.enums.Difficulty;
import com.github.stachelbeere1248.zombiesutils.game.enums.Map;
import org.jetbrains.annotations.NotNull;

public class GameMode {
    private final Map map;
    private Difficulty difficulty;

    public GameMode(@NotNull Map map) {
        this.map = map;
        this.difficulty = Difficulty.NORMAL;
    }

    public GameMode(@NotNull Map map, @NotNull Difficulty difficulty) {
        this.map = map;
        this.difficulty = difficulty;
    }

    public Map getMap() {
        return map;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void changeDifficulty(@NotNull Difficulty difficulty) {
        switch (map) {
            case DEAD_END:
            case BAD_BLOOD:
            case PRISON:
                this.difficulty = difficulty;
                break;
            case ALIEN_ARCADIUM:
                throw new RuntimeException("Achievement Get: Alien Arcadium Hard/RIP" + Map.ALIEN_ARCADIUM);
        }
    }

    public boolean is(Map map, Difficulty difficulty) {
        return this.getDifficulty() == difficulty && this.getMap() == map;
    }
}
