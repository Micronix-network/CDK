package it.micronixnetwork.application.plugin.crude.service;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import it.micronixnetwork.application.plugin.crude.annotation.Children;
import it.micronixnetwork.application.plugin.crude.annotation.GlobalFilter;
import it.micronixnetwork.application.plugin.crude.annotation.Owner;
import it.micronixnetwork.application.plugin.crude.annotation.ToList;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.gui.component.renderer.element.Text;
import it.micronixnetwork.application.plugin.crude.helper.FieldUtil;
import it.micronixnetwork.application.plugin.crude.helper.Format;
import it.micronixnetwork.application.plugin.crude.model.Traceable;
import it.micronixnetwork.gaf.domain.RoledUser;
import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.gaf.service.SearchResult;
import it.micronixnetwork.gaf.service.hibernate.HibernateSmartySearchEngine;
import it.micronixnetwork.gaf.service.hibernate.HibernateSupport;
import it.micronixnetwork.gaf.util.MD5;
import it.micronixnetwork.gaf.util.ObjectUtil;
import it.micronixnetwork.gaf.util.StringUtil;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.JoinTable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("unchecked")
@Transactional(rollbackFor = Throwable.class)
public class CrudeServiceImpl extends HibernateSupport implements CrudeService {

    private static final Set<Class> SCALAR = new HashSet<Class>(Arrays.asList(String.class, Boolean.class, Byte.class, Character.class, Short.class, Integer.class, Float.class, Long.class,
            Double.class, BigInteger.class, BigDecimal.class, AtomicBoolean.class, AtomicInteger.class, AtomicLong.class, Date.class, Calendar.class, GregorianCalendar.class, Class.class, UUID.class,
            Number.class, Object.class, Timestamp.class));

    private static final Set<Class> NO_LIKE = new HashSet<Class>(Arrays.asList(Boolean.class, Byte.class, Character.class, Short.class, Integer.class, Float.class, Long.class, Double.class,
            BigInteger.class, BigDecimal.class, AtomicBoolean.class, AtomicInteger.class, AtomicLong.class, Date.class, Calendar.class, GregorianCalendar.class, Number.class, Timestamp.class,
            Time.class));

    @Override
    public Object get(Class cls, Object id) throws ServiceException {
        Session session = getCurrentSession();
        // Assert
        if (id == null) {
            throw new ServiceException("Parametro null");
        }
        return session.get(cls, (Serializable) id);
    }

    @Override
    public Object create(RoledUser user, Object obj) throws ServiceException {
        Session session = getCurrentSession();
        // Assert
        if (obj == null) {
            throw new ServiceException("Parametro null");
        }

        if (obj instanceof Traceable) {
            ((Traceable) obj).creationDate = new Date();
            ((Traceable) obj).userId = user.getId();
            ((Traceable) obj).valenza = 0;
            ((Traceable) obj).version = 1;
        }

        return session.save(obj);
    }

    @Override
    public Object update(RoledUser user, Class cls, Object obj) throws ServiceException {
        Session session = getCurrentSession();

        // Assert
        if (obj == null) {
            throw new ServiceException("Parametro null");
        }

        // Recupero oggetto da aggiornare
        Object id = ObjectUtil.getFieldValue("id", obj);

        Object e = (Object) session.get(cls, (Serializable) id);

        if (e == null) {
            throw new ServiceException("L'oggetto di tipo: " + cls.getCanonicalName() + "con ID: ("+id+") non esiste");
        }

        if (e instanceof Traceable) {
            ((Traceable) e).updateDate = new Date();
            ((Traceable) e).valenza = 1;
            session.update(e);

            Object toInsert = null;
            debugXML(e, 5);
            toInsert=Clone(e, true);
            debugXML(toInsert, 5);
            fillObject(obj, toInsert);
            debugXML(toInsert, 5);
            ((Traceable) toInsert).updateDate = null;
            ((Traceable) toInsert).valenza = 0;
            ((Traceable) toInsert).creationDate = new Date();
            ((Traceable) toInsert).userId = user.getId();
            if (((Traceable) e).version == null) {
                ((Traceable) toInsert).version = 1;
            } else {
                ((Traceable) toInsert).version = ((Traceable) e).version + 1;
            }
            return session.save(toInsert);
        } else {
            // modifica valori oggetto marcati in input (Tranne l'id)
            fillObject(obj, e);

            // Aggiornamento dati
            session.update(e);
            return id;
        }

    }

    @Override
    public void remove(RoledUser user, Class cls, List ids) throws ServiceException {

        // Assert
        if (ids == null) {
            throw new ServiceException("Parametro null");
        }

        // The list is void nothing to do
        if (ids.size() == 0) {
            return;
        }

        Session session = getCurrentSession();

        for (Object id : ids) {
            // Retriving object to delete
            Object e = session.get(cls, (Serializable) id);

            // Object removing
            if (e != null) {

                if (e instanceof Traceable) {

                    ((Traceable) e).updateDate = new Date();
                    ((Traceable) e).valenza = 1;
                    session.update(e);
                    // modifica valori oggetto marcati in input e annulla l'id
                    Object obj = Clone(e,false);
                    if (obj != null) {
                        ((Traceable) obj).updateDate = null;
                        ((Traceable) obj).valenza = 2;
                        ((Traceable) obj).creationDate = new Date();
                        ((Traceable) obj).userId = user.getId();
                        if (((Traceable) e).version == null) {
                            ((Traceable) obj).version = 1;
                        } else {
                            ((Traceable) obj).version = ((Traceable) e).version + 1;
                        }

                        session.save(obj);
                    } else {
                        throw new ServiceException("Object " + e.getClass().getName() + " not cloneable");
                    }

                } else {
                    // Aggiornamento dati
                    session.delete(e);
                }

            }
        }

    }

    @Override
    public SearchResult find(RoledUser user, final Class clazz, final Map<String, String> filters, final String toOrder, Integer page, Integer size, Integer limit)
            throws ServiceException {
        String tbAlias = clazz.getSimpleName().toLowerCase().substring(0, 3);

        List<String> orderBy = new ArrayList<String>();
        List<String> input = StringUtil.stringToList(toOrder);
        for (String field : input) {
            orderBy.add(tbAlias + "." + field);
        }
        String query = "select distinct " + tbAlias + " From " + clazz.getSimpleName() + " " + tbAlias;

        //Costruzione filtri 
        HashMap<String, String> wheres = new LinkedHashMap<String, String>();
        HashMap<String, Object> values = new LinkedHashMap<String, Object>();

        Map<String, Field> fields = FieldUtil.retriveWorkFields(clazz);

        for (String fieldName : fields.keySet()) {
            Field field = fields.get(fieldName);
            Class fieldType = field.getType();
            Class collectionType = null;

            if (field.isAnnotationPresent(ToList.class)) {
                ToList toList = field.getAnnotation(ToList.class);
                if (toList.filtered() && toList.fixValue().equals("nill")) {
                    if (Collection.class.isAssignableFrom(fieldType)) {
                        ParameterizedType type = (ParameterizedType) field.getGenericType();
                        collectionType = (Class) type.getActualTypeArguments()[0];
                    }

                    if (field.isAnnotationPresent(Transient.class)) {
                        String rule = toList.filterRule();
                        if (!rule.equals("nill")) {
                            if (rule.matches("([^\\.]*)|([^\\.]*\\.[^\\.]*)|([^\\.]*\\.[^\\.]*\\.[^\\.]*)|([^\\.]*\\.[^\\.]*\\.[^\\.]*\\.[^\\.]*)")) {
                                fieldName = rule;
                            }
                        }
                    }
                    if (collectionType != null) {
                        query += " left join " + tbAlias + "." + fieldName + " " + collectionType.getSimpleName().toLowerCase().substring(0, 3);
                        fieldName = fieldName + ".id";
                    }
                    ActionContext context = ActionContext.getContext();
                    ValueStack stack = context.getValueStack();

                    String s_value = filters.get(fieldName);
                    if (!StringUtil.EmptyOrNull(s_value)) {
                        //Check Collection field type
                        if (collectionType != null) {
                            try {
                                fieldType = collectionType.getField("id").getType();
                            } catch (Exception e) {
                            }
                        }
                        Object value = Format.convert(fieldType, s_value, stack);
                        if (value != null) {
                            String dbField = tbAlias + "." + fieldName;
                            if (collectionType != null) {
                                dbField = collectionType.getSimpleName().toLowerCase().substring(0, 3) + ".id";
                            }
                            if (NO_LIKE.contains(value.getClass())) {
                                if (value instanceof Date) {
                                    Temporal tempo = field.getAnnotation(Temporal.class);
                                    if (tempo != null) {
                                        if (tempo.value() == TemporalType.DATE) {
                                            dbField = "date(" + dbField + ")";
                                        }
                                        if (tempo.value() == TemporalType.TIME) {
                                            dbField = "time(" + dbField + ")";
                                        }
                                    }
                                }
                                wheres.put(field.getName(), dbField + "=:0");
                                values.put(field.getName(), value);
                            } else {
                                wheres.put(field.getName(), dbField + " like :0");
                                values.put(field.getName(), value);
                            }

                        }
                    }
                }

                if (!toList.fixValue().equals("nill")) {
                    ActionContext context = ActionContext.getContext();
                    ValueStack stack = context.getValueStack();
                    if (!StringUtil.EmptyOrNull(toList.fixValue())) {
                        Object value = Format.convert(fieldType, toList.fixValue(), stack);
                        if (value != null) {
                            String dbField = tbAlias + "." + fieldName;
                            wheres.put(field.getName(), dbField + "=:0");
                            values.put(field.getName(), value);
                        }
                    }
                }

                if (toList.defaultOrdered()) {
                    String toOrd = tbAlias + "." + fieldName;
                    String direc = "asc";
                    if (toList.descendant()) {
                        direc = "desc";
                    }
                    if (!(orderBy.contains(toOrd + " desc") || orderBy.contains(toOrd + " asc") || orderBy.contains(toOrd + " none"))) {
                        orderBy.add(toOrd + " " + direc);
                    }
                }

            }

            if (field.isAnnotationPresent(Owner.class)) {
                Owner owner = field.getAnnotation(Owner.class);
                String holdingRule = owner.holdingRule();
                List roles = StringUtil.stringToList(owner.adminRoles());
                if (!StringUtil.checkStringExistenz(user.getRoles(), roles) || roles.size() == 0) {
                    wheres.put(fieldName, fieldName + "=:0");
                    if (holdingRule.equals("nill")) {
                        values.put(fieldName, user.getId());
                    } else {
                        ActionContext context = ActionContext.getContext();
                        ValueStack stack = context.getValueStack();
                        stack.push(this);
                        values.put(fieldName, stack.findValue(holdingRule));
                        stack.pop();
                    }
                }
            }
        }

        GlobalFilter gf = (GlobalFilter) clazz.getAnnotation(GlobalFilter.class);

        if (gf != null) {
            String filter = gf.filter();
            if (!filter.equals("nill")) {
                wheres.put("_gaf_filter", filter);
            }
        }
        
        if(Traceable.class.isAssignableFrom(clazz)){
            String dbField = tbAlias + ".valenza";
            wheres.put("valenza", dbField + "=:0");
            values.put("valenza", 0);
        }

        return produceResult(query, wheres, values, orderBy, page, size, limit);
    }

    private SearchResult produceResult(final String query, final HashMap<String, String> wheres, final HashMap<String, Object> values, final List<String> orderBy, Integer page, Integer size,
            final Integer limit) throws ServiceException {
        Session session = getCurrentSession();

        // Creazione del SearchEngine specifico per la ricerca
        HibernateSmartySearchEngine searchEngine = new HibernateSmartySearchEngine(session) {
            {
                // Settaggio valori e pattern

                if (limit != null) {
                    setLimit(limit);
                }

                // setSql(true);
                for (String key : values.keySet()) {
                    Object value = values.get(key);
                    if (value != null) {
                        if (value instanceof String) {
                            String where = wheres.get(key);
                            if (where != null && where.indexOf("like") != -1) {
                                addFieldPattern(key, "%#u%");
                            }
                        }
                        setValue(key, value);
                    }
                }
            }

            @Override
            protected void build() {
                setSelectString(query);

                // Condizioni di where
                for (String key : wheres.keySet()) {
                    String condition = wheres.get(key);
                    if (condition != null) {
                        if (key.equals("_gaf_filter")) {
                            addWhereClausole(condition);
                        } else {
                            addWhereClausole(key, condition);
                        }
                    }
                }

                // Settaggio order by
                for (String orderby : orderBy) {
                    addFieldAlias(orderby, orderby);
                    addOrderField(orderby);
                }
            }
        };

        return searchEngine.produceResult(page, size);
    }

    private void getAllFields(Class bean, ArrayList<Field> fields) {
        if (bean != null && !SCALAR.contains(bean)) {
            Field[] partial = bean.getDeclaredFields();
            fields.addAll(Arrays.asList(partial));
            Class superC = bean.getSuperclass();
            getAllFields(superC, fields);
        }
    }

    private void fillObject(Object src, Object tgt) {
        ArrayList<Field> fields = new ArrayList<Field>();
        getAllFields(src.getClass(), fields);
        if (fields.size() > 0) {
            for (Field field : fields) {
                if (!field.getName().equals("id")) {
                    Object value = null;
                    field.setAccessible(true);
                    try {
                        value = field.get(src);
                    } catch (IllegalArgumentException e) {
                    } catch (IllegalAccessException e) {
                    }
                    ToInput input = field.getAnnotation(ToInput.class);
                    if (input != null) {
                        boolean enc = input.encoded();
                        try {
                            if (value != null && value instanceof String) {
                                //Controllo se siamo difronte ad un campo precedentmente criptato
                                if (!Text.UBIMAJOR.equals(value)) {
                                    if (enc) {
                                        MD5 md5 = new MD5();
                                        try {
                                            md5.Update((String) value, null);
                                            String hash = md5.asHex();
                                            value = hash;
                                        } catch (UnsupportedEncodingException e) {
                                        }
                                    }
                                }
                            }
                           
                            if (!Text.UBIMAJOR.equals(value) && !(value==null && Set.class.isAssignableFrom(field.getType()))) {
                                field.set(tgt, value);
                            }
                            
                        } catch (IllegalArgumentException e) {
                        } catch (IllegalAccessException e) {
                        }
                    }
                }
            }
        }
    }

    private Object Clone(Object src,boolean withChild) {
        
        if (src == null) {
            return null;
        }
        
        Object result = null;
        try {
            result = src.getClass().newInstance();
        } catch (Exception ex) {
            debug(src.getClass().getName() + " instance problem", ex);
            return null;
        }
        
        ArrayList<Field> fields = new ArrayList<Field>();
        getAllFields(src.getClass(), fields);
        if (fields.size() > 0) {
            for (Field field : fields) {
                if (!field.getName().equals("id")) {
                    Object value = null;
                    field.setAccessible(true);
                    try {
                        value = field.get(src);
                    } catch (Exception e) {
                        debug(field.getName() + " get value problem", e);
                    }
                    if (value != null) {
                        try {
                            Object toSet = null;
                            if (value instanceof Set) {  
                                if (field.isAnnotationPresent(JoinTable.class) || !withChild) {
                                   toSet = null; 
                                } else {
                                    toSet = new HashSet<Object>();
                                    for (Object obj : ((Set) value)) {
                                        Object o = Clone(obj,withChild);
                                        if (o != null) {
                                            ((HashSet) toSet).add(o);
                                        }
                                    }
                                }
                            } else {
                                toSet = value;
                            }
                            if(toSet!=null) field.set(result, toSet);
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
        return result;
    }

    private void setParameter(Query query, Object value, String key) {
        String[] params_name = query.getNamedParameters();

        if (params_name == null) {
            return;
        }

        if (!Arrays.asList(params_name).contains(key)) {
            return;
        }

        if (value == null) {
            return;
        }

        if (value instanceof String) {
            if (((String) value).trim().equals("")) {
                return;
            }
        }
        if (value instanceof Character) {
            if (value.toString().trim().equals("")) {
                return;
            }
        }
        if (value instanceof String) {
            query.setString(key, (String) value);
        }
        if (value instanceof Date) {
            query.setDate(key, (Date) value);
        }
        if (value instanceof Integer) {
            query.setInteger(key, (Integer) value);
        }
        if (value instanceof Character) {
            query.setCharacter(key, (Character) value);
        }
        if (value instanceof Double) {
            query.setDouble(key, (Double) value);
        }
        if (value instanceof Float) {
            query.setFloat(key, (Float) value);
        }
        if (value instanceof Long) {
            query.setLong(key, (Long) value);
        }
        if (value instanceof Boolean) {
            query.setBoolean(key, (Boolean) value);
        }
        if (value instanceof Short) {
            query.setShort(key, (Short) value);
        }
        if (value instanceof Collection) {
            query.setParameterList(key, (Collection) value);
        }
        if (value instanceof BigDecimal) {
            query.setBigDecimal(key, (BigDecimal) value);
        }
        if (value instanceof Number) {
            query.setParameter(key, value);
        }
    }

    @Override
    public void addChildren(RoledUser user, Class parent, Object parentId, String fieldName, Object child) throws ServiceException {
        Class target = child.getClass();
        Object parentObj = (Object) getCurrentSession().get(parent, (Serializable) parentId);
        try {
            Field[] fields = parent.getFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Children.class)) {
                    ParameterizedType type = (ParameterizedType) field.getGenericType();
                    Class listClass = (Class) type.getActualTypeArguments()[0];
                    if (listClass.equals(target) && fieldName.equals(field.getName())) {
                        Object fvalue;
                        fvalue = field.get(parentObj);
                        ((Collection) fvalue).add(child);
                        debugXML(parentObj, 5);
                    }
                }
            }
            // Salvataggio
            getCurrentSession().update(parentObj);
        } catch (IllegalArgumentException e) {
            error("", e);
        } catch (IllegalAccessException e) {
            error("", e);
        }
    }

}
