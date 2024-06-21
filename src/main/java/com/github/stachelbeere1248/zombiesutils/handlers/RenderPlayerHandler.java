package com.github.stachelbeere1248.zombiesutils.handlers;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class RenderPlayerHandler {
    @SubscribeEvent
    public void onRender(RenderPlayerEvent.@NotNull Pre event) {
        if (inRange(event.entityPlayer.getPositionVector())) {
            event.setCanceled(!ZombiesUtils.getInstance().getConfig().getPlayerVis());
        }
    }

    private boolean inRange(Vec3 playerOther) {
        return playerOther.squareDistanceTo(Minecraft.getMinecraft().thePlayer.getPositionVector()) <= 16;
    }
}
