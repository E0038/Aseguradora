package org.e38.m6.aseguradora.control;

import java.util.Arrays;
import java.util.List;

/**
 * @author sergi
 */
public class SqlInjectorChecker {

    private static final List<String> INVALID_TEXTS = Arrays.asList(
            "WHILE", "SELECT", "INSERT", "DELETE", "DROP", "=", "'",//to not break varchars
            "CREATE", "EXEC", ";", "\"", "EXECUTE"//, "@", "&", "#", "|", "+", ".", "(", ")"
    );

    public static boolean checkInputStrings(String... strings) {
        boolean ck = true;
        for (String s : strings) {
            ck = checkInputSentence(s);
            if (!ck) {
                break;
            }
        }
        return ck;
    }

    /**
     * @param s
     * @return true if valid input.
     */
    public static boolean checkInputSentence(String s) {
        if (!s.matches("([\\w]*[\\s]*)*")) {//only alphanumeric words
            return false;
        }
        s = s.toUpperCase();
        return !INVALID_TEXTS.contains(s);
    }
}
