package com.github.stachelbeere1248.zombiesutils.handlers;

import com.github.stachelbeere1248.zombiesutils.game.waves.WaveTiming;
import com.github.stachelbeere1248.zombiesutils.utils.Scoreboard;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.jetbrains.annotations.NotNull;

public class TickHandler {
    @SubscribeEvent
    public void onTick(TickEvent.@NotNull ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) return;
        Scoreboard.refresh();
        WaveTiming.onTick();
    }
}
