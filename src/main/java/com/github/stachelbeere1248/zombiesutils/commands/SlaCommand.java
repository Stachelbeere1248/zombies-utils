package com.github.stachelbeere1248.zombiesutils.commands;

import com.github.stachelbeere1248.zombiesutils.game.Map;
import com.github.stachelbeere1248.zombiesutils.game.windows.Sla;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlaCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "sla";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/sla toggle\n/sla offset [x] [x] [x]\n/sla set <de|bb|aa>";
    }

    @Override
    public void processCommand(ICommandSender sender, String @NotNull [] args) throws CommandException {
        if (args.length == 0) sender.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
        else {
            switch (args[0]) {
                case "toggle":
                    Sla.toggle();
                    sender.addChatMessage(new ChatComponentText("SLA active: " + Sla.isEnabled()));
                    break;
                case "offset":
                    if (args.length == 1) Sla.getInstance().ifPresent(Sla::resetOffset);
                    else if (args.length != 4) sender.addChatMessage(new ChatComponentText("/sla offset [x] [x] [x]"));
                    else {
                        try {
                            double x = Double.parseDouble(args[1]);
                            double y = Double.parseDouble(args[2]);
                            double z = Double.parseDouble(args[3]);
                            Sla.getInstance().ifPresent(sla -> sla.setOffset(new double[]{x, y, z}));
                            sender.addChatMessage(new ChatComponentText(String.format("Offset set to %s %s %s", x, y, z)));
                        } catch (NumberFormatException ignored) {
                            sender.addChatMessage(new ChatComponentText("Please input valid numbers"));
                        }
                    }
                    break;
                case "set":
                    switch (args[1]) {
                        case "de":
                            new Sla(Map.DEAD_END);
                            break;
                        case "bb":
                            new Sla(Map.BAD_BLOOD);
                            break;
                        case "aa":
                            new Sla(Map.ALIEN_ARCADIUM);
                            break;
                        default:
                            sender.addChatMessage(new ChatComponentText("/sla set <de|bb|aa>"));
                    }
                    break;
                default:
                    sender.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
                    break;
            }
        }
    }
    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String @NotNull [] args, BlockPos blockPos) {
        List<String> options = new ArrayList<String>();
        if (args.length == 1) options.addAll(Arrays.asList("toggle","offset","set"));
        else if ("offset".equals(args[0]) && (args.length == 2 || args.length == 3 || args.length == 4))
            options.add("0");
        else if ("set".equals(args[0])) options.addAll(Arrays.asList("de", "bb", "aa"));
        return options;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
