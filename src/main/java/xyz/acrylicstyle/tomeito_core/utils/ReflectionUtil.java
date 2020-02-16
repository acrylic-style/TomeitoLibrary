package xyz.acrylicstyle.tomeito_core.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.plugin.java.JavaPlugin;

public class ReflectionUtil {
	public static Object invokeMethodWithType(Class<?> clazz, String methodName, String arg1) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Class<?> class1;
		if (TypeUtil.isInt(arg1)) {
			class1 = Integer.class;
		} else if (TypeUtil.isBoolean(arg1)) {
			class1 = Boolean.class;
		} else if (TypeUtil.isDouble(arg1)) {
			class1 = Double.class;
		} else if (TypeUtil.isFloat(arg1)) {
			class1 = Float.class;
		} else {
			class1 = String.class;
		}
		Method method = clazz.getDeclaredMethod(methodName, class1);
		method.setAccessible(true);
		Object clazzz;
		try {
			if (clazz.getSuperclass() != null && clazz.getSuperclass().getCanonicalName().contains("JavaPlugin")) clazzz = JavaPlugin.getProvidingPlugin(clazz);
			else clazzz = clazz.newInstance();
		} catch (Exception e) {
			clazzz = clazz;
		}
		if (TypeUtil.isInt(arg1)) {
			return method.invoke(clazzz, Integer.parseInt(arg1));
		} else if (TypeUtil.isBoolean(arg1)) {
			return method.invoke(clazzz, Boolean.parseBoolean(arg1));
		} else if (TypeUtil.isDouble(arg1)) {
			return method.invoke(clazzz, Double.parseDouble(arg1));
		} else if (TypeUtil.isFloat(arg1)) {
			return method.invoke(clazzz, Float.parseFloat(arg1));
		} else {
			return method.invoke(clazzz, arg1);
		}
	}

	public static Object invokeMethodWithType(Class<?> clazz, String methodName, String arg1, String arg2) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Class<?> class1 = null;
		Class<?> class2 = null;
		if (TypeUtil.isInt(arg1)) {
			class1 = Integer.class;
		} else if (TypeUtil.isBoolean(arg1)) {
			class1 = Boolean.class;
		} else if (TypeUtil.isDouble(arg1)) {
			class1 = Double.class;
		} else if (TypeUtil.isFloat(arg1)) {
			class1 = Float.class;
		} else {
			class1 = String.class;
		}
		if (TypeUtil.isInt(arg2)) {
			class2 = Integer.class;
		} else if (TypeUtil.isBoolean(arg2)) {
			class2 = Boolean.class;
		} else if (TypeUtil.isDouble(arg2)) {
			class2 = Double.class;
		} else if (TypeUtil.isFloat(arg2)) {
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
		if (TypeUtil.isInt(arg1)) {
			if (TypeUtil.isInt(arg2)) {
				return method.invoke(clazzz, Integer.parseInt(arg1), Integer.parseInt(arg2));
			} else if (TypeUtil.isBoolean(arg2)) {
				return method.invoke(clazzz, Integer.parseInt(arg1), Boolean.parseBoolean(arg2));
			} else if (TypeUtil.isDouble(arg2)) {
				return method.invoke(clazzz, Integer.parseInt(arg1), Double.parseDouble(arg2));
			} else if (TypeUtil.isFloat(arg2)) {
				return method.invoke(clazzz, Integer.parseInt(arg1), Float.parseFloat(arg2));
			} else {
				return method.invoke(clazzz, Integer.parseInt(arg1), arg2);
			}
		} else if (TypeUtil.isBoolean(arg1)) {
			if (TypeUtil.isInt(arg2)) {
				return method.invoke(clazzz, Boolean.parseBoolean(arg1), Integer.parseInt(arg2));
			} else if (TypeUtil.isBoolean(arg2)) {
				return method.invoke(clazzz, Boolean.parseBoolean(arg1), Boolean.parseBoolean(arg2));
			} else if (TypeUtil.isDouble(arg2)) {
				return method.invoke(clazzz, Boolean.parseBoolean(arg1), Double.parseDouble(arg2));
			} else if (TypeUtil.isFloat(arg2)) {
				return method.invoke(clazzz, Boolean.parseBoolean(arg1), Float.parseFloat(arg2));
			} else {
				return method.invoke(clazzz, Boolean.parseBoolean(arg1), arg2);
			}
		} else if (TypeUtil.isDouble(arg1)) {
			if (TypeUtil.isInt(arg2)) {
				return method.invoke(clazzz, Double.parseDouble(arg1), Integer.parseInt(arg2));
			} else if (TypeUtil.isBoolean(arg2)) {
				return method.invoke(clazzz, Double.parseDouble(arg1), Boolean.parseBoolean(arg2));
			} else if (TypeUtil.isDouble(arg2)) {
				return method.invoke(clazzz, Double.parseDouble(arg1), Double.parseDouble(arg2));
			} else if (TypeUtil.isFloat(arg2)) {
				return method.invoke(clazzz, Double.parseDouble(arg1), Float.parseFloat(arg2));
			} else {
				return method.invoke(clazzz, Double.parseDouble(arg1), arg2);
			}
		} else if (TypeUtil.isFloat(arg1)) {
			if (TypeUtil.isInt(arg2)) {
				return method.invoke(clazzz, Float.parseFloat(arg1), Integer.parseInt(arg2));
			} else if (TypeUtil.isBoolean(arg2)) {
				return method.invoke(clazzz, Float.parseFloat(arg1), Boolean.parseBoolean(arg2));
			} else if (TypeUtil.isDouble(arg2)) {
				return method.invoke(clazzz, Float.parseFloat(arg1), Double.parseDouble(arg2));
			} else if (TypeUtil.isFloat(arg2)) {
				return method.invoke(clazzz, Float.parseFloat(arg1), Float.parseFloat(arg2));
			} else {
				return method.invoke(clazzz, Float.parseFloat(arg1), arg2);
			}
		} else {
			if (TypeUtil.isInt(arg2)) {
				return method.invoke(clazzz, arg1, Integer.parseInt(arg2));
			} else if (TypeUtil.isBoolean(arg2)) {
				return method.invoke(clazzz, arg1, Boolean.parseBoolean(arg2));
			} else if (TypeUtil.isDouble(arg2)) {
				return method.invoke(clazzz, arg1, Double.parseDouble(arg2));
			} else if (TypeUtil.isFloat(arg2)) {
				return method.invoke(clazzz, arg1, Float.parseFloat(arg2));
			} else {
				return method.invoke(clazzz, arg1, arg2);
			}
		}
	}
}
