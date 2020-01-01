package de.galaxymc.spigot.apis.languageapi.file;

import de.galaxymc.spigot.apis.languageapi.LanguageAPI;
import de.galaxymc.spigot.apis.languageapi.data.PlayerData;
import de.galaxymc.spigot.apis.languageapi.language.Language;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static de.galaxymc.spigot.apis.languageapi.data.Data.configFile;
import static de.galaxymc.spigot.apis.languageapi.data.Data.playerFile;

public class PlayerFile {

    public YamlConfiguration cfg;

    public void init() throws IOException {
        if (!playerFile.exists()) playerFile.createNewFile();
        cfg = YamlConfiguration.loadConfiguration(playerFile);
        cfg.options().copyDefaults(true);
        cfg.addDefault("schema-version", "1.0");
        save();
    }

    public Language getLanguage(Player p) {
        if (!isPlayerPresent(p)) return LanguageAPI.getInstance().getLanguageContainer().defaultLanguage();
        String id = cfg.getString(p.getUniqueId() + ".lang");
        return LanguageAPI.getInstance().getLanguageContainer().getLanguageById(id);
    }


    public boolean isPlayerPresent(Player p) {
        return cfg.isSet(p.getUniqueId() + ".lang");
    }

    public void setPlayerData(PlayerData data) {
        if (data == null) return;
        cfg.set(data.getPlayer().getUniqueId() + ".lang", data.getCurrent().getId());
        cfg.set(data.getPlayer().getUniqueId() + ".name", data.getPlayer().getName());
        save();
    }

    private void save() {
        try {
            cfg.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<Language, Integer> countLanguageUsage() {
        Map<Language, Integer> langs = new HashMap<>();
        Set<String> keys = cfg.getKeys(true);
        keys.stream().filter(key -> key.endsWith("lang")).forEach(key -> {
            Language lang = LanguageAPI.getInstance().getLanguageContainer().getLanguageById(cfg.getString(key));
            if (lang == null) return;
            langs.put(lang, langs.get(lang) + 1);
        });
        return langs;
    }

}