package xyz.acrylicstyle.tomeito_api.providers;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@SuppressWarnings("unused")
public final class LanguageProvider extends ConfigProvider {
    public LanguageProvider(@NotNull File file) {
        super(file);
    }

    public LanguageProvider(@NotNull String path) {
        super(path);
    }

    public LanguageProvider(@NotNull String path, boolean disableConstructor) {
        super(path, disableConstructor);
    }

    public static LanguageProvider init(String plugin, String language) {
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
