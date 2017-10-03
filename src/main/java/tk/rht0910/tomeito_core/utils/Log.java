package tk.rht0910.tomeito_core.utils;

import org.bukkit.Bukkit;

/**
 *
 * @author tomeito0110
 * Get logger in using Bukkit.
 *
 */
public class Log {
	public static String classn = Thread.currentThread().getStackTrace()[2].getClassName();
	public static void info(String msg) {
		//classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		Bukkit.getLogger().info("[" + classn + "] " + msg);
	}

	public static void warning(String msg) {
		//classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		Bukkit.getLogger().warning("[" + classn + "] " + msg);
	}

	public static void warn(String msg) {
		//classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		Bukkit.getLogger().warning("[" + classn + "] " + msg);
	}

	public static void severe(String msg) {
		//classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		Bukkit.getLogger().severe("[" + classn + "] " + msg);
	}

	public static void error(String msg) {
		//classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		Bukkit.getLogger().severe("[" + classn + "] " + msg);
	}

	public static void config(String msg) {
		//classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		Bukkit.getLogger().config("[" + classn + "] " + msg);
	}

	public static void fine(String msg) {
		//classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		Bukkit.getLogger().fine("[" + classn + "] " + msg);
	}

	public static void finer(String msg) {
		//classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		Bukkit.getLogger().finer("[" + classn + "] " + msg);
	}

	public static void finest(String msg) {
		//classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		Bukkit.getLogger().finest("[" + classn + "] " + msg);
	}

	public static void debug(String msg) {
		//classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		Bukkit.getLogger().info("[" + classn + "] [DEBUG] " + msg);
	}
}
