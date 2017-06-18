package de.alphahelix.npcaddon;

import de.alphahelix.alphalibary.utils.Util;
import de.alphahelix.npcaddon.commands.NPCCommand;
import de.alphahelix.npcaddon.files.LocationFile;
import de.alphahelix.npcaddon.files.NPCFile;
import de.alphahelix.npcaddon.instances.NPCOptions;
import de.alphahelix.npcaddon.util.RankingUtil;
import de.alphahelix.uhcremastered.UHC;
import de.alphahelix.uhcremastered.addons.core.Addon;
import de.alphahelix.uhcremastered.addons.csv.CrossSystemManager;
import de.alphahelix.uhcremastered.addons.csv.CrossSystemVariable;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class NPCAddon extends Addon {

    private static NPCAddon instance;
    private static LocationFile locationFile;

    private static NPCOptions npcOptions;

    public static NPCAddon getInstance() {
        return instance;
    }

    public static LocationFile getLocationFile() {
        return locationFile;
    }

    public static NPCOptions getNpcOptions() {
        return npcOptions;
    }

    public static void setNpcOptions(NPCOptions npcOptions) {
        NPCAddon.npcOptions = npcOptions;
    }

    @Override
    public void onEnable() {
        instance = this;

        locationFile = new LocationFile();
        new NPCFile();
        new NPCCommand();

        Util.runLater(40, true, () -> CrossSystemManager.addVar(new CrossSystemVariable() {
            @Override
            public String value() {
                return getNpcOptions().getRewardsName();
            }

            @Override
            public String name() {
                return "UHC:NPCAddon:RewardNPC";
            }
        }));

        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onJoin(PlayerJoinEvent e) {
                new BukkitRunnable() {
                    public void run() {
                        RankingUtil.updateNPCS(e.getPlayer());
                    }
                }.runTaskLater(UHC.getInstance(), 20);
            }
        }, UHC.getInstance());
    }
}
