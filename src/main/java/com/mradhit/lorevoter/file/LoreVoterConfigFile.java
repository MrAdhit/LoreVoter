package com.mradhit.lorevoter.file;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import com.mradhit.lorevoter.LoreVoter;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class LoreVoterConfigFile {
    private static LoreVoterConfigFile INSTANCE;

    private File configFile;
    private Config config;

    public static LoreVoterConfigFile getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LoreVoterConfigFile();
        }

        return INSTANCE;
    }

    private LoreVoterConfigFile() {
        try {
            this.configFile = new File(LoreVoter.plugin.getDataFolder(), "config.toml");
            this.configFile.getParentFile().mkdirs();

            if (this.configFile.createNewFile()) {
                try (FileOutputStream cacheOutputStream = new FileOutputStream(this.configFile, false)) {
                    cacheOutputStream.write(LoreVoter.plugin.getResource("config.toml").readAllBytes());
                }
            }

            Toml toml = new Toml().read(this.configFile);
            this.config = toml.to(Config.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Config getConfig() {
        return this.config;
    }

    public void saveConfig() {
        TomlWriter tomlWriter = new TomlWriter();

        try {
            tomlWriter.write(this.config, this.configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        LoreVoter.logger.warning("Reloading Config Files!");
        Toml toml = new Toml().read(this.configFile);
        this.config = toml.to(Config.class);
    }

    public static class VotePlayer {
        public List<String> rewards = Arrays.asList("give {username} wooden_sword", "give {username} dirt");
        String cache_file = "cache/savedvoter.bin";
    }

    public static class VoteParty {
        public int goal = 20;
        public List<String> rewards = Arrays.asList("give @a wooden_sword", "give @a dirt");
        String cache_file = "cache/voteparty.bin";
    }

    public static class Broadcast {
        public String message = "&c&l{username}&r just voted using &a&l{service}";
    }

    public static class Vote {
        public VotePlayer player = new VotePlayer();
        public VoteParty party = new VoteParty();
    }

    public static class Config {
        public Vote vote = new Vote();
        public Broadcast broadcast = new Broadcast();
    }
}
