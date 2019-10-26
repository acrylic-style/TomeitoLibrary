package xyz.acrylicstyle.tomeito_core.utils;

public abstract class Callback<T> {
    public abstract void done(T t, Throwable e);
}

