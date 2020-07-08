package xyz.acrylicstyle.tomeito_api.gui;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import util.Collection;

import java.util.UUID;
import java.util.function.Supplier;

public class PerPlayerInventory<V> extends Collection<UUID, V> {
    private final Supplier<V> constructor;

    public PerPlayerInventory(@NotNull Supplier<V> constructor) {
        this.constructor = constructor;
    }

    @NotNull
    public V get(@NotNull UUID key) {
        if (!this.containsKey(key)) super.put(key, constructor.get());
        return super.get(key);
    }

    @Override
    @Contract("_ -> fail")
    public V get(Object key) {
        throw new UnsupportedOperationException();
    }
}
