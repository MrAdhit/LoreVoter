package com.mradhit.lorevoter.listener;

import com.mradhit.lorevoter.event.VotePartyEvent;
import com.mradhit.lorevoter.file.LoreVoterConfigFile;
import com.mradhit.lorevoter.manager.VotePartyManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VotePartyListener implements Listener {
    private static final LoreVoterConfigFile config = LoreVoterConfigFile.getInstance();
    private static final VotePartyManager votePartyManager = VotePartyManager.getInstance();

    @EventHandler
    public void OnVoteParty(VotePartyEvent event) {
        if (event.voteAmount >= config.getConfig().vote.party.goal) {
            votePartyManager.execute();
            votePartyManager.reset();
        }
    }
}
