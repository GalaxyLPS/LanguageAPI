package de.galaxymc.spigot.apis.languageapi.file;

import de.galaxymc.spigot.apis.languageapi.data.Data;
import de.galaxymc.spigot.apis.languageapi.language.Language;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LanguageFile {

    private final File file;
    private YamlConfiguration cfg;
    private final Language language;

    public LanguageFile(File file, Language language) {
        this.file = file;
        this.language = language;
        if (!this.file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cfg = YamlConfiguration.loadConfiguration(this.file);
    }

    public Map<String, String> load() {
        Set<String> keys = cfg.getKeys(true);
        Map<String, String> m = new HashMap<>();
        for (String key : keys) {
            String value = cfg.getString(key);
            if (value == null) continue;
            if (value.startsWith("MemorySection")) continue;
            m.put(key, value);
        }
        return m;
    }

    public void save() {
        cfg.options().copyDefaults(true);
        for (String s : Data.getDefaultStrings().keySet()) {
            cfg.addDefault(s, Data.getString(s));
        }
        saveConfiguration();
        for (String s : language.getMessages().keySet()) {
            String msg = language.getMessage(s);
            if (msg == null) continue;
            if (msg.startsWith("MemorySection")) continue;
            cfg.set(s, msg);
        }
        saveConfiguration();
    }

    public void saveConfiguration() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}