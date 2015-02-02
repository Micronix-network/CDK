package it.micronixnetwork.application.plugin.crude.action;

import it.micronixnetwork.gaf.exception.ApplicationException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;

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
