package de.galaxymc.spigot.apis.languageapi.listener;

import de.galaxymc.spigot.apis.languageapi.data.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class OnlineListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        PlayerData.create(e.getPlayer());
    }

}