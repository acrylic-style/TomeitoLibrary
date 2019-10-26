package xyz.acrylicstyle.tomeito_core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import xyz.acrylicstyle.tomeito_core.commands.Debug;
import xyz.acrylicstyle.tomeito_core.connection.PluginChannelListener;
import xyz.acrylicstyle.tomeito_core.utils.Log;


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

	public final class TomeitoCommand implements CommandExecutor {
		@Override
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
			if (args.length != 0) {
				if (args[0].equalsIgnoreCase("debug")) {
					Debug.run(sender, args);
				} else {
					sender.sendMessage(ChatColor.BLUE + "--------------------------------------------------");
					sender.sendMessage(ChatColor.AQUA + "TomeitoLibrary v" + version);
					sender.sendMessage(ChatColor.GREEN + " /tomeitolib debug - Useful for debug something but it's not complete debug tool.");
					sender.sendMessage(ChatColor.BLUE + "--------------------------------------------------");
				}
			} else {
				sender.sendMessage(ChatColor.BLUE + "--------------------------------------------------");
				sender.sendMessage(ChatColor.AQUA + "TomeitoLibrary v" + version);
				sender.sendMessage(ChatColor.GREEN + " /tomeitolib debug - Useful for debug something but it's not complete debug tool.");
				sender.sendMessage(ChatColor.BLUE + "--------------------------------------------------");
			}
			return true;
		}
	}
}
