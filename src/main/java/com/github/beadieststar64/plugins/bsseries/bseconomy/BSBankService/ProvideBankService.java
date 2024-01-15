package com.github.beadieststar64.plugins.bsseries.bseconomy.BSBankService;

import org.bukkit.OfflinePlayer;

public interface ProvideBankService {
    void initialize();
    void save();

    /**
     * return args info
     * 1st arg: boolean
     * 2nd arg: if 1st arg is false, return error message
     */
    Object[] createBankAccount(String bankAccountName, OfflinePlayer player);
}
