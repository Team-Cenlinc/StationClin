package org.minecraft.bukkit.com.syapole.stationxec.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Tab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Collection onlinePlayers = Bukkit.getOnlinePlayers();
        List<String> playersList = new ArrayList<>();
        for (Object o : onlinePlayers) {
            playersList.add(((Player) o).getDisplayName());
        }

        if (args.length > 1) {
            return new ArrayList<>();
        }

        if (args.length == 0) {
            return playersList;
        }

        String[] players = new String[playersList.size()];
        playersList.toArray(players);
        return Arrays.stream(players).filter(name -> name.toLowerCase().startsWith(args[0])).collect(Collectors.toList());
    }

}
