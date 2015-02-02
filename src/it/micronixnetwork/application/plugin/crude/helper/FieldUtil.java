package it.micronixnetwork.application.plugin.crude.helper;

import it.micronixnetwork.application.plugin.crude.annotation.Children;
import it.micronixnetwork.gaf.util.ObjectUtil;
import it.micronixnetwork.gaf.util.StringUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

public class FieldUtil {

    static public Map<String, Field> retriveWorkFields(Class type) {
	HashMap<String, Field> result = new LinkedHashMap<String, Field>();

	Field[] fields = type.getDeclaredFields();

	for (Field field : fields) {
	    if (field.getModifiers() == 1) {
		boolean subinspection =field.isAnnotationPresent(OneToOne.class) || field.isAnnotationPresent(ManyToOne.class);
		if (subinspection) {
		    Class clazz = field.getType();
		    Field[] subFields = clazz.getDeclaredFields();
		    for (Field subField : subFields) {
			if (subField.getModifiers() == 1) {
			    result.put(field.getName() + "." + subField.getName(), subField);
			}
		    }
		} else {
		    result.put(field.getName(), field);
		}
	    }
	}

	return result;
    }
    
    static public Map<String, Field> retriveChildrenField(Class type) {
	HashMap<String, Field> result = new LinkedHashMap<String, Field>();
	Map<String,Field> fields=FieldUtil.retriveWorkFields(type);
	//Field[] fields = targetClass.getDeclaredFields();
	for (String fieldKey : fields.keySet()) {
	    Field field=fields.get(fieldKey);
	    if (field.getModifiers() == 1) {
		Children ca = field.getAnnotation(Children.class);
		if(ca !=null)
		    result.put(fieldKey, field);
	    }
	}
	return result;
    }
    
    static public Object retriveFieldValue(String field_key,Object obj)  {
	Object result=obj;
	for (String field_name : StringUtil.stringToList(field_key,"\\.")) {
	    Field fld;
	    try {
		fld = result.getClass().getField(field_name);
	    } catch (Exception e) {
		return null;
	    } 
	    result=ObjectUtil.getFieldValue(fld, result);
	}
	return result;
    }
    
    static public Field retriveField(String field_key,Class clazz)  {
	Field result=null;
	for (String field_name : StringUtil.stringToList(field_key,"\\.")) {
	    try {
		result = clazz.getField(field_name);
		clazz=result.getType();
	    } catch (Exception e) {
		return null;
	    } 
	}
	return result;
    }
    
    static public Object retriveObjectOfField(String field_key,Object obj)  {
	Object result=obj;
	List<String> fields=StringUtil.stringToList(field_key,"\\.");
	for (int i=0;i<fields.size()-1;i++) {
	    Field fld;
	    try {
		fld = result.getClass().getField(fields.get(i));
	    } catch (Exception e) {
		return null;
	    } 
	    result=ObjectUtil.getFieldValue(fld, result);
	}
	return result;
    }
    

}
