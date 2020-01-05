package org.minecraft.bukkit.com.syapole.stationxec.ICCard;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.minecraft.bukkit.com.syapole.stationxec.Main;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class XcAutoTUM implements Listener {
    private Main plugin;

    private int Amount;
    private String CurrencyUnit;

    public XcAutoTUM(Main ATUMStatus) {
        plugin = ATUMStatus;
    }

    private Map<String, Object> playerInfo;
    private Map<String, Object> cardInfo;

    private ItemStack Inv0;
    ItemStack Inv1;
    ItemStack Inv2;
    ItemStack Inv3;
    ItemStack Inv4;
    ItemStack Inv5;
    ItemStack Inv6;
    ItemStack Inv7;
    ItemStack Inv8;

    String Amount100 = ChatColor.AQUA + "> 100 <";
    String Amount200 = ChatColor.AQUA + "> 200 <";
    String Amount500 = ChatColor.AQUA + "> 500 <";
    String Amount1000 = ChatColor.AQUA + "> 1000 <";

    ItemStack confirmButton = new ItemStack(Material.WOOL, 1, (byte) 13);
    ItemStack cancelButton = new ItemStack(Material.WOOL, 1, (byte) 14);
    ItemStack verifyButton = new ItemStack(Material.WOOL, 1, (byte) 9);

    ItemMeta cfbMeta = confirmButton.getItemMeta();
    ItemMeta cnbMeta = confirmButton.getItemMeta();
    ItemMeta vfbMeta = confirmButton.getItemMeta();
    int clickTimes;

    @EventHandler
    public void onTouchScreen(PlayerInteractEvent e) throws IOException {
        try {
            CurrencyUnit = (String) plugin.getConfig().get("CurrencyUnit");
        } catch (CommandException ce) {
            CurrencyUnit = "Dollar";
        }
        Player p = e.getPlayer();
        String ID = p.getName();
        File cardInfoFolder = new File(plugin.getDataFolder(), "carddata");
        File cardInfoYAML = new File(cardInfoFolder.getPath(), ID + ".yml");
        if (e.getClickedBlock().getType().equals(Material.WALL_SIGN)) {
            Sign s = (Sign) e.getClickedBlock().getState();
            if (s.getLine(0).equals(ChatColor.AQUA + "[Station]") && s.getLine(3).equals(ChatColor.AQUA + "[ATUM]")) {
                if (!cardInfoYAML.exists()) {
                    p.sendMessage(ChatColor.AQUA + "StationXec" + ChatColor.RED + " >>> You haven't got a card!");
                }
                ItemStack ICCard = new ItemStack(Material.NAME_TAG);
                ItemMeta ICCardMeta = ICCard.getItemMeta();
                ICCardMeta.setDisplayName(ChatColor.AQUA + "Transportation Card");
                if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    if (e.getHand().equals(EquipmentSlot.HAND)) {
                        File playerInfoFolder = new File(plugin.getDataFolder(), "playerdata");
                        File PlayerYAMLData = new File(playerInfoFolder.getPath(), ID + ".yml");

                        Yaml yaml = new Yaml();

                        try {
                            playerInfo = yaml.load(new InputStreamReader(new FileInputStream(PlayerYAMLData)));
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }

                        clickTimes = (int) playerInfo.get("ClickTimes");
                        if (clickTimes == 0) {
                            Amount = 100;

                            cfbMeta.setDisplayName(ChatColor.GREEN + "Confirm");
                            cnbMeta.setDisplayName(ChatColor.RED + "Cancel");
                            vfbMeta.setDisplayName(ChatColor.AQUA + "Top-up Mode");

                            confirmButton.setItemMeta(cfbMeta);
                            cancelButton.setItemMeta(cnbMeta);
                            verifyButton.setItemMeta(vfbMeta);
                            if (p.getInventory().getItem(0).equals(confirmButton)) {
                                p.sendMessage(ChatColor.AQUA + "----StationXec Card Auto Top-up System----");
                                p.sendMessage(ChatColor.AQUA + "The top-up amount you choose now: 100 " + CurrencyUnit);
                            } else {

                                ItemStack Air = new ItemStack(Material.AIR, 1);

                                Inv0 = p.getInventory().getItem(0);
                                Inv1 = p.getInventory().getItem(1);
                                Inv2 = p.getInventory().getItem(2);
                                Inv3 = p.getInventory().getItem(3);
                                Inv4 = p.getInventory().getItem(4);
                                Inv5 = p.getInventory().getItem(5);
                                Inv6 = p.getInventory().getItem(6);
                                Inv7 = p.getInventory().getItem(7);
                                Inv8 = p.getInventory().getItem(8);

                                p.getInventory().setItem(0, Air);
                                p.getInventory().setItem(1, Air);
                                p.getInventory().setItem(2, Air);
                                p.getInventory().setItem(3, Air);
                                p.getInventory().setItem(4, Air);
                                p.getInventory().setItem(5, Air);
                                p.getInventory().setItem(6, Air);
                                p.getInventory().setItem(7, Air);
                                p.getInventory().setItem(8, Air);

                                p.getInventory().setItem(0, confirmButton);
                                p.getInventory().setItem(4, verifyButton);
                                p.getInventory().setItem(8, cancelButton);

                                p.sendMessage(ChatColor.AQUA + "----StationXec Card Auto Top-up System----");
                                p.sendMessage(ChatColor.AQUA + "The top-up amount you choose now: 100 " + CurrencyUnit);
                            }
                        } else if (clickTimes == 1) {
                            p.sendMessage(ChatColor.AQUA + "----StationXec Card Auto Top-up System----");
                            p.sendMessage(ChatColor.AQUA + "The top-up amount you choose now: 200 " + CurrencyUnit);
                            Amount = 200;
                        } else if (clickTimes == 2) {
                            p.sendMessage(ChatColor.AQUA + "----StationXec Card Auto Top-up System----");
                            p.sendMessage(ChatColor.AQUA + "The top-up amount you choose now: 500 " + CurrencyUnit);
                            Amount = 500;
                        } else if (clickTimes == 3) {
                            p.sendMessage(ChatColor.AQUA + "----StationXec Card Auto Top-up System----");
                            p.sendMessage(ChatColor.AQUA + "The top-up amount you choose now: 1000 " + CurrencyUnit);
                            Amount = 1000;

                            clickTimes = -1;
                        }
                        try {
                            playerInfo = yaml.load(new InputStreamReader(new FileInputStream(PlayerYAMLData)));
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }

                        playerInfo.replace("ClickTimes", clickTimes += 1);
                        playerInfo.replace("Top-up", Amount);

                        try {
                            yaml.dump(playerInfo, new OutputStreamWriter(new FileOutputStream(PlayerYAMLData)));
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @EventHandler

    public void clickButton(BlockPlaceEvent e){
        String ID = e.getPlayer().getName();
        cfbMeta.setDisplayName(ChatColor.GREEN + "Confirm");
        cnbMeta.setDisplayName(ChatColor.RED + "Cancel");
        vfbMeta.setDisplayName(ChatColor.AQUA + "Top-up Mode");

        confirmButton.setItemMeta(cfbMeta);
        cancelButton.setItemMeta(cnbMeta);
        verifyButton.setItemMeta(vfbMeta);
        if(e.getItemInHand().equals(confirmButton)){

            File playerInfoFolder = new File(plugin.getDataFolder(), "playerdata");
            File PlayerYAMLData = new File(playerInfoFolder.getPath(), ID + ".yml");

            Yaml yaml = new Yaml();

            try {
                playerInfo = yaml.load(new InputStreamReader(new FileInputStream(PlayerYAMLData)));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

            int TopupAmount = (int) playerInfo.get("Top-up");

            playerInfo.replace("Top-up", 0);
            playerInfo.replace("ClickTimes", 0);

            File cardInfoFolder = new File(plugin.getDataFolder(), "carddata");
            File cardYAMLData = new File(cardInfoFolder.getPath(), ID + ".yml");

            try {
                cardInfo = yaml.load(new InputStreamReader(new FileInputStream(cardYAMLData)));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

            int OldBalance = (int) cardInfo.get("Balance");
            int NewBalance = OldBalance + TopupAmount;

            Main.econ.withdrawPlayer(e.getPlayer(), TopupAmount);

            e.getPlayer().sendMessage(ChatColor.AQUA + "----StationXec Card Auto Top-up System----");
            e.getPlayer().sendMessage(ChatColor.AQUA + "Owner: " + ID);
            e.getPlayer().sendMessage(ChatColor.AQUA + "Top-up Amount: " + TopupAmount + CurrencyUnit);
            e.getPlayer().sendMessage(ChatColor.AQUA + "Old Balance: " + OldBalance + CurrencyUnit);
            e.getPlayer().sendMessage(ChatColor.AQUA + "Current Balance: " + NewBalance + CurrencyUnit);

            cardInfo.replace("Balance", NewBalance);
            cardInfo.replace("OldBalance", OldBalance);


            String Owner = (String) cardInfo.get("Owner");
            String Status = (String) cardInfo.get("Status");
            String MD5 = (String) cardInfo.get("MD5");

            ItemStack ICCard = new ItemStack(Material.NAME_TAG);
            ItemMeta ICCardMeta = ICCard.getItemMeta();
            ICCardMeta.setDisplayName(ChatColor.AQUA + "Transportation Card");
            ArrayList<String> lore = new ArrayList<String>();
            lore.add("Balance: " + OldBalance);
            lore.add("Status: N");
            lore.add("Owner: " + ID);
            lore.add("MD5: " + MD5);
            ICCardMeta.setLore(lore);
            ICCard.setItemMeta(ICCardMeta);

            ItemMeta chICCardMeta = ICCard.getItemMeta();

            chICCardMeta.setDisplayName(ChatColor.AQUA + "Transportation Card");

            ArrayList<String> chlore = new ArrayList<String>();
            chlore.add("Balance: " + NewBalance);
            chlore.add("Status: E");
            chlore.add("Owner: " + ID);
            chlore.add("MD5: " + MD5);
            chICCardMeta.setLore(chlore);

            Inventory playerInventory = e.getPlayer().getInventory();
            for (int i = 0; i < 36; i++){
                ItemStack PlayerItem = playerInventory.getItem(i);
                if(PlayerItem == ICCard){
                    PlayerItem.setType(Material.AIR);
                    PlayerItem.setType(Material.NAME_TAG);
                    PlayerItem.setItemMeta(chICCardMeta);
                    break;
                }
            }

            try {
                yaml.dump(playerInfo, new OutputStreamWriter(new FileOutputStream(PlayerYAMLData)));
                yaml.dump(cardInfo, new OutputStreamWriter(new FileOutputStream(cardYAMLData)));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            e.setCancelled(true);

            e.getPlayer().getInventory().setItem(0, Inv0);
            e.getPlayer().getInventory().setItem(1, Inv1);
            e.getPlayer().getInventory().setItem(2, Inv2);
            e.getPlayer().getInventory().setItem(3, Inv3);
            e.getPlayer().getInventory().setItem(4, Inv4);
            e.getPlayer().getInventory().setItem(5, Inv5);
            e.getPlayer().getInventory().setItem(6, Inv6);
            e.getPlayer().getInventory().setItem(7, Inv7);
            e.getPlayer().getInventory().setItem(8, Inv8);
        } else if (e.getItemInHand().equals(cancelButton)){
            File playerInfoFolder = new File(plugin.getDataFolder(), "playerdata");
            File PlayerYAMLData = new File(playerInfoFolder.getPath(), ID + ".yml");

            Yaml yaml = new Yaml();

            try {
                playerInfo = yaml.load(new InputStreamReader(new FileInputStream(PlayerYAMLData)));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

            playerInfo.replace("Top-up", 0);
            playerInfo.replace("ClickTimes", 0);

            try {
                yaml.dump(playerInfo, new OutputStreamWriter(new FileOutputStream(PlayerYAMLData)));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            e.setCancelled(true);

            e.getPlayer().getInventory().setItem(0, Inv0);
            e.getPlayer().getInventory().setItem(1, Inv1);
            e.getPlayer().getInventory().setItem(2, Inv2);
            e.getPlayer().getInventory().setItem(3, Inv3);
            e.getPlayer().getInventory().setItem(4, Inv4);
            e.getPlayer().getInventory().setItem(5, Inv5);
            e.getPlayer().getInventory().setItem(6, Inv6);
            e.getPlayer().getInventory().setItem(7, Inv7);
            e.getPlayer().getInventory().setItem(8, Inv8);

            e.getPlayer().sendMessage(ChatColor.AQUA + "----StationXec Card Auto Top-up System----");
            e.getPlayer().sendMessage(ChatColor.AQUA + "Owner: " + ID);
            e.getPlayer().sendMessage(ChatColor.RED + "The trade is interrupted by User.");
        }

    }

    private String toString(Object o) {
        String res = toString(o);
        return res;
        }
    @EventHandler
    public void onSignCreation(SignChangeEvent e) {
        if (e.getBlock().getType().equals(Material.WALL_SIGN)) {
            if (e.getPlayer().isOp() || e.getPlayer().hasPermission("stationxec.setup.atum")) {
                if (e.getLine(0).equals("[Station]") && e.getLine(3).equals("[ATUM]")) {
                    e.setLine(0, ChatColor.AQUA + "[Station]");
                    e.setLine(3, ChatColor.AQUA + "[ATUM]");
                }
            }
        }
    }
}
