package it.micronixnetwork.application.plugin.crude.action;

import it.micronixnetwork.application.plugin.crude.model.Message;
import it.micronixnetwork.gaf.exception.ApplicationException;

import java.util.Map;

/**
 * Command per il recupero di una commessa dal DB
 * 
 * @author kobo
 * 
 */
public class Create extends EditAction {

    private static final long serialVersionUID = 1L;

    private Object insertedId;

    private Map<String, String> childNames;

    public Map<String, String> getChildNames() {
	return childNames;
    }

    public Create() {
	operation = OP_UPDATE;
    }

    public Object getInsertedId() {
	return insertedId;
    }

    @Override
    protected void validInput() {
	String ruleFile = "";
	Message msg = new Message();
	try {
	    Class target = getPrototypeClass(targetClass);
	    ruleFile = target.getSimpleName() + "Create";
	} catch (Exception ex) {
	    debug("Classe per regole non trovata", ex);
	}

	// DroolsFormMap input=new DroolsFormMap(getObjState());
	try {
	    fireRules(ruleFile, getObjState(), msg);
	} catch (Exception ex) {
	    debug("Esecuzione regole non eseguita", ex);
	}
	if (msg.getStatus() >= 0) {
	    super.validInput();
	} else {
	    addFieldError("generic", msg.getMessage());
	}
    }
    
    @Override
    protected String doIt(Class target) throws ApplicationException {

	Object obj = compose(target, getObjState(), false);

	if (obj != null) {
	    if (!rulesControl(target, obj))
		return INPUT;
	    insertedId = getCrudeService().create(getUser(),obj);
	    childNames = childFields(obj.getClass());

	} else {
	    addActionError(getText("crude.insert.error.packaging"));
	    return INPUT;
	}

	return SUCCESS;
    }

    @Override
    protected boolean checkRole() {
	return checkRole(getUser().getRoles(), getInsertRoles());
    }

}
