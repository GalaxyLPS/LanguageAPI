package de.galaxymc.spigot.apis.languageapi.player;

import de.galaxymc.spigot.apis.languageapi.data.PlayerData;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerDataRegistry {

    public List<PlayerData> playerData = new ArrayList<>();

    public void addPlayerData(PlayerData data) {
        playerData.add(data);
    }

    public PlayerData getPlayerData(Player p) {
        return playerData.stream().filter(data -> data.getPlayer() == p).findFirst().orElse(null);
    }


}