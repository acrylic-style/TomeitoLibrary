package xyz.acrylicstyle.tomeito_api.utils;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.acrylicstyle.tomeito_api.providers.LanguageProvider;

import java.util.HashMap;

public class Lang {
    /**
     * Language Map
     */
    public final HashMap<String, LanguageProvider> languages;

    /**
     * Plugin name
     */
    public final String plugin;

    /**
     * Constructs language with argument.
     * @param plugin A plugin name, /plugins/[here]/language_[language].yml
     */
    public Lang(String plugin) {
        this.plugin = plugin;
        this.languages = new HashMap<>();
    }

    /**
     * Add language into HashMap.
     * @param language A language that you want to define
     * @return This instance
     */
    public Lang addLanguage(String language) {
        this.languages.put(language, LanguageProvider.init(this.plugin, language));
        return this;
    }

    /**
     * Get defined LanguageProvider with specified language.
     * @throws IllegalArgumentException When specified language is not defined
     * @throws IllegalStateException When specified language was found but value is not defined
     * @param language A language that you want to get LanguageProvider
     * @return LanguageProvider that initialized by {@link #addLanguage(String)}
     */
    public LanguageProvider get(String language) {
        if (!this.languages.containsKey(language)) throw new IllegalArgumentException("Language \"" + language + "\" is not defined.");
        LanguageProvider lang = this.languages.get(language);
        if (lang == null) throw new IllegalStateException("Value is null");
        return lang;
    }

    /**
     * Copy resource to plugin folder.<br>
     * It will always replace existing file.
     * @param plugin A plugin that wants to copy resource to.
     * @param file File name
     */
    public static <T extends JavaPlugin> void saveResource(T plugin, String file) {
        saveResource(plugin, file, true);
    }

    public static String format(String format, Object... args) {
        return String.format(ChatColor.translateAlternateColorCodes('&', format), args);
    }

    /**
     * Copy resource to plugin folder.
     * @param plugin A plugin that wants to copy resource to.
     * @param file File name
     * @param replace Replace existing file or not
     */
    public static <T extends JavaPlugin> void saveResource(T plugin, String file, boolean replace) {
        T.getPlugin(plugin.getClass()).saveResource(file, replace);
    }
}
