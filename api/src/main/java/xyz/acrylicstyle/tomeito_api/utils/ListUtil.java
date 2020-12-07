package xyz.acrylicstyle.tomeito_api.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {
    @Contract("_ -> new")
    @NotNull
    public static <T extends R, R> List<R> downgrade(@NotNull List<T> list) {
        return new ArrayList<>(list);
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public static <T, R extends T> List<R> upgrade(@NotNull List<T> list) {
        ArrayList<R> arrayList = new ArrayList<>();
        list.forEach(t -> arrayList.add((R) t));
        return arrayList;
    }
}
