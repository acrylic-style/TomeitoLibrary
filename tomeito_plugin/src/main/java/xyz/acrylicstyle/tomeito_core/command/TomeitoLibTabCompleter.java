package xyz.acrylicstyle.tomeito_core.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import util.CollectionList;
import util.ICollectionList;
import util.ReflectionHelper;
import util.reflect.Ref;
import util.reflect.RefClass;
import xyz.acrylicstyle.tomeito_api.reflection.Refs;
import xyz.acrylicstyle.tomeito_api.utils.Log;
import xyz.acrylicstyle.tomeito_api.utils.TabCompleterHelper;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class TomeitoLibTabCompleter extends TabCompleterHelper implements TabCompleter {
    public static CollectionList<String> COMMONS = new CollectionList<>();

    static {
        COMMONS.add("int");
        COMMONS.add("double");
        COMMONS.add("boolean");
        COMMONS.add("float");
        COMMONS.add("long");
        COMMONS.add("Integer");
        COMMONS.add("Double");
        COMMONS.add("Boolean");
        COMMONS.add("Float");
        COMMONS.add("Long");
        COMMONS.add("String");
        COMMONS.add("for");
        COMMONS.add("==");
        COMMONS.add("=");
        COMMONS.add("<=");
        COMMONS.add("<");
        COMMONS.add(">=");
        COMMONS.add(">");
        COMMONS.add("!=");
        COMMONS.add("<<");
        COMMONS.add(">>");
        COMMONS.add("&&");
        COMMONS.add("||");
        COMMONS.add("new");
    }

    private boolean isMethodCallOrField(@NotNull RefClass<?> clazz, String s, boolean isStatic) {
        return isMethodCall(clazz, s, isStatic) || isField(clazz, s, isStatic);
    }

    private boolean isMethodCall(@NotNull RefClass<?> clazz, String s, boolean isStatic) {
        String r = s == null ? null : s.replaceAll("(.*?)\\(.*\\)", "$1");
        return ICollectionList
                .asList(clazz.getDeclaredMethods())
                .filter(m -> Modifier.isStatic(m.getModifiers()) == isStatic)
                .filter(m -> m.getName().equals(r))
                .size() != 0;
    }

    private boolean isMethodCall(@NotNull RefClass<?> clazz, String s) {
        String r = s == null ? null : s.replaceAll("(.*?)\\(.*\\)", "$1");
        return ICollectionList
                .asList(clazz.getDeclaredMethods())
                .filter(m -> m.getName().equals(r))
                .size() != 0;
    }

    @NotNull
    private RefClass<?> getMethodReturnValue(@NotNull RefClass<?> clazz, String s, boolean isStatic) {
        String r = s == null ? null : s.replaceAll("(.*?)\\(.*\\)", "$1");
        return Ref.getClass(Objects.requireNonNull(ICollectionList
                .asList(clazz.getDeclaredMethods())
                .filter(m -> Modifier.isStatic(m.getModifiers()) == isStatic)
                .filter(m -> m.getName().equals(r))
                .map(m -> m.getMethod().getReturnType())
                .first()));
    }

    @NotNull
    private RefClass<?> getMethodReturnValue(@NotNull RefClass<?> clazz, String s) {
        String r = s == null ? null : s.replaceAll("(.*?)\\(.*\\)", "$1");
        return Ref.getClass(Objects.requireNonNull(ICollectionList
                .asList(clazz.getDeclaredMethods())
                .filter(m -> m.getName().equals(r))
                .map(m -> m.getMethod().getReturnType())
                .first()));
    }

    private boolean isField(@NotNull RefClass<?> clazz, String s, boolean isStatic) {
        return ICollectionList
                .asList(clazz.getDeclaredFields())
                .filter(m -> Modifier.isStatic(m.getModifiers()) == isStatic)
                .filter(m -> m.getName().equals(s))
                .size() != 0;
    }

    private boolean isField(@NotNull RefClass<?> clazz, String s) {
        return ICollectionList
                .asList(clazz.getDeclaredFields())
                .filter(m -> m.getName().equals(s))
                .size() != 0;
    }

    @NotNull
    private RefClass<?> getFieldReturnValue(@NotNull RefClass<?> clazz, String s, boolean isStatic) {
        return Ref.getClass(Objects.requireNonNull(ICollectionList
                .asList(clazz.getDeclaredFields())
                .filter(m -> Modifier.isStatic(m.getModifiers()) == isStatic)
                .filter(m -> m.getName().equals(s))
                .map(m -> m.getField().getType())
                .first()));
    }

    @NotNull
    private RefClass<?> getFieldReturnValue(@NotNull RefClass<?> clazz, String s) {
        return Ref.getClass(Objects.requireNonNull(ICollectionList
                .asList(clazz.getDeclaredFields())
                .filter(m -> m.getName().equals(s))
                .map(m -> m.getField().getType())
                .first()));
    }

    private int a = 0;

    private boolean hasNext(String[] c, int i) {
        return i + this.a++ < c.length - 1;
    }

    @SuppressWarnings("StringConcatenationInLoop")
    private @NotNull ICollectionList<String> getAllThings(String prev, String s) {
        String a = "";
        String[] c = s.split("\\.");
        for (int i = 0; i < c.length; i++) {
            a += a.equals("") ? c[i] : "." + c[i];
            if (isValidClass(a.replaceAll("(.*)\\(.*\\)", "$1"))) {
                AtomicReference<RefClass<?>> refClass = new AtomicReference<>(RefClass.forName(a.replaceAll("(.*)\\(.*\\)", "$1")));
                final RefClass<?> r = refClass.get();
                boolean isStatic = prev == null || !prev.equals("new");
                this.a = 0;
                ICollectionList<String> list = isStatic ? Refs.getStatics(refClass.get()).addChain("class") : Refs.getInstances(refClass.get());
                while (hasNext(c, i)) {
                    synchronized (refClass) {
                        refClass.set(r);
                        String next = c[i + this.a];
                        List<String> stacks = new CollectionList<>();
                        if (i + this.a > 0) {
                            stacks.addAll(Arrays.asList(c).subList(i + 1, c.length));
                            if (!s.endsWith(".") && stacks.size() > 0) stacks.remove(stacks.size() - 1);
                        }
                        AtomicBoolean stackChanged = new AtomicBoolean(false);
                        stacks.forEach(p -> {
                            if (p.endsWith(")")) { // method call
                                if (isMethodCall(refClass.get(), p)) {
                                    refClass.set(getMethodReturnValue(refClass.get(), p.replaceAll("(.*)(\\(.*\\)|)", "$1")));
                                    stackChanged.set(true);
                                }
                            } else { // field
                                if (isField(refClass.get(), p)) {
                                    refClass.set(getFieldReturnValue(refClass.get(), p));
                                    stackChanged.set(true);
                                }
                            }
                            Log.debug(p + " class: " + refClass.get().getClazz().getCanonicalName());
                        });
                        if (stackChanged.get()) list = Refs.getInstances(refClass.get());
                        if (isMethodCallOrField(refClass.get(), next, isStatic)) {
                            if (isMethodCall(refClass.get(), next, isStatic)) {
                                next = next.replaceAll("(.*)(\\(.*\\)|)", "$1");
                                if (isStatic) {
                                    list = Refs.getInstances(getMethodReturnValue(refClass.get(), next, true));
                                } else {
                                    list = Refs.getInstances(getMethodReturnValue(refClass.get(), next, false));
                                }
                            } else {
                                if (isStatic) {
                                    list = Refs.getInstances(getFieldReturnValue(refClass.get(), next, true));
                                } else {
                                    list = Refs.getInstances(getFieldReturnValue(refClass.get(), next, false));
                                }
                            }
                        }
                    }
                }
                this.a = 0;
                list = new CollectionList<>(list.unique());
                list = list.filter(d -> !d.contains("$"));
                return list.concat(COMMONS).unique();
            }
        }
        if (!isValidClass(s)) {
            if (ReflectionHelper.isValidPackage(trim(s))) {
                return findClasses(s).concat(COMMONS);
            }
            return findPackages(s).concat(COMMONS);
        }
        return new CollectionList<>("u");
    }

    private final ClassLoader cl;

    public TomeitoLibTabCompleter(@NotNull ClassLoader cl) {
        this.cl = cl;
    }

    @NotNull
    public ClassLoader getClassLoader() {
        return cl;
    }

    private CollectionList<String> findClasses(String packageName) {
        CollectionList<String> list = findClasses(packageName, false);
        return list.size() == 0 ? findClasses(packageName, true) : list;
    }

    private CollectionList<String> findClasses(String packageName, boolean recursive) {
        try {
            return ReflectionHelper
                    .findAllClasses(cl, trim(packageName), recursive)
                    .map(Class::getCanonicalName)
                    .concat(findPackages(packageName), findSystemClasses(packageName, recursive));
        } catch (SecurityException e) {
            try {
                return findPackages(packageName).concat(findSystemClasses(packageName, recursive));
            } catch (SecurityException e2) {
                return findPackages(packageName);
            }
        }
    }

    private CollectionList<String> findSystemClasses(String packageName, boolean recursive) {
        return ReflectionHelper
                .findAllClasses(ClassLoader.getSystemClassLoader(), trim(packageName), recursive)
                .map(Class::getCanonicalName)
                .concat(findPackages(packageName));
    }

    private CollectionList<String> findPackages(String packageName) {
        CollectionList<String> list = findPackages(packageName, packageName.equalsIgnoreCase(""));
        if (list.size() == 0) return findPackages(packageName, true);
        return list;
    }

    private CollectionList<String> findPackages(String packageName, boolean recursive) {
        return ReflectionHelper
                .findPackages(
                        getClassLoader(),
                        trim(packageName),
                        recursive
                );
    }

    private static String trim(String s) {
        return s.replaceAll("(.*)\\..*", "$1");
    }

    @SuppressWarnings("ConstantConditions")
    private static boolean isValidClass(String clazz) {
        try {
            RefClass.forName(clazz);
            return true;
        } catch (Exception e) {
            if (!(e instanceof ClassNotFoundException)) throw e;
            return false;
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 0) return Arrays.asList("debug", "debug-legacy");
        if (args.length == 1) return filterArgsList(Arrays.asList("debug", "debug-legacy"), args[0]);
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("debug-legacy")) {
                if (!isValidClass(args[1])) {
                    if (ReflectionHelper.isValidPackage(trim(args[1]))) {
                        return filterArgsList(findClasses(args[1]), args[1]);
                    }
                    return filterArgsList(findPackages(args[1]), args[1]);
                }
            }
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("debug-legacy")) {
                try {
                    return filterArgsList(Refs.getAllThings(RefClass.forName(args[1])), args[2]);
                } catch (Exception e) {
                    if (!(e instanceof ClassNotFoundException)) throw e;
                }
            }
        }
        if (args[0].equalsIgnoreCase("debug")) {
            String s0 = args.length-2 <= 0 ? null : args[args.length-2];
            String s = args[args.length-1];
            String[] g = s.split("\\.");
            CollectionList<String> k = ICollectionList.asList(g);
            if (k.size() > 0 && !s.endsWith(".")) k.remove(k.size()-1);
            if (s.endsWith(".")) k.add("");
            ICollectionList<String> things = getAllThings(s0, s);
            ICollectionList<String> list = filterArgsList(things, k.join("."));
            if (list.size() != 0) return filterArgsList(list, s); // usually a package.class
            ICollectionList<String> lst = filterArgsList(things, s);
            if (lst.size() == 0) {
                lst = filterArgsList(things, s.endsWith(".") ? "" : g[g.length-1]);
                lst.removeAll(COMMONS);
                return lst.map(l -> (s.endsWith(".") ? k.join(".") : (k.join(".") + ".")) + l); // usually a inner fields or something
            }
            return lst;
        }
        return new CollectionList<>();
    }
}
