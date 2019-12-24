package org.minecraft.bukkit.xyz.sinapole.stationxec;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;
import org.minecraft.bukkit.xyz.sinapole.stationxec.StationListener.BlockDoors.FullHeight;
import org.minecraft.bukkit.xyz.sinapole.stationxec.StationListener.BlockDoors.HalfHeight;
import org.minecraft.bukkit.xyz.sinapole.stationxec.StationListener.XcChecker;

public class Main extends JavaPlugin{
    public final Logger logger = Logger.getLogger("Minecraft");
    public static Economy econ = null;
    private final XcChecker pul = new XcChecker(this);
    private final HalfHeight hh = new HalfHeight(this);
    private final FullHeight fh = new FullHeight(this);

    @Override
    public void onEnable(){
        saveConfig();
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this.pul, this);
        pm.registerEvents(this.hh, this);
        pm.registerEvents(this.fh, this);
        if (!setupEconomy()) {
           logger.severe(ChatColor.AQUA + "StationXec" + ChatColor.RED + " >>> Can not work without Vault!");
            getServer().getPluginManager().disablePlugin(this);
        }
    }
    @Override
    public void onDisable(){
        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.info(ChatColor.AQUA + pdfFile.getName() + ChatColor.RED + " >>> Bye, see you next time!");
    }
    @Override
    public void onLoad(){
        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.info(ChatColor.AQUA + pdfFile.getName() + " >>> I am running in this server! The current version is " + pdfFile.getVersion());
    }
    private boolean setupEconomy(){
        if(getServer().getPluginManager().getPlugin("Vault") == null){
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if(rsp == null){
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}