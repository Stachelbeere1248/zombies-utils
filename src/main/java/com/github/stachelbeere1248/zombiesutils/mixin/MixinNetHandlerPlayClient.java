package com.github.stachelbeere1248.zombiesutils.mixin;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import com.github.stachelbeere1248.zombiesutils.game.enums.Map;
import com.github.stachelbeere1248.zombiesutils.timer.Timer;
import com.github.stachelbeere1248.zombiesutils.utils.LanguageSupport;
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
        if (Scoreboard.isNotZombies()) return;
        final String soundEffect = packet.getSoundName();
        if (!(
                soundEffect.equals("mob.wither.spawn")
                        || (soundEffect.equals("mob.guardian.curse") && !zombies_utils$alienUfoOpened)
        )) return;
        zombies_utils$alienUfoOpened = soundEffect.equals("mob.guardian.curse");
        try {

            if (!Timer.getInstance().isPresent()) {
                Timer.instance = new Timer(
                        Scoreboard.getServerNumber().orElseThrow(Timer.TimerException.ServerNumberException::new),
                        Map.getMap().orElseThrow(Timer.TimerException.MapException::new),
                        Scoreboard.getRound()
                );
                return;
            }

            final Timer running = Timer.getInstance().get();
            final byte round = Scoreboard.getRound();

            if (round == 0) {
                if (Scoreboard.getLineCount() < 13) Timer.instance = new Timer(
                        Scoreboard.getServerNumber().orElseThrow(Timer.TimerException.ServerNumberException::new),
                        Map.getMap().orElseThrow(Timer.TimerException.MapException::new),
                        round
                );
                return;
            }

            if (!running.equalsServerOrNull(Scoreboard.getServerNumber().orElse(null))) {
                Timer.instance = new Timer(
                        Scoreboard.getServerNumber().orElseThrow(Timer.TimerException.ServerNumberException::new),
                        Map.getMap().orElseThrow(Timer.TimerException.MapException::new),
                        round
                );
                return;
            }

            running.split(round);

        } catch (Timer.TimerException e) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§cFailed to start or split timer. Please send a log to Stachelbeere1248."));
            ZombiesUtils.getInstance().getLogger().warn(e);
        }
    }

    @Unique
    private void zombies_utils$handleTitle(@NotNull S45PacketTitle packet) {
        if (packet.getType() != S45PacketTitle.Type.TITLE) return;
        final String message = packet.getMessage().getUnformattedText().trim();

        Timer.getInstance().ifPresent(timer -> {
            if (Scoreboard.isNotZombies()) return;

            if (LanguageSupport.isWin(message)) {
                switch (timer.getGameMode().getMap()) {
                    case DEAD_END:
                    case BAD_BLOOD:
                    case PRISON: //TODO: Escape
                        timer.split((byte) 30);
                        Timer.dropInstances();
                        break;
                    case ALIEN_ARCADIUM:
                        timer.split((byte) 105);
                        Timer.dropInstances();
                        break;
                }
            } else if (LanguageSupport.isLoss(message)) Timer.dropInstances();
        });
    }

}
