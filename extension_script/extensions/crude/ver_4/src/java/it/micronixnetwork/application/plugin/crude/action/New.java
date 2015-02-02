package it.micronixnetwork.application.plugin.crude.action;

import it.micronixnetwork.application.plugin.crude.annotation.Children;
import it.micronixnetwork.application.plugin.crude.model.FormModel;
import it.micronixnetwork.gaf.exception.ActionException;
import it.micronixnetwork.gaf.exception.ApplicationException;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command per la creazione di un nuovo oggetto sul DB
 * 
 * @author kobo
 * 
 */
public class New extends CrudAction {

    private static final long serialVersionUID = 1L;

    private Object target;
    
    private Map<String, String> filters = new HashMap<String, String>();
    
    public New() {
	operation = OP_INSERT;
    }
    
    /**
     * Mappa per utilizzare i campi di filtro come possibili valori di input
     * il filtro viene usato anche per il passaggio di parametri FK
     * @return
     */
    public Map<String, String> getFilters() {
   	return filters;
       }
    

    @Override
    protected String doIt() throws ApplicationException {
	if(targetClass==null) throw new ActionException("Tipo oggetto non definito");
	// Creo il nuovo oggetto da inserire nel DB
	Class clazz = getPrototypeClass(targetClass);
	
	if(clazz==null)
	    throw new ActionException("Classe dell'oggetto non caricabile");
	
	formModel=createFormModel(clazz);
	
	target = compose(clazz, filters,true);
	
	return SUCCESS;
    }

    public Object getTarget() {
	return target;
    }

    @Override
    protected boolean checkRole() {
	return checkRole(getUser().getRoles(), getInsertRoles());
    }

  

}
