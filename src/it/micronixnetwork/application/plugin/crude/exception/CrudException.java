package it.micronixnetwork.application.plugin.crude.exception;

import it.micronixnetwork.gaf.exception.ActionException;

public class CrudException extends ActionException {

    private static final long serialVersionUID = 4974273096708846178L;

    public CrudException() {
	super();
    }

    public CrudException(String message, Throwable cause) {
	super(message, cause);
    }

    public CrudException(String message) {
	super(message);
    }

    public CrudException(Throwable cause) {
	super(cause);
    }
    
    

}
