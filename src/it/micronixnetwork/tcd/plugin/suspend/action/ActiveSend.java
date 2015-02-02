package it.micronixnetwork.tcd.plugin.suspend.action;

import it.micronixnetwork.application.plugin.crude.action.CrudAction;
import it.micronixnetwork.application.plugin.crude.helper.Format;
import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.tcd.plugin.suspend.service.SuspendService;
import it.micronixnetwork.tcd.service.EngineService;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

public class ActiveSend extends CrudAction implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private SuspendService suspendService;
    
    public void setSuspendService(SuspendService suspendService) {
	this.suspendService = suspendService;
    }

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

	    suspendService.send(keysToDelete);

	}
	return SUCCESS;
    }

    @Override
    protected boolean checkRole() {
	return true;
    }

}
