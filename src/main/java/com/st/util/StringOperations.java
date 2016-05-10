package com.st.util;

/**
 * Contains convenience helpers for string manipulations.
 */
public class StringOperations {

    /**
     * Returns a string with the last found character replaced (searching from
     * tail to head).
     *
     * @param str the string
     * @param toReplace the character to look for and replace.
     * @param replacement the replacement.
     * @return the updated string.
     */
    public static String replaceLast(String str, char toReplace, char replacement) {
        StringBuilder b = new StringBuilder(str);
        int idx = str.lastIndexOf(toReplace);
        b.replace(idx, idx + 1, "" + replacement);
        return b.toString();
    }

    /**
     * Returns a string with the last character replaced.
     *
     * @param str the string.
     * @param replacement the replacement for the tail.
     * @return the updated string.
     */
    public static String replaceLastChar(String str, char replacement) {
        if (str == null || str.equals("")) {
            return str;
        }
        return str.substring(0, str.length() - 1) + replacement;
    }

    /**
     * Returns a string representation of a byte count.
     *
     * @param bytes the no of bytes.
     * @return the readable string.
     */
    public static String humanReadableByteCount(long bytes) {
        int unit = 1024;
        if (bytes < unit) {
            return bytes + " B";
        }
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = ("KMGTPE").charAt(exp - 1) + ("i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

}
