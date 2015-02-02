package it.micronixnetwork.application.plugin.crude.service;

import it.micronixnetwork.application.plugin.crude.annotation.Computed;
import it.micronixnetwork.application.plugin.crude.annotation.Owner;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.ContributorsRenderer;
import it.micronixnetwork.application.plugin.crude.helper.Format;
import it.micronixnetwork.gaf.domain.RoledUser;
import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.gaf.service.hibernate.HibernateSupport;
import it.micronixnetwork.gaf.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings("unchecked")
@Transactional(rollbackFor = Throwable.class)
public class ObjectCreatorServiceImpl extends HibernateSupport implements ObjectCreatorService {

    private CrudeService crudeService;

    public void setCrudeService(CrudeService crudeService) {
	this.crudeService = crudeService;
    }

    public ObjectCreatorServiceImpl() {

    }

    @Override
    public Object compose(Class target, Map<String, String> objState, ValueStack stack, boolean fillRelation) throws ServiceException {
	Object result = null;
	try {
	    if (target != null) {
		result = target.newInstance();
		Field[] fields = target.getFields();
		for (Field field : fields) {
		    String s_value = objState.get(field.getName());
		    if (field.getModifiers() == 1) {
			if (field.isAnnotationPresent(ToInput.class) || field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(Owner.class)) {
			    ContributorsRenderer contrib = field.getAnnotation(ContributorsRenderer.class);
			    if (contrib != null) {
				setFieldContributors(field, result, s_value, stack);
				break;
			    }
			    setFieldValue(field, result, s_value, stack);
			}
			if (fillRelation) {
			    if (field.isAnnotationPresent(JoinColumn.class) && field.isAnnotationPresent(OneToOne.class)) {
				JoinColumn jc = field.getAnnotation(JoinColumn.class);
				if (jc != null) {
				    String fk = objState.get(jc.name());
				    if (fk != null) {
					setFieldWidthObjFromDb(field, result, fk, jc.name(), stack);
				    }
				}
			    }
			}
		    }

		}
	    }
	    postProcess(result, stack);

	} catch (InstantiationException e) {
	    error("Compose error", e);
	} catch (IllegalAccessException e) {
	    error("Compose error", e);
	}
	return result;
    }

    private void postProcess(Object result, ValueStack stack) throws IllegalArgumentException, IllegalAccessException {
	if (result == null)
	    return;
	Class clazz = result.getClass();
	Field[] fields = clazz.getFields();
	for (Field field : fields) {
	    if (field.getModifiers() == 1) {
		Computed computed = (Computed) field.getAnnotation(Computed.class);
		if (computed != null) {
		    Object preValue = field.get(result);
		    if (preValue == null) {
			String value = computed.value();
			if (!value.equals("nill")) {
			    stack.push(result);
			    Object eval = stack.findValue(value);
			    stack.pop();
			    if (eval != null) {
				Object toSet = Format.convert(field.getType(), eval.toString(), stack);
				field.set(result, toSet);
			    }
			}
		    }

		}
	    }
	}
    }

    private void setFieldWidthObjFromDb(Field field, Object result, String fk_value, String fk_name, ValueStack stack) {
	// Recupero la classe dell'oggetto
	Class type = field.getType();

	// Cerco di convertire il valore in fromato stringa nel giusto tipo
	Object fk_real = null;
	Object toSet = null;
	try {
	    Field idField = type.getField("id");
	    fk_real = Format.convert(idField.getType(), fk_value, stack);

	    if (fk_real != null) {
		// Tento di recuperare l'oggetto dal db
		toSet = crudeService.get(type, fk_real);
		if (toSet != null) {
		    // Vado a settare il valore al campo di result
		    field.set(result, toSet);
		}
	    }

	} catch (Exception e) {
	    error("Chiave primaria non trovata", e);
	}
    }

    private void setFieldContributors(Field field, Object result, String s_value, ValueStack stack) throws ServiceException, IllegalArgumentException, IllegalAccessException {
	if (s_value == null)
	    return;
	// Controllo che il filed sia una Collection
	if (Collection.class.isAssignableFrom(field.getType())) {
	    Collection contribs = null;
	    if (field.getType() == Set.class) {
		contribs = new HashSet();
	    }
	    if (field.getType() == List.class) {
		contribs = new ArrayList();
	    }
	    if (contribs == null)
		throw new ServiceException("I can not instance contributors");

	    // Recupero oggetti da inserire
	    ParameterizedType type = (ParameterizedType) field.getGenericType();
	    Class objType = (Class) type.getActualTypeArguments()[0];

	    // Recupero tipo id
	    Field idField = null;
	    try {
		idField = objType.getField("id");
	    } catch (Exception e) {
		error("No primary key field", e);
		throw new ServiceException(e);
	    }

	    List<String> keyList = StringUtil.stringToList(s_value);

	    for (String key : keyList) {
		Object idValue = Format.convert(idField.getType(), key, stack);
		if (idValue == null) {
		    throw new ServiceException("I can not convert PK reference " + key + " to " + idField.getType());
		}
		Object cont = crudeService.get(objType, idValue);
		// Inserimento dell'oggetto nel vettore di contributors
		if (cont != null) {
		    contribs.add(cont);
		}
	    }

	    if (contribs.size() > 0) {
		field.set(result, contribs);
	    }
	}

    }

    private void setFieldValue(Field field, Object result, String s_value, ValueStack stack) throws ServiceException, IllegalArgumentException, IllegalAccessException {
	RoledUser user = (RoledUser) stack.findValue("user");
	if (user == null)
	    throw new ServiceException("User not present on system");
	Owner owner = field.getAnnotation(Owner.class);
	if (owner != null) {
	    String roles_s = owner.adminRoles();
	    String holdingRule = owner.holdingRule();
	    List roles = StringUtil.stringToList(roles_s);
	    if (!StringUtil.checkStringExistenz(user.getRoles(), roles) || s_value == null) {
		if (holdingRule.equals("nill")) {
		    s_value = user.getId();
		} else {
		    Object eval = stack.findValue(holdingRule);
		    if (eval != null) {
			s_value = eval.toString();
		    }
		}
	    }
	}

	Object toSet = Format.convert(field.getType(), s_value, stack);
	field.set(result, toSet);
    }

    @Override
    public Object createObjectId(Class clazz, Map<String, String> idsMap, ValueStack stack) throws ServiceException {
	Object result = null;
	Class targetClass = null;

	if (idsMap.isEmpty())
	    return null;

	if (clazz == null)
	    return null;
	try {
	    Field id = clazz.getField("id");
	    if (id != null) {
		targetClass = id.getType();
	    }
	} catch (Exception e) {
	}

	if (targetClass == null)
	    return null;

	if (idsMap.size() == 1) {
	    for (String kvalue : idsMap.keySet()) {
		result = Format.convert(targetClass, idsMap.get(kvalue), stack);
	    }
	}

	if (result != null)
	    return result;

	try {
	    result = targetClass.newInstance();
	} catch (Exception e) {
	    return null;
	}

	for (String fieldName : idsMap.keySet()) {
	    Field field = null;
	    try {
		field = targetClass.getField(fieldName);
	    } catch (Exception e) {
	    }
	    if (field != null) {
		try {
		    field.set(result, Format.convert(field.getType(), idsMap.get(fieldName), stack));
		} catch (Exception e) {
		}
	    }
	}
	return result;
    }

}
