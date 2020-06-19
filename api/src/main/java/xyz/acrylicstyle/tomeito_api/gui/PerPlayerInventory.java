package xyz.acrylicstyle.tomeito_api.gui;

import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import util.Collection;
import util.StringCollection;

import java.util.UUID;

public class PerPlayerInventory extends StringCollection<Collection<UUID, Inventory>> {
    private static final PerPlayerInventory instance = new PerPlayerInventory();

    @NotNull
    public static PerPlayerInventory getInstance() { return instance; }

    @NotNull
    public static Collection<UUID, Inventory> getMap(@NotNull String key) {
        return instance.get(key);
    }

    @NotNull
    public Collection<UUID, Inventory> get(@NotNull String key) {
        if (!this.containsKey(key)) super.put(key, new Collection<>());
        return super.get(key);
    }

    @Override
    @Contract("_ -> fail")
    public Collection<UUID, Inventory> get(Object key) {
        throw new UnsupportedOperationException();
    }
}
