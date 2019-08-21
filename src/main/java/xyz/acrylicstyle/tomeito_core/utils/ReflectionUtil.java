package xyz.acrylicstyle.tomeito_core.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.plugin.java.JavaPlugin;

public class ReflectionUtil {
	public static Object invokeMethodWithType(Class<?> clazz, String methodName, String arg1) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Class<?> class1 = null;
		String s = arg1;
		if (TypeUtil.isInt(s)) {
			class1 = Integer.class;
		} else if (TypeUtil.isBoolean(s)) {
			class1 = Boolean.class;
		} else if (TypeUtil.isDouble(s)) {
			class1 = Double.class;
		} else if (TypeUtil.isFloat(s)) {
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
		if (TypeUtil.isInt(s)) {
			return method.invoke(clazzz, Integer.parseInt(s));
		} else if (TypeUtil.isBoolean(s)) {
			return method.invoke(clazzz, Boolean.parseBoolean(s));
		} else if (TypeUtil.isDouble(s)) {
			return method.invoke(clazzz, Double.parseDouble(s));
		} else if (TypeUtil.isFloat(s)) {
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
		if (TypeUtil.isInt(s)) {
			class1 = Integer.class;
		} else if (TypeUtil.isBoolean(s)) {
			class1 = Boolean.class;
		} else if (TypeUtil.isDouble(s)) {
			class1 = Double.class;
		} else if (TypeUtil.isFloat(s)) {
			class1 = Float.class;
		} else {
			class1 = String.class;
		}
		if (TypeUtil.isInt(t)) {
			class2 = Integer.class;
		} else if (TypeUtil.isBoolean(t)) {
			class2 = Boolean.class;
		} else if (TypeUtil.isDouble(t)) {
			class2 = Double.class;
		} else if (TypeUtil.isFloat(t)) {
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
		if (TypeUtil.isInt(s)) {
			if (TypeUtil.isInt(t)) {
				return method.invoke(clazzz, Integer.parseInt(s), Integer.parseInt(t));
			} else if (TypeUtil.isBoolean(t)) {
				return method.invoke(clazzz, Integer.parseInt(s), Boolean.parseBoolean(t));
			} else if (TypeUtil.isDouble(t)) {
				return method.invoke(clazzz, Integer.parseInt(s), Double.parseDouble(t));
			} else if (TypeUtil.isFloat(t)) {
				return method.invoke(clazzz, Integer.parseInt(s), Float.parseFloat(t));
			} else {
				return method.invoke(clazzz, Integer.parseInt(s), t);
			}
		} else if (TypeUtil.isBoolean(s)) {
			if (TypeUtil.isInt(t)) {
				return method.invoke(clazzz, Boolean.parseBoolean(s), Integer.parseInt(t));
			} else if (TypeUtil.isBoolean(t)) {
				return method.invoke(clazzz, Boolean.parseBoolean(s), Boolean.parseBoolean(t));
			} else if (TypeUtil.isDouble(t)) {
				return method.invoke(clazzz, Boolean.parseBoolean(s), Double.parseDouble(t));
			} else if (TypeUtil.isFloat(t)) {
				return method.invoke(clazzz, Boolean.parseBoolean(s), Float.parseFloat(t));
			} else {
				return method.invoke(clazzz, Boolean.parseBoolean(s), t);
			}
		} else if (TypeUtil.isDouble(s)) {
			if (TypeUtil.isInt(t)) {
				return method.invoke(clazzz, Double.parseDouble(s), Integer.parseInt(t));
			} else if (TypeUtil.isBoolean(t)) {
				return method.invoke(clazzz, Double.parseDouble(s), Boolean.parseBoolean(t));
			} else if (TypeUtil.isDouble(t)) {
				return method.invoke(clazzz, Double.parseDouble(s), Double.parseDouble(t));
			} else if (TypeUtil.isFloat(t)) {
				return method.invoke(clazzz, Double.parseDouble(s), Float.parseFloat(t));
			} else {
				return method.invoke(clazzz, Double.parseDouble(s), t);
			}
		} else if (TypeUtil.isFloat(s)) {
			if (TypeUtil.isInt(t)) {
				return method.invoke(clazzz, Float.parseFloat(s), Integer.parseInt(t));
			} else if (TypeUtil.isBoolean(t)) {
				return method.invoke(clazzz, Float.parseFloat(s), Boolean.parseBoolean(t));
			} else if (TypeUtil.isDouble(t)) {
				return method.invoke(clazzz, Float.parseFloat(s), Double.parseDouble(t));
			} else if (TypeUtil.isFloat(t)) {
				return method.invoke(clazzz, Float.parseFloat(s), Float.parseFloat(t));
			} else {
				return method.invoke(clazzz, Float.parseFloat(s), t);
			}
		} else {
			if (TypeUtil.isInt(t)) {
				return method.invoke(clazzz, s, Integer.parseInt(t));
			} else if (TypeUtil.isBoolean(t)) {
				return method.invoke(clazzz, s, Boolean.parseBoolean(t));
			} else if (TypeUtil.isDouble(t)) {
				return method.invoke(clazzz, s, Double.parseDouble(t));
			} else if (TypeUtil.isFloat(t)) {
				return method.invoke(clazzz, s, Float.parseFloat(t));
			} else {
				return method.invoke(clazzz, s, t);
			}
		}
	}
}
