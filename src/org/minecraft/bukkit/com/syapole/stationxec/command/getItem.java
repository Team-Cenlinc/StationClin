package org.minecraft.bukkit.com.syapole.stationxec.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class getItem implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings){
        if(sender instanceof Player){
            Player p = (Player) sender;
            ItemStack Controller = new ItemStack(Material.TRIPWIRE_HOOK);
            ItemMeta CTMeta = Controller.getItemMeta();
            CTMeta.setDisplayName(ChatColor.AQUA + "Remote Controller");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("Only for Platform Screen Door[Range:5*5]");
            CTMeta.setLore(lore);
            Controller.setItemMeta(CTMeta);
            p.getInventory().addItem(Controller);
        }else{
            sender.sendMessage(ChatColor.AQUA + "StationXec" + ChatColor.RED + " >>> No available in the console");
        }
        return true;
    }
}
