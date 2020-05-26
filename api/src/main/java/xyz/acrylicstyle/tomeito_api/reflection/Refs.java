package xyz.acrylicstyle.tomeito_api.reflection;

import util.CollectionList;
import util.CollectionSet;
import util.ICollectionList;
import util.reflect.RefClass;
import util.reflect.RefField;
import util.reflect.RefMethod;

import java.lang.reflect.Modifier;

public class Refs {
    public static CollectionList<String> getAllThings(RefClass<?> refClass) {
        return getAllMethods(refClass).concat(getAllFields(refClass));
    }

    public static CollectionSet<String> getInstances(RefClass<?> refClass) {
        return getInstanceMethods(refClass).concat(getInstanceFields(refClass));
    }

    public static CollectionList<String> getStatics(RefClass<?> refClass) {
        return getStaticMethods(refClass).concat(getStaticFields(refClass));
    }

    public static CollectionList<String> getAllMethods(RefClass<?> refClass) {
        return getStaticMethods(refClass).concat(getInstanceMethods(refClass));
    }

    public static CollectionList<String> getStaticMethods(RefClass<?> refClass) {
        return ICollectionList
                .asList(refClass.getDeclaredMethods())
                .filter(r -> Modifier.isStatic(r.getModifiers()))
                .filter(s -> !s.getName().startsWith("lambda$"))
                .map(m -> {
                    String signature = ICollectionList.asList(m.getParameterTypes()).map(Class::getCanonicalName).join(", ");
                    return m.getName() + "(" + signature + ")";
                }).concat(getSuperMethodsAsString(refClass, true)).unique();
    }

    public static CollectionList<String> getSuperMethodsAsString(RefClass<?> refClass, boolean isStatic) {
        return getSuperMethods(refClass)
                .filter(r -> Modifier.isStatic(r.getModifiers()) == isStatic)
                .filter(s -> !s.getName().startsWith("lambda$"))
                .map(m -> {
            String signature = ICollectionList.asList(m.getParameterTypes()).map(Class::getCanonicalName).join(", ");
            return m.getName() + "(" + signature + ")";
        });
    }

    public static CollectionList<RefMethod<?>> getSuperMethods(RefClass<?> refClass) {
        CollectionList<RefMethod<?>[]> list0 = getSuperclasses(refClass).map(RefClass::getDeclaredMethods);
        CollectionList<RefMethod<?>> list = new CollectionList<>();
        list0.forEach(a -> list.addAll(ICollectionList.asList(a)));
        return list.unique();
    }

    public static CollectionList<String> getSuperFields(RefClass<?> refClass, boolean isStatic) {
        CollectionList<RefField<?>[]> list0 = getSuperclasses(refClass).map(RefClass::getDeclaredFields);
        CollectionList<RefField<?>> list = new CollectionList<>();
        list0.forEach(a -> list.addAll(ICollectionList.asList(a)));
        return list.filter(f -> Modifier.isStatic(f.getModifiers()) == isStatic).map(RefField::getName);
    }

    public static CollectionList<RefClass<?>> getSuperclasses(RefClass<?> refClass) {
        CollectionList<RefClass<?>> list = new CollectionList<>();
        Class<?> clazz = refClass.getClazz();
        while (clazz.getSuperclass() != null) {
            list.add(new RefClass<>(clazz.getSuperclass()));
            list.addAll(ICollectionList.asList(clazz.getInterfaces()).map(RefClass::new));
            clazz = clazz.getSuperclass();
        }
        return list;
    }

    public static CollectionSet<String> getInstanceMethods(RefClass<?> refClass) {
        return new CollectionSet<>(ICollectionList
                .asList(refClass.getDeclaredMethods())
                .filter(r -> !Modifier.isStatic(r.getModifiers()))
                .filter(s -> !s.getName().startsWith("lambda$"))
                .map(m -> {
                    String signature = ICollectionList.asList(m.getParameterTypes()).map(Class::getCanonicalName).join(", ");
                    return m.getName() + "(" + signature + ")";
                }).concat(getSuperMethodsAsString(refClass, false)));
    }

    public static CollectionList<String> getAllFields(RefClass<?> refClass) {
        return getStaticFields(refClass).concat(getInstanceFields(refClass));
    }

    public static CollectionList<String> getStaticFields(RefClass<?> refClass) {
        return ICollectionList
                .asList(refClass.getDeclaredFields())
                .filter(r -> Modifier.isStatic(r.getModifiers()))
                .map(RefField::getName)
                .concat(getSuperFields(refClass, true)).unique();
    }

    public static CollectionList<String> getInstanceFields(RefClass<?> refClass) {
        return ICollectionList
                .asList(refClass.getDeclaredFields())
                .filter(r -> !Modifier.isStatic(r.getModifiers()))
                .concat()
                .map(RefField::getName)
                .concat(getSuperFields(refClass, false)).unique();
    }
}
