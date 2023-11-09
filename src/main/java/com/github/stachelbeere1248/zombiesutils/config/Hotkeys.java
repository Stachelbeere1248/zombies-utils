package com.github.stachelbeere1248.zombiesutils.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class Hotkeys {
    private final KeyBinding chatMacro;

    public Hotkeys() {
        chatMacro = new KeyBinding(
                "Chat Macro",
                Keyboard.KEY_Q,
                "Zombies Utils"
        );
    }
    public void registerAll() {
        ClientRegistry.registerKeyBinding(this.chatMacro);
    }
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKey() == chatMacro.getKeyCode() && Keyboard.getEventKeyState() && Minecraft.getMinecraft().currentScreen == null) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage(ZombiesUtilsConfig.getChatMacro());
        }
    }
}
