package com.exceptions;

/**
 * Created by simon on 5/16/2019.
 */

/**
 * Excepción abstracta para las excepciones que resultan en una respuesta REST.
 *
 */
public abstract class AbstractRestException extends RuntimeException {

    private static final long serialVersionUID = 899932894743929147L;

    /**
     * Constructor sin argumentos.
     */
    public AbstractRestException() {
        super();
    }

    /**
     * Constructor.
     *
     * @param message
     *            mensaje de error.
     * @param cause
     *            causa de la excepción.
     * @param enableSuppression
     *            indica si se puede suprimir la excepción.
     * @param writableStackTrace
     *            indica si el stack trace es writable.
     */
    public AbstractRestException(String message, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Constructor.
     *
     * @param message
     *            mensaje de error.
     * @param cause
     *            causa de la excepción.
     */
    public AbstractRestException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param message
     *            mensaje de error.
     */
    public AbstractRestException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param cause
     *            causa de la excepción.
     */
    public AbstractRestException(Throwable cause) {
        super(cause);
    }

}
