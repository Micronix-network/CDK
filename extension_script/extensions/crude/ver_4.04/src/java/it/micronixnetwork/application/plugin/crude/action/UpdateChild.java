package it.micronixnetwork.application.plugin.crude.action;

import it.micronixnetwork.gaf.exception.ApplicationException;

/**
 * Command per la modifica di un oggetto sul DB
 * 
 * @author kobo
 * 
 */
public class UpdateChild extends Update {

    private static final long serialVersionUID = 1L;
    
    private String childFieldName;
    
    public void setChildFieldName(String childFieldName) {
	this.childFieldName = childFieldName;
    }
    
    public String getChildFieldName() {
	return childFieldName;
    }
    
    public UpdateChild() {
	operation=OP_UPDATE;
    }

    @Override
    protected String doIt(Class target) throws ApplicationException {
	return super.doIt(target);
    }

    @Override
    protected boolean checkRole() {
	return checkRole(getUser().getRoles(), getModifyRoles());
    }

}
