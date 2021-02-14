package xyz.acrylicstyle.api.kt

import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask

fun Plugin.runTask(function: () -> Unit): BukkitTask = server.scheduler.runTask(this, function)

fun Plugin.runTaskAsynchronously(function: () -> Unit): BukkitTask = server.scheduler.runTaskAsynchronously(this, function)

fun Plugin.runTaskLater(delay: Long, function: () -> Unit): BukkitTask = server.scheduler.runTaskLater(this, function, delay)

fun Plugin.runTaskLaterAsynchronously(delay: Long, function: () -> Unit): BukkitTask = server.scheduler.runTaskLaterAsynchronously(this, function, delay)

fun Plugin.runTaskTimer(delay: Long, period: Long, function: () -> Unit): BukkitTask = server.scheduler.runTaskTimer(this, function, delay, period)

fun Plugin.runTaskTimerAsynchronously(delay: Long, period: Long, function: () -> Unit): BukkitTask = server.scheduler.runTaskTimerAsynchronously(this, function, delay, period)
