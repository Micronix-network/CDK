package it.micronixnetwork.application.plugin.crude.action;

import it.micronixnetwork.gaf.exception.ActionException;
import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.struts2.action.exception.UnauthorizedUser;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * Command per il recupero di una commessa dal DB
 * 
 * @author kobo
 * 
 */
public class CrudEntry extends CrudAction {

    private static final long serialVersionUID = 1L;


    @Override
    protected boolean checkRole() {
	return true;
    }


    @Override
    protected String doIt() throws ApplicationException {
	Class target = null;
	String prototype = getCardParam("prototype", false);
	try {
	    target = factory.getClassInstance(prototype);

	} catch (Throwable e) {
	    info("TragetClass is void or not assignable");
	}

	if (target != null) {
	    formModel = createFormModel(target);
	}
	return SUCCESS;
    }

}
