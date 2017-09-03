package tk.rht0910.tomeito_core.utils;

import org.bukkit.Bukkit;

public class Log {
	public static void info(String msg) {
		Bukkit.getLogger().info("[PluginManager] " + msg);
	}

	public static void warning(String msg) {
		Bukkit.getLogger().warning("[PluginManager] " + msg);
	}

	public static void warn(String msg) {
		Bukkit.getLogger().warning("[PluginManager] " + msg);
	}

	public static void severe(String msg) {
		Bukkit.getLogger().severe("[PluginManager] " + msg);
	}

	public static void error(String msg) {
		Bukkit.getLogger().severe("[PluginManager] " + msg);
	}

	public static void config(String msg) {
		Bukkit.getLogger().config("[PluginManager] " + msg);
	}

	public static void fine(String msg) {
		Bukkit.getLogger().fine("[PluginManager] " + msg);
	}

	public static void finer(String msg) {
		Bukkit.getLogger().finer("[PluginManager] " + msg);
	}

	public static void finest(String msg) {
		Bukkit.getLogger().finest("[PluginManager] " + msg);
	}

	public static void debug(String msg) {
		Bukkit.getLogger().info("[PluginManager] [DEBUG] " + msg);
	}
}
