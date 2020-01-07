package xyz.acrylicstyle.tomeito_core.commands;

import groovy.util.Eval;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

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
            Object result = Eval.me(argsString);
            sender.sendMessage(ChatColor.GREEN + "Result[" + (result != null ? Modifier.toString(result.getClass().getModifiers()) : "<?>") + "](" + (result != null ? result.getClass().getCanonicalName() : "null") + "):");
            sender.sendMessage(ChatColor.GREEN + "" + result);
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "An error occurred: " + e);
            for (StackTraceElement st : e.getStackTrace()) {
                sender.sendMessage(ChatColor.RED + "    " + st.toString());
            }
        }
    }
}
