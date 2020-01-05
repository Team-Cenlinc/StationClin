package org.minecraft.bukkit.com.syapole.stationxec.Command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.minecraft.bukkit.com.syapole.stationxec.Main;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Refound implements CommandExecutor {

    private Main plugin;

    public Refound(Main plugin) {
        this.plugin = plugin;
    }

    Map<String, Object> cardInfo;

    private String CurrencyUnit;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {

            Player p = (Player) sender;
            String ID = p.getName();

            try {
                CurrencyUnit = (String) plugin.getConfig().get("CurrencyUnit");
            } catch(CommandException ce) {
                CurrencyUnit = "Dollar";
            }

            File cardInfoFolder = new File(plugin.getDataFolder(), "carddata");
            File cardInfoYAML = new File(cardInfoFolder.getPath(), ID + ".yml");
            if (cardInfoYAML.exists()) {
                Yaml yaml = new Yaml();
                File YAMLData = new File(cardInfoFolder.getPath(), ID + ".yml");
                try {
                    cardInfo = yaml.load(new InputStreamReader(new FileInputStream(YAMLData)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                int Balance = (int) cardInfo.get("Balance");
                String Status = (String) cardInfo.get("Status");
                String getID = (String) cardInfo.get("ID");

                ItemStack ICCard = new ItemStack(Material.NAME_TAG);
                ItemMeta ICCardMeta = ICCard.getItemMeta();

                ICCardMeta.setDisplayName(ChatColor.AQUA + "Transportation Card");

                Random ra =new Random();
                int cardSecOri = ra.nextInt(100000)+1;
                String cardNum = cardSecOri + "";

                String MD5Str = MD5Encode(cardNum, "UTF-8");

                ArrayList<String> lore = new ArrayList<String>();
                lore.add("Balance: " + Balance);
                lore.add("Status: N");
                lore.add("Owner: " + ID);
                lore.add("MD5: " + MD5Str);
                ICCardMeta.setLore(lore);
                ICCard.setItemMeta(ICCardMeta);

                Location PlayerLocation = p.getLocation();

                p.getWorld().dropItem(PlayerLocation, ICCard);

                Main.econ.withdrawPlayer(p, 20);

                p.sendMessage(ChatColor.AQUA + "StationXec" +" >>> We'll take 20 " + CurrencyUnit + " as service fee.");

                if (Balance < 0) {
                    sender.sendMessage(ChatColor.AQUA + "StationXec" + ChatColor.RED + " >>> No balance left...");
                } else {
                    cardInfo.replace("MD5", MD5Str);
                    cardInfo.replace("Location", null);
                }

                try {
                    yaml.dump(cardInfo, new OutputStreamWriter(new FileOutputStream(YAMLData)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                sender.sendMessage(ChatColor.AQUA + "StationXec" + ChatColor.RED + " >>> You haven't got a transportation card");
            }
        } else {
            sender.sendMessage(ChatColor.AQUA + "StationXec" + ChatColor.RED + " >>> No available on the console");
        }
        return true;
    }
    // MD5


    private static final String hexDigIts[] = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

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
