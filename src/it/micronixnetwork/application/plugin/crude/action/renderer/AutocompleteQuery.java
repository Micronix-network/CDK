package it.micronixnetwork.application.plugin.crude.action.renderer;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import it.micronixnetwork.application.plugin.crude.action.CrudAction;
import it.micronixnetwork.application.plugin.crude.annotation.AsincInfo;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.AutocompleteRenderer;
import it.micronixnetwork.application.plugin.crude.helper.Format;
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
public class AutocompleteQuery extends CrudAction implements JSONAction {

    private static final long serialVersionUID = 1L;

    private String fieldName;

    private String fieldValue;

    private String dependValue;

    private String className;

    private String jsonOutData;

    public String getJsonOutData() {
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

    public void setDependValue(String dependValue) {
        this.dependValue = dependValue;
    }

    @Override
    protected String doIt() throws ApplicationException {
        JSONArray array = new JSONArray();
        
        ActionContext context = ActionContext.getContext();
        ValueStack stack = context.getValueStack();

        if (fieldName == null) {
            throw new ActionException("Assert: No fieldName difined");
        }

        if (fieldValue == null) {
            throw new ActionException("Assert: No fieldFValue difined");
        }

        if (className == null) {
            throw new ActionException("Assert: No className difined");
        }

        if (fieldValue == null || fieldValue.length() == 0) {
            return SUCCESS;
        }

        Class target = getPrototypeClass(className);

        Field field = null;
        try {
            field = target.getField(fieldName);
        } catch (Exception e) {
            throw new ActionException("No field found: " + fieldName, e);
        }

        AutocompleteRenderer autoc = (AutocompleteRenderer) field.getAnnotation(AutocompleteRenderer.class);

        if (autoc != null && !autoc.jsonQuery().equals("nill")) {

            //Controllo dipendenze
            String dep = autoc.dependFrom();
            Field depField = null;
            if (!dep.equals("nill")) {
                try {
                    depField = target.getField(dep);
                } catch (Exception e) {
                    throw new ActionException("No field found: " + dep, e);
                }
            }
            
            //Recupero tipo dipendenza

            HashMap<String, Object> params = new HashMap<String, Object>();

            String query = autoc.jsonQuery();

            params.put("fieldValue", fieldValue + "%");
            
            if(depField!=null){
                params.put(dep, Format.convert(depField.getType(), dependValue, stack));
            }

            List<Object[]> qresult = (List<Object[]>) queryService.search(query, params, false);

            boolean append=autoc.append();
            
            for (int i = 0; i < qresult.size(); i++) {
                Object[] row = qresult.get(i);
                JSONObject obj = new JSONObject();
                if(append){
                    obj.put("label", row[0]+" - "+row[1]);
                }else{
                    obj.put("label", row[1]);
                }
                obj.put("value", row[0]);
                array.put(obj);
            }
        }
        
        jsonOutData = array.toString();

        return SUCCESS;
    }

    @Override
    protected boolean checkRole() {
        return true;
    }
}
