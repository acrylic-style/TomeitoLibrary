package xyz.acrylicstyle.tomeito_api.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface PlayerGui extends Listener, InventoryHolder {
    @NotNull
    @Override
    Inventory getInventory();

    @EventHandler
    default void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() != this) return;
        if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
            e.setCancelled(true);
            return;
        }
        if (e.getClickedInventory() == null || e.getClickedInventory().getHolder() != this) return;
        e.setCancelled(true);
    }

    @EventHandler
    default void onInventoryDrag(InventoryDragEvent e) {
        if (e.getInventory().getHolder() != this) return;
        e.setCancelled(true);
    }

    @EventHandler
    default void onInventoryClose(InventoryCloseEvent e) {}

    static PlayerGui create(@NotNull Supplier<Inventory> getInventorySupplier) {
        return create(getInventorySupplier, e -> {});
    }

    static PlayerGui create(@NotNull Supplier<Inventory> getInventorySupplier, @NotNull Consumer<InventoryClickEvent> inventoryClickEventConsumer) {
        return create(getInventorySupplier, inventoryClickEventConsumer, e -> {});
    }

    @NotNull
    static PlayerGui create(@NotNull Supplier<Inventory> getInventorySupplier, @NotNull Consumer<InventoryClickEvent> inventoryClickEventConsumer, @NotNull Consumer<InventoryDragEvent> inventoryDragEventConsumer) {
        return new PlayerGui() {
            @Override
            @EventHandler
            public @NotNull Inventory getInventory() {
                return getInventorySupplier.get();
            }

            @Override
            @EventHandler
            public void onInventoryClick(InventoryClickEvent e) {
                inventoryClickEventConsumer.accept(e);
            }

            @Override
            @EventHandler
            public void onInventoryDrag(InventoryDragEvent e) {
                inventoryDragEventConsumer.accept(e);
            }
        };
    }
}
