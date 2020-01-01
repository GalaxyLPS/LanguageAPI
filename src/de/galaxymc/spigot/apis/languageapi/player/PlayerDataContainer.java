package de.galaxymc.spigot.apis.languageapi.player;

import de.galaxymc.spigot.apis.languageapi.data.PlayerData;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerDataContainer {

    public List<PlayerData> playerData = new ArrayList<>();

    public void addPlayerData(PlayerData data) {
        playerData.add(data);
    }

    public PlayerData getPlayerData(Player p) {
        Optional<PlayerData> maybe = playerData.stream().filter(data -> data.getPlayer() == p).findFirst();
        return maybe.orElse(null);
    }


}