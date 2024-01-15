package com.github.beadieststar64.plugins.bsseries.bseconomy;

import com.github.beadieststar64.plugins.bsseries.bscore.YamlReader;
import com.github.beadieststar64.plugins.bsseries.bseconomy.BSBankService.BankService;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class Wallet {

    private final Plugin plugin;
    private final YamlReader by;

    private final HashMap<UUID, Integer> integerWallet = new HashMap<>();
    private final HashMap<UUID, Double> decimalWallet = new HashMap<>();

    public Wallet(Plugin plugin) {
        this.plugin = plugin;
        YamlReader config = new YamlReader(plugin);
        by = new YamlReader(plugin, config.getString("Yml_Folder"), "bank.yml");
    }

    public void setIntegerWallet(Player player, int v) {
        YamlReader config = new YamlReader(plugin);
        File file = new File(plugin.getDataFolder() + File.separator + config.getString("Yml_Folder"), "bank.yml");
        if(!file.exists()) {
            BankService bs = new BankService(plugin);
            bs.initialize();
        }
        if(!by.getYaml().contains(player.getUniqueId() + ".Bank_Balance")) {
            by.getYaml().set(player.getUniqueId() + ".MCID", player.getName());
            by.getYaml().set(player.getUniqueId() + ".Bank_Balance", config.getYaml().getInt("Initial_Holdings"));
            by.saveConfig();
        }

        integerWallet.put(player.getUniqueId(), v);
        by.getYaml().set(player.getUniqueId() + ".Bank_Balance", v);
        by.saveConfig();

        player.sendMessage(player.getName() + "さんの残高を" + v + config.getString("Currency") + "に設定しました");
    }

    public void silentSetIntegerWallet(Player player, int v) {
        YamlReader config = new YamlReader(plugin);
        File file = new File(plugin.getDataFolder() + File.separator + config.getString("Yml_Folder"), "bank.yml");
        if(!file.exists()) {
            BankService bs = new BankService(plugin);
            bs.initialize();
        }
        if(!by.getYaml().contains(player.getUniqueId() + ".Bank_Balance")) {
            by.getYaml().set(player.getUniqueId() + ".MCID", player.getName());
            by.getYaml().set(player.getUniqueId() + ".Bank_Balance", config.getYaml().getInt("Initial_Holdings"));
            by.saveConfig();
        }

        integerWallet.put(player.getUniqueId(), v);
        by.getYaml().set(player.getUniqueId() + ".Bank_Balance", v);
        by.saveConfig();
    }

    public int getIntegerWallet(Player player) {
        if(integerWallet.isEmpty()) {
            return by.getYaml().getInt(player.getUniqueId() + ".Bank_Balance");
        }
        return integerWallet.get(player.getUniqueId());
    }

    public void setDecimalWallet(Player player) {
        YamlReader config = new YamlReader(plugin);
        File file = new File(new File(plugin.getDataFolder(), config.getString("Yml_Folder")), "bank.yml");
        if(!file.exists()) {
            BankService bs = new BankService(plugin);
            bs.initialize();
        }
        YamlReader bank = new YamlReader(plugin, config.getString("Yml_Folder"), "bank.yml");

        decimalWallet.put(player.getUniqueId(), bank.getYaml().getDouble(player.getUniqueId() + ".Bank_Balance"));
    }

    public double getDecimalWallet(Player player) {
        if(decimalWallet.isEmpty()) {
            return by.getYaml().getDouble(player.getUniqueId() + ".Bank_Balance");
        }
        return decimalWallet.get(player.getUniqueId());
    }
}
