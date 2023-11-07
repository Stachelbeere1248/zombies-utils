package com.github.stachelbeere1248.zombiesutils.commands;

import com.github.stachelbeere1248.zombiesutils.game.Map;
import com.github.stachelbeere1248.zombiesutils.game.windows.Sla;
import net.minecraft.client.Minecraft;
import net.minecraft.command.*;
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
        return "/sla off\n/sla offset [x] [x] [x]\n/sla rotate\n/sla mirror\n/sla map <de|bb|aa>";
    }

    @Override
    public void processCommand(ICommandSender sender, String @NotNull [] args) throws CommandException {
        if (args.length == 0) sender.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
        else {
            switch (args[0]) {
                case "off":
                    Sla.drop();
                    sender.addChatMessage(new ChatComponentText("SLA data deleted"));
                    break;
                case "offset":
                    if (args.length == 1) Sla.getInstance().ifPresent(Sla::resetOffset);
                    else if (args.length != 4) throw new WrongUsageException("An offset should have three coordinates!");
                    else {
                        try {
                            double x = Double.parseDouble(args[1]);
                            double y = Double.parseDouble(args[2]);
                            double z = Double.parseDouble(args[3]);
                            Sla.getInstance().ifPresent(sla -> sla.setOffset(new double[]{x, y, z}));
                            sender.addChatMessage(new ChatComponentText(String.format("Offset set to %s %s %s", x, y, z)));
                        } catch (NumberFormatException ignored) {
                            throw new NumberInvalidException();
                        }
                    }
                    break;
                case "rotate":
                    sender.addChatMessage(new ChatComponentText("Rotating map..."));
                    Sla.getInstance().ifPresent(Sla::rotate);
                    break;
                case "mirror":
                    switch (args[1]) {
                        case "x":
                            Sla.getInstance().ifPresent(Sla::mirrorX);
                            break;
                        case "z":
                            Sla.getInstance().ifPresent(Sla::mirrorZ);
                            break;
                        default: throw new WrongUsageException("Invalid option: available: x, z");
                    }
                    break;
                case "map":
                    switch (args[1]) {
                        case "de":
                            Sla.instance = new Sla(Map.DEAD_END);
                            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("SLA forced to map DE"));
                            break;
                        case "bb":
                            Sla.instance = new Sla(Map.BAD_BLOOD);
                            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("SLA forced to map BB"));
                            break;
                        case "aa":
                            Sla.instance = new Sla(Map.ALIEN_ARCADIUM);
                            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("SLA forced to map AA"));
                            break;
                        default: throw new WrongUsageException("Invalid option: available: de, bb, aa");

                    }
                    break;
                default: throw new WrongUsageException("Invalid option: available: off, offset, rotate, mirror, map");
            }
        }
    }
    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String @NotNull [] args, BlockPos blockPos) {
        List<String> options = new ArrayList<>();
        if (args.length == 1) options.addAll(Arrays.asList("off","offset","rotate","mirror","map"));
        else if ("offset".equals(args[0]) && (args.length == 2 || args.length == 3 || args.length == 4))
            options.add("0");
        else if ("map".equals(args[0])) options.addAll(Arrays.asList("de", "bb", "aa"));
        else if ("mirror".equals(args[0])) {
            options.addAll(Arrays.asList("x","z"));
        }
        return options;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
