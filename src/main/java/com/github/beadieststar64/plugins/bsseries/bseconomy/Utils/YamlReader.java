package com.github.beadieststar64.plugins.bsseries.bseconomy.Utils;

import com.github.beadieststar64.plugins.bsseries.bseconomy.Utils.FileManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.logging.Level;

public class YamlReader {

    private FileConfiguration yaml = null;
    private final File yamlFile;
    private final String file;
    private final Plugin plugin;

    public YamlReader(Plugin plugin) {
        this(plugin, "");
    }

    public YamlReader(Plugin plugin, String folderName) {
        this(plugin, folderName, "config.yml");
    }

    public YamlReader(Plugin plugin, String folderName, String fileName) {
        this.plugin = plugin;
        this.file = fileName;
        if(folderName.isEmpty()) {
            yamlFile = new File(plugin.getDataFolder(), fileName);
            saveDefault(fileName);
        }else{
            yamlFile = new File(plugin.getDataFolder() + folderName, fileName);
            saveDefault(new File(plugin.getDataFolder(), folderName), fileName);
        }
    }

    public void saveDefault(String fileName) {
        if(!yamlFile.exists()) {
            FileManager fm = new FileManager(plugin);
            fm.createFile(plugin.getDataFolder(), fileName);
        }
    }

    public void saveDefault(File folder, String fileName) {
        if(!yamlFile.exists()) {
            FileManager fm = new FileManager(plugin);
            fm.createFile(folder, fileName);
        }
    }

    public void reloadYaml() {
        yaml = YamlConfiguration.loadConfiguration(yamlFile);
        final InputStream defYamlStream = plugin.getResource(file);
        if(defYamlStream == null) {
            return;
        }
        yaml.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defYamlStream, StandardCharsets.UTF_8)));
    }

    public FileConfiguration getYaml() {
        if(yaml == null) {
            reloadYaml();
        }
        return yaml;
    }

    public void saveConfig() {
        if (yaml == null) {
            return;
        }
        try {
            getYaml().save(yamlFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + yamlFile, ex);
        }
        reloadYaml();
    }

    public String getString(String key) {
        return getYaml().getString(key);
    }

    public void set(String key, Objects var) {
        getYaml().set(key, var);
        saveConfig();
    }
}
