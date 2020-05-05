package xyz.acrylicstyle.tomeito_core;

import com.google.common.collect.Sets;
import org.bukkit.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.help.HelpTopic;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.*;
import xyz.acrylicstyle.tomeito_api.TomeitoAPI;
import xyz.acrylicstyle.tomeito_api.command.Command;
import xyz.acrylicstyle.tomeito_api.events.block.*;
import xyz.acrylicstyle.tomeito_api.events.entity.*;
import xyz.acrylicstyle.tomeito_api.events.misc.*;
import xyz.acrylicstyle.tomeito_api.events.player.*;
import xyz.acrylicstyle.tomeito_api.events.server.*;
import xyz.acrylicstyle.tomeito_api.messaging.PluginChannelListener;
import xyz.acrylicstyle.tomeito_api.subcommand.SubCommand;
import xyz.acrylicstyle.tomeito_api.subcommand.SubCommandExecutor;
import xyz.acrylicstyle.tomeito_api.utils.DummyList;
import xyz.acrylicstyle.tomeito_api.utils.Log;
import xyz.acrylicstyle.tomeito_core.commands.DebugGroovy;
import xyz.acrylicstyle.tomeito_core.commands.DebugLegacy;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The Plugin implementation of TomeitoLib.<br />
 * Do not shade it if you plan to add TomeitoLib.jar in plugins folder, or it will fail to load.<br />
 * Or, please define TomeitoLib at softdepend or depend at least!
 */
public class TomeitoLib extends JavaPlugin implements Listener, TomeitoAPI {
    public static PluginChannelListener pcl = null;
    public static TomeitoLib instance = null;

    public TomeitoLib() {
        instance = this;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        pcl = new PluginChannelListener();
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getServicesManager().register(TomeitoAPI.class, this, this, ServicePriority.Normal);
        Bukkit.getPluginCommand("tlib").setExecutor(new TomeitoCommand());
        Log.info("Enabled TomeitoLib");
    }

    @Override
    public void onDisable() {
        Log.info("Disabled TomeitoLib");
    }

    @Override
    public void registerCommands(@NotNull String packageName) {
        CollectionList<Class<?>> classes = ReflectionHelper.findAllAnnotatedClasses(this.getClassLoader(), packageName, Command.class);
        classes.forEach(clazz -> {
            Command command = clazz.getAnnotation(Command.class);
            try {
                Log.info("Loading command: " + command.value());
                TomeitoAPI.registerCommand(command.value(), (CommandExecutor) clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private void a(Cancellable cancellableEvent, double dmg, Entity e, Entity killer, PreDeathReason reason) {
        if (e instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) e;
            if ((entity.getHealth() - dmg) <= 0) {
                if (entity instanceof Player) {
                    PlayerPreDeathEvent event = new PlayerPreDeathEvent((Player) entity, killer, reason);
                    Bukkit.getPluginManager().callEvent(event);
                    if (event.isCancelled()) cancellableEvent.setCancelled(true);
                } else {
                    EntityPreDeathEvent event = new EntityPreDeathEvent(entity, killer, reason);
                    Bukkit.getPluginManager().callEvent(event);
                    if (event.isCancelled()) cancellableEvent.setCancelled(true);
                }
            }
        }
    }

    // (Player|Entity)PreDeathEvent
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent e) {
        a(e, e.getFinalDamage(), e.getEntity(), null, PreDeathReason.UNKNOWN);
    }

    // (Player|Entity)PreDeathEvent
    // EntityDamageByPlayerEvent
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        a(e, e.getFinalDamage(), e.getEntity(), e.getDamager(), PreDeathReason.KILLED_BY_ENTITY);
        if (e.getDamager() instanceof Player) {
            EntityDamageByPlayerEvent event = new EntityDamageByPlayerEvent((Player) e.getDamager(), e.getEntity(), e.getDamage(), e.getFinalDamage());
            Bukkit.getPluginManager().callEvent(event);
            if (!event.isCancelled()) e.setDamage(event.getDamage());
            if (event.isCancelled()) e.setCancelled(true);
        }
    }

    // (Player|Entity)PreDeathEvent
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamageByBlock(EntityDamageByBlockEvent e) {
        a(e, e.getFinalDamage(), e.getEntity(), null, PreDeathReason.KILLED_BY_BLOCK);
    }

    // DispenserTNTPrimeEvent
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockDispense(BlockDispenseEvent e) {
        if (e.getItem().getType() == Material.TNT) {
            DispenserTNTPrimeEvent event = new DispenserTNTPrimeEvent(e.getBlock());
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) e.setCancelled(true);
        }
    }

    // PlayerTNTPrimeEvent
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (!e.hasItem()) return;
            if (e.getItem().getType() == Material.FLINT_AND_STEEL) {
                if (e.getClickedBlock() == null) return;
                if (e.getClickedBlock().getType() == Material.TNT) {
                    PlayerTNTPrimeEvent event = new PlayerTNTPrimeEvent(e.getClickedBlock(), e.getPlayer());
                    Bukkit.getPluginManager().callEvent(event);
                    if (event.isCancelled()) {
                        e.setCancelled(true);
                        // return;
                    }
                    // e.getClickedBlock().getLocation().getBlock().setType(Material.AIR);
                    // e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.FUSE, 1, 1);
                    // Location location = e.getClickedBlock().getLocation().add(0.5, 0, 0.5);
                    // TNTPrimed tnt = location.getWorld().spawn(location, TNTPrimed.class);
                    // tnt.setFuseTicks(event.getFuseTicks());
                    // tnt.setFireTicks(event.getFireTicks());
                    // if (event.getYield() != -1) tnt.setYield(event.getYield());
                    // if (event.getVelocity() != null) tnt.setVelocity(event.getVelocity().clone());
                }
            }
        }
    }

    // WitherSkullBlockBreakEvent
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityExplodeEvent(EntityExplodeEvent e) {
        if (e.getEntity().getType() == EntityType.WITHER_SKULL) {
            WitherSkull witherSkull = (WitherSkull) e.getEntity();
            WitherSkullBlockBreakEvent event = new WitherSkullBlockBreakEvent(e.getLocation(), e.blockList(), witherSkull, e.getYield());
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                e.setCancelled(true);
                return;
            }
            e.setYield(event.getYield());
        }
    }

    private final Set<UUID> prevPlayersOnGround = Sets.newHashSet(); // PlayerJumpEvent

    // PlayerJumpEvent
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (player.getVelocity().getY() > 0) {
            double jumpVelocity = 0.42F;
            if (player.hasPotionEffect(PotionEffectType.JUMP)) {
                AtomicInteger amplifier = new AtomicInteger();
                player.getActivePotionEffects().forEach(p -> {
                    if (p.getType() == PotionEffectType.JUMP) amplifier.set(p.getAmplifier());
                });
                jumpVelocity += (float) (amplifier.get() + 1) * 0.1F;
            }
            if (e.getPlayer().getLocation().getBlock().getType() != Material.LADDER && prevPlayersOnGround.contains(player.getUniqueId())) {
                if (!((Entity) player).isOnGround() && Double.compare(player.getVelocity().getY(), jumpVelocity) == 0) {
                    PlayerJumpEvent event = new PlayerJumpEvent(e.getPlayer(), e.getFrom().clone(), e.getTo().clone());
                    Bukkit.getServer().getPluginManager().callEvent(event);
                    if (event.isCancelled()) e.getPlayer().teleport(event.getFrom());
                }
            }
        }
        if (((Entity) player).isOnGround()) {
            prevPlayersOnGround.add(player.getUniqueId());
        } else {
            prevPlayersOnGround.remove(player.getUniqueId());
        }
    }

    private @NotNull List<String> getCommands() {
        if (Boolean.getBoolean("xyz.acrylicstyle.tomeito_core.useUnstableUnknownCommandEventISwear")) {
            List<String> commands = new ArrayList<>();
            for (HelpTopic t : Bukkit.getHelpMap().getHelpTopics())
                commands.add(t.getName().replaceAll("/", "").split(" ")[0].toLowerCase());
            return commands;
        } else return new DummyList<>();
    }

    // UnknownCommandEvent
    @EventHandler(priority = EventPriority.LOWEST)
    public void onServerCommand(ServerCommandEvent e) {
        if (!getCommands().contains(e.getCommand().replaceAll("/", "").split("\\s+")[0].toLowerCase())) {
            UnknownCommandEvent event = new UnknownCommandEvent(e.getSender(), e.getCommand());
            Bukkit.getPluginManager().callEvent(event);
            if (event.getMessage() != null) {
                e.setCommand("");
                e.getSender().sendMessage(event.getMessage());
            }
        }
    }

    // UnknownCommandEvent
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
        if (!getCommands().contains(e.getMessage().replaceAll("/", "").split("\\s+")[0].toLowerCase())) {
            UnknownCommandEvent event = new UnknownCommandEvent(e.getPlayer(), e.getMessage());
            Bukkit.getPluginManager().callEvent(event);
            if (event.getMessage() != null) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(event.getMessage());
            }
        }
    }

    private static class TomeitoCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
            if (args.length != 0) {
                if (args[0].equalsIgnoreCase("debug")) {
                    DebugGroovy.run(sender, args);
                } else if (args[0].equalsIgnoreCase("debug-legacy")) {
                    DebugLegacy.run(sender, args);
                } else sendHelp(sender);
            } else sendHelp(sender);
            return true;
        }

        private void sendHelp(CommandSender sender) {
            sender.sendMessage(ChatColor.BLUE + "--------------------------------------------------");
            sender.sendMessage(ChatColor.AQUA + "TomeitoLibrary v" + TomeitoLib.instance.getDescription().getVersion());
            sender.sendMessage(ChatColor.GREEN + " /tomeitolib debug - Useful for debug.");
            sender.sendMessage(ChatColor.GREEN + " /tomeitolib debug-legacy - Useful for debug.");
            sender.sendMessage(ChatColor.BLUE + "--------------------------------------------------");
        }
    }

    // ---------------

    public void registerCommands(@NotNull ClassLoader classLoader, @NotNull final String rootCommandName, @NotNull final String subCommandsPackage) {
        registerCommands(classLoader, rootCommandName, subCommandsPackage, (sender, command, label, args) -> true);
    }

    private final static StringCollection<List<Map.Entry<SubCommand, SubCommandExecutor>>> subCommands = new StringCollection<>();

    /**
     * Registers command with sub commands.
     * @param classLoader Class loader that will be used to load classes. System class loader will be used if left null. (which will be unable to load classes under plugin)
     * @param rootCommandName A root command name. Must be defined at plugin.yml.
     * @param subCommandsPackage Package name that contains sub commands classes. Must be annotated by SubCommand and must extend SubCommandExecutor.
     * @param postCommand A CommandExecutor that runs very first. Return false to interrupt command execution.
     */
    public void registerCommands(@Nullable ClassLoader classLoader, @NotNull final String rootCommandName, @NotNull final String subCommandsPackage, @NotNull CommandExecutor postCommand) {
        if (classLoader == null) classLoader = ClassLoader.getSystemClassLoader();
        CollectionList<Class<?>> classes = ReflectionHelper.findAllAnnotatedClasses(classLoader, subCommandsPackage, SubCommand.class);
        Log.debug("Found " + classes.size() + " classes under " + subCommandsPackage);
        registerCommands(rootCommandName, classes, postCommand);
    }

    /**
     * Registers command with sub commands.
     * @param rootCommandName A root command name. Must be defined at plugin.yml.
     * @param classes Class list that will load. All classes must implement CommandExecutor or it will fail to load.
     * @param postCommand A CommandExecutor that runs very first. Return false to interrupt command execution.
     */
    public void registerCommands(@NotNull final String rootCommandName, @NotNull final CollectionList<Class<?>> classes, @NotNull CommandExecutor postCommand) {
        final CollectionList<Map.Entry<SubCommand, SubCommandExecutor>> commands = new CollectionList<>();
        classes.forEach(clazz -> {
            SubCommand command = clazz.getAnnotation(SubCommand.class);
            try {
                SubCommandExecutor subCommandExecutor = (SubCommandExecutor) clazz.newInstance();
                commands.add(new AbstractMap.SimpleImmutableEntry<>(command, subCommandExecutor));
                Log.debug("Registered sub command at " + rootCommandName + ": " + command.name());
                subCommands.add(rootCommandName, commands);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        subCommands.forEach((s, l) -> Log.debug("Command " + s + " has " + l.size() + " sub commands"));
        TomeitoAPI.registerCommand(rootCommandName, new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
                if (!postCommand.onCommand(sender, command, label, args)) return true;
                if (args.length == 0) {
                    $sendMessage(sender);
                    return true;
                }
                List<Map.Entry<SubCommand, SubCommandExecutor>> commands = subCommands.get(rootCommandName);
                if (commands == null) throw new IllegalStateException("Root command isn't defined! (Tried to get " + rootCommandName + ")");
                List<Map.Entry<SubCommand, SubCommandExecutor>> entries = ICollectionList.asList(commands).filter(e -> e.getKey().name().equals(args[0]));
                if (entries.size() == 0) {
                    $sendMessage(sender);
                    return true;
                }
                CollectionList<String> argsList = ICollectionList.asList(args);
                argsList.shift();
                ICollectionList.asList(entries).map(Map.Entry::getValue).forEach(s -> s.onCommand(sender, argsList.toArray(new String[0])));
                return true;
            }

            @NotNull
            @Contract(pure = true)
            public String getCommandHelp(String command, String description) {
                return ChatColor.YELLOW + command + ChatColor.GRAY + " - " + ChatColor.AQUA + description;
            }

            public void $sendMessage(@NotNull CommandSender sender) {
                sender.sendMessage(ChatColor.GOLD + "-----------------------------------");
                List<Map.Entry<SubCommand, SubCommandExecutor>> commands = subCommands.get(rootCommandName);
                ICollectionList.asList(commands).map(Map.Entry::getKey).forEach(s -> sender.sendMessage(getCommandHelp(s.usage(), s.description())));
                sender.sendMessage(ChatColor.GOLD + "-----------------------------------");
            }
        });
    }
}
