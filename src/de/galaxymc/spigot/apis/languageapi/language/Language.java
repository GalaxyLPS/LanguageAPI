package de.galaxymc.spigot.apis.languageapi.language;

import de.galaxymc.spigot.apis.languageapi.LanguageAPI;
import de.galaxymc.spigot.apis.languageapi.data.Data;
import de.galaxymc.spigot.apis.languageapi.file.LanguageFile;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Map;

import static de.galaxymc.spigot.apis.languageapi.data.Data.languageDir;

public class Language {

    private final String id;
    private final LanguageFile file;
    private Map<String, String> messages;

    public Language(String id) {
        this.id = id;
        this.file = new LanguageFile(new File(languageDir + "/" + id + ".lang"), this);
        this.messages = file.load();
    }

    public String getId() {
        return id;
    }

    public LanguageFile getFile() {
        return file;
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    public String getMessage(String messageId) {
        String s = messages.get(messageId);
        if (s == null) return Data.getString(messageId);
        return s;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }

    public static String getString(Language language, String messageId) {
        return language.getMessage(messageId);
    }

    public static String getString(Player p, String messageId) {
        return LanguageAPI.getInstance().getPlayerDataRegistry().getPlayerData(p).getCurrent().getMessage(messageId); // TODO: nullpointer if no playerdata found
    }
}