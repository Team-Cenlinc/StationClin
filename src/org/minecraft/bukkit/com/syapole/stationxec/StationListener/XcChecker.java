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
import org.bukkit.scheduler.BukkitRunnable;
import org.minecraft.bukkit.com.syapole.stationxec.Main;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;

import static org.minecraft.bukkit.com.syapole.stationxec.Main.econ;

public class XcChecker implements Listener {
    private Main plugin;

    public XcChecker(Main checkerStatus) {
        plugin = checkerStatus;
    }

    private Map<String, Object> playerInfo;
    private Yaml yaml = new Yaml();
    private String currencyUnit;
    @EventHandler
    public void onTouchButton(PlayerInteractEvent e) {
        String ID = e.getPlayer().getName();
        File playerDataFile = new File(Main.getInstance().getDataFolder(), "playerdata");
        File playerInfoYAML = new File(playerDataFile.getPath(), ID + ".yml");
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType().equals(Material.WALL_SIGN)) {

                    Sign s = (Sign) e.getClickedBlock().getState();
                    if (s.getLine(0).equals(ChatColor.AQUA + "[Station]") && s.getLine(3).equals(ChatColor.AQUA + "[Checker]")) {


                        try {
                            currencyUnit = (String) plugin.getConfig().get("CurrencyUnit");
                        } catch(CommandException ce) {
                            currencyUnit = "Dollar";
                        }


                            if (e.getPlayer().isOp() || e.getPlayer().hasPermission("stationxec.use.checker")) {
                            Player p = e.getPlayer();
                            Location loc = e.getClickedBlock().getLocation();
                            if (playerInfo.get("Location") == null) {
                                if (s.getLine(2).equals("<Enter>")) {
                                    String locXYZ = loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ();
                                    playerInfo.replace("Location", locXYZ);
                                    try {
                                        yaml.dump(playerInfo, new OutputStreamWriter(new FileOutputStream(playerInfoYAML)));
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

                            } else if (playerInfo.get("Location") != null) {
                                String locXYZA = (String) playerInfo.get("Location");
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
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
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
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
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
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
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
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
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
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
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
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
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
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
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
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                                if (times < 1) {
                                                    p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                    econ.withdrawPlayer(p, 5);
                                                } else {
                                                    int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                    p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                    econ.withdrawPlayer(p, CostFee);
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
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + currencyUnit);
                                                econ.withdrawPlayer(p, 5);
                                            } else {
                                                int CostFee = 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + CostFee + currencyUnit);
                                                econ.withdrawPlayer(p, CostFee);
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

                                playerInfo.replace("Location", null);
                                try {
                                    yaml.dump(playerInfo, new OutputStreamWriter(new FileOutputStream(playerInfoYAML)));
                                } catch (FileNotFoundException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        } else if (s.getLine(0).equals(ChatColor.AQUA + "[Station]") && s.getLine(3).equals(ChatColor.AQUA + "[Checker]") && s.getLine(2).equals("<Custom Price>")) {
                            int Price = Integer.parseInt(s.getLine(1));
                            Player p = e.getPlayer();
                            Location loc = e.getClickedBlock().getLocation();
                            if (playerInfo.get("Location") == null) {
                                if (s.getLine(2).equals("<Enter>")) {
                                    String locXYZ = loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ();
                                    playerInfo.replace("Location", locXYZ);
                                    try {
                                        yaml.dump(playerInfo, new OutputStreamWriter(new FileOutputStream(playerInfoYAML)));
                                    } catch (FileNotFoundException ex) {
                                        ex.printStackTrace();
                                    }
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
                            } else if (playerInfo.get("Location") != null) {
                                String locXYZA = (String) playerInfo.get("Location");
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
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
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
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
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
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
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
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
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
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
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
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
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
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
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
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
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
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                String money = s.getLine(1);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + money + currencyUnit);
                                                econ.withdrawPlayer(p, Price);
                                            } else {
                                                int cost = Price * Integer.parseInt(new java.text.DecimalFormat("0").format(times));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + cost + currencyUnit);
                                                econ.withdrawPlayer(p, cost);
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
                                playerInfo.replace("Location", null);
                                try {
                                    yaml.dump(playerInfo, new OutputStreamWriter(new FileOutputStream(playerInfoYAML)));
                                } catch (FileNotFoundException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            }
                        }
                    }

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
