package com.github.stachelbeere1248.zombiesutils.handlers;

import com.github.stachelbeere1248.zombiesutils.timer.Timer;
import com.github.stachelbeere1248.zombiesutils.utils.Scoreboard;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class Round1Correction {

    private final Timer timer;
    private final String serverNumber;

    public Round1Correction(Timer timer, String serverNumber) {
        this.timer = timer;
        this.serverNumber = serverNumber;
    }

    @SubscribeEvent
    public void onWaveSpawn(@NotNull EntityJoinWorldEvent event) {
        if (!(event.entity instanceof EntityZombie)) return;
        if (Scoreboard.isNotZombies()) return;
        final Optional<String> s = Scoreboard.getServerNumber();
        if (!s.isPresent()) return;
        if (!s.get().equals(serverNumber)) {
            MinecraftForge.EVENT_BUS.unregister(this);
            return;
        }
        this.timer.correctStartTick();
        MinecraftForge.EVENT_BUS.unregister(this);
    }
}
