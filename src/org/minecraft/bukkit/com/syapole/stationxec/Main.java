package org.minecraft.bukkit.com.syapole.stationxec;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.minecraft.bukkit.com.syapole.stationxec.Controller.Controller;
import org.minecraft.bukkit.com.syapole.stationxec.Controller.XzHookAvoidListener;
import org.minecraft.bukkit.com.syapole.stationxec.StationListener.XcChecker;
import org.minecraft.bukkit.com.syapole.stationxec.StationListener.BlockDoors.FullHeight;
import org.minecraft.bukkit.com.syapole.stationxec.StationListener.BlockDoors.HalfHeight;

import net.milkbowl.vault.economy.Economy;
import org.minecraft.bukkit.com.syapole.stationxec.command.Tab;
import org.minecraft.bukkit.com.syapole.stationxec.command.getItem;

public class Main extends JavaPlugin{
    public final Logger logger = Logger.getLogger("Minecraft");
    private static Main instance;
    public static Economy econ = null;
    private final XcChecker pul = new XcChecker(this);
    private final HalfHeight hh = new HalfHeight(this);
    private final FullHeight fh = new FullHeight(this);
    private final Controller ctl = new Controller(this);
    private final XzHookAvoidListener hal = new XzHookAvoidListener(this);

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable(){
        instance = this;
        InitStorageFolder();
        getConfig().addDefault("CurrencyUnit", "Dollar");
        getConfig().addDefault("Language", "en_US");
        getConfig().options().copyDefaults(true);
        saveConfig();
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this.pul, this);
        pm.registerEvents(this.hh, this);
        pm.registerEvents(this.fh, this);
        pm.registerEvents(this.ctl, this);
        pm.registerEvents(this.hal, this);

        this.getCommand("controller").setExecutor(new getItem());
        this.getCommand("test").setTabCompleter(new Tab());

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
        InitStorageFolder();
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

    private void InitStorageFolder() {
        File playerDataFile = new File(Main.getInstance().getDataFolder(), "playerdata");
        File languageDataFile = new File(Main.getInstance().getDataFolder(), "lang");
        if (!playerDataFile.exists()) {
            playerDataFile.mkdirs();
        }
        if(!languageDataFile.exists()){
            languageDataFile.mkdirs();
        }

    }
}