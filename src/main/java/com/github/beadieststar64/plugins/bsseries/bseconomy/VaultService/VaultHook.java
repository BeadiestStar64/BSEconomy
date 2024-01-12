package com.github.beadieststar64.plugins.bsseries.bseconomy.VaultService;

import com.github.beadieststar64.plugins.bsseries.bseconomy.BSEconomy;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;

public class VaultHook {

    private final Plugin plugin;
    private final Economy econ;

    public VaultHook(Plugin plugin) {
        this.plugin = plugin;
        this.econ = VaultService.getVIInstance();
    }

    public void hook() {
        Bukkit.getServicesManager().register(Economy.class, this.econ, plugin, ServicePriority.Normal);
        Bukkit.getConsoleSender().sendMessage("[BSEconomy]" + ChatColor.GREEN + "VaultAPI hooked into " + ChatColor.AQUA + plugin.getName());
    }

    public void unhook() {
        Bukkit.getServicesManager().unregister(Economy.class, this.econ);
        Bukkit.getConsoleSender().sendMessage("[BSEconomy]" + ChatColor.RED + "VaultAPI unhooked from " + ChatColor.AQUA + plugin.getName());
    }
}
