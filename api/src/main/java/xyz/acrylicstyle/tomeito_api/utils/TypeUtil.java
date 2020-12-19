package xyz.acrylicstyle.tomeito_api.utils;

@SuppressWarnings({ "UnusedReturnValue" })
public class TypeUtil {
    public static boolean isInt(String arg) {
        try {
            Integer.parseInt(arg);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String arg) {
        try {
            Double.parseDouble(arg);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @deprecated Use {@link Boolean#parseBoolean(String)}
     */
    @Deprecated
    public static boolean parseBoolean(String bool) throws Exception {
        if (bool.equalsIgnoreCase("true")) return true;
        else if (bool.equalsIgnoreCase("false")) return false;
        else throw new Exception("Provided string is not boolean.");
    }

    public static boolean isBoolean(String arg) {
        return arg.equalsIgnoreCase("false") || arg.equalsIgnoreCase("true");
    }

    public static boolean isFloat(String arg) {
        try {
            Float.parseFloat(arg);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
