package xyz.acrylicstyle.tomeito_core.providers;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;

@SuppressWarnings("unused")
public final class LanguageProvider extends ConfigProvider {
    public LanguageProvider(String path) throws IOException, InvalidConfigurationException {
        super(path);
    }

    public static LanguageProvider init(String plugin, String language) throws IOException, InvalidConfigurationException {
        return new LanguageProvider("./plugins/" + plugin + "/language_" + language + ".yml");
    }

    public String get(String path, String def) {
        return ChatColor.translateAlternateColorCodes('&', super.getString(path, def));
    }

    public String get(String path) {
        return ChatColor.translateAlternateColorCodes('&', super.getString(path));
    }

    public String getVersion() {
        return super.getString("version", "0.0.0");
    }
}
