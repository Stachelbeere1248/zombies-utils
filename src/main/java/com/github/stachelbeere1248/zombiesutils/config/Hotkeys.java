package com.github.stachelbeere1248.zombiesutils.config;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class Hotkeys {
    private final KeyBinding chatMacro;
    private final KeyBinding rlSpawn;
    private final KeyBinding playerVisiblity;

    public Hotkeys() {
        chatMacro = new KeyBinding(
                "Chat Macro",
                Keyboard.KEY_Q,
                "Zombies Utils"
        );
        rlSpawn = new KeyBinding(
                "Rocket Launcher Mode",
                Keyboard.KEY_UP,
                "Zombies Utils"
        );
        playerVisiblity = new KeyBinding(
                "Player Visibility",
                Keyboard.KEY_RIGHT,
                "Zombies Utils"
        );

    }

    public void registerAll() {
        ClientRegistry.registerKeyBinding(this.chatMacro);
        ClientRegistry.registerKeyBinding(this.rlSpawn);
        ClientRegistry.registerKeyBinding(this.playerVisiblity);
    }

    public KeyBinding getChatMacro() {
        return chatMacro;
    }

    public KeyBinding getRlSpawn() {
        return rlSpawn;
    }
    public KeyBinding getPlayerVisiblity() {
        return playerVisiblity;
    }
}
