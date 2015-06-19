package it.micronixnetwork.application.plugin.crude.gui.component;

import it.micronixnetwork.application.plugin.crude.action.CrudAction;
import it.micronixnetwork.application.plugin.crude.annotation.Children;
import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.Group1Directive;
import it.micronixnetwork.application.plugin.crude.annotation.Group2Directive;
import it.micronixnetwork.application.plugin.crude.annotation.Group3Directive;
import it.micronixnetwork.application.plugin.crude.annotation.Owner;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.gui.component.renderer.FieldRenderer;
import it.micronixnetwork.application.plugin.crude.gui.component.renderer.RendererFactory;
import it.micronixnetwork.application.plugin.crude.helper.FieldUtil;
import it.micronixnetwork.application.plugin.crude.helper.Format;
import it.micronixnetwork.gaf.domain.RoledUser;
import it.micronixnetwork.gaf.util.ObjectUtil;
import it.micronixnetwork.gaf.util.StringUtil;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.util.ValueStack;

public class InputForm extends CrudView {

    private List<FieldRenderer> column1 = new ArrayList<FieldRenderer>();
    private List<FieldRenderer> column2 = new ArrayList<FieldRenderer>();
    private List<FieldRenderer> column3 = new ArrayList<FieldRenderer>();

    private static float rowk = 6;

    private String mod = CrudAction.OP_INSERT;

    public void setMod(String mod) {
	this.mod = mod;
    }

    private RendererFactory rendererFactory;

    public InputForm(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
	super(stack, req, res);
	rendererFactory = RendererFactory.getInstance();
    }

   
   
    protected void renderBody(Writer writer) throws IOException {
	Object toView = stack.findValue(getVar());
	// Rendering GUI oggetto principale
	writeFormGui(toView, writer);
    }

    private void writeFormGui(Object toView, Writer writer) throws IOException {
	RoledUser user = (RoledUser) stack.findValue("user");

	if (toView == null)
	    return;

	Class targetClass = toView.getClass();

	// Recupero informazioni di visualizzazioni
	Group1Directive grp1dir = (Group1Directive) targetClass.getAnnotation(Group1Directive.class);
	Group2Directive grp2dir = (Group2Directive) targetClass.getAnnotation(Group2Directive.class);
	Group3Directive grp3dir = (Group3Directive) targetClass.getAnnotation(Group3Directive.class);

	String opt = (String) stack.findValue("operation");

	if (targetClass != null) {
	    Map<String, Field> fields = FieldUtil.retriveWorkFields(toView.getClass());
	    String cardId = (String) stack.findValue("cardId");

	    Integer ptype = (Integer) stack.findValue("ptype");

	    if (ptype == null)
		ptype = 0;

	    writer.write("<input type=\"hidden\" name=\"ptype\" value=\"" + ptype + "\"/>");
	    for (String field_key : fields.keySet()) {

		boolean skip = false;

		Field field = fields.get(field_key);
		Object value = FieldUtil.retriveFieldValue(field_key, toView);

		FieldStyleDirective fdirective = field.getAnnotation(FieldStyleDirective.class);
		Children children = field.getAnnotation(Children.class);

		if (children != null) {
		    skip = true;
		}

		if (!skip) {
		    if (CrudAction.OP_UPDATE.equals(mod) && field.isAnnotationPresent(Id.class)) {
			writer.write("<input type=\"hidden\"   name=\"objState['" + field_key + "']\" value=\"" + (value != null ? value : "") + "\"/>");
		    }else{
		    if (renderizable(field, user.getRoles(), mod)) {
			FieldRenderer renderer = rendererFactory.produce(targetClass, field_key, field);
			if (renderer != null) {
			    int column = 1;
			    if (fdirective != null) {
				column = fdirective.group();
			    }
			    switch (column) {
			    case 1:
				column1.add(renderer);
				break;
			    case 2:
				column2.add(renderer);
				break;
			    case 3:
				column3.add(renderer);
				break;
			    default:
				column1.add(renderer);
				break;
			    }
			}
		    }
                    }
		}
	    }

	    String iniWidth = "dim50";

	    boolean emp1 = column1.size() > 0;
	    boolean emp2 = column2.size() > 0;
	    boolean emp3 = column3.size() > 0;

	    if (emp1 && emp2 && emp3) {
		iniWidth = "dim33";
	    }

	    if ((emp1 != emp2) != emp3) {
		iniWidth = "dim100";
	    }

	    stack.push(toView);

	    if (column1.size() > 0) {
		writer.write("<div class=\""+cardId+"_cbp-mc-column col1 " + iniWidth + "\"");
		if (grp1dir != null) {
		    writer.write(" style=\"" + grp1dir.style() + "\">");
		    if (!StringUtil.EmptyOrNull(grp1dir.title())) {
			writer.write("<h4>" + getText(grp1dir.title(), grp1dir.title()) + "</h4>");
		    }
		} else {
		    writer.write(">");
		}
		for (FieldRenderer rend : column1) {
		    writer.write(drawRenderer(rend, toView, stack));
		}
		writer.write("</div>");
	    }
	    if (column2.size() > 0) {
		writer.write("<div class=\""+cardId+"_cbp-mc-column col2 " + iniWidth + "\"");
		if (grp2dir != null) {
		    writer.write(" style=\"" + grp2dir.style() + "\">");
		    if (!StringUtil.EmptyOrNull(grp2dir.title())) {
			writer.write("<h4>" + getText(grp2dir.title(), grp2dir.title()) + "</h4>");
		    }
		} else {
		    writer.write(">");
		}
		for (FieldRenderer rend : column2) {
		    writer.write(drawRenderer(rend, toView, stack));
		}
		writer.write("</div>");
	    }
	    if (column3.size() > 0) {
		writer.write("<div class=\""+cardId+"_cbp-mc-column col3 " + iniWidth + "\"");
		if (grp3dir != null) {
		    writer.write(" style=\"" + grp3dir.style() + "\">");
		    if (!StringUtil.EmptyOrNull(grp3dir.title())) {
			writer.write("<h4>" + getText(grp3dir.title(), grp3dir.title()) + "</h4>");
		    }
		} else {
		    writer.write(">");
		}

		for (FieldRenderer rend : column3) {
		    writer.write(drawRenderer(rend, toView,stack));
		}
		writer.write("</div>");
	    }
	    stack.pop();
	}
    }

    private String drawRenderer(FieldRenderer rend,Object toView, ValueStack stack) throws IOException {
	StringBuffer result = new StringBuffer();
	Object value = FieldUtil.retriveFieldValue(rend.getFieldName(), toView);
	if (CrudAction.OP_VIEW.equals(mod)) {
	    result.append(rend.renderView(stack,value));
	} else {
	    Field field = rend.getField();
	    ToInput input = field.getAnnotation(ToInput.class);
	    if (input != null && !input.active()) {
		result.append(rend.renderView(stack,value));
	    } else {
		result.append(rend.renderInput(stack,value));
	    }
	}
	return result.toString();
    }

    private boolean renderizable(Field field, String[] userRoles, String mod) {
	ToInput input = field.getAnnotation(ToInput.class);
	ToView toView = field.getAnnotation(ToView.class);
	Owner owner = field.getAnnotation(Owner.class);

	// Viene prodotta una GUI per i campi marcati owner solo nel caso si
	// faccia parte degli adminRoles
	if (owner != null) {
	    String roles_s = owner.adminRoles();
	    if (!StringUtil.checkStringExistenz(userRoles, roles_s)) {
		return false;
	    }
	}

	// Se siamo in view il field deve essere annotato ToView
	if (CrudAction.OP_VIEW.equals(mod) && toView == null)
	    return false;

	// Se siamo in update o in insert il field deve essere annotato ToInput
	if (!CrudAction.OP_VIEW.equals(mod) && input == null)
	    return false;

	// ToInput e siamo in insert o update
	if (input != null && !CrudAction.OP_VIEW.equals(mod)) {
	    // Controllo delle autorizzzioni di input
	    if (!StringUtil.checkStringExistenz(userRoles, input.roles())) {
		return false;
	    }
	}

	// ToView e siamo in view
	if (toView != null && CrudAction.OP_VIEW.equals(mod)) {
	    // Controllo delle autorizzzioni di view
	    if (!StringUtil.checkStringExistenz(userRoles, toView.roles())) {
		return false;
	    }
	}

	return true;
    }

    @Override
    public boolean end(Writer writer, String body) {
	try {
	    Object toView = stack.findValue(getVar());
	    if (toView != null) {
		Class targetClass = toView.getClass();
		String cardId = (String) stack.findValue("cardId");

		writer.write("<script type=\"text/javascript\">");
		writer.write("function " + cardId + "_edit_fillForeignKeyData($form){");
		Field[] fields = targetClass.getDeclaredFields();
		for (Field field : fields) {
		    if (field.isAnnotationPresent(Id.class)) {
			Object value = ObjectUtil.getFieldValue(field, toView);
			value = value != null ? value : "";
			writer.write("addHiddenToForm($form,\"filters['parent." + field.getName() + "']\",'" + Format.formatValue(value, field,stack) + "');");
		    }
		}
		writer.write("}");
		writer.write("</script>");

		// Disegno FORM
		writer.write("<form");
		writeBasicAttribute(writer);
		writer.write(">");
		// Rendering GUI oggetto principale
		writeFormGui(toView, writer);
		writer.write("</form>");
		
		// Disegno liste children nel caso di modifica
		
		Map<String,String> childNames=(Map<String,String>)stack.findValue("childNames");
		
		if(childNames!=null){
		    for (String childFieldName : childNames.keySet()) {
			Field childField=null;
			try{
			    childField=FieldUtil.retriveField(childFieldName, targetClass);
			    toView=FieldUtil.retriveObjectOfField(childFieldName, toView);
			}catch(Exception ex){
			    
			}
			if(childField!=null){
			    StringBuffer ctable = produceChildrenTable(cardId, childField, toView, mod);
			    writer.write(ctable.toString());  
			}	
		    }  
		}
		return false;
	    }
	} catch (IOException e) {
	    error("Script rendering error", e);
	} catch (IllegalAccessException e) {
	    error("Script rendering error", e);
	}
	return false;
    }

    private StringBuffer produceChildrenTable(String cardId, Field childField, Object toView, String mod) throws IOException, IllegalAccessException {
	RoledUser user = (RoledUser) stack.findValue("user");

	StringBuffer table = new StringBuffer();

	ParameterizedType childSetType = (ParameterizedType) childField.getGenericType();
	Class<?> childType = (Class<?>) childSetType.getActualTypeArguments()[0];

	table.append("<div class=\""+cardId+"_form_title\" style=\"margin-top:10px;\">" + getText(childType.getSimpleName() + ".list.title", childType.getSimpleName()) + "</div>");
	table.append("<table class=\""+cardId+"_children_table\">");

	Set children = (Set) childField.get(toView);

	if (children != null && children.size() > 0) {
	    Map<String, Field> fields = FieldUtil.retriveWorkFields(childType);
	    table.append("<thead>");
	    table.append("<tr>");
	    for (String field_key : fields.keySet()) {
		Field field = fields.get(field_key);
		if (field.isAnnotationPresent(ToView.class)) {
		    table.append("<th>");
		    table.append(getText(childType.getSimpleName() + "." + field_key, field.getName()));
		    table.append("</th>");
		}
	    }
	    if (mod.equals(CrudAction.OP_UPDATE)) {
		table.append("<th style=\"width:50px\"></th>");
		table.append("</tr>");
	    }
	    table.append("</thead>");

	    table.append("<tbody>");
	    for (Object row : children) {
		stack.push(row);

		table.append("<tr class=\"" + getId() + "_resultRow\"");

		Field primaryKey = getPrimaryKeyField(childType);

		if (primaryKey != null) {
		    if (primaryKey.isAnnotationPresent(Id.class)) {
			Object value = ObjectUtil.getFieldValue(primaryKey, row);
			if (value != null) {
			    table.append(" pk_" + primaryKey.getName() + "=\"" + value.toString() + "\"");
			}
		    }
		}
		table.append(">");
		for (String field_key : fields.keySet()) {
		    Field field = fields.get(field_key);
		    FieldStyleDirective fdir = field.getAnnotation(FieldStyleDirective.class);
		    String style = "";
		    if (fdir != null) {
			style = fdir.tableCellStyle();
		    }
		    if (field.isAnnotationPresent(ToView.class)) {
			Object paramValue = FieldUtil.retriveFieldValue(field_key, row);
			String cellContent = "";
			FieldRenderer renderer = rendererFactory.produce(childType, field_key, field);
			if (renderer != null) {
			    cellContent = renderer.renderCell(stack,paramValue).toString();
			}
			table.append("<td style=\"" + style + "\">" + cellContent + "</td>");

		    }
		}
		if (mod.equals(CrudAction.OP_UPDATE)) {
		    table.append("<td style=\"max-width:50px\">");
		    table.append("<button class=\"" + getId() + "_child_delete btn-mini btn btn-primary\">" + getText(childType.getSimpleName() + ".button.delete", "Delete") + "</button>");
		    table.append("</td>");
		}
		table.append("</tr>");
		stack.pop();
	    }
	    table.append("</tbody>");

	}
	table.append("</table>");
	return table;
    }

}
