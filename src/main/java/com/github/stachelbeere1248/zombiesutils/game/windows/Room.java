package com.github.stachelbeere1248.zombiesutils.game.windows;

import com.github.stachelbeere1248.zombiesutils.config.ZombiesUtilsConfig;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Room {
    private final Window[] windows;
    private final String name;
    private final String alias;
    private int activeWindowCount;

    public Room(String name, String alias, Window[] windows) {
        this.windows = windows;
        this.name = name;
        this.alias = alias;
    }

    @Contract(" -> new")
    public static Room @NotNull [] getDE() {
        Vec3 t = new Vec3(3, 3, 3);
        t.rotatePitch(3);
        return new Room[]{
                new Room("Alley", "al", new Window[]{
                        new Window(1, 9, 138, 87),
                        new Window(2, 13, 138, 63),
                        new Window(3, 85, 140, 59),
                        new Window(4, 79, 140, 17)
                }),
                new Room("Office", "o", new Window[]{
                        new Window(1, 85, 152, 53),
                        new Window(2, 105, 152, 63),
                        new Window(3, 115, 152, 129)
                }),
                new Room("Hotel", "h", new Window[]{
                        new Window(1, 1, 136, 93),
                        new Window(2, -19, 136, 29),
                        new Window(3, 53, 138, 7),
                        new Window(4, 51, 138, -7),
                        new Window(5, -7, 152, -43),
                        new Window(6, 51, 152, -11)
                }),
                new Room("Apartments", "a", new Window[]{
                        new Window(1, 39, 152, 19),
                        new Window(2, -31, 152, 31),
                        new Window(3, -27, 152, 103),
                        new Window(4, -9, 152, 125)
                }),
                new Room("Power Station", "ps", new Window[]{
                        new Window(1, -5, 166, 65),
                        new Window(2, 7, 166, 125),
                        new Window(3, -11, 136, 133)
                }),
                new Room("Rooftop", "rt", new Window[]{
                        new Window(1, -31, 166, 129),
                        new Window(2, -27, 166, 61),
                        new Window(3, -75, 166, 51),
                        new Window(4, -99, 166, 77)
                }),
                new Room("Garden", "g", new Window[]{
                        new Window(1, 1, 136, -33),
                        new Window(2, 49, 136, -67),
                        new Window(3, 69, 136, -33)
                }),
                new Room("Gallery", "gal", new Window[]{
                        new Window(1, 45, 152, 155),
                        new Window(2, 61, 152, 109),
                        new Window(3, 31, 152, 131)
                })
        };
    }

    @Contract(" -> new")
    public static Room @NotNull [] getBB() {
        return new Room[]{
                new Room("Courtyard", "co", new Window[]{
                        new Window(1, 39, 138, 41),
                        new Window(2, 61, 138, 21),
                        new Window(3, 49, 138, -37),
                        new Window(4, 25, 138, -35)
                }),
                new Room("Mansion", "m", new Window[]{
                        new Window(1, 1, 148, -35),
                        new Window(2, 1, 148, 37),
                        new Window(3, -25, 146, 57)
                }),
                new Room("Library", "l", new Window[]{
                        new Window(1, 3, 148, -89),
                        new Window(2, -41, 148, -59),
                        new Window(3, -81, 148, -61),
                        new Window(4, -79, 148, -115),
                        new Window(5, -109, 148, -93),
                        new Window(6, -107, 148, -67)
                }),
                new Room("Dungeon", "d", new Window[]{
                        new Window(1, -21, 136, -99),
                        new Window(2, -57, 136, -69),
                        new Window(3, -19, 136, -45),
                        new Window(4, -19, 136, -37),
                        new Window(5, -73, 136, -23)
                }),
                new Room("Crypts", "cr", new Window[]{
                        new Window(1, -7, 136, -5),
                        new Window(2, -31, 136, 1),
                        new Window(3, -57, 136, 41)
                }),
                new Room("Graveyard", "gy", new Window[]{
                        new Window(1, -71, 136, 63),
                        new Window(2, -33, 136, 101),
                        new Window(3, -13, 136, 67)
                }),
                new Room("Balcony", "b", new Window[]{
                        new Window(1, -65, 148, -37),
                        new Window(2, -113, 148, 5),
                        new Window(3, -107, 144, 25),
                        new Window(4, -83, 136, 55)
                }),
                new Room("Great Hall", "gh", new Window[]{
                        new Window(1, -39, 148, -27),
                        new Window(2, -55, 148, 31),
                        new Window(3, -63, 152, 31)
                })

        };
    }

    @Contract(" -> new")
    public static Room @NotNull [] getAA() {
        return new Room[]{
                new Room("Park Entrance", "ent", new Window[]{
                        new Window(1, 13, 144, 63),
                        new Window(2, -45, 144, 31),
                        new Window(3, -43, 144, 21),
                        new Window(4, -21, 144, -11),
                        new Window(5, 45, 144, 27)
                }),
                new Room("Roller Coaster", "rc", new Window[]{
                        new Window(1, -25, 144, 79),
                        new Window(2, -57, 144, 55)
                }),
                new Room("Ferris Wheel", "fw", new Window[]{
                        new Window(1, 55, 144, 63),
                        new Window(2, 35, 144, 89)
                }),
                new Room("Bumper Cars", "bp", new Window[]{
                        new Window(1, 45, 146, -27),
                        new Window(2, 67, 146, -3)
                })
        };
    }

    public String getName() {
        return name;
    }

    public Window[] getWindows() {
        return windows;
    }

    public void increaseActiveWindowCount() {
        this.activeWindowCount++;
    }

    public void resetActiveWindowCount() {
        this.activeWindowCount = 0;
    }

    public int getActiveWindowCount() {
        return activeWindowCount;
    }

    public String getSlaString() {
        StringBuilder slaString = new StringBuilder(String.format("§6%s§r§d %x§e:", this.getName(), activeWindowCount));
        for (Window window : this.windows) {
            if (window.isActive()) slaString.append("§2 ").append(alias).append(window.getID());
            else if (!ZombiesUtilsConfig.isSlaShortened()) slaString.append("§c ").append(alias).append(window.getID());
        }
        return slaString.toString();
    }
}
