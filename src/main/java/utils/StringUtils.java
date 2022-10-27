package utils;

public class StringUtils {
    public static final String EMPTY = "";

    public static boolean isNullOrEmptyOrBlank(String inputString) {
        return inputString == null
                || inputString.isEmpty()
                || inputString.isBlank();
    }

    static boolean containsIgnoreLetterCase(String stringContaining, String substring) {
        if (stringContaining == substring) {
            return true;
        }
        if (stringContaining == null || substring == null) {
            return false;
        }
        return contains(stringContaining.toUpperCase(), substring.toUpperCase());
    }

    static boolean contains(String stringContaining, String substring) {
        if (stringContaining == substring) {
            return true;
        }
        return stringContaining.contains(substring);
    }
}
