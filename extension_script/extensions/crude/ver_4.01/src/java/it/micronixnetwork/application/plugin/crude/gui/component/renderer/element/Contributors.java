package it.micronixnetwork.application.plugin.crude.gui.component.renderer.element;

import it.micronixnetwork.application.plugin.crude.gui.component.renderer.FieldRenderer;
import it.micronixnetwork.application.plugin.crude.helper.I18n;
import it.micronixnetwork.gaf.struts2.action.WebAppAction;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.LinkedHashMap;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;

public class Contributors extends FieldRenderer {

    public Contributors(Class targetClass, String fieldName, Field field) {
	super(targetClass, fieldName, field);

    }

    @Override
    public StringBuffer renderInput(ValueStack stack,Object fieldValue) throws IOException {
	
	
	StringBuffer html = new StringBuffer();

	ParameterizedType type = (ParameterizedType) field.getGenericType();
	Class contribClass = (Class) type.getActualTypeArguments()[0];

	String query = "From " + contribClass.getSimpleName();

	LinkedHashMap values = null;

	if (!query.equals("nill")) {
	    try {
		// recupero la lista dei valori da visualizzare dal db
		ActionInvocation inv = ActionContext.getContext().getActionInvocation();
		Object realAction = inv.getAction();
		if (realAction != null && realAction instanceof WebAppAction) {
		    List qresult = (List) ((WebAppAction) realAction).executeHQLQuery(query, false);
		    values = new LinkedHashMap();
		    for (Object obj : qresult) {
			Object val = contribClass.getField("id").get(obj);
			values.put(obj.toString(), val);
		    }
		}
	    } catch (Exception ex) {
		throw new IOException(ex);
	    }
	}

	html.append("<p style=\"\" class=\"" + fieldName + "_field "+getCardId(stack)+"_crud_field\">");
	html.append(writeLabel(""+getCardId(stack)+"_full_label", stack,true));
	html.append("<div style=\"border: 1px solid silver\">");	
	html.append("<select id=\""+getCardId(stack)+"_"+fieldName+"\" name=\"objState['" + fieldName + "']\"  class=\"tooltip right_input_multiselect multiselect\" style=\"" + getFieldStyle(field) + "\" size=\"6\" multiple=\"true\">");
	if (values != null && values instanceof LinkedHashMap) {
	    for (Object label : ((LinkedHashMap) values).keySet()) {
		html.append(writeOption(contribClass, label, ((LinkedHashMap) values).get(label), fieldValue, stack));
	    }
	}
	html.append("</select>");
	html.append("<div style=\"clear: both;\">");
	html.append("</div>");
	html.append("</div>");
	html.append("</p>");
	return html;
    }

    public StringBuffer renderView(ValueStack stack,Object fieldValue) throws IOException {
	StringBuffer html = new StringBuffer();
	html.append("<p style=\"\" class=\"" + fieldName + "_field "+getCardId(stack)+"_crud_field\">");
	html.append(writeLabel(""+getCardId(stack)+"_full_label", stack,false));
	if (fieldValue != null) {
	    if (fieldValue instanceof Iterable) {
		html.append("<ul class=\"to_view_list\">");
		for (Object ele : (Iterable) fieldValue) {
		    html.append("<li>" + ele.toString().trim() + "</li>");
		}
		html.append("</ul>");
	    }
	}
	html.append("</p>");
	return html;
    }
    
    public StringBuffer renderCell(ValueStack stack,Object fieldValue) throws IOException {
	StringBuffer html = new StringBuffer();
	if (fieldValue != null) {
	    if (fieldValue instanceof Iterable) {
		html.append("<ul class=\"to_view_list\">");
		for (Object ele : (Iterable) fieldValue) {
		    html.append("<li>" + ele.toString().trim() + "</li>");
		}
		html.append("</ul>");
	    }
	}
	return html;
    }

    private StringBuffer writeOption(Class type, Object label, Object value, Object actual, ValueStack stack) throws IOException {
	StringBuffer html = new StringBuffer();
	if (value != null && label != null) {
	    html.append("<option value='");
	    html.append(value.toString());
	    html.append("'");
	    if (actual != null && checkList(type, actual, value)) {
		html.append(" selected");
	    }
	    html.append(">");
	    html.append(I18n.getText(targetClass.getSimpleName() + "." + label, label.toString(), stack));
	    html.append("</option>");
	}
	return html;
    }

    private boolean checkList(Class type, Object iterable, Object value) {
	if (iterable == null)
	    return false;
	if (iterable instanceof Iterable) {
	    for (Object ele : (Iterable) iterable) {
		try {
		    Object key = type.getField("id").get(ele);
		    if (key.equals(value))
			return true;
		} catch (Exception e) {
		}
	    }
	}
	return false;
    }

}
