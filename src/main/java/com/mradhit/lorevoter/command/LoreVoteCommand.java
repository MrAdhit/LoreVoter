package com.mradhit.lorevoter.command;

import com.mradhit.lorevoter.file.LoreVoterConfigFile;
import com.mradhit.lorevoter.manager.VotePartyManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LoreVoteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String sub = args[0];
        if (sub == null) return true;

        if (sub.equalsIgnoreCase("reload")) {
            sender.sendMessage("Reloading config");
            LoreVoterConfigFile.getInstance().reloadConfig();
        }

        if (sub.equalsIgnoreCase("party")) {
            String sub1 = args[1];
            if (sub1 == null) return true;

            if (sub1.equalsIgnoreCase("set")) {
                String sub2 = args[2];
                if (sub2 == null) return true;

                if (sub2.equalsIgnoreCase("amount")) {
                    String sub3 = args[3];
                    if (sub3 == null) return true;

                    int amount = Integer.parseInt(sub3);

                    VotePartyManager.getInstance().set(amount);
                    sender.sendMessage("Setting VoteParty amount to " + amount);
                }
            }

            if (sub1.equalsIgnoreCase("query")) {
                sender.sendMessage("Current VoteParty amount " + VotePartyManager.getInstance().get());
            }

            if (sub1.equalsIgnoreCase("trigger")) {
                sender.sendMessage("Triggering VoteParty reward");
                VotePartyManager.getInstance().execute();
            }

        }

        return false;
    }
}
