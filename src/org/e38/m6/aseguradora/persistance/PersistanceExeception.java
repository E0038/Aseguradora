package org.e38.m6.aseguradora.persistance;

/**
 * Created by sergi on 4/7/16.
 */
public class PersistanceExeception extends Exception {
    public PersistanceExeception() {
    }

    public PersistanceExeception(String message) {
        super(message);
    }

    public PersistanceExeception(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistanceExeception(Throwable cause) {
        super(cause);
    }

    public PersistanceExeception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
