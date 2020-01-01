package de.galaxymc.spigot.apis.languageapi.language;

import de.galaxymc.spigot.apis.languageapi.LanguageAPI;
import de.galaxymc.spigot.apis.languageapi.data.Data;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static de.galaxymc.spigot.apis.languageapi.data.Data.languageDir;

public class LanguageContainer {

    private List<Language> languages;

    public LanguageContainer() {
        this.languages = new ArrayList<>();
    }

    public void init() throws IOException {
        loadLanguages();
    }

    public void loadLanguage(String id) {
        languages.add(new Language(id));
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public Language getLanguageById(String id) {
        Optional<Language> maybe = languages.stream().filter(lang -> lang.getId().equalsIgnoreCase(id)).findFirst();
        return maybe.orElse(defaultLanguage());
    }

    public Language defaultLanguage() {
        String defaultId = LanguageAPI.getInstance().getSettings().getDefaultLang();
        Optional<Language> maybe = languages.stream().filter(lang -> lang.getId().equalsIgnoreCase(defaultId)).findFirst();
        return maybe.orElse(null);
    }

    public Language defaultLanguage(Player p) {
        String local = ((CraftPlayer) p).getHandle().locale;
        Optional<Language> language = languages.stream().filter(lang -> lang.getId().equalsIgnoreCase(local)).findFirst();
        return language.orElse(defaultLanguage());
    }

    public Inventory createInventory(Player owner) {
        int size = LanguageAPI.getInstance().getLanguageContainer().getLanguages().size();
        int invSize;
        if (size <= 9) invSize = 9;
        else if (size <= 18) invSize = 18;
        else if (size <= 27) invSize = 27;
        else if (size <= 36) invSize = 36;
        else if (size <= 45) invSize = 45;
        else invSize = 45;
        Inventory inventory = Bukkit.createInventory(owner, invSize, Language.getString(owner, "lang.inventory.name"));
        for (ItemStack item : createItems()) {
            inventory.addItem(item);
        }
        return inventory;
    }

    public ItemStack[] createItems() {
        List<ItemStack> items = new ArrayList<>();
        languages.stream().forEach(lang -> {
            ItemStack stack = new ItemStack(Material.PLAYER_HEAD);
            stack.setAmount(1);
            SkullMeta meta = (SkullMeta) stack.getItemMeta();
            meta.setDisplayName(lang.getId());
            meta.setUnbreakable(true);
            stack.setItemMeta(meta);
            items.add(stack);
        });
        ItemStack[] itemStacks = new ItemStack[items.size()];
        for (int i = 0; i < itemStacks.length; i++) {
            itemStacks[i] = items.get(i);
        }
        return itemStacks;
    }

    public void loadLanguages() throws IOException {
        File[] langs = languageDir.listFiles();
        if (langs == null || langs.length == 0) {
            langs = new File[1];
            langs[0] = createEmptyLanguage();
        }
        Arrays.stream(langs).forEach(lang -> {
            int extindex = lang.getName().lastIndexOf('.');
            if (extindex == 0) return;
            String id = lang.getName().substring(0, extindex); // remove extension
            loadLanguage(id);
        });
    }

    public File createEmptyLanguage() throws IOException {
        Language language = new Language("en_US");
        language.setMessages(Data.getDefaultStrings());
        File file = new File(languageDir + "/" + language.getId() + ".lang");
        file.createNewFile();
        language.getFile().save();
        return file;
    }

    public Language mostUsed() {
        Map<Language, Integer> langs = LanguageAPI.getInstance().getPlayerFile().countLanguageUsage();
        Language highest = null;
        int value = 0;
        for (Language lang : langs.keySet()) {
            int current = langs.get(lang);
            if (current > value) {
                highest = lang;
                value = current;
            }
        }
        return highest;
    }

}