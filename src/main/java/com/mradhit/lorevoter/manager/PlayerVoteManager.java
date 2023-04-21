package com.mradhit.lorevoter.manager;

import com.mradhit.lorevoter.file.PlayerVoteCacheFile;
import com.mradhit.lorevoter.util.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerVoteManager {
    private String username;

    private final PlayerVoteCacheFile cacheFile = PlayerVoteCacheFile.getInstance();

    public PlayerVoteManager(String username) {
        this.username = username;
    }

    public int get() {
        return this.cacheFile.cache.getOrDefault(this.username, 0);
    }

    public void add() {
        this.cacheFile.cache.put(this.username, this.get() + 1);
        this.cacheFile.write();
    }

    public void sub() {
        this.cacheFile.cache.put(this.username, this.get() - 1);
        this.cacheFile.write();
    }

    public void set(int amount) {
        PlayerVoteCacheFile.getInstance().cache.put(this.username, amount);
        this.cacheFile.write();
    }

    public static List<Tuple<String, Integer>> top() {
        List<Tuple<String, Integer>> sorted = new ArrayList<>();
        List<Integer> list = PlayerVoteCacheFile.getInstance().cache.values().stream().sorted().collect(Collectors.toList());

        for (Integer val : list) {
            for (Map.Entry<String, Integer> entry : PlayerVoteCacheFile.getInstance().cache.entrySet()) {
                if (entry.getValue().equals(val)) {
                    sorted.add(new Tuple<>(entry.getKey(), val));
                }
            }
        }

        return sorted;
    }
}
