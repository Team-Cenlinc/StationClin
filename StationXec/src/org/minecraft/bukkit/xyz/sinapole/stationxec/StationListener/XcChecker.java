package org.minecraft.bukkit.xyz.sinapole.stationxec.StationListener;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;
import org.minecraft.bukkit.xyz.sinapole.stationxec.Main;

public class XcChecker implements Listener {
    Main plugin;

    public XcChecker(Main checkerStatus) {
        plugin = checkerStatus;
    }

    @EventHandler
    public void onTouchButton(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getHand().equals(EquipmentSlot.HAND)) {
                if (e.getClickedBlock().getType().equals(Material.WALL_SIGN)) {
                    Sign s = (Sign) e.getClickedBlock().getState();
                    if (s.getLine(0).equals(ChatColor.AQUA + "[Station]") && s.getLine(3).equals(ChatColor.AQUA + "[Checker]")) {

                        if (e.getPlayer().isOp() || e.getPlayer().hasPermission("stationxec.use.checker")) {
                            Player p = e.getPlayer();
                            Location loc = e.getClickedBlock().getLocation();
                            if (!plugin.getConfig().contains(p.getName())) {
                                if (s.getLine(2).equals("<Enter>")) {
                                    String locXYZ = loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ();
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
                                    plugin.getConfig().set(p.getName(), locXYZ);
                                    plugin.saveConfig();
                                } else {
                                    p.sendMessage(ChatColor.AQUA + "StationXec" + ChatColor.RED + " >>> You can not enter the station again.");
                                }
                            } else if (plugin.getConfig().contains(p.getName()) && plugin.getConfig().getString(p.getName()) != null) {
                                String locXYZA = plugin.getConfig().getString(p.getName());
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
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y > ys) {
                                        int resulty = y - ys;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y == ys) {
                                        int resulty = 0;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
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
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y > ys) {
                                        int resulty = y - ys;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y == ys) {
                                        int resulty = 0;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
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
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y > ys) {
                                        int resulty = y - ys;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y == ys) {
                                        int resulty = 0;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
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
                                plugin.getConfig().set(p.getName(), null);
                                plugin.saveConfig();
                            }
                        } else if (s.getLine(0).equals(ChatColor.AQUA + "[Station]") && s.getLine(3).equals(ChatColor.AQUA + "[Checker]") && s.getLine(2).equals("<Default>")) {
                            Player p = e.getPlayer();
                            Location loc = e.getClickedBlock().getLocation();
                            if (!plugin.getConfig().contains(p.getName())) {
                                String locXYZ = loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ();
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
                                plugin.getConfig().set(p.getName(), locXYZ);
                                plugin.saveConfig();

                            } else if (plugin.getConfig().contains(p.getName()) && plugin.getConfig().getString(p.getName()) != null) {
                                String locXYZA = plugin.getConfig().getString(p.getName());
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
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y > ys) {
                                        int resulty = y - ys;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y == ys) {
                                        int resulty = 0;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
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
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y > ys) {
                                        int resulty = y - ys;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y == ys) {
                                        int resulty = 0;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
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
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y > ys) {
                                        int resulty = y - ys;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y == ys) {
                                        int resulty = 0;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
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
                                plugin.getConfig().set(p.getName(), null);
                                plugin.saveConfig();
                            }
                        } else if (s.getLine(0).equals(ChatColor.AQUA + "[Station]") && s.getLine(3).equals(ChatColor.AQUA + "[Checker]") && s.getLine(2).equals("<High Speed>")) {
                            Player p = e.getPlayer();
                            Location loc = e.getClickedBlock().getLocation();
                            if (!plugin.getConfig().contains(p.getName())) {
                                String locXYZ = loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ();
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
                                plugin.getConfig().set(p.getName(), locXYZ);
                                plugin.saveConfig();

                            } else if (plugin.getConfig().contains(p.getName()) && plugin.getConfig().getString(p.getName()) != null) {
                                String locXYZA = plugin.getConfig().getString(p.getName());
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
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y > ys) {
                                        int resulty = y - ys;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y == ys) {
                                        int resulty = 0;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
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
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y > ys) {
                                        int resulty = y - ys;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y == ys) {
                                        int resulty = 0;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
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
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y > ys) {
                                        int resulty = y - ys;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y == ys) {
                                        int resulty = 0;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
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
                                plugin.getConfig().set(p.getName(), null);
                                plugin.saveConfig();
                            }
                        } else if (s.getLine(0).equals(ChatColor.AQUA + "[Station]") && s.getLine(3).equals(ChatColor.AQUA + "[Checker]") && s.getLine(2).equals("<Custom Price>")) {
                            int Price = Integer.parseInt(s.getLine(1));
                            Player p = e.getPlayer();
                            Location loc = e.getClickedBlock().getLocation();
                            if (!plugin.getConfig().contains(p.getName())) {
                                String locXYZ = loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ();
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
                                plugin.getConfig().set(p.getName(), locXYZ);
                                plugin.saveConfig();

                            } else if (plugin.getConfig().contains(p.getName()) && plugin.getConfig().getString(p.getName()) != null) {
                                String locXYZA = plugin.getConfig().getString(p.getName());
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
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y > ys) {
                                        int resulty = y - ys;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y == ys) {
                                        int resulty = 0;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
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
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y > ys) {
                                        int resulty = y - ys;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y == ys) {
                                        int resulty = 0;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
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
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y > ys) {
                                        int resulty = y - ys;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        }
                                    } else if (y == ys) {
                                        int resulty = 0;
                                        if (zs > z) {
                                            int resultz = zs - z;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z > zs) {
                                            int resultz = z - zs;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
                                            }
                                        } else if (z == zs) {
                                            int resultz = 0;
                                            int result = resultx + resulty + resultz;
                                            int times = result / 100;
                                            if (times < 1) {
                                                Main.econ.withdrawPlayer(p, 5);
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 + " Dollars");
                                            } else {
                                                Main.econ.withdrawPlayer(p, 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)));
                                                p.sendMessage(ChatColor.AQUA + "StationXec >>> The journey fee is " + 5 * Integer.parseInt(new java.text.DecimalFormat("0").format(times)) + " Dollars");
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
                                plugin.getConfig().set(p.getName(), null);
                                plugin.saveConfig();
                            }
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
