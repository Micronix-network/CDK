package it.micronixnetwork.application.plugin.crude.action;

import it.micronixnetwork.application.plugin.crude.helper.Format;
import it.micronixnetwork.gaf.exception.ApplicationException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * Command per la rimozione di una lista di righe dal DB
 * 
 * @author kobo
 * 
 */
public class Remove extends CrudAction {

    private static final long serialVersionUID = 1L;

    private Map<String, List<String>> idObj = new HashMap<String, List<String>>();

    public Map<String, List<String>> getIdObj() {
	return idObj;
    }

    @Override
    protected String doIt() throws ApplicationException {
	
	ActionContext context = ActionContext.getContext();
	ValueStack stack = context.getValueStack();

	debugXML(idObj, 6);

	Class target = getPrototypeClass(targetClass);

	if (idObj != null && idObj.size() > 0) {

	    Class idType = null;

	    Field[] fields = target.getDeclaredFields();
	    for (Field field : fields) {
		if (field.isAnnotationPresent(Id.class)) {
		    idType = field.getType();
		    break;
		}
	    }

	    ArrayList keysToDelete = new ArrayList();

	    if (idType != null) {
		List<String> values = null;
		for (String key : idObj.keySet()) {
		    values = idObj.get(key);
		}
		if (values != null) {
		    for (String value : values) {
			keysToDelete.add(Format.convert(idType, value,stack));
		    }
		}
	    }

	    getCrudeService().remove(getUser(),target, keysToDelete);

	}
	return SUCCESS;
    }

    @Override
    protected boolean checkRole() {
	return checkRole(getUser().getRoles(), getDeleteRoles());
    }

}
