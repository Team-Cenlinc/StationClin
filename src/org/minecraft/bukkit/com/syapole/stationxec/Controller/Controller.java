package org.minecraft.bukkit.com.syapole.stationxec.Controller;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.minecraft.bukkit.com.syapole.stationxec.Main;
import org.minecraft.bukkit.com.syapole.stationxec.StationListener.BlockDoors.FullHeight;
import org.minecraft.bukkit.com.syapole.stationxec.StationListener.BlockDoors.HalfHeight;

import java.util.ArrayList;

public class Controller implements Listener {
    Main plugin;

    public Controller(Main XecHyperloo) {
        plugin = XecHyperloo;
    }

    @EventHandler
    public void onControllingDoor(PlayerInteractEvent e){
        ItemStack ControllerItem = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta CTMeta = ControllerItem.getItemMeta();
        CTMeta.setDisplayName(ChatColor.AQUA + "Remote Controller");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Only for Platform Screen Door[Range:5*5]");
        CTMeta.setLore(lore);
        ControllerItem.setItemMeta(CTMeta);
        Location PlayerLoc = e.getPlayer().getLocation();
        Block CenterBlock = PlayerLoc.getBlock();
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(e.getItem() != null && e.getClickedBlock() != null) {
                if(e.getItem().equals(ControllerItem)){
                    for(int x = 0; x < 5; x++) {
                        for (int z = 0; z < 5; z++) {
                            for(int y = 0; y < 5; y++){
                                if(CenterBlock.getRelative(x, y, z).getType().equals(Material.WALL_SIGN) || CenterBlock.getRelative(x, y, z).getType().equals(Material.SIGN)) {
                                    Location SignLoc = CenterBlock.getRelative(x, y, z).getLocation();
                                    Sign s = (Sign) SignLoc.getBlock();
                                    if (s.getLine(0).equals(ChatColor.AQUA + "[Station]") && s.getLine(2).equals("<Half Height>") && s.getLine(3).equals(ChatColor.AQUA + "[Block Door]")) {
                                        new HalfHeight(plugin).openDoor(SignLoc);
                                    }else if (s.getLine(0).equals(ChatColor.AQUA + "[Station]") && s.getLine(2).equals("<Full Height>") && s.getLine(3).equals(ChatColor.AQUA + "[Block Door]")){
                                        new FullHeight(plugin).openDoorFull(SignLoc);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
