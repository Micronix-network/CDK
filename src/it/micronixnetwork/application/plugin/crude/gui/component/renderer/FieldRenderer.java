package it.micronixnetwork.application.plugin.crude.gui.component.renderer;

import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.ValidField;
import it.micronixnetwork.application.plugin.crude.helper.Format;
import it.micronixnetwork.application.plugin.crude.helper.I18n;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.util.ValueStack;
import it.micronixnetwork.application.plugin.crude.annotation.Conditional;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;

public abstract class FieldRenderer {
    
    protected static final String TIP_FIELD="tooltip";
    protected static final String INPUT_FIELD="_input_field";
    
    public static final String HIDDEN_FIELD="********";

    protected final Field field;
    protected final Class targetClass;
    protected final String fieldName;
    protected final ToView toview;
    protected final ToInput toinput;
    protected final Conditional condition;


  
    public FieldRenderer(Class targetClass, String fieldName, Field field) {
	this.field = field;
	this.fieldName = fieldName;
	this.targetClass = targetClass;
        this.toview = field.getAnnotation(ToView.class);
	this.toinput = field.getAnnotation(ToInput.class);
        this.condition= field.getAnnotation(Conditional.class);
   
    }

    public abstract StringBuffer renderInput(ValueStack stack,Object fieldValue) throws IOException;
    
    public abstract StringBuffer renderView(ValueStack stack,Object fieldValue) throws IOException;
    
    public abstract StringBuffer renderCell(ValueStack stack,Object fieldValue) throws IOException;

    protected StringBuffer writeLabel(String css_class,ValueStack stack,boolean input) throws IOException {
	StringBuffer html = new StringBuffer();
	ValidField validation = field.getAnnotation(ValidField.class);
	FieldStyleDirective direct = field.getAnnotation(FieldStyleDirective.class);
	String style="";
	if(direct!=null){
	    style=direct.labelFieldStyle();
	}
	boolean required = false;

	if (validation != null)
	    required = !validation.empty();
	String clip = "";
	if (required && input)
	    clip = "(*)";
	if (css_class == null)
	    css_class = "";
	html.append("<label style=\""+style+"\" for=\"" + fieldName + "\" class=\"" + css_class + " "+getCardId(stack)+"_data_label\">" + clip + " " + I18n.getText(targetClass.getSimpleName() + "." + fieldName, field.getName(),stack) + "</label>");
	return html;
    }
    
    protected String calcInputId(ValueStack stack){
	String cardId = (String) stack.findValue("cardId");
	String uiid = (String) stack.findValue("uiid");
	String suffix=fieldName.replaceAll("\\.", "_");
	String id=cardId + "_" + uiid +"_"+targetClass.getSimpleName()+"_"+suffix+"_input";
	return id;
    }
    
    protected StringBuffer drawDefaultView(Class targetClass, Field field, Object value,boolean hidden,String rule, ValueStack stack,String cssClass) throws IOException {

	String cardId = (String) stack.findValue("cardId");
	
	String uiid=(String) stack.findValue("uiid");
	
	String clazz="";
	
	StringBuffer result = new StringBuffer();

	if (value == null)
	    value = "";
	
	if (hidden) {
	    value = HIDDEN_FIELD;
	}
	
	Object ognlComputed=null;
	
	if(!hidden && !"nill".equals(rule)){
	    ognlComputed = stack.findValue(rule);
	    if (ognlComputed != null) {
		if (ognlComputed instanceof Map) {
		    Object toShow = ((Map) ognlComputed).get(value.toString());
		    if (toShow instanceof List) {
			clazz = (String) ((List) toShow).get(1);
			value = ((List) toShow).get(0);
		    }else{
			value = toShow;
		    }
		}else{
		    value=ognlComputed;
		}
	    }
	}
	
	String suffix=fieldName.replaceAll("\\.", "_");
	
	result.append("<span id=\""+cardId+"_"+uiid+"_"+targetClass.getSimpleName()+"_"+suffix+"\" class=\"" + clazz + " "+cssClass+"\"><span name=\"objState['" + fieldName + "']\" style=\"" + getFieldStyle(field) + "\">" + Format.formatValue(value, field, stack) + "</span></span>");
	
	//Script per la modifica onfly solo nell'interfaccia di view o di insert
	result.append("<script type=\"text/javascript\">");
	result.append("function "+cardId+"_"+uiid+"_"+targetClass.getSimpleName()+"_set_"+suffix+"(val){");
	if (ognlComputed != null) {
	    if (ognlComputed instanceof Map) {
		    for (Object key : ((Map) ognlComputed).keySet()) {
			result.append("if(val=='"+key+"'){");
			Object toShow = ((Map) ognlComputed).get(key);
			if (toShow instanceof List) {
				clazz = (String) ((List) toShow).get(1);
				value = ((List) toShow).get(0);
				result.append("$(\"#"+cardId+"_"+uiid+"_"+targetClass.getSimpleName()+"_"+suffix+"\").removeClass();");
				result.append("$(\"#"+cardId+"_"+uiid+"_"+targetClass.getSimpleName()+"_"+suffix+"\").addClass('"+clazz+" "+cssClass+"');");
			    }else{
				value = toShow;
			    }
			result.append("}");
		    }
		    Object toShow = ((Map) ognlComputed).get(value.toString()); 
	    }
	}
	result.append("$(\"#"+cardId+"_"+uiid+"_"+targetClass.getSimpleName()+"_"+suffix+" [name=\\\"objState['"+fieldName+"']\\\"]\").text(val);");
	result.append("}");
	result.append("</script>");
	return result;
    }

    protected String getCardId(ValueStack stack){
	String cardId = (String) stack.findValue("cardId");
	if(cardId==null) return "";
	return cardId;
    }

    protected String getFieldStyle(Field field) {
	FieldStyleDirective direct = field.getAnnotation(FieldStyleDirective.class);
	String style = "";
	if (direct == null || "".equals(direct.inputFieldStyle())) {
	    style = "min-width:100px";
	}else{
	    style = direct.inputFieldStyle();
	}
	return style;
    }
    
    protected StringBuffer appendFieldParagraph(StringBuffer compBuffer,ValueStack stack){
        String uiid=(String) stack.findValue("uiid");
        StringBuffer result=new StringBuffer("<p id=\""+getCardId(stack)+"_"+uiid+"_"+ fieldName + "_field_paragraph\" class=\"" + fieldName + "_field "+getCardId(stack)+"_crud_field\" style=\"display:"+getDisplay(stack)+"\">");
        result.append(compBuffer);
        result.append("</p>");
        return result;
    }
    
    protected String getDisplay(ValueStack stack){
        if(condition==null) return "";
        Boolean hide=false;
        try{
            hide=!(Boolean)stack.findValue(condition.rule());
        }catch(Exception ex){
            hide=true;
        }
        if(hide){
            return "none";
        }
        return "";
    }

    public Field getField() {
        return field;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public String getFieldName() {
        return fieldName;
    }

    
    

}
