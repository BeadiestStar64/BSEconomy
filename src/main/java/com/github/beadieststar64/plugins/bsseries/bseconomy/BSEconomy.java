package com.github.beadieststar64.plugins.bsseries.bseconomy;

import com.github.beadieststar64.plugins.bsseries.bscore.FileManager;
import com.github.beadieststar64.plugins.bsseries.bscore.YamlReader;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class BSEconomy extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        FileManager fm = new FileManager(this);
        fm.init(getDataFolder(), "RequestFiles.txt");

        YamlReader yr = new YamlReader(this);
        if(yr.getYaml().getBoolean("Use_Vault")) {
            getServer().getConsoleSender().sendMessage("[BCEconomy]" + ChatColor.AQUA + "Vault setup now...");
            VaultService vs = new VaultService(this);
            vs.vaultInit();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
