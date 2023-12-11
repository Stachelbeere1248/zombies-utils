package com.github.stachelbeere1248.zombiesutils.commands;

import net.minecraftforge.client.ClientCommandHandler;

public class CommandRegistry {
    public static void registerAll() {
        ClientCommandHandler.instance.registerCommand(new CategoryCommand());
        ClientCommandHandler.instance.registerCommand(new SlaCommand());
        ClientCommandHandler.instance.registerCommand(new ZombiesUtilsCommand());
        ClientCommandHandler.instance.registerCommand(new QuickZombiesCommand());
    }
}
