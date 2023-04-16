package com.mradhit.lorevoter.listener;

import com.mradhit.lorevoter.manager.VoteCacheManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent event) {
        new VoteCacheManager(event.getPlayer().getName()).execute();
    }
}
