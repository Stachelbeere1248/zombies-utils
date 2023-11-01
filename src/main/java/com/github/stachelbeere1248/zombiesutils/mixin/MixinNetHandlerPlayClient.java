package com.github.stachelbeere1248.zombiesutils.mixin;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import com.github.stachelbeere1248.zombiesutils.timer.Timer;
import com.github.stachelbeere1248.zombiesutils.utils.Scoreboard;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S29PacketSoundEffect;
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
    @Unique
    private void zombies_utils$handleSound(@NotNull S29PacketSoundEffect packet) {
        String soundEffect = packet.getSoundName();
        if (!(
                soundEffect.equals("mob.wither.spawn")
                || soundEffect.equals("mob.enderdragon.end")
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
            //TODO: GAME END
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
}
