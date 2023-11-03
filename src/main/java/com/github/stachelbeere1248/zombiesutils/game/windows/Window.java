package com.github.stachelbeere1248.zombiesutils.game.windows;

public class Window {
    private final short[] xyz = new short[3];
    private final int alias;
    private boolean isActive;

    public Window(int alias, int x, int y, int z) {
        this.alias = alias;
        xyz[0] = (short) x; xyz[1] = (short) y; xyz[2] = (short) z;
    }

    public int getAlias() {
        return alias;
    }
    public short[] getXYZ() {
        return xyz;
    }

    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }
}
