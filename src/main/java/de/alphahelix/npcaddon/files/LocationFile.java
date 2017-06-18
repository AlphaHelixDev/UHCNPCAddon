package de.alphahelix.npcaddon.files;

import de.alphahelix.alphalibary.file.SimpleJSONFile;
import de.alphahelix.npcaddon.NPCAddon;
import org.bukkit.Location;

public class LocationFile extends SimpleJSONFile {
    public LocationFile() {
        super(NPCAddon.getInstance().getDataFolder().getAbsolutePath(), "locations.json");
    }

    public void addRankingNPC(Location loc, int rank) {
        addValuesToList("NPCS", new RankingNPC(loc, rank));
    }

    public RankingNPC[] getRankingNPC() {
        if (jsonContains("NPCS"))
            return getListValues("NPCS", RankingNPC[].class);
        return new RankingNPC[]{};
    }

    public static class RankingNPC {

        private Location loc;
        private int rank;

        RankingNPC(Location loc, int rank) {
            this.loc = loc;
            this.rank = rank;
        }

        public Location getLoc() {
            return loc;
        }

        public int getRank() {
            return rank;
        }
    }
}
