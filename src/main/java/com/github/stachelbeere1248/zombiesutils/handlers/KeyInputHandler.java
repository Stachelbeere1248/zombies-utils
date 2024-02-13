package com.github.stachelbeere1248.zombiesutils.handlers;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import com.github.stachelbeere1248.zombiesutils.config.Hotkeys;
import com.github.stachelbeere1248.zombiesutils.config.ZombiesUtilsConfig;
import com.github.stachelbeere1248.zombiesutils.game.waves.WaveTiming;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;


public class KeyInputHandler {
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState() && Minecraft.getMinecraft().currentScreen == null) {
            Hotkeys hotkeys = ZombiesUtils.getInstance().getHotkeys();
            if (Keyboard.getEventKey() == hotkeys.getChatMacro().getKeyCode()) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage(
                        ZombiesUtilsConfig.getChatMacro()
                );
            } else if (Keyboard.getEventKey() == hotkeys.getRlSpawn().getKeyCode()) {
                RenderGameOverlayHandler.toggleRL();
                WaveTiming.toggleRL();
            }
        }
    }
}
