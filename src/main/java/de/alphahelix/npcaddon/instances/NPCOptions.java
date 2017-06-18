package de.alphahelix.npcaddon.instances;

import java.util.UUID;

public class NPCOptions {

    private String statsName, rewardsName;
    private UUID rewardsSkin;

    public NPCOptions(String statsName, String rewardsName, UUID rewardsSkin) {
        this.statsName = statsName;
        this.rewardsName = rewardsName;
        this.rewardsSkin = rewardsSkin;
    }

    public String getStatsName() {
        return statsName;
    }

    public String getRewardsName() {
        return rewardsName;
    }

    public UUID getRewardsSkin() {
        return rewardsSkin;
    }
}
