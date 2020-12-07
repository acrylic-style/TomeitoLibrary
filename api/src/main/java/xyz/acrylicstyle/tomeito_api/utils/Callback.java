package xyz.acrylicstyle.tomeito_api.utils;

import org.jetbrains.annotations.Nullable;

public interface Callback<T> extends util.Callback<T> {
    @Override
    void done(@Nullable T t, @Nullable Throwable e);

    default void complete(@Nullable T t) { done(t, null); }

    default void completeExceptionally(@Nullable Throwable e) { done(null, e); }
}
