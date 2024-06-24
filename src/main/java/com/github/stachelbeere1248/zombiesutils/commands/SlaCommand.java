package com.github.stachelbeere1248.zombiesutils.commands;

import com.github.stachelbeere1248.zombiesutils.game.SLA;
import com.github.stachelbeere1248.zombiesutils.game.enums.Map;
import com.github.stachelbeere1248.zombiesutils.game.sla.QuickSLA;
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
        return "/sla off\n/sla offset [x] [x] [x]\n/sla rotate\n/sla mirror\n/sla map <de|bb|aa|p>\n/sla quick";
    }

    @Override
    public void processCommand(ICommandSender sender, String @NotNull [] args) throws CommandException {
        if (args.length == 0) throw new WrongUsageException(
                "[Missing option] options: off, offset, rotate, mirror, map, quick");
        else switch (args[0]) {
            case "off":
                SLA.drop();
                sender.addChatMessage(new ChatComponentText("SLA data deleted"));
                break;
            case "offset":
                if (args.length == 1) SLA.getInstance().ifPresent(SLA::resetOffset);
                else if (args.length != 4) throw new WrongUsageException(
                        "An offset should have three coordinates!");
                else {
                    try {
                        int x = Integer.parseInt(args[1]);
                        int y = Integer.parseInt(args[2]);
                        int z = Integer.parseInt(args[3]);
                        SLA.getInstance().ifPresent(sla -> sla.setOffset(new int[]{x, y, z}));
                    } catch (NumberFormatException ignored) {
                        throw new NumberInvalidException("Invalid Integer:", args[1]);
                    }
                }
                break;
            case "rotate":
                if (args.length == 1) SLA.getInstance().ifPresent(sla -> sla.rotate(1));
                else {
                    int rotations;
                    try {
                        rotations = Integer.parseInt(args[1]);
                    } catch (NumberFormatException ignored) {
                        throw new NumberInvalidException("Invalid Integer:", args[1]);
                    }
                    SLA.getInstance().ifPresent(sla -> sla.rotate(rotations));
                }
                break;
            case "mirror":
                switch (args[1]) {
                    case "x":
                        SLA.getInstance().ifPresent(SLA::mirrorX);
                        break;
                    case "z":
                        SLA.getInstance().ifPresent(SLA::mirrorZ);
                        break;
                    default:
                        throw new WrongUsageException("Invalid option: available: x, z");
                }
                break;
            case "map":
                switch (args[1]) {
                    case "de":
                        SLA.instance = new SLA(Map.DEAD_END);
                        break;
                    case "bb":
                        SLA.instance = new SLA(Map.BAD_BLOOD);
                        break;
                    case "aa":
                        SLA.instance = new SLA(Map.ALIEN_ARCADIUM);
                        break;
                    case "p":
                        SLA.instance = new SLA(Map.PRISON);
                        break;
                    default:
                        throw new WrongUsageException(
                                "[Invalid option] options: de, bb, aa, p", args[1]);
                }
                break;
            case "quick":
                switch (args[1]) {
                    //noinspection SpellCheckingInspection
                    case "mogi_a":
                        QuickSLA.mogi_a();
                        break;
                    //noinspection SpellCheckingInspection
                    case "ghxula":
                        QuickSLA.ghxula();
                        break;
                    //noinspection SpellCheckingInspection
                    case "ghxula-garden":
                        QuickSLA.ghxulaGarden();
                        break;
                    default:
                        //noinspection SpellCheckingInspection
                        throw new WrongUsageException(
                                "[Invalid option] options: mogi_a, ghxula, ghxula-garden", args[1]);
                }
                break;
            default:
                throw new WrongUsageException(
                        "[Invalid option] options: off, offset, rotate, mirror, map", args[0]);
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String @NotNull [] args, BlockPos blockPos) {
        List<String> options = new ArrayList<>();
        if (args.length == 1) options.addAll(Arrays.asList("off", "offset", "rotate", "mirror", "map", "quick"));
        else {
            if (args.length >= 2) switch (args[0]) {
                case "offset":
                    if (args.length < 5) options.add("0");
                    break;
                case "map":
                    options.addAll(Arrays.asList("de", "bb", "aa", "p"));
                    break;
                case "mirror":
                    options.addAll(Arrays.asList("x", "z"));
                    break;
                case "rotate":
                    options.add("1");
                    break;
                case "quick":
                    //noinspection SpellCheckingInspection
                    options.addAll(Arrays.asList("mogi_a", "ghxula", "ghxula-garden"));
                default:
                    break;
            }
        }
        return options;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
