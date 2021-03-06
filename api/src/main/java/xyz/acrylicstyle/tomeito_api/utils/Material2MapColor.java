package xyz.acrylicstyle.tomeito_api.utils;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A color mapping mapped with 1.8, 1.12, 1.15.2 Color table.
 * It might throw an error if your server has incompatible version.
 * This color mapping is only responsible for materials, it does not see data value.
 */
@SuppressWarnings("unused")
public enum Material2MapColor {
    MINECRAFT_1_8 {
        private final Map<Material, Byte> COLOR = new HashMap<>();

        protected void init() {
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
            COLOR.put(Material.STATIONARY_LAVA, (byte) 18);
            COLOR.put(Material.STATIONARY_WATER, (byte) 54);
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

        @Override
        @NotNull
        public Map<Material, Byte> getColorMap() { return COLOR; }
    },
    MINECRAFT_1_12 {
        private final Map<Material, Byte> COLOR = new HashMap<>();

        protected void init() {
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
            COLOR.put(Material.BIRCH_WOOD_STAIRS, (byte) 53);
            COLOR.put(Material.ENDER_STONE, (byte) 10);
            COLOR.put(Material.WOOD, (byte) 52);
            COLOR.put(Material.WATER, (byte) 50);
            COLOR.put(Material.STATIONARY_WATER, (byte) 50);
            COLOR.put(Material.STATIONARY_LAVA, (byte) 18);
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
            COLOR.put(Material.MUSHROOM_SOUP, (byte) 53);
            COLOR.put(Material.BROWN_MUSHROOM, (byte) 53);
            COLOR.put(Material.HUGE_MUSHROOM_1, (byte) 52);
            COLOR.put(Material.HUGE_MUSHROOM_2, (byte) 52);
            COLOR.put(Material.BANNER, (byte) 52);
            COLOR.put(Material.STANDING_BANNER, (byte) 52);
            COLOR.put(Material.WALL_BANNER, (byte) 52);
            COLOR.put(Material.WOOD_AXE, (byte) 53);
            COLOR.put(Material.WORKBENCH, (byte) 54);
            COLOR.put(Material.WOOD_DOOR, (byte) 53);
            COLOR.put(Material.WOODEN_DOOR, (byte) 53);
            COLOR.put(Material.WOOD_DOUBLE_STEP, (byte) 53);
            COLOR.put(Material.WOOD_PICKAXE, (byte) 53);
            COLOR.put(Material.WOOD_PLATE, (byte) 53);
            COLOR.put(Material.WOOD_SPADE, (byte) 53);
            COLOR.put(Material.WOOD_STAIRS, (byte) 53);
            COLOR.put(Material.WOOD_STEP, (byte) 53);
            COLOR.put(Material.WOOD_SWORD, (byte) 53);
            COLOR.put(Material.QUARTZ, (byte) 57);
            COLOR.put(Material.QUARTZ_BLOCK, (byte) 58);
            COLOR.put(Material.QUARTZ_ORE, (byte) 57);
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
            COLOR.put(Material.BOOKSHELF, (byte) 52);
            COLOR.put(Material.BOOK_AND_QUILL, (byte) 52);
            COLOR.put(Material.BOOK, (byte) 52);
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
            COLOR.put(Material.BRICK, (byte) 62);
            COLOR.put(Material.BRICK_STAIRS, (byte) 62);
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
            COLOR.put(Material.COCOA, (byte) 54);
            COLOR.put(Material.DROPPER, (byte) 44);
            COLOR.put(Material.DIODE, (byte) 46);
            COLOR.put(Material.REDSTONE_COMPARATOR, (byte) 46);
            COLOR.put(Material.REDSTONE_COMPARATOR_OFF, (byte) 46);
            COLOR.put(Material.REDSTONE_COMPARATOR_ON, (byte) 46);
            COLOR.put(Material.DISPENSER, (byte) 44);
            COLOR.put(Material.DAYLIGHT_DETECTOR, (byte) 44);
            COLOR.put(Material.DAYLIGHT_DETECTOR_INVERTED, (byte) 44);
            COLOR.put(Material.FENCE, (byte) 52);
            COLOR.put(Material.FENCE_GATE, (byte) 52);
            COLOR.put(Material.FURNACE, (byte) 86);
            COLOR.put(Material.GLASS, (byte) 90);
            COLOR.put(Material.STAINED_GLASS, (byte) 90);
            COLOR.put(Material.THIN_GLASS, (byte) 90);
            COLOR.put(Material.STAINED_GLASS_PANE, (byte) 90);
            COLOR.put(Material.JUKEBOX, (byte) 52);
            COLOR.put(Material.JACK_O_LANTERN, (byte) 62);
            COLOR.put(Material.NOTE_BLOCK, (byte) 52);
            COLOR.put(Material.YELLOW_FLOWER, (byte) 74);
            COLOR.put(Material.LOG, (byte) 52);
            COLOR.put(Material.LOG_2, (byte) 53);
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
            COLOR.put(Material.TRAP_DOOR, (byte) 52);
            COLOR.put(Material.STRING, (byte) 46);
            COLOR.put(Material.CARPET, (byte) 46);
            COLOR.put(Material.REDSTONE_LAMP_ON, (byte) 106);
        }

        @Override
        @NotNull
        public Map<Material, Byte> getColorMap() {
            return COLOR;
        }
    },
    MINECRAFT_1_15_2 {
        private final Map<Material, Byte> COLOR = new HashMap<>();

        protected void init() {
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
            COLOR.put(Material.LONG_GRASS, (byte) 7);
            COLOR.put(Material.SLIME_BLOCK, (byte) 1);
            COLOR.put(Material.SAND, (byte) 2);
            COLOR.put(Material.SANDSTONE, (byte) 2);
            COLOR.put(Material.BIRCH_DOOR, (byte) 2);
            COLOR.put(Material.BIRCH_FENCE, (byte) 2);
            COLOR.put(Material.BIRCH_DOOR_ITEM, (byte) 2);
            COLOR.put(Material.BIRCH_FENCE_GATE, (byte) 2);
            COLOR.put(Material.BIRCH_WOOD_STAIRS, (byte) 2);
            COLOR.put(Material.ENDER_STONE, (byte) 2);
            COLOR.put(Material.WOOD, (byte) 13);
            COLOR.put(Material.WATER, (byte) 12);
            COLOR.put(Material.GLOWSTONE, (byte) 18);
            COLOR.put(Material.BED_BLOCK, (byte) 13);
            COLOR.put(Material.LAVA, (byte) 4);
            COLOR.put(Material.RED_MUSHROOM, (byte) 28);
            COLOR.put(Material.REDSTONE_BLOCK, (byte) 4);
            COLOR.put(Material.REDSTONE_WIRE, (byte) 4);
            COLOR.put(Material.REDSTONE_ORE, (byte) 4);
            COLOR.put(Material.GLOWING_REDSTONE_ORE, (byte) 18);
            COLOR.put(Material.IRON_BLOCK, (byte) 22);
            COLOR.put(Material.IRON_DOOR_BLOCK, (byte) 22);
            COLOR.put(Material.IRON_ORE, (byte) 22);
            COLOR.put(Material.LEAVES, (byte) 19);
            COLOR.put(Material.LEAVES_2, (byte) 19);
            COLOR.put(Material.GRASS, (byte) 1);
            COLOR.put(Material.WOOL, (byte) 8);
            COLOR.put(Material.SNOW, (byte) 8);
            COLOR.put(Material.SNOW_BLOCK, (byte) 8);
            COLOR.put(Material.CLAY, (byte) 9);
            COLOR.put(Material.DIRT, (byte) 10);
            COLOR.put(Material.STONE, (byte) 11);
            COLOR.put(Material.MUSHROOM_SOUP, (byte) 28);
            COLOR.put(Material.BROWN_MUSHROOM, (byte) 26);
            COLOR.put(Material.HUGE_MUSHROOM_1, (byte) 26);
            COLOR.put(Material.HUGE_MUSHROOM_2, (byte) 26);
            COLOR.put(Material.BANNER, (byte) 11);
            COLOR.put(Material.STANDING_BANNER, (byte) 11);
            COLOR.put(Material.WALL_BANNER, (byte) 11);
            COLOR.put(Material.WOOD_AXE, (byte) 13);
            COLOR.put(Material.WORKBENCH, (byte) 13);
            COLOR.put(Material.WOOD_DOOR, (byte) 13);
            COLOR.put(Material.WOODEN_DOOR, (byte) 13);
            COLOR.put(Material.WOOD_DOUBLE_STEP, (byte) 13);
            COLOR.put(Material.WOOD_PICKAXE, (byte) 13);
            COLOR.put(Material.WOOD_PLATE, (byte) 13);
            COLOR.put(Material.WOOD_SPADE, (byte) 13);
            COLOR.put(Material.WOOD_STAIRS, (byte) 13);
            COLOR.put(Material.WOOD_STEP, (byte) 13);
            COLOR.put(Material.WOOD_SWORD, (byte) 13);
            COLOR.put(Material.QUARTZ, (byte) 14);
            COLOR.put(Material.QUARTZ_BLOCK, (byte) 14);
            COLOR.put(Material.QUARTZ_ORE, (byte) 14);
            COLOR.put(Material.QUARTZ_STAIRS, (byte) 14);
            COLOR.put(Material.SEA_LANTERN, (byte) 14);
            COLOR.put(Material.PUMPKIN, (byte) 15);
            COLOR.put(Material.ACACIA_STAIRS, (byte) 15);
            COLOR.put(Material.ACACIA_DOOR, (byte) 15);
            COLOR.put(Material.ACACIA_FENCE, (byte) 15);
            COLOR.put(Material.ACACIA_FENCE_GATE, (byte) 15);
            COLOR.put(Material.HARD_CLAY, (byte) 15);
            COLOR.put(Material.STAINED_CLAY, (byte) 15);
            COLOR.put(Material.CLAY_BRICK, (byte) 15);
            COLOR.put(Material.HAY_BLOCK, (byte) 15);
            COLOR.put(Material.MELON, (byte) 27);
            COLOR.put(Material.MELON_BLOCK, (byte) 27);
            COLOR.put(Material.MELON_STEM, (byte) 27);
            COLOR.put(Material.MYCEL, (byte) 24);
            COLOR.put(Material.ENCHANTMENT_TABLE, (byte) 24);
            COLOR.put(Material.SOUL_SAND, (byte) 26);
            COLOR.put(Material.DARK_OAK_DOOR, (byte) 34);
            COLOR.put(Material.DARK_OAK_DOOR_ITEM, (byte) 34);
            COLOR.put(Material.DARK_OAK_FENCE, (byte) 34);
            COLOR.put(Material.DARK_OAK_FENCE_GATE, (byte) 34);
            COLOR.put(Material.DARK_OAK_STAIRS, (byte) 34);
            COLOR.put(Material.ENDER_PORTAL_FRAME, (byte) 24);
            COLOR.put(Material.DRAGON_EGG, (byte) 24);
            COLOR.put(Material.OBSIDIAN, (byte) 29);
            COLOR.put(Material.COAL_BLOCK, (byte) 29);
            COLOR.put(Material.COAL_ORE, (byte) 29);
            COLOR.put(Material.GOLD_PLATE, (byte) 30);
            COLOR.put(Material.GOLD_AXE, (byte) 30);
            COLOR.put(Material.GOLD_BARDING, (byte) 30);
            COLOR.put(Material.GOLD_BLOCK, (byte) 30);
            COLOR.put(Material.GOLD_BOOTS, (byte) 30);
            COLOR.put(Material.GOLD_INGOT, (byte) 30);
            COLOR.put(Material.GOLD_NUGGET, (byte) 30);
            COLOR.put(Material.GOLD_ORE, (byte) 30);
            COLOR.put(Material.GOLDEN_APPLE, (byte) 30);
            COLOR.put(Material.DIAMOND_BLOCK, (byte) 31);
            COLOR.put(Material.PRISMARINE, (byte) 23);
            COLOR.put(Material.PRISMARINE_CRYSTALS, (byte) 23);
            COLOR.put(Material.PRISMARINE_SHARD, (byte) 23);
            COLOR.put(Material.BEACON, (byte) 31);
            COLOR.put(Material.LAPIS_BLOCK, (byte) 32);
            COLOR.put(Material.LAPIS_ORE, (byte) 32);
            COLOR.put(Material.EMERALD_ORE, (byte) 33);
            COLOR.put(Material.EMERALD_BLOCK, (byte) 33);
            COLOR.put(Material.EMERALD, (byte) 33);
            COLOR.put(Material.SPRUCE_DOOR, (byte) 26);
            COLOR.put(Material.SPRUCE_DOOR_ITEM, (byte) 26);
            COLOR.put(Material.SPRUCE_FENCE, (byte) 26);
            COLOR.put(Material.SPRUCE_FENCE_GATE, (byte) 26);
            COLOR.put(Material.SPRUCE_WOOD_STAIRS, (byte) 26);
            COLOR.put(Material.NETHER_BRICK, (byte) 28);
            COLOR.put(Material.NETHER_BRICK_ITEM, (byte) 28);
            COLOR.put(Material.NETHER_WARTS, (byte) 28);
            COLOR.put(Material.NETHERRACK, (byte) 28);
            COLOR.put(Material.NETHER_FENCE, (byte) 28);
            COLOR.put(Material.NETHER_BRICK_STAIRS, (byte) 28);
            COLOR.put(Material.NETHER_STAR, (byte) 31);
            COLOR.put(Material.ANVIL, (byte) 22);
            COLOR.put(Material.BARRIER, (byte) 0);
            COLOR.put(Material.BOOKSHELF, (byte) 52);
            COLOR.put(Material.BOOK_AND_QUILL, (byte) 26);
            COLOR.put(Material.BOOK, (byte) 26);
            COLOR.put(Material.BEDROCK, (byte) 29);
            COLOR.put(Material.DEAD_BUSH, (byte) 26);
            COLOR.put(Material.DIAMOND_AXE, (byte) 31);
            COLOR.put(Material.DIAMOND_BARDING, (byte) 31);
            COLOR.put(Material.DIAMOND_BOOTS, (byte) 31);
            COLOR.put(Material.DIAMOND_HELMET, (byte) 31);
            COLOR.put(Material.DIAMOND_CHESTPLATE, (byte) 31);
            COLOR.put(Material.DIAMOND_HOE, (byte) 31);
            COLOR.put(Material.DIAMOND_LEGGINGS, (byte) 31);
            COLOR.put(Material.DIAMOND_ORE, (byte) 31);
            COLOR.put(Material.DIAMOND_PICKAXE, (byte) 31);
            COLOR.put(Material.DIAMOND_SPADE, (byte) 31);
            COLOR.put(Material.DIAMOND_SWORD, (byte) 31);
            COLOR.put(Material.IRON_DOOR, (byte) 22);
            COLOR.put(Material.IRON_AXE, (byte) 22);
            COLOR.put(Material.IRON_BOOTS, (byte) 22);
            COLOR.put(Material.IRON_CHESTPLATE, (byte) 22);
            COLOR.put(Material.IRON_FENCE, (byte) 22);
            COLOR.put(Material.IRON_TRAPDOOR, (byte) 22);
            COLOR.put(Material.IRON_SWORD, (byte) 22);
            COLOR.put(Material.IRON_SPADE, (byte) 22);
            COLOR.put(Material.COBBLE_WALL, (byte) 11);
            COLOR.put(Material.COBBLESTONE, (byte) 11);
            COLOR.put(Material.COBBLESTONE_STAIRS, (byte) 11);
            COLOR.put(Material.MOSSY_COBBLESTONE, (byte) 11);
            COLOR.put(Material.BRICK, (byte) 11);
            COLOR.put(Material.BRICK_STAIRS, (byte) 11);
            COLOR.put(Material.DOUBLE_STONE_SLAB2, (byte) 11);
            COLOR.put(Material.ICE, (byte) 31);
            COLOR.put(Material.PACKED_ICE, (byte) 31);
            COLOR.put(Material.BREWING_STAND, (byte) 31);
            COLOR.put(Material.WEB, (byte) 14);
            COLOR.put(Material.SPONGE, (byte) 18);
            COLOR.put(Material.SIGN, (byte) 13);
            COLOR.put(Material.SIGN_POST, (byte) 13);
            COLOR.put(Material.WALL_SIGN, (byte) 13);
            COLOR.put(Material.CAKE, (byte) 14);
            COLOR.put(Material.RAILS, (byte) 12);
            COLOR.put(Material.CAULDRON, (byte) 22);
            COLOR.put(Material.CHEST, (byte) 13);
            COLOR.put(Material.TRAPPED_CHEST, (byte) 13);
            COLOR.put(Material.ENDER_CHEST, (byte) 24);
            COLOR.put(Material.CACTUS, (byte) 27);
            COLOR.put(Material.HOPPER, (byte) 22);
            COLOR.put(Material.COCOA, (byte) 26);
            COLOR.put(Material.DROPPER, (byte) 22);
            COLOR.put(Material.DIODE, (byte) 11);
            COLOR.put(Material.REDSTONE_COMPARATOR, (byte) 11);
            COLOR.put(Material.REDSTONE_COMPARATOR_OFF, (byte) 11);
            COLOR.put(Material.REDSTONE_COMPARATOR_ON, (byte) 11);
            COLOR.put(Material.DISPENSER, (byte) 11);
            COLOR.put(Material.DAYLIGHT_DETECTOR, (byte) 11);
            COLOR.put(Material.DAYLIGHT_DETECTOR_INVERTED, (byte) 11);
            COLOR.put(Material.FENCE, (byte) 13);
            COLOR.put(Material.FENCE_GATE, (byte) 13);
            COLOR.put(Material.FURNACE, (byte) 13);
            COLOR.put(Material.GLASS, (byte) 13);
            COLOR.put(Material.STAINED_GLASS, (byte) 0);
            COLOR.put(Material.THIN_GLASS, (byte) 0);
            COLOR.put(Material.STAINED_GLASS_PANE, (byte) 0);
            COLOR.put(Material.JUKEBOX, (byte) 13);
            COLOR.put(Material.JACK_O_LANTERN, (byte) 13);
            COLOR.put(Material.NOTE_BLOCK, (byte) 13);
            COLOR.put(Material.YELLOW_FLOWER, (byte) 18);
            COLOR.put(Material.LOG, (byte) 13);
            COLOR.put(Material.LOG_2, (byte) 13);
            COLOR.put(Material.PISTON_BASE, (byte) 13);
            COLOR.put(Material.PISTON_EXTENSION, (byte) 13);
            COLOR.put(Material.PISTON_MOVING_PIECE, (byte) 13);
            COLOR.put(Material.PISTON_STICKY_BASE, (byte) 13);
            COLOR.put(Material.RED_ROSE, (byte) 128);
            COLOR.put(Material.REDSTONE_LAMP_OFF, (byte) 11);
            COLOR.put(Material.SANDSTONE_STAIRS, (byte) 2);
            COLOR.put(Material.VINE, (byte) 7);
            COLOR.put(Material.SUGAR_CANE_BLOCK, (byte) 2);
            COLOR.put(Material.TNT, (byte) 4);
            COLOR.put(Material.TRAP_DOOR, (byte) 13);
            COLOR.put(Material.STRING, (byte) 0);
            COLOR.put(Material.CARPET, (byte) 8);
            COLOR.put(Material.REDSTONE_LAMP_ON, (byte) 11);
        }

        @Override
        @NotNull
        public Map<Material, Byte> getColorMap() {
            return COLOR;
        }
    },
    ;

    static {
        MINECRAFT_1_8.initSafe();
        MINECRAFT_1_12.initSafe();
        MINECRAFT_1_15_2.initSafe();
    }

    protected abstract void init() throws Throwable;

    private void initSafe() {
        try {
            this.init();
        } catch (Throwable ignore) {}
    }

    @NotNull
    public static final List<Material> INVISIBLE_BLOCKS = new ArrayList<>();

    // took from https://minecraft.gamepedia.com/Map_item_format#Original_Color_Table
    static {
        INVISIBLE_BLOCKS.add(Material.AIR);
        INVISIBLE_BLOCKS.add(Material.CAKE_BLOCK);
        INVISIBLE_BLOCKS.add(Material.POWERED_RAIL);
        INVISIBLE_BLOCKS.add(Material.DETECTOR_RAIL);
        INVISIBLE_BLOCKS.add(Material.TORCH);
        INVISIBLE_BLOCKS.add(Material.LADDER);
        INVISIBLE_BLOCKS.add(Material.LEVER);
        INVISIBLE_BLOCKS.add(Material.IRON_FENCE);
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
    }

    /**
     * Get a byte color. (from 1.8.1 Color table)
     * @param material Material of the block (Items are not guaranteed to return their color byte)
     * @return Returns byte color. Returns 44 if couldn't find or mapped with 44.
     */
    public final byte getColor(@NotNull Material material) {
        return getColorMap().getOrDefault(material, (byte) 44);
    }

    /**
     * Get a byte color. (from 1.8.1 Color table)
     * @param material Material of the block (Items are not guaranteed to return their color byte)
     * @return Returns byte color. Returns null if not found.
     */
    @Nullable
    public final Byte getColorNullable(@NotNull Material material) {
        return getColorMap().get(material);
    }

    /**
     * Color mapping, mapped by their Material and values are byte that can be used to MapRenderer.
     */
    @NotNull
    public abstract Map<Material, Byte> getColorMap();
}
