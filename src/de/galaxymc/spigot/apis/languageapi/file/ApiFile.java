package de.galaxymc.spigot.apis.languageapi.file;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class ApiFile {

    protected YamlConfiguration cfg;
    protected File file;

    protected void save() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}