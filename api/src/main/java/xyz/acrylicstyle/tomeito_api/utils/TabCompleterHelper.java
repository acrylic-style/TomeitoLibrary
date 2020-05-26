package xyz.acrylicstyle.tomeito_api.utils;

import util.CollectionList;

import java.util.List;

public class TabCompleterHelper {
    public static CollectionList<String> filterArgsList(CollectionList<String> list, String s) {
        return list.filter(s2 -> s2.toLowerCase().startsWith(s.toLowerCase()));
    }

    public static CollectionList<String> filterArgsList(List<String> list, String s) { return filterArgsList(new CollectionList<>(list), s); }
}
