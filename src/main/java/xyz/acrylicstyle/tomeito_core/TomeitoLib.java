package xyz.acrylicstyle.tomeito_core;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import xyz.acrylicstyle.tomeito_core.commands.DebugGroovy;
import xyz.acrylicstyle.tomeito_core.connection.PluginChannelListener;
import xyz.acrylicstyle.tomeito_core.utils.Log;
import xyz.acrylicstyle.tomeito_core.utils.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class TomeitoLib extends JavaPlugin implements Listener {
    public final static String version = "1.0.0";
    public static PluginChannelListener pcl = null;

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
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (args.length != 0) {
                if (args[0].equalsIgnoreCase("debug")) {
                    DebugGroovy.run(sender, args);
                } else if (args[0].equalsIgnoreCase("debug-groovy")) {
                    DebugGroovy.run(sender, args);
                } else sendHelp(sender);
            } else sendHelp(sender);
            return true;
        }

        private void sendHelp(CommandSender sender) {
            sender.sendMessage(ChatColor.BLUE + "--------------------------------------------------");
            sender.sendMessage(ChatColor.AQUA + "TomeitoLibrary v" + version);
            sender.sendMessage(ChatColor.GREEN + " /tomeitolib debug - Useful for debug.");
            sender.sendMessage(ChatColor.BLUE + "--------------------------------------------------");
        }
    }

    public static String convertItemStackToJson(ItemStack itemStack) {
        try {
            Class<?> craftItemStackClazz = ReflectionUtil.getOBCClass("inventory.CraftItemStack");
            Method asNMSCopyMethod = craftItemStackClazz.getMethod("asNMSCopy", ItemStack.class);
            Class<?> nmsItemStackClazz = ReflectionUtil.getNMSClass("ItemStack");
            Class<?> nbtTagCompoundClazz = ReflectionUtil.getNMSClass("NBTTagCompound");
            Method saveNMSItemMethod = nmsItemStackClazz.getMethod("save", nbtTagCompoundClazz);
            Object nmsNBTTagCompound = nbtTagCompoundClazz.newInstance();
            Object nmsItemStack = asNMSCopyMethod.invoke(null, itemStack);
            Object itemAsJson = saveNMSItemMethod.invoke(nmsItemStack, nmsNBTTagCompound);
            return itemAsJson.toString();
        } catch (ClassNotFoundException
                | NoSuchMethodException
                | InstantiationException
                | IllegalAccessException
                | InvocationTargetException e) { // impossible
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unused")
    public static TextComponent getItemTooltipMessage(ItemStack item) {
        TextComponent text = new TextComponent();
        text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new BaseComponent[]{ new TextComponent(convertItemStackToJson(item)) }));
        return text;
    }
}
