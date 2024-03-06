package com.github.stachelbeere1248.zombiesutils.game.enums;

import org.jetbrains.annotations.NotNull;

public enum Map {
    DEAD_END, BAD_BLOOD, ALIEN_ARCADIUM, PRISON;

    @Override
    public @NotNull String toString() {
        switch (this) {
            case DEAD_END: return "Dead End";
            case BAD_BLOOD: return "Bad Blood";
            case ALIEN_ARCADIUM: return "Alien Arcadium";
            case PRISON: return "Prison";
            default: throw new IllegalStateException("Unexpected Map value:" + this);
        }
    }
}
