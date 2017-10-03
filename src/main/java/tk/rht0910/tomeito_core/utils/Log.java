package tk.rht0910.tomeito_core.utils;

import org.bukkit.Bukkit;

/**
 *
 * @author tomeito0110
 * Get logger in using Bukkit.
 *
 */
public class Log {
	public static String[] classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
	public static void info(String msg) {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		Bukkit.getLogger().info("[" + classn[classn.length] + "] " + msg);
	}

	public static void warning(String msg) {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		Bukkit.getLogger().warning("[" + classn[classn.length] + "] " + msg);
	}

	public static void warn(String msg) {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		Bukkit.getLogger().warning("[" + classn[classn.length - 1] + "] " + msg);
	}

	public static void severe(String msg) {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		Bukkit.getLogger().severe("[" + classn[classn.length - 1] + "] " + msg);
	}

	public static void error(String msg) {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		Bukkit.getLogger().severe("[" + classn[classn.length] + "] " + msg);
	}

	public static void config(String msg) {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		Bukkit.getLogger().config("[" + classn[classn.length] + "] " + msg);
	}

	public static void fine(String msg) {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		Bukkit.getLogger().fine("[" + classn[classn.length] + "] " + msg);
	}

	public static void finer(String msg) {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		Bukkit.getLogger().finer("[" + classn[classn.length] + "] " + msg);
	}

	public static void finest(String msg) {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		Bukkit.getLogger().finest("[" + classn[classn.length] + "] " + msg);
	}

	public static void debug(String msg) {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		Bukkit.getLogger().info("[" + classn[classn.length] + "] [DEBUG] " + msg);
	}
}
