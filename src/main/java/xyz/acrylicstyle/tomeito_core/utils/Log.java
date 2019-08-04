package xyz.acrylicstyle.tomeito_core.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 *
 * @author tomeito0110
 * Get logger in using Bukkit.
 *
 */
public class Log {
	@SuppressWarnings("unused")
	private static String[] classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
	public static ChatColor color = ChatColor.RESET;

	public static void info(String msg) {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		//classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".")[];
		try {
			String name = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getSimpleName();
			if (name.equalsIgnoreCase("") || name == null) name = "Anonymous";
			Bukkit.getLogger().info("[" + name + "] " + msg);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void warning(String msg) {
		try {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		//classn = Thread.currentThread().getStackTrace()[2].getClassName();
		String name = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getSimpleName();
		if (name.equalsIgnoreCase("") || name == null) name = "Anonymous";
		Bukkit.getLogger().warning("[" + name + "] " + msg);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void warn(String msg) {
		try {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		//classn = Thread.currentThread().getStackTrace()[2].getClassName();
		String name = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getSimpleName();
		if (name.equalsIgnoreCase("") || name == null) name = "Anonymous";
		Bukkit.getLogger().warning("[" + name + "] " + msg);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void severe(String msg) {
		try {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		//classn = Thread.currentThread().getStackTrace()[2].getClassName();
		String name = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getSimpleName();
		if (name.equalsIgnoreCase("") || name == null) name = "Anonymous";
		Bukkit.getLogger().severe("[" + name + "] " + msg);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void error(String msg) {
		try {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		//classn = Thread.currentThread().getStackTrace()[2].getClassName();
		String name = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getSimpleName();
		if (name.equalsIgnoreCase("") || name == null) name = "Anonymous";
		Bukkit.getLogger().severe("[" + name + "] " + msg);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void config(String msg) {
		try {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		//classn = Thread.currentThread().getStackTrace()[2].getClassName();
		String name = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getSimpleName();
		if (name.equalsIgnoreCase("") || name == null) name = "Anonymous";
		Bukkit.getLogger().config("[" + name + "] " + msg);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void fine(String msg) {
		try {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		//classn = Thread.currentThread().getStackTrace()[2].getClassName();
		String name = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getSimpleName();
		if (name.equalsIgnoreCase("") || name == null) name = "Anonymous";
		Bukkit.getLogger().fine("[" + name + "] " + msg);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void finer(String msg) {
		try {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		//classn = Thread.currentThread().getStackTrace()[2].getClassName();
		String name = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getSimpleName();
		if (name.equalsIgnoreCase("") || name == null) name = "Anonymous";
		Bukkit.getLogger().finer("[" + name + "] " + msg);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void finest(String msg) {
		try {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		//classn = Thread.currentThread().getStackTrace()[2].getClassName();
		String name = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getSimpleName();
		if (name.equalsIgnoreCase("") || name == null) name = "Anonymous";
		Bukkit.getLogger().finest("[" + name + "] " + msg);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void debug(String msg) {
		try {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		//classn = Thread.currentThread().getStackTrace()[2].getClassName();
		String name = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getSimpleName();
		if (name.equalsIgnoreCase("") || name == null) name = "Anonymous";
		Bukkit.getLogger().info("[" + name + "] [DEBUG] " + msg);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
