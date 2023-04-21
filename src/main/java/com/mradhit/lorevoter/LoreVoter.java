package com.mradhit.lorevoter;

import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mradhit.lorevoter.command.LoreVoteCommand;
import com.mradhit.lorevoter.command.VoteCommand;
import com.mradhit.lorevoter.file.LoreVoterConfigFile;
import com.mradhit.lorevoter.file.VoterCacheFile;
import com.mradhit.lorevoter.listener.PlayerJoinListener;
import com.mradhit.lorevoter.listener.VotePartyListener;
import com.mradhit.lorevoter.listener.VotifierListener;
import com.mradhit.lorevoter.manager.VotePartyManager;
import com.mradhit.lorevoter.placeholder.LoreVoterExpansion;
import me.clip.placeholderapi.PlaceholderAPI;
import me.lucko.commodore.Commodore;
import me.lucko.commodore.CommodoreProvider;
import me.lucko.commodore.file.CommodoreFileReader;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Logger;

public final class LoreVoter extends JavaPlugin {
    public static LoreVoter plugin;
    public static Logger logger;

    @Override
    public void onEnable() {
        plugin = this;
        logger = this.getLogger();

        logger.info("Loading Plugin");

        LoreVoterConfigFile.getInstance();
        VoterCacheFile.getInstance();
        VotePartyManager.getInstance();

        plugin.getServer().getPluginManager().registerEvents(new VotifierListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new VotePartyListener(), plugin);

        new LoreVoterExpansion().register();

        PluginCommand voteCommand = plugin.getCommand("vote");
        voteCommand.setExecutor(new VoteCommand());

        PluginCommand lorevoteCommand = plugin.getCommand("lorevote");
        lorevoteCommand.setExecutor(new LoreVoteCommand());

        Commodore commodore = CommodoreProvider.getCommodore(this);

        try {
            LiteralCommandNode<?> lorevoteCompletion = CommodoreFileReader.INSTANCE.parse(plugin.getResource("lorevote.commodore"));
            commodore.register(lorevoteCompletion);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(PlaceholderAPI.setPlaceholders(player, LoreVoterConfigFile.getInstance().getConfig().vote.broadcast.message));
            }
        }, 0, LoreVoterConfigFile.getInstance().getConfig().vote.broadcast.interval);

        logger.info("Plugin loaded!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
