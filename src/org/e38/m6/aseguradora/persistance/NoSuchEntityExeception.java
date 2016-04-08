package org.e38.m6.aseguradora.persistance;

/**
 * Created by sergi on 4/7/16.
 */
public class NoSuchEntityExeception extends PersistanceExeception {
    public NoSuchEntityExeception() {
    }

    public NoSuchEntityExeception(String message) {
        super(message);
    }

    public NoSuchEntityExeception(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchEntityExeception(Throwable cause) {
        super(cause);
    }

    public NoSuchEntityExeception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
