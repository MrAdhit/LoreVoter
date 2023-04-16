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
                    cacheOutputStream.write(defaultConfig);
                }
            }

            Toml toml = new Toml().read(this.configFile);
            this.config = toml.to(Config.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Vote getConfig() {
        return this.config.vote;
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

    public static class Vote {
        public VotePlayer player = new VotePlayer();
        public VoteParty party = new VoteParty();
    }

    public static class Config {
        Vote vote = new Vote();
    }

    private static final byte[] defaultConfig = {
            (byte)0x5B, (byte)0x76, (byte)0x6F, (byte)0x74, (byte)0x65, (byte)0x2E,
            (byte)0x70, (byte)0x6C, (byte)0x61, (byte)0x79, (byte)0x65, (byte)0x72,
            (byte)0x5D, (byte)0x0D, (byte)0x0A, (byte)0x63, (byte)0x61, (byte)0x63,
            (byte)0x68, (byte)0x65, (byte)0x5F, (byte)0x66, (byte)0x69, (byte)0x6C,
            (byte)0x65, (byte)0x20, (byte)0x3D, (byte)0x20, (byte)0x22, (byte)0x63,
            (byte)0x61, (byte)0x63, (byte)0x68, (byte)0x65, (byte)0x2F, (byte)0x73,
            (byte)0x61, (byte)0x76, (byte)0x65, (byte)0x64, (byte)0x76, (byte)0x6F,
            (byte)0x74, (byte)0x65, (byte)0x72, (byte)0x2E, (byte)0x62, (byte)0x69,
            (byte)0x6E, (byte)0x22, (byte)0x0D, (byte)0x0A, (byte)0x72, (byte)0x65,
            (byte)0x77, (byte)0x61, (byte)0x72, (byte)0x64, (byte)0x73, (byte)0x20,
            (byte)0x3D, (byte)0x20, (byte)0x5B, (byte)0x0D, (byte)0x0A, (byte)0x20,
            (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x22, (byte)0x67, (byte)0x69,
            (byte)0x76, (byte)0x65, (byte)0x20, (byte)0x7B, (byte)0x75, (byte)0x73,
            (byte)0x65, (byte)0x72, (byte)0x6E, (byte)0x61, (byte)0x6D, (byte)0x65,
            (byte)0x7D, (byte)0x20, (byte)0x77, (byte)0x6F, (byte)0x6F, (byte)0x64,
            (byte)0x65, (byte)0x6E, (byte)0x5F, (byte)0x73, (byte)0x77, (byte)0x6F,
            (byte)0x72, (byte)0x64, (byte)0x22, (byte)0x2C, (byte)0x20, (byte)0x0D,
            (byte)0x0A, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x22,
            (byte)0x67, (byte)0x69, (byte)0x76, (byte)0x65, (byte)0x20, (byte)0x7B,
            (byte)0x75, (byte)0x73, (byte)0x65, (byte)0x72, (byte)0x6E, (byte)0x61,
            (byte)0x6D, (byte)0x65, (byte)0x7D, (byte)0x20, (byte)0x64, (byte)0x69,
            (byte)0x72, (byte)0x74, (byte)0x22, (byte)0x0D, (byte)0x0A, (byte)0x5D,
            (byte)0x0D, (byte)0x0A, (byte)0x0D, (byte)0x0A, (byte)0x5B, (byte)0x76,
            (byte)0x6F, (byte)0x74, (byte)0x65, (byte)0x2E, (byte)0x70, (byte)0x61,
            (byte)0x72, (byte)0x74, (byte)0x79, (byte)0x5D, (byte)0x0D, (byte)0x0A,
            (byte)0x67, (byte)0x6F, (byte)0x61, (byte)0x6C, (byte)0x20, (byte)0x3D,
            (byte)0x20, (byte)0x32, (byte)0x32, (byte)0x0D, (byte)0x0A, (byte)0x63,
            (byte)0x61, (byte)0x63, (byte)0x68, (byte)0x65, (byte)0x5F, (byte)0x66,
            (byte)0x69, (byte)0x6C, (byte)0x65, (byte)0x20, (byte)0x3D, (byte)0x20,
            (byte)0x22, (byte)0x63, (byte)0x61, (byte)0x63, (byte)0x68, (byte)0x65,
            (byte)0x2F, (byte)0x76, (byte)0x6F, (byte)0x74, (byte)0x65, (byte)0x70,
            (byte)0x61, (byte)0x72, (byte)0x74, (byte)0x79, (byte)0x2E, (byte)0x62,
            (byte)0x69, (byte)0x6E, (byte)0x22, (byte)0x0D, (byte)0x0A, (byte)0x72,
            (byte)0x65, (byte)0x77, (byte)0x61, (byte)0x72, (byte)0x64, (byte)0x73,
            (byte)0x20, (byte)0x3D, (byte)0x20, (byte)0x5B, (byte)0x0D, (byte)0x0A,
            (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x22, (byte)0x67,
            (byte)0x69, (byte)0x76, (byte)0x65, (byte)0x20, (byte)0x40, (byte)0x61,
            (byte)0x20, (byte)0x77, (byte)0x6F, (byte)0x6F, (byte)0x64, (byte)0x65,
            (byte)0x6E, (byte)0x5F, (byte)0x73, (byte)0x77, (byte)0x6F, (byte)0x72,
            (byte)0x64, (byte)0x22, (byte)0x2C, (byte)0x20, (byte)0x0D, (byte)0x0A,
            (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x22, (byte)0x67,
            (byte)0x69, (byte)0x76, (byte)0x65, (byte)0x20, (byte)0x40, (byte)0x61,
            (byte)0x20, (byte)0x64, (byte)0x69, (byte)0x72, (byte)0x74, (byte)0x22,
            (byte)0x0D, (byte)0x0A, (byte)0x5D, (byte)0x0D, (byte)0x0A
    };
}
