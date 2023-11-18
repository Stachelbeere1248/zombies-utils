package com.github.stachelbeere1248.zombiesutils.config;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
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
    public KeyBinding getChatMacro() {
        return chatMacro;
    }
}
