package xyz.acrylicstyle.tomeito_core.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import xyz.acrylicstyle.tomeito_core.providers.LanguageProvider;

/**
 * Defines language.
 */
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
	 * @param plugin A plugin name, /plugins/<here>/language_<language>.yml
	 */
	public Lang(String plugin) {
		this.plugin = plugin;
		this.languages = new HashMap<String, LanguageProvider>();
	}

	/**
	 * Add language into HashMap.
	 * @param language A language that you want to define
	 * @return This instance
	 * @throws FileNotFoundException When file wasn't found
	 * @throws IOException When tried to load/create a file but failed for some reason
	 * @throws InvalidConfigurationException When loaded configuration but it has yaml error
	 */
	public Lang addLanguage(String language) throws FileNotFoundException, IOException, InvalidConfigurationException {
		this.languages.put(language, LanguageProvider.init(this.plugin, language));
		return this;
	}

	/**
	 * Get defined LanguageProvider with specified language.
	 * @throws IllegalArgumentException When specified language is not defined
	 * @throws IlleggalStateException When specified language was found but value is not defined
	 * @param language A language that you want to get LanguageProvider
	 * @return LanguageProvider that initialized by {@link #addLanguage(String)}
	 */
	public LanguageProvider get(String language) {
		if (!this.languages.containsKey(language)) throw new IllegalArgumentException("Language \"" + language + "\" is not defined.");
		LanguageProvider lang = this.languages.get(language);
		if (lang == null) throw new IllegalStateException("Specified language was found, but value is not defined");
		return lang;
	}

	/**
	 * Copy resource to plugin folder.<br>
	 * It will always replace existing file.
	 * @param plugin A plugin that wants to copy resource to.
	 * @param file File name
	 */
	public <T extends JavaPlugin> void saveResource(T plugin, String file) {
		saveResource(plugin, file, true);
	}

	/**
	 * Copy resource to plugin folder.
	 * @param plugin A plugin that wants to copy resource to.
	 * @param file File name
	 * @param replace Replace existing file or not
	 */
	public <T extends JavaPlugin> void saveResource(T plugin, String file, boolean replace) {
		T.getPlugin(plugin.getClass()).saveResource(file, replace);
	}
}
