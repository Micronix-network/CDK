package it.micronixnetwork.application.plugin.crude.gui.component.renderer.element;

import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.TextRenderer;
import it.micronixnetwork.application.plugin.crude.gui.component.renderer.FieldRenderer;
import it.micronixnetwork.application.plugin.crude.helper.Format;

import java.io.IOException;
import java.lang.reflect.Field;

import com.opensymphony.xwork2.util.ValueStack;

public class Text extends FieldRenderer {
    
    public final static String UBIMAJOR="Ub1m410rm1n0rc35547";
    
    private boolean disabled;
    
    private final TextRenderer text;
    
    private final ToView toview;

    public Text(Class targetClass, String fieldName, Field field, boolean disabled) {
	super(targetClass, fieldName, field);
	this.disabled=disabled;
	this.text=field.getAnnotation(TextRenderer.class);
	toview = field.getAnnotation(ToView.class);
    }

    @Override
    public StringBuffer renderInput(ValueStack stack,Object fieldValue) throws IOException{
	StringBuffer html=new StringBuffer();
	Object value=fieldValue;
	String textId=calcInputId(stack);
	html.append("<p style=\"\" class=\"" + fieldName + "_field "+getCardId(stack)+"_crud_field\">");
	String input_type ="";
	String defaultValue =""; 
	
	    if(text!=null){
		input_type = text.type();
		defaultValue=text.initValue();
	    }

	    html.append(writeLabel(null,stack,true));
	    html.append("<input id=\""+textId+"\" ");
	   
	    if(TextRenderer.PASSWORD_TYPE.equals(input_type)) {
		html.append("type=\"password\"");
	    }else{
		html.append("type=\"text\"");
	    }
	    
	    html.append("name=\"objState['" + fieldName + "']\"  class=\""+TIP_FIELD+" "+getCardId(stack)+INPUT_FIELD+" "+getCardId(stack)+"_right_input_row");
	    
	    if (TextRenderer.INT_TYPE.equals(input_type)) {
		html.append(" input_integer");
	    }
	    if (TextRenderer.CURRENCY_TYPE.equals(input_type)) {
		html.append(" input_currency");
	    }
	    if (TextRenderer.PERC_TYPE.equals(input_type)) {
		html.append(" input_perc");
	    }
	    if (TextRenderer.DATE_TYPE.equals(input_type)) {
		html.append(" date_input");
	    }
	    if (TextRenderer.TIME_TYPE.equals(input_type)) {
		html.append(" time_input");
	    }
	    if (TextRenderer.REAL_TYPE.equals(input_type)) {
		html.append(" input_real");
	    }
	    if (TextRenderer.MINUTES_TYPE.equals(input_type)) {
		html.append(" time_input");
	    }

	   
	    if (!defaultValue.equals("")) {
		if (value == null) {
		    value = stack.findValue(defaultValue);
		}
	    }
	    
	    String toStamp;
	    
	    if(TextRenderer.PASSWORD_TYPE.equals(input_type)) {
		toStamp=UBIMAJOR;
	    }else{
		toStamp=Format.formatValue(value, field,stack);
	    }
	    
	    html.append("\" value=\"" + toStamp + "\"/ style=\""+getFieldStyle(field)+"\"");
	    if(disabled){
		html.append(" disabled");
	    }
	    html.append(">");
	    html.append("</p>");
	    if(text!=null){
	    html.append("<script type=\"text/javascript\">");
    	    if(!text.droppableFrom().equals("")){
    		html.append("$('#"+textId+"').droppable({");
    		html.append("accept: '."+text.droppableFrom()+"',");
    		html.append("hoverClass: 'hover',");
    		html.append("tolerance: 'pointer',");
    		html.append("drop: function(event, ui){");
    		html.append("$('#"+textId+"').val(ui.draggable.attr('toDrop'));");
    		html.append("} });");
            }
	    html.append("</script>");
	    }
	    return html;
	
    }

    @Override
    public StringBuffer renderView(ValueStack stack,Object fieldValue) throws IOException {
	StringBuffer html = new StringBuffer();
	html.append("<p style=\"\" class=\"" + fieldName + "_field "+getCardId(stack)+"_crud_field\">");
	html.append(writeLabel(null, stack,false));
	html.append(drawDefaultView(targetClass, field, fieldValue, toview!=null?toview.hidden():false, text!=null?text.viewRule():"nill", stack,getCardId(stack)+"_right_view_row"));
	html.append("</p>");
	return html;
    }

    @Override
    public StringBuffer renderCell(ValueStack stack,Object fieldValue) throws IOException {
	StringBuffer html = new StringBuffer();
	html.append(drawDefaultView(targetClass, field, fieldValue, toview!=null?toview.hidden():false, text!=null?text.viewRule():"nill", stack,""));
	return html;
    }

   


}
