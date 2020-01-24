package org.minecraft.bukkit.com.syapole.stationxec.Controller;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.minecraft.bukkit.com.syapole.stationxec.Main;

import java.util.ArrayList;

public class XzHookAvoidListener implements Listener {
    Main plugin;

    public XzHookAvoidListener(Main XecHyperloo) {
        plugin = XecHyperloo;
    }
    @EventHandler
    public void onAvoidPlacing(BlockPlaceEvent e){
        Player p = e.getPlayer();
        ItemStack Controller = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta CTMeta = Controller.getItemMeta();
        CTMeta.setDisplayName(ChatColor.AQUA + "Remote Controller");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Only for Platform Screen Door[Range:4*4]");
        CTMeta.setLore(lore);
        Controller.setItemMeta(CTMeta);
        if (e.getItemInHand().equals(Controller)){
            e.setCancelled(true);
        }else{
            e.setCancelled(false);
        }

    }
}
