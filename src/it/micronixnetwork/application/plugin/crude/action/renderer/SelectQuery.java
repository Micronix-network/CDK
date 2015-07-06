package it.micronixnetwork.application.plugin.crude.action.renderer;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import java.lang.reflect.Field;

import it.micronixnetwork.application.plugin.crude.action.CrudAction;
import it.micronixnetwork.application.plugin.crude.annotation.NULLClass;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.SelectRenderer;
import it.micronixnetwork.gaf.exception.ActionException;
import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.struts2.action.JSONAction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Command per il recupero di una commessa dal DB
 *
 * @author kobo
 *
 */
public class SelectQuery extends CrudAction implements JSONAction {

    private static final long serialVersionUID = 1L;

    private String fieldName;

    private String fieldValue;

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

    @Override
    protected String doIt() throws ApplicationException {
        JSONArray array = new JSONArray();

        if (fieldName == null) {
            throw new ActionException("Assert: No fieldName difined");
        }

        if (fieldValue == null) {
            throw new ActionException("Assert: No fieldFValue difined");
        }

        if (className == null) {
            throw new ActionException("Assert: No className difined");
        }

        if (fieldValue == null) {
            return SUCCESS;
        }

        Class target = getPrototypeClass(className);

        Field field = null;
        try {
            field = target.getField(fieldName);
        } catch (Exception e) {
            throw new ActionException("No field found: " + fieldName, e);
        }

        SelectRenderer select = (SelectRenderer) field.getAnnotation(SelectRenderer.class);

        HashMap<String, String> result = null;

        if (select != null) {

            if (!(select.mappedObject()==NULLClass.class)) {

                Class clazz = select.mappedObject();
                List qresult = listByClass(clazz, fieldName, fieldValue);
                CrudAction.MapField mapF = getMappedField(clazz);

                for (Object opt : qresult) {
                    Object key = null;
                    Object label = null;
                    Map<String, Object> attributes = null;
                    try {
                        key = mapF.keyF.get(opt);
                        label = mapF.labelF.get(opt);
                        if (mapF.attributesF.size() > 0) {
                            attributes = new HashMap<>();
                            for (Field attF : mapF.attributesF) {
                                attributes.put(attF.getName(), attF.get(opt));
                            }
                        }
                    } catch (IllegalArgumentException ex) {
                    } catch (IllegalAccessException ex) {
                    }
                    JSONObject obj = new JSONObject();
                    obj.put("label", label);
                    obj.put("value", key);
                    if (attributes != null) {
                        for (String att : attributes.keySet()) {
                            obj.put(att, attributes.get(att));
                        }
                    }
                    array.put(obj);
                }

            } else {
                if (!select.map().equals("nill")) {

                    String map = select.map();

                    if (map.startsWith("mapByQuery(")) {
                        if (!map.equals("nill")) {
                            final String marker = select.dependFrom();
                            if (!marker.equals("nill")) {
                                Object toSerachParam = compose(target, new HashMap<String, String>() {
                                    {
                                        put(marker, fieldValue);
                                    }
                                }, false);

                                ActionContext context = ActionContext.getContext();
                                ValueStack stack = context.getValueStack();
                                stack.push(toSerachParam);
                                result = (HashMap<String, String>) stack.findValue(map);
                                stack.pop();

                                if (result != null) {
                                    for (String code : result.keySet()) {
                                        JSONObject obj = new JSONObject();
                                        obj.put("label", result.get(code));
                                        obj.put("value", code);
                                        array.put(obj);
                                    }
                                }

                            }
                        }
                    }
                }
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
