package com.github.beadieststar64.plugins.bsseries.bseconomy.BSBankService;

import com.github.beadieststar64.plugins.bsseries.bscore.FileManager;
import com.github.beadieststar64.plugins.bsseries.bscore.Translator;
import com.github.beadieststar64.plugins.bsseries.bscore.YamlReader;
import com.github.beadieststar64.plugins.bsseries.bseconomy.Wallet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BankService implements ProvideBankService{

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

    @Override
    public void initialize() {
        Bukkit.getConsoleSender().sendMessage("[BSEconomy]" + ChatColor.BLUE + ts.getTranslator("BSBankService_Initialize"));
        if(!new File(plugin.getDataFolder() + File.separator + config.getString("Yml_Folder"), "bank.yml").exists()) {
            FileManager fm = new FileManager(plugin);
            fm.createFile(new File(plugin.getDataFolder(), config.getString("Yml_Folder")), "bank.yml");
        }

        Wallet wallet = new Wallet(plugin);
        /*if(config.getYaml().getBoolean("Decimal_Balance")) {
            for(Player player : Bukkit.getServer().getOnlinePlayers()) {
                wallet.setDecimalWallet(player);
            }
        }else{
            for(Player player : Bukkit.getServer().getOnlinePlayers()) {
                wallet.setIntegerWallet(player, bank.getYaml().getInt(player.getUniqueId() + ".Bank_Balance"));
            }
        }
         */
    }

    @Override
    public void save() {

    }

    @Override
    public Object[] createBankAccount(String accountName, OfflinePlayer player) {
        if(bank == null) {
            return new Object[]{false, ts.getTranslator("Service_Exception")};
        }
        FileConfiguration fc = bank.getYaml();
        if(fc.contains(player.getUniqueId().toString())) {
            if(fc.contains(player.getUniqueId() + ".Bank_Account." + accountName)) {
                return new Object[]{false, ts.getTranslator("Duplicate_Bank_Account")};
            }
            if(!fc.contains(player.getUniqueId() + ".Bank_Account") || fc.getList(player.getUniqueId() + ".Bank_Account") == null) {
                return new Object[]{false, ts.getTranslator("Service_Exception")};
            }
            if(fc.getList(player.getUniqueId() + ".Bank_Account").size() > config.getYaml().getInt("Bank_Account_Limit")) {
                return new Object[]{false, ts.getTranslator("Exceed_Possessions_Bank_Account")};
            }
        }

        //setup
        fc.set(player.getUniqueId() + ".MCID", player.getName());
        fc.set(player.getUniqueId() + ".Bank_Account." + accountName + ".Balance", 0);
        bank.saveConfig();
        plugin.getLogger().info("Create to yml. " + fc.getString(player.getUniqueId() + ".MCID"));
        return new Object[]{true};
    }

    public void test(OfflinePlayer offlinePlayer) {
        Player player = (Player) offlinePlayer;
        List<Object> list = new ArrayList<>(Collections.singletonList(bank.getYaml().get(player.getUniqueId() + ".Bank_Account.Normal")));
        plugin.getLogger().info("テスト: " + list.get(0));
    }
}
