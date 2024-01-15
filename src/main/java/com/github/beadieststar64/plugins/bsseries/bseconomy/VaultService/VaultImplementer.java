package com.github.beadieststar64.plugins.bsseries.bseconomy.VaultService;

import com.github.beadieststar64.plugins.bsseries.bscore.Translator;
import com.github.beadieststar64.plugins.bsseries.bscore.YamlReader;
import com.github.beadieststar64.plugins.bsseries.bseconomy.BSBankService.BankService;
import com.github.beadieststar64.plugins.bsseries.bseconomy.BSEconomy;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.text.NumberFormat;
import java.util.List;
import java.util.UUID;

public class VaultImplementer implements Economy {

    private final VaultService service = VaultService.getInstance;
    private final Translator ts;
    private final FileConfiguration config;

    public VaultImplementer(Plugin plugin) {
        config = new YamlReader(plugin).getYaml();
        this.ts = new Translator(plugin, config.getString("Language"));
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "BSEconomy";
    }

    @Override
    public boolean hasBankSupport() {
        return true;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double amount) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        return nf.format(amount);
    }

    @Override
    public String currencyNamePlural() {
        return currencyNameSingular();
    }

    @Override
    public String currencyNameSingular() {
        return config.getString("Currency");
    }

    @Override
    public boolean hasAccount(String mcid) {
        OfflinePlayer player = Bukkit.getPlayer(mcid);
        return hasAccount(player);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        if(offlinePlayer == null) {
            BSEconomy.getInstance.getLogger().warning("Player not found.");
            return false;
        }
        BankService bs = new BankService(BSEconomy.getInstance);
        bs.test(offlinePlayer);
        return true;
    }

    @Override
    public boolean hasAccount(String mcid, String world) {
        OfflinePlayer player = Bukkit.getPlayer(mcid);
        return hasAccount(player, world);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String world) {
        BankService bs = new BankService(BSEconomy.getInstance);
        return false;
    }

    @Override
    public double getBalance(String mcid) {
        OfflinePlayer player = Bukkit.getPlayer(mcid);
        if(player == null) {
            return 0;
        }
        return getBalance(player);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        UUID uuid = offlinePlayer.getUniqueId();
        return service.playerBank.get(uuid);
    }

    @Override
    public double getBalance(String mcid, String world) {
        OfflinePlayer player = Bukkit.getPlayer(mcid);
        if(player == null) {
            return 0;
        }
        return getBalance(player, world);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String world) {
        UUID uuid = offlinePlayer.getUniqueId();
        return service.playerBank.get(uuid);
    }

    @Override
    public boolean has(String mcid, double amount) {
        OfflinePlayer player = Bukkit.getPlayer(mcid);
        return has(player, amount);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double amount) {
        return false;
    }

    @Override
    public boolean has(String mcid, String world, double amount) {
        OfflinePlayer player = Bukkit.getPlayer(mcid);
        return has(player, world, amount);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String world, double amount) {
        return false;
    }

    //withdraw -> 引き出す
    @Override
    public EconomyResponse withdrawPlayer(String mcid, double amount) {
        OfflinePlayer player = Bukkit.getPlayer(mcid);
        return withdrawPlayer(player, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double amount) {
        if(amount < 0) {

        }
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(String mcid, String world, double amount) {
        OfflinePlayer player = Bukkit.getPlayer(mcid);
        return withdrawPlayer(player, world, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String world, double amount) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String mcid, double amount) {
        OfflinePlayer player = Bukkit.getPlayer(mcid);
        if(player == null) {
            return null;
        }
        return depositPlayer(player, amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double amount) {
        UUID uuid = offlinePlayer.getUniqueId();
        double oldBalance = service.playerBank.get(uuid);
        service.playerBank.put(uuid, oldBalance + amount);
        return new EconomyResponse(oldBalance, service.playerBank.get(uuid), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(String mcid, String world, double amount) {
        OfflinePlayer player = Bukkit.getPlayer(mcid);
        if(player == null) {
            return null;
        }
        return depositPlayer(player, world, amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String world, double amount) {
        UUID uuid = offlinePlayer.getUniqueId();
        double oldBalance = service.playerBank.get(uuid);
        service.playerBank.put(uuid, oldBalance + amount);
        return null;
    }

    @Override
    public EconomyResponse createBank(String account, String mcid) {
        OfflinePlayer player = Bukkit.getPlayer(mcid);
        return createBank(account, player);
    }

    @Override
    public EconomyResponse createBank(String account, OfflinePlayer offlinePlayer) {
        BankService bs = new BankService(BSEconomy.getInstance);
        if(offlinePlayer == null) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, ChatColor.RED + ts.getTranslator("Service_Exception"));
        }
        Object[] objects = bs.createBankAccount(account, offlinePlayer);
        if(!(boolean) objects[0]) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, ChatColor.RED + (String) objects[1]);
        }else{
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.SUCCESS, "");
        }
    }

    @Override
    public EconomyResponse deleteBank(String account) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String account) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String mcid, double amount) {
        if(amount < 0) {

        }
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String mcid, double amount) {
        if(amount < 0) {

        }
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String mcid, double amount) {
        if(amount < 0) {

        }
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String account, String mcid) {
        OfflinePlayer player = Bukkit.getPlayer(mcid);
        return isBankOwner(account, player);
    }

    @Override
    public EconomyResponse isBankOwner(String account, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String account, String mcid) {
        OfflinePlayer player = Bukkit.getPlayer(mcid);
        return isBankMember(account, player);
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "");
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String mcid) {
        OfflinePlayer player = Bukkit.getPlayer(mcid);
        return createPlayerAccount(player);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(String mcid, String world) {
        OfflinePlayer player = Bukkit.getPlayer(mcid);
        return createPlayerAccount(player, world);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String world) {
        return false;
    }
}
