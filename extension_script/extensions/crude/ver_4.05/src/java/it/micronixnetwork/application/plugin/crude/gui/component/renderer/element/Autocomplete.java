package it.micronixnetwork.application.plugin.crude.gui.component.renderer.element;

import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.AutocompleteRenderer;
import it.micronixnetwork.application.plugin.crude.gui.component.renderer.FieldRenderer;

import java.io.IOException;
import java.lang.reflect.Field;

import com.opensymphony.xwork2.util.ValueStack;

public class Autocomplete extends FieldRenderer {

    private final AutocompleteRenderer autoc;
    

    public Autocomplete(Class targetClass, String fieldName, Field field) {
	super(targetClass, fieldName, field);
	autoc = field.getAnnotation(AutocompleteRenderer.class);
    }

    public StringBuffer renderInput(ValueStack stack,Object fieldValue) throws IOException {
	StringBuffer result = new StringBuffer();
	if (autoc != null) {
	    
	    String autocId=calcInputId(stack);
	    result.append(writeLabel(null, stack,true));
	    result.append("<input id=\""+autocId+"\" class=\""+TIP_FIELD+" "+getCardId(stack)+INPUT_FIELD+" "+getCardId(stack)+"_right_input_row\" style=\"" + getFieldStyle(field) + "\"");
	    if(!autoc.viewRule().equals("nill")){
		Object valueLabel=stack.findValue(autoc.viewRule());
		if(valueLabel!=null){
		    result.append("value=\""+valueLabel+"\"");
		}
	    }else{
		if(fieldValue!=null){
                    result.append("value=\""+fieldValue+"\"");
		}
	    }
	    result.append(">");
	    result.append("<input type=\"hidden\" id=\""+autocId+"_id\" name=\"objState['" + fieldName + "']\"");
	    if(fieldValue!=null){
		result.append("value=\""+fieldValue+"\"");
	    }
	    result.append(">");
	    result.append("</input>");
	    result=appendFieldParagraph(result, stack);
	    result.append("<script type=\"text/javascript\">");
	    result.append("$('#"+autocId+"').autocomplete({");
	    result.append("source: function( request, response ) {");
	    result.append("$.ajax({");
            result.append("url: \""+stack.findValue("calcAction('autocompleteQuery','crude',null)")+"\",");
            result.append("dataType: \"json\",");
            result.append("data: {fieldValue: request.term,fieldName:\""+fieldName+"\",className:\""+targetClass.getName()+"\"},");
            result.append("success: function( data ) {");
            result.append("response(data)");
            result.append("}");
            result.append("})");
            result.append("},");
            result.append("minLength: 2,");
            result.append("focus: function( event, ui ) {");
            result.append("$('#"+autocId+"').val( ui.item.label );");
            result.append("return false;");
            result.append("},");
            result.append("select: function( event, ui ) {");
            result.append("$('#"+autocId+"').val( ui.item.label );");
            result.append("$('#"+autocId+"_id').val( ui.item.value );");
            result.append("return false;");
            result.append("},");
            result.append("open: function() {");
            result.append("},");
            result.append("close: function() {");
            result.append("}");
            result.append("});");
            if(!autoc.droppableFrom().equals("")){
                result.append("$('#"+autocId+"').droppable({");
                result.append("accept: '."+autoc.droppableFrom()+"',");
                result.append("hoverClass: 'hover',");
                result.append("tolerance: 'pointer',");
                result.append("drop: function(event, ui){");
                result.append("$('#"+autocId+"').val(ui.draggable.attr('toDrop'));");
                result.append("} });");
            }
	    result.append("</script>");
	}
	return result;
    }

    public StringBuffer renderView(ValueStack stack,Object fieldValue) throws IOException {
	StringBuffer html = new StringBuffer();
	html.append(writeLabel(null, stack,false));
	html.append(drawDefaultView(targetClass, field, fieldValue, toview!=null?toview.masked():false, autoc.viewRule(), stack,getCardId(stack)+"_right_view_row"));
	html=appendFieldParagraph(html, stack);
	return html;
    }
    
    public StringBuffer renderCell(ValueStack stack,Object fieldValue) throws IOException {
	StringBuffer html = new StringBuffer();
	html.append(drawDefaultView(targetClass, field, fieldValue, toview!=null?toview.masked():false, autoc.viewRule(), stack,""));
	return html;
    }
    
    

    

}
