package com.github.stachelbeere1248.zombiesutils.mixin;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import com.github.stachelbeere1248.zombiesutils.utils.InvalidMapException;
import com.github.stachelbeere1248.zombiesutils.utils.LanguageSupport;
import com.github.stachelbeere1248.zombiesutils.utils.ScoardboardException;
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
                || (soundEffect.equals("mob.guardian.curse")
                && !zombies_utils$alienUfoOpened)
        )) return;

        zombies_utils$alienUfoOpened = soundEffect.equals("mob.guardian.curse");

        try {
            ZombiesUtils.getInstance().getGameManager().splitOrNew(Scoreboard.getRound());
        } catch (ScoardboardException | InvalidMapException e) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§cFailed to start or split timer. Please send a log to Stachelbeere1248."));
            ZombiesUtils.getInstance().getLogger().error(e.fillInStackTrace());
        }
    }

    @Unique
    private void zombies_utils$handleTitle(@NotNull S45PacketTitle packet) {
        if (packet.getType() != S45PacketTitle.Type.TITLE) return;
        if (Scoreboard.isNotZombies()) return;
        final String message = packet.getMessage().getUnformattedText().trim();
        String serverNumber;
            serverNumber = Scoreboard.getServerNumber().orElse("");
        if (LanguageSupport.isWin(message)) ZombiesUtils.getInstance().getGameManager().endGame(serverNumber,true);
        if (LanguageSupport.isLoss(message)) ZombiesUtils.getInstance().getGameManager().endGame(serverNumber, false);
    }

}
