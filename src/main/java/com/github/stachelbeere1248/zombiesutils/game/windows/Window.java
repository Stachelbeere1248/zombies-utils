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

    public void setActive(boolean active) {
        isActive = active;
    }
    public boolean isActive() {
        return isActive;
    }
    public void rotate() {
        final short x = xyz[0], z = xyz[2];
        xyz[0] = (short) -z;
        xyz[2] = x;
    }
    public void mirrorX() {
        xyz[0] = (short) -xyz[0];
    }
    public void mirrorZ() {
        xyz[2] = (short) -xyz[2];
    }
}
