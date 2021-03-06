package de.galaxymc.spigot.apis.languageapi.data;

import de.galaxymc.spigot.apis.languageapi.LanguageAPI;
import de.galaxymc.spigot.apis.languageapi.language.Language;
import org.bukkit.entity.Player;

public class PlayerData {

    Player player;
    Language current = LanguageAPI.getInstance().getLanguageRegistry().defaultLanguage();

    private PlayerData(Player player) {
        this.player = player;
    }

    public void load() {
        current = LanguageAPI.getInstance().getPlayerFile().getLanguage(player);
    }

    public void save() {
        LanguageAPI.getInstance().getPlayerFile().setPlayerData(this);
    }

    public void setCurrent(Language current) {
        this.current = current;
    }

    public Language getCurrent() {
        return current;
    }

    public Player getPlayer() {
        return player;
    }

    public static PlayerData create(Player p) {
        PlayerData data = new PlayerData(p);
        data.load();
        if (data.getCurrent() == null) {
            data.setCurrent(LanguageAPI.getInstance().getLanguageRegistry().defaultLanguage(p));
            LanguageAPI.getInstance().getPlayerFile().setPlayerData(data);
        }
        LanguageAPI.getInstance().getPlayerDataRegistry().addPlayerData(data);
        return data;
    }

}