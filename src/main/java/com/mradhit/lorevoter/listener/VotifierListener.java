package com.mradhit.lorevoter.listener;

import com.mradhit.lorevoter.LoreVoter;
import com.mradhit.lorevoter.file.LoreVoterConfigFile;
import com.mradhit.lorevoter.manager.VoteCacheManager;
import com.mradhit.lorevoter.manager.VotePartyManager;
import com.mradhit.lorevoter.manager.VoteRewardManager;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static com.mradhit.lorevoter.LoreVoter.logger;
import static com.mradhit.lorevoter.LoreVoter.plugin;

public class VotifierListener implements Listener {
    @EventHandler
    public void OnVotifierEvent(VotifierEvent event) {
        String voter = event.getVote().getUsername();

        VotePartyManager.getInstance().add();

        String broadcastMessage = LoreVoterConfigFile.getInstance().getConfig().broadcast.message
                .replaceAll("\\{username}", voter).replaceAll("\\{service}", event.getVote().getServiceName()).replaceAll("&", "§");

        plugin.getServer().broadcastMessage(broadcastMessage);

        if (LoreVoter.plugin.getServer().getPlayer(voter) == null) {
            new VoteCacheManager(voter).cache();
            logger.info(voter + " is not in the server, saving");
            return;
        }

        new VoteRewardManager(voter).execute();
    }
}
