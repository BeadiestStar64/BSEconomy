package com.github.beadieststar64.plugins.bsseries.bseconomy;

import com.github.beadieststar64.plugins.bsseries.bscore.YamlReader;
import com.github.beadieststar64.plugins.bsseries.bseconomy.VaultService.VaultService;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class Commands implements CommandExecutor {

    private final Plugin plugin;
    private final YamlReader config;

    public Commands(Plugin plugin) {
        this.plugin = plugin;
        this.config = new YamlReader(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String str, @NotNull String[] args) {
        Player player = null;

        if(sender instanceof Player) {
            player = (Player) sender;
        }

        if(command.getName().equals("bank")) {
            Wallet wallet = new Wallet(plugin);

            switch (args[0]) {
                case "check" -> {
                    if(checkSender(3, sender)) {
                        return true;
                    }
                    if(checkSender(1, sender)) {
                        if(sender.hasPermission("")) {

                        }
                    }
                }
            }

            if(args[0].equals("create")) {
                if(checkSender(3, sender)) {
                    VaultService vs = new VaultService(plugin);
                    Player target = Bukkit.getPlayer(args[1]);
                    vs.playerBank.put(target.getUniqueId(), 0.0);
                    YamlReader yr = new YamlReader(plugin);
                    sender.sendMessage(args[1] + "さんの口座を開設しました");
                    sender.sendMessage("残高: " + vs.playerBank.get(target.getUniqueId()) + yr.getYaml().getString("Currency"));
                    return true;
                }
            }
            if(args[0].equals("balance")) {
                if(args.length < 2) {
                    if(checkSender(1, sender)) {
                        if(config.getYaml().getBoolean("Use_Vault")) {
                            VaultService vs = new VaultService(plugin);
                            YamlReader yr = new YamlReader(plugin);
                            sender.sendMessage("残高: " + vs.playerBank.get(player.getUniqueId()) + yr.getString("Currency"));
                        }else{
                            if(config.getYaml().getBoolean("Decimal_Balance")) {
                                sender.sendMessage("残高: " + wallet.getDecimalWallet(player) + config.getString("Currency"));
                            }else{
                                sender.sendMessage("残高: " + wallet.getIntegerWallet(player) + config.getString("Currency"));
                            }
                        }
                        return true;
                    }
                }
            }
            if(args[0].equals("set")) {
                if(args.length < 3) {
                    if(checkSender(1, sender)) {
                        try {
                            YamlReader config = new YamlReader(plugin);
                            if (!config.getYaml().getBoolean("Decimal_Balance")) {
                                wallet.setIntegerWallet(player, Integer.parseInt(args[1]));
                            }
                            return true;
                        }catch (Exception e) {
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean checkSender(int checkType, CommandSender sender) {
        if(checkType == 1) {
            return sender instanceof Player;
        }
        if(checkType == 2) {
            return (sender instanceof ConsoleCommandSender || sender instanceof Player);
        }
        if(checkType == 3) {
            return sender instanceof ConsoleCommandSender;
        }
        return false;
    }
}
