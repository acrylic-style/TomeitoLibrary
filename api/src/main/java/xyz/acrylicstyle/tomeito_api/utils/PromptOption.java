package xyz.acrylicstyle.tomeito_api.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class PromptOption {
    @Contract(pure = true)
    @NotNull
    public static PromptOption getInstance() { return new PromptOption(); }
}
