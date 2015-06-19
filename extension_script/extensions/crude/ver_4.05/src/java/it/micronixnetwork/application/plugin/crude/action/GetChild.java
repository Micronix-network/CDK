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
public class GetChild extends CrudAction {

    private static final long serialVersionUID = 1L;

    private Map<String, String> idObj = new HashMap<String, String>();

    private Object target;

    private Object updateId;

    private String childFieldName;

    public void setChildFieldName(String childFieldName) {
        this.childFieldName = childFieldName;
    }

    public String getChildFieldName() {
        return childFieldName;
    }

    public Object getUpdateId() {
        return updateId;
    }

    public GetChild() {
        operation = OP_UPDATE;
    }

    @Override
    protected String doIt() throws ApplicationException {

        // Assert
        if (idObj.size() == 0) {
            throw new ActionException("Parametro kiave non inizializzato");
        }

        if (targetClass == null) {
            throw new ActionException("Tipo oggeto non definito");
        }

        if (childFieldName == null) {
            throw new ActionException("Nome del campo child non definito");
        }

        Class clazz = getPrototypeClass(targetClass);

        updateId = createObjectId(clazz, idObj);

        if (clazz != null) {
            target = getCrudeService().get(clazz, updateId);
        } else {
            throw new ActionException("Classe dell'oggetto non caricabile");
        }

        return SUCCESS;
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
