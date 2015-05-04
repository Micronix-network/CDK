package it.micronixnetwork.application.plugin.crude.action;

import it.micronixnetwork.gaf.exception.ActionException;
import it.micronixnetwork.gaf.exception.ApplicationException;

import java.util.HashMap;
import java.util.Map;

/**
 * Command per l'inserimento di un child ad un oggetto parent
 * 
 * @author kobo
 * 
 */
public class CreateChild extends EditAction {

    private static final long serialVersionUID = 1L;

    private Object insertedId;

    private Map<String, String> idObj = new HashMap<String, String>();
    
    public CreateChild() {
	operation=OP_UPDATE;
    }
    
    private String childFieldName;
    
    public void setChildFieldName(String childFieldName) {
	this.childFieldName = childFieldName;
    }
    
    public String getChildFieldName() {
	return childFieldName;
    }

    public Object getInsertedId() {
	return insertedId;
    }

    @Override
    protected String doIt(Class target) throws ApplicationException {
	
	if (targetParent == null)
	    throw new ActionException("targetParent not defined");

	Class parentClsObj = getPrototypeClass(targetParent);

	if (target == null)
	    throw new ActionException("targetClass not loadable");

	if (parentClsObj == null)
	    throw new ActionException("targetParent not loadable");

	if (idObj.size() == 0)
	    throw new ActionException("Parent PK not defined");

	Object parentPK = createObjectId(parentClsObj, idObj);

	Object child = compose(target, getObjState(),false);

	// Aggiunta child al parent

	if (child != null && childFieldName!=null) {
	    if (!rulesControl(target, child)) return INPUT;
	    getCrudeService().addChildren(getUser(),parentClsObj, parentPK,childFieldName, child);
	} else {
	    addActionError(getText("crude.insert.error.packaging"));
	    return INPUT;
	}
	return SUCCESS;
    }

    public Map<String, String> getIdObj() {
	return idObj;
    }

    public void setIdObj(Map<String, String> idObj) {
	this.idObj = idObj;
    }

    @Override
    protected boolean checkRole() {
	return checkRole(getUser().getRoles(), getModifyRoles());
    }

}
