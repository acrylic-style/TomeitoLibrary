package xyz.acrylicstyle.tomeito_core.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.plugin.java.JavaPlugin;

public class ReflectionUtil {
	/**
	 * Check if array contains needle but not case-sensitive.
	 * @param array Haystack
	 * @param needle Needle
	 * @return true if found, otherwise false
	 */
	public static boolean includes(String[] array, String needle) {
		boolean found = false;
		for (String element : array) {
			if (element.equalsIgnoreCase(needle)) found = true;
		}
		return found;
	}

	/**
	 * Check if array contains needle and that element contains all of multiple needle but not case-sensitive.
	 * @param array Haystack
	 * @param needle Needle
	 * @return true if found, otherwise false
	 */
	public static boolean contains(String[] array, String... needle) {
		boolean found = false;
		for (String element : array) {
			found = true;
			for (String e : needle) {
				if (found) found = element.contains(e);
			}
		}
		return found;
	}

	/**
	 * Returns array element index that contains needle but not case-sensitive.<br>
	 * If there are multiple needles in haystack, returns first one.
	 * @param array Haystack
	 * @param needle Needle
	 * @return a number except -1 if found, returns -1 if not found
	 */
	public static int indexOf(String[] array, String needle) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equalsIgnoreCase(needle)) return i;
		}
		return -1;
	}

	public static boolean isInt(String arg) {
		try {
			Integer.parseInt(arg);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isDouble(String arg) {
		try {
			Double.parseDouble(arg);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean parseBoolean(String bool) throws Exception {
		if (bool.equalsIgnoreCase("true")) return true;
		else if (bool.equalsIgnoreCase("false")) return false;
		else throw new Exception("Provided string is not boolean.");
	}

	public static boolean isBoolean(String arg) {
		try {
			ReflectionUtil.parseBoolean(arg);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isFloat(String arg) {
		try {
			Float.parseFloat(arg);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static Object invokeMethodWithType(Class<?> clazz, String methodName, String arg1) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Class<?> class1 = null;
		String s = arg1;
		if (ReflectionUtil.isInt(s)) {
			class1 = Integer.class;
		} else if (ReflectionUtil.isBoolean(s)) {
			class1 = Boolean.class;
		} else if (ReflectionUtil.isDouble(s)) {
			class1 = Double.class;
		} else if (ReflectionUtil.isFloat(s)) {
			class1 = Float.class;
		} else {
			class1 = String.class;
		}
		Method method = clazz.getDeclaredMethod(methodName, class1);
		method.setAccessible(true);
		Object clazzz = null;
		try {
			if (clazz.getSuperclass() != null && clazz.getSuperclass().getCanonicalName().contains("JavaPlugin")) clazzz = JavaPlugin.getProvidingPlugin(clazz);
			else clazzz = clazz.newInstance();
		} catch (Exception e) {
			clazzz = clazz;
		}
		if (ReflectionUtil.isInt(s)) {
			return method.invoke(clazzz, Integer.parseInt(s));
		} else if (ReflectionUtil.isBoolean(s)) {
			return method.invoke(clazzz, Boolean.parseBoolean(s));
		} else if (ReflectionUtil.isDouble(s)) {
			return method.invoke(clazzz, Double.parseDouble(s));
		} else if (ReflectionUtil.isFloat(s)) {
			return method.invoke(clazzz, Float.parseFloat(s));
		} else {
			return method.invoke(clazzz, s);
		}
	}

	public static Object invokeMethodWithType(Class<?> clazz, String methodName, String arg1, String arg2) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Class<?> class1 = null;
		Class<?> class2 = null;
		String s = arg1;
		String t = arg2;
		if (ReflectionUtil.isInt(s)) {
			class1 = Integer.class;
		} else if (ReflectionUtil.isBoolean(s)) {
			class1 = Boolean.class;
		} else if (ReflectionUtil.isDouble(s)) {
			class1 = Double.class;
		} else if (ReflectionUtil.isFloat(s)) {
			class1 = Float.class;
		} else {
			class1 = String.class;
		}
		if (ReflectionUtil.isInt(t)) {
			class2 = Integer.class;
		} else if (ReflectionUtil.isBoolean(t)) {
			class2 = Boolean.class;
		} else if (ReflectionUtil.isDouble(t)) {
			class2 = Double.class;
		} else if (ReflectionUtil.isFloat(t)) {
			class2 = Float.class;
		} else {
			class2 = String.class;
		}
		Method method = clazz.getDeclaredMethod(methodName, class1, class2);
		method.setAccessible(true);
		Object clazzz = null;
		try {
			if (clazz.getSuperclass() != null && clazz.getSuperclass().getCanonicalName().contains("JavaPlugin")) clazzz = JavaPlugin.getProvidingPlugin(clazz);
			else clazzz = clazz.newInstance();
		} catch (Exception e) {
			clazzz = clazz;
		}
		if (ReflectionUtil.isInt(s)) {
			if (ReflectionUtil.isInt(t)) {
				return method.invoke(clazzz, Integer.parseInt(s), Integer.parseInt(t));
			} else if (ReflectionUtil.isBoolean(t)) {
				return method.invoke(clazzz, Integer.parseInt(s), Boolean.parseBoolean(t));
			} else if (ReflectionUtil.isDouble(t)) {
				return method.invoke(clazzz, Integer.parseInt(s), Double.parseDouble(t));
			} else if (ReflectionUtil.isFloat(t)) {
				return method.invoke(clazzz, Integer.parseInt(s), Float.parseFloat(t));
			} else {
				return method.invoke(clazzz, Integer.parseInt(s), t);
			}
		} else if (ReflectionUtil.isBoolean(s)) {
			if (ReflectionUtil.isInt(t)) {
				return method.invoke(clazzz, Boolean.parseBoolean(s), Integer.parseInt(t));
			} else if (ReflectionUtil.isBoolean(t)) {
				return method.invoke(clazzz, Boolean.parseBoolean(s), Boolean.parseBoolean(t));
			} else if (ReflectionUtil.isDouble(t)) {
				return method.invoke(clazzz, Boolean.parseBoolean(s), Double.parseDouble(t));
			} else if (ReflectionUtil.isFloat(t)) {
				return method.invoke(clazzz, Boolean.parseBoolean(s), Float.parseFloat(t));
			} else {
				return method.invoke(clazzz, Boolean.parseBoolean(s), t);
			}
		} else if (ReflectionUtil.isDouble(s)) {
			if (ReflectionUtil.isInt(t)) {
				return method.invoke(clazzz, Double.parseDouble(s), Integer.parseInt(t));
			} else if (ReflectionUtil.isBoolean(t)) {
				return method.invoke(clazzz, Double.parseDouble(s), Boolean.parseBoolean(t));
			} else if (ReflectionUtil.isDouble(t)) {
				return method.invoke(clazzz, Double.parseDouble(s), Double.parseDouble(t));
			} else if (ReflectionUtil.isFloat(t)) {
				return method.invoke(clazzz, Double.parseDouble(s), Float.parseFloat(t));
			} else {
				return method.invoke(clazzz, Double.parseDouble(s), t);
			}
		} else if (ReflectionUtil.isFloat(s)) {
			if (ReflectionUtil.isInt(t)) {
				return method.invoke(clazzz, Float.parseFloat(s), Integer.parseInt(t));
			} else if (ReflectionUtil.isBoolean(t)) {
				return method.invoke(clazzz, Float.parseFloat(s), Boolean.parseBoolean(t));
			} else if (ReflectionUtil.isDouble(t)) {
				return method.invoke(clazzz, Float.parseFloat(s), Double.parseDouble(t));
			} else if (ReflectionUtil.isFloat(t)) {
				return method.invoke(clazzz, Float.parseFloat(s), Float.parseFloat(t));
			} else {
				return method.invoke(clazzz, Float.parseFloat(s), t);
			}
		} else {
			if (ReflectionUtil.isInt(t)) {
				return method.invoke(clazzz, s, Integer.parseInt(t));
			} else if (ReflectionUtil.isBoolean(t)) {
				return method.invoke(clazzz, s, Boolean.parseBoolean(t));
			} else if (ReflectionUtil.isDouble(t)) {
				return method.invoke(clazzz, s, Double.parseDouble(t));
			} else if (ReflectionUtil.isFloat(t)) {
				return method.invoke(clazzz, s, Float.parseFloat(t));
			} else {
				return method.invoke(clazzz, s, t);
			}
		}
	}
}
