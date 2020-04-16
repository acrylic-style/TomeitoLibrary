package xyz.acrylicstyle.tomeito_api.utils;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Creates dummy list that does absolutely nothing.
 */
public class DummyList<E> implements List<E> {
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return true;
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return Collections.emptyIterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @SuppressWarnings("SuspiciousToArrayCall")
    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        return new ArrayList<E>().toArray(a);
    }

    @Override
    public boolean add(E e) {
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return true;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return true;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> c) {
        return true;
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends E> c) {
        return true;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return true;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return true;
    }

    @Override
    public void clear() {}

    @Override
    public E get(int index) { return null; }

    @Override
    public E set(int index, E element) { return null; }

    @Override
    public void add(int index, E element) {}

    @Override
    public E remove(int index) { return null; }

    @Override
    public int indexOf(Object o) { return 0; }

    @Override
    public int lastIndexOf(Object o) { return 0; }

    @NotNull
    @Override
    public ListIterator<E> listIterator() { return Collections.emptyListIterator(); }

    @NotNull
    @Override
    public ListIterator<E> listIterator(int index) { return Collections.emptyListIterator(); }

    @NotNull
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new ArrayList<>();
    }
}
