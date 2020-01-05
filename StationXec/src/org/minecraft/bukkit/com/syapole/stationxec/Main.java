package org.minecraft.bukkit.com.syapole.stationxec;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;
import org.minecraft.bukkit.com.syapole.stationxec.Command.Refound;
import org.minecraft.bukkit.com.syapole.stationxec.Command.getItem;
import org.minecraft.bukkit.com.syapole.stationxec.ICCard.XcAutoTUM;
import org.minecraft.bukkit.com.syapole.stationxec.StationListener.BlockDoors.FullHeight;
import org.minecraft.bukkit.com.syapole.stationxec.StationListener.BlockDoors.HalfHeight;
import org.minecraft.bukkit.com.syapole.stationxec.StationListener.XcChecker;

public class Main extends JavaPlugin{
    public final Logger logger = Logger.getLogger("Minecraft");
    private static Main instance;
    public static Economy econ = null;
    private final XcChecker pul = new XcChecker(this);
    private final HalfHeight hh = new HalfHeight(this);
    private final FullHeight fh = new FullHeight(this);
    private final XcAutoTUM atum = new XcAutoTUM(this);

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable(){
        instance = this;
        InitStorageFolder();
        getConfig().addDefault("CurrencyUnit", "Dollar");
        getConfig().addDefault("Language", "zh_CH");
        getConfig().options().copyDefaults(true);
        saveConfig();
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this.pul, this);
        pm.registerEvents(this.hh, this);
        pm.registerEvents(this.fh, this);
        pm.registerEvents(this.atum, this);

        this.getCommand("card").setExecutor(new getItem(this));
        this.getCommand("refound").setExecutor(new Refound(this));
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
        File cardDataFile = new File(Main.getInstance().getDataFolder(), "carddata");
        File languageDataFile = new File(Main.getInstance().getDataFolder(), "lang");
        if (!playerDataFile.exists()) {
            playerDataFile.mkdir();
        }
        if(!cardDataFile.exists()){
            cardDataFile.mkdir();
        }
        if(!languageDataFile.exists()){
            languageDataFile.mkdir();
        }

    }
}