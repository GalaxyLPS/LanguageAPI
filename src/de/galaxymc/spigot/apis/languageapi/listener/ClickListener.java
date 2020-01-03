package de.galaxymc.spigot.apis.languageapi.listener;

import de.galaxymc.spigot.apis.languageapi.LanguageAPI;
import de.galaxymc.spigot.apis.languageapi.data.Data;
import de.galaxymc.spigot.apis.languageapi.data.PlayerData;
import de.galaxymc.spigot.apis.languageapi.language.Language;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public final class ClickListener implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase(Data.getString("lang.inventory.name"))) {
            if (!(e.getWhoClicked() instanceof Player)) return;
            Player p = (Player) e.getWhoClicked();
            PlayerData data = LanguageAPI.getInstance().getPlayerDataRegistry().getPlayerData(p);
            ItemStack item = e.getCurrentItem();
            Language language = LanguageAPI.getInstance().getLanguageRegistry().getLanguageById(item.getItemMeta().getDisplayName());
            data.setCurrent(language);
            data.save();
            p.closeInventory();
            p.sendMessage(LanguageAPI.getInstance().getSettings().getPrefix() + Language.getString(language, "lang.info.language.change"));
        }
    }
}