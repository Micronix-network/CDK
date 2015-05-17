package it.micronixnetwork.application.plugin.crude.gui.component.renderer.element;

import it.micronixnetwork.application.plugin.crude.annotation.renderer.YesNoRenderer;
import it.micronixnetwork.application.plugin.crude.gui.component.renderer.FieldRenderer;
import it.micronixnetwork.application.plugin.crude.helper.I18n;

import java.io.IOException;
import java.lang.reflect.Field;

import com.opensymphony.xwork2.util.ValueStack;

public class YesNo extends FieldRenderer {

    private final YesNoRenderer yesNo;

    public YesNo(Class targetClass, String fieldName, Field field) {
	super(targetClass, fieldName, field);
	yesNo = field.getAnnotation(YesNoRenderer.class);
    }

    @Override
    public StringBuffer renderInput(ValueStack stack,Object fieldValue) throws IOException {
	StringBuffer html = new StringBuffer();
	html.append("<p style=\"\" class=\"" + fieldName + "_field "+getCardId(stack)+"_crud_field\">");
	String values = yesNo.values();
	String[] a_values = values.split("\\|");
	String[] real_values = new String[2];
	if (a_values.length > 1) {
	    real_values[0] = a_values[0];
	    real_values[1] = a_values[1];
	} else {
	    real_values[0] = "1";
	    real_values[1] = "0";
	}
	html.append(writeLabel(null, stack,true));
	html.append("<select name=\"objState['" + fieldName + "']\"  class=\""+TIP_FIELD+" "+getCardId(stack)+INPUT_FIELD+" "+getCardId(stack)+"_right_input_select\" style=\"" + getFieldStyle(field) + "\">");
	for (int i = 0; i < real_values.length; i++) {
	    html.append("<option value=\"" + real_values[i] + "\"");
	    if (fieldValue != null) {
		if (real_values[i].equals(fieldValue.toString())) {
		    html.append(" selected=\"selected\"");
		}
		;
	    }
	    html.append(">");
	    if (i == 0)
		html.append(I18n.getText("crude.yes", "Yes", stack));
	    else
		html.append(I18n.getText("crude.no", "No", stack));
	    html.append("</option>");
	}
	html.append("</select>");
	html.append("</p>");
	return html;
    }

    @Override
    public StringBuffer renderView(ValueStack stack,Object fieldValue) throws IOException {
	StringBuffer html = new StringBuffer();
	html.append("<p style=\"\" class=\"" + fieldName + "_field "+getCardId(stack)+"_crud_field\">");
	html.append(writeLabel(null, stack,false));
	html.append(drawDefaultView(targetClass, field, fieldValue, false, yesNo.viewRule(), stack,getCardId(stack)+"_right_view_row"));
	html.append("</p>");
	return html;
    }

    @Override
    public StringBuffer renderCell(ValueStack stack,Object fieldValue) throws IOException {
	StringBuffer html = new StringBuffer();
	html.append(drawDefaultView(targetClass, field, fieldValue, false, yesNo.viewRule(), stack,""));
	return html;
    }

}
