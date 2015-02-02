package it.micronixnetwork.tcd.service.exception;

import it.micronixnetwork.gaf.exception.ServiceException;


public class EngineException extends ServiceException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public EngineException() {
    }

    public EngineException(Throwable cause) {
	super(cause);
    }

    public EngineException(String message) {
	super(message);
    }

    public EngineException(String message, Throwable cause) {
	super(message, cause);
    }

}
