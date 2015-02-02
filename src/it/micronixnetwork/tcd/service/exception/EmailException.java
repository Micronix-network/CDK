package it.micronixnetwork.tcd.service.exception;

import it.micronixnetwork.gaf.exception.ServiceException;


public class EmailException extends ServiceException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public EmailException() {
    }

    public EmailException(Throwable cause) {
	super(cause);
    }

    public EmailException(String message) {
	super(message);
    }

    public EmailException(String message, Throwable cause) {
	super(message, cause);
    }

}
