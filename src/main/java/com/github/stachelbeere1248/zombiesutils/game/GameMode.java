package com.github.stachelbeere1248.zombiesutils.game;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("DuplicatedCode")
public class GameMode {
    private static GameMode currentGameMode = null;
    public static final GameMode DEAD_END_NORMAL = new GameMode(Map.DEAD_END), DEAD_END_HARD = new GameMode(Map.DEAD_END, Difficulty.HARD), DEAD_END_RIP = new GameMode(Map.DEAD_END, Difficulty.RIP);
    public static final GameMode BAD_BLOOD_NORMAL = new GameMode(Map.BAD_BLOOD), BAD_BLOOD_HARD = new GameMode(Map.BAD_BLOOD, Difficulty.HARD), BAD_BLOOD_RIP = new GameMode(Map.BAD_BLOOD, Difficulty.RIP);
    public static final GameMode ALIEN_ARCADIUM = new GameMode(Map.ALIEN_ARCADIUM);
    private final Map map;
    private final Difficulty difficulty;

    private GameMode (@NotNull Map map, @NotNull Difficulty difficulty) {
        this.map = map;
        this.difficulty = difficulty;
    }
    private GameMode(@NotNull Map map) {
        this.map = map;
        this.difficulty = Difficulty.NORMAL;
    }
    public static void create(@NotNull Map map) {
        switch (map) {
            case DEAD_END: currentGameMode = DEAD_END_NORMAL; break;
            case BAD_BLOOD: currentGameMode =  BAD_BLOOD_NORMAL; break;
            case ALIEN_ARCADIUM: currentGameMode =  ALIEN_ARCADIUM; break;
        }
    }
    public Map getMap() {
        return map;
    }
    public Difficulty getDifficulty() {
        return difficulty;
    }
    public void changeDifficulty(@NotNull Difficulty difficulty) {
        switch (map) {
            case ALIEN_ARCADIUM:
                throw new RuntimeException("Achievement Get: Alien Arcadium Hard/RIP" + Map.ALIEN_ARCADIUM);
            case DEAD_END:
                switch (difficulty) {
                    case NORMAL: currentGameMode = DEAD_END_NORMAL; break;
                    case HARD: currentGameMode = DEAD_END_HARD; break;
                    case RIP: currentGameMode = DEAD_END_RIP; break;
                }
                break;
            case BAD_BLOOD:
                switch (difficulty) {
                    case NORMAL: currentGameMode = BAD_BLOOD_NORMAL; break;
                    case HARD: currentGameMode = BAD_BLOOD_HARD; break;
                    case RIP: currentGameMode = BAD_BLOOD_RIP; break;
                } break;
        }
    }
    public static GameMode getCurrentGameMode() {
        return currentGameMode;
    }

    /**
     * Call to invalidate {@link #currentGameMode} to trigger the garbage collector
     */
    public static void drop() {
        currentGameMode = null;
    }

    public boolean equals(@NotNull GameMode gameMode) {
        return (this.getDifficulty() == gameMode.getDifficulty() && this.getMap() == gameMode.getMap());
    }
}
