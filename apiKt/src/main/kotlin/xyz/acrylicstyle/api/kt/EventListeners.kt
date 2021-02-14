package xyz.acrylicstyle.api.kt

import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin

class PluginAwareEventListener(val plugin: Plugin) : Listener {
    inline fun <reified T : Event> event(priority: EventPriority = EventPriority.NORMAL, ignoreCancelled: Boolean = false, crossinline executor: (T) -> Unit) {
        Bukkit.getPluginManager().registerEvent(T::class.java, this, priority, { _, event ->
            executor.invoke(event as T)
        }, plugin, ignoreCancelled)
    }

    fun registerEvents() = this.registerEvents(plugin)

    companion object {
        fun make(plugin: Plugin, function: PluginAwareEventListener.() -> Unit) = PluginAwareEventListener(plugin).also { function.invoke(it) }
    }
}

inline fun <reified T : Event> Listener.event(plugin: Plugin, priority: EventPriority = EventPriority.NORMAL, ignoreCancelled: Boolean = false, crossinline executor: (T) -> Unit) {
    Bukkit.getPluginManager().registerEvent(T::class.java, this, priority, { _, event ->
        executor.invoke(event as T)
    }, plugin, ignoreCancelled)
}

fun Listener.registerEvents(plugin: Plugin) = plugin.server.pluginManager.registerEvents(this, plugin)

val emptyListener = object : Listener {}

inline fun <reified T : Event> Plugin.event(priority: EventPriority = EventPriority.NORMAL, ignoreCancelled: Boolean = false, crossinline executor: (T) -> Unit) {
    Bukkit.getPluginManager().registerEvent(T::class.java, emptyListener, priority, { _, event ->
        executor.invoke(event as T)
    }, this, ignoreCancelled)
}
