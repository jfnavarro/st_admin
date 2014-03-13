package com.spatialtranscriptomics.util;

/**
 * Contains convenience helpers for string manipulations.
 */
public class StringOperations {

	/**
	 * Returns a string with the last found character replaced (searching from
	 * tail to head).
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
	
}
