package com.github.stachelbeere1248.zombiesutils.mixin;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import com.github.stachelbeere1248.zombiesutils.timer.Timer;
import com.github.stachelbeere1248.zombiesutils.utils.Scoreboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.util.ChatComponentText;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {
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
        if (Scoreboard.isZombies()) return;
        final String soundEffect = packet.getSoundName();
        if (!(
                soundEffect.equals("mob.wither.spawn")
                || (soundEffect.equals("mob.guardian.curse") && !zombies_utils$alienUfoOpened)
        )) return;
        zombies_utils$alienUfoOpened = soundEffect.equals("mob.guardian.curse");
        /*if (!Timer.getInstance().isPresent()) {
            new Timer(
                    Scoreboard.getServerNumber().orElseThrow(() -> new RuntimeException("cannot figure out servernumber")),
                    Scoreboard.getMap().orElseThrow(() -> new RuntimeException("cannot figure out map")).map
            );
        }  else { Timer timer = Timer.getInstance().get();

            final Scoreboard.MapContainer map = Scoreboard.getMap().orElse(new Scoreboard.MapContainer(
                    timer.getGameMode().getMap(),
                    false
            ));

            if (map.pregame) {
                new Timer(
                        Scoreboard.getServerNumber().orElseThrow(() -> new RuntimeException("cannot figure out servernumber")),
                        map.map
                );
            } else if (timer.equalsServerOrNull(Scoreboard.getServerNumber().orElse(null))) timer.split(Scoreboard.getRound());
            else {
                new Timer(
                        Scoreboard.getServerNumber().orElseThrow(() -> new RuntimeException("cannot figure out servernumber")),
                        map.map
                );
            }
        }*/


        try {

            if (Timer.getInstance().isPresent()) {
                final Timer running = Timer.getInstance().get();
                final byte round = Scoreboard.getRound();

                if (round == 0) {
                    if (Scoreboard.getLineCount()<13) Timer.setInstance(new Timer(
                            Scoreboard.getServerNumber().orElseThrow(Timer.TimerException.ServerNumberException::new),
                            Scoreboard.getMap().orElseThrow(Timer.TimerException.MapException::new),
                            round
                    ));
                } else if (!running.equalsServerOrNull(Scoreboard.getServerNumber().orElse(null))) {
                    Timer.setInstance(new Timer(
                            Scoreboard.getServerNumber().orElseThrow(Timer.TimerException.ServerNumberException::new),
                            Scoreboard.getMap().orElseThrow(Timer.TimerException.MapException::new),
                            round
                    ));
                } else running.split(round);



            } else Timer.setInstance(new Timer(
                    Scoreboard.getServerNumber().orElseThrow(Timer.TimerException.ServerNumberException::new),
                    Scoreboard.getMap().orElseThrow(Timer.TimerException.MapException::new),
                    Scoreboard.getRound()
            ));
        } catch (Timer.TimerException e) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§cFailed to start or split timer.\nData parsing error. Blame scoreboard."));
            ZombiesUtils.getInstance().getLogger().warn(e);
        }
    }
    @Unique
    private void zombies_utils$handleTitle(@NotNull S45PacketTitle packet) {
        if (packet.getType() != S45PacketTitle.Type.TITLE) return;
        final String message = packet.getMessage().getUnformattedText().trim();

        Timer.getInstance().ifPresent(timer -> {
            if (Scoreboard.isZombies()) return;

            if (message.equals("§aYou Win!")) {
                switch (timer.getGameMode().getMap()) {
                    case DEAD_END: case BAD_BLOOD:
                        timer.split((byte) 30);
                        Timer.dropInstances();
                        break;
                    case ALIEN_ARCADIUM:
                        timer.split((byte) 105);
                        Timer.dropInstances();
                        break;
                }
            } else if (message.equals("§cGame Over!")) Timer.dropInstances();
        });
    }

}
