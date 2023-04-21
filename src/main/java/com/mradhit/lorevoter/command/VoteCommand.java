package com.mradhit.lorevoter.command;

import com.mradhit.lorevoter.file.LoreVoterConfigFile;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VoteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String message = LoreVoterConfigFile.getInstance().getConfig().vote.output.replaceAll("&", "§").replaceAll("\\{and}", "&");
        sender.sendMessage(PlaceholderAPI.setPlaceholders((Player) sender, message));
        return false;
    }
}
