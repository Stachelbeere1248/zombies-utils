package com.github.stachelbeere1248.zombiesutils.handlers;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import com.github.stachelbeere1248.zombiesutils.config.Hotkeys;
import com.github.stachelbeere1248.zombiesutils.game.waves.WaveTiming;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;


public class KeyInputHandler {
    @SubscribeEvent
    public void onKeyInput(InputEvent event) {
        if (Minecraft.getMinecraft().currentScreen != null) return;
        if (event instanceof InputEvent.KeyInputEvent) {
            if (Keyboard.getEventKey() == '\0') return;
            if (Keyboard.getEventKeyState()) {
                final Hotkeys hotkeys = ZombiesUtils.getInstance().getHotkeys();
                if (Keyboard.getEventKey() == hotkeys.getChatMacro().getKeyCode()) Minecraft.getMinecraft().thePlayer
                        .sendChatMessage(ZombiesUtils.getInstance().getConfig().getChatMacro());
                else if (Keyboard.getEventKey() == hotkeys.getRlSpawn().getKeyCode()) {
                    ZombiesUtils.getInstance().getHandlers().getRenderer().toggleRL();
                    WaveTiming.toggleRL();
                } else if (Keyboard.getEventKey() == hotkeys.getPlayerVisiblity().getKeyCode()) {
                    ZombiesUtils.getInstance().getHandlers().getRenderPlayerHandler().togglePlayerVisibility();
                }
            }
        } else if (event instanceof InputEvent.MouseInputEvent) {
            if (Mouse.getEventButtonState()) {
                if (Mouse.getEventButton() == 1) ZombiesUtils.getInstance().getHandlers().getRenderer().addClick();
            }
        }
    }

}
