package it.micronixnetwork.application.plugin.crude.gui.component;

import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.ToList;
import it.micronixnetwork.application.plugin.crude.helper.FieldUtil;
import it.micronixnetwork.gaf.domain.RoledUser;
import it.micronixnetwork.gaf.util.StringUtil;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsException;

import com.opensymphony.xwork2.util.ValueStack;

public class FilterForm extends CrudView {

    int filteredFields = 0;

    public FilterForm(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        super(stack, req, res);
    }

    protected void renderBody(Writer writer) throws IOException {
        Class targetClass = getPrototypeClass();

        if (targetClass != null) {
            Map<String, Field> fields = FieldUtil.retriveWorkFields(getPrototypeClass());
            writer.write("<tr>");

            String cardId = (String) stack.findValue("cardId");
            for (String field_key : fields.keySet()) {
                Field field = fields.get(field_key);
                if (processField(writer, field_key, field)) {
                    filteredFields++;
                }
            }

            writer.write("<td>");

            if (filteredFields > 0) {
                writer.write("<button class=\"btn btn-mini btn-success " + cardId + "_image_btn " + cardId + "_search_btn\" style=\"margin:0;margin-left:3px\" id=\"" + cardId + "_filter_button\"><span>" + getText("crude.label.filter", "Filter") + "</span></button>");
                writer.write("<button class=\"btn btn-mini btn-success " + cardId + "_image_btn " + cardId + "_reset_btn\" style=\"margin:0;margin-left:3px;\" id=\"" + cardId + "_reset_filter_button\"><span>" + getText("crude.label.reset", "Reset") + "</span></button>");
            }
            writer.write("</td>");
            writer.write("</tr>");
        }
    }

    private boolean processField(Writer writer, String fieldName, Field field) throws IOException {

        String cardId = (String) stack.findValue("cardId");

        RoledUser user = (RoledUser) stack.findValue("user");

        Class fieldType = field.getType();

        String inputClass = "text_input";
        if (fieldType == java.util.Date.class) {
            inputClass = "date_input ";
        }

        if (fieldType == Float.class || field.getType() == Double.class) {
            inputClass = "real_input ";
        }

        if (fieldType == Integer.class || field.getType() == Short.class || field.getType() == Long.class) {
            inputClass = "integer_input ";
        }

        if (field.isAnnotationPresent(ToList.class)) {
            ToList toList = field.getAnnotation(ToList.class);
            
            boolean append=toList.append();

            String defValue = !toList.defaultValue().equals("nill") ? toList.defaultValue() : "";

            if (Collection.class.isAssignableFrom(fieldType)) {
                fieldName = fieldName + ".id";
            }

            if (toList.hidden()) {
                writer.write("<input class=\"filter_field\" id=\"" + cardId + "_filter_" + fieldName + "\" type=\"hidden\" name=\"filters['" + fieldName + "']\" value=\"" + defValue + "\"/>");
                return false;
            }
            FieldStyleDirective fdir = field.getAnnotation(FieldStyleDirective.class);
            String cellStyle = "";
            if (fdir != null) {
                cellStyle = fdir.tableCellStyle();
            }
            List<String> roles = StringUtil.stringToList(toList.roles());
            boolean checkRole = checkRole(user.getRoles(), roles) || roles.size() == 0;
            if (toList.filtered() && checkRole && toList.fixValue().equals("nill")) {
                String rule = toList.filterRule();
                writer.write("<td class=\"" + cardId + "_filter_box\">");
                writer.write("<label>" + getText(getPrototypeSimpleName() + "." + fieldName, field.getName()) + ":" + "</label>");
                if (!rule.equals("nill")) {
                    Object values = findValue(rule);
                    if (values != null) {
                        if (values instanceof LinkedHashMap) {
                            writer.write("<select class=\"filter_field\" id=\"" + cardId + "_filter_" + fieldName + "\" name=\"filters['" + fieldName + "']\">");
                            writeOption(writer, "all", "", defValue,false);
                            for (Object value : ((LinkedHashMap) values).keySet()) {
                                Object label = ((LinkedHashMap) values).get(value);
                                if (label instanceof List) {
                                    label = ((List) label).get(0);
                                }
                                writeOption(writer, label, value, defValue,append);
                            }
                            writer.write("</select>");
                            writer.write("</td>");
                            return true;
                        }
                    }

                    if (field.isAnnotationPresent(Transient.class) && rule.matches("([^\\.]*)|([^\\.]*\\.[^\\.]*)|([^\\.]*\\.[^\\.]*\\.[^\\.]*)|([^\\.]*\\.[^\\.]*\\.[^\\.]*\\.[^\\.]*)")) {
                        writer.write("<input class=\"filter_field " + inputClass + "\"type=\"text\" id=\"" + cardId + "_filter_" + fieldName + "\"  name=\"filters['" + rule + "']\" style=\"" + cellStyle + "\" value=\"" + defValue + "\"/>");
                        writer.write("</td>");
                        return true;
                    }

                }
                writer.write("<input class=\"filter_field " + inputClass + "\" type=\"text\" id=\"" + cardId + "_filter_" + fieldName + "\"  name=\"filters['" + fieldName + "']\" style=\"" + cellStyle + "\" value=\"" + defValue + "\"/>");
                writer.write("</td>");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean end(Writer writer, String body) {
        super.end(writer, body);
        try {
            super.end(writer, body, false);
            writer.write("<table");
            writeBasicAttribute(writer);
            writer.write(" style=\"\")");
            writer.write(">");
            renderBody(writer);
            writer.write("</table>");
            if (filteredFields == 0) {
                try {
                    writer.write("<script type=\"text/javascript\">$('#" + getId() + "').hide();</script>");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            throw new StrutsException(e);
        } finally {
        }
        return false;
    }
    
    private void writeOption(Writer writer, Object label, Object value, Object actual,boolean append) throws IOException {
	if (value != null && label != null) {
	    writer.write("<option value='");
	    writer.write(value.toString());
	    writer.write("'");
	    if (actual != null && actual.toString().trim().equals(value.toString().trim())) {
		writer.write(" selected");
	    }
	    writer.write(">");
            if(append){
                writer.write(value.toString()+" - "+getText(getPrototypeSimpleName() + "." + label, "'" + label.toString() + "'"));
            }else{
                writer.write(getText(getPrototypeSimpleName() + "." + label, "'" + label.toString() + "'"));
            }
	    writer.write("</option>");
	}
    }

}
