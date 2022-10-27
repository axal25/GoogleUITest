package utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssertionUtils {

    public static void assertContainsIgnoreCase(String stringContaining, String substring) {
        assertTrue(StringUtils.containsIgnoreLetterCase(stringContaining, substring),
                String.format("\"%s\" does not contain \"%s\" (Ignoring Letter Case)",
                        stringContaining, substring));
    }

    public static void assertContains(String stringContaining, String substring) {
        assertTrue(StringUtils.contains(stringContaining, substring),
                String.format("\"%s\" does not contain \"%s\"",
                        stringContaining, substring));
    }
}
