package com.github.stachelbeere1248.zombiesutils.game.windows;

import com.github.stachelbeere1248.zombiesutils.game.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;

public class Sla {
    private static Sla instance = null;
    private static boolean enabled = false;
    private final double[] offset = new double[3];
    private final Room[] rooms;


    public Sla(@NotNull Map map) {
        switch (map) {
            case DEAD_END: this.rooms = Room.getDE(); break;
            case BAD_BLOOD: this.rooms = Room.getBB(); break;
            case ALIEN_ARCADIUM: this.rooms = Room.getAA(); break;
            default: throw new IllegalStateException("Unexpected value: " + map);
        }
        instance = this;
    }

    public void refreshActives() {
        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        final double[] playerCoords = {
                player.posX + offset[0],
                player.posY + offset[1],
                player.posZ + offset[2]
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

                // (2x)² + (2y)² + (2z)² = 4 (x²+y²+z²)
                if (distanceDoubledThenSquared < 10000) {
                    window.setActive(true);
                    room.increaseActiveWindowCount();
                } else window.setActive(false);
            }
        }
    }


    public static Optional<Sla> getInstance() {
        return Optional.ofNullable(instance);
    }
    public static void drop() {
        instance = null;
    }
    public Room[] getRooms() {
        return rooms;
    }
    public static boolean isEnabled() {
        return enabled;
    }
    public static void toggle() {
        Sla.enabled = !Sla.enabled;
    }
    public void resetOffset() {
        Arrays.fill(this.offset, 0);
    }
    public void setOffset(double[] offset) {
        System.arraycopy(offset, 0, this.offset, 0, 3);
    }
}
