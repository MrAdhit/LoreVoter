package com.mradhit.lorevoter.manager;

import com.mradhit.lorevoter.LoreVoter;
import com.mradhit.lorevoter.file.VoterCacheFile;

public class VoteCacheManager {
    private final String username;
    private final VoterCacheFile cacheFile = VoterCacheFile.getInstance();

    public VoteCacheManager(String username) {
        this.username = username;
    }

    public void cache() {
        cacheFile.cache.put(this.username, cacheFile.cache.getOrDefault(this.username, 0) + 1);
        cacheFile.write();
    }

    public boolean check() {
        return cacheFile.cache.containsKey(this.username);
    }

    public void execute() {
        if (!this.check()) return;
        if (LoreVoter.plugin.getServer().getPlayer(this.username) == null) return;

        LoreVoter.logger.info(this.username + " has saved vote, giving vote rewards");

        VoteRewardManager reward = new VoteRewardManager(this.username);
        int amount = cacheFile.cache.get(this.username);

        for (int i = 0; i < amount; i++) {
            reward.execute();
        }

        cacheFile.cache.remove(this.username);
        cacheFile.write();
    }
}
