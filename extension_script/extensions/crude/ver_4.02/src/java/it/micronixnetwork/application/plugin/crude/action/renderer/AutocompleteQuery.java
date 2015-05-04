package it.micronixnetwork.application.plugin.crude.action.renderer;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import it.micronixnetwork.application.plugin.crude.action.CrudAction;
import it.micronixnetwork.application.plugin.crude.annotation.AsincInfo;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.AutocompleteRenderer;
import it.micronixnetwork.gaf.exception.ActionException;
import it.micronixnetwork.gaf.exception.ApplicationException;

/**
 * Command per il recupero di una commessa dal DB
 * 
 * @author kobo
 * 
 */
public class AutocompleteQuery extends CrudAction {

    private static final long serialVersionUID = 1L;
    
    private String fieldName;

    private String fieldValue;
    
    private String className;
    
    private String infoString;
    
    public String getInfoString() {
	return infoString;
    }
    
    public void setFieldName(String fieldName) {
	this.fieldName = fieldName;
    }

    public void setFieldValue(String fieldValue) {
	this.fieldValue = fieldValue;
    }
    
    public void setClassName(String className) {
	this.className = className;
    }

    
    @Override
    protected String doIt() throws ApplicationException {
	StringBuffer out=new StringBuffer("[");
	
	if (fieldName == null)
	    throw new ActionException("Assert: No fieldName difined");

	if (fieldValue == null)
	    throw new ActionException("Assert: No fieldFValue difined");
	
	if (className == null)
	    throw new ActionException("Assert: No className difined");
	
	if(fieldValue==null || fieldValue.length()<2){
	    return SUCCESS;
	}
	
	Class target=getPrototypeClass(className);
	
	Field field=null;
	try {
	    field = target.getField(fieldName);
	} catch (Exception e) {
	    throw new ActionException("No field found: " + fieldName, e);
	}
	
	AutocompleteRenderer autoc=(AutocompleteRenderer)field.getAnnotation(AutocompleteRenderer.class);
	
	if(autoc!=null && !autoc.jsonQuery().equals("nill")){
	    
	    HashMap<String,Object> params=new HashMap<String, Object>();
	    
	    String query=autoc.jsonQuery();
	    
	    params.put("fieldValue", fieldValue+"%");
	    
	    List<Object[]> qresult=(List<Object[]>)queryService.search(query, params, false);
	    
	    out=new StringBuffer("[");
	    
	    for(int i=0;i<qresult.size();i++){
		Object[] row=qresult.get(i);
		out.append("{\"label\":\""+row[0].toString()+"\",\"value\":\""+row[1].toString()+"\"}");
		if(i<qresult.size()-1){
		    out.append(",");
		}
	    }
	   
	}
	
	out.append("]");
	
	infoString=out.toString();
	
	return SUCCESS;
    }

    @Override
    protected boolean checkRole() {
	return true;
    }
}
