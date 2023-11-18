package com.github.stachelbeere1248.zombiesutils.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class Hotkeys {
    private final KeyBinding chatMacro;
    private final KeyBinding debug;

    public Hotkeys() {
        chatMacro = new KeyBinding(
                "Chat Macro",
                Keyboard.KEY_Q,
                "Zombies Utils"
        );
        debug = new KeyBinding(
                "Debug",
                Keyboard.KEY_K,
                "Zombies Utils"
        );
    }
    public void registerAll() {
        ClientRegistry.registerKeyBinding(this.chatMacro);
        ClientRegistry.registerKeyBinding(this.debug);
    }
    public KeyBinding getChatMacro() {
        return chatMacro;
    }
    public KeyBinding getDebugger() {
        return debug;
    }
}
