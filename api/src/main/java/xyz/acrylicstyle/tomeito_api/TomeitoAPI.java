package xyz.acrylicstyle.tomeito_api;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.CollectionList;
import xyz.acrylicstyle.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import xyz.acrylicstyle.minecraft.NBTTagCompound;
import xyz.acrylicstyle.tomeito_api.messaging.PluginChannelListener;

import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * This interface defines static methods. For instance methods, see {@link BaseTomeitoAPI}.
 */
public interface TomeitoAPI extends BaseTomeitoAPI, Plugin {
    /**
     * Obtain the instance of TomeitoAPI. Requires TomeitoLib (Plugin) to work.
     * @return Instance of TomeitoAPI
     * @throws RuntimeException When couldn't find plugin
     */
    static TomeitoAPI getInstance() {
        try {
            Field field = Class.forName("xyz.acrylicstyle.tomeito_core.TomeitoLib").getDeclaredField("instance");
            field.setAccessible(true);
            return (TomeitoAPI) field.get(null);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtain the instance of PluginChannelListener.
     * @return Instance of {@link PluginChannelListener}
     */
    static PluginChannelListener getPluginChannelListener() {
        return PluginChannelListener.pcl;
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static TextComponent getItemTooltipMessage(@Nullable ItemStack item) {
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

    static void registerCommand(@NotNull String command, @NotNull CommandExecutor executor) {
        PluginCommand pluginCommand = Bukkit.getPluginCommand(command);
        if (pluginCommand == null) throw new NullPointerException("Command '" + command + "' isn't defined inside plugin.yml!");
        pluginCommand.setExecutor(executor);
    }

    static void registerTabCompleter(@NotNull String command, @NotNull TabCompleter tabCompleter) {
        PluginCommand pluginCommand = Bukkit.getPluginCommand(command);
        if (pluginCommand == null) throw new NullPointerException("Command '" + command + "' isn't defined inside plugin.yml!");
        pluginCommand.setTabCompleter(tabCompleter);
    }

    @NotNull
    static UUID uuidFromStringWithoutDashes(@NotNull String s) {
        return UUID.fromString(s.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"));
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
    @SuppressWarnings("JavaDoc")
    @Nullable
    static Player ensurePlayer(@NotNull CommandSender sender) {
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
    static CollectionList<Player> getOnlinePlayers() {
        CollectionList<Player> players = new CollectionList<>();
        players.addAll(Bukkit.getOnlinePlayers());
        return players;
    }

    /**
     * Gets all online operators.
     * @return All online operators as list.
     */
    @NotNull
    static CollectionList<Player> getOnlineOperators() { return getOnlinePlayers().filter(Player::isOp); }

    /**
     * Random number between 0 - max.<br />
     * It isn't secure, so if you want the secure numbers, use {@link TomeitoAPI#randomSecureNumber(int)}.
     * @param max Maximum random number.
     * @return Random number
     */
    static int randomNumber(int max) { return (int) (Math.random() * max + 1); }

    /**
     * Random number between 0 - max.
     * @param max Maximum random number.
     * @return Unique random number
     */
    static int randomSecureNumber(int max) { return new SecureRandom().nextInt() * max + 1; }

    /**
     * Checks if the target is inside the between location1 and location2.
     * @param target The target location that you want to check.
     * @param location1 Shape 1
     * @param location2 Shape 2
     * @return If the target inside the shape or not
     */
    static boolean inside(@NotNull Location target, @NotNull Location location1, @NotNull Location location2) {
        double x = target.getX();
        double y = target.getY();
        double z = target.getZ();
        double x1 = location1.getX();
        double y1 = location1.getY();
        double z1 = location1.getZ();
        double x2 = location2.getX();
        double y2 = location2.getY();
        double z2 = location2.getZ();
        if ((x > x1) && (x < x2)) {
            if ((y > y1) && (y < y2)) {
                return (z > z1) && (z < z2);
            }
        }
        return false;
    }

    /**
     * @param seconds seconds - it explains everything
     * @return A string. Examples:
     * <ul>
     *     <li>60 -> 1:00</li>
     *     <li>90 -> 1:30</li>
     * </ul>
     */
    @NotNull
    static String secondsToTime(int seconds) {
        int minutes = (int) Math.floor((float) seconds / 60F);
        String sec = Integer.toString(seconds % 60);
        return minutes + ":" + (sec.length() == 1 ? "0" + sec : sec);
    }

    /**
     * Converts time (hh:mm) to seconds (integer).<br />
     * Use {@link #timeToSeconds(String)} as it has better function.
     * @param time Time, must be format in hh:mm
     * @return Seconds in Integer
     * @throws IllegalArgumentException When the time isn't format in hh:mm
     */
    static int timeToSeconds0(String time) {
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
    static int timeToSeconds(String s) {
        if (Pattern.compile("^\\d+$").matcher(s).matches()) {
            return Integer.parseInt(s);
        } else {
            return timeToSeconds0(s);
        }
    }
}
