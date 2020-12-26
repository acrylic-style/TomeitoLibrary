package xyz.acrylicstyle.tomeito_api.reflect;

import util.CollectionList;
import util.CollectionSet;
import util.ICollectionList;
import util.reflect.RefClass;
import util.reflect.RefField;
import util.reflect.RefMethod;

import java.lang.reflect.Modifier;

public class Refs {
    public static CollectionList<?, String> getAllThings(RefClass<?> refClass) {
        if (refClass == null) return new CollectionList<>();
        return getAllMethods(refClass).thenAddAll(getAllFields(refClass));
    }

    public static CollectionSet<?, String> getInstances(RefClass<?> refClass) {
        if (refClass == null) return new CollectionSet<>();
        return new CollectionSet<>(getAllMethodsM(refClass).<String>map(m -> {
            String signature = ICollectionList.asList(m.getParameterTypes()).map(Class::getCanonicalName).join(", ");
            return m.getName() + "(" + signature + ")";
        }));
    }

    public static CollectionList<?, String> getStatics(RefClass<?> refClass) {
        if (refClass == null) return new CollectionList<>();
        return getStaticMethods(refClass).thenAddAll(getStaticFields(refClass));
    }

    public static CollectionList<?, String> getAllMethods(RefClass<?> refClass) {
        if (refClass == null) return new CollectionList<>();
        return getStaticMethods(refClass).thenAddAll(getInstanceMethods(refClass));
    }

    public static CollectionList<?, String> getStaticMethods(RefClass<?> refClass) {
        if (refClass == null) return new CollectionList<>();
        return ICollectionList
                .asList(refClass.getDeclaredMethods())
                .filter(r -> Modifier.isStatic(r.getModifiers()))
                .filter(s -> !s.getName().startsWith("lambda$"))
                .map(m -> {
                    String signature = ICollectionList.asList(m.getParameterTypes()).map(Class::getCanonicalName).join(", ");
                    return m.getName() + "(" + signature + ")";
                })
                .thenAddAll(getSuperMethodsAsString(refClass, true))
                .unique();
    }

    public static CollectionList<?, String> getSuperMethodsAsString(RefClass<?> refClass, boolean isStatic) {
        return getSuperMethods(refClass)
                .filter(r -> Modifier.isStatic(r.getModifiers()) == isStatic)
                .filter(s -> !s.getName().startsWith("lambda$"))
                .map(m -> {
            String signature = ICollectionList.asList(m.getParameterTypes()).map(Class::getCanonicalName).join(", ");
            return m.getName() + "(" + signature + ")";
        });
    }

    public static CollectionList<?, RefMethod<?>> getSuperMethods(RefClass<?> refClass) {
        CollectionList<?, RefMethod<?>[]> list0 = getSuperclasses(refClass).map(RefClass::getDeclaredMethods);
        CollectionList<?, RefMethod<?>> list = new CollectionList<>();
        list0.forEach(a -> list.addAll((ICollectionList<?, RefMethod<?>>) ICollectionList.asList(a)));
        return list;
    }

    public static CollectionList<?, String> getSuperFields(RefClass<?> refClass, boolean isStatic) {
        CollectionList<?, RefField<?>[]> list0 = getSuperclasses(refClass).map(RefClass::getDeclaredFields);
        CollectionList<?, RefField<?>> list = new CollectionList<>();
        list0.forEach(a -> list.addAll((ICollectionList<?, RefField<?>>) ICollectionList.asList(a)));
        return list.filter(f -> Modifier.isStatic(f.getModifiers()) == isStatic).map(RefField::getName);
    }

    public static CollectionList<?, RefField<?>> getSuperFieldsF(RefClass<?> refClass, boolean isStatic) {
        CollectionList<?, RefField<?>[]> list0 = getSuperclasses(refClass).map(RefClass::getDeclaredFields);
        CollectionList<?, RefField<?>> list = new CollectionList<>();
        list0.forEach(a -> list.addAll((ICollectionList<?, RefField<?>>) ICollectionList.asList(a)));
        return list.filter(f -> Modifier.isStatic(f.getModifiers()) == isStatic);
    }

    public static CollectionList<?, RefClass<?>> getSuperclasses(RefClass<?> refClass) {
        CollectionList<?, RefClass<?>> list = new CollectionList<>();
        Class<?> clazz = refClass.getClazz();
        while (clazz.getSuperclass() != null) {
            list.add(new RefClass<>(clazz.getSuperclass()));
            list.addAll(getAllInterfaces(new RefClass<>(clazz.getSuperclass())));
            clazz = clazz.getSuperclass();
        }
        list.addAll(getAllInterfaces(refClass));
        return list;
    }

    public static CollectionList<?, RefClass<?>> getAllInterfaces(RefClass<?> refClass) {
        CollectionList<?, RefClass<?>> classes = new CollectionList<>();
        for (Class<?> anInterface : refClass.getClazz().getInterfaces()) classes.addAll(getAllInterfaces(new RefClass<>(anInterface)));
        return classes;
    }

    public static CollectionSet<?, String> getInstanceMethods(RefClass<?> refClass) {
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
    public static CollectionList<?, RefMethod<?>> getInstanceMethodsM(RefClass<?> refClass) {
        return (CollectionList<?, RefMethod<?>>) ICollectionList
                .asList(refClass.getDeclaredMethods())
                .filter(r -> !Modifier.isStatic(r.getModifiers()))
                .filter(s -> !s.getName().startsWith("lambda$"))
                .concat(new ICollectionList[]{ getSuperMethods(refClass) })
                .unchecked();
    }

    @SuppressWarnings("unchecked")
    public static CollectionList<?, RefMethod<?>> getStaticMethodsM(RefClass<?> refClass) {
        return (CollectionList<?, RefMethod<?>>) ICollectionList
                .asList(refClass.getDeclaredMethods())
                .filter(r -> Modifier.isStatic(r.getModifiers()))
                .filter(s -> !s.getName().startsWith("lambda$"))
                .concat(new ICollectionList[]{ getSuperMethods(refClass) })
                .unchecked();
    }

    @SuppressWarnings("unchecked")
    public static CollectionList<?, RefMethod<?>> getAllMethodsM(RefClass<?> refClass) {
        if (refClass == null) return new CollectionList<>();
        return (CollectionList<?, RefMethod<?>>) ICollectionList
                .asList(refClass.getDeclaredMethods())
                .filter(s -> !s.getName().startsWith("lambda$"))
                .concat(new ICollectionList[] { getSuperMethods(refClass) })
                .unchecked();
    }

    public static CollectionList<?, String> getAllFields(RefClass<?> refClass) {
        return getStaticFields(refClass).thenAddAll(getInstanceFields(refClass));
    }

    public static CollectionList<?, String> getStaticFields(RefClass<?> refClass) {
        return ICollectionList
                .asList(refClass.getDeclaredFields())
                .filter(r -> Modifier.isStatic(r.getModifiers()))
                .map(RefField::getName)
                .thenAddAll(getSuperFields(refClass, true)).unique();
    }

    public static CollectionList<?, String> getInstanceFields(RefClass<?> refClass) {
        return ICollectionList
                .asList(refClass.getDeclaredFields())
                .filter(r -> !Modifier.isStatic(r.getModifiers()))
                .map(RefField::getName)
                .thenAddAll(getSuperFields(refClass, false))
                .unique();
    }

    @SuppressWarnings("unchecked")
    public static CollectionList<?, RefField<?>> getInstanceFieldsF(RefClass<?> refClass) {
        return (CollectionList<?, RefField<?>>) ICollectionList
                .asList(refClass.getDeclaredFields())
                .filter(r -> !Modifier.isStatic(r.getModifiers()))
                .concat(new ICollectionList[] { getSuperFieldsF(refClass, false) })
                .unique()
                .unchecked();
    }

    @SuppressWarnings("unchecked")
    public static CollectionList<?, RefField<?>> getStaticFieldsF(RefClass<?> refClass) {
        return (CollectionList<?, RefField<?>>) ICollectionList
                .asList(refClass.getDeclaredFields())
                .filter(r -> Modifier.isStatic(r.getModifiers()))
                .concat(new CollectionList[]{ getSuperFieldsF(refClass, true) })
                .unique()
                .unchecked();
    }

    public static CollectionList<?, RefField<?>> getFields(RefClass<?> refClass) {
        return getInstanceFieldsF(refClass).thenAddAll(getStaticFieldsF(refClass)).unique();
    }
}
