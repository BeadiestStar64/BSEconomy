package com.github.beadieststar64.plugins.bsseries.bseconomy.BSBankService;

import com.github.beadieststar64.plugins.bsseries.bscore.FileManager;
import com.github.beadieststar64.plugins.bsseries.bscore.Translator;
import com.github.beadieststar64.plugins.bsseries.bscore.YamlReader;
import com.github.beadieststar64.plugins.bsseries.bseconomy.Wallet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class BankService {

    private final Plugin plugin;
    private final Translator ts;
    private final YamlReader config;
    private final YamlReader bank;

    public BankService(Plugin plugin) {
        this.plugin = plugin;
        config = new YamlReader(plugin);
        ts = new Translator(plugin, config.getString("Language"));
        bank = new YamlReader(plugin, config.getString("Yml_Folder"), "bank.yml");
    }

    public void init() {
        Bukkit.getConsoleSender().sendMessage("[BSEconomy]" + ChatColor.BLUE + ts.getTranslator("BSBankService_Initialize"));
        if(!new File(plugin.getDataFolder() + File.separator + config.getString("Yml_Folder"), "bank.yml").exists()) {
            FileManager fm = new FileManager(plugin);
            fm.createFile(new File(plugin.getDataFolder(), config.getString("Yml_Folder")), "bank.yml");
        }

        Wallet wallet = new Wallet(plugin);
        if(config.getYaml().getBoolean("Decimal_Balance")) {
            for(Player player : Bukkit.getServer().getOnlinePlayers()) {
                wallet.setDecimalWallet(player);
            }
        }else{
            for(Player player : Bukkit.getServer().getOnlinePlayers()) {
                wallet.setIntegerWallet(player, bank.getYaml().getInt(player.getUniqueId() + ".Bank_Balance"));
            }
        }
    }
}
