package com.mradhit.lorevoter.listener;

import com.mradhit.lorevoter.LoreVoter;
import com.mradhit.lorevoter.file.LoreVoterConfigFile;
import com.mradhit.lorevoter.manager.PlayerVoteManager;
import com.mradhit.lorevoter.manager.VoteCacheManager;
import com.mradhit.lorevoter.manager.VotePartyManager;
import com.mradhit.lorevoter.manager.VoteRewardManager;
import com.vexsoftware.votifier.model.VotifierEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static com.mradhit.lorevoter.LoreVoter.logger;
import static com.mradhit.lorevoter.LoreVoter.plugin;

public class VotifierListener implements Listener {
    @EventHandler
    public void OnVotifierEvent(VotifierEvent event) {
        String voter = event.getVote().getUsername();

        new PlayerVoteManager(voter).add();
        VotePartyManager.getInstance().add();

        String broadcastMessage = LoreVoterConfigFile.getInstance().getConfig().broadcast.message
                .replaceAll("\\{username}", voter).replaceAll("\\{service}", event.getVote().getServiceName()).replaceAll("&", "§").replaceAll("\\{and}", "&");

        plugin.getServer().broadcastMessage(PlaceholderAPI.setPlaceholders(Bukkit.getOfflinePlayer(voter), broadcastMessage));

        if (LoreVoter.plugin.getServer().getPlayer(voter) == null) {
            new VoteCacheManager(voter).cache();
            logger.info(voter + " is not in the server, saving");
            return;
        }

        new VoteRewardManager(voter).execute();
    }
}
