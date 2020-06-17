package xyz.acrylicstyle.tomeito_core;

import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.CollectionList;
import util.ICollectionList;
import util.ReflectionHelper;
import util.StringCollection;
import xyz.acrylicstyle.tomeito_api.TomeitoAPI;
import xyz.acrylicstyle.tomeito_api.command.Command;
import xyz.acrylicstyle.tomeito_api.events.block.DispenserTNTPrimeEvent;
import xyz.acrylicstyle.tomeito_api.events.block.PlayerTNTPrimeEvent;
import xyz.acrylicstyle.tomeito_api.events.entity.EntityPreDeathEvent;
import xyz.acrylicstyle.tomeito_api.events.entity.WitherSkullBlockBreakEvent;
import xyz.acrylicstyle.tomeito_api.events.misc.PreDeathReason;
import xyz.acrylicstyle.tomeito_api.events.player.EntityDamageByPlayerEvent;
import xyz.acrylicstyle.tomeito_api.events.player.PlayerJumpEvent;
import xyz.acrylicstyle.tomeito_api.events.player.PlayerPreDeathEvent;
import xyz.acrylicstyle.tomeito_api.messaging.PluginChannelListener;
import xyz.acrylicstyle.tomeito_api.scheduler.TomeitoScheduler;
import xyz.acrylicstyle.tomeito_api.subcommand.SubCommand;
import xyz.acrylicstyle.tomeito_api.subcommand.SubCommandExecutor;
import xyz.acrylicstyle.tomeito_api.utils.Log;
import xyz.acrylicstyle.tomeito_core.command.TomeitoLibTabCompleter;
import xyz.acrylicstyle.tomeito_core.commands.DebugGroovy;
import xyz.acrylicstyle.tomeito_core.commands.DebugLegacy;
import xyz.acrylicstyle.tomeito_core.commands.PacketCommand;
import xyz.acrylicstyle.tomeito_core.scheduler.CraftTomeitoScheduler;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * The Plugin implementation of TomeitoLib.<br />
 * Do not shade it if you plan to add TomeitoLib.jar in plugins folder, or it will fail to load.<br />
 * Or, please define TomeitoLib at softdepend or depend at least!
 */
public class TomeitoLib extends TomeitoAPI implements Listener {
    public static final Logger LOGGER = Logger.getLogger("TomeitoLib");
    /**
     * It only exists for backward compatibility.
     */
    @SuppressWarnings("unused")
    public static PluginChannelListener pcl = PluginChannelListener.pcl;
    public static TomeitoLib instance = null;

    private final CraftTomeitoScheduler scheduler = new CraftTomeitoScheduler();

    public TomeitoLib() {
        instance = this;
    }

    @Override
    public void onLoad() {
        instance = this;
        new Thread(() -> {
            Log.info("Preloading classes");
            tryPreloadClass("groovy.lang.GroovyObjectSupport");
            tryPreloadClass("org.codehaus.groovy.control.Phases");
            tryPreloadClass("groovy.lang.Binding");
            tryPreloadClass("groovy.lang.GroovyShell");
            tryPreloadClass("groovy.lang.GroovyClassLoader");
            tryPreloadClass("org.codehaus.groovy.control.CompilationFailedException");
            tryPreloadClass("groovy.security.GroovyCodeSourcePermission");
            tryPreloadClass("groovy.lang.GroovyCodeSource");
            tryPreloadClass("groovy.lang.Script");
            tryPreloadClass("org.codehaus.groovy.runtime.InvokerHelper");
            tryPreloadClass("groovy.lang.MissingMethodException");
            tryPreloadClass("groovy.lang.MissingPropertyException");
            tryPreloadClass("groovy.lang.GroovyRuntimeException");
            tryPreloadClass("org.codehaus.groovy.runtime.typehandling.NumberMathModificationInfo");
            tryPreloadClass("org.codehaus.groovy.runtime.callsite.PojoMetaMethodsSite$PojoCachedMethodSiteNoWrapNoCoerce");
            tryPreloadClass("org.codehaus.groovy.classgen.asm.OptimizingStatementWriter$FastPathData");
            Log.info("Done loading classes");
        }).start();
    }

    private static final ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
    private static final Timer timer = new Timer();

    @Override
    public void onEnable() {
        for (int i = 0; i < 300; i++) System.currentTimeMillis(); // warm up
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getServicesManager().register(TomeitoAPI.class, this, this, ServicePriority.Normal);
        Bukkit.getPluginCommand("tlib").setExecutor(new TomeitoCommand());
        Bukkit.getPluginCommand("tlib").setTabCompleter(new TomeitoLibTabCompleter());
        Log.info("Enabled TomeitoLib");
        new BukkitRunnable() {
            @Override
            public void run() {
                scheduler.tick();
            }
        }.runTaskTimer(this, 1, 1);
        pool.execute(() -> {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    pool.execute(scheduler::tickAsync);
                }
            };
            timer.scheduleAtFixedRate(task, 50, 50);
        });
    }

    @Override
    public void onDisable() {
        Log.info("Stopping timer!");
        timer.cancel();
        Log.info("Disabled TomeitoLib");
    }

    @Override
    public @NotNull TomeitoScheduler getTomeitoScheduler() { return scheduler; }

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
            } catch (ClassCastException e) {
                LOGGER.warning("Couldn't cast class to CommandExecutor!");
                LOGGER.warning("Class: " + clazz.getCanonicalName());
                LOGGER.warning("Make sure this class extends/implements CommandExecutor, then try again.");
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
            if (e.getYield() != event.getYield()) e.setYield(event.getYield());
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

    private static class TomeitoCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
            if (args.length != 0) {
                CollectionList<String> list = ICollectionList.asList(args);
                list.shift();
                String[] trimmedArgs = list.toArray(new String[0]);
                if (args[0].equalsIgnoreCase("debug")) {
                    DebugGroovy.run(sender, args);
                } else if (args[0].equalsIgnoreCase("debug-legacy")) {
                    DebugLegacy.run(sender, args);
                } else if (args[0].equalsIgnoreCase("packet")) {
                    PacketCommand.run(sender, trimmedArgs);
                } else sendHelp(sender);
            } else sendHelp(sender);
            return true;
        }

        private void sendHelp(CommandSender sender) {
            sender.sendMessage(ChatColor.BLUE + "--------------------------------------------------");
            sender.sendMessage(ChatColor.AQUA + "TomeitoLibrary v" + TomeitoLib.instance.getDescription().getVersion());
            sender.sendMessage(ChatColor.GREEN + " /tomeitolib debug - Useful for debug.");
            sender.sendMessage(ChatColor.GREEN + " /tomeitolib debug-legacy - Not Useful for debug. (legacy)");
            sender.sendMessage(ChatColor.GREEN + " /tomeitolib packet <player> <Packet> <arguments...> - Sends packet to the player");
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
    public void registerCommands(@Nullable ClassLoader classLoader, @NotNull final String rootCommandName, @NotNull final String subCommandsPackage, @Nullable CommandExecutor postCommand) {
        if (classLoader == null) classLoader = ClassLoader.getSystemClassLoader();
        CollectionList<Class<?>> classes = ReflectionHelper.findAllAnnotatedClasses(classLoader, subCommandsPackage, SubCommand.class);
        Log.info("Found " + classes.size() + " classes under " + subCommandsPackage + "(/" + rootCommandName + ")");
        Log.info("If this was unexpected(it says 0 classes), try specifying ClassLoader of plugin.");
        registerCommands(rootCommandName, classes, postCommand);
    }

    /**
     * Registers command with sub commands.
     * @param rootCommandName A root command name. Must be defined at plugin.yml.
     * @param classes Class list that will load. All classes must implement CommandExecutor or it will fail to load.
     * @param postCommand A CommandExecutor that runs very first. Return false to interrupt command execution.
     */
    public void registerCommands(@NotNull final String rootCommandName, @NotNull final CollectionList<Class<?>> classes, @Nullable CommandExecutor postCommand) {
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
            } catch (ClassCastException e) {
                LOGGER.warning("Couldn't cast class to SubCommandExecutor!");
                LOGGER.warning("Class: " + clazz.getCanonicalName());
                LOGGER.warning("Make sure this class implements SubCommandExecutor, then try again.");
            }
        });
        subCommands.forEach((s, l) -> Log.debug("Command " + s + " has " + l.size() + " sub commands"));
        @NotNull CommandExecutor finalPostCommand = postCommand == null ? ((sender, command, label, args) -> true) : postCommand;
        TomeitoAPI.registerCommand(rootCommandName, new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
                if (!finalPostCommand.onCommand(sender, command, label, args)) return true;
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
