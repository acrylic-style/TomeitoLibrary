package xyz.acrylicstyle.tomeito_core.commands;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import util.ICollectionList;
import xyz.acrylicstyle.tomeito_api.utils.Log;

import java.lang.reflect.Modifier;

public class DebugGroovy {
    public static boolean enabled = false;

    public static void run(CommandSender sender, String[] args) {
        if (!enabled) {
            sender.sendMessage(ChatColor.RED + "This command is disabled.");
            return;
        }
        if (!sender.hasPermission("*") || !sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "Sorry but you don't have enough permission.");
            return;
        }
        if (args.length <= 1) {
            sender.sendMessage(ChatColor.RED + "Usage:");
            sender.sendMessage(ChatColor.RED + "/tomeitolib debug <Script>");
            return;
        }
        String argsString = ICollectionList.asList(args).thenShift().join(" ");
        try {
            Object result = eval(sender, args, argsString);
            sender.sendMessage(ChatColor.GREEN + "Result[" + (result != null ? Modifier.toString(result.getClass().getModifiers()) : "<?>") + "](" + (result != null ? result.getClass().getCanonicalName() : "null") + "):");
            sender.sendMessage(ChatColor.GREEN + "" + result);
        } catch (Throwable e) {
            sender.sendMessage(ChatColor.RED + "An error occurred: " + e.getClass().getSimpleName() + ": " + e.getMessage());
            Log.error("Error occurred on eval: ");
            e.printStackTrace();
        }
    }

    private static Object eval(CommandSender sender, String[] args, String expression) {
        Binding b = new Binding();
        b.setVariable("sender", sender);
        b.setVariable("args", args);
        b.setProperty("sender", sender);
        b.setProperty("args", args);
        return new GroovyShell(b).evaluate(expression);
    }
}
