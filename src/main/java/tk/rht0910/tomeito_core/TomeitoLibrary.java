package tk.rht0910.tomeito_core;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import tk.rht0910.tomeito_core.utils.Log;

public class TomeitoLibrary extends JavaPlugin implements Listener {
	@Override
	public void onLoad() {

	}

	@Override
	public void onEnable() {
		Log.info("Enabled TomeitoLib!");
	}
}
