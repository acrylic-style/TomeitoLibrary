package xyz.acrylicstyle.tomeito_api.reflector;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.reflect.Ref;
import util.reflector.Reflector;
import util.reflector.ReflectorHandler;
import util.reflector.ReflectorOption;
import xyz.acrylicstyle.tomeito_api.utils.ReflectionUtil;

import java.util.Set;

/**
 * @deprecated Class name or anything might change at any time
 */
@Deprecated
public interface BukkitEntity extends Entity {
    @NotNull
    static BukkitEntity getInstance(@NotNull Entity entity) {
        return Reflector.newReflector(null, BukkitEntity.class, new ReflectorHandler(Ref.forName(ReflectionUtil.getCraftBukkitPackage() + ".entity.CraftPlayer").getClazz(), entity));
    }

    @Nullable
    @ReflectorOption(errorOption = ReflectorOption.ErrorOption.RETURN_NULL)
    Set<String> getScoreboardTags();
}
