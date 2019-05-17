package com.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by simon on 5/16/2019.
 */

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class ServiceErrorRestException extends AbstractRestException {

    private static final long serialVersionUID = 8865835735154620120L;

    /**
     * Constructor sin argumentos.
     */
    public ServiceErrorRestException() {
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
    public ServiceErrorRestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
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
    public ServiceErrorRestException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param message
     *            mensaje de error.
     */
    public ServiceErrorRestException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param cause
     *            causa de la excepción.
     */
    public ServiceErrorRestException(Throwable cause) {
        super(cause);
    }

}
