package xyz.acrylicstyle.tomeito_core.commands;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import xyz.acrylicstyle.tomeito_core.utils.ReflectionUtil;

public class Debug {
	public static boolean run(CommandSender sender, String[] args) {
		if (!sender.hasPermission("*") || !sender.isOp()) {
			sender.sendMessage(ChatColor.RED + "Sorry but you don't have enough permission.");
			return true;
		}
		if (args.length <= 2) {
			sender.sendMessage(ChatColor.RED + "Usage:");
			sender.sendMessage(ChatColor.RED + "/tomeitolib debug <Class> <Field> [= [Value]] - Get / Set field.");
			sender.sendMessage(ChatColor.RED + "/tomeitolib debug <Class> <Method> ( [[arg1] [arg2]] ) - Invoke method with args.");
			return true;
		}
		try {
			boolean did = false;
			for (int i = 1; i < args.length; i++) {
				if (!did) did = true; else return true;
				Class<?> clazz = Class.forName(args[1]);
				if (ReflectionUtil.includes(args, "=")) {
					// set field, example: /tlib debug xyz.acrylicstyle.zombieescape.ZombieEscape gameStarted = true
					// args[1] -> Class
					// args[2] -> Field
					// args[3] -> =
					// args[4] -> value
					if (args.length != (ReflectionUtil.indexOf(args, "=")+2)) throw new IllegalArgumentException("Missing 1 argument after =");
					Field field = clazz.getDeclaredField(args[2]);
					field.setAccessible(true);
					Object clazzz = null;
					try {
						if (clazz.getSuperclass() != null && clazz.getSuperclass().getCanonicalName().contains("JavaPlugin")) clazzz = JavaPlugin.getProvidingPlugin(clazz);
						else clazzz = clazz.newInstance();
					} catch (Exception e) {
						clazzz = clazz;
					}
					String s = args[ReflectionUtil.indexOf(args, "=")+1];
					if (ReflectionUtil.isInt(s)) {
						field.setInt(clazzz, Integer.parseInt(s));
					} else if (ReflectionUtil.isBoolean(s)) {
						field.setBoolean(clazzz, Boolean.parseBoolean(s));
					} else if (ReflectionUtil.isDouble(s)) {
						field.setDouble(clazzz, Double.parseDouble(s));
					} else if (ReflectionUtil.isFloat(s)) {
						field.setFloat(clazzz, Float.parseFloat(s));
					} else if (s.equalsIgnoreCase("null")) {
						field.set(clazzz, null);
					} else {
						field.set(clazzz, s);
					}
					sender.sendMessage(ChatColor.GREEN + "Field[" + Modifier.toString(field.getModifiers()) + "] " + args[ReflectionUtil.indexOf(args, "=")-1] + " has been set to:");
					sender.sendMessage(ChatColor.GREEN + "" + field.get(clazzz));
				} else if (ReflectionUtil.includes(args, "(") && ReflectionUtil.includes(args, ")")) {
					// invoke method, example: /tlib debug xyz.acrylicstyle.zombieescape.ReflectionUtil.ReflectionUtil isInt ( 123 )
					// args[1] -> Class
					// args[2] -> Method
					// args[3] -> (
					// args[4] -> argument or )
					// args[5] -> argument or )
					// args[6] -> ) if args[5] was argument
					if (ReflectionUtil.indexOf(args, ")") == ReflectionUtil.indexOf(args, "(")+1) { // /tlib debug ... ( )
						Method method = clazz.getMethod(args[2]);
						Object result = method.invoke(clazz);
						sender.sendMessage(ChatColor.GREEN + "Result(" + (result != null ? result.getClass().getCanonicalName() : "null") + "):");
						sender.sendMessage(ChatColor.GREEN + "" + result);
					} else if (ReflectionUtil.indexOf(args, ")") == ReflectionUtil.indexOf(args, "(")+2) { // /tlib debug ... ( 1 )
						Object result = ReflectionUtil.invokeMethodWithType(clazz, args[2], args[4]);
						sender.sendMessage(ChatColor.GREEN + "Result(" + (result != null ? result.getClass().getCanonicalName() : "null") + "):");
					} else if (ReflectionUtil.indexOf(args, ")") == ReflectionUtil.indexOf(args, "(")+3) { // /tlib debug ... ( 1 2 )
						Object result = ReflectionUtil.invokeMethodWithType(clazz, args[2], args[4], args[5]);
						sender.sendMessage(ChatColor.GREEN + "Result(" + (result != null ? result.getClass().getCanonicalName() : "null") + "):");
						sender.sendMessage(ChatColor.GREEN + "" + result);
					}
				} else if (ReflectionUtil.includes(args, "()")) { // /tlib debug ... ()
					Method method = clazz.getDeclaredMethod(args[2]);
					method.setAccessible(true);
					Object clazzz = null;
					try {
						if (clazz.getSuperclass() != null && clazz.getSuperclass().getCanonicalName().contains("JavaPlugin")) clazzz = JavaPlugin.getProvidingPlugin(clazz);
						else clazzz = clazz.newInstance();
					} catch (Exception e) {
						clazzz = clazz;
					}
					Object result = method.invoke(clazzz);
					sender.sendMessage(ChatColor.GREEN + "Result(" + (result != null ? result.getClass().getCanonicalName() : "null") + "):");
					sender.sendMessage(ChatColor.GREEN + "" + result);
				} else if (args[2].contains("()")) { // /tlib debug ... reload()
					String methodName = args[2].replaceAll("()", "");
					Method method = clazz.getDeclaredMethod(methodName);
					method.setAccessible(true);
					Object clazzz = null;
					if (clazz.getSuperclass() != null && clazz.getSuperclass().getCanonicalName().contains("JavaPlugin")) clazzz = JavaPlugin.getProvidingPlugin(clazz);
					else clazzz = clazz.newInstance();
					Object result = method.invoke(clazzz);
					sender.sendMessage(ChatColor.GREEN + "Result(" + (result != null ? result.getClass().getCanonicalName() : "null") + "):");
					sender.sendMessage(ChatColor.GREEN + "" + result);
				} else {
					// get field, example: /tlib debug xyz.acrylicstyle.zombieescape.ZombieEscape gameStarted
					// args[1] -> Class
					// args[2] -> Field
					Field field = clazz.getDeclaredField(args[2]);
					field.setAccessible(true);
					Object clazzz = null;
					try {
						if (clazz.getSuperclass() != null && clazz.getSuperclass().getCanonicalName().contains("JavaPlugin")) clazzz = JavaPlugin.getProvidingPlugin(clazz);
						else clazzz = clazz.newInstance();
					} catch (Exception e) {
						clazzz = clazz;
					}
					sender.sendMessage(ChatColor.GREEN + "Field[" + Modifier.toString(field.getModifiers()) + "] (" + (field.get(clazzz) != null ? field.get(clazzz).getClass().getCanonicalName() : "null") + "):");
					sender.sendMessage(ChatColor.GREEN + "" + field.get(clazzz));
				}
			}
		} catch (Throwable e) {
			sender.sendMessage(ChatColor.RED + "An error occurred: " + e);
			for (StackTraceElement st : e.getStackTrace()) {
				sender.sendMessage(ChatColor.RED + "    " + st.toString());
			}
		}
		return true;
	}
}
