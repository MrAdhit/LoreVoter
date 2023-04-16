package com.mradhit.lorevoter.file;

import com.mradhit.lorevoter.LoreVoter;

import java.io.*;
import java.util.HashMap;

public class VoterCacheFile {
    private static VoterCacheFile INSTANCE;
    private static final LoreVoterConfigFile config = LoreVoterConfigFile.getInstance();

    public HashMap<String, Integer> cache;
    private File cacheFile;

    public static VoterCacheFile getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new VoterCacheFile();
        }

        return INSTANCE;
    }

    private VoterCacheFile() {
        try {
            this.cacheFile = new File(LoreVoter.plugin.getDataFolder(), config.getConfig().player.cache_file);
            this.cacheFile.getParentFile().mkdirs();
            this.cacheFile.createNewFile();

            FileInputStream inputStream = new FileInputStream(this.cacheFile);

            byte[] blob = inputStream.readAllBytes();

            ObjectInputStream bin = new ObjectInputStream(new ByteArrayInputStream(blob));
            this.cache = (HashMap<String, Integer>) bin.readObject();

            inputStream.close();
        } catch (EOFException | ClassNotFoundException e) {
            this.cache = new HashMap<>();
            this.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write() {
        Object obj = this.cache;

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

            objectOutputStream.writeObject(obj);

            try (FileOutputStream cacheOutputStream = new FileOutputStream(this.cacheFile, false)) {
                cacheOutputStream.write(byteArrayOutputStream.toByteArray());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
