package com.github.stachelbeere1248.zombiesutils.handlers;

import com.github.stachelbeere1248.zombiesutils.utils.Scoreboard;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TickHandler {
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) return;
        Scoreboard.refresh();
    }
}
