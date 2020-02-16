package xyz.acrylicstyle.tomeito_core.commands;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import xyz.acrylicstyle.tomeito_core.utils.Log;

import java.lang.reflect.Modifier;

public class DebugGroovy {
    public static void run(CommandSender sender, String[] args) {
        if (!sender.hasPermission("*") || !sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "Sorry but you don't have enough permission.");
            return;
        }
        if (args.length <= 1) {
            sender.sendMessage(ChatColor.RED + "Usage:");
            sender.sendMessage(ChatColor.RED + "/tomeitolib debug-groovy <Script>");
            return;
        }
        StringBuilder argsSB = new StringBuilder();
        for (int i = 1; i < args.length; i++) argsSB.append(args[i]);
        String argsString = argsSB.toString();
        try {
            Object result = eval(argsString);
            sender.sendMessage(ChatColor.GREEN + "Result[" + (result != null ? Modifier.toString(result.getClass().getModifiers()) : "<?>") + "](" + (result != null ? result.getClass().getCanonicalName() : "null") + "):");
            sender.sendMessage(ChatColor.GREEN + "" + result);
        } catch (Throwable e) {
            sender.sendMessage(ChatColor.RED + "An error occurred: " + e);
            Log.error("Error occurred on eval: ");
            e.printStackTrace();
            for (StackTraceElement st : e.getStackTrace()) {
                sender.sendMessage(ChatColor.RED + "    " + st.toString());
            }
        }
    }

    private static Object eval(String expression) {
        Binding b = new Binding();
        b.setVariable(null, null);
        GroovyShell sh = new GroovyShell(b);
        return sh.evaluate(expression);
    }
}
