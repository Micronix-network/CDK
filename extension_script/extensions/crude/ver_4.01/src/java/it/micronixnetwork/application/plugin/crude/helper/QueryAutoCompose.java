package it.micronixnetwork.application.plugin.crude.helper;

import it.micronixnetwork.application.plugin.crude.annotation.Owner;
import it.micronixnetwork.gaf.domain.RoledUser;
import it.micronixnetwork.gaf.service.SearchEngineException;
import it.micronixnetwork.gaf.util.StringUtil;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;

import com.opensymphony.xwork2.util.ValueStack;

public class QueryAutoCompose {
    
    
    private static void analyzeField(ValueStack stack,HashMap<String, String> wheres,HashMap<String, Object> values, String fieldName, Field field) {
	if (field.isAnnotationPresent(Owner.class)) {
	    Owner owner = field.getAnnotation(Owner.class);
	    String holdingRule=owner.holdingRule();
	    List roles = StringUtil.stringToList(owner.adminRoles());
	    RoledUser user=(RoledUser)stack.findValue("user");
	    if(user!=null){
    	    if (!StringUtil.checkStringExistenz(user.getRoles(), roles) || roles.size()==0) {
    		wheres.put(fieldName, fieldName + "=:"+fieldName);
    		if(holdingRule.equals("nill")){
    		    values.put(fieldName, user.getId());
    		}else{
    		    values.put(fieldName, stack.findValue(holdingRule));
    		}
    	    }
	    }
	}
    }
    
    public static void processClass(Class target,ValueStack stack,HashMap<String, String> wheres,HashMap<String, Object> values){
	Map<String, Field> fields = FieldUtil.retriveWorkFields(target);
	for (String field_key : fields.keySet()) {
	    Field field = fields.get(field_key);
	    analyzeField(stack,wheres, values, field_key, field);
	}
    };
    
    
    public static String composeQueryString(String query,HashMap<String, String> wheres) {
	String queryStr = query;
	if (queryStr == null) {
	    return null;
	}
	String where = createWhere(wheres);
	if (where != null && where.trim().length() > 0) {
	    queryStr += " where " + where;
	}
	return queryStr;
    }
    
    private static String createWhere(HashMap<String, String> wheres) {
	String result = "";
	Set<String> keys = wheres.keySet();
	int i = 0;
	for (String key : keys) {
	    if (i > 0) {
		result += " and";
	    }
	    result += " " + wheres.get(key);
	    i++;
	}
	return result;
    }
   
}
