package xyz.acrylicstyle.tomeito_api.utils;

import java.lang.reflect.Array;

public class ArrayUtil {
    /**
     * Check if array(haystack) contains needle but case-insensitive.
     * @param array Haystack
     * @param needle Needle
     * @return true if found, otherwise false
     */
    public static boolean includes(String[] array, String needle) {
        boolean found = false;
        for (String element : array) {
            if (element.equalsIgnoreCase(needle)) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * Returns array element index that contains needle but not case-sensitive.<br>
     * If there are multiple needles in haystack, returns first one.
     * @param array Haystack
     * @param needle Needle
     * @return a positive number if found, returns -1 if not found
     */
    public static int indexOf(String[] array, String needle) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equalsIgnoreCase(needle)) return i;
        }
        return -1;
    }

    /**
     * Expands array size to the specific size.
     * @param array an array, it won't be modified.
     * @param size the new size of the array, this won't do anything if array size >= size.
     * @param <T> the array type
     * @return new array or clone of the array at the param2 if array size >= param3
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] expand(T[] array, int size) {
        if (array.length >= size) return array.clone();
        Object arr = Array.newInstance(array.getClass().getComponentType(), size);
        for (int i = 0; i < array.length; i++) Array.set(arr, i, array[i]);
        return (T[]) arr;
    }
}
