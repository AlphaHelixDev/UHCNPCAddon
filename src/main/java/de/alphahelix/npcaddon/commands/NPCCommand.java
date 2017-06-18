package de.alphahelix.npcaddon.commands;

import de.alphahelix.alphalibary.command.CommandWatcher;
import de.alphahelix.alphalibary.command.SimpleCommand;
import de.alphahelix.alphalibary.command.arguments.IntArgument;
import de.alphahelix.alphalibary.command.arguments.StringArgument;
import de.alphahelix.alphalibary.fakeapi.utils.PlayerFakeUtil;
import de.alphahelix.alphalibary.utils.MessageUtil;
import de.alphahelix.alphalibary.uuid.UUIDFetcher;
import de.alphahelix.npcaddon.NPCAddon;
import de.alphahelix.npcaddon.util.NPCUtil;
import de.alphahelix.uhcremastered.UHC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class NPCCommand extends SimpleCommand {
    public NPCCommand() {
        super("npc", "Spawns in a new NPC");
    }

    @Override
    public boolean execute(CommandSender cs, String s, String[] args) {
        CommandWatcher set = new CommandWatcher(args);
        CommandWatcher createRA = new CommandWatcher(args);
        CommandWatcher removeRA = new CommandWatcher(args);

        StringArgument first = new StringArgument();
        IntArgument rank = new IntArgument();

        set.addArguments(first);
        createRA.addArguments(first, rank);
        removeRA.addArguments(first, rank);

        if (!(cs instanceof Player)) return true;

        Player p = (Player) cs;

        if (set.isSame()) {
            if (first.fromArgument().equalsIgnoreCase("setStats")) {
                PlayerFakeUtil.spawnPlayer(p, p.getLocation(), UUIDFetcher.getUUID(p), NPCAddon.getNpcOptions().getStatsName());
                cs.sendMessage(UHC.getGameOptions().getChatPrefix() + "You've set the position of a NPC to show the stats at your location!");
                return true;
            } else if (first.fromArgument().equalsIgnoreCase("setReward")) {
                PlayerFakeUtil.spawnPlayer(p, p.getLocation(), NPCAddon.getNpcOptions().getRewardsSkin(), NPCAddon.getNpcOptions().getRewardsName());
                cs.sendMessage(UHC.getGameOptions().getChatPrefix() + "You've set the position of a NPC to give rewards!");
                return true;
            }
        } else if(createRA.isSame()) {
            if(first.fromArgument().equalsIgnoreCase("createRankingNPC")) {
                NPCUtil.spawnRankingNPC(p.getLocation(), rank.fromArgument(), p);
                cs.sendMessage(UHC.getGameOptions().getChatPrefix() + "You've created the NPC for place " + rank.getEnteredArgument());
            }
        } else if(removeRA.isSame()) {
            if(first.fromArgument().equalsIgnoreCase("removeRankingNPC")) {
                NPCUtil.removeRankingNPC(rank.fromArgument(), p);
                p.sendMessage(UHC.getGameOptions().getChatPrefix() + "You've successfully removed the NPC for place : " + rank.getEnteredArgument());
            }
        }

        MessageUtil.sendCenteredMessage(p, "§3" + new String(new char[53]).replace("\0", "="));
        cs.sendMessage(" ");
        MessageUtil.sendCenteredMessage(p, "§7/npc setStats §3- §7Spawns the stats npc");
        MessageUtil.sendCenteredMessage(p, "§7/npc setReward §3- §7Spawns the rewards npc");
        MessageUtil.sendCenteredMessage(p, "§7/npc createRankingNPC §8<§7rank§8> §3- §7Spawns an npc representing the rank");
        MessageUtil.sendCenteredMessage(p, "§7/npc removeRankingNPC §8<§7rank§8> §3- §7Removes the npc for the rank");
        cs.sendMessage(" ");
        MessageUtil.sendCenteredMessage(p, "§3" + new String(new char[53]).replace("\0", "="));
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender commandSender, String s, String[] strings) {
        return null;
    }
}
