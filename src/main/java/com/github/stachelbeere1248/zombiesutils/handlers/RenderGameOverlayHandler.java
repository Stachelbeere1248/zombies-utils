package com.github.stachelbeere1248.zombiesutils.handlers;

import com.github.stachelbeere1248.zombiesutils.config.ZombiesUtilsConfig;
import com.github.stachelbeere1248.zombiesutils.game.SLA;
import com.github.stachelbeere1248.zombiesutils.game.waves.Waves;
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
    private int rl = 0;
    private final boolean[] clicks = new boolean[20];
    private int clickPointer = 0;
    private final FontRenderer fontRenderer;

    public RenderGameOverlayHandler() {
        this.fontRenderer = Objects.requireNonNull(Minecraft.getMinecraft().fontRendererObj, "FontRenderer must not be null!");
    }

    private static String getTimeString(long timerTicks) {
        final long minutesPart = (timerTicks * 50) / 60000;
        final long secondsPart = ((timerTicks * 50) % 60000) / 1000;
        final long tenthSecondsPart = ((timerTicks * 50) % 1000) / 100;
        return String.format("%d:%02d.%d", minutesPart, secondsPart, tenthSecondsPart);
    }

    private static String getWaveString(long waveTicks, int wave) {
        final long minutesPart = (waveTicks * 50) / 60000;
        final long secondsPart = ((waveTicks * 50) % 60000) / 1000;
        final long tenthSecondsPart = ((waveTicks * 50) % 1000) / 100;
        return String.format("W%d %d:%02d.%d", wave, minutesPart, secondsPart, tenthSecondsPart);
    }

    void toggleRL() {
        if (rl == 0) rl = ZombiesUtilsConfig.getWaveOffset();
        else rl = 0;
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.@NotNull Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;

        Timer.getInstance().ifPresent(timer -> {
            renderTime(timer.roundTime());
            renderSpawnTime(
                    Waves.get(
                            timer.getGameMode().getMap(),
                            timer.getRound()
                    ),
                    timer.roundTime()
            );
        });

        SLA.getInstance().ifPresent(sla -> {
            sla.refreshActives();
            renderSla(sla.getRooms());
        });

        if (ZombiesUtilsConfig.getCpsToggle()) renderCPS();
    }

    private void renderTime(long timerTicks) {
        if (Scoreboard.isNotZombies()) return;

        final String time = getTimeString(timerTicks);
        final int width = fontRenderer.getStringWidth(time);

        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        final int screenWidth = scaledResolution.getScaledWidth();
        final int screenHeight = scaledResolution.getScaledHeight();

        fontRenderer.drawStringWithShadow(
                time,
                screenWidth - width,
                screenHeight - fontRenderer.FONT_HEIGHT,
                0xFFFFFF
        );
    }

    private void renderSla(Room @NotNull [] rooms) {
        int y = 0;
        for (Room room : rooms) {
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

    private void renderSpawnTime(byte @NotNull [] waveTimes, short roundTicks) {
        if (Scoreboard.isNotZombies() || !ZombiesUtilsConfig.getSST()) return;

        final int length = waveTimes.length + 1;
        int heightIndex = 0;
        int color = 0xFFFF55;

        for (byte waveTime : waveTimes) {
            int clonedColor = color;
            final short waveTicks = (short) ((waveTime * 20) + rl);

            if (roundTicks > waveTicks) {
                if (ZombiesUtilsConfig.isSpawntimeNotShortened()) clonedColor = 0x555555;
                else {
                    heightIndex++;
                    continue;
                }
            }

            final String time = getWaveString(waveTicks, heightIndex + 1);
            final int width = fontRenderer.getStringWidth(time);
            final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
            final int screenWidth = scaledResolution.getScaledWidth();
            final int screenHeight = scaledResolution.getScaledHeight();

            fontRenderer.drawStringWithShadow(
                    time,
                    screenWidth - width,
                    screenHeight - fontRenderer.FONT_HEIGHT * (length - heightIndex),
                    clonedColor
            );
            if (clonedColor != 0x555555) color = 0xAAAAAA;
            heightIndex++;
        }
    }
    public void renderCPS() {
        final String cps = String.format("%2d", getClicks());
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        final int screenWidth = scaledResolution.getScaledWidth();
        final int width = fontRenderer.getStringWidth(cps);

        fontRenderer.drawStringWithShadow(
                cps,
                screenWidth - width,
                0,
                0xAAAAAA
        );
    }

    public int getClicks() {
        int i = 0;
        for (boolean tick : clicks) {
            if (tick) i++;
        }
        return i;
    }

    public void addClick() {
        clicks[clickPointer] = true;
    }

    public void tick() {
        clickPointer = (clickPointer + 1) % 20;
        clicks[clickPointer] = false;
    }
}
