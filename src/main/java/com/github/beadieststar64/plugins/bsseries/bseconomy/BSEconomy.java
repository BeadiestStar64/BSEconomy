package com.github.beadieststar64.plugins.bsseries.bseconomy;

import com.github.beadieststar64.plugins.bsseries.bscore.FileManager;
import com.github.beadieststar64.plugins.bsseries.bscore.Translator;
import com.github.beadieststar64.plugins.bsseries.bscore.YamlReader;
import com.github.beadieststar64.plugins.bsseries.bseconomy.BSBankService.BankService;
import com.github.beadieststar64.plugins.bsseries.bseconomy.VaultService.VaultService;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class BSEconomy extends JavaPlugin {

    @Override
    public void onEnable() {
        //plugin initialize
        FileManager fm = new FileManager(this);
        fm.init(getDataFolder(), "RequestFiles.txt");
        YamlReader config = new YamlReader(this);
        VaultService vs = new VaultService(this);
        Translator ts = new Translator(this, config.getString("Language"));

        //bank initialize
        if(VaultService.useVault) {
            getServer().getConsoleSender().sendMessage("[BCEconomy]" + ChatColor.AQUA + ts.getTranslator("Vault_Initialize"));
            vs.vaultInit();
        }else{
            BankService bs = new BankService(this);
            bs.init();
        }

        //register commands
        getCommand("bank").setExecutor(new Commands(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        VaultService vs = new VaultService(this);
        if(VaultService.useVault) {
            vs.vaultPurge();
        }else{
            BankService bs = new BankService(this);
            bs.save();
        }
    }
}
