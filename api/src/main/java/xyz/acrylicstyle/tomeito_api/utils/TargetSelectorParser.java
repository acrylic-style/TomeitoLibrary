package xyz.acrylicstyle.tomeito_api.utils;

import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import util.Collection;
import util.CollectionList;
import util.ICollectionList;
import util.StringCollection;
import util.reflect.Ref;
import xyz.acrylicstyle.tomeito_api.TomeitoAPI;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public class TargetSelectorParser {
    public static final StringCollection<Function<String, Object>> OPTION_PARSERS = new StringCollection<>();
    public static final StringCollection<EntityType> ENTITY_TYPES;

    static {
        ENTITY_TYPES = new StringCollection<>((Map<String, EntityType>) Ref.getClass(EntityType.class).getDeclaredField("NAME_MAP").accessible(true).get(null));

        // <editor-fold desc="Option Parsers">

        OPTION_PARSERS.add("c", TargetSelectorParser::parseInt);
        OPTION_PARSERS.add("team", s -> s);
        OPTION_PARSERS.add("r", TargetSelectorParser::parseInt);
        OPTION_PARSERS.add("rm", TargetSelectorParser::parseInt);
        OPTION_PARSERS.add("x", TargetSelectorParser::parseInt);
        OPTION_PARSERS.add("y", TargetSelectorParser::parseInt);
        OPTION_PARSERS.add("z", TargetSelectorParser::parseInt);
        // OPTION_PARSERS.add("tag", s -> s);
        OPTION_PARSERS.add("l", TargetSelectorParser::parseInt);
        OPTION_PARSERS.add("lm", TargetSelectorParser::parseInt);
        OPTION_PARSERS.add("m", TargetSelectorParser::parseGameMode);
        OPTION_PARSERS.add("name", s -> s);
        // OPTION_PARSERS.add("rx", PlayerSelectorParser::parseInt);
        // OPTION_PARSERS.add("rxm", PlayerSelectorParser::parseInt);
        // OPTION_PARSERS.add("ry", PlayerSelectorParser::parseInt);
        // OPTION_PARSERS.add("rym", PlayerSelectorParser::parseInt);
        OPTION_PARSERS.add("type", TargetSelectorParser::parseEntityType);

        // </editor-fold>
    }

    @SuppressWarnings("RedundantIfStatement")
    private static boolean check(Location _origin, Entity entity, Collection<String, Object> options) {
        Location loc = _origin.clone();
        if (options.containsKey("type") && entity.getType() != options.get("type")) return false;
        if (options.containsKey("x")) loc.setX((int) options.get("x"));
        if (options.containsKey("y")) loc.setY((int) options.get("y"));
        if (options.containsKey("z")) loc.setZ((int) options.get("z"));
        if (options.containsKey("r")) {
            if (entity.getLocation().distance(loc) <= (int) options.get("r")) {
                if (options.containsKey("rm") && entity.getLocation().distance(loc) < (int) options.get("rm")) return false;
            } else return false;
        }
        if (options.containsKey("rm") && entity.getLocation().distance(loc) < (int) options.get("rm")) return false;
        if (options.containsKey("name") && !entity.getName().equals(options.get("name"))) return false;
        if (entity.getType() == EntityType.PLAYER) {
            Player player = (Player) entity;
            if (options.containsKey("m") && player.getGameMode() != options.get("m")) return false;
            if (options.containsKey("team")) {
                Team team = player.getScoreboard().getTeam((String) options.get("team"));
                if (team != null && !team.hasEntry(player.getName())) return false;
            }
        }
        // todo: add 'tag'
        return true;
    }

    private static Object parseOption(String key, String value) {
        if (!OPTION_PARSERS.containsKey(key)) {
            //Log.warn("Ignoring invalid key: " + key);
            return value;
        }
        return OPTION_PARSERS.get(key).apply(value);
    }

    // <editor-fold desc="Private Value Parsers">

    private static EntityType parseEntityType(String s) {
        if (!ENTITY_TYPES.containsKey(s.toLowerCase())) {
            //Log.warn("Invalid entity type: " + s);
            return null;
        }
        return ENTITY_TYPES.get(s.toLowerCase());
    }

    private static GameMode parseGameMode(String s) {
        if (s.equals("0") || s.equalsIgnoreCase("survival") || s.equalsIgnoreCase("s")) {
            return GameMode.SURVIVAL;
        } else if (s.equalsIgnoreCase("1") || s.equalsIgnoreCase("creative") || s.equalsIgnoreCase("c")) {
            return GameMode.CREATIVE;
        } else if (s.equalsIgnoreCase("2") || s.equalsIgnoreCase("adventure") || s.equalsIgnoreCase("a")) {
            return GameMode.ADVENTURE;
        } else if (s.equalsIgnoreCase("3") || s.equalsIgnoreCase("spectator") || s.equalsIgnoreCase("sp")) {
            return GameMode.SPECTATOR;
        } else {
            //Log.warn("Ignoring unparsable gamemode: " + s);
            return null;
        }
    }

    private static int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            //Log.warn("Ignoring unparseable integer: " + s);
            return 0;
        }
    }

    // </editor-fold>

    public static @NotNull CollectionList<? extends CommandSender> parse(@NotNull CommandSender sender, @NotNull String selector) {
        CollectionList<CommandSender> players = new CollectionList<>();
        if (!selector.contains("[") && !selector.contains("]") && selector.contains(",")) {
            return ICollectionList.asList(selector.split(",")).map(Bukkit::getPlayerExact).nonNull();
        }
        String optsString = new String(selector.toCharArray()).replaceAll(".*\\[(.*)]", "$1");
        Collection<String, String> rawArgs = new Collection<>();
        for (String s : optsString.split(",")) {
            try {
                String[] s1 = s.split("=");
                rawArgs.add(s1[0], s1[1]);
            } catch (Exception e) {
                //Log.info("Ignoring unparsable selector option: '" + s + "'");
            }
        }
        Collection<String, Object> options = rawArgs.mapValues(TargetSelectorParser::parseOption);
        if (selector.startsWith("@s")) return new CollectionList<>(sender);
        if (sender instanceof BlockCommandSender) {
            BlockCommandSender commandBlock = (BlockCommandSender) sender;
            if (selector.startsWith("@p")) return new CollectionList<>(getNearestEntity(commandBlock.getBlock().getLocation(), options, EntityType.PLAYER));
            if (selector.startsWith("@e")) return getEntities(commandBlock.getBlock().getWorld().getEntities(), commandBlock.getBlock().getLocation(), options, null);
            if (selector.startsWith("@a")) return getEntities(TomeitoAPI.getOnlinePlayers(), commandBlock.getBlock().getLocation(), options, null);
            if (selector.startsWith("@r")) return new CollectionList<>(getEntities(TomeitoAPI.getOnlinePlayers(), commandBlock.getBlock().getLocation(), options, null).shuffle().first());
        } else if (sender instanceof ConsoleCommandSender) {
            // ConsoleCommandSender console = (ConsoleCommandSender) sender;
            if (selector.startsWith("@p")) {
                // Not parsable from console, they don't have the location
                return players;
            }
            if (selector.startsWith("@e")) return getEntities(w().getEntities(), new Location(w(), 0, 0, 0), options, null);
            if (selector.startsWith("@a")) return getEntities(TomeitoAPI.getOnlinePlayers(), new Location(w(), 0, 0, 0), options, null);
            if (selector.startsWith("@r")) return new CollectionList<>(getEntities(TomeitoAPI.getOnlinePlayers(), new Location(w(), 0, 0, 0), options, null).shuffle().first());
        } else if (sender instanceof Player) {
            Player player = (Player) sender;
            if (selector.startsWith("@p")) return new CollectionList<>(player);
            if (selector.startsWith("@e")) return getEntities(player.getWorld().getEntities(), player.getLocation(), options, null);
            if (selector.startsWith("@a")) return getEntities(TomeitoAPI.getOnlinePlayers(), player.getLocation(), options, null);
            if (selector.startsWith("@r")) return new CollectionList<>(getEntities(TomeitoAPI.getOnlinePlayers(), player.getLocation(), options, null).shuffle().first());
        }
        Player p = Bukkit.getPlayerExact(selector.replaceAll("(.*)\\[.*]", "$1"));
        if (p != null) players.add(p);
        return players;
    }

    private static World w() {
        return Objects.requireNonNull(Bukkit.getWorld("world"));
    }

    private static CommandSender getNearestEntity(@NotNull Location location, Collection<String, Object> options, EntityType type) {
        AtomicDouble distance = new AtomicDouble(Double.MAX_VALUE);
        AtomicReference<Entity> entity = new AtomicReference<>();
        location.getWorld().getEntities().forEach(e -> {
            double d = location.distance(e.getLocation());
            if (type != null && e.getType() == type) {
                if (check(location, e, options) && distance.get() < d) {
                    distance.set(d);
                    entity.set(e);
                }
            } else if (type == null) {
                if (check(location, e, options) && distance.get() < d) {
                    distance.set(d);
                    entity.set(e);
                }
            }
        });
        return entity.get();
    }

    @NotNull
    private static CollectionList<CommandSender> getEntities(@NotNull List<? extends Entity> entities, Location base, @NotNull Collection<String, Object> options, EntityType type) {
        CollectionList<Entity> list = new CollectionList<>();
        entities.forEach(e -> {
            if (type != null && e.getType() == type) {
                if (check(base, e, options)) {
                    list.add(e);
                }
            } else if (type == null) {
                if (check(base, e, options)) {
                    list.add(e);
                }
            }
        });
        int count = options.containsKey("c") ? (int) options.get("c") : Integer.MAX_VALUE;
        CollectionList<CommandSender> anotherList = new CollectionList<>();
        list.foreach((e, i) -> {
            if (i < count) anotherList.add(e);
        });
        return anotherList;
    }
}

