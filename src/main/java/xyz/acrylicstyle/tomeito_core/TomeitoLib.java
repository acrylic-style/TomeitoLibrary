package xyz.acrylicstyle.tomeito_core;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import xyz.acrylicstyle.tomeito_core.utils.Log;


public class TomeitoLib extends JavaPlugin implements Listener {
	@Override
	public void onEnable() {
		Log.info("Enabled TomeitoLib");
	}

	@Override
	public void onDisable() {
		Log.info("Disabled TomeitoLib");
	}
}
