package it.micronixnetwork.application.plugin.crude.action;

import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.exception.CrudException;
import it.micronixnetwork.application.plugin.crude.helper.FieldUtil;
import it.micronixnetwork.application.plugin.crude.helper.Format;
import it.micronixnetwork.application.plugin.crude.helper.QueryAutoCompose;
import it.micronixnetwork.application.plugin.crude.model.Message;
import it.micronixnetwork.application.plugin.crude.service.CrudeService;
import it.micronixnetwork.application.plugin.crude.service.DroolsService;
import it.micronixnetwork.application.plugin.crude.service.ObjectCreatorService;
import it.micronixnetwork.gaf.domain.RoledUser;
import it.micronixnetwork.gaf.exception.ActionException;
import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.gaf.struts2.action.CardAction;
import it.micronixnetwork.gaf.struts2.action.exception.UnauthorizedUser;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.drools.runtime.StatefulKnowledgeSession;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.TextProviderFactory;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ValueStack;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.MapAttribute;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.MapDependKey;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.MapKey;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.MapLabel;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.MapOrder;
import it.micronixnetwork.gaf.util.StringUtil;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class CrudAction extends CardAction {

    public final static String SEARCHUI = "search_ui";

    public final static String OP_INSERT = "insert";

    public final static String OP_UPDATE = "update";

    public final static String OP_VIEW = "view";

    private static final long serialVersionUID = 1L;

    private CrudeService crudeService;

    protected ObjectCreatorService objectCreatorService;

    protected DroolsService droolsService;

    protected ObjectFactory factory;

    protected int ptype = -1;

    protected String targetClass;

    protected String targetParent;

    protected String uiid;

    protected String operation;

    private TextProvider localProvider = null;

    public String getUiid() {
        return uiid;
    }

    public String getChildUiid() {
        return uiid + "_child";
    }

    public void setUiid(String uiid) {
        this.uiid = uiid;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setPtype(int ptype) {
        this.ptype = ptype;
    }

    public int getPtype() {
        return ptype;
    }

    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public String getTargetParent() {
        return targetParent;
    }

    public void setTargetParent(String targetParent) {
        this.targetParent = targetParent;
    }

    public void setCrudeService(CrudeService crudeService) {
        this.crudeService = crudeService;
    }

    public CrudeService getCrudeService() {
        return crudeService;
    }

    public void setObjectCreatorService(ObjectCreatorService objectCreatorService) {
        this.objectCreatorService = objectCreatorService;
    }

    public void setDroolsService(DroolsService droolsService) {
        this.droolsService = droolsService;
    }

    @Inject("struts-factory")
    public void setFactory(ObjectFactory factory) {
        this.factory = factory;
    }

    public ObjectFactory getFactory() {
        return factory;
    }

    protected Object createObjectId(Class clazz, Map<String, String> idsMap) throws ServiceException {
        Object result = null;
        ActionContext context = ActionContext.getContext();
        ValueStack stack = context.getValueStack();
        stack.push(this);
        result = objectCreatorService.createObjectId(clazz, idsMap, stack);
        stack.pop();
        return result;
    }

    protected String realDeformatter(String value) {
        ActionContext context = ActionContext.getContext();
        ValueStack stack = context.getValueStack();
        return Format.realDeformatter(value, stack);
    }

    public LinkedHashMap<String, Object> mapByQuery(String query) {
        return mapByQuery(query, null);
    }

    public LinkedHashMap<String, Object> mapByQuery(String query, HashMap param) {
        LinkedHashMap<String, Object> result = new LinkedHashMap();

        List<Object[]> qresult = null;
        try {
            qresult = queryService.search(query, param, false);
        } catch (Exception ex) {
            error("mapQuery error: ", ex);
        }

        if (qresult != null) {
            for (Object[] row : (List<Object[]>) qresult) {
                String[] more = null;
                if (row.length > 2) {
                    more = new String[row.length - 1];
                }
                if (row[0] != null) {
                    if (more == null) {
                        result.put(row[0].toString(), row[1]==null?row[0].toString():row[1].toString());
                    } else {
                        for (int i = 0; i < more.length; i++) {
                            more[i] = row[i + 1]==null?row[0].toString():row[i + 1].toString();
                        }
                        result.put(row[0].toString(), more);
                    }
                }
            }
        }
        return result;
    }
    
    public List listByClass(Class clazz,String depend,String dependValue){
        List result=new ArrayList();
        
        ActionContext context = ActionContext.getContext();
        ValueStack stack = context.getValueStack();
        
        MapField mapF=getMappedField(clazz);

        if (mapF.keyF != null) {
            
            String query = "From " + clazz.getName();
            
            HashMap<String, String> wheres = null;
            HashMap<String, Object> values = null;
            
            if(depend!=null && mapF.dependF!=null){
                wheres = new LinkedHashMap<String, String>();
                wheres.put(mapF.dependF.getName(), mapF.dependF.getName()+"=:"+mapF.dependF.getName());
                Object value=null;
                if(dependValue==null)
                    value=stack.findValue(depend);
                else
                    value=Format.convert(mapF.dependF.getType(), dependValue, stack);
                values = new LinkedHashMap<String, Object>();
                values.put(mapF.dependF.getName(), value);
                query=QueryAutoCompose.composeQueryString(query, wheres);
            }
            
            if(mapF.orderF!=null){
                MapOrder order_a = mapF.orderF.getAnnotation(MapOrder.class);
                boolean des=order_a.descendant();
                query+=" order by "+mapF.orderF.getName();
                if(des){
                    query+=" DESC";
                }else{
                    query+=" ASC";
                }
            }

            result=(List)executeHQLQuery(query,values, false);
        }
        
        return result;
    }
    
    public MapField getMappedField(Class clazz){
        MapField result=new MapField();
        Map<String, Field> fields = FieldUtil.retriveWorkFields(clazz);
        if (!fields.isEmpty()) {
            for (String fieldName : fields.keySet()) {
                Field field = fields.get(fieldName);

                MapAttribute att_a = field.getAnnotation(MapAttribute.class);
                if (att_a != null) {
                    result.attributesF.add(field);
                }

                MapKey key_a = field.getAnnotation(MapKey.class);
                if (key_a != null) {
                    result.keyF = field;
                }

                MapLabel label_a = field.getAnnotation(MapLabel.class);
                if (label_a != null) {
                    result.labelF = field;
                }

                MapOrder order_a = field.getAnnotation(MapOrder.class);
                if (order_a != null) {
                    result.orderF = field;
                }
                
                MapDependKey depend_a = field.getAnnotation(MapDependKey.class);
                if (depend_a != null) {
                    result.dependF = field;
                }
            }
        }
        
        return result;
    }

    /**
     * Conta le occorrenze di un oggetto del dominio sul DB
     *
     * @param domainObj
     * @param countConditions
     * @param vals
     * @return
     */
    public Long count(String domainObj, String countConditions, LinkedHashMap<String, Object> vals) {

        try {

            Class clazz = getPrototypeClass(domainObj);

            String hql = "select count(*) from " + clazz.getSimpleName();

            HashMap<String, String> wheres = new LinkedHashMap<String, String>();
            HashMap<String, Object> values = new LinkedHashMap<String, Object>();
            ActionContext context = ActionContext.getContext();
            ValueStack stack = context.getValueStack();

            stack.push(this);
            QueryAutoCompose.processClass(clazz, stack, wheres, values);
            stack.pop();

            if (countConditions != null) {
                wheres.put("countConditions", countConditions);
            }

            if (vals != null) {
                for (String key : vals.keySet()) {
                    values.put(key, vals.get(key));
                }
            }

            hql = QueryAutoCompose.composeQueryString(hql, wheres);

            Object result = queryService.uniqueResult(hql, values, false);

            return (Long) result;

        } catch (Exception ex) {
            error("Conut exception", ex);
            return 100l;
        }
    }

    public Object sum(String domainObj, String fieldname, String sumConditions, LinkedHashMap<String, Object> vals) {

        try {

            Class clazz = getPrototypeClass(domainObj);

            String hql = "select sum(" + fieldname + ") from " + clazz.getSimpleName();

            HashMap<String, String> wheres = new LinkedHashMap<String, String>();
            HashMap<String, Object> values = new LinkedHashMap<String, Object>();
            ActionContext context = ActionContext.getContext();
            ValueStack stack = context.getValueStack();

            stack.push(this);
            QueryAutoCompose.processClass(clazz, stack, wheres, values);
            stack.pop();

            if (sumConditions != null) {
                wheres.put("sumConditions", sumConditions);
            }

            if (vals != null) {
                for (String key : vals.keySet()) {
                    values.put(key, vals.get(key));
                }
            }

            hql = QueryAutoCompose.composeQueryString(hql, wheres);

            info(hql);

            Object result = queryService.uniqueResult(hql, values, false);

            return result;

        } catch (Exception ex) {
            error("Conut exception", ex);
            return 100d;
        }
    }

    protected Object compose(Class target, Map<String, String> objState, boolean fillRelation) throws ActionException, ServiceException {
        Object result = null;
        ActionContext context = ActionContext.getContext();
        ValueStack stack = context.getValueStack();
        stack.push(this);
        result = objectCreatorService.compose(target, objState, stack, fillRelation);
        stack.pop();
        return result;
    }

    protected Class getPrototypeClass(String prototypeName) throws ApplicationException {
        if (factory == null) {
            throw new CrudException("The class factory is null");
        }
        try {
            Class target = factory.getClassInstance(prototypeName);
            return target;
        } catch (Throwable e) {
            throw new CrudException("TragetClass is void or not assignable", e);
        }
    }

    protected Object ognlEvaluation(String rule, Object model) {
        Object result = null;
        ActionContext context = ActionContext.getContext();
        ValueStack stack = context.getValueStack();
        stack.push(model);
        result = stack.findValue(rule);
        stack.pop();
        return result;
    }

    protected void fireRules(String fileName, Object fact, Message msg) throws ServiceException {
        List<String> rules = new ArrayList<String>();

        String rule = ServletActionContext.getServletContext().getRealPath("/WEB-INF/rules/" + fileName + ".drl");

        rules.add(rule);

        StatefulKnowledgeSession session = droolsService.createSession(rules);

        if (session != null) {

            if (queryService != null) {
                debug("Setting : queryService");
                session.setGlobal("queryService", queryService);
            }

            if (crudeService != null) {
                debug("Setting : crudeService");
                session.setGlobal("crudeService", crudeService);
            }

            List<Object> facts = new ArrayList<Object>();

            facts.add(fact);
            facts.add(msg);

            droolsService.addFactsToSession(session, facts);

            droolsService.fireRules(session);
        }
    }

    protected Map<String, String> childFields(Class targetClass) {
        Map<String, String> result = new LinkedHashMap<String, String>();
        Map<String, Field> childrenField = FieldUtil.retriveChildrenField(targetClass);
        for (String fieldKey : childrenField.keySet()) {
            Field field = childrenField.get(fieldKey);
            ToInput input = field.getAnnotation(ToInput.class);
            ToView view = field.getAnnotation(ToView.class);
            if (input != null || view != null) {
                RoledUser user = getUser();
                String[] userRoles = {};
                if (user != null) {
                    userRoles = user.getRoles();
                }

                boolean found = false;

                if (input != null && operation.equals(OP_UPDATE)) {
                    String roles_s = input.roles();
                    found = checkRole(userRoles, roles_s);
                }

                if (view != null && operation.equals(OP_VIEW)) {
                    String roles_s = view.roles();
                    found = checkRole(userRoles, roles_s);
                }

                if (found) {
                    ParameterizedType type = (ParameterizedType) field.getGenericType();
                    Class stringListClass = (Class) type.getActualTypeArguments()[0];
                    result.put(fieldKey, stringListClass.getName());
                }
            }
        }
        return result;
    }

    public static String getSearchui() {
        return SEARCHUI;
    }

    public String getInsertRoles() {
        return getCardParam("rolesCreate");
    }

    public String getModifyRoles() {
        return getCardParam("rolesMody");
    }

    public String getDeleteRoles() {
        return getCardParam("rolesRemove");
    }

    protected void setLocalProvider(String className) {

        if (className != null) {
            final Locale locale = (Locale) ActionContext.getContext().getLocale();
            ResourceBundle bundle = null;
            try {
                bundle = ResourceBundle.getBundle(className.toLowerCase(), locale);
            } catch (Exception ex) {
                warn(ex.getMessage());
            }

            if (bundle != null) {
                TextProviderFactory tpf = new TextProviderFactory();
                localProvider = tpf.createInstance(bundle, new LocaleProvider() {
                    public Locale getLocale() {
                        return locale;
                    }
                });
            }
        }

    }

    @Override
    protected final String exe() throws ApplicationException {

        String className = getCardParam("message_bundle", false);

        if (StringUtil.EmptyOrNull(className)) {
            if (targetClass == null) {
                className = getCardParam("prototype", false);
            } else {
                className = targetClass;
            }
        }

        setLocalProvider(className);

        if (checkRole()) {
            return doIt();
        } else {
            throw new UnauthorizedUser("User without right role");
        }
    }
    
    

    public String getText(String aTextName) {
        if (localProvider != null) {
            if (localProvider.hasKey(aTextName)) {
                return localProvider.getText(aTextName);
            };
        }
        return super.getText(aTextName);
    }

    public String getText(String aTextName, String defaultValue) {
        if (localProvider != null) {
            if (localProvider.hasKey(aTextName)) {
                return localProvider.getText(aTextName, defaultValue);
            }
        }
        return super.getText(aTextName, defaultValue);
    }

    public String getText(String aTextName, String defaultValue, String obj) {
        if (localProvider != null) {
            if (localProvider.hasKey(aTextName)) {
                return localProvider.getText(aTextName, defaultValue, obj);
            }
        }
        return super.getText(aTextName, defaultValue, obj);
    }

    public String getText(String aTextName, List<Object> args) {
        String result;
        if (localProvider != null) {
            if (localProvider.hasKey(aTextName)) {
                return localProvider.getText(aTextName, args);
            }
        }
        return super.getText(aTextName, args);
    }

    public String getText(String key, String[] args) {
        if (localProvider != null) {
            if (localProvider.hasKey(key)) {
                return localProvider.getText(key, args);
            }
        }
        return super.getText(key, args);
    }

    public String getText(String aTextName, String defaultValue, List<Object> args) {
        if (localProvider != null) {
            if (localProvider.hasKey(aTextName)) {
                return localProvider.getText(aTextName, defaultValue, args);
            }
        }
        return super.getText(aTextName, defaultValue, args);
    }

    public String getText(String key, String defaultValue, String[] args) {
        if (localProvider != null) {
            if (localProvider.hasKey(key)) {
                return localProvider.getText(key, defaultValue, args);
            }
        }
        return super.getText(key, defaultValue, args);
    }

    public String getText(String key, String defaultValue, List<Object> args, ValueStack stack) {
        if (localProvider != null) {
            if (localProvider.hasKey(key)) {
                return localProvider.getText(key, defaultValue, args, stack);
            }
        }
        return super.getText(key, defaultValue, args, stack);
    }

    public String getText(String key, String defaultValue, String[] args, ValueStack stack) {
        if (localProvider != null) {
            if (localProvider.hasKey(key)) {
                return localProvider.getText(key, defaultValue, args, stack);
            }
        }
        return super.getText(key, defaultValue, args, stack);
    }

    abstract protected boolean checkRole();

    abstract protected String doIt() throws ApplicationException;
    
    
    public static class MapField{
        public Field keyF = null;
        public Field labelF = null;
        public Field orderF = null;
        public Field dependF = null;
        public List<Field> attributesF = new ArrayList<>();
    }

}
