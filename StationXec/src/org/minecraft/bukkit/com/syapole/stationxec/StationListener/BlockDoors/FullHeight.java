package org.minecraft.bukkit.com.syapole.stationxec.StationListener.BlockDoors;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;
import org.minecraft.bukkit.com.syapole.stationxec.Main;

public class FullHeight implements Listener {
    Main plugin;

    public FullHeight(Main XecHyperloo) {
        plugin = XecHyperloo;
    }

    @EventHandler
    public void onHalfHeightBlockDoor(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getHand().equals(EquipmentSlot.HAND)) {
                if (e.getClickedBlock().getType().equals(Material.WALL_SIGN)) {
                    Sign s = (Sign) e.getClickedBlock().getState();
                    if (s.getLine(0).equals(ChatColor.AQUA + "[Station]") && s.getLine(2).equals("<Full Height>") && s.getLine(3).equals(ChatColor.AQUA + "[Block Door]")) {
                        int ShutdoorTime = Integer.parseInt(s.getLine(1));
                        Block b = e.getClickedBlock();
                        Location loc1 = e.getClickedBlock().getLocation().add(1, 0, 0);
                        Location loc2 = e.getClickedBlock().getLocation().add(0, 0, 1);
                        Location loc3 = e.getClickedBlock().getLocation().subtract(1, 0, 0);
                        Location loc4 = e.getClickedBlock().getLocation().subtract(0, 0, 1);
                        if (loc1.getBlock().getType().equals(Material.PRISMARINE)) {
                            b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    b.getWorld().playSound(b.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 2);
                                    for (int i = 1; i < 150; i++) {
                                        if (b.getRelative(i, 0, 0).getType().equals(Material.THIN_GLASS)) {
                                            b.getRelative(i, 0, 0).setType(Material.AIR);
                                            Location UpdateCurrent = b.getRelative(i, -1, 0).getLocation();
                                            if (b.getRelative(i, 1, 0).getType().equals(Material.THIN_GLASS)) {
                                                b.getRelative(i, 1, 0).setType(Material.AIR);
                                            }
                                        } else if (b.getRelative(i, 0, 0).getType().equals(Material.IRON_BLOCK)) {
                                            break;
                                        } else if (b.getRelative(i, 1, 0).getType().equals(Material.IRON_BLOCK)) {
                                            break;

                                        }
                                    }
                                }
                            }.runTaskLater(this.plugin, 20);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                                    }
                                }.runTaskLater(this.plugin, (ShutdoorTime - 1) * 20);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                                    }
                                }.runTaskLater(this.plugin, (ShutdoorTime - 1) * 20 + 10);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                                    }
                                }.runTaskLater(this.plugin, ShutdoorTime * 20);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                                    }
                                }.runTaskLater(this.plugin, ShutdoorTime * 20 + 10);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        b.getWorld().playSound(b.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 2);
                                        for (int i = 1; i < 150; i++) {
                                            if (b.getRelative(i, 0, 0).getType().equals(Material.AIR)) {
                                                b.getRelative(i, 0, 0).setType(Material.THIN_GLASS);
                                                if (b.getRelative(i, 1, 0).getType().equals(Material.AIR)) {
                                                    b.getRelative(i, 1, 0).setType(Material.THIN_GLASS);
                                                }
                                            } else if (b.getRelative(i, 0, 0).getType().equals(Material.IRON_BLOCK)) {
                                                break;
                                            } else if (b.getRelative(i, 1, 0).getType().equals(Material.IRON_BLOCK)) {
                                                break;
                                            }
                                        }
                                    }
                                }.runTaskLater(this.plugin, ShutdoorTime * 20 + 20);
                        } else if (loc3.getBlock().getType().equals(Material.PRISMARINE)) {
                            b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    b.getWorld().playSound(b.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 2);
                                    for (int i = -1; i > -150; i--) {
                                        if (b.getRelative(i, 0, 0).getType().equals(Material.THIN_GLASS)) {
                                            b.getRelative(i, 0, 0).setType(Material.AIR);
                                            if (b.getRelative(i, 1, 0).getType().equals(Material.THIN_GLASS)) {
                                                b.getRelative(i, 1, 0).setType(Material.AIR);
                                            }
                                        } else if (b.getRelative(i, 0, 0).getType().equals(Material.IRON_BLOCK)) {
                                            break;
                                        } else if (b.getRelative(i, 1, 0).getType().equals(Material.IRON_BLOCK)) {
                                            break;

                                        }
                                    }
                                }
                            }.runTaskLater(this.plugin, 20);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                                }
                            }.runTaskLater(this.plugin, (ShutdoorTime - 1) * 20);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                                }
                            }.runTaskLater(this.plugin, (ShutdoorTime - 1) * 20 + 10);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                                }
                            }.runTaskLater(this.plugin, ShutdoorTime * 20);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                                }
                            }.runTaskLater(this.plugin, ShutdoorTime * 20 + 10);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    b.getWorld().playSound(b.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 2);
                                    for (int i = -1; i > -150; i--) {
                                        if (b.getRelative(i, 0, 0).getType().equals(Material.AIR)) {
                                            b.getRelative(i, 0, 0).setType(Material.THIN_GLASS);
                                            if (b.getRelative(i, 1, 0).getType().equals(Material.AIR)) {
                                                b.getRelative(i, 1, 0).setType(Material.THIN_GLASS);
                                            }
                                        } else if (b.getRelative(i, 0, 0).getType().equals(Material.IRON_BLOCK)) {
                                            break;
                                        } else if (b.getRelative(i, 1, 0).getType().equals(Material.IRON_BLOCK)) {
                                            break;
                                        }
                                    }
                                }
                            }.runTaskLater(this.plugin, ShutdoorTime * 20 + 20);
                        } else if (loc2.getBlock().getType().equals(Material.PRISMARINE)) {
                            b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    b.getWorld().playSound(b.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 2);
                                    for (int i = 1; i < 150; i++) {
                                        if (b.getRelative(0, 0, i).getType().equals(Material.THIN_GLASS)) {
                                            b.getRelative(0, 0, i).setType(Material.AIR);
                                            if (b.getRelative(0, 1, i).getType().equals(Material.THIN_GLASS)) {
                                                b.getRelative(0, 1, i).setType(Material.AIR);
                                            }
                                        } else if (b.getRelative(0, 0, i).getType().equals(Material.IRON_BLOCK)) {
                                            break;
                                        } else if (b.getRelative(0, 1, i).getType().equals(Material.IRON_BLOCK)) {
                                            break;

                                        }
                                    }
                                }
                            }.runTaskLater(this.plugin, 20);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                                }
                            }.runTaskLater(this.plugin, (ShutdoorTime - 1) * 20);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                                }
                            }.runTaskLater(this.plugin, (ShutdoorTime - 1) * 20 + 10);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                                }
                            }.runTaskLater(this.plugin, ShutdoorTime * 20);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                                }
                            }.runTaskLater(this.plugin, ShutdoorTime * 20 + 10);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    b.getWorld().playSound(b.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 2);
                                    for (int i = 1; i < 150; i++) {
                                        if (b.getRelative(0, 0, i).getType().equals(Material.AIR)) {
                                            b.getRelative(0, 0, i).setType(Material.THIN_GLASS);
                                            if (b.getRelative(0, 1, i).getType().equals(Material.AIR)) {
                                                b.getRelative(0, 1, i).setType(Material.THIN_GLASS);
                                            }
                                        } else if (b.getRelative(0, 0, i).getType().equals(Material.IRON_BLOCK)) {
                                            break;
                                        } else if (b.getRelative(0, 1, i).getType().equals(Material.IRON_BLOCK)) {
                                            break;
                                        }
                                    }
                                }
                            }.runTaskLater(this.plugin, ShutdoorTime * 20 + 20);
                        } else if (loc4.getBlock().getType().equals(Material.PRISMARINE)) {
                            b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    b.getWorld().playSound(b.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 2);
                                    for (int i = -1; i > -150; i--) {
                                        if (b.getRelative(0, 0, i).getType().equals(Material.THIN_GLASS)) {
                                            b.getRelative(0, 0, i).setType(Material.AIR);
                                            if (b.getRelative(0, 1, i).getType().equals(Material.THIN_GLASS)) {
                                                b.getRelative(0, 1, i).setType(Material.AIR);
                                            }
                                        } else if (b.getRelative(0, 0, i).getType().equals(Material.IRON_BLOCK)) {
                                            break;
                                        } else if (b.getRelative(0, 1, i).getType().equals(Material.IRON_BLOCK)) {
                                            break;

                                        }
                                    }
                                }
                            }.runTaskLater(this.plugin, 20);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                                }
                            }.runTaskLater(this.plugin, (ShutdoorTime - 1) * 20);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                                }
                            }.runTaskLater(this.plugin, (ShutdoorTime - 1) * 20 + 10);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                                }
                            }.runTaskLater(this.plugin, ShutdoorTime * 20);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                                }
                            }.runTaskLater(this.plugin, ShutdoorTime * 20 + 10);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    b.getWorld().playSound(b.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 2);
                                    for (int i = -1; i > -150; i--) {
                                        if (b.getRelative(0, 0, i).getType().equals(Material.AIR)) {
                                            b.getRelative(0, 0, i).setType(Material.THIN_GLASS);
                                            if (b.getRelative(0, 1, i).getType().equals(Material.AIR)) {
                                                b.getRelative(0, 1, i).setType(Material.THIN_GLASS);
                                            }
                                        } else if (b.getRelative(0, 0, i).getType().equals(Material.IRON_BLOCK)) {
                                            break;
                                        } else if (b.getRelative(0, 1, i).getType().equals(Material.IRON_BLOCK)) {
                                            break;
                                        }
                                    }
                                }
                            }.runTaskLater(this.plugin, ShutdoorTime * 20 + 20);
                        }
                    }
                }
            }
        }
    }

            @EventHandler
            public void onSignCreation (SignChangeEvent e){
                if (e.getBlock().getType().equals(Material.WALL_SIGN)) {
                    if (e.getPlayer().isOp() || e.getPlayer().hasPermission("stationxec.setup.blockdoor")) {
                        if (e.getLine(0).equals("[Station]") && e.getLine(3).equals("[Block Door]")) {
                            e.setLine(0, ChatColor.AQUA + "[Station]");
                            e.setLine(3, ChatColor.AQUA + "[Block Door]");
                        }
                    }
                }
            }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockRedstone(BlockRedstoneEvent e) {
        if (e.getBlock().getType().equals(Material.REDSTONE_WIRE) && e.getOldCurrent() == 0 && e.getBlock().getLocation().subtract(0, 2, 0).getBlock().getType().equals(Material.REDSTONE_TORCH_ON)) {
            if(e.getBlock().getLocation().add(1, 0, 1).getBlock().getType().equals(Material.WALL_SIGN)){
                openDoorFull(e.getBlock().getLocation().add(1, 0, 1));
            }else if(e.getBlock().getLocation().add(1, 0, 0).subtract(0,0,1).getBlock().getType().equals(Material.WALL_SIGN)){
                openDoorFull(e.getBlock().getLocation().add(1, 0, 0).subtract(0,0,1));
            }else if(e.getBlock().getLocation().subtract(1, 0, 0).add(0,0,1).getBlock().getType().equals(Material.WALL_SIGN)){
                openDoorFull(e.getBlock().getLocation().subtract(1, 0, 0).add(0,0,1));
            }else if(e.getBlock().getLocation().subtract(1,0,1).getBlock().getType().equals(Material.WALL_SIGN)){
                openDoorFull(e.getBlock().getLocation().subtract(1,0,1));
            }else if(e.getBlock().getLocation().subtract(2,0,0).getBlock().getType().equals(Material.WALL_SIGN)){
                openDoorFull(e.getBlock().getLocation().subtract(2,0,0));
            }else if(e.getBlock().getLocation().add(2,0,0).getBlock().getType().equals(Material.WALL_SIGN)){
                openDoorFull(e.getBlock().getLocation().add(2,0,0));
            }else if(e.getBlock().getLocation().subtract(0,0,2).getBlock().getType().equals(Material.WALL_SIGN)){
                openDoorFull(e.getBlock().getLocation().subtract(0,0,2));
            }else if(e.getBlock().getLocation().add(0,0,2).getBlock().getType().equals(Material.WALL_SIGN)){
                openDoorFull(e.getBlock().getLocation().add(0,0,2));
            }
        }
    }
    public void openDoorFull(Location Sign_Loc){
        Sign s = (Sign) Sign_Loc.getBlock().getState();
        if (s.getLine(0).equals(ChatColor.AQUA + "[Station]") && s.getLine(2).equals("<Full Height>") && s.getLine(3).equals(ChatColor.AQUA + "[Block Door]")) {
            int ShutdoorTime = Integer.parseInt(s.getLine(1));
            Block b = Sign_Loc.getBlock();
            Location loc1 = Sign_Loc.getBlock().getLocation().add(1, 0, 0);
            Location loc2 = Sign_Loc.getBlock().getLocation().add(0, 0, 1);
            Location loc3 = Sign_Loc.getBlock().getLocation().subtract(1, 0, 0);
            Location loc4 = Sign_Loc.getBlock().getLocation().subtract(0, 0, 1);
            if (loc1.getBlock().getType().equals(Material.PRISMARINE)) {
                b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 2);
                        for (int i = 1; i < 150; i++) {
                            if (b.getRelative(i, 0, 0).getType().equals(Material.THIN_GLASS)) {
                                b.getRelative(i, 0, 0).setType(Material.AIR);
                                if (b.getRelative(i, 1, 0).getType().equals(Material.THIN_GLASS)) {
                                    b.getRelative(i, 1, 0).setType(Material.AIR);
                                }
                            } else if (b.getRelative(i, 0, 0).getType().equals(Material.IRON_BLOCK)) {
                                break;
                            } else if (b.getRelative(i, 1, 0).getType().equals(Material.IRON_BLOCK)) {
                                break;

                            }
                        }
                    }
                }.runTaskLater(this.plugin, 20);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                    }
                }.runTaskLater(this.plugin, (ShutdoorTime - 1) * 20);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                    }
                }.runTaskLater(this.plugin, (ShutdoorTime - 1) * 20 + 10);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                    }
                }.runTaskLater(this.plugin, ShutdoorTime * 20);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                    }
                }.runTaskLater(this.plugin, ShutdoorTime * 20 + 10);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 2);
                        for (int i = 1; i < 150; i++) {
                            if (b.getRelative(i, 0, 0).getType().equals(Material.AIR)) {
                                b.getRelative(i, 0, 0).setType(Material.THIN_GLASS);
                                if (b.getRelative(i, 1, 0).getType().equals(Material.AIR)) {
                                    b.getRelative(i, 1, 0).setType(Material.THIN_GLASS);
                                }
                            } else if (b.getRelative(i, 0, 0).getType().equals(Material.IRON_BLOCK)) {
                                break;
                            } else if (b.getRelative(i, 1, 0).getType().equals(Material.IRON_BLOCK)) {
                                break;
                            }
                        }
                    }
                }.runTaskLater(this.plugin, ShutdoorTime * 20 + 20);
            } else if (loc3.getBlock().getType().equals(Material.PRISMARINE)) {
                b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 2);
                        for (int i = -1; i > -150; i--) {
                            if (b.getRelative(i, 0, 0).getType().equals(Material.THIN_GLASS)) {
                                b.getRelative(i, 0, 0).setType(Material.AIR);
                                if (b.getRelative(i, 1, 0).getType().equals(Material.THIN_GLASS)) {
                                    b.getRelative(i, 1, 0).setType(Material.AIR);
                                }
                            } else if (b.getRelative(i, 0, 0).getType().equals(Material.IRON_BLOCK)) {
                                break;
                            } else if (b.getRelative(i, 1, 0).getType().equals(Material.IRON_BLOCK)) {
                                break;

                            }
                        }
                    }
                }.runTaskLater(this.plugin, 20);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                    }
                }.runTaskLater(this.plugin, (ShutdoorTime - 1) * 20);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                    }
                }.runTaskLater(this.plugin, (ShutdoorTime - 1) * 20 + 10);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                    }
                }.runTaskLater(this.plugin, ShutdoorTime * 20);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                    }
                }.runTaskLater(this.plugin, ShutdoorTime * 20 + 10);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 2);
                        for (int i = -1; i > -150; i--) {
                            if (b.getRelative(i, 0, 0).getType().equals(Material.AIR)) {
                                b.getRelative(i, 0, 0).setType(Material.THIN_GLASS);
                                if (b.getRelative(i, 1, 0).getType().equals(Material.AIR)) {
                                    b.getRelative(i, 1, 0).setType(Material.THIN_GLASS);
                                }
                            } else if (b.getRelative(i, 0, 0).getType().equals(Material.IRON_BLOCK)) {
                                break;
                            } else if (b.getRelative(i, 1, 0).getType().equals(Material.IRON_BLOCK)) {
                                break;
                            }
                        }
                    }
                }.runTaskLater(this.plugin, ShutdoorTime * 20 + 20);
            } else if (loc2.getBlock().getType().equals(Material.PRISMARINE)) {
                b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 2);
                        for (int i = 1; i < 150; i++) {
                            if (b.getRelative(0, 0, i).getType().equals(Material.THIN_GLASS)) {
                                b.getRelative(0, 0, i).setType(Material.AIR);
                                if (b.getRelative(0, 1, i).getType().equals(Material.THIN_GLASS)) {
                                    b.getRelative(0, 1, i).setType(Material.AIR);
                                }
                            } else if (b.getRelative(0, 0, i).getType().equals(Material.IRON_BLOCK)) {
                                break;
                            } else if (b.getRelative(0, 1, i).getType().equals(Material.IRON_BLOCK)) {
                                break;

                            }
                        }
                    }
                }.runTaskLater(this.plugin, 20);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                    }
                }.runTaskLater(this.plugin, (ShutdoorTime - 1) * 20);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                    }
                }.runTaskLater(this.plugin, (ShutdoorTime - 1) * 20 + 10);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                    }
                }.runTaskLater(this.plugin, ShutdoorTime * 20);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                    }
                }.runTaskLater(this.plugin, ShutdoorTime * 20 + 10);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 2);
                        for (int i = 1; i < 150; i++) {
                            if (b.getRelative(0, 0, i).getType().equals(Material.AIR)) {
                                b.getRelative(0, 0, i).setType(Material.THIN_GLASS);
                                if (b.getRelative(0, 1, i).getType().equals(Material.AIR)) {
                                    b.getRelative(0, 1, i).setType(Material.THIN_GLASS);
                                }
                            } else if (b.getRelative(0, 0, i).getType().equals(Material.IRON_BLOCK)) {
                                break;
                            } else if (b.getRelative(0, 1, i).getType().equals(Material.IRON_BLOCK)) {
                                break;
                            }
                        }
                    }
                }.runTaskLater(this.plugin, ShutdoorTime * 20 + 20);
            } else if (loc4.getBlock().getType().equals(Material.PRISMARINE)) {
                b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 2);
                        for (int i = -1; i > -150; i--) {
                            if (b.getRelative(0, 0, i).getType().equals(Material.THIN_GLASS)) {
                                b.getRelative(0, 0, i).setType(Material.AIR);
                                if (b.getRelative(0, 1, i).getType().equals(Material.THIN_GLASS)) {
                                    b.getRelative(0, 1, i).setType(Material.AIR);
                                }
                            } else if (b.getRelative(0, 0, i).getType().equals(Material.IRON_BLOCK)) {
                                break;
                            } else if (b.getRelative(0, 1, i).getType().equals(Material.IRON_BLOCK)) {
                                break;

                            }
                        }
                    }
                }.runTaskLater(this.plugin, 20);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                    }
                }.runTaskLater(this.plugin, (ShutdoorTime - 1) * 20);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                    }
                }.runTaskLater(this.plugin, (ShutdoorTime - 1) * 20 + 10);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                    }
                }.runTaskLater(this.plugin, ShutdoorTime * 20);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
                    }
                }.runTaskLater(this.plugin, ShutdoorTime * 20 + 10);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.getWorld().playSound(b.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 2);
                        for (int i = -1; i > -150; i--) {
                            if (b.getRelative(0, 0, i).getType().equals(Material.AIR)) {
                                b.getRelative(0, 0, i).setType(Material.THIN_GLASS);
                                if (b.getRelative(0, 1, i).getType().equals(Material.AIR)) {
                                    b.getRelative(0, 1, i).setType(Material.THIN_GLASS);
                                }
                            } else if (b.getRelative(0, 0, i).getType().equals(Material.IRON_BLOCK)) {
                                break;
                            } else if (b.getRelative(0, 1, i).getType().equals(Material.IRON_BLOCK)) {
                                break;
                            }
                        }
                    }
                }.runTaskLater(this.plugin, ShutdoorTime * 20 + 20);
            }
        }
    }
}

