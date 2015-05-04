package it.micronixnetwork.application.plugin.crude.action;

import it.micronixnetwork.gaf.exception.ApplicationException;


/**
 * Command per la rimozione di una lista di children dal DB
 * 
 * @author kobo
 * 
 */
public class RemoveChild extends Remove {

    private static final long serialVersionUID = 1L;

    @Override
    protected String doIt() throws ApplicationException {
	return super.doIt();
    }
    
    @Override
    protected boolean checkRole() {
	return checkRole(getUser().getRoles(), getModifyRoles());
    }

}
