package tk.rht0910.tomeito_core.utils;

import org.bukkit.Bukkit;

/**
 *
 * @author tomeito0110
 * Get logger in using Bukkit.
 *
 */
public class Log {
	@SuppressWarnings("unused")
	private static String[] classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");

	public Log(String className) {
		//classn = className;
	}

	public static void info(String msg) {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		//classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".")[];
		try {
			Bukkit.getLogger().info("[" + Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getSimpleName() + "] " + msg);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void warning(String msg) {
		try {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		//classn = Thread.currentThread().getStackTrace()[2].getClassName();
		Bukkit.getLogger().warning("[" + Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getSimpleName() + "] " + msg);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void warn(String msg) {
		try {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		//classn = Thread.currentThread().getStackTrace()[2].getClassName();
		Bukkit.getLogger().warning("[" + Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getSimpleName() + "] " + msg);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void severe(String msg) {
		try {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		//classn = Thread.currentThread().getStackTrace()[2].getClassName();
		Bukkit.getLogger().severe("[" + Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getSimpleName() + "] " + msg);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void error(String msg) {
		try {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		//classn = Thread.currentThread().getStackTrace()[2].getClassName();
		Bukkit.getLogger().severe("[" + Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getSimpleName() + "] " + msg);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void config(String msg) {
		try {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		//classn = Thread.currentThread().getStackTrace()[2].getClassName();
		Bukkit.getLogger().config("[" + Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getSimpleName() + "] " + msg);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void fine(String msg) {
		try {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		//classn = Thread.currentThread().getStackTrace()[2].getClassName();
		Bukkit.getLogger().fine("[" + Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getSimpleName() + "] " + msg);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void finer(String msg) {
		try {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		//classn = Thread.currentThread().getStackTrace()[2].getClassName();
		Bukkit.getLogger().finer("[" + Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getSimpleName() + "] " + msg);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void finest(String msg) {
		try {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		//classn = Thread.currentThread().getStackTrace()[2].getClassName();
		Bukkit.getLogger().finest("[" + Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getSimpleName() + "] " + msg);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void debug(String msg) {
		try {
		classn = Thread.currentThread().getStackTrace()[2].getClassName().split(".");
		//classn = Thread.currentThread().getStackTrace()[2].getClassName();
		Bukkit.getLogger().info("[" + Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getSimpleName() + "] [DEBUG] " + msg);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
