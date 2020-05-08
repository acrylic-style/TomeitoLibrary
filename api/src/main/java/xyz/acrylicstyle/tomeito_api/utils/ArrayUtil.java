package xyz.acrylicstyle.tomeito_api.utils;

@SuppressWarnings("unused")
public class ArrayUtil {
    /**
     * Check if array contains needle but not case-sensitive.
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
     * Check if array contains needle and that element contains all of multiple needle but not case-sensitive.
     * @param array Haystack
     * @param needle Needle
     * @return true if found, otherwise false
     */
    public static boolean contains(String[] array, String... needle) {
        boolean found = false;
        for (String element : array) {
            found = true;
            for (String e : needle) {
                if (found) found = element.contains(e);
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
}
