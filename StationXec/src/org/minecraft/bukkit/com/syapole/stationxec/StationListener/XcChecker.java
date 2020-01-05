package org.minecraft.bukkit.com.syapole.stationxec.StationListener;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.minecraft.bukkit.com.syapole.stationxec.Main;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;

public class XcChecker implements Listener {
    private Main plugin;

    public XcChecker(Main checkerStatus) {
        plugin = checkerStatus;
    }

    private Map<String, Object> cardInfo;

    @EventHandler
    public void onTouchButton(PlayerInteractEvent e) {

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType().equals(Material.WALL_SIGN)) {

                    Sign s = (Sign) e.getClickedBlock().getState();
                    if (s.getLine(0).equals(ChatColor.AQUA + "[Station]") && s.getLine(3).equals(ChatColor.AQUA + "[Checker]")) {
                        String ID = e.getPlayer().getName();
                        File cardInfoFolder = new File(plugin.getDataFolder(), "carddata");
                        File cardInfoYAML = new File(cardInfoFolder.getPath(), ID + ".yml");

                        Yaml yaml = new Yaml();

                        try {
                            cardInfo = yaml.load(new InputStreamReader(new FileInputStream(cardInfoFolder)));
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }

                        String MD5 = (String) cardInfo.get("MD5");
                        int OldBalance = (int) cardInfo.get("OldBalance");
                        int Balance = (int) cardInfo.get("Balance");

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

                        ItemStack ICCard2 = new ItemStack(Material.NAME_TAG);
                        ItemMeta ICCardMeta2 = ICCard.getItemMeta();
                        ICCardMeta.setDisplayName(ChatColor.AQUA + "Transportation Card");
                        ArrayList<String> lore2 = new ArrayList<String>();
                        lore2.add("Balance: " + Balance);
                        lore2.add("Status: N");
                        lore2.add("Owner: " + ID);
                        lore2.add("MD5: " + MD5);
                        ICCardMeta2.setLore(lore2);
                        ICCard2.setItemMeta(ICCardMeta2);

                        String currencyUnit;
                        try {
                            currencyUnit = (String) plugin.getConfig().get("CurrencyUnit");
                        } catch(CommandException ce) {
                            currencyUnit = "Dollar";
                        }
                        if (e.getItem().equals(ICCard) | e.getItem().equals(ICCard2)) {


                            if (e.getPlayer().isOp() || e.getPlayer().hasPermission("stationxec.use.checker")) {
                            Player p = e.getPlayer();
                            Location loc = e.getClickedBlock().getLocation();
                            if (cardInfo.get("Location") == null) {
                                if (s.getLine(2).equals("<Enter>")) {
                                    String locXYZ = loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ();
                                    cardInfo.replace("Location", locXYZ);
                                    try {
                                        yaml.dump(cardInfo, new OutputStreamWriter(new FileOutputStream(cardInfoFolder)));
                                    } catch (FileNotFoundException ex) {
                                        ex.printStackTrace();
                                    }
                                    //BlockDelete
                                    Location locblock0 = e.getClickedBlock().getLocation().add(1, 0, 0);
                                    Location locblock1 = e.getClickedBlock().getLocation().add(0, 0, 1);
                                    Location locblock2 = e.getClickedBlock().getLocation().subtract(1, 0, 0);
                                    Location locblock3 = e.getClickedBlock().getLocation().subtract(0, 0, 1);
                                    if (locblock0.getBlock().getType().equals(Material.COBBLE_WALL)) {
                                        locblock0.getBlock().setType(Material.AIR);
                                        new BukkitRunnable() {
                                            @Override
                                            public void run() {
                                                locblock0.getBlock().setType(Material.COBBLE_WALL);
                                            }
                                        }.runTaskLater(this.plugin, 20 * 3);
                                    } else if (locblock1.getBlock().getType().equals(Material.COBBLE_WALL)) {
                                        locblock1.getBlock().setType(Material.AIR);
                                        new BukkitRunnable() {
                                            @Override
                                            public void run() {
                                                locblock1.getBlock().setType(Material.COBBLE_WALL);
                                            }
                                        }.runTaskLater(this.plugin, 20 * 3);
                                    } else if (locblock2.getBlock().getType().equals(Material.COBBLE_WALL)) {
                                        locblock2.getBlock().setType(Material.AIR);
                                        new BukkitRunnable() {
                                            @Override
                                            public void run() {
                                                locblock2.getBlock().setType(Material.COBBLE_WALL);
                                            }
                                        }.runTaskLater(this.plugin, 20 * 3);
                                    } else if (locblock3.getBlock().getType().equals(Material.COBBLE_WALL)) {
                                        locblock3.getBlock().setType(Material.AIR);
                                        new BukkitRunnable() {
                                            @Override
                                            public void run() {
                                                locblock3.getBlock().setType(Material.COBBLE_WALL);
                                            }
                                        }.runTaskLater(this.plugin, 20 * 3);
                                    }
                                    e.getClickedBlock().getWorld().playSound(e.getClickedBlock().getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 1);
                                } else {
                                    p.sendMessage(ChatColor.AQUA + "StationXec" + ChatColor.RED + " >>> You must enter a station first.");
                                }
                                ItemStack chICCard = new ItemStack(Material.NAME_TAG);
                                ItemMeta chICCardMeta = chICCard.getItemMeta();

                                chICCardMeta.setDisplayName(ChatColor.AQUA + "Transportation Card");

                                ArrayList<String> chlore = new ArrayList<String>();
                                chlore.add("Balance: " + Balance);
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


                            } else if (cardInfo.get("Location") != null) {
                                String locXYZA = (String) cardInfo.get("Location");
                                int xs = Integer.parseInt(locXYZA.split("/")[0]);
                                int ys = Integer.parseInt(locXYZA.split("/")[1]);
                                int zs = Integer.parseInt(locXYZA.split("/")[2]);
                                int x = loc.getBlockX();
                                int y = loc.getBlockY();
                                int z = loc.getBlockZ();
                                if (xs > x) {
                                    int resultx = xs - x;
                                    if (ys > y) {
                                        int resulty = ys - y;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        }
                                    } else if (y > ys) {
                                        int resulty = y - ys;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        }
                                    } else if (y == ys) {
                                        int resulty = 0;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        }
                                    }
                                } else if (x > xs) {
                                    int resultx = x - xs;
                                    if (ys > y) {
                                        int resulty = ys - y;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        }
                                    } else if (y > ys) {
                                        int resulty = y - ys;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        }
                                    } else if (y == ys) {
                                        int resulty = 0;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        }
                                    }
                                } else if (xs == x) {
                                    int resultx = 0;
                                    if (ys > y) {
                                        int resulty = ys - y;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        }
                                    } else if (y > ys) {
                                        int resulty = y - ys;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        }
                                    } else if (y == ys) {
                                        int resulty = 0;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                Main.econ.withdrawPlayer(p, CostFee);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                cardInfo.replace("Balance", Balance - 5);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                cardInfo.replace("Balance", Balance - CostFee);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                            }
                                        }
                                    }
                                }
                                Location locblock0 = e.getClickedBlock().getLocation().add(1, 0, 0);
                                Location locblock1 = e.getClickedBlock().getLocation().add(0, 0, 1);
                                Location locblock2 = e.getClickedBlock().getLocation().subtract(1, 0, 0);
                                Location locblock3 = e.getClickedBlock().getLocation().subtract(0, 0, 1);
                                if (locblock0.getBlock().getType().equals(Material.COBBLE_WALL)) {
                                    locblock0.getBlock().setType(Material.AIR);
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            locblock0.getBlock().setType(Material.COBBLE_WALL);
                                        }
                                    }.runTaskLater(this.plugin, 20 * 3);
                                } else if (locblock1.getBlock().getType().equals(Material.COBBLE_WALL)) {
                                    locblock1.getBlock().setType(Material.AIR);
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            locblock1.getBlock().setType(Material.COBBLE_WALL);
                                        }
                                    }.runTaskLater(this.plugin, 20 * 3);
                                } else if (locblock2.getBlock().getType().equals(Material.COBBLE_WALL)) {
                                    locblock2.getBlock().setType(Material.AIR);
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            locblock2.getBlock().setType(Material.COBBLE_WALL);
                                        }
                                    }.runTaskLater(this.plugin, 20 * 3);
                                } else if (locblock3.getBlock().getType().equals(Material.COBBLE_WALL)) {
                                    locblock3.getBlock().setType(Material.AIR);
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            locblock3.getBlock().setType(Material.COBBLE_WALL);
                                        }
                                    }.runTaskLater(this.plugin, 20 * 3);
                                }
                                e.getClickedBlock().getWorld().playSound(e.getClickedBlock().getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 1);
                                int NewBalance = (int) cardInfo.get("Balance");
                                ItemStack chICCard = new ItemStack(Material.NAME_TAG);
                                ItemMeta chICCardMeta = chICCard.getItemMeta();

                                chICCardMeta.setDisplayName(ChatColor.AQUA + "Transportation Card");

                                ArrayList<String> chlore = new ArrayList<String>();
                                chlore.add("Balance: " + NewBalance);
                                chlore.add("Status: N");
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
                                cardInfo.replace("Location", null);
                                try {
                                    yaml.dump(cardInfo, new OutputStreamWriter(new FileOutputStream(cardInfoFolder)));
                                } catch (FileNotFoundException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        } else if (s.getLine(0).equals(ChatColor.AQUA + "[Station]") && s.getLine(3).equals(ChatColor.AQUA + "[Checker]") && s.getLine(2).equals("<Custom Price>")) {
                            int Price = Integer.parseInt(s.getLine(1));
                            Player p = e.getPlayer();
                            Location loc = e.getClickedBlock().getLocation();
                            if (cardInfo.get("Location") == null) {
                                String locXYZ = loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ();
                                cardInfo.replace("Location", locXYZ);
                                try {
                                    yaml.dump(cardInfo, new OutputStreamWriter(new FileOutputStream(cardInfoFolder)));
                                } catch (FileNotFoundException ex) {
                                    ex.printStackTrace();
                                }
                                //BlockDelete
                                Location locblock0 = e.getClickedBlock().getLocation().add(1, 0, 0);
                                Location locblock1 = e.getClickedBlock().getLocation().add(0, 0, 1);
                                Location locblock2 = e.getClickedBlock().getLocation().subtract(1, 0, 0);
                                Location locblock3 = e.getClickedBlock().getLocation().subtract(0, 0, 1);
                                if (locblock0.getBlock().getType().equals(Material.COBBLE_WALL)) {
                                    locblock0.getBlock().setType(Material.AIR);
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            locblock0.getBlock().setType(Material.COBBLE_WALL);
                                        }
                                    }.runTaskLater(this.plugin, 20 * 3);
                                } else if (locblock1.getBlock().getType().equals(Material.COBBLE_WALL)) {
                                    locblock1.getBlock().setType(Material.AIR);
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            locblock1.getBlock().setType(Material.COBBLE_WALL);
                                        }
                                    }.runTaskLater(this.plugin, 20 * 3);
                                } else if (locblock2.getBlock().getType().equals(Material.COBBLE_WALL)) {
                                    locblock2.getBlock().setType(Material.AIR);
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            locblock2.getBlock().setType(Material.COBBLE_WALL);
                                        }
                                    }.runTaskLater(this.plugin, 20 * 3);
                                } else if (locblock3.getBlock().getType().equals(Material.COBBLE_WALL)) {
                                    locblock3.getBlock().setType(Material.AIR);
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            locblock3.getBlock().setType(Material.COBBLE_WALL);
                                        }
                                    }.runTaskLater(this.plugin, 20 * 3);
                                }
                                e.getClickedBlock().getWorld().playSound(e.getClickedBlock().getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 1);
                                ItemStack chICCard = new ItemStack(Material.NAME_TAG);
                                ItemMeta chICCardMeta = chICCard.getItemMeta();

                                chICCardMeta.setDisplayName(ChatColor.AQUA + "Transportation Card");

                                ArrayList<String> chlore = new ArrayList<String>();
                                chlore.add("Balance: " + Balance);
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
                            } else if (cardInfo.get("Location") != null) {
                                String locXYZA = (String) cardInfo.get("Location");
                                int xs = Integer.parseInt(locXYZA.split("/")[0]);
                                int ys = Integer.parseInt(locXYZA.split("/")[1]);
                                int zs = Integer.parseInt(locXYZA.split("/")[2]);
                                int x = loc.getBlockX();
                                int y = loc.getBlockY();
                                int z = loc.getBlockZ();
                                if (xs > x) {
                                    int resultx = xs - x;
                                    if (ys > y) {
                                        int resulty = ys - y;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        }
                                    } else if (y > ys) {
                                        int resulty = y - ys;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        }
                                    } else if (y == ys) {
                                        int resulty = 0;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        }
                                    }
                                } else if (x > xs) {
                                    int resultx = x - xs;
                                    if (ys > y) {
                                        int resulty = ys - y;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        }
                                    } else if (y > ys) {
                                        int resulty = y - ys;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        }
                                    } else if (y == ys) {
                                        int resulty = 0;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        }
                                    }
                                } else if (xs == x) {
                                    int resultx = 0;
                                    if (ys > y) {
                                        int resulty = ys - y;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        }
                                    } else if (y > ys) {
                                        int resulty = y - ys;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        }
                                    } else if (y == ys) {
                                        int resulty = 0;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                cardInfo.replace("Balance", Balance - Price);
                                                cardInfo.replace("OldBalance", Balance);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                cardInfo.replace("Balance", Balance - cost);
                                                cardInfo.replace("OldBalance", Balance);
                                            }
                                        }
                                    }
                                }
                                Location locblock0 = e.getClickedBlock().getLocation().add(1, 0, 0);
                                Location locblock1 = e.getClickedBlock().getLocation().add(0, 0, 1);
                                Location locblock2 = e.getClickedBlock().getLocation().subtract(1, 0, 0);
                                Location locblock3 = e.getClickedBlock().getLocation().subtract(0, 0, 1);
                                if (locblock0.getBlock().getType().equals(Material.COBBLE_WALL)) {
                                    locblock0.getBlock().setType(Material.AIR);
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            locblock0.getBlock().setType(Material.COBBLE_WALL);
                                        }
                                    }.runTaskLater(this.plugin, 20 * 3);
                                } else if (locblock1.getBlock().getType().equals(Material.COBBLE_WALL)) {
                                    locblock1.getBlock().setType(Material.AIR);
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            locblock1.getBlock().setType(Material.COBBLE_WALL);
                                        }
                                    }.runTaskLater(this.plugin, 20 * 3);
                                } else if (locblock2.getBlock().getType().equals(Material.COBBLE_WALL)) {
                                    locblock2.getBlock().setType(Material.AIR);
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            locblock2.getBlock().setType(Material.COBBLE_WALL);
                                        }
                                    }.runTaskLater(this.plugin, 20 * 3);
                                } else if (locblock3.getBlock().getType().equals(Material.COBBLE_WALL)) {
                                    locblock3.getBlock().setType(Material.AIR);
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            locblock3.getBlock().setType(Material.COBBLE_WALL);
                                        }
                                    }.runTaskLater(this.plugin, 20 * 3);
                                }
                                e.getClickedBlock().getWorld().playSound(e.getClickedBlock().getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 1);
                                int NewBalance = (int) cardInfo.get("Balance");
                                ItemStack chICCard = new ItemStack(Material.NAME_TAG);
                                ItemMeta chICCardMeta = chICCard.getItemMeta();

                                chICCardMeta.setDisplayName(ChatColor.AQUA + "Transportation Card");

                                ArrayList<String> chlore = new ArrayList<String>();
                                chlore.add("Balance: " + NewBalance);
                                chlore.add("Status: N");
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
                                cardInfo.replace("Location", null);
                                try {
                                    yaml.dump(cardInfo, new OutputStreamWriter(new FileOutputStream(cardInfoFolder)));
                                } catch (FileNotFoundException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }

                }
            }
        }

    private String toString(Object o) {
        String res = toString(o);
        return res;
    }


    @EventHandler
    public void onSignCreation(SignChangeEvent e) {
        if (e.getBlock().getType().equals(Material.WALL_SIGN)) {
            if (e.getPlayer().isOp() || e.getPlayer().hasPermission("stationxec.setup.checker")) {
                if (e.getLine(0).equals("[Station]") && e.getLine(3).equals("[Checker]")) {
                    e.setLine(0, ChatColor.AQUA + "[Station]");
                    e.setLine(3, ChatColor.AQUA + "[Checker]");
                }
            }
        }
    }
}
