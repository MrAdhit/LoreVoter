package com.mradhit.lorevoter.placeholder;

import com.mradhit.lorevoter.manager.VotePartyManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class LoreVoterExpansion extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "lorevote";
    }

    @Override
    public @NotNull String getAuthor() {
        return "MrAdhit";
    }

    @Override
    public @NotNull String getVersion() {
        return getClass().getPackage().getImplementationVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        String[] args = params.split("_");

        String sub = args[0];
        if (sub == null) return null;

        if (sub.equalsIgnoreCase("party")) {
            String sub1 = args[1];
            if (sub1 == null) return null;

            if (sub1.equalsIgnoreCase("amount")) {
                return String.valueOf(VotePartyManager.getInstance().get());
            }

            if (sub1.equalsIgnoreCase("max")) {
                return String.valueOf(VotePartyManager.getInstance().getMax());
            }

            if (sub1.equalsIgnoreCase("required")) {
                return String.valueOf(VotePartyManager.getInstance().getMax() - VotePartyManager.getInstance().get());
            }
        }
        return null;
    }
}
