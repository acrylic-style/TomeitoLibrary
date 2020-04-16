package xyz.acrylicstyle.tomeito_api.utils;

import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A color mapping mapped with 1.8.1 Color table.<br />
 * It might throw an error if your server has incompatible version.<br />
 * Still in WIP. I might update this at anytime but I think I won't unless there are bugs.
 */
@SuppressWarnings("unused")
public class Material2MapColor {
    public static final List<Material> INVISIBLE_BLOCKS = new ArrayList<>();

    /**
     * Color mapping, mapped by their Material and values are byte that can be used to MapRenderer.
     */
    public static final Map<Material, Byte> COLOR = new HashMap<>();

    // took from https://minecraft.gamepedia.com/Map_item_format#Original_Color_Table
    static {
        INVISIBLE_BLOCKS.add(Material.AIR);
        INVISIBLE_BLOCKS.add(Material.CAKE_BLOCK);
        INVISIBLE_BLOCKS.add(Material.POWERED_RAIL);
        INVISIBLE_BLOCKS.add(Material.DETECTOR_RAIL);
        INVISIBLE_BLOCKS.add(Material.TORCH);
        INVISIBLE_BLOCKS.add(Material.LADDER);
        INVISIBLE_BLOCKS.add(Material.LEVER);
        INVISIBLE_BLOCKS.add(Material.IRON_BARDING);
        INVISIBLE_BLOCKS.add(Material.PORTAL);
        INVISIBLE_BLOCKS.add(Material.ACTIVATOR_RAIL);
        INVISIBLE_BLOCKS.add(Material.SKULL);
        INVISIBLE_BLOCKS.add(Material.FLOWER_POT);
        INVISIBLE_BLOCKS.add(Material.TRIPWIRE);
        INVISIBLE_BLOCKS.add(Material.TRIPWIRE_HOOK);
        INVISIBLE_BLOCKS.add(Material.DIODE_BLOCK_ON);
        INVISIBLE_BLOCKS.add(Material.DIODE_BLOCK_OFF);
        INVISIBLE_BLOCKS.add(Material.REDSTONE_TORCH_ON);
        INVISIBLE_BLOCKS.add(Material.REDSTONE_TORCH_OFF);
        INVISIBLE_BLOCKS.add(Material.WOOD_BUTTON);
        INVISIBLE_BLOCKS.add(Material.STONE_BUTTON);

        COLOR.put(Material.AIR, (byte) 0);
        COLOR.put(Material.CAKE_BLOCK, (byte) 0);
        COLOR.put(Material.POWERED_RAIL, (byte) 0);
        COLOR.put(Material.DETECTOR_RAIL, (byte) 0);
        COLOR.put(Material.TORCH, (byte) 0);
        COLOR.put(Material.LADDER, (byte) 0);
        COLOR.put(Material.LEVER, (byte) 0);
        COLOR.put(Material.IRON_BARDING, (byte) 0);
        COLOR.put(Material.PORTAL, (byte) 0);
        COLOR.put(Material.ACTIVATOR_RAIL, (byte) 0);
        COLOR.put(Material.SKULL, (byte) 0);
        COLOR.put(Material.FLOWER_POT, (byte) 0);
        COLOR.put(Material.TRIPWIRE, (byte) 0);
        COLOR.put(Material.TRIPWIRE_HOOK, (byte) 0);
        COLOR.put(Material.DIODE_BLOCK_ON, (byte) 0);
        COLOR.put(Material.DIODE_BLOCK_OFF, (byte) 0);
        COLOR.put(Material.REDSTONE_TORCH_ON, (byte) 0);
        COLOR.put(Material.REDSTONE_TORCH_OFF, (byte) 0);
        COLOR.put(Material.WOOD_BUTTON, (byte) 0);
        COLOR.put(Material.STONE_BUTTON, (byte) 0);
        COLOR.put(Material.LONG_GRASS, (byte) 6);
        COLOR.put(Material.SLIME_BLOCK, (byte) 7);
        COLOR.put(Material.SAND, (byte) 8);
        COLOR.put(Material.SANDSTONE, (byte) 9);
        COLOR.put(Material.BIRCH_DOOR, (byte) 10);
        COLOR.put(Material.BIRCH_FENCE, (byte) 10);
        COLOR.put(Material.BIRCH_DOOR_ITEM, (byte) 10);
        COLOR.put(Material.BIRCH_FENCE_GATE, (byte) 10);
        COLOR.put(Material.BIRCH_WOOD_STAIRS, (byte) 10);
        COLOR.put(Material.ENDER_STONE, (byte) 10);
        COLOR.put(Material.WOOD, (byte) 48);
        COLOR.put(Material.WATER, (byte) 54);
        COLOR.put(Material.GLOWSTONE, (byte) 10);
        COLOR.put(Material.BED_BLOCK, (byte) 12);
        COLOR.put(Material.LAVA, (byte) 17);
        COLOR.put(Material.RED_MUSHROOM, (byte) 18);
        COLOR.put(Material.REDSTONE_BLOCK, (byte) 16);
        COLOR.put(Material.REDSTONE_WIRE, (byte) 19);
        COLOR.put(Material.REDSTONE_ORE, (byte) 17);
        COLOR.put(Material.GLOWING_REDSTONE_ORE, (byte) 18);
        COLOR.put(Material.IRON_BLOCK, (byte) 26);
        COLOR.put(Material.IRON_DOOR_BLOCK, (byte) 25);
        COLOR.put(Material.IRON_ORE, (byte) 27);
        COLOR.put(Material.LEAVES, (byte) 28);
        COLOR.put(Material.LEAVES_2, (byte) 29);
        COLOR.put(Material.GRASS, (byte) 30);
        COLOR.put(Material.WOOL, (byte) 32);
        COLOR.put(Material.SNOW, (byte) 33);
        COLOR.put(Material.SNOW_BLOCK, (byte) 34);
        COLOR.put(Material.CLAY, (byte) 36);
        COLOR.put(Material.DIRT, (byte) 40);
        COLOR.put(Material.STONE, (byte) 46);
        COLOR.put(Material.MUSHROOM_SOUP, (byte) 48);
        COLOR.put(Material.BROWN_MUSHROOM, (byte) 49);
        COLOR.put(Material.HUGE_MUSHROOM_1, (byte) 50);
        COLOR.put(Material.HUGE_MUSHROOM_2, (byte) 50);
        COLOR.put(Material.BANNER, (byte) 51);
        COLOR.put(Material.STANDING_BANNER, (byte) 51);
        COLOR.put(Material.WALL_BANNER, (byte) 51);
        COLOR.put(Material.WOOD_AXE, (byte) 51);
        COLOR.put(Material.WORKBENCH, (byte) 51);
        COLOR.put(Material.WOOD_DOOR, (byte) 51);
        COLOR.put(Material.WOODEN_DOOR, (byte) 51);
        COLOR.put(Material.WOOD_DOUBLE_STEP, (byte) 51);
        COLOR.put(Material.WOOD_PICKAXE, (byte) 51);
        COLOR.put(Material.WOOD_PLATE, (byte) 51);
        COLOR.put(Material.WOOD_SPADE, (byte) 51);
        COLOR.put(Material.WOOD_STAIRS, (byte) 51);
        COLOR.put(Material.WOOD_STEP, (byte) 51);
        COLOR.put(Material.WOOD_SWORD, (byte) 51);
        COLOR.put(Material.QUARTZ, (byte) 56);
        COLOR.put(Material.QUARTZ_BLOCK, (byte) 58);
        COLOR.put(Material.QUARTZ_ORE, (byte) 59);
        COLOR.put(Material.QUARTZ_STAIRS, (byte) 57);
        COLOR.put(Material.SEA_LANTERN, (byte) 57);
        COLOR.put(Material.PUMPKIN, (byte) 62);
        COLOR.put(Material.ACACIA_STAIRS, (byte) 62);
        COLOR.put(Material.ACACIA_DOOR, (byte) 62);
        COLOR.put(Material.ACACIA_FENCE, (byte) 62);
        COLOR.put(Material.ACACIA_FENCE_GATE, (byte) 62);
        COLOR.put(Material.HARD_CLAY, (byte) 62);
        COLOR.put(Material.STAINED_CLAY, (byte) 62);
        COLOR.put(Material.CLAY_BRICK, (byte) 63);
        COLOR.put(Material.HAY_BLOCK, (byte) 74);
        COLOR.put(Material.MELON, (byte) 76);
        COLOR.put(Material.MELON_BLOCK, (byte) 78);
        COLOR.put(Material.MELON_STEM, (byte) 76);
        COLOR.put(Material.MYCEL, (byte) 96);
        COLOR.put(Material.ENCHANTMENT_TABLE, (byte) 98);
        COLOR.put(Material.SOUL_SAND, (byte) 106);
        COLOR.put(Material.DARK_OAK_DOOR, (byte) 105);
        COLOR.put(Material.DARK_OAK_DOOR_ITEM, (byte) 105);
        COLOR.put(Material.DARK_OAK_FENCE, (byte) 105);
        COLOR.put(Material.DARK_OAK_FENCE_GATE, (byte) 105);
        COLOR.put(Material.DARK_OAK_STAIRS, (byte) 105);
        COLOR.put(Material.ENDER_PORTAL_FRAME, (byte) 111);
        COLOR.put(Material.DRAGON_EGG, (byte) 118);
        COLOR.put(Material.OBSIDIAN, (byte) 116);
        COLOR.put(Material.COAL_BLOCK, (byte) 117);
        COLOR.put(Material.COAL_ORE, (byte) 118);
        COLOR.put(Material.GOLD_PLATE, (byte) 122);
        COLOR.put(Material.GOLD_AXE, (byte) 122);
        COLOR.put(Material.GOLD_BARDING, (byte) 122);
        COLOR.put(Material.GOLD_BLOCK, (byte) 122);
        COLOR.put(Material.GOLD_BOOTS, (byte) 122);
        COLOR.put(Material.GOLD_INGOT, (byte) 122);
        COLOR.put(Material.GOLD_NUGGET, (byte) 122);
        COLOR.put(Material.GOLD_ORE, (byte) 123);
        COLOR.put(Material.GOLDEN_APPLE, (byte) 122);
        COLOR.put(Material.DIAMOND_BLOCK, (byte) 126);
        COLOR.put(Material.PRISMARINE, (byte) 124);
        COLOR.put(Material.PRISMARINE_CRYSTALS, (byte) 125);
        COLOR.put(Material.PRISMARINE_SHARD, (byte) 127);
        COLOR.put(Material.BEACON, (byte) 126);
        COLOR.put(Material.LAPIS_BLOCK, (byte) 131);
        COLOR.put(Material.LAPIS_ORE, (byte) 128);
        COLOR.put(Material.EMERALD_ORE, (byte) 134);
        COLOR.put(Material.EMERALD_BLOCK, (byte) 134);
        COLOR.put(Material.EMERALD, (byte) 132);
        COLOR.put(Material.SPRUCE_DOOR, (byte) 138);
        COLOR.put(Material.SPRUCE_DOOR_ITEM, (byte) 138);
        COLOR.put(Material.SPRUCE_FENCE, (byte) 138);
        COLOR.put(Material.SPRUCE_FENCE_GATE, (byte) 138);
        COLOR.put(Material.SPRUCE_WOOD_STAIRS, (byte) 138);
        COLOR.put(Material.NETHER_BRICK, (byte) 142);
        COLOR.put(Material.NETHER_BRICK_ITEM, (byte) 143);
        COLOR.put(Material.NETHER_WARTS, (byte) 141);
        COLOR.put(Material.NETHERRACK, (byte) 140);
        COLOR.put(Material.NETHER_FENCE, (byte) 141);
        COLOR.put(Material.NETHER_BRICK_STAIRS, (byte) 141);
        COLOR.put(Material.NETHER_STAR, (byte) 126);
        COLOR.put(Material.ANVIL, (byte) 87);
        COLOR.put(Material.BARRIER, (byte) 0);
        COLOR.put(Material.BOOKSHELF, (byte) 50);
        COLOR.put(Material.BOOK_AND_QUILL, (byte) 50);
        COLOR.put(Material.BOOK, (byte) 50);
        COLOR.put(Material.BEDROCK, (byte) 87);
        COLOR.put(Material.DEAD_BUSH, (byte) 106);
        COLOR.put(Material.DIAMOND_AXE, (byte) 125);
        COLOR.put(Material.DIAMOND_BARDING, (byte) 125);
        COLOR.put(Material.DIAMOND_BOOTS, (byte) 125);
        COLOR.put(Material.DIAMOND_HELMET, (byte) 125);
        COLOR.put(Material.DIAMOND_CHESTPLATE, (byte) 125);
        COLOR.put(Material.DIAMOND_HOE, (byte) 125);
        COLOR.put(Material.DIAMOND_LEGGINGS, (byte) 125);
        COLOR.put(Material.DIAMOND_ORE, (byte) 126);
        COLOR.put(Material.DIAMOND_PICKAXE, (byte) 125);
        COLOR.put(Material.DIAMOND_SPADE, (byte) 125);
        COLOR.put(Material.DIAMOND_SWORD, (byte) 125);
        COLOR.put(Material.IRON_DOOR, (byte) 26);
        COLOR.put(Material.IRON_AXE, (byte) 26);
        COLOR.put(Material.IRON_BOOTS, (byte) 26);
        COLOR.put(Material.IRON_CHESTPLATE, (byte) 26);
        COLOR.put(Material.IRON_FENCE, (byte) 26);
        COLOR.put(Material.IRON_TRAPDOOR, (byte) 26);
        COLOR.put(Material.IRON_SWORD, (byte) 26);
        COLOR.put(Material.IRON_SPADE, (byte) 26);
        COLOR.put(Material.COBBLE_WALL, (byte) 45);
        COLOR.put(Material.COBBLESTONE, (byte) 45);
        COLOR.put(Material.COBBLESTONE_STAIRS, (byte) 45);
        COLOR.put(Material.MOSSY_COBBLESTONE, (byte) 45);
        COLOR.put(Material.BRICK, (byte) 46);
        COLOR.put(Material.BRICK_STAIRS, (byte) 46);
        COLOR.put(Material.DOUBLE_STONE_SLAB2, (byte) 46);
        COLOR.put(Material.ICE, (byte) 21);
        COLOR.put(Material.PACKED_ICE, (byte) 22);
        COLOR.put(Material.BREWING_STAND, (byte) 26);
        COLOR.put(Material.WEB, (byte) 14);
        COLOR.put(Material.SPONGE, (byte) 73);
        COLOR.put(Material.SIGN, (byte) 50);
        COLOR.put(Material.SIGN_POST, (byte) 50);
        COLOR.put(Material.WALL_SIGN, (byte) 50);
        COLOR.put(Material.CAKE, (byte) 82);
        COLOR.put(Material.RAILS, (byte) 90);
        COLOR.put(Material.CAULDRON, (byte) 90);
        COLOR.put(Material.CHEST, (byte) 50);
        COLOR.put(Material.TRAPPED_CHEST, (byte) 50);
        COLOR.put(Material.ENDER_CHEST, (byte) 96);
        COLOR.put(Material.CACTUS, (byte) 30);
        COLOR.put(Material.HOPPER, (byte) 47);
        COLOR.put(Material.COCOA, (byte) 49);
        COLOR.put(Material.DROPPER, (byte) 44);
        COLOR.put(Material.DIODE, (byte) 46);
        COLOR.put(Material.REDSTONE_COMPARATOR, (byte) 46);
        COLOR.put(Material.REDSTONE_COMPARATOR_OFF, (byte) 46);
        COLOR.put(Material.REDSTONE_COMPARATOR_ON, (byte) 46);
        COLOR.put(Material.DISPENSER, (byte) 44);
        COLOR.put(Material.DAYLIGHT_DETECTOR, (byte) 44);
        COLOR.put(Material.DAYLIGHT_DETECTOR_INVERTED, (byte) 44);
        COLOR.put(Material.FENCE, (byte) 50);
        COLOR.put(Material.FENCE_GATE, (byte) 50);
        COLOR.put(Material.FURNACE, (byte) 86);
        COLOR.put(Material.GLASS, (byte) 90);
        COLOR.put(Material.STAINED_GLASS, (byte) 90);
        COLOR.put(Material.THIN_GLASS, (byte) 90);
        COLOR.put(Material.STAINED_GLASS_PANE, (byte) 90);
        COLOR.put(Material.JUKEBOX, (byte) 50);
        COLOR.put(Material.JACK_O_LANTERN, (byte) 62);
        COLOR.put(Material.NOTE_BLOCK, (byte) 50);
        COLOR.put(Material.YELLOW_FLOWER, (byte) 74);
        COLOR.put(Material.LOG, (byte) 50);
        COLOR.put(Material.LOG_2, (byte) 50);
        COLOR.put(Material.PISTON_BASE, (byte) 35);
        COLOR.put(Material.PISTON_EXTENSION, (byte) 35);
        COLOR.put(Material.PISTON_MOVING_PIECE, (byte) 35);
        COLOR.put(Material.PISTON_STICKY_BASE, (byte) 35);
        COLOR.put(Material.RED_ROSE, (byte) 114);
        COLOR.put(Material.REDSTONE_LAMP_OFF, (byte) 106);
        COLOR.put(Material.SANDSTONE_STAIRS, (byte) 9);
        COLOR.put(Material.VINE, (byte) 30);
        COLOR.put(Material.SUGAR_CANE_BLOCK, (byte) 6);
        COLOR.put(Material.TNT, (byte) 16);
        COLOR.put(Material.TRAP_DOOR, (byte) 50);
        COLOR.put(Material.STRING, (byte) 46);
        COLOR.put(Material.CARPET, (byte) 46);
        COLOR.put(Material.REDSTONE_LAMP_ON, (byte) 106);
    }

    /**
     * Get a byte color. (from 1.8.1 Color table)
     * @param material Material of the block (Items are not guaranteed to return their color byte)
     * @return Returns byte color. Returns 44 if couldn't find or mapped with 44.
     */
    public static byte getColor(Material material) {
        return COLOR.getOrDefault(material, (byte) 44);
    }

    /**
     * Get a byte color. (from 1.8.1 Color table)
     * @param material Material of the block (Items are not guaranteed to return their color byte)
     * @return Returns byte color. Returns null if not found.
     */
    @Nullable
    public static Byte getColorNullable(Material material) {
        return COLOR.get(material);
    }
}
