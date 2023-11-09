package com.github.stachelbeere1248.zombiesutils.render;

import com.github.stachelbeere1248.zombiesutils.config.ZombiesUtilsConfig;
import com.github.stachelbeere1248.zombiesutils.game.sla.SLA;
import com.github.stachelbeere1248.zombiesutils.game.windows.Room;
import com.github.stachelbeere1248.zombiesutils.timer.Timer;
import com.github.stachelbeere1248.zombiesutils.utils.Scoreboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RenderGameOverlayHandler {
    private final FontRenderer fontRenderer;

    public RenderGameOverlayHandler() {
        this.fontRenderer = Objects.requireNonNull(Minecraft.getMinecraft().fontRendererObj, "FontRenderer must not be null!");
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.@NotNull Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;
        Timer.getInstance().ifPresent(timer -> renderTime(timer.roundTime()));
        SLA.getInstance().ifPresent(sla -> {
            sla.refreshActives();
            renderSla(sla.getRooms());
        });

    }

    private void renderTime(long timerTicks) {
        if (Scoreboard.isZombies()) return;
        long minutesPart = (timerTicks*50) / 60000;
        long secondsPart = ((timerTicks*50) % 60000) / 1000;
        long tenthSecondsPart = ((timerTicks*50) % 1000) / 100;
        String time = String.format("%d:%02d.%d", minutesPart, secondsPart, tenthSecondsPart);
        int width = fontRenderer.getStringWidth(time);
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int screenWidth = scaledResolution.getScaledWidth();
        int screenHeight = scaledResolution.getScaledHeight();
        final int color = 0xFFFFFF;
        fontRenderer.drawStringWithShadow(time, screenWidth - width, screenHeight - fontRenderer.FONT_HEIGHT, color);
    }
    private void renderSla(Room @NotNull [] rooms) {
        int y = 0;
        for (Room room: rooms) {
            if (ZombiesUtilsConfig.isSlaShortened() && room.getActiveWindowCount() == 0) continue;
            fontRenderer.drawStringWithShadow(
                    room.getSlaString(),
                    1,
                    1 + y * fontRenderer.FONT_HEIGHT,
                    0xFFFFFF
            );
            y++;
        }
    }
}
