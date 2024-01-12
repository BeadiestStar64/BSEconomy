package com.github.beadieststar64.plugins.bsseries.bseconomy.VaultService;

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
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;

    private static VaultService instance;
    private static VaultImplementer vi;
    private VaultHook vh;

    public final HashMap<UUID,Double> playerBank = new HashMap<>();

    public VaultService(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        vi = new VaultImplementer();
        vh = new VaultHook(plugin);
    }

    public static VaultService getInstance() {
        return instance;
    }

    public static VaultImplementer getVIInstance(){
        return vi;
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

    private void setupChat() {
        RegisteredServiceProvider<Chat> rsp = plugin.getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
    }

    private void setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = plugin.getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Permission getPermissions() {
        return perms;
    }

    public static Chat getChat() {
        return chat;
    }
}
