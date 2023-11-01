package com.github.stachelbeere1248.zombiesutils.render;

import com.github.stachelbeere1248.zombiesutils.timer.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

public class TimeRenderer {
    private final FontRenderer fontRenderer;

    public TimeRenderer() {
        this.fontRenderer = Objects.requireNonNull(Minecraft.getMinecraft().fontRendererObj, "FontRenderer must not be null!");
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.ALL) return;
        if (!Timer.getInstance().isPresent()) return;

        long timerTicks = Timer.getInstance().get().roundTime();
        long minutesPart = (timerTicks*50) / 60000;
        long secondsPart = ((timerTicks*50) % 60000) / 1000;
        long tenthSecondsPart = ((timerTicks*50) % 1000) / 100;
        String time = String.format("%d:%02d.%d", minutesPart, secondsPart, tenthSecondsPart);
        int width = fontRenderer.getStringWidth(time);
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int screenWidth = scaledResolution.getScaledWidth();
        int screenHeight = scaledResolution.getScaledHeight();
        int color = 0xFFFFFF;
        fontRenderer.drawStringWithShadow(time, screenWidth - width, screenHeight - fontRenderer.FONT_HEIGHT, color);
    }
}
