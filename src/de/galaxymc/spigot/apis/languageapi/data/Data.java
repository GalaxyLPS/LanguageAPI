package de.galaxymc.spigot.apis.languageapi.data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Data {

    public static final File baseDir = new File("plugins/LanguageAPI/");
    public static final File languageDir = new File(baseDir + "/language/");
    public static final File configFile = new File(baseDir + "/config.yml");
    public static final File playerFile = new File(baseDir + "/players.yml");

    private static final Map<String, String> defaultStrings = new HashMap<>();

    static {
        baseDir.mkdirs();
        languageDir.mkdirs();

        defaultStrings.put("lang.info.language.change", "You language has been changed!");
        defaultStrings.put("lang.command.help.change", "Change your language!");
        defaultStrings.put("lang.command.help.help", "Shows this list!");
        defaultStrings.put("lang.command.help.reload.all", "Reload the plugin files");
        defaultStrings.put("lang.command.help.reload.config", "Reload the config file");
        defaultStrings.put("lang.command.help.reload.players", "Reload the players file");
        defaultStrings.put("lang.command.help.reload.lang", "Reload the language files");
        defaultStrings.put("lang.command.help.stats", "Shows stats about the languages");
        defaultStrings.put("lang.command.help.alias", "Same as /lang");

        defaultStrings.put("lang.stats.header", "--------Language Statistics--------");
        defaultStrings.put("lang.stats.footer", "--------Language Statistics--------");
        defaultStrings.put("lang.command.noperm", "You are not allowed to do this!");
        defaultStrings.put("lang.admin.reload.success", "The reload was successful!");
        defaultStrings.put("lang.admin.reload.failure", "The reload failed! Error is available in the server logs!");
        defaultStrings.put("lang.inventory.name", "Choose a language!");
    }

    public static String getString(String id) {
        return defaultStrings.get(id);
    }

    public static void addString(String id, String defaultString) {
        defaultStrings.put(id, defaultString);
    }

    public static Map<String, String> getDefaultStrings() {
        return defaultStrings;
    }
}