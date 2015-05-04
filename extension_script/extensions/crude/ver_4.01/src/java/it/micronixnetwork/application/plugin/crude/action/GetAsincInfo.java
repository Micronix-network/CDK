package it.micronixnetwork.application.plugin.crude.action;

import it.micronixnetwork.application.plugin.crude.annotation.AsincInfo;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.helper.FieldUtil;
import it.micronixnetwork.application.plugin.crude.helper.Format;
import it.micronixnetwork.gaf.exception.ActionException;
import it.micronixnetwork.gaf.exception.ApplicationException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * Command per il recupero di una commessa dal DB
 * 
 * @author kobo
 * 
 */
public class GetAsincInfo extends CrudAction {

    private static final long serialVersionUID = 1L;

    private String fieldName;

    private String fieldValue;
    
    private String className;

    private String infoString;

    public void setFieldName(String fieldName) {
	this.fieldName = fieldName;
    }

    public void setFieldValue(String fieldValue) {
	this.fieldValue = fieldValue;
    }
    
    public void setClassName(String className) {
	this.className = className;
    }
    

    public String getInfoString() {
	return infoString;
    }

    
    @Override
    protected String doIt() throws ApplicationException {
	
	ActionContext context = ActionContext.getContext();
	ValueStack stack = context.getValueStack();
	
	infoString="";

	if (fieldName == null)
	    throw new ActionException("Assert: No fieldName difined");

	if (fieldValue == null)
	    throw new ActionException("Assert: No fieldFValue difined");
	
	if (className == null)
	    throw new ActionException("Assert: No className difined");
	
	if(fieldValue.trim().equals("")){
	    return SUCCESS;
	}

	Class target=getPrototypeClass(className);

	Field field=null;
	try {
	    field = target.getField(fieldName);
	} catch (Exception e) {
	    throw new ActionException("No field found: " + fieldName, e);
	}
	
	AsincInfo asincInfo=(AsincInfo)field.getAnnotation(AsincInfo.class);

	if(asincInfo !=null && !asincInfo.mappedObject().equals("nill")){
	    String infoObj=asincInfo.mappedObject();
	    
	    String fieldNameToFill=asincInfo.targetField();
	    
	    Field fieldTofill=null;
	    if(!fieldNameToFill.equals("nill")){
		try {
		    fieldTofill = target.getField(fieldNameToFill);
		} catch (Exception e) {
		    throw new ActionException("No field found: " + fieldName, e);
		}
	    }
	    
	    Object value=Format.convert(field.getType(), fieldValue,stack);
	    
	    HashMap<String,Object> params=new HashMap<String, Object>();
	    
	    String query="From "+infoObj+" where id=:id";
	    
	    params.put("id", value);
	    
	    Object result=(Object)queryService.uniqueResult(query, params, false);
	    
	    if(result!=null && fieldTofill!=null && result.getClass().equals(fieldTofill.getType()) ){
		StringBuffer out=new StringBuffer("<script type=\"text/javascript\">");
		Map<String, Field> fields=FieldUtil.retriveWorkFields(result.getClass());
		for (String fName : fields.keySet()) {
		    Field fld=fields.get(fName);
		    ToInput input=fld.getAnnotation(ToInput.class);
		    if(input!=null){
			String fValue=Format.formatValue(FieldUtil.retriveFieldValue(fName, result),fld,stack);
		    	out.append(getCardId()+"_"+getUiid()+"_"+target.getSimpleName()+"_set_"+fieldNameToFill+"_"+fName+"('"+fValue+"');");
		    }
		}
		out.append("</script>");
		infoString=out.toString();
	    }
		
	    
	}
	return SUCCESS;
    }

    @Override
    protected boolean checkRole() {
	return true;
    }
}
