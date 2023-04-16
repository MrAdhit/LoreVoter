package com.mradhit.lorevoter;

import com.mradhit.lorevoter.file.VoterCacheFile;
import com.mradhit.lorevoter.listener.PlayerJoinListener;
import com.mradhit.lorevoter.listener.VotePartyListener;
import com.mradhit.lorevoter.listener.VotifierListener;
import com.mradhit.lorevoter.manager.VotePartyManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class LoreVoter extends JavaPlugin {
    public static LoreVoter plugin;
    public static Logger logger;

    @Override
    public void onEnable() {
        plugin = this;
        logger = this.getLogger();

        logger.info("Loading Plugin");

        VoterCacheFile.getInstance();
        VotePartyManager.getInstance();

        plugin.getServer().getPluginManager().registerEvents(new VotifierListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new VotePartyListener(), plugin);

        logger.info("Plugin loaded!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
