package xyz.acrylicstyle.tomeito_core.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.CollectionList;
import util.ICollectionList;
import util.ReflectionHelper;
import util.StringCollection;
import util.reflect.Ref;
import util.reflect.RefClass;
import util.reflect.RefField;
import xyz.acrylicstyle.tomeito_api.reflection.Refs;
//import xyz.acrylicstyle.tomeito_api.utils.Log;
import xyz.acrylicstyle.tomeito_api.utils.TabCompleterHelper;
import xyz.acrylicstyle.tomeito_core.TomeitoLib;

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
        COMMONS.add("null");
        COMMONS.add("\"\"");
        COMMONS.add("0");
        COMMONS.add("false");
        COMMONS.add("true");
        COMMONS.add("void");
        COMMONS.add("getClass()");
    }

    private boolean isMethodCallOrField(@Nullable RefClass<?> clazz, String s) {
        if (clazz == null) return false;
        return isMethodCall(clazz, s) || isField(clazz, s);
    }

    private boolean isMethodCall(@Nullable RefClass<?> clazz, String s) {
        if (clazz == null) return false;
        String r = s == null ? null : s.replaceAll("(.*)\\(.*\\)", "$1");
        return Refs.getAllMethods(clazz).map(a -> a.replaceAll("(.*)\\(.*\\)", "$1")).contains(r);
    }

    @NotNull
    private RefClass<?> getMethodReturnValue(@NotNull RefClass<?> clazz, String s, boolean isStatic) {
        String r = s == null ? null : s.replaceAll("(.*)\\(.*\\)", "$1");
        return new RefClass<>(Objects.requireNonNull(
                Refs.getAllMethodsM(clazz)
                        .filter(m -> Modifier.isStatic(m.getModifiers()) == isStatic)
                        .filter(m -> m.getName().equals(r))
                        .map(m -> m.getMethod().getReturnType())
                        .first()
        ));
    }

    @NotNull
    private RefClass<?> getMethodReturnValue(@NotNull RefClass<?> clazz, String s) {
        String r = s == null ? null : s.replaceAll("(.*)\\(.*\\)", "$1");
        return new RefClass<>(Objects.requireNonNull(
                Refs.getAllMethodsM(clazz)
                        .filter(m -> m.getName().equals(r))
                        .map(m -> m.getMethod().getReturnType())
                        .first()
        ));
    }

    private boolean isField(@Nullable RefClass<?> clazz, String s) {
        if (clazz == null) return false;
        return Refs.getFields(clazz).map(RefField::getName).filter(m -> m.equals(s)).size() != 0;
    }

    @NotNull
    private RefClass<?> getFieldReturnValue(@NotNull RefClass<?> clazz, String s, boolean isStatic) {
        return Ref.getClass(Objects.requireNonNull(Refs.getFields(clazz)
                .filter(f -> Modifier.isStatic(f.getModifiers()) == isStatic)
                .filter(f -> f.getName().equals(s))
                .map(m -> m.getField().getType())
                .first()));
    }

    @NotNull
    private RefClass<?> getFieldReturnValue(@NotNull RefClass<?> clazz, String s) {
        return Ref.getClass(Objects.requireNonNull(Refs.getFields(clazz)
                .filter(m -> m.getName().equals(s))
                .map(m -> m.getField().getType())
                .first()));
    }

    private int a = 0;

    private boolean hasNext(String[] c, int i) {
        return i + this.a++ < c.length - 1;
    }

    @SuppressWarnings({"StringConcatenationInLoop", "unchecked"})
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
                            if (p.equals("class")) {
                                refClass.set(Ref.getClass(Class.class));
                            } else if (p.equals("getClass()")) {
                                refClass.set(Ref.getClass(Class.class));
                            } else if (p.endsWith(")")) { // method call
                                //Log.info("< Is " + p + " Method?");
                                if (isMethodCall(refClass.get(), p)) {
                                    refClass.set(getMethodReturnValue(refClass.get(), p.replaceAll("(.*)(\\(.*\\)|)", "$1")));
                                    //Log.info("> Yes");
                                } else {
                                    refClass.set(null);
                                    //Log.info("> No");
                                }
                            } else { // field
                                //Log.info("< Is " + p + " Field?");
                                if (isField(refClass.get(), p)) {
                                    refClass.set(getFieldReturnValue(refClass.get(), p));
                                    //Log.info("> Yes");
                                } else {
                                    refClass.set(null);
                                    //Log.info("> No");
                                }
                            }
                            stackChanged.set(true);
                            /*
                            if (refClass.get() == null) {
                                Log.info(p + " -> null");
                            } else {
                                Log.info(p + " -> " + refClass.get().getClazz().toGenericString());
                            }
                            */
                        });
                        if (stackChanged.get()) {
                            if (refClass.get() == null ){
                                list = new CollectionList<>();
                            } else {
                                list = Refs.getAllMethodsM(refClass.get()).map(m -> {
                                    String signature = ICollectionList.asList(m.getParameterTypes()).map(Class::getCanonicalName).join(", ");
                                    return m.getName() + "(" + signature + ")";
                                });
                            }
                        }
                        if (isMethodCallOrField(refClass.get(), next)) {
                            if (isMethodCall(refClass.get(), next)) {
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
            if (isValidPackage(trim(s))) return findClasses(s).concat(COMMONS);
            return findPackages(s).concat(COMMONS);
        }
        return new CollectionList<>();
    }

    private boolean isValidPackage(String pkg) {
        CollectionList<ClassLoader> loaders = ICollectionList.asList((List<?>) Ref.getDeclaredField(JavaPluginLoader.class, "loaders")
                .accessible(true)
                .get((JavaPluginLoader) TomeitoLib.instance.getPluginLoader())).map(o -> (ClassLoader) o);
        AtomicBoolean found = new AtomicBoolean(false);
        loaders.forEach(cl -> {
            if (!found.get() && Ref.getDeclaredMethod(ClassLoader.class, "getPackage", String.class)
                    .accessible(true)
                    .invoke(cl, pkg) != null) found.set(true);
        });
        return found.get();
    }

    private CollectionList<String> findClasses(String packageName) {
        CollectionList<String> list = findClasses(packageName, false);
        return list.size() == 0 ? findClasses(packageName, true) : list;
    }

    private final StringCollection<Integer> loaders1 = new StringCollection<>();
    private final StringCollection<Integer> loaders2 = new StringCollection<>();
    private final StringCollection<CollectionList<String>> classes = new StringCollection<>();
    private final StringCollection<CollectionList<String>> packages = new StringCollection<>();

    private CollectionList<String> findClasses(String packageName, boolean recursive) {
        String p = trim(packageName);
        if (!classes.containsKey(p)) classes.add(p, new CollectionList<>());
        try {
            CollectionList<ClassLoader> loaders = ICollectionList.asList((List<?>) Ref.getDeclaredField(JavaPluginLoader.class, "loaders")
                    .accessible(true)
                    .get((JavaPluginLoader) TomeitoLib.instance.getPluginLoader())).map(o -> (ClassLoader) o);
            if (loaders.size() != this.loaders1.getOrDefault(p, -1)) {
                this.loaders1.add(p, loaders.size());
                classes.get(p).clear();
                loaders.forEach(cl -> classes.get(p).addAll(ReflectionHelper.findAllClasses(cl, p, recursive).map(Class::getCanonicalName)));
            }
            return classes.get(p).concat(findPackages(packageName), findSystemClasses(packageName, recursive));
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
        if (list.size() == 0) list = findPackages(packageName, true);
        if (list.size() == 0) list = findPackages(trim(packageName + "."), true);
        if (list.size() == 0) list = findPackages(trim(trim(packageName + ".") + "."), true);
        if (list.size() == 0) list = findPackages("", true);
        return list;
    }

    private CollectionList<String> findPackages(String packageName, boolean recursive) {
        String p = trim(packageName);
        if (!packages.containsKey(p)) packages.add(p, new CollectionList<>());
        CollectionList<ClassLoader> loaders = ICollectionList.asList((List<?>) Ref.getDeclaredField(JavaPluginLoader.class, "loaders")
                .accessible(true)
                .get((JavaPluginLoader) TomeitoLib.instance.getPluginLoader())).map(o -> (ClassLoader) o);
        if (loaders.size() != this.loaders2.getOrDefault(p, -1)) {
            this.loaders2.add(p, loaders.size());
            packages.get(p).clear();
            loaders.forEach(cl -> packages.get(p).addAll(ReflectionHelper.findPackages(cl, p, recursive)));
        }
        return packages.get(p);
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
                    if (isValidPackage(trim(args[1]))) {
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
            //Log.info("T: " + things.join(", "));
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
