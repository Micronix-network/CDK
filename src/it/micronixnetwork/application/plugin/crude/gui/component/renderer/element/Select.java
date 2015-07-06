package it.micronixnetwork.application.plugin.crude.gui.component.renderer.element;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import it.micronixnetwork.application.plugin.crude.annotation.AsincInfo;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.SelectRenderer;
import it.micronixnetwork.application.plugin.crude.gui.component.renderer.FieldRenderer;
import it.micronixnetwork.application.plugin.crude.helper.I18n;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;

import com.opensymphony.xwork2.util.ValueStack;
import it.micronixnetwork.application.plugin.crude.action.CrudAction;
import it.micronixnetwork.application.plugin.crude.annotation.NULLClass;
import it.micronixnetwork.gaf.util.StringUtil;
import java.util.HashMap;
import java.util.Map;

public class Select extends FieldRenderer {

    private final SelectRenderer select;

    public Select(Class targetClass, String fieldName, Field field) {
        super(targetClass, fieldName, field);
        select = field.getAnnotation(SelectRenderer.class);
    }

    public StringBuffer renderInput(ValueStack stack, Object fieldValue) throws IOException {
        StringBuffer result = new StringBuffer();
        String cardId = (String) stack.findValue("cardId");
        String uiid = (String) stack.findValue("uiid");

        if (select != null) {

            String map = select.map();

            Class mapObj = select.mappedObject();

            String startValue = select.startValue();

            List startValue_option = null;

            if (!startValue.equals("nill")) {
                startValue_option = (List) stack.findValue(startValue);
            }

            String selectId = calcInputId(stack);
            result.append(writeLabel(null, stack, true));

            result.append("<select id=\"" + selectId + "\" name=\"objState['" + fieldName + "']\"  class=\"" + TIP_FIELD + " " + getCardId(stack) + INPUT_FIELD + " " + getCardId(stack) + "_right_input_select\" style=\"" + getFieldStyle(field) + "\">");
            if (startValue_option != null) {
                result.append(writeOption(startValue_option.get(0), startValue_option.get(1), fieldValue, stack, null));
            }

            if (!(mapObj == NULLClass.class)) {
                appendSelectorValues(result, mapObj, stack, fieldValue);
            } else {
                if (!map.equals("nill")) {
                    appendSelectorValues(result, map, stack, fieldValue);
                }
            }
            result.append("</select>");
            result = appendFieldParagraph(result, stack);

            result.append("<script type=\"text/javascript\">");

            //Aggiunta logica per la gestione dell'onchange del selettore
            appendOnChangeEventLogic(result, selectId, cardId, uiid);

            //Aggiunta logica per la getione della chiamata degli eveti sull'onchange
            appendActiveOnChangeLogic(result, stack, startValue_option, selectId, cardId, uiid);

            result.append("</script>");
        }
        return result;
    }

    private void appendSelectorValues(StringBuffer result, String map, ValueStack stack, Object fieldValue) throws IOException {
        Object values = stack.findValue(map);
        if (values != null && values instanceof LinkedHashMap) {
            for (Object code : ((LinkedHashMap) values).keySet()) {
                result.append(writeOption(((LinkedHashMap) values).get(code), code, fieldValue, stack, null));
            }
        }
    }

    private void appendSelectorValues(StringBuffer result, Class clazz, ValueStack stack, Object fieldValue) throws IOException {
        ActionInvocation inv = ActionContext.getContext().getActionInvocation();
        Object realAction = inv.getAction();

        List qresult = ((CrudAction) realAction).listByClass(clazz, select.dependFrom(),null);

        CrudAction.MapField mapF = ((CrudAction) realAction).getMappedField(clazz);

        for (Object opt : qresult) {
            Object key = null;
            Object label = null;
            Map<String, Object> attributes = null;
            try {
                key = mapF.keyF.get(opt);
                label = mapF.labelF.get(opt);
                if (mapF.attributesF.size() > 0) {
                    attributes = new HashMap<>();
                    for (Field attF : mapF.attributesF) {
                        attributes.put(attF.getName(), attF.get(opt));
                    }
                }
            } catch (IllegalArgumentException ex) {
            } catch (IllegalAccessException ex) {
            }
            result.append(writeOption(label, key, fieldValue, stack, attributes));
        }

    }

    private void appendOnChangeEventLogic(StringBuffer result, String selectId, String cardId, String uiid) {
        result.append("$('#" + selectId + "').change(function(){");
        AsincInfo asincInfo = (AsincInfo) field.getAnnotation(AsincInfo.class);
        if (asincInfo != null) {
            result.append("try{");
            result.append(cardId + "_show_info('" + fieldName + "',$(this).val(),'" + targetClass.getName() + "','" + uiid + "');");
            result.append("}catch(error){alert(error);}");
        }

        List<String> activeOnChange = StringUtil.stringToList(select.activeOnChange());
        if (activeOnChange.size() > 0) {
            for (String toCall : activeOnChange) {
                result.append("try{");
                result.append(cardId + "_active_" + targetClass.getSimpleName() + "_" + toCall + "('" + fieldName + "',$(this).val(),'" + targetClass.getName() + "','" + uiid + "');");
                result.append("}catch(error){alert(error);}");
            }
        }
        result.append("});");
    }

    private void appendActiveOnChangeLogic(StringBuffer result, ValueStack stack, List startValue_option, String selectId, String cardId, String uiid) {
        boolean append = select.append();
        if (!select.dependFrom().equals("nill")) {
            result.append("function " + cardId + "_active_" + targetClass.getSimpleName() + "_" + fieldName + "(caller,value,targetClass,uiid){");
            result.append("var waiting='" + select.dependFrom() + "';");
            result.append("if(waiting==caller){");
            result.append("$.ajax({");
            result.append("url: \"" + stack.findValue("calcAction('asincSelectQuery','crude',null)") + "\",");
            result.append("dataType: \"json\",");
            result.append("data: {fieldValue: value,fieldName:\"" + fieldName + "\",className:\"" + targetClass.getName() + "\"},");
            result.append("success: function( data ) {");
            result.append("var options = '';");
            if (startValue_option != null) {
                result.append("options +='<option value=\"" + startValue_option.get(1) + "\">" + startValue_option.get(0) + "</option>';");
            }
            result.append("for (var i=0; i<data.length; i++) {");
            if (append) {
                result.append("options +='<option value=\"' + data[i].value + '\">'+data[i].value+' - '+ data[i].label + '</option>';");
            } else {
                result.append("options +='<option value=\"' + data[i].value + '\">' + data[i].label + '</option>';");
            }
            result.append("}");
            result.append("$('#" + selectId + "').html(options);");
            result.append("},");
            result.append("error : function (richiesta,stato,errori) {");
            result.append("alert(\"Error: \"+errori);");
            result.append("}");
            result.append("})");
            result.append("};");
            result.append("};");
        }
    }

    private StringBuffer writeOption(Object multiple, Object value, Object actual, ValueStack stack, Map<String, Object> attributes) throws IOException {
        StringBuffer html = new StringBuffer();
        if (value != null && multiple != null) {
            String label = null;
            String[] attrs = null;
            if ((multiple instanceof String[])) {
                attrs = (String[]) multiple;
                label = attrs[0];
            } else {
                label = multiple + "";
            }
            html.append("<option value='");
            html.append(value.toString());
            html.append("'");
            if (attributes == null) {
                if (attrs != null) {
                    for (int i = 1; i < attrs.length; i++) {
                        html.append(" attr" + i + "='" + attrs[i] + "'");
                    }
                }
            } else {
                for (String nomeAtt : attributes.keySet()) {
                    html.append(nomeAtt + "='" + attributes.get(nomeAtt) + "'");
                }
            }
            if (actual != null && actual.toString().trim().equals(value.toString().trim())) {
                html.append(" selected");
            }
            html.append(">");
            if (label instanceof String) {
                if (select.append()) {
                    html.append(value.toString() + " - " + I18n.getText(targetClass.getSimpleName() + "." + label, label.toString(), stack));
                } else {
                    html.append(I18n.getText(targetClass.getSimpleName() + "." + label, label.toString(), stack));
                }
            }
            html.append("</option>");
        }
        return html;
    }

    public StringBuffer renderView(ValueStack stack, Object fieldValue) throws IOException {
        StringBuffer html = new StringBuffer();
        html.append(writeLabel(null, stack, false));
        String rule = select.viewRule();
        if (rule.equals("nill")) {
            rule = select.map();
        }
        html.append(drawDefaultView(targetClass, field, fieldValue, toview != null ? toview.masked() : false, rule, stack, getCardId(stack) + "_right_view_row"));
        html = appendFieldParagraph(html, stack);
        return html;
    }

    public StringBuffer renderCell(ValueStack stack, Object fieldValue) throws IOException {
        StringBuffer html = new StringBuffer();
        html.append(drawDefaultView(targetClass, field, fieldValue, toview != null ? toview.masked() : false, select.viewRule(), stack, ""));
        return html;
    }

}
