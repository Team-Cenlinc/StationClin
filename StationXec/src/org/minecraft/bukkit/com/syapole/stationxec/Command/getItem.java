package org.minecraft.bukkit.com.syapole.stationxec.Command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlRepresenter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.minecraft.bukkit.com.syapole.stationxec.Main;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class getItem implements CommandExecutor {
    private Main plugin;

    public getItem(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String ID = p.getName();

            File cardInfoFolder = new File(plugin.getDataFolder(), "carddata");
            File cardInfoYAML = new File(cardInfoFolder.getPath(), ID + ".yml");
            File playerInfoFolder = new File(plugin.getDataFolder(), "playerdata");
            if (!cardInfoYAML.exists()) {
                ItemStack ICCard = new ItemStack(Material.NAME_TAG);
                ItemMeta ICCardMeta = ICCard.getItemMeta();

                ICCardMeta.setDisplayName(ChatColor.AQUA + "Transportation Card");

                Random ra =new Random();
                int cardSecOri = ra.nextInt(100000)+1;
                String cardNum = cardSecOri + "";

                String MD5Str = MD5Encode(cardNum, "UTF-8");

                ArrayList<String> lore = new ArrayList<String>();
                lore.add("Balance: 200");
                lore.add("Status: N");
                lore.add("Owner: " + ID);
                lore.add("MD5: " + MD5Str);
                ICCardMeta.setLore(lore);
                ICCard.setItemMeta(ICCardMeta);

                Location PlayerLocation = p.getLocation();

                p.getWorld().dropItem(PlayerLocation, ICCard);

                File YAMLData = new File(cardInfoFolder.getPath(), ID + ".yml");
                File PlayerYAMLData = new File(playerInfoFolder.getPath(), ID + ".yml");



                Yaml yaml = new Yaml();

                Map<String, Object> main = new HashMap<String, Object>();
                main.put("Balance", 200);
                main.put("Status", "N");
                main.put("Owner", ID);
                main.put("Location", null);
                main.put("MD5", MD5Str);
                main.put("OldBalance", 200);

                Map<String, Object> playerInfo = new HashMap<String, Object>();
                playerInfo.put("Top-up", 0);
                playerInfo.put("ClickTimes", 0);
                playerInfo.put("MonthlyTicket", null);
                playerInfo.put("ExpireTime", null);



                try {
                    yaml.dump(main, new OutputStreamWriter(new FileOutputStream(YAMLData)));
                    yaml.dump(playerInfo, new OutputStreamWriter(new FileOutputStream(PlayerYAMLData)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            } else {
                sender.sendMessage(ChatColor.AQUA + "StationXec" + ChatColor.RED + " >>> You have got a card! If you lost it, try to use /refound");
            }
        } else {
            sender.sendMessage(ChatColor.AQUA + "StationXec" + ChatColor.RED + " >>> No available on the console");
        }
        return true;
    }

    private String toString(Object o) {
        String res = toString(o);
        return res;
    }

    // MD5


    private static final String hexDigIts[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String MD5Encode(String origin, String charsetname) {

        String resultString = null;

        try {

            resultString = new String(origin);

            MessageDigest md = MessageDigest.getInstance("MD5");

            if (null == charsetname || "".equals(charsetname)) {

                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));

            } else {

                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));

            }

        } catch (Exception e) {

        }

        return resultString;

    }

    public static String byteArrayToHexString(byte b[]) {

        StringBuffer resultSb = new StringBuffer();

        for (int i = 0; i < b.length; i++) {

            resultSb.append(byteToHexString(b[i]));

        }

        return resultSb.toString();

    }

    public static String byteToHexString(byte b) {

        int n = b;

        if (n < 0) {

            n += 256;

        }

        int d1 = n / 16;

        int d2 = n % 16;

        return hexDigIts[d1] + hexDigIts[d2];
    }
}
