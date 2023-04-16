package com.mradhit.lorevoter.manager;

import com.mradhit.lorevoter.LoreVoter;

public class VoteRewardManager {
    private final String username;

    public VoteRewardManager(String username) {
        this.username = username;
    }

    public void execute() {
        LoreVoter.logger.info("Gifting vote rewards to " + this.username);
        LoreVoter.plugin.getServer().dispatchCommand(LoreVoter.plugin.getServer().getConsoleSender(), "give " + username + " wooden_sword");
    }
}
