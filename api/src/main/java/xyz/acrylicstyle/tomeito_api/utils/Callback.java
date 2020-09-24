package xyz.acrylicstyle.tomeito_api.utils;

import org.jetbrains.annotations.Nullable;

public interface Callback<T> {
    void done(T t, @Nullable Throwable e);
}

