package com.mradhit.lorevoter.listener;

import com.mradhit.lorevoter.event.VotePartyEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VotePartyListener implements Listener {
    @EventHandler
    public void OnVoteParty(VotePartyEvent event) {
        System.out.println(event.voteAmount);
    }
}
