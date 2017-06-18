package de.alphahelix.npcaddon.util;

import de.alphahelix.alphalibary.fakeapi.FakeAPI;
import de.alphahelix.alphalibary.fakeapi.instances.FakeArmorstand;
import de.alphahelix.alphalibary.fakeapi.instances.FakePlayer;
import de.alphahelix.alphalibary.fakeapi.instances.NoSuchFakeEntityException;
import de.alphahelix.alphalibary.fakeapi.utils.ArmorstandFakeUtil;
import de.alphahelix.alphalibary.fakeapi.utils.PlayerFakeUtil;
import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.alphalibary.item.LeatherItemBuilder;
import de.alphahelix.alphalibary.nms.REnumEquipSlot;
import de.alphahelix.alphalibary.uuid.UUIDFetcher;
import de.alphahelix.npcaddon.NPCAddon;
import de.alphahelix.uhcremastered.UHC;
import de.alphahelix.uhcremastered.instances.PlayerStatistic;
import de.alphahelix.uhcremastered.utils.StatsUtil;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NPCUtil {
    public static void createStatsArmorStands(Player p) {
        try {
            Location loc = FakeAPI.getFakePlayerByName(p, NPCAddon.getNpcOptions().getStatsName()).getCurrentlocation();
            PlayerStatistic stats = StatsUtil.getStatistics(p);

            FakeArmorstand games = ArmorstandFakeUtil.spawnTemporaryArmorstand(
                    p,
                    loc.clone(),
                    UHC.getMessages().getStats().getGames(stats.getGames()));

            FakeArmorstand kills = ArmorstandFakeUtil.spawnTemporaryArmorstand(
                    p,
                    loc.clone(),
                    UHC.getMessages().getStats().getKills(stats.getKills()));

            FakeArmorstand deaths = ArmorstandFakeUtil.spawnTemporaryArmorstand(
                    p,
                    loc.clone(),
                    UHC.getMessages().getStats().getDeaths(stats.getDeaths()));

            FakeArmorstand killrate = ArmorstandFakeUtil.spawnTemporaryArmorstand(
                    p,
                    loc.clone(),
                    UHC.getMessages().getStats().getKillDeathRate(StatsUtil.getKillDeathRate(stats)));

            FakeArmorstand wins = ArmorstandFakeUtil.spawnTemporaryArmorstand(
                    p,
                    loc.clone(),
                    UHC.getMessages().getStats().getWins(stats.getWins()));

            FakeArmorstand coins = ArmorstandFakeUtil.spawnTemporaryArmorstand(
                    p,
                    loc.clone(),
                    UHC.getMessages().getStats().getCoins(stats.getCoins()));

            FakeArmorstand points = ArmorstandFakeUtil.spawnTemporaryArmorstand(
                    p,
                    loc.clone(),
                    UHC.getMessages().getStats().getPoints(stats.getPoints()));

            ArmorstandFakeUtil.moveArmorstand(p, 0, 0.3, 0, points);
            ArmorstandFakeUtil.moveArmorstand(p, 0, 0.5, 0, coins);
            ArmorstandFakeUtil.moveArmorstand(p, 0, 0.7, 0, wins);
            ArmorstandFakeUtil.moveArmorstand(p, 0, 0.9, 0, killrate);
            ArmorstandFakeUtil.moveArmorstand(p, 0, 1.1, 0, deaths);
            ArmorstandFakeUtil.moveArmorstand(p, 0, 1.3, 0, kills);
            ArmorstandFakeUtil.moveArmorstand(p, 0, 1.5, 0, games);
        } catch (NoSuchFakeEntityException e) {
            e.printStackTrace();
        }
    }

    public static void spawnRankingNPC(Location l, int rank, Player p) {
        String name = StatsUtil.getPlayernameByRank(rank);

        if(name == null) return;
        if(name.isEmpty() || name.equals("") || name.equals(" ")) return;

        FakePlayer ranked = PlayerFakeUtil.spawnTemporaryPlayer(p, l, UUIDFetcher.getUUID(name), "§7" + Integer.toString(rank) + ".");
        ArmorstandFakeUtil.spawnTemporaryArmorstand(p, l.clone().add(0, 0.2, 0), "§7" + name);

        ItemStack chest, pants, boots;
        if (rank == 1) {
            chest = new ItemBuilder(Material.GOLD_CHESTPLATE).build();
            pants = new ItemBuilder(Material.GOLD_LEGGINGS).build();
            boots = new ItemBuilder(Material.GOLD_BOOTS).build();
        } else if (rank == 2) {
            chest = new ItemBuilder(Material.IRON_CHESTPLATE).build();
            pants = new ItemBuilder(Material.IRON_LEGGINGS).build();
            boots = new ItemBuilder(Material.IRON_BOOTS).build();
        } else if (rank == 3) {
            chest = new ItemBuilder(Material.LEATHER_CHESTPLATE).build();
            pants = new ItemBuilder(Material.LEATHER_LEGGINGS).build();
            boots = new ItemBuilder(Material.LEATHER_BOOTS).build();
        } else {
            chest = new LeatherItemBuilder(Material.LEATHER_CHESTPLATE).setColor(Color.WHITE).build();
            pants = new LeatherItemBuilder(Material.LEATHER_LEGGINGS).setColor(Color.WHITE).build();
            boots = new LeatherItemBuilder(Material.LEATHER_BOOTS).setColor(Color.WHITE).build();
        }

        PlayerFakeUtil.equipPlayer(p, ranked, chest, REnumEquipSlot.CHESTPLATE);
        PlayerFakeUtil.equipPlayer(p, ranked, pants, REnumEquipSlot.LEGGINGS);
        PlayerFakeUtil.equipPlayer(p, ranked, boots, REnumEquipSlot.BOOTS);

        NPCAddon.getLocationFile().addRankingNPC(l, rank);
    }

    public static void removeRankingNPC(int rank, Player p) {
        try {
            PlayerFakeUtil.removePlayer(p, FakeAPI.getFakePlayerByName(p, rank + "."));
            ArmorstandFakeUtil.destroyArmorstand(p, FakeAPI.getFakeArmorstandByName(p, StatsUtil.getPlayernameByRank(rank)));
        } catch (NoSuchFakeEntityException e) {
            e.printStackTrace();
        }
    }
}
