package com.github.beadieststar64.plugins.bsseries.bseconomy;

import com.github.beadieststar64.plugins.bsseries.bseconomy.Utils.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class BSEconomy extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        FileManager fm = new FileManager(this);
        fm.init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
