package xyz.acrylicstyle.tomeito_api.utils;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.Random;

public class SlimeUtils {
    public static boolean isSlimeChunk(Location location) {
        return isSlimeChunk(location.getWorld().getSeed(), location.getChunk().getX(), location.getChunk().getZ());
    }

    public static boolean isSlimeChunk(World world, double x, double z) {
        return isSlimeChunk(world.getSeed(), x, z);
    }

    public static boolean isSlimeChunk(long seed, double x, double z) {
        return isSlimeChunk(seed, (int) Math.floor(x) >> 4, (int) Math.floor(z) >> 4);
    }

    public static boolean isSlimeChunk(long seed, int chunkX, int chunkZ) {
        return new Random(seed + ((long) chunkX * chunkX * 0x4c1906) + (chunkX * 0x5ac0dbL) + ((long) chunkZ * chunkZ) * 0x4307a7L + (chunkZ * 0x5f24fL) ^ 0x3ad8025f).nextInt(10) == 0;
    }
}
