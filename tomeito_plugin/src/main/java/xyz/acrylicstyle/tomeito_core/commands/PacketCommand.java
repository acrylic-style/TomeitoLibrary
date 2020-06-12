package xyz.acrylicstyle.tomeito_core.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import util.ArgumentParser;
import util.CollectionList;
import util.ICollectionList;
import util.reflect.Ref;
import util.reflect.RefConstructor;
import xyz.acrylicstyle.minecraft.ChatMessage;
import xyz.acrylicstyle.minecraft.IChatBaseComponent;
import xyz.acrylicstyle.minecraft.Packet;
import xyz.acrylicstyle.minecraft.PlayerConnection;
import xyz.acrylicstyle.shared.NMSAPI;
import xyz.acrylicstyle.shared.OBCAPI;
import xyz.acrylicstyle.tomeito_api.utils.ReflectionUtil;
import xyz.acrylicstyle.tomeito_api.utils.TargetSelectorParser;
import xyz.acrylicstyle.tomeito_core.command.TomeitoLibTabCompleter;

public class PacketCommand {
    @SuppressWarnings("deprecation")
    public static void run(CommandSender player, String[] args) {
        if (!player.hasPermission("tomeitolib.packet")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to do this.");
            return;
        }
        CollectionList<String> arguments = new ArgumentParser(ICollectionList.asList(args).join(" ")).arguments.clone();
        if (arguments.size() < 2) {
            player.sendMessage(ChatColor.RED + "/tl packet <Player> <Packet> [args...]");
            return;
        }
        CollectionList<Player> players = TargetSelectorParser.parse(player, arguments.get(0)).filter(sender -> sender instanceof Player).map(s -> (Player) s);
        if (players.size() == 0) {
            player.sendMessage(ChatColor.RED + "Couldn't find any player with: " + arguments.get(0));
            return;
        }
        if (!TomeitoLibTabCompleter.packets.contains(arguments.get(1))) {
            player.sendMessage(ChatColor.RED + "Invalid packet: " + arguments.get(1));
            return;
        }
        CollectionList<String> a = arguments.clone();
        a.shift();
        a.shift();
        CollectionList<Object> unknownArguments = a.map(s -> {
            // Boolean
            if (s.equals("true")) {
                return true;
            } else if (s.equals("false")) {
                return false;
            }

            // Numbers
            if (s.matches("^(\\d+(\\.\\d+|))[fF]$")) {
                return Float.parseFloat(s.replaceAll("(\\d+(\\.\\d+|))[fF]", "$1"));
            } else if (s.matches("^(\\d+(\\.\\d+|))[dD]$")) {
                return Double.parseDouble(s.replaceAll("(\\d+(\\.\\d+|))[dD]", "$1"));
            } else if (s.matches("^(\\d+(\\.\\d+|))[lL]$")) {
                return Double.parseDouble(s.replaceAll("(\\d+(\\.\\d+|))[lL]", "$1"));
            } else if (s.matches("^(\\d+)[sS]$")) {
                return Short.parseShort(s.replaceAll("(\\d+)[sS]", "$1"));
            } else if (s.matches("^\\d+$")) {
                return Integer.parseInt(s.replaceAll("(\\d+)", "$1"));
            } else if (s.matches("^\\(ChatMessage\\).*")) {
                return new ChatMessage(s.replaceFirst("^\\(ChatMessage\\)(.*)", "$1"), (Object) null).getHandle();
            }
            return s;
        });
        CollectionList<Class<?>> classes = unknownArguments
                .clone()
                .map(o -> {
                    if (o.getClass().getCanonicalName().endsWith(".ChatMessage")) return IChatBaseComponent.CLASS;
                    return NMSAPI.PRIMITIVES.containsKey(o.getClass()) ? NMSAPI.PRIMITIVES.get(o.getClass()) : o.getClass();
                });
        Object packet;
        try {
            packet = Ref.forName(ReflectionUtil.getNMSPackage() + "." + arguments.get(1))
                    .getDeclaredConstructor(classes.toArray(new Class<?>[0]))
                    .accessible(true)
                    .newInstance(unknownArguments.toArray(new Object[0]));
        } catch (Exception e) {
            player.sendMessage(ChatColor.RED + "Couldn't find " + ChatColor.YELLOW + arguments.get(1) + ChatColor.RED + " constructor with: " + ChatColor.GOLD + classes.map(Class::getCanonicalName).join(ChatColor.YELLOW + ", " + ChatColor.GOLD));
            player.sendMessage(ChatColor.RED + "Found:");
            for (RefConstructor<Object> constructor : Ref.forName(ReflectionUtil.getNMSPackage() + "." + arguments.get(1)).getDeclaredConstructors()) {
                player.sendMessage(ChatColor.RED + " - "
                        + ChatColor.YELLOW + arguments.get(1) + "("
                        + ChatColor.GREEN + ICollectionList.asList(constructor.getParameterTypes()).map(Class::getCanonicalName).join(ChatColor.YELLOW + ", " + ChatColor.GREEN)
                        + ChatColor.YELLOW + ")");
            }
            return;
        }
        players.forEach(p -> {
            OBCAPI cp = OBCAPI.getEmptyOBCAPI(p, "entity.CraftPlayer");
            NMSAPI ep = NMSAPI.getEmptyNMSAPI(cp.invoke("getHandle"), "EntityPlayer");
            NMSAPI pc = NMSAPI.getEmptyNMSAPI(ep.getField("playerConnection"), "PlayerConnection");
            Ref.getMethod(PlayerConnection.CLASS, "sendPacket", Packet.CLASS).invokeObj(pc.getHandle(), packet);
        });
        player.sendMessage(ChatColor.GREEN + "Sent packet " + arguments.get(1) + " to " + ChatColor.GOLD + players.map(Player::getName).join(ChatColor.YELLOW + ", " + ChatColor.GOLD));
        if (unknownArguments.size() != 0) {
            player.sendMessage(ChatColor.GREEN + "With data: " + ChatColor.GOLD + unknownArguments.join(ChatColor.YELLOW + ", " + ChatColor.GOLD));
        }
    }
}
