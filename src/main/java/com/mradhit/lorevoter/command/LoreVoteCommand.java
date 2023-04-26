package com.mradhit.lorevoter.command;

import com.mradhit.lorevoter.file.LoreVoterConfigFile;
import com.mradhit.lorevoter.manager.PlayerVoteManager;
import com.mradhit.lorevoter.manager.VotePartyManager;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.mradhit.lorevoter.LoreVoter.plugin;
import static com.mradhit.lorevoter.LoreVoter.task;

public class LoreVoteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (args[0] == null) return true;
            String sub = args[0];

            if (sub.equalsIgnoreCase("reload")) {
                sender.sendMessage("Reloading config");
                LoreVoterConfigFile.getInstance().reloadConfig();
                task.cancel();
                task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage(PlaceholderAPI.setPlaceholders(player,
                                LoreVoterConfigFile.getInstance().getConfig().vote.broadcast.message));
                    }
                }, 0, LoreVoterConfigFile.getInstance().getConfig().vote.broadcast.interval);
            }

            if (sub.equalsIgnoreCase("broadcast")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(PlaceholderAPI.setPlaceholders(player,
                            LoreVoterConfigFile.getInstance().getConfig().vote.broadcast.message));
                }
            }

            if (sub.equalsIgnoreCase("party")) {
                if (args[1] == null) return true;
                String sub1 = args[1];

                if (sub1.equalsIgnoreCase("set")) {
                    if (args[2] == null) return true;
                    String sub2 = args[2];

                    int amount = Integer.parseInt(sub2);

                    VotePartyManager.getInstance().set(amount);
                    sender.sendMessage("Setting VoteParty amount to " + amount);
                }

                if (sub1.equalsIgnoreCase("query")) {
                    sender.sendMessage("Current VoteParty amount " + VotePartyManager.getInstance().get());
                }

                if (sub1.equalsIgnoreCase("trigger")) {
                    sender.sendMessage("Triggering VoteParty reward");
                    VotePartyManager.getInstance().execute();
                }

            }

            if (sub.equalsIgnoreCase("player")) {
                if (args[1] == null) return true;
                String sub1 = args[1];

                if (args[2] == null) return true;
                String sub2 = args[2];

                PlayerVoteManager playerVoteManager = new PlayerVoteManager(sub1);

                if (sub2.equalsIgnoreCase("set")) {
                    if (args[3] == null) return true;
                    String sub3 = args[3];

                    int amount = Integer.parseInt(sub3);

                    playerVoteManager.set(amount);
                }

                if (sub2.equalsIgnoreCase("add")) {
                    sender.sendMessage("Adding " + sub1 + " votes");
                    playerVoteManager.add();
                }

                if (sub2.equalsIgnoreCase("sub")) {
                    sender.sendMessage("Subtracting " + sub1 + " votes");
                    playerVoteManager.sub();
                }

                if (sub2.equalsIgnoreCase("query")) {
                    sender.sendMessage(sub1 + " has " + playerVoteManager.get() + " votes");
                }
            }
            return false;
        } catch (IndexOutOfBoundsException e) {
            return true;
        }
    }
}
