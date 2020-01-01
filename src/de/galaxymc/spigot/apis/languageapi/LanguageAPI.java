package de.galaxymc.spigot.apis.languageapi;

import de.galaxymc.spigot.apis.languageapi.command.LangCommand;
import de.galaxymc.spigot.apis.languageapi.data.PlayerData;
import de.galaxymc.spigot.apis.languageapi.file.ConfigFile;
import de.galaxymc.spigot.apis.languageapi.file.PlayerFile;
import de.galaxymc.spigot.apis.languageapi.language.LanguageContainer;
import de.galaxymc.spigot.apis.languageapi.listener.ClickListener;
import de.galaxymc.spigot.apis.languageapi.listener.OnlineListener;
import de.galaxymc.spigot.apis.languageapi.player.PlayerDataContainer;
import de.galaxymc.spigot.apis.languageapi.settings.PluginSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Objects;

public final class LanguageAPI extends JavaPlugin {

    private PlayerFile playerFile;
    private ConfigFile configFile;
    private LanguageContainer languageContainer;
    private PluginSettings settings;
    private PlayerDataContainer playerDataContainer;
    private static LanguageAPI instance;

    @Override
    public void onEnable() {
        configFile = new ConfigFile();
        playerFile = new PlayerFile();
        languageContainer = new LanguageContainer();
        playerDataContainer = new PlayerDataContainer();
        try {
            configFile.init();
            playerFile.init();
            languageContainer.init();
            settings = configFile.load();
            languageContainer.getLanguages().forEach(lang -> lang.getFile().save());
        } catch (IOException e) {
            e.printStackTrace();
            this.setEnabled(false);
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerData.create(p);
        }
        Bukkit.getPluginManager().registerEvents(new OnlineListener(), this);
        Bukkit.getPluginManager().registerEvents(new ClickListener(), this);
        Objects.requireNonNull(getCommand("lang")).setExecutor(new LangCommand());
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onDisable() {
        playerDataContainer.playerData.forEach(PlayerData::save);
    }

    public static String prefix() {
        return instance.settings.getPrefix();
    }

    public PlayerFile getPlayerFile() {
        return playerFile;
    }

    public ConfigFile getConfigFile() {
        return configFile;
    }

    public LanguageContainer getLanguageContainer() {
        return languageContainer;
    }

    public PluginSettings getSettings() {
        return settings;
    }

    public PlayerDataContainer getPlayerDataContainer() {
        return playerDataContainer;
    }

    public static LanguageAPI getInstance() {
        return instance;
    }
}
