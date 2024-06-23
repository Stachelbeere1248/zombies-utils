package com.github.stachelbeere1248.zombiesutils.handlers;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import com.github.stachelbeere1248.zombiesutils.config.ZombiesUtilsConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class RenderPlayerHandler {
    private boolean visible = ZombiesUtils.getInstance().getConfig().getPlayerVis();
    @SubscribeEvent
    public void onRender(RenderPlayerEvent.@NotNull Pre event) {
        if (event.entityPlayer.isPlayerSleeping() || event.entityPlayer.isUser()) return;
        if (inRange(event.entityPlayer.getPositionVector())) {
            event.setCanceled(!visible);
        }
    }

    private boolean inRange(@NotNull Vec3 playerOther) {
        return playerOther.squareDistanceTo(Minecraft.getMinecraft().thePlayer.getPositionVector()) <= 16;
    }
    public void togglePlayerVisibility() {
        this.visible = !this.visible;
    }
}
