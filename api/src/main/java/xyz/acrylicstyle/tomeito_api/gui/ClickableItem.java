package xyz.acrylicstyle.tomeito_api.gui;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import util.Validate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ClickableItem {
    @NotNull private final Consumer<InventoryClickEvent> executor;
    @NotNull private final ItemStack item;

    private ClickableItem(@NotNull ItemStack itemStack, @NotNull Consumer<InventoryClickEvent> executor) {
        this.item = itemStack;
        this.executor = executor;
    }

    @NotNull
    public static ClickableItem of(@NotNull Material material, @NotNull String name, @NotNull Consumer<InventoryClickEvent> executor) {
        return of(material, 1, name, new ArrayList<>(), executor);
    }

    @NotNull
    public static ClickableItem of(@NotNull Material material, int amount, @NotNull String name, @NotNull Consumer<InventoryClickEvent> executor) {
        return of(material, amount, name, new ArrayList<>(), executor);
    }

    @NotNull
    public static ClickableItem of(@NotNull Material material, int amount, @NotNull String name, @NotNull List<String> lore, @NotNull Consumer<InventoryClickEvent> executor) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return of(item, executor);
    }

    @NotNull
    public static ClickableItem of(@NotNull ItemStack itemStack, @NotNull Consumer<InventoryClickEvent> executor) {
        return new ClickableItem(Validate.notNull(itemStack, "item cannot be null"), Validate.notNull(executor, "executor cannot be null"));
    }

    @NotNull
    public static ClickableItem empty(@NotNull ItemStack itemStack) {
        return new ClickableItem(Validate.notNull(itemStack, "item cannot be null"), e -> {});
    }

    @NotNull
    public ItemStack getItemStack() {
        return item;
    }

    @NotNull
    public Consumer<InventoryClickEvent> getExecutor() {
        return executor;
    }
}
