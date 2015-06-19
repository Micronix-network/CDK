package it.micronixnetwork.application.plugin.crude.action.renderer;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import it.micronixnetwork.application.plugin.crude.action.CrudAction;
import it.micronixnetwork.application.plugin.crude.annotation.AsincInfo;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.AutocompleteRenderer;
import it.micronixnetwork.gaf.exception.ActionException;
import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.struts2.action.JSONAction;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Command per il recupero di una commessa dal DB
 * 
 * @author kobo
 * 
 */
public class AutocompleteQuery extends CrudAction implements JSONAction{

    private static final long serialVersionUID = 1L;
    
    private String fieldName;

    private String fieldValue;
    
    private String className;
    
    private String jsonOutData;
    
    public String getjsonOutData() {
	return jsonOutData;
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
	JSONArray array=new JSONArray();
	
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
	    
            for(int i=0;i<qresult.size();i++){
                   Object[] row=qresult.get(i);
                   JSONObject obj=new JSONObject();
                   obj.put("label", row[1]);
                   obj.put("value", row[0]);
                   array.put(obj);
            } 
	}
	jsonOutData=array.toString();
	
	return SUCCESS;
    }

    @Override
    protected boolean checkRole() {
	return true;
    }
}
