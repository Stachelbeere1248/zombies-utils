package com.github.stachelbeere1248.zombiesutils.handlers;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class RenderPlayerHandler {
    private boolean visible;

    public RenderPlayerHandler() {
        this.visible = ZombiesUtils.getInstance().getConfig().getPlayerVis();
    }

    @SubscribeEvent
    public void onRender(RenderPlayerEvent.@NotNull Pre event) {
        if (event.entityPlayer.isPlayerSleeping() || event.entityPlayer.isUser()) return;
        if (inRange(event.entityPlayer.getPositionVector())) {
            event.setCanceled(!visible);
        }
    }

    private boolean inRange(@NotNull Vec3 playerOther) {
        final int range = ZombiesUtils.getInstance().getConfig().getPlayerVisRange();
        return playerOther.squareDistanceTo(Minecraft.getMinecraft().thePlayer.getPositionVector()) <= range * range;
    }
    public void togglePlayerVisibility() {
        this.visible = !this.visible;
        final String s;
        if (this.visible) {
            s = "§dPlayer Visibility §e is now §rON";
        } else {
            s = "§dPlayer Visibility §e is now §8OFF";
        }
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                s
        ));
    }
}
