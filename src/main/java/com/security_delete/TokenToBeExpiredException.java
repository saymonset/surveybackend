package com.security_delete;

/**
 *  Lanzada cuando el token está próximo a expirar.
 *
 * Created by simon on 5/15/2019.
 */
public class TokenToBeExpiredException extends RuntimeException {

    private static final long serialVersionUID = 6843809149684938492L;

    /**
     * Contructor
     */
    public TokenToBeExpiredException() {
        super();
    }

    /**
     * Contructor
     *
     * @param message
     */
    public TokenToBeExpiredException(String message) {
        super(message);
    }

    /**
     * Contructor
     *
     * @param message
     * @param cause
     */
    public TokenToBeExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
