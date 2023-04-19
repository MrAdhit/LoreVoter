package com.mradhit.lorevoter.manager;

import com.mradhit.lorevoter.LoreVoter;
import com.mradhit.lorevoter.event.VotePartyEvent;
import com.mradhit.lorevoter.file.LoreVoterConfigFile;
import com.mradhit.lorevoter.file.VotePartyCacheFile;
import org.bukkit.Bukkit;

import java.util.List;

public class VotePartyManager {
    private static VotePartyManager INSTANCE;

    private final LoreVoterConfigFile config = LoreVoterConfigFile.getInstance();
    private final VotePartyCacheFile cacheFile = VotePartyCacheFile.getInstance();

    public static VotePartyManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new VotePartyManager();
        }

        return INSTANCE;
    }

    private VotePartyManager() { }

    public int get() {
        return this.cacheFile.cache;
    }

    public void add() {
        this.cacheFile.cache += 1;
        Bukkit.getPluginManager().callEvent(new VotePartyEvent(this.cacheFile.cache));
        this.cacheFile.write();
    }

    public void sub() {
        this.cacheFile.cache -= 1;
        Bukkit.getPluginManager().callEvent(new VotePartyEvent(this.cacheFile.cache));
        this.cacheFile.write();
    }

    public void reset() {
        this.cacheFile.cache = 0;
        Bukkit.getPluginManager().callEvent(new VotePartyEvent(this.cacheFile.cache));
        this.cacheFile.write();
    }

    public void set(int amount) {
        this.cacheFile.cache = amount;
        Bukkit.getPluginManager().callEvent(new VotePartyEvent(this.cacheFile.cache));
        this.cacheFile.write();
    }

    public void setMax(int amount) {
        this.config.getConfig().vote.party.goal = amount;
        this.config.saveConfig();
    }

    public int getMax() {
        return this.config.getConfig().vote.party.goal;
    }

    public void execute() {
        LoreVoter.logger.info("VoteParty Rewards Executed!");

        List<String> rewards = this.config.getConfig().vote.party.rewards;
        for (String reward : rewards) {
            LoreVoter.plugin.getServer().dispatchCommand(LoreVoter.plugin.getServer().getConsoleSender(), reward);
        }
    }
}
