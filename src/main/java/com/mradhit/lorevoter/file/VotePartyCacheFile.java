package com.mradhit.lorevoter.file;

import com.mradhit.lorevoter.LoreVoter;

import java.io.*;
import java.util.HashMap;

public class VotePartyCacheFile {
    private static VotePartyCacheFile INSTANCE;
    public int cache;
    private File cacheFile;

    public static VotePartyCacheFile getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new VotePartyCacheFile();
        }

        return INSTANCE;
    }

    private VotePartyCacheFile() {
        try {
            this.cacheFile = new File(LoreVoter.plugin.getDataFolder(), "votepartycache.bin");
            this.cacheFile.getParentFile().mkdirs();
            this.cacheFile.createNewFile();

            FileInputStream inputStream = new FileInputStream(this.cacheFile);

            byte[] blob = inputStream.readAllBytes();

            ObjectInputStream bin = new ObjectInputStream(new ByteArrayInputStream(blob));
            this.cache = (int) bin.readObject();

            inputStream.close();
        } catch (EOFException | ClassNotFoundException e) {
            this.cache = 0;
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
