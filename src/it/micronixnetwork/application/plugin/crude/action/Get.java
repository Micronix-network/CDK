package it.micronixnetwork.application.plugin.crude.action;

import it.micronixnetwork.gaf.exception.ActionException;
import it.micronixnetwork.gaf.exception.ApplicationException;

import java.util.HashMap;
import java.util.Map;

/**
 * Command per il recupero di una commessa dal DB
 *
 * @author kobo
 *
 */
public class Get extends CrudAction {

    private static final long serialVersionUID = 1L;

    private Map<String, String> idObj = new HashMap<String, String>();

    private Object target;

    private Map<String, String> childNames;

    private Object updateId;

    public Object getUpdateId() {
        return updateId;
    }

    public Get() {
        operation = OP_UPDATE;
    }

    @Override
    protected String doIt() throws ApplicationException {

        //Assert
        if (idObj.size() == 0) {
            throw new ActionException("Object key paramenter not retrived");
        }

        if (targetClass == null) {
            throw new ActionException("Object class not defined");
        }

        Class clazz = getPrototypeClass(targetClass);

        updateId = createObjectId(clazz, idObj);

        if (updateId == null) {
            throw new ActionException("Object primarykey not created");
        }

        if (clazz != null) {
            target = getCrudeService().get(clazz, updateId);
        } else {
            throw new ActionException("Objcet class not loadable");
        }

        childNames = childFields(clazz);

        return SUCCESS;
    }

    public Map<String, String> getChildNames() {
        return childNames;
    }

    public Object getTarget() {
        return target;
    }

    public Map<String, String> getIdObj() {
        return idObj;
    }

    public void setIdObj(Map<String, String> idObj) {
        this.idObj = idObj;
    }

    @Override
    protected boolean checkRole() {
        return true;
    }

}
