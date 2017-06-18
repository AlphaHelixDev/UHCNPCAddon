package de.alphahelix.npcaddon.util;

import de.alphahelix.npcaddon.NPCAddon;
import de.alphahelix.npcaddon.files.LocationFile;
import org.bukkit.entity.Player;

public class RankingUtil {
    public static void updateNPCS(Player p) {
        for(LocationFile.RankingNPC ra : NPCAddon.getLocationFile().getRankingNPC())
            NPCUtil.spawnRankingNPC(ra.getLoc(), ra.getRank(), p);
    }
}
