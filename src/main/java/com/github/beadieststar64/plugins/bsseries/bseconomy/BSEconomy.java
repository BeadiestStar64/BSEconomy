package com.github.beadieststar64.plugins.bsseries.bseconomy;

import com.github.beadieststar64.plugins.bsseries.bscore.FileManager;
import com.github.beadieststar64.plugins.bsseries.bscore.YamlReader;
import com.github.beadieststar64.plugins.bsseries.bseconomy.BSBankService.BankService;
import com.github.beadieststar64.plugins.bsseries.bseconomy.VaultService.VaultService;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class BSEconomy extends JavaPlugin {

    private static BSEconomy plugin;

    @Override
    public void onEnable() {

        plugin = this;

        // Plugin startup logic
        FileManager fm = new FileManager(this);
        fm.init(getDataFolder(), "RequestFiles.txt");

        YamlReader yr = new YamlReader(this);
        if(yr.getYaml().getBoolean("Use_Vault")) {
            getServer().getConsoleSender().sendMessage("[BCEconomy]" + ChatColor.AQUA + "Vault setup now...");
            VaultService vs = new VaultService(this);
            vs.vaultInit();
        }else{
            BankService bs = new BankService(this);
            bs.init();
        }

        getCommand("bank").setExecutor(new Commands(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static BSEconomy getInstance() {return plugin;}
}
