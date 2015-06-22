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
import it.micronixnetwork.gaf.util.StringUtil;

public class Select extends FieldRenderer {

    private final SelectRenderer select;
    

    public Select(Class targetClass, String fieldName, Field field) {
	super(targetClass, fieldName, field);
	select = field.getAnnotation(SelectRenderer.class);
    }

    public StringBuffer renderInput(ValueStack stack,Object fieldValue) throws IOException {
	StringBuffer result = new StringBuffer();
	String cardId = (String) stack.findValue("cardId");
	String uiid = (String) stack.findValue("uiid");
        
	if (select != null) {
	    
	    String map = select.map();
            
            boolean append=select.append();

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
	    result.append("<select id=\""+selectId+"\" name=\"objState['" + fieldName + "']\"  class=\""+TIP_FIELD+" "+getCardId(stack)+INPUT_FIELD+" "+getCardId(stack)+"_right_input_select\" style=\"" + getFieldStyle(field) + "\">");
	    if (values != null && values instanceof LinkedHashMap) {
		if (startValue_option != null) {
		    result.append(writeOption(startValue_option.get(1), startValue_option.get(0), fieldValue, stack,append));
		}
		for (Object code : ((LinkedHashMap) values).keySet()) {
                    result.append(writeOption(((LinkedHashMap) values).get(code), code, fieldValue, stack,append));
		}
	    }
	    result.append("</select>");
	    result=appendFieldParagraph(result, stack);
	    AsincInfo asincInfo = (AsincInfo) field.getAnnotation(AsincInfo.class);
            List<String> activeOnChange=StringUtil.stringToList(select.activeOnChange());
            String depend= select.dependFrom();
            result.append("<script type=\"text/javascript\">");
            result.append("$('#"+selectId+"').change(function(){");
            if(asincInfo != null){
                result.append("try{");
                result.append(cardId + "_show_info('" + fieldName + "',$(this).val(),'" + targetClass.getName() + "','" + uiid + "');");
                result.append("}catch(error){alert(error);}");
            }
            if(activeOnChange.size()>0){
                for (String  toCall: activeOnChange) {
                    result.append("try{");
                    result.append(cardId + "_active_"+toCall+"('" + fieldName + "',$(this).val(),'" + targetClass.getName() + "','" + uiid + "');");
                    result.append("}catch(error){alert(error);}");
                }
            }
            result.append("});");
            if(!depend.equals("nill")){
                result.append("function "+cardId + "_active_"+fieldName+"(caller,value,targetClass,uiid){");
                //result.append("alert('"+selectId+"');");
                result.append("var waiting='"+depend+"';");
                result.append("if(waiting==caller){");
                result.append("$.ajax({");
                result.append("url: \""+stack.findValue("calcAction('asincSelectQuery','crude',null)")+"\",");
                result.append("dataType: \"json\",");
                result.append("data: {fieldValue: value,fieldName:\""+fieldName+"\",className:\""+targetClass.getName()+"\"},");
                result.append("success: function( data ) {");
                result.append("var options = '';");
                result.append("for (var i=0; i<data.length; i++) {" +
                              "    options +='<option value=\"' + data[i].value + '\">' + data[i].label + '</option>';" +
                              "}");
                result.append("$('#"+selectId+"').html(options);");
                result.append("},");
                result.append("error : function (richiesta,stato,errori) {");
                result.append("alert(\"Error: \"+errori);");
                result.append("}");
                result.append("})");
                result.append("};");
                result.append("};");
            }
            result.append("</script>");
	}
	return result;
    }
    
    private StringBuffer writeOption(Object attributes, Object value, Object actual, ValueStack stack,boolean append) throws IOException {
	StringBuffer html = new StringBuffer();
	if (value != null && attributes != null) {
            String label=null;
            String[] attrs=null;
            if((attributes instanceof String[])){
                attrs=(String[])attributes;
                label=attrs[0];
            }else{
                 label=attributes+"";
            }
	    html.append("<option value='");
	    html.append(value.toString());
	    html.append("'");
            if(attrs!=null){
                for(int i=1;i<attrs.length;i++){   
                    html.append(" attr"+i+"='"+attrs[i]+"'");
                }
            }
	    if (actual != null && actual.toString().trim().equals(value.toString().trim())) {
		html.append(" selected");
	    }
	    html.append(">");
            if(label instanceof String){
                if(append){
                    html.append(value.toString()+" - "+I18n.getText(targetClass.getSimpleName() + "." + label, label.toString(), stack));
                }else{
                    html.append(I18n.getText(targetClass.getSimpleName() + "." + label, label.toString(), stack));
                }
            }
	    html.append("</option>");
	}
	return html;
    }

    public StringBuffer renderView(ValueStack stack,Object fieldValue) throws IOException {
	StringBuffer html = new StringBuffer();
	html.append(writeLabel(null, stack,false));
        String rule=select.viewRule();
        if(rule.equals("nill"))
            rule=select.map();
	html.append(drawDefaultView(targetClass, field, fieldValue, toview!=null?toview.masked():false, rule, stack,getCardId(stack)+"_right_view_row"));
	html=appendFieldParagraph(html, stack);
	return html;
    }
    
    public StringBuffer renderCell(ValueStack stack,Object fieldValue) throws IOException {
	StringBuffer html = new StringBuffer();
	html.append(drawDefaultView(targetClass, field, fieldValue, toview!=null?toview.masked():false, select.viewRule(), stack,""));
	return html;
    }
    
    

    

}
