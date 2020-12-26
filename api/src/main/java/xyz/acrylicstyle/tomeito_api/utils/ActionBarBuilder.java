package xyz.acrylicstyle.tomeito_api.utils;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import util.CollectionList;
import xyz.acrylicstyle.tomeito_api.TomeitoAPI;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class ActionBarBuilder {
    private final CollectionList<?, String> list = new CollectionList<>();
    private final String separator;

    public ActionBarBuilder() { this(" / "); }

    public ActionBarBuilder(@NotNull String separator) { this.separator = separator; }

    @Contract("_ -> this")
    @NotNull
    public ActionBarBuilder add(@NotNull String s) {
        list.add(s);
        return this;
    }

    public boolean contains(@NotNull String s) { return list.contains(s); }

    @NotNull
    public String get(int index) { return list.get(index); }

    @Contract("_ -> this")
    @NotNull
    public ActionBarBuilder remove(int index) {
        list.remove(index);
        return this;
    }

    @Contract("_ -> this")
    @NotNull
    public ActionBarBuilder removeIf(@NotNull Predicate<String> predicate) {
        list.removeIf(predicate);
        return this;
    }

    @Contract("_ -> this")
    @NotNull
    public ActionBarBuilder forEach(@NotNull Consumer<String> action) {
        list.forEach(action);
        return this;
    }

    @Contract("_ -> this")
    @NotNull
    public ActionBarBuilder filter(@NotNull Function<String, Boolean> predicate) {
        list.removeIf(s -> !predicate.apply(s));
        return this;
    }

    @Contract("_ -> this")
    @NotNull
    public ActionBarBuilder remove(@NotNull String s) {
        list.remove(s);
        return this;
    }

    @Contract("-> this")
    @NotNull
    public ActionBarBuilder sort() {
        list.sort(String::compareTo);
        return this;
    }

    @Contract("_ -> this")
    @NotNull
    public ActionBarBuilder sort(@NotNull Comparator<String> comparator) {
        list.sort(comparator);
        return this;
    }

    @Contract("_ -> this")
    @NotNull
    public ActionBarBuilder addAll(@NotNull Iterable<String> iterable) {
        iterable.forEach(list::add);
        return this;
    }

    @Contract("_ -> this")
    @NotNull
    public ActionBarBuilder addAll(@NotNull Iterator<String> iterator) {
        iterator.forEachRemaining(list::add);
        return this;
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Contract(value = "-> new", pure = true)
    @NotNull
    @Override
    public ActionBarBuilder clone() {
        ActionBarBuilder builder = new ActionBarBuilder();
        builder.list.addAll(this.list);
        return builder;
    }

    @NotNull
    public String build() { return list.join(separator); }

    public void send(@NotNull Player player) { TomeitoAPI.sendActionbar(player, this.build()); }

    public void broadcast() { TomeitoAPI.broadcastActionBar(this.build()); }
}
