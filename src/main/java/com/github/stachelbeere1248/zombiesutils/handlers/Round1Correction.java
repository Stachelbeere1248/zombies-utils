package com.github.stachelbeere1248.zombiesutils.handlers;

import com.github.stachelbeere1248.zombiesutils.timer.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class Round1Correction {

    private final Timer TIMER;

    public Round1Correction(Timer timer) {
        TIMER = timer;
    }

    @SubscribeEvent
    public void onWaveSpawn(@NotNull EntityJoinWorldEvent event) {
        final Entity entity = event.entity;
        if (entity instanceof EntityZombie) {
            TIMER.correctStartTick();
            MinecraftForge.EVENT_BUS.unregister(this);
        }
    }
}
