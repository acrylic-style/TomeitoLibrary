package xyz.acrylicstyle.tomeito_api.utils;

public interface ThrowableConsumer<T> {
    void accept(T t) throws Throwable;
}
