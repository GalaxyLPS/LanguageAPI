package de.galaxymc.spigot.apis.languageapi.command;

import de.galaxymc.spigot.apis.languageapi.LanguageAPI;
import de.galaxymc.spigot.apis.languageapi.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class LangCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player)) {
            s.sendMessage(LanguageAPI.prefix() + "You have to be a player!");
            return true;
        }
        Player p = (Player) s;
        if (args.length == 0) {
            printHelp(p);
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (!p.hasPermission("lang.admin")) {
                    p.sendMessage(LanguageAPI.prefix() + Language.getString(p, "lang.command.noperm"));
                    return true;
                }
                try {
                    LanguageAPI.getInstance().getLanguageContainer().loadLanguages();
                    p.sendMessage(LanguageAPI.prefix() + Language.getString(p, "lang.admin.reload.success"));
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    p.sendMessage(LanguageAPI.prefix() + Language.getString(p, "lang.admin.reload.failure"));
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("change")) {
                if (LanguageAPI.getInstance().getSettings().isPermission() && !p.hasPermission("lang.change")) {
                    p.sendMessage(LanguageAPI.prefix() + Language.getString(p, "lang.command.noperm"));
                    return true;
                }
                p.openInventory(LanguageAPI.getInstance().getLanguageContainer().createInventory(p));
                return true;
            } else if (args[0].equalsIgnoreCase("stats")) {
                if (p.hasPermission("lang.admin")) {
                    p.sendMessage(LanguageAPI.prefix() + Language.getString(p, "lang.stats.header"));
                    p.sendMessage(LanguageAPI.prefix() + Language.getString(p, "lang.stats.most") + LanguageAPI.getInstance().getLanguageContainer().mostUsed().getId());
                    p.sendMessage(LanguageAPI.prefix() + Language.getString(p, "lang.stats.footer"));
                } else {
                    p.sendMessage(LanguageAPI.prefix() + Language.getString(p, "lang.command.noperm"));
                }
            } else if (args[0].equalsIgnoreCase("help")) {
                printHelp(p);
                return true;
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (!p.hasPermission("lang.admin")) {
                    p.sendMessage(LanguageAPI.prefix() + Language.getString(p, "lang.command.noperm"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("config")) {
                    LanguageAPI.getInstance().getConfigFile().load();
                    p.sendMessage(LanguageAPI.prefix() + Language.getString(p, "lang.admin.reload.success"));
                } else if (args[1].equalsIgnoreCase("players")) {
                    Bukkit.getOnlinePlayers().forEach(player -> LanguageAPI.getInstance().getPlayerDataContainer().getPlayerData(p).load());
                    p.sendMessage(LanguageAPI.prefix() + Language.getString(p, "lang.admin.reload.success"));
                } else if (args[1].equalsIgnoreCase("lang")) {
                    try {
                        LanguageAPI.getInstance().getLanguageContainer().loadLanguages();
                        p.sendMessage(LanguageAPI.prefix() + Language.getString(p, "lang.admin.reload.success"));
                    } catch (IOException e) {
                        e.printStackTrace();
                        p.sendMessage(LanguageAPI.prefix() + Language.getString(p, "lang.admin.reload.failure"));
                    }
                }
            }
        }
        return true;
    }

    public void printHelp(Player p) {
        if (!p.hasPermission("lang.admin")) {
            p.sendMessage("/lang change -> " + Language.getString(p, "lang.command.help.change"));
            return;
        }
        p.sendMessage(LanguageAPI.prefix() + "/lang -> " + Language.getString(p, "lang.command.help.help"));
        p.sendMessage(LanguageAPI.prefix() + "/lang reload -> " + Language.getString(p, "lang.command.help.reload.all"));
        p.sendMessage(LanguageAPI.prefix() + "/lang reload config -> " + Language.getString(p, "lang.command.help.reload.config"));
        p.sendMessage(LanguageAPI.prefix() + "/lang reload players -> " + Language.getString(p, "lang.command.help.reload.players"));
        p.sendMessage(LanguageAPI.prefix() + "/lang reload lang -> " + Language.getString(p, "lang.command.help.reload.lang"));
        p.sendMessage(LanguageAPI.prefix() + "/lang stats -> " + Language.getString(p, "lang.command.help.stats"));
        p.sendMessage(LanguageAPI.prefix() + "/lang help -> " + Language.getString(p, "lang.command.help.alias"));
    }
}
