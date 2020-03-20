package xyz.acrylicstyle.tomeito_core;

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
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.CollectionList;
import util.ICollectionList;
import util.ReflectionHelper;
import util.StringCollection;
import xyz.acrylicstyle.craftbukkit.CraftItemStack;
import xyz.acrylicstyle.minecraft.NBTTagCompound;
import xyz.acrylicstyle.tomeito_core.commands.DebugGroovy;
import xyz.acrylicstyle.tomeito_core.connection.PluginChannelListener;
import xyz.acrylicstyle.tomeito_core.command.Command;
import xyz.acrylicstyle.tomeito_core.subcommand.SubCommand;
import xyz.acrylicstyle.tomeito_core.subcommand.SubCommandExecutor;
import xyz.acrylicstyle.tomeito_core.utils.Log;

import java.security.SecureRandom;
import java.util.AbstractMap;
import java.util.Map;
import java.util.UUID;

/**
 * The Plugin implementation of TomeitoLib. Do not shade it if you plan to add TomeitoLib.jar in plugins folder, or it will fail to load.
 */
public class TomeitoLib extends JavaPlugin implements Listener {
    public static PluginChannelListener pcl = null;
    private static TomeitoLib instance = null;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        pcl = new PluginChannelListener();
        Bukkit.getPluginCommand("tlib").setExecutor(new TomeitoCommand());
        Log.info("Enabled TomeitoLib");
    }

    @Override
    public void onDisable() {
        Log.info("Disabled TomeitoLib");
    }

    private static class TomeitoCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
            if (args.length != 0) {
                if (args[0].equalsIgnoreCase("debug")) {
                    DebugGroovy.run(sender, args);
                } else sendHelp(sender);
            } else sendHelp(sender);
            return true;
        }

        private void sendHelp(CommandSender sender) {
            sender.sendMessage(ChatColor.BLUE + "--------------------------------------------------");
            sender.sendMessage(ChatColor.AQUA + "TomeitoLibrary v" + TomeitoLib.instance.getDescription().getVersion());
            sender.sendMessage(ChatColor.GREEN + " /tomeitolib debug - Useful for debug.");
            sender.sendMessage(ChatColor.BLUE + "--------------------------------------------------");
        }
    }

    // ---------------

    @NotNull
    @Contract("null -> new")
    public static TextComponent getItemTooltipMessage(ItemStack item) {
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

    public static void registerCommands(@NotNull final String rootCommandName, @NotNull final String subCommandsPackage) {
        registerCommands(rootCommandName, subCommandsPackage, (sender, command, label, args) -> true);
    }

    private static StringCollection<CollectionList<Map.Entry<SubCommand, SubCommandExecutor>>> subCommands = new StringCollection<>();

    public static void registerCommands(@NotNull final String rootCommandName, @NotNull final String subCommandsPackage, @NotNull CommandExecutor postCommand) {
        CollectionList<Class<?>> classes = ReflectionHelper.findAllAnnotatedClasses(instance.getClassLoader(), subCommandsPackage, Command.class);
        classes.forEach(clazz -> {
            SubCommand command = clazz.getAnnotation(SubCommand.class);
            try {
                SubCommandExecutor subCommandExecutor = (SubCommandExecutor) clazz.newInstance();
                CollectionList<Map.Entry<SubCommand, SubCommandExecutor>> commands = new CollectionList<>();
                commands.add(new AbstractMap.SimpleEntry<>(command, subCommandExecutor));
                subCommands.add(rootCommandName, commands);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        registerCommand(rootCommandName, new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
                postCommand.onCommand(sender, command, label, args);
                if (args.length == 0) {
                    $sendMessage(sender);
                    return true;
                }
                String commandName = command.getName();
                CollectionList<Map.Entry<SubCommand, SubCommandExecutor>> commands = subCommands.get(commandName);
                if (commands == null) throw new IllegalStateException("Root command isn't defined! (Tried to get " + commandName + ")");
                CollectionList<Map.Entry<SubCommand, SubCommandExecutor>> entries = commands.filter(e -> e.getKey().name().equals(args[0]));
                if (entries.size() == 0) {
                    $sendMessage(sender);
                    return true;
                }
                CollectionList<String> argsList = ICollectionList.asList(args);
                argsList.shift();
                entries.map(Map.Entry::getValue).forEach(s -> s.onCommand(sender, argsList.toArray(new String[0])));
                return true;
            }

            public String getCommandHelp(String command, String description) {
                return ChatColor.YELLOW + command + ChatColor.GRAY + " - " + ChatColor.AQUA + description;
            }

            public void $sendMessage(CommandSender sender) {
                sender.sendMessage(ChatColor.GOLD + "-----------------------------------");
                CollectionList<Map.Entry<SubCommand, SubCommandExecutor>> commands = subCommands.get(rootCommandName);
                commands.map(Map.Entry::getKey).forEach(s -> sender.sendMessage(getCommandHelp(s.usage(), s.description())));
                sender.sendMessage(ChatColor.GOLD + "-----------------------------------");
            }
        });
    }

    public static void registerCommands(@NotNull final String packageName) {
        CollectionList<Class<?>> classes = ReflectionHelper.findAllAnnotatedClasses(instance.getClassLoader(), packageName, Command.class);
        classes.forEach(clazz -> {
            Command command = clazz.getAnnotation(Command.class);
            try {
                Log.info("Loading command: " + command.value());
                registerCommand(command.value(), (CommandExecutor) clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public static void registerCommand(String command, CommandExecutor executor) {
        PluginCommand pluginCommand = Bukkit.getPluginCommand(command);
        if (pluginCommand == null) throw new NullPointerException("Command '" + command + "' isn't defined inside plugin.yml!");
        pluginCommand.setExecutor(executor);
    }

    @NotNull
    public static UUID uuidFromStringWithoutDashes(@NotNull String s) {
        return UUID.fromString(s.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"));
    }

    /**
     * Ensure the {@link CommandSender} is a {@link Player}.
     * @return {@link Player} if {@link CommandSender} was player, null otherwise.
     * You need to run only <pre>
     *     {@link Player} player = {@link TomeitoLib}.{@link TomeitoLib#ensurePlayer(CommandSender)};<br />
     *     if (player == null) return;<br />
     *     // your code
     * </pre>
     */
    @SuppressWarnings("JavaDoc")
    @Nullable
    public static Player ensurePlayer(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be invoked from console.");
            return null;
        }
        return (Player) sender;
    }

    /**
     * Gets all online players.
     * @return All online players.
     */
    @NotNull
    public static CollectionList<Player> getOnlinePlayers() {
        CollectionList<Player> players = new CollectionList<>();
        players.addAll(Bukkit.getOnlinePlayers());
        return players;
    }

    /**
     * Gets all online operators.
     * @return All online operators.
     */
    @NotNull
    public static CollectionList<Player> getOnlineOperators() { return getOnlinePlayers().filter(Player::isOp); }

    /**
     * Random number between 0 - max.<br />
     * It isn't secure, so if you want the secure numbers, use {@link TomeitoLib#randomSecureNumber(int)}.
     * @param max Maximum random number.
     * @return Random number
     */
    public static int randomNumber(int max) { return (int) (Math.random() * max + 1); }

    /**
     * Random number between 0 - max.
     * @param max Maximum random number.
     * @return Unique random number
     */
    public static int randomSecureNumber(int max) {
        return new SecureRandom().nextInt() * max + 1;
    }

    /**
     * Checks if the target is inside the between location1 and location2.
     * @param target The target location that you want to check.
     * @param location1 Shape 1
     * @param location2 Shape 2
     * @return If the target inside the shape or not
     */
    public static boolean inside(Location target, Location location1, Location location2) {
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
    public static String secondsToTime(int seconds) {
        int minutes = (int) Math.floor((float) seconds / 60F);
        String sec = Integer.toString(seconds % 60);
        return minutes + ":" + (sec.length() == 1 ? "0" + sec : sec);
    }
}
