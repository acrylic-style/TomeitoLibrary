package xyz.acrylicstyle.tomeito_api.utils;

public interface Callback<T> {
    void done(T t, Throwable e);
}

