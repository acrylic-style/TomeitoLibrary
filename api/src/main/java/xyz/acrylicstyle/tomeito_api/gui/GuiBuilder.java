package xyz.acrylicstyle.tomeito_api.gui;

import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import xyz.acrylicstyle.tomeito_api.gui.impl.SimpleGuiBuilder;

import java.util.Map;
import java.util.function.Consumer;

public interface GuiBuilder extends Listener, InventoryHolder {
    /**
     * Set item at the slot. It doesn't register click event.
     * @param slot the slot
     * @param itemStack the item
     */
    @NotNull
    GuiBuilder setItem(int slot, @NotNull ItemStack itemStack);

    /**
     * Set item at the slot and registers click event.
     * @param slot the slot
     * @param clickableItem the item + click event
     */
    @NotNull
    GuiBuilder setItem(int slot, @NotNull ClickableItem clickableItem);

    /**
     * Registers the event.
     * @param plugin the plugin
     */
    @NotNull
    GuiBuilder register(@NotNull Plugin plugin);

    /**
     * Get all registered click events.
     * This list is immutable.
     * @return the immutable list, changes to the this list will not be saved.
     */
    @NotNull
    Map<Integer, Consumer<InventoryClickEvent>> getClickEvents();

    /**
     * Constructs GuiBuilder with specified inventory.
     * After calling constructor, you must call {@link #register(Plugin)} or the events doesn't work.
     */
    @NotNull
    static GuiBuilder newInstance(Inventory inventory) { return new SimpleGuiBuilder(inventory); }

    /**
     * Constructs GuiBuilder with newly created inventory with specified name and the size.
     * After calling constructor, you must call {@link #register(Plugin)} or the events doesn't work.
     */
    @NotNull
    static GuiBuilder newInstance(@NotNull String name, int size) { return new SimpleGuiBuilder(name, size); }

    /**
     * Constructs GuiBuilder with newly created inventory with specified size.
     * After calling constructor, you must call {@link #register(Plugin)} or the events doesn't work.
     */
    @NotNull
    static GuiBuilder newInstance(int size) { return new SimpleGuiBuilder(size); }
}
