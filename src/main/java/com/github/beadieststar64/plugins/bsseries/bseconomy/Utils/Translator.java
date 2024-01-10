package com.github.beadieststar64.plugins.bsseries.bseconomy.Utils;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Properties;

public class Translator {

    private final Plugin plugin;
    private final YamlReader yr;
    private final String language;

    public Translator(Plugin plugin) {
        this.plugin = plugin;
        this.yr = new YamlReader(plugin);
        this.language = yr.getString("Language");
    }

    public String getTranslator(String key) {
        Properties prop = new Properties();
        File file = new File(new File(plugin.getDataFolder(), yr.getString("Translator_Folder")), yr.getString("Language"));
        try(InputStream stream = Files.newInputStream(file.toPath());
            InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            prop.load(reader);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty(key);
    }
}