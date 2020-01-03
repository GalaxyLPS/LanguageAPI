package de.galaxymc.spigot.apis.languageapi;

import de.galaxymc.spigot.apis.languageapi.command.LangCommand;
import de.galaxymc.spigot.apis.languageapi.data.PlayerData;
import de.galaxymc.spigot.apis.languageapi.file.ConfigFile;
import de.galaxymc.spigot.apis.languageapi.file.PlayerFile;
import de.galaxymc.spigot.apis.languageapi.language.LanguageRegistry;
import de.galaxymc.spigot.apis.languageapi.listener.ClickListener;
import de.galaxymc.spigot.apis.languageapi.listener.OnlineListener;
import de.galaxymc.spigot.apis.languageapi.player.PlayerDataRegistry;
import de.galaxymc.spigot.apis.languageapi.settings.PluginSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Objects;

public final class LanguageAPI extends JavaPlugin {

    private PlayerFile playerFile;
    private ConfigFile configFile;
    private LanguageRegistry languageRegistry;
    private PluginSettings settings;
    private PlayerDataRegistry playerDataRegistry;
    private static LanguageAPI instance;

    @Override
    public void onEnable() {
        configFile = new ConfigFile();
        playerFile = new PlayerFile();
        languageRegistry = new LanguageRegistry();
        playerDataRegistry = new PlayerDataRegistry();
        try {
            configFile.init();
            playerFile.init();
            languageRegistry.init();
            settings = configFile.load();
            languageRegistry.getLanguages().forEach(lang -> lang.getFile().saveLanguage());
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
        playerDataRegistry.playerData.forEach(PlayerData::save);
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

    public LanguageRegistry getLanguageRegistry() {
        return languageRegistry;
    }

    public PluginSettings getSettings() {
        return settings;
    }

    public PlayerDataRegistry getPlayerDataRegistry() {
        return playerDataRegistry;
    }

    public static LanguageAPI getInstance() {
        return instance;
    }
}
