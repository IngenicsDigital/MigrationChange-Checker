package de.pixel.mcc.internal;

public final class VerifyException extends RuntimeException {

    public VerifyException() {
    }

    public VerifyException(final String message) {
        super(message);
    }

    public VerifyException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public VerifyException(final Throwable cause) {
        super(cause);
    }
}
