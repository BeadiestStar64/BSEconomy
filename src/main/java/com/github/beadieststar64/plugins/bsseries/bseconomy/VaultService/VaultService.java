package com.github.beadieststar64.plugins.bsseries.bseconomy.VaultService;

import com.github.beadieststar64.plugins.bsseries.bscore.YamlReader;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.HashMap;
import java.util.UUID;

public class VaultService {

    private final Plugin plugin;
    private final VaultHook vh;

    private static Economy econ = null;
    public static VaultService getInstance;
    public static VaultImplementer getVIInstance;

    public final HashMap<UUID,Double> playerBank = new HashMap<>();
    public static boolean useVault;

    public VaultService(Plugin plugin) {
        this.plugin = plugin;
        getInstance = this;
        getVIInstance = new VaultImplementer(plugin);
        vh = new VaultHook(plugin);
        YamlReader config = new YamlReader(plugin);
        useVault = config.getYaml().getBoolean("Use_Vault");
    }

    public void vaultInit() {
        vh.hook();
    }

    public void vaultPurge() {
        vh.unhook();
    }

    private boolean setupEconomy() {
        if(Bukkit.getPluginManager().getPlugin("Vault") == null) {
            plugin.getLogger().severe("Vault not found!");
            return true;
            //error
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public static Economy getEconomy() {
        return econ;
    }
}
