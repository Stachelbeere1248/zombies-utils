package com.github.stachelbeere1248.zombiesutils.mixin;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import com.github.stachelbeere1248.zombiesutils.game.GameMode;
import com.github.stachelbeere1248.zombiesutils.timer.Timer;
import com.github.stachelbeere1248.zombiesutils.utils.Scoreboard;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S45PacketTitle;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {
    public MixinNetHandlerPlayClient() {}
    @Unique
    private boolean zombies_utils$alienUfoOpened;
    @Inject(method = "handleSoundEffect", at = @At(value = "HEAD"))
    private void handleSound(S29PacketSoundEffect packetIn, CallbackInfo ci) {
        zombies_utils$handleSound(packetIn);
    }
    @Inject(method = "handleTitle", at = @At(value = "HEAD"))
    private void handleTitle(S45PacketTitle packetIn, CallbackInfo ci) {
        zombies_utils$handleTitle(packetIn);
    }
    @Unique
    private void zombies_utils$handleSound(@NotNull S29PacketSoundEffect packet) {
        if (!Scoreboard.isZombies()) return;
        String soundEffect = packet.getSoundName();
        if (!(
                soundEffect.equals("mob.wither.spawn")
                || (soundEffect.equals("mob.guardian.curse") && !zombies_utils$alienUfoOpened)
        )) return;
        zombies_utils$alienUfoOpened = soundEffect.equals("mob.guardian.curse");

        if (!Timer.getInstance().isPresent()) {
            ZombiesUtils.getInstance().getLogger().info("Attempting creation of new timer");
            new Timer(
                    Scoreboard.getServerNumber().orElseThrow(() -> new RuntimeException("cannot figure out servernumber")),
                    Scoreboard.getMap().orElseThrow(() -> new RuntimeException("cannot figure out map"))
            );
        } else {
            Timer timer = Timer.getInstance().get();
            if (timer.equalsServerOrNull(Scoreboard.getServerNumber().orElse(null))) timer.split(Scoreboard.getRound());

            else {
                ZombiesUtils.getInstance().getLogger().info("Attempting creation of new timer");
                //also kills the previous timer using the garbage collector
                new Timer(
                        Scoreboard.getServerNumber().orElseThrow(() -> new RuntimeException("cannot figure out servernumber")),
                        Scoreboard.getMap().orElseThrow(() -> new RuntimeException("cannot figure out map"))
                );
            }
        }
    }
    @Unique
    private void zombies_utils$handleTitle(S45PacketTitle packet) {
        if (packet.getType() != S45PacketTitle.Type.TITLE) return;
        if (!Scoreboard.isZombies()) return;
        final String message = packet.getMessage().getUnformattedText().trim();
        if (message.equals("\u00a7aYou Win!")) {
            switch (GameMode.getCurrentGameMode().getMap()) {
                case DEAD_END: case BAD_BLOOD:
                    Timer.getInstance().ifPresent(timer -> {
                        timer.split((byte) 30);
                        Timer.dropInstances();
                    });
                    break;
                case ALIEN_ARCADIUM:
                    Timer.getInstance().ifPresent(timer -> {
                        timer.split((byte) 105);
                        Timer.dropInstances();
                    });
                    break;
            }
        } else if (message.equals("\u00a7cGame Over!")) {
            Timer.dropInstances();
        } else {
            ZombiesUtils.getInstance().getLogger().debug(message);
        }

    }
}
