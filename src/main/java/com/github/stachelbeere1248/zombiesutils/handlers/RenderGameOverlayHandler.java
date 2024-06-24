package com.github.stachelbeere1248.zombiesutils.handlers;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import com.github.stachelbeere1248.zombiesutils.game.SLA;
import com.github.stachelbeere1248.zombiesutils.game.waves.Waves;
import com.github.stachelbeere1248.zombiesutils.game.windows.Room;
import com.github.stachelbeere1248.zombiesutils.utils.Scoreboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RenderGameOverlayHandler {
    private final byte[] clicks = new byte[20];
    private final FontRenderer fontRenderer;
    private int rl = 0;
    private int clickPointer = 0;

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
        if (rl == 0) rl = ZombiesUtils.getInstance().getConfig().getOffset();
        else rl = 0;
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.@NotNull Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;

        ZombiesUtils.getInstance().getGameManager().getGame().ifPresent(
                game -> {
                    renderTime(game.getTimer().getRoundTime());
                    renderSpawnTime(
                            Waves.get(
                                    game.getGameMode().getMap(),
                                    game.getRound()
                            ),
                            game.getTimer().getRoundTime()
                    );
                }
        );

        if (!Minecraft.getMinecraft().gameSettings.showDebugInfo) SLA.getInstance().ifPresent(sla -> renderSla(sla.getRooms()));
        if (ZombiesUtils.getInstance().getConfig().getCpsToggle()) renderCPS();
    }

    private void renderTime(short timerTicks) {
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
            if (ZombiesUtils.getInstance().getConfig().isSlaShortened() && room.getActiveWindowCount() == 0) continue;
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
        if (Scoreboard.isNotZombies() || !ZombiesUtils.getInstance().getConfig().getSST()) return;

        final int length = waveTimes.length + 1;
        int heightIndex = 0;
        int color = 0xFFFF55;

        for (byte waveTime : waveTimes) {
            int clonedColor = color;
            final short waveTicks = (short) ((waveTime * 20) + rl);

            if (roundTicks > waveTicks) {
                if (!ZombiesUtils.getInstance().getConfig().isSpawntimeShortened()) clonedColor = 0x555555;
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
        for (byte tick : clicks) {
            i += tick;
        }
        return i;
    }

    public void addClick() {
        clicks[clickPointer]++;
    }

    public void tick() {
        clickPointer = (clickPointer + 1) % 20;
        clicks[clickPointer] = 0;
    }
}
