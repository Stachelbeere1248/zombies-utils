package com.github.stachelbeere1248.zombiesutils.game.sla;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import com.github.stachelbeere1248.zombiesutils.game.Map;
import com.github.stachelbeere1248.zombiesutils.game.windows.Room;
import com.github.stachelbeere1248.zombiesutils.game.windows.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;

public class SLA {
    public static SLA instance = null;
    private final int[] offset = new int[3];
    private final Room[] rooms;


    public SLA(@NotNull Map map) {
        switch (map) {
            case DEAD_END: this.rooms = Room.getDE(); break;
            case BAD_BLOOD: this.rooms = Room.getBB(); break;
            case ALIEN_ARCADIUM: this.rooms = Room.getAA(); break;
            default: throw new IllegalStateException("Unexpected value: " + map);
        }
    }

    public void rotate(int rotations) {
        for (Room room : rooms) {
            for (Window window: room.getWindows()) {
                window.rotate(rotations);
            }
        }
    }
    public void mirrorX() {
        for (Room room : rooms) {
            for (Window window: room.getWindows()) {
                window.mirrorX();
            }
        }
        System.out.println("Co3 now at " + Arrays.toString(rooms[0].getWindows()[0].getXYZ()));
    }
    public void mirrorZ() {
        for (Room room : rooms) {
            for (Window window: room.getWindows()) {
                window.mirrorZ();
            }
        }
        short[] win0 = rooms[0].getWindows()[0].getXYZ();
        ZombiesUtils.getInstance().getLogger().info("Window \"0\" is now at %s %s %s" + (double) win0[0]/2 + (double) win0[1]/2 + (double) win0[2]/2);
    }
    public void refreshActives() {
        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        final double[] playerCoords = {
                (player.posX - offset[0]),
                player.posY - offset[1],
                player.posZ - offset[2]
        };
        for (Room room: rooms
             ) {
            room.resetActiveWindowCount();
            for (Window window: room.getWindows()
                 ) {
                double distanceDoubledThenSquared = 0;
                for (int i = 0; i < 3; i++) {
                    distanceDoubledThenSquared += ((playerCoords[i]*2 - window.getXYZ()[i]) * (playerCoords[i]*2 - window.getXYZ()[i]));
                }

                // (2x)²+(2y)²+(2z)² = 4(x²+y²+z²) = 4d²
                if (distanceDoubledThenSquared < 10000) {
                    window.setActive(true);
                    room.increaseActiveWindowCount();
                } else window.setActive(false);
            }
        }
    }


    public static Optional<SLA> getInstance() {
        return Optional.ofNullable(instance);
    }
    public static void drop() {
        instance = null;
    }
    public Room[] getRooms() {
        return rooms;
    }
    public void resetOffset() {
        Arrays.fill(this.offset, 0);
    }
    public void setOffset(int[] offset) {
        System.arraycopy(offset, 0, this.offset, 0, 3);
    }
}
