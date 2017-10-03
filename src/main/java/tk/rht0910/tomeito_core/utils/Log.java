package tk.rht0910.tomeito_core.utils;

import org.bukkit.Bukkit;

/**
 *
 * @author tomeito0110
 * Get logger in using Bukkit.
 *
 */
public class Log {
	public static void info(String msg) {
		Bukkit.getLogger().info("[" + Thread.currentThread().getStackTrace()[2].getClassName() + "] " + msg);
	}

	public static void warning(String msg) {
		Bukkit.getLogger().warning("[" + Thread.currentThread().getStackTrace()[2].getClassName() + "] " + msg);
	}

	public static void warn(String msg) {
		Bukkit.getLogger().warning("[" + Thread.currentThread().getStackTrace()[2].getClassName() + "] " + msg);
	}

	public static void severe(String msg) {
		Bukkit.getLogger().severe("[" + Thread.currentThread().getStackTrace()[2].getClassName() + "] " + msg);
	}

	public static void error(String msg) {
		Bukkit.getLogger().severe("[" + Thread.currentThread().getStackTrace()[2].getClassName() + "] " + msg);
	}

	public static void config(String msg) {
		Bukkit.getLogger().config("[" + Thread.currentThread().getStackTrace()[2].getClassName() + "] " + msg);
	}

	public static void fine(String msg) {
		Bukkit.getLogger().fine("[" + Thread.currentThread().getStackTrace()[2].getClassName() + "] " + msg);
	}

	public static void finer(String msg) {
		Bukkit.getLogger().finer("[" + Thread.currentThread().getStackTrace()[2].getClassName() + "] " + msg);
	}

	public static void finest(String msg) {
		Bukkit.getLogger().finest("[" + Thread.currentThread().getStackTrace()[2].getClassName() + "] " + msg);
	}

	public static void debug(String msg) {
		Bukkit.getLogger().info("[" + Thread.currentThread().getStackTrace()[2].getClassName() + "] [DEBUG] " + msg);
	}
}
