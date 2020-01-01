package de.galaxymc.spigot.apis.languageapi.settings;

public class PluginSettings {

    private String schemaVersion;
    private boolean autoUpdate;
    private String prefix;
    private String defaultLang;
    private boolean permission; // if true you need to have the permission lang.change in order to change your language

    public PluginSettings(String schemaVersion, boolean autoUpdate, String prefix, String defaultLang, boolean permission) {
        this.schemaVersion = schemaVersion;
        this.autoUpdate = autoUpdate;
        this.prefix = prefix;
        this.defaultLang = defaultLang;
        this.permission = permission;
    }

    public void setDefaultLang(String defaultLang) {
        this.defaultLang = defaultLang;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public boolean isAutoUpdate() {
        return autoUpdate;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getDefaultLang() {
        return defaultLang;
    }

    public boolean isPermission() {
        return permission;
    }
}
