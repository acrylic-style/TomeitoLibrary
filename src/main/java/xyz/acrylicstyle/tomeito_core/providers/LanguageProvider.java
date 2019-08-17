package xyz.acrylicstyle.tomeito_core.providers;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;

public final class LanguageProvider extends ConfigProvider {
	public LanguageProvider(String path) throws FileNotFoundException, IOException, InvalidConfigurationException {
		super(path);
	}

	public static LanguageProvider init(String plugin, String language) throws FileNotFoundException, IOException, InvalidConfigurationException {
		return new LanguageProvider("./plugins/" + plugin + "/language_" + language + ".yml");
	}

	public String get(String path, String def) {
		return super.getString(path, def);
	}

	public String get(String path) {
		return super.getString(path);
	}

	public String getVersion() {
		return super.getString("version", "0.0.0");
	}
}
