package it.micronixnetwork.tcd.service.exception;

import it.micronixnetwork.gaf.exception.ServiceException;


public class FaxException extends ServiceException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public FaxException() {
    }

    public FaxException(Throwable cause) {
	super(cause);
    }

    public FaxException(String message) {
	super(message);
    }

    public FaxException(String message, Throwable cause) {
	super(message, cause);
    }

}
