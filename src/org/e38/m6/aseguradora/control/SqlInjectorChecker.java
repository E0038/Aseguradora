package org.e38.m6.aseguradora.control;

/**
 * @author sergi
 */
public class SqlInjectorChecker {

    private static final String[] INVALID_TEXTS = {
        "WHILE", "SELECT", "INSERT", "DELETE", "DROP", "=", "'",//to not break varchars
        "CREATE", "EXEC", ";", "\""//, "@", "&", "#", "|", "+", ".", "(", ")"
    };

    /**
     *
     *
     * @param s
     * @return true if valid input.
     */
    public static boolean checkInputSentence(String s) {
        if (!(s.matches("([\\w]*[\\s]*)*"))) {//only alphanumeric words
            return false;
        }
        s = s.toUpperCase();
        boolean ck = true;
        for (String invalidText : INVALID_TEXTS) {
            if (s.contains(invalidText)) {
                ck = false;
                break;
            }
        }
        return ck;
    }

    public static boolean checkInputStrings(String... strings) {
        boolean ck = true;
        for (String s : strings) {
            ck = checkInputSentence(s) && s.length() <= 20;
            if (!ck) {
                break;
            }
        }
        return ck;
    }
}
