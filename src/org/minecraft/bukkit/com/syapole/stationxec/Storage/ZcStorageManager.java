package org.minecraft.bukkit.com.syapole.stationxec.Storage;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.minecraft.bukkit.com.syapole.stationxec.Main;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ZcStorageManager implements Listener {
    private Yaml yaml = new Yaml();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(p.hasPlayedBefore()){
            String ID = p.getName();
            p.sendMessage(ChatColor.AQUA + "StationXec >>> Welcome, " + p.getName() + ".");
            p.getServer().getLogger().info(ChatColor.AQUA + "StationXec >>> Now checking the file completeness for the player " + ID);
            File playerDataFile = new File(Main.getInstance().getDataFolder(), "playerdata");
            File playerInfoYAML = new File(playerDataFile.getPath(), ID + ".yml");
            if (!playerDataFile.exists()) {
                playerDataFile.mkdirs();
            }
            if (!playerInfoYAML.exists()) {
                Map<String, Object> playerData = new HashMap<String, Object>();
                playerData.put("Location", null);
                playerData.put("LastTF", null);
                playerData.put("LastTM", null);
                try {
                    yaml.dump(playerData, new OutputStreamWriter(new FileOutputStream(playerInfoYAML)));
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }else{
            String ID = p.getName();
            p.sendMessage(ChatColor.AQUA + "StationXec >>> Welcome, " + p.getName() + ".");
            p.getServer().getLogger().info(ChatColor.AQUA + "StationXec >>> Now checking the file completeness for the player " + ID);
            File playerDataFile = new File(Main.getInstance().getDataFolder(), "playerdata");
            File playerInfoYAML = new File(playerDataFile.getPath(), ID + ".yml");
            if (!playerDataFile.exists()) {
                playerDataFile.mkdirs();
                p.getServer().getLogger().info(ChatColor.AQUA + "StationXec >>> Now checking the file completeness for the player " + ID);
            }
            if (!playerInfoYAML.exists()) {
                Map<String, Object> playerData = new HashMap<String, Object>();
                playerData.put("Location", null);
                playerData.put("LastTF", null);
                playerData.put("LastTM", null);
                try {
                    yaml.dump(playerData, new OutputStreamWriter(new FileOutputStream(playerInfoYAML)));
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


}
