package xyz.acrylicstyle.tomeito_core.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.lang.reflect.Modifier;

public class DebugGroovy {
    public static final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("groovy");

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
            Object result = scriptEngine.eval(argsString);
            sender.sendMessage(ChatColor.GREEN + "Result[" + (result != null ? Modifier.toString(result.getClass().getModifiers()) : "<?>") + "](" + (result != null ? result.getClass().getCanonicalName() : "null") + "):");
            sender.sendMessage(ChatColor.GREEN + "" + result);
        } catch (ScriptException e) {
            sender.sendMessage(ChatColor.RED + "An error occurred: " + e);
            for (StackTraceElement st : e.getStackTrace()) {
                sender.sendMessage(ChatColor.RED + "    " + st.toString());
            }
        }
    }
}
