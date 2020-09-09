package xyz.acrylicstyle.tomeito_api.utils;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import util.ReflectionHelper;

import java.lang.reflect.Method;

public class Title {
    @NotNull
    protected String title = "";

    @NotNull
    protected String subTitle = "";

    protected int fadeIn = 20;
    protected int stay = 200;
    protected int fadeOut = 20;

    @NotNull
    @Contract("_ -> this")
    public Title title(String title) {
        this.title = title;
        return this;
    }

    @NotNull
    @Contract("_ -> this")
    public Title subTitle(String subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    @NotNull
    @Contract("_ -> this")
    public Title fadeIn(int fadeIn) {
        this.fadeIn = fadeIn;
        return this;
    }

    @NotNull
    @Contract("_ -> this")
    public Title stay(int stay) {
        this.stay = stay;
        return this;
    }

    @NotNull
    @Contract("_ -> this")
    public Title fadeOut(int fadeOut) {
        this.fadeOut = fadeOut;
        return this;
    }

    /**
     * Clears title and subTitle value.
     */
    @NotNull
    @Contract("-> this")
    public Title clear() {
        this.title = "";
        this.subTitle = "";
        return this;
    }

    /**
     * Resets title, subTitle, fadeIn, stay, fadeOut value to the default.
     */
    @NotNull
    @Contract("-> this")
    public Title reset() {
        this.title = "";
        this.subTitle = "";
        this.fadeIn = 10;
        this.stay = 60;
        this.fadeOut = 10;
        return this;
    }

    /**
     * Sends title to the player.
     * @param player The player
     * @return This title instance
     * @throws RuntimeException When couldn't find any Player#sendTitle method (this shouldn't happen)
     */
    @NotNull
    @Contract("_ -> this")
    public Title send(Player player) {
        try {
            Method m = ReflectionHelper.findMethod(Player.class, "sendTitle", String.class, String.class);
            Method m2 = ReflectionHelper.findMethod(Player.class, "sendTitle", String.class, String.class, int.class, int.class, int.class);
            if (m == null && m2 == null) throw new NoSuchMethodException("Couldn't find any Player#sendTitle method");
            if (m2 != null) {
                m2.invoke(player, title, subTitle, fadeIn, stay, fadeOut);
                return this;
            }
            m.invoke(player, title, subTitle);
            return this;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a title instance with no title.
     * @return a newly created title instance
     */
    @NotNull
    @Contract(value = "-> new", pure = true)
    public static Title createTitle() {
        return new Title();
    }
}
