package com.mradhit.lorevoter.manager;

import com.mradhit.lorevoter.event.VotePartyEvent;
import com.mradhit.lorevoter.file.VotePartyCacheFile;
import org.bukkit.Bukkit;

public class VotePartyManager {
    private static VotePartyManager INSTANCE;

    private final VotePartyCacheFile cacheFile = VotePartyCacheFile.getInstance();

    public static VotePartyManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new VotePartyManager();
        }

        return INSTANCE;
    }

    private VotePartyManager() { }

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

    public void execute() {
        // TODO: execute command when vote party goal is reached
    }
}
