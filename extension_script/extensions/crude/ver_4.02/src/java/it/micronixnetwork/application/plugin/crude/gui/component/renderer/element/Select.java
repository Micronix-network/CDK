package it.micronixnetwork.application.plugin.crude.gui.component.renderer.element;

import it.micronixnetwork.application.plugin.crude.annotation.AsincInfo;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.SelectRenderer;
import it.micronixnetwork.application.plugin.crude.gui.component.renderer.FieldRenderer;
import it.micronixnetwork.application.plugin.crude.helper.I18n;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;

import com.opensymphony.xwork2.util.ValueStack;

public class Select extends FieldRenderer {

    private final SelectRenderer select;
    private final ToView toview;
    private final ToInput toinput;

    public Select(Class targetClass, String fieldName, Field field) {
	super(targetClass, fieldName, field);
	select = field.getAnnotation(SelectRenderer.class);
	toview = field.getAnnotation(ToView.class);
	toinput = field.getAnnotation(ToInput.class);
    }

    public StringBuffer renderInput(ValueStack stack,Object fieldValue) throws IOException {
	StringBuffer result = new StringBuffer();
	String cardId = (String) stack.findValue("cardId");
	String uiid = (String) stack.findValue("uiid");
	if (select != null) {
	    result.append("<p style=\"\" class=\"" + fieldName + "_field "+getCardId(stack)+"_crud_field\">");

	    String map = select.map();

	    Object values = null;

	    if (!map.equals("nill")) {
		values = stack.findValue(map);
	    }

	    String startValue = select.startValue();

	    List startValue_option = null;

	    if (!startValue.equals("nill")) {
		startValue_option = (List) stack.findValue(startValue);
	    }
	    
	    String suffix=fieldName.replaceAll("\\.", "_");
	    
	    String selectId=cardId + "_" + uiid +"_"+targetClass.getSimpleName()+"_"+suffix+"_input";
	    
	    result.append(writeLabel(null, stack,true));
	    result.append("<select id=\""+selectId+"\" name=\"objState['" + fieldName + "']\"  class=\"tooltip "+getCardId(stack)+"_right_input_select\" style=\"" + getFieldStyle(field) + "\">");
	    if (values != null && values instanceof LinkedHashMap) {
		if (startValue_option != null) {
		    result.append(writeOption(startValue_option.get(0), startValue_option.get(1), fieldValue, stack));
		}
		for (Object label : ((LinkedHashMap) values).keySet()) {
		    result.append(writeOption(label, ((LinkedHashMap) values).get(label), fieldValue, stack));
		}
	    }
	    result.append("</select>");
	    result.append("</p>");
	    AsincInfo asincInfo = (AsincInfo) field.getAnnotation(AsincInfo.class);
	    if (asincInfo != null) {
		result.append("<script type=\"text/javascript\">");
		result.append("$('#"+selectId+"').change(function(){");
		result.append(cardId + "_show_info('" + fieldName + "',$(this).val(),'" + targetClass.getName() + "','" + uiid + "');");
		result.append("})");
		result.append("</script>");
	    }
	}
	return result;
    }
    
    private StringBuffer writeOption(Object label, Object value, Object actual, ValueStack stack) throws IOException {
	StringBuffer html = new StringBuffer();
	if (value != null && label != null) {
	    html.append("<option value='");
	    html.append(value.toString());
	    html.append("'");
	    if (actual != null && actual.toString().trim().equals(value.toString().trim())) {
		html.append(" selected");
	    }
	    html.append(">");
	    html.append(I18n.getText(targetClass.getSimpleName() + "." + label, label.toString(), stack));
	    html.append("</option>");
	}
	return html;
    }

    public StringBuffer renderView(ValueStack stack,Object fieldValue) throws IOException {
	StringBuffer html = new StringBuffer();
	html.append("<p style=\"\" class=\"" + fieldName + "_field "+getCardId(stack)+"_crud_field\">");
	html.append(writeLabel(null, stack,false));
	html.append(drawDefaultView(targetClass, field, fieldValue, toview!=null?toview.hidden():false, select.viewRule(), stack,getCardId(stack)+"_right_view_row"));
	html.append("</p>");
	return html;
    }
    
    public StringBuffer renderCell(ValueStack stack,Object fieldValue) throws IOException {
	StringBuffer html = new StringBuffer();
	html.append(drawDefaultView(targetClass, field, fieldValue, toview!=null?toview.hidden():false, select.viewRule(), stack,""));
	return html;
    }
    
    

    

}
