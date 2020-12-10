package xyz.acrylicstyle.tomeito_api;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.Callback;
import util.Collection;
import util.CollectionList;
import util.CollectionSet;
import util.ICollectionList;
import util.MathUtils;
import util.ReflectionHelper;
import util.UUIDUtil;
import util.Validate;
import util.function.StringConverter;
import util.promise.Promise;
import util.reflect.Ref;
import xyz.acrylicstyle.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import xyz.acrylicstyle.craftbukkit.v1_8_R3.util.CraftUtils;
import xyz.acrylicstyle.mcutil.lang.MCVersion;
import xyz.acrylicstyle.minecraft.v1_8_R1.NBTTagCompound;
import xyz.acrylicstyle.shared.NMSAPI;
import xyz.acrylicstyle.tomeito_api.inventory.InventoryUtils;
import xyz.acrylicstyle.tomeito_api.messaging.PluginChannelListener;
import xyz.acrylicstyle.tomeito_api.scheduler.TomeitoScheduler;
import xyz.acrylicstyle.tomeito_api.shared.ChannelConstants;
import xyz.acrylicstyle.tomeito_api.utils.ProtocolVersionRetriever;
import xyz.acrylicstyle.tomeito_api.utils.ReflectionUtil;

import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * This interface defines static methods. For instance methods, see {@link BaseTomeitoAPI}.
 */
public abstract class TomeitoAPI extends JavaPlugin implements BaseTomeitoAPI, Plugin {
    /**
     * Obtain the instance of TomeitoAPI. Requires TomeitoLib (Plugin) to work.
     * @return Instance of TomeitoAPI
     * @throws RuntimeException When couldn't find plugin
     */
    public static TomeitoAPI getInstance() {
        return (TomeitoAPI) Ref.forName("xyz.acrylicstyle.tomeito_core.TomeitoLib").getDeclaredField("instance").accessible(true).get(null);
    }

    /**
     * Obtain the instance of PluginChannelListener.
     * @return Instance of {@link PluginChannelListener}
     */
    public static PluginChannelListener getPluginChannelListener() {
        return PluginChannelListener.pcl;
    }

    public static void tryPreloadClass(@NotNull String clazz) { tryPreloadClass(clazz, false); }
    public static void tryPreloadClass(@NotNull String clazz, boolean printError) {
        try {
            Class.forName(clazz);
        } catch (ClassNotFoundException e) {
            if (printError) e.printStackTrace();
        }
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static TextComponent getItemTooltipMessage(@Nullable ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return new TextComponent();
        TextComponent text = new TextComponent();
        text.setHoverEvent(
                new HoverEvent(HoverEvent.Action.SHOW_ITEM,
                        new BaseComponent[] {
                                new TextComponent(CraftItemStack.asNMSCopy(item).save(new NBTTagCompound()).toString())
                        })
        );
        return text;
    }

    @SuppressWarnings("deprecation")
    public static void registerCommand(@NotNull String prefix, @NotNull String label, @NotNull Command command) {
        SimpleCommandMap map = (SimpleCommandMap) Ref.getClass(Bukkit.getServer().getClass())
                .getDeclaredField("commandMap")
                .accessible(true)
                .getObj(Bukkit.getServer());
        map.register(label, prefix, command);
    }

    @SuppressWarnings("deprecation")
    public static void registerCommand(@NotNull String label, @NotNull Plugin plugin) {
        SimpleCommandMap map = (SimpleCommandMap) Ref.getClass(Bukkit.getServer().getClass())
                .getDeclaredField("commandMap")
                .accessible(true)
                .getObj(Bukkit.getServer());
        PluginCommand command = Ref.getClass(PluginCommand.class).getDeclaredConstructor(String.class, Plugin.class).newInstance(label, plugin);
        map.register(label, plugin.getName().toLowerCase(), command);
    }

    public static void registerTextCommand(@NotNull String label, @NotNull Plugin plugin, @NotNull String text) {
        registerCommand(label, plugin);
        TomeitoAPI.registerCommand(label, (sender, command, label1, args) -> {
            sender.sendMessage(text);
            return true;
        });
    }

    @NotNull
    public static String getFriendlyName(@NotNull Material material) {
        Validate.notNull(material, "material cannot be null");
        String name = material.name().replaceAll("_", " ").toLowerCase();
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public static TomeitoScheduler getScheduler() { return getInstance().getTomeitoScheduler(); }

    public static void registerCommand(@NotNull String command, @NotNull CommandExecutor executor) {
        PluginCommand pluginCommand = Bukkit.getPluginCommand(command);
        if (pluginCommand == null) throw new NullPointerException("Command '" + command + "' isn't defined inside plugin.yml!");
        pluginCommand.setExecutor(executor);
    }

    public static void registerTabCompleter(@NotNull String command, @NotNull TabCompleter tabCompleter) {
        PluginCommand pluginCommand = Bukkit.getPluginCommand(command);
        if (pluginCommand == null) throw new NullPointerException("Command '" + command + "' isn't defined inside plugin.yml!");
        pluginCommand.setTabCompleter(tabCompleter);
    }

    @NotNull
    public static UUID uuidFromStringWithoutDashes(@NotNull String s) {
        return UUIDUtil.uuidFromStringWithoutDashes(s);
    }

    /**
     * Ensure the {@link CommandSender} is a {@link Player}.
     * @return {@link Player} if {@link CommandSender} was player, null otherwise.
     * You need to run only <pre>
     *     Player player = TomeitoAPI.ensurePlayer(sender);<br />
     *     if (player == null) return;<br />
     *     // your code
     * </pre> as this method sends message to the sender when does not meet requirements.
     */
    @Nullable
    public static Player ensurePlayer(@NotNull CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be invoked from console.");
            return null;
        }
        return (Player) sender;
    }

    /**
     * Gets all online players.
     * @return All online players as list.
     */
    @NotNull
    public static CollectionList<Player> getOnlinePlayers() {
        CollectionList<Player> players = new CollectionList<>();
        players.addAll(Bukkit.getOnlinePlayers());
        return players;
    }

    /**
     * Gets all online operators.
     * @return All online operators as list.
     */
    @NotNull
    public static CollectionList<Player> getOnlineOperators() { return getOnlinePlayers().filter(Player::isOp); }

    /**
     * Random number between 0 - max.<br />
     * It isn't secure, so if you want the secure numbers, use {@link TomeitoAPI#randomSecureNumber(int)}.
     * @param max Maximum random number.
     * @return Random number
     */
    public static int randomNumber(int max) { return MathUtils.randomNumber(max); }

    /**
     * Random number between 0 - max.
     * @param seed seed that will be used to generate number
     * @param max Maximum random number.
     * @return Unique random number
     */
    public static int randomSecureNumber(int max, byte[] seed) { return MathUtils.randomSecureNumber(max, seed); }

    /**
     * Random number between 0 - max.
     * @param max Maximum random number.
     * @return Unique random number
     */
    public static int randomSecureNumber(int max) { return MathUtils.randomSecureNumber(max); }

    /**
     * Checks if the target is inside the between location1 and location2.
     * @param target The target location that you want to check.
     * @param location1 Shape 1
     * @param location2 Shape 2
     * @return If the target inside the shape or not
     */
    public static boolean inside(@NotNull Location target, @NotNull Location location1, @NotNull Location location2) {
        double x = target.getX();
        double y = target.getY();
        double z = target.getZ();
        double x1 = location1.getX();
        double y1 = location1.getY();
        double z1 = location1.getZ();
        double x2 = location2.getX();
        double y2 = location2.getY();
        double z2 = location2.getZ();
        if ((x >= x1 && x <= x2) || (x >= x2 && x <= x1)) {
            if ((y >= y1 && y <= y2) || (y >= y2 && y <= y1)) {
                return (z >= z1 && z <= z2) || (z >= z2 && z <= z1);
            }
        }
        return false;
    }

    /**
     * @return A string. Examples:
     * <ul>
     *     <li>60 -> 1:00</li>
     *     <li>90 -> 1:30</li>
     * </ul>
     */
    @NotNull
    public static String secondsToTime(int seconds) {
        return MathUtils.secondsToTime(seconds);
    }

    /**
     * Converts time (hh:mm) to seconds (integer).<br />
     * Use {@link #timeToSeconds(String)} as it has better function.
     * @param time Time, must be format in hh:mm
     * @return Seconds in Integer
     * @throws IllegalArgumentException When the time isn't format in hh:mm
     */
    public static int timeToSeconds0(@NotNull String time) {
        if (!Pattern.compile("^\\d+:\\d+$").matcher(time).matches()) throw new IllegalArgumentException("Time must be format number:number.");
        String[] times = time.split(":");
        return Integer.parseInt(times[0])*60 + Integer.parseInt(times[1]);
    }

    /**
     * Converts time (hh:mm or just seconds) to seconds (integer).
     * @param s Time, must be format in seconds (it will just converts to integer) or hh:mm.
     * @return Seconds in integer
     * @throws IllegalArgumentException When couldn't parse time
     */
    public static int timeToSeconds(@NotNull String s) { return MathUtils.timeToSeconds(s); }

    /**
     * Broadcasts the sound to all players.
     * @param sound the sound to be played
     * @param pitch the pitch of the sound
     */
    public static void broadcastSound(@NotNull Sound sound, float pitch) {
        Bukkit.getOnlinePlayers().forEach(p -> p.playSound(p.getLocation(), sound, 100000F, pitch));
    }

    public static @NotNull Promise<@NotNull Integer> getProtocolVersion(@NotNull Player player) {
        return ProtocolVersionRetriever.getProtocolVersion(player);
    }

    public static @NotNull Promise<@NotNull Integer> getProtocolVersion(@NotNull Player player, @NotNull ProtocolVersionRetriever.Type type) {
        return ProtocolVersionRetriever.getProtocolVersion(player, type);
    }

    public static @NotNull Promise<@NotNull MCVersion> getSingleProtocolVersion(@NotNull Player player) {
        return getProtocolVersion(player).then(TomeitoAPI::getReleaseVersionIfPossible);
    }

    public static @NotNull Promise<@NotNull MCVersion> getSingleProtocolVersion(@NotNull Player player, @NotNull ProtocolVersionRetriever.Type type) {
        return getProtocolVersion(player, type).then(TomeitoAPI::getReleaseVersionIfPossible);
    }

    public static MCVersion getReleaseVersionIfPossible(int protocolVersion) {
        CollectionList<MCVersion> list = ICollectionList.asList(MCVersion.getByProtocolVersion(protocolVersion));
        return list.filter(v -> !v.isSnapshot()).size() == 0 // if non-snapshot version wasn't found
                ? Objects.requireNonNull(list.first()) // return the last version anyway
                : Objects.requireNonNull(list.filter(v -> !v.isSnapshot()).first()); // or return non-snapshot version instead
    }

    /**
     * Sends action bar message to specified player.
     * @param player the player to send action bar. if the null was provided, it will just return without doing anything.
     * @param message the action bar message to send. if the null was provided, it will just return without doing anything.
     */
    public static void sendActionbar(@Nullable Player player, @Nullable String message) {
        if (player == null || message == null) return;
        String nmsVersion = Bukkit.getServer().getClass().getPackage().getName();
        nmsVersion = nmsVersion.substring(nmsVersion.lastIndexOf(".") + 1);
        if (!nmsVersion.startsWith("v1_9_R") && !nmsVersion.startsWith("v1_8_R")) {
            Ref.getClass(Player.Spigot.class)
                    .getMethod("sendMessage", ChatMessageType.class, BaseComponent.class)
                    .invoke(player.spigot(), ChatMessageType.ACTION_BAR, new TextComponent(message));
            return;
        }
        try {
            Class<?> ppoc = ReflectionUtil.getNMSClass("PacketPlayOutChat");
            Class<?> chat = ReflectionUtil.getNMSClass((nmsVersion.equalsIgnoreCase("v1_8_R1") ? "ChatSerializer" : "ChatComponentText"));
            Class<?> chatBaseComponent = ReflectionUtil.getNMSClass("IChatBaseComponent");
            Method method = null;
            if (nmsVersion.equalsIgnoreCase("v1_8_R1")) method = chat.getDeclaredMethod("a", String.class);
            Object object = nmsVersion.equalsIgnoreCase("v1_8_R1") ? chatBaseComponent.cast(Objects.requireNonNull(method).invoke(chat, "{'text': '" + message + "'}")) : chat.getConstructor(String.class).newInstance(message);
            Object packetPlayOutChat = ppoc.getConstructor(chatBaseComponent, Byte.TYPE).newInstance(object, (byte) 2);
            Object playerConnection = ReflectionHelper.getFieldWithoutException(NMSAPI.getClassWithoutException("EntityPlayer"), CraftUtils.getHandle(player), "playerConnection");
            ReflectionHelper.invokeMethodWithoutException(NMSAPI.getClassWithoutException("PlayerConnection"), playerConnection, "sendPacket", packetPlayOutChat);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void broadcastActionBar(@Nullable String message) {
        if (message == null) return;
        Bukkit.getOnlinePlayers().forEach(p -> sendActionbar(p, message));
    }

    public static ItemStack createItemStack(@NotNull Material material, @Nullable String displayName) {
        return createItemStack(material, displayName, false, (String[]) null);
    }

    public static ItemStack createItemStack(@NotNull Material material, @Nullable String displayName, boolean enchanted) {
        return createItemStack(material, displayName, enchanted, (String[]) null);
    }

    public static ItemStack createItemStack(@NotNull Material material, @Nullable String displayName, @Nullable String... lore) {
        return createItemStack(material, displayName, false, lore);
    }

    public static ItemStack createItemStack(@NotNull Material material, @Nullable String displayName, boolean enchanted, @Nullable String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (displayName != null) meta.setDisplayName(displayName);
        if (lore != null) meta.setLore(Arrays.asList(lore));
        if (enchanted) {
            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 0, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Changes player's skin.
     * Requires TomeitoBungee on BungeeCord to work.
     * @param player the player who will be nicked
     * @param nick the nick, if empty, it will revert the skin to the original.
     */
    @Contract(pure = true)
    public static void changeSkin(@NotNull Player player, @NotNull String nick) {
        PluginChannelListener.pcl.sendToBungeeCord(player, ChannelConstants.SET_SKIN, player.getUniqueId().toString(), nick);
    }

    protected static final Collection<UUID, Map.Entry<Promise<Object>, StringConverter<?>>> prompts = new Collection<>();

    /**
     * Prompts text to player. Prompt will be cancelled when player quits, and returns a null.
     * @param player the player to prompt
     * @param converter the converter to use
     * @param timeout the timeout until player enters something
     * @return the result, null if player didn't send message in specified time or the player quit
     * @deprecated not working correctly
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Deprecated
    @NotNull
    public static <T> Promise<@Nullable T> prompt(@NotNull Player player, @NotNull StringConverter<T> converter, int timeout) {
        return new Promise<T>() {
            @Override
            public T apply(Object o) {
                prompts.add(player.getUniqueId(), new AbstractMap.SimpleImmutableEntry<>((Promise) this, converter));
                return waitUntilResolve(timeout);
            }
        };
    }

    public static void run(@NotNull Runnable runnable) {
        Bukkit.getScheduler().runTask(getInstance(), runnable);
    }

    public static void runAsync(@NotNull Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(getInstance(), runnable);
    }

    @NotNull
    @Contract(pure = true)
    public static Collection<Material, Integer> diff(@NotNull Inventory a, @NotNull Inventory b) {
        return new InventoryUtils(a).diff(b);
    }

    @Contract(pure = true)
    @NotNull
    public static CollectionSet<Material> getMaterials(@NotNull Inventory inventory) {
        return new InventoryUtils(inventory).getMaterials();
    }

    @Contract(pure = true)
    public static int getTotalMaterialAmount(@NotNull Inventory inventory, @NotNull Material material) {
        return new InventoryUtils(inventory).getTotalAmount(material);
    }
}
