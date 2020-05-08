package xyz.acrylicstyle.tomeito_api;

import org.bukkit.command.CommandExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.CollectionList;

/**
 * Defines instance methods.<br />
 * This interface only exists to define non-static methods.
 */
public interface BaseTomeitoAPI {
    void registerCommands(@NotNull final String packageName);

    /**
     * Registers command with sub commands.
     * @param classLoader Class loader that will be used to load classes. System class loader will be used if left null. (which will be unable to load classes under plugin)
     * @param rootCommandName A root command name. Must be defined at plugin.yml.
     * @param subCommandsPackage Package name that contains sub commands classes. Must be annotated by SubCommand and must extend SubCommandExecutor.
     * @param postCommandExecutor A CommandExecutor that runs very first. Return false to interrupt command execution.
     */
    void registerCommands(@Nullable ClassLoader classLoader, @NotNull final String rootCommandName, @NotNull final String subCommandsPackage, @Nullable CommandExecutor postCommandExecutor);

    void registerCommands(@NotNull ClassLoader classLoader, @NotNull final String rootCommandName, @NotNull final String subCommandsPackage);

    /**
     * Registers command with sub commands.
     * @param rootCommandName A root command name. Must be defined at plugin.yml.
     * @param classes Class list that will load. All classes must implement CommandExecutor or it will fail to load.
     * @param postCommand A CommandExecutor that runs very first. Return false to interrupt command execution.
     */
    void registerCommands(@NotNull final String rootCommandName, @NotNull final CollectionList<Class<?>> classes, @Nullable CommandExecutor postCommand);
}
