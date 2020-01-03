package de.galaxymc.spigot.apis.languageapi.file;

import de.galaxymc.spigot.apis.languageapi.settings.PluginSettings;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static de.galaxymc.spigot.apis.languageapi.data.Data.configFile;

public final class ConfigFile extends ApiFile {

    private YamlConfiguration cfg;

    public ConfigFile() {
        cfg = YamlConfiguration.loadConfiguration(configFile);
    }

    public void init() throws IOException {
        if (!configFile.exists())
            configFile.createNewFile();
        cfg.options().copyDefaults(true);
        Map<String, Object> defaults = new HashMap<>();
        defaults.put("schema-version", "1.0");
        defaults.put("autoUpdate", true);
        defaults.put("prefix", "&8[Language&8] &f");
        defaults.put("language.default", "en_US");
        defaults.put("language.permission", true);
        cfg.addDefaults(defaults);
        save();
    }

    public PluginSettings load() {
        String schemaVersion = cfg.getString("schema-version");
        boolean autoUpdate = cfg.getBoolean("autoUpdate");
        String prefix = Objects.requireNonNull(cfg.getString("prefix")).replaceAll("&", "§");
        String defaultLang = cfg.getString("language.default");
        boolean permission = cfg.getBoolean("language.permission");
        return new PluginSettings(schemaVersion, autoUpdate, prefix, defaultLang, permission);
    }

}