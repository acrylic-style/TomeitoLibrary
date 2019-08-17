package xyz.acrylicstyle.tomeito_core.providers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigProvider extends YamlConfiguration {
	public final File file;
	public final String path;

	public ConfigProvider(String path) throws FileNotFoundException, IOException, InvalidConfigurationException {
		this(path, false);
	}

	public ConfigProvider(String path, boolean disableConstructor) throws IOException, InvalidConfigurationException {
		if (disableConstructor) throw new UnsupportedOperationException();
		this.path = path;
		this.file = new File(this.path);
		if (!this.file.exists()) { // for avoid some dangerous situation
			this.file.mkdirs(); // creates directory(ies) including file nameW
			this.file.delete(); // deletes file but not parent directory
			this.file.createNewFile();
		}
		this.load(this.file);
	}

	/**
	 * @param path relative or absolute path from the spigot.jar
	 * @return ConfigProvider<br>
	 *
	 * <b>Note: This method could return null if any exception has occurred.</b>
	 */
	public static ConfigProvider initWithoutException(String path) {
		try {
			return new ConfigProvider(path);
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked") // for cast to correct type
	public static Map<String, Object> getConfigSectionValue(Object o, boolean deep) {
		if (o == null) return null;
		Map<String, Object> map;
		if (o instanceof ConfigurationSection) {
			map = ((ConfigurationSection) o).getValues(deep);
		} else if (o instanceof Map) {
			map = (Map<String, Object>) o;
		} else return null;
		return map;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getConfigSectionValue(String path, boolean deep) {
		Object o = this.get(path, new HashMap<String, Object>());
		if (o == null) return null;
		Map<String, Object> map;
		if (o instanceof ConfigurationSection) {
			map = ((ConfigurationSection) o).getValues(deep);
		} else if (o instanceof Map) {
			map = (Map<String, Object>) o;
		} else return null;
		return map;
	}

	public void reload() throws IOException, InvalidConfigurationException {
		if (!this.file.exists()) this.file.createNewFile();
		this.load(this.file);
	}

	public void reloadWithoutException() {
		try {
			this.load(this.file);
		} catch (Exception ignore) {}
	}

	public void save() throws IOException {
		this.save(this.file);
	}

	public void setThenSave(String path, Object value) throws IOException {
		this.set(path, value);
		this.save();
	}

	public static void setThenSave(String path, Object value, File file) throws IOException, InvalidConfigurationException {
		YamlConfiguration config = new YamlConfiguration();
		if (!file.exists()) config.save(file);
		config.load(file);
		config.set(path, value);
		config.save(file);
	}

	public static Boolean getBoolean(String path, Boolean def, String pluginName) throws FileNotFoundException, IOException, InvalidConfigurationException {
		return getBoolean(path, def, new File("./plugins/" + pluginName + "/config.yml"));
	}

	public static Boolean getBoolean(String path, Boolean def, File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
		YamlConfiguration config = new YamlConfiguration();
		config.load(file);
		return config.getBoolean(path, def);
	}

	public static String getString(String path, String def, String pluginName) throws FileNotFoundException, IOException, InvalidConfigurationException {
		return getString(path, def, new File("./plugins/" + pluginName + "/config.yml"));
	}

	public static String getString(String path, String def, File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
		YamlConfiguration config = new YamlConfiguration();
		config.load(file);
		return config.getString(path, def);
	}

	public static void setThenSave(String path, Object value, String pluginName) throws IOException, InvalidConfigurationException {
		setThenSave(path, value, new File("./plugins/" + pluginName + "/config.yml"));
	}
}
