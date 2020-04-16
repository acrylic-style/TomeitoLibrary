package xyz.acrylicstyle.tomeito_api.utils;

import org.bukkit.Bukkit;

public class ReflectionUtil {
    /**
     * Get org.bukkit.craftbukkit class.
     * @param clazz Class name of OBC.
     * @return Specified OBC class.
     * @throws ClassNotFoundException When couldn't find specified class
     */
    public static Class<?> getOBCClass(String clazz) throws ClassNotFoundException { return Class.forName(getCraftBukkitPackage() + "." + clazz); }

    /**
     * Get net.minecraft.server class.
     * @param clazz Class name of NMS.
     * @return Specified NMS class.
     * @throws ClassNotFoundException When couldn't find specified class
     */
    public static Class<?> getNMSClass(String clazz) throws ClassNotFoundException { return Class.forName(getNMSPackage() + "." + clazz); }

    /**
     * Get server version for reflection.
     * @return Server version like v1_15_R1
     */
    public static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    /**
     * Get org.bukkit.craftbukkit package.
     * @return org.bukkit.craftbukkit package
     */
    public static String getCraftBukkitPackage() { return "org.bukkit.craftbukkit." + getServerVersion(); }

    /**
     * Get net.minecraft.server package.
     * @return net.minecraft.server package
     */
    public static String getNMSPackage() { return "net.minecraft.server." + getServerVersion(); }
}
