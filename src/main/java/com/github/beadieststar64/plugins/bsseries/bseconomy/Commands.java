package com.github.beadieststar64.plugins.bsseries.bseconomy;

import com.github.beadieststar64.plugins.bsseries.bscore.Translator;
import com.github.beadieststar64.plugins.bsseries.bscore.YamlReader;
import com.github.beadieststar64.plugins.bsseries.bseconomy.VaultService.VaultImplementer;
import com.github.beadieststar64.plugins.bsseries.bseconomy.VaultService.VaultService;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
    private final VaultImplementer vi;
    private final Translator ts;

    public Commands(Plugin plugin) {
        this.plugin = plugin;
        this.config = new YamlReader(plugin);
        this.vi = new VaultImplementer(plugin);
        this.ts = new Translator(plugin, config.getString("Language"));
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
                    //arg1: player
                    switch (args.length) {
                        case 1 -> {
                            if(!(sender instanceof Player)) {
                                sender.sendMessage(ChatColor.RED + ts.getTranslator("Reject_Command_Console_Error"));
                                return true;
                            }
                            if(vi.hasAccount(player)) {
                                vi.createBank("Normal", player);
                                return true;
                            }
                        }
                        case 2 -> {
                            if(!checkPermission(sender, "bseconomy.commands.admin")) {
                                sender.sendMessage(ChatColor.RED + ts.getTranslator("Reject_Command_Permission_Error"));
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean checkPermission(CommandSender sender, String permission) {
        if(sender instanceof ConsoleCommandSender) {
            return true;
        }
        if(sender instanceof Player) {
            Player player = (Player) sender;
            return player.hasPermission(permission);
        }
        return false;
    }
}