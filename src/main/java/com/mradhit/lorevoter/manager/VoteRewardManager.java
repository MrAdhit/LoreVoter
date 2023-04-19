package com.mradhit.lorevoter.manager;

import com.mradhit.lorevoter.LoreVoter;
import com.mradhit.lorevoter.file.LoreVoterConfigFile;

import java.util.List;

public class VoteRewardManager {
    private static final LoreVoterConfigFile config = LoreVoterConfigFile.getInstance();

    private final String username;

    public VoteRewardManager(String username) {
        this.username = username;
    }

    public void execute() {
        LoreVoter.logger.info("Gifting vote rewards to " + this.username);

        List<String> rewards = config.getConfig().vote.player.rewards;
        for (String reward : rewards) {
            String processor = reward.replaceAll("\\{username}", this.username);
            LoreVoter.plugin.getServer().dispatchCommand(LoreVoter.plugin.getServer().getConsoleSender(), processor);
        }
    }
}
