package xyz.acrylicstyle.tlibtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import util.Collection;
import util.ICollectionList;
import xyz.acrylicstyle.tlibtest.util.Target;
import xyz.acrylicstyle.tomeito_api.BaseTomeitoAPI;
import xyz.acrylicstyle.tomeito_api.TomeitoAPI;
import xyz.acrylicstyle.tomeito_api.command.OpCommandExecutor;
import xyz.acrylicstyle.tomeito_api.command.PlayerCommandExecutor;
import xyz.acrylicstyle.tomeito_api.command.PlayerOpCommandExecutor;
import xyz.acrylicstyle.tomeito_api.event.EventBuilder;
import xyz.acrylicstyle.tomeito_api.event.EventsBuilder;
import xyz.acrylicstyle.tomeito_api.event.PendingEvent;
import xyz.acrylicstyle.tomeito_api.event.PendingEventExecutor;
import xyz.acrylicstyle.tomeito_api.events.block.DispenserTNTPrimeEvent;
import xyz.acrylicstyle.tomeito_api.events.block.PlayerTNTPrimeEvent;
import xyz.acrylicstyle.tomeito_api.events.entity.EntityPreDeathEvent;
import xyz.acrylicstyle.tomeito_api.events.entity.WitherSkullBlockBreakEvent;
import xyz.acrylicstyle.tomeito_api.events.player.EntityDamageByPlayerEvent;
import xyz.acrylicstyle.tomeito_api.events.player.PlayerJumpEvent;
import xyz.acrylicstyle.tomeito_api.events.player.PlayerPreDeathEvent;
import xyz.acrylicstyle.tomeito_api.gui.ClickableItem;
import xyz.acrylicstyle.tomeito_api.gui.GuiBuilder;
import xyz.acrylicstyle.tomeito_api.gui.PlayerGui;
import xyz.acrylicstyle.tomeito_api.gui.impl.SimpleGuiBuilder;
import xyz.acrylicstyle.tomeito_api.inventory.InventoryUtils;
import xyz.acrylicstyle.tomeito_api.providers.ConfigProvider;
import xyz.acrylicstyle.tomeito_api.providers.LanguageProvider;
import xyz.acrylicstyle.tomeito_api.reflect.Refs;
import xyz.acrylicstyle.tomeito_api.scheduler.TomeitoRunnable;
import xyz.acrylicstyle.tomeito_api.scheduler.TomeitoScheduler;
import xyz.acrylicstyle.tomeito_api.scheduler.TomeitoTask;
import xyz.acrylicstyle.tomeito_api.sounds.Sound;
import xyz.acrylicstyle.tomeito_api.subcommand.OpSubCommandExecutor;
import xyz.acrylicstyle.tomeito_api.subcommand.PlayerOpSubCommandExecutor;
import xyz.acrylicstyle.tomeito_api.subcommand.PlayerSubCommandExecutor;
import xyz.acrylicstyle.tomeito_api.subcommand.SubCommandExecutor;
import xyz.acrylicstyle.tomeito_api.utils.ArrayUtil;
import xyz.acrylicstyle.tomeito_api.utils.Callback;
import xyz.acrylicstyle.tomeito_api.utils.Lang;
import xyz.acrylicstyle.tomeito_api.utils.Log;
import xyz.acrylicstyle.tomeito_api.utils.ReflectionUtil;
import xyz.acrylicstyle.tomeito_api.utils.SlimeUtils;
import xyz.acrylicstyle.tomeito_api.utils.TabCompleterHelper;
import xyz.acrylicstyle.tomeito_api.utils.Title;
import xyz.acrylicstyle.tomeito_api.utils.TypeUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static org.junit.runners.Parameterized.Parameter;

@RunWith(Parameterized.class)
public class VisibilityTest {
    private static final Collection<Class<?>, List<Target>> target = new Collection<>();

    private static void add(Class<?> clazz, Target... targets) {
        if (targets.length == 0) {
            target.add(clazz, ICollectionList.asList(Target.values()));
            return;
        }
        target.add(clazz, ICollectionList.asList(targets));
    }

    static {
        add(TomeitoAPI.class, Target.METHOD);
        add(BaseTomeitoAPI.class);
        add(TypeUtil.class);
        add(Title.class, Target.METHOD, Target.CONSTRUCTOR);
        add(TabCompleterHelper.class);
        add(SlimeUtils.class);
        add(ReflectionUtil.class);
        add(Log.class);
        add(Log.Logger.class, Target.METHOD);
        add(Lang.class);
        add(Callback.class);
        add(ArrayUtil.class);
        add(SubCommandExecutor.class);
        add(PlayerSubCommandExecutor.class);
        add(PlayerOpSubCommandExecutor.class);
        add(OpSubCommandExecutor.class);
        add(Sound.class, Target.FIELD);
        add(Refs.class);
        add(LanguageProvider.class);
        add(TomeitoRunnable.class, Target.CONSTRUCTOR);
        add(TomeitoScheduler.class);
        add(TomeitoTask.class);
        add(ConfigProvider.class);
        add(InventoryUtils.class, Target.METHOD, Target.CONSTRUCTOR);
        add(PlayerGui.class);
        add(GuiBuilder.class);
        add(ClickableItem.class, Target.METHOD);
        add(SimpleGuiBuilder.class, Target.METHOD, Target.CONSTRUCTOR);
        add(PlayerPreDeathEvent.class, Target.METHOD, Target.CONSTRUCTOR);
        add(PlayerJumpEvent.class, Target.METHOD, Target.CONSTRUCTOR);
        add(EntityDamageByPlayerEvent.class, Target.METHOD, Target.CONSTRUCTOR);
        add(EntityPreDeathEvent.class, Target.METHOD, Target.CONSTRUCTOR);
        add(WitherSkullBlockBreakEvent.class, Target.METHOD, Target.CONSTRUCTOR);
        add(PlayerTNTPrimeEvent.class, Target.METHOD, Target.CONSTRUCTOR);
        add(DispenserTNTPrimeEvent.class, Target.METHOD, Target.CONSTRUCTOR);
        add(EventBuilder.class, Target.METHOD, Target.CONSTRUCTOR);
        add(EventsBuilder.class, Target.METHOD, Target.CONSTRUCTOR);
        add(PendingEvent.class, Target.METHOD, Target.CONSTRUCTOR);
        add(PendingEventExecutor.class, Target.METHOD, Target.CONSTRUCTOR);
        add(OpCommandExecutor.class);
        add(PlayerCommandExecutor.class);
        add(PlayerOpCommandExecutor.class);
    }

    @Parameters
    public static List<Object[]> data() {
        List<Object[]> list = new ArrayList<>();
        target.keysList().forEach(clazz -> {
            List<Target> targets = target.get(clazz);
            if (targets.contains(Target.METHOD)) {
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.getName().startsWith("$$$reportNull$$$") || method.getName().startsWith("lambda$")) continue;
                    list.add(new Object[] { method });
                }
            }
            if (targets.contains(Target.FIELD)) {
                for (Field field : clazz.getDeclaredFields()) list.add(new Object[] { field });
            }
            if (targets.contains(Target.CONSTRUCTOR)) {
                for (Constructor<?> constructor : clazz.getDeclaredConstructors()) list.add(new Object[] { constructor });
            }
        });
        return list;
    }

    @Parameter public Member member;

    private String getVisibility(Member member) {
        if (Modifier.isPublic(member.getModifiers())) return "public";
        if (Modifier.isProtected(member.getModifiers())) return "protected";
        if (Modifier.isPrivate(member.getModifiers())) return "private";
        return "package-private";
    }

    @Test
    public void ensurePublic() {
        String name = member.getName();
        String type = "?";
        if (member instanceof Field) {
            name = ((Field) member).toGenericString();
            type = "field";
        }
        if (member instanceof Method) {
            name = ((Method) member).toGenericString();
            type = "method";
        }
        if (member instanceof Constructor) {
            name = ((Constructor<?>) member).toGenericString();
            type = "constructor";
        }
        assert Modifier.isPublic(member.getModifiers()) : type + " " + name + " in " + member.getDeclaringClass().getCanonicalName() + " was " + getVisibility(member);
    }
}
