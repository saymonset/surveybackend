package com.security;

/**
 * Lanzado cuando el token del usuario expiró.
 *
 * Created by simon on 5/15/2019.
 */
public class TokenExpiredException  extends RuntimeException {

    private static final long serialVersionUID = 4583192505040884562L;

    /**
     * Contructor
     */
    public TokenExpiredException() {
        super();
    }

    /**
     * Contructor
     *
     * @param message
     */
    public TokenExpiredException(String message) {
        super(message);
    }

    /**
     * Contructor
     *
     * @param message
     * @param cause
     */
    public TokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
