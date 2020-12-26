package xyz.acrylicstyle.tomeito_api.reflect;

import util.CollectionList;
import util.CollectionSet;
import util.ICollectionList;
import util.reflect.RefClass;
import util.reflect.RefField;
import util.reflect.RefMethod;

import java.lang.reflect.Modifier;

public class Refs {
    public static ICollectionList<String> getAllThings(RefClass<?> refClass) {
        if (refClass == null) return new CollectionList<>();
        return getAllMethods(refClass).thenAddAll(getAllFields(refClass));
    }

    public static ICollectionList<String> getInstances(RefClass<?> refClass) {
        if (refClass == null) return new CollectionSet<>();
        return new CollectionSet<>(getAllMethodsM(refClass).map(m -> {
            String signature = ICollectionList.asList(m.getParameterTypes()).map(Class::getCanonicalName).join(", ");
            return m.getName() + "(" + signature + ")";
        }));
    }

    public static ICollectionList<String> getStatics(RefClass<?> refClass) {
        if (refClass == null) return new CollectionList<>();
        return getStaticMethods(refClass).thenAddAll(getStaticFields(refClass));
    }

    public static ICollectionList<String> getAllMethods(RefClass<?> refClass) {
        if (refClass == null) return new CollectionList<>();
        return getStaticMethods(refClass).thenAddAll(getInstanceMethods(refClass));
    }

    public static ICollectionList<String> getStaticMethods(RefClass<?> refClass) {
        if (refClass == null) return new CollectionList<>();
        return ICollectionList
                .asList(refClass.getDeclaredMethods())
                .filter(r -> Modifier.isStatic(r.getModifiers()))
                .filter(s -> !s.getName().startsWith("lambda$"))
                .map(m -> {
                    String signature = ICollectionList.asList(m.getParameterTypes()).map(Class::getCanonicalName).join(", ");
                    return m.getName() + "(" + signature + ")";
                }).thenAddAll(getSuperMethodsAsString(refClass, true)).unique();
    }

    public static ICollectionList<String> getSuperMethodsAsString(RefClass<?> refClass, boolean isStatic) {
        return getSuperMethods(refClass)
                .filter(r -> Modifier.isStatic(r.getModifiers()) == isStatic)
                .filter(s -> !s.getName().startsWith("lambda$"))
                .map(m -> {
                    String signature = ICollectionList.asList(m.getParameterTypes()).map(Class::getCanonicalName).join(", ");
                    return m.getName() + "(" + signature + ")";
                });
    }

    public static ICollectionList<RefMethod<?>> getSuperMethods(RefClass<?> refClass) {
        ICollectionList<RefMethod<?>[]> list0 = getSuperclasses(refClass).map(RefClass::getDeclaredMethods);
        CollectionList<RefMethod<?>> list = new CollectionList<>();
        list0.forEach(a -> list.addAll(ICollectionList.asList(a)));
        return list;
    }

    public static ICollectionList<String> getSuperFields(RefClass<?> refClass, boolean isStatic) {
        ICollectionList<RefField<?>[]> list0 = getSuperclasses(refClass).map(RefClass::getDeclaredFields);
        CollectionList<RefField<?>> list = new CollectionList<>();
        list0.forEach(a -> list.addAll(ICollectionList.asList(a)));
        return list.filter(f -> Modifier.isStatic(f.getModifiers()) == isStatic).map(RefField::getName);
    }

    public static ICollectionList<RefField<?>> getSuperFieldsF(RefClass<?> refClass, boolean isStatic) {
        ICollectionList<RefField<?>[]> list0 = getSuperclasses(refClass).map(RefClass::getDeclaredFields);
        CollectionList<RefField<?>> list = new CollectionList<>();
        list0.forEach(a -> list.addAll(ICollectionList.asList(a)));
        return list.filter(f -> Modifier.isStatic(f.getModifiers()) == isStatic);
    }

    public static ICollectionList<RefClass<?>> getSuperclasses(RefClass<?> refClass) {
        CollectionList<RefClass<?>> list = new CollectionList<>();
        Class<?> clazz = refClass.getClazz();
        while (clazz.getSuperclass() != null) {
            list.add(new RefClass<>(clazz.getSuperclass()));
            list.addAll(getAllInterfaces(new RefClass<>(clazz.getSuperclass())));
            clazz = clazz.getSuperclass();
        }
        list.addAll(getAllInterfaces(refClass));
        return list;
    }

    public static ICollectionList<RefClass<?>> getAllInterfaces(RefClass<?> refClass) {
        CollectionList<RefClass<?>> classes = new CollectionList<>();
        for (Class<?> anInterface : refClass.getClazz().getInterfaces()) classes.addAll(getAllInterfaces(new RefClass<>(anInterface)));
        return classes;
    }

    public static ICollectionList<String> getInstanceMethods(RefClass<?> refClass) {
        return new CollectionSet<>(ICollectionList
                .asList(refClass.getDeclaredMethods())
                .filter(r -> !Modifier.isStatic(r.getModifiers()))
                .filter(s -> !s.getName().startsWith("lambda$"))
                .map(m -> {
                    String signature = ICollectionList.asList(m.getParameterTypes()).map(Class::getCanonicalName).join(", ");
                    return m.getName() + "(" + signature + ")";
                })
                .thenAdd("getClass()")
                .thenAddAll(getSuperMethodsAsString(refClass, false)));
    }

    @SuppressWarnings("unchecked")
    public static ICollectionList<RefMethod<?>> getInstanceMethodsM(RefClass<?> refClass) {
        return ICollectionList
                .asList(refClass.getDeclaredMethods())
                .filter(r -> !Modifier.isStatic(r.getModifiers()))
                .filter(s -> !s.getName().startsWith("lambda$"))
                .concat(new ICollectionList[]{ getSuperMethods(refClass) });
    }

    @SuppressWarnings("unchecked")
    public static ICollectionList<RefMethod<?>> getStaticMethodsM(RefClass<?> refClass) {
        return ICollectionList
                .asList(refClass.getDeclaredMethods())
                .filter(r -> Modifier.isStatic(r.getModifiers()))
                .filter(s -> !s.getName().startsWith("lambda$"))
                .concat(new ICollectionList[]{ getSuperMethods(refClass) });
    }

    @SuppressWarnings("unchecked")
    public static ICollectionList<RefMethod<?>> getAllMethodsM(RefClass<?> refClass) {
        if (refClass == null) return new CollectionList<>();
        return ICollectionList
                .asList(refClass.getDeclaredMethods())
                .filter(s -> !s.getName().startsWith("lambda$"))
                .concat(new ICollectionList[]{ getSuperMethods(refClass) });
    }

    public static ICollectionList<String> getAllFields(RefClass<?> refClass) {
        return getStaticFields(refClass).thenAddAll(getInstanceFields(refClass));
    }

    public static ICollectionList<String> getStaticFields(RefClass<?> refClass) {
        return ICollectionList
                .asList(refClass.getDeclaredFields())
                .filter(r -> Modifier.isStatic(r.getModifiers()))
                .map(RefField::getName)
                .thenAddAll(getSuperFields(refClass, true)).unique();
    }

    public static ICollectionList<String> getInstanceFields(RefClass<?> refClass) {
        return ICollectionList
                .asList(refClass.getDeclaredFields())
                .filter(r -> !Modifier.isStatic(r.getModifiers()))
                .map(RefField::getName)
                .thenAddAll(getSuperFields(refClass, false)).unique();
    }

    @SuppressWarnings("unchecked")
    public static ICollectionList<RefField<?>> getInstanceFieldsF(RefClass<?> refClass) {
        return ICollectionList
                .asList(refClass.getDeclaredFields())
                .filter(r -> !Modifier.isStatic(r.getModifiers()))
                .concat(new ICollectionList[]{ getSuperFieldsF(refClass, false) })
                .unique();
    }

    @SuppressWarnings("unchecked")
    public static ICollectionList<RefField<?>> getStaticFieldsF(RefClass<?> refClass) {
        return ICollectionList
                .asList(refClass.getDeclaredFields())
                .filter(r -> Modifier.isStatic(r.getModifiers()))
                .concat(new ICollectionList[]{ getSuperFieldsF(refClass, true) })
                .unique();
    }

    public static ICollectionList<RefField<?>> getFields(RefClass<?> refClass) {
        return getInstanceFieldsF(refClass).thenAddAll(getStaticFieldsF(refClass)).unique();
    }
}
