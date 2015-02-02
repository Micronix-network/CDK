package it.micronixnetwork.application.plugin.crude.action;

import it.micronixnetwork.application.plugin.crude.annotation.Owner;
import it.micronixnetwork.application.plugin.crude.annotation.ValidField;
import it.micronixnetwork.application.plugin.crude.model.Message;
import it.micronixnetwork.gaf.exception.ActionException;
import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.util.ExprValidator;
import it.micronixnetwork.gaf.util.REXTokenizer;
import it.micronixnetwork.gaf.util.StringUtil;

import java.awt.Dimension;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.dao.DataIntegrityViolationException;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.TextProviderFactory;

public abstract class EditAction extends CrudAction {

    private Map<String, String> objState = new HashMap<String, String>();

    public Map<String, String> getObjState() {
	return objState;
    }

    public void setObjState(Map<String, String> objState) {
	this.objState = objState;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void validInput() {

	Class target = null;

	try {
	    target = getPrototypeClass(targetClass);
	} catch (Exception e) {
	    addFieldError("generic", getText("crude.validation.error.generic"));
	    e.printStackTrace();
	    return;
	}

	ResourceBundle bundle = getTexts(target.getName());

	TextProvider localProvider = null;

	if (bundle != null) {
	    final Locale locale = (Locale) ActionContext.getContext().getLocale();
	    TextProviderFactory tpf = new TextProviderFactory();
	    localProvider = tpf.createInstance(bundle, new LocaleProvider() {
		public Locale getLocale() {
		    return locale;
		}
	    });
	}
	
	String ruleFile = target.getSimpleName() + this.getClass().getSimpleName();
	Message msg = new Message();

	// DroolsFormMap input=new DroolsFormMap(getObjState());
	try {
	    fireRules(ruleFile, getObjState(), msg);
	} catch (Exception ex) {
	    debug("Esecuzione regole non eseguita", ex);
	}
	if (msg.getStatus() >= 0) {
	    if (checkVoid(target, localProvider))
		    checkValid(target, localProvider);
	} else {
	    addFieldError("generic", msg.getMessage());
	}

	

    }
    

    private void checkValid(Class target, TextProvider localProvider) {
	if (target != null) {
	    Field[] fields = target.getFields();
	    for (Field field : fields) {
		if (field.getModifiers() == 1) {
		    Owner owner = field.getAnnotation(Owner.class);
		    if (owner != null) {
			String roles_s = owner.adminRoles();
			String holdingRule = owner.holdingRule();
			List roles = StringUtil.stringToList(roles_s);
			if (!checkRole(getUser().getRoles(), roles)) {
			    return;
			}
		    }
		    ValidField validF = field.getAnnotation(ValidField.class);
		    if (validF != null) {
			if (!validField(target, localProvider, validF, field)) {
			    return;
			}
		    }
		}
	    }
	}
    }

    private boolean validField(Class target, TextProvider localProvider, ValidField validF, Field idfield) {
	String type = validF.type();
	String rule = validF.rule();

	String filedLabel = null;
	if (localProvider != null) {
	    filedLabel = localProvider.getText(target.getSimpleName() + "." + idfield.getName(), idfield.getName());
	} else {
	    filedLabel = idfield.getName();
	}

	String toValid = objState.get(idfield.getName());

	if (ValidField.EMAIL_VALIDATION.equals(type)) {
	    if (!ExprValidator.validEmail(toValid)) {
		addFieldError(idfield.getName(), getText("crude.validation.error.email", new String[] { filedLabel }));
		return false;
	    }
	}

	if (ValidField.INT_VALIDATION.equals(type)) {
	    if (!ExprValidator.validInteger(toValid)) {
		addFieldError(idfield.getName(), getText("crude.validation.error.int", new String[] { filedLabel }));
		return false;
	    }
	}

	if (ValidField.FLOAT_VALIDATION.equals(type)) {

	    toValid = realDeformatter(toValid);

	    if (!ExprValidator.validFloat(toValid)) {
		addFieldError(idfield.getName(), getText("crude.validation.error.real", new String[] { filedLabel }));
		return false;
	    }
	}

	if (ValidField.DOUBLE_VALIDATION.equals(type)) {

	    toValid = realDeformatter(toValid);

	    if (!ExprValidator.validDouble(toValid)) {
		addFieldError(idfield.getName(), getText("crude.validation.error.real", new String[] { filedLabel }));
		return false;
	    }
	}

	if (ValidField.LONG_VALIDATION.equals(type)) {
	    if (!ExprValidator.validLong(toValid)) {
		addFieldError(idfield.getName(), getText("crude.validation.error.int", new String[] { filedLabel }));
		return false;
	    }
	}

	if (ValidField.CF_VALIDATION.equals(type)) {
	    if (!ExprValidator.validCodFiscale(toValid)) {
		addFieldError(idfield.getName(), getText("crude.validation.error.cf", new String[] { filedLabel }));
		return false;
	    }
	}

	if (ValidField.PIVA_VALIDATION.equals(type)) {
	    if (!ExprValidator.validPartitaIva(toValid)) {
		addFieldError(idfield.getName(), getText("crude.validation.error.piva", new String[] { filedLabel }));
		return false;
	    }
	}

	if (ValidField.TIME_VALIDATION.equals(type)) {
	    String pattern = getText("time.format", "HH:mm");
	    if (!ExprValidator.validTime(toValid, pattern)) {
		addFieldError(idfield.getName(), getText("crude.validation.error.time", new String[] { filedLabel }));
		return false;
	    }
	}

	if (ValidField.DATE_VALIDATION.equals(type)) {
	    String patterm = getText("date.format", "dd/MM/yyyy");
	    if (!ExprValidator.validDate(toValid, patterm)) {
		addFieldError(idfield.getName(), getText("crude.validation.error.date", new String[] { filedLabel }));
		return false;
	    }
	}

	int maxSize = validF.maxsize();

	if (toValid != null) {
	    if (toValid.length() > maxSize) {
		addFieldError(idfield.getName(), getText("crude.validation.error.maxsize", new String[] { filedLabel, String.valueOf(maxSize) }));
		return false;
	    }
	}

	int minSize = validF.minsize();

	if (minSize > 0 && (toValid == null || toValid.length() < minSize)) {
	    addFieldError(idfield.getName(), getText("crude.validation.error.minsize", new String[] { filedLabel, String.valueOf(minSize) }));
	    return false;
	}

	int size = validF.size();

	if (size > 0 && (toValid == null || toValid.length() != size)) {
	    addFieldError(idfield.getName(), getText("crude.validation.error.size", new String[] { filedLabel, String.valueOf(size) }));
	    return false;
	}

	return true;
    }

    protected boolean rulesControl(Class target, Object toCheck) {
	if (target != null) {

	    ResourceBundle bundle = getTexts(target.getName());

	    TextProvider localProvider = null;

	    if (bundle != null) {
		final Locale locale = (Locale) ActionContext.getContext().getLocale();
		TextProviderFactory tpf = new TextProviderFactory();
		localProvider = tpf.createInstance(bundle, new LocaleProvider() {
		    public Locale getLocale() {
			return locale;
		    }
		});
	    }

	    Field[] fields = target.getFields();
	    for (Field field : fields) {
		ValidField validF = field.getAnnotation(ValidField.class);
		if (validF != null) {
		    String rule = validF.rule();
		    if (validF != null && !rule.equals("")) {
			if (!evalueRule(target, rule, field, toCheck, localProvider)) {
			    return false;
			}
		    }
		}
	    }
	}
	return true;
    }

    private boolean evalueRule(Class target, String rule, Field field, Object input, TextProvider localProvider) {

	String filedLabel = null;
	if (localProvider != null) {
	    filedLabel = localProvider.getText(target.getSimpleName() + "." + field.getName(), field.getName());
	} else {
	    filedLabel = field.getName();
	}

	try {
	    Object expr = Ognl.parseExpression(rule);
	    OgnlContext ctx = new OgnlContext();
	    Object value = Ognl.getValue(expr, ctx, input);
	    if (value instanceof Boolean) {
		boolean result = ((Boolean) value).booleanValue();
		if (!result) {
		    addFieldError(field.getName(), getText("crude.validation.error.rule", new String[] { filedLabel, rule }));
		}
		return result;
	    } else {
		warn("NOT VALID RULE FOR :" + target.getSimpleName() + "." + field.getName());
		return true;
	    }

	} catch (OgnlException e) {
	    warn("OGNL EXCEPTION RULE FOR :" + target.getSimpleName() + "." + field.getName());
	}
	return true;
    }

    private boolean checkVoid(Class target, TextProvider localProvider) {
	if (target != null) {
	    Field[] fields = target.getFields();
	    for (Field field : fields) {
		if (field.getModifiers() == 1) {
		    Owner owner = field.getAnnotation(Owner.class);

		    if (owner != null) {
			String roles_s = owner.adminRoles();
			String holdingRule = owner.holdingRule();
			List roles = StringUtil.stringToList(roles_s);
			if (!checkRole(getUser().getRoles(), roles)) {
			    return true;
			}
		    }

		    ValidField validF = field.getAnnotation(ValidField.class);
		    if (validF != null) {
			if (!validF.empty()) {
			    if (ExprValidator.validVoid(objState.get(field.getName()))) {
				String filedLabel = null;
				if (localProvider != null) {
				    filedLabel = localProvider.getText(target.getSimpleName() + "." + field.getName(), field.getName());
				} else {
				    filedLabel = field.getName();
				}
				addFieldError(field.getName(), getText("crude.validation.error.void", new String[] { filedLabel }));
				return false;
			    }
			}
		    }
		}
	    }
	}

	return true;

    }

    @Override
    protected String doIt() throws ApplicationException {

	if (targetClass == null)
	    throw new ActionException("Tipo oggeto non definito");

	Class target = getPrototypeClass(targetClass);

	if (target == null)
	    throw new ActionException("Classe dell'oggetto non caricabile");

	ResourceBundle bundle = getTexts(target.getName());

	TextProvider localProvider = null;

	if (bundle != null) {
	    final Locale locale = (Locale) ActionContext.getContext().getLocale();
	    TextProviderFactory tpf = new TextProviderFactory();
	    localProvider = tpf.createInstance(bundle, new LocaleProvider() {
		public Locale getLocale() {
		    return locale;
		}
	    });
	}
	try {
	    return doIt(target);
	} catch (DataIntegrityViolationException ex) {
	    error("", ex);
	    String msg = ex.getMostSpecificCause().getLocalizedMessage();
	    if (msg.startsWith("Duplicate entry")) {
		String fieldName = null;
		String value = null;
		REXTokenizer tk = new REXTokenizer(msg, "\'[^\']*\'", false);
		if (tk.hasNext()) {
		    value = ((String) tk.next()).replaceAll("'", "");
		}
		if (tk.hasNext()) {
		    fieldName = ((String) tk.next()).replaceAll("'", "");
		}
		if (fieldName != null) {
		    String filedLabel = null;
		    if (localProvider != null) {
			filedLabel = localProvider.getText(target.getSimpleName() + "." + fieldName, fieldName);
		    } else {
			filedLabel = fieldName;
		    }
		    addFieldError(fieldName, getText("crude.validation.error.duplicate", new String[] { filedLabel, value }));
		}
		addActionError(getText("crude.validation.error.generic.duplicate", msg));
		return INPUT;
	    }
	    addActionError(getText("crude.insert.error.generic.duplicate") + msg);

	    return INPUT;
	} catch (Exception e) {
	    error("Errore non gestito", e);
	    addActionError(getText("crude.insert.error.db"));
	    return INPUT;
	}
    }

    protected abstract String doIt(Class target) throws Exception;

}
