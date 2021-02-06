package xyz.acrylicstyle.tomeito_api.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import util.CollectionList;
import util.ICollectionList;

import java.util.Collections;
import java.util.List;

public class TabCompleterHelper {
    protected static final List<String> emptyList = Collections.emptyList();

    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull ICollectionList<String> filterArgsList(CollectionList<String> list, String s) {
        return list.filter(s2 -> s2.toLowerCase().startsWith(s.toLowerCase()));
    }

    @Contract("_, _ -> new")
    public static @NotNull ICollectionList<String> filterArgsList(List<String> list, String s) { return filterArgsList(new CollectionList<>(list), s); }
}
