package xyz.acrylicstyle.tomeito_api.events.server;

import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.event.server.ServerEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UnknownCommandEvent extends ServerEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    @NotNull protected final CommandSender sender;
    @NotNull protected String command;
    @Nullable protected String message = null;

    public UnknownCommandEvent(@NotNull final CommandSender sender, @NotNull final String command) {
        this.sender = sender;
        this.command = command;
    }

    @Override
    @NotNull
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @NotNull
    public CommandSender getSender() {
        return sender;
    }

    @NotNull
    public String getCommand() {
        return command;
    }

    /**
     * Get the unknown command message.
     * @return Unknown command message. Null indicates does not send unknown command message to the sender.
     */
    @Nullable
    public String getMessage() {
        return message;
    }

    /**
     * Set the unknown command message. Set to null if you don't want to send unknown command message to the sender.
     * @param message Unknown command message
     */
    public void setMessage(@Nullable String message) {
        this.message = message;
    }
}
