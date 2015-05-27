package it.micronixnetwork.application.plugin.crude.gui.component;

import it.micronixnetwork.application.plugin.crude.annotation.Computed;
import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.ToList;
import it.micronixnetwork.application.plugin.crude.helper.FieldUtil;
import it.micronixnetwork.application.plugin.crude.helper.Format;
import it.micronixnetwork.gaf.domain.RoledUser;
import it.micronixnetwork.gaf.service.SearchResult;
import it.micronixnetwork.gaf.util.CodeGenerator;
import it.micronixnetwork.gaf.util.ObjectUtil;
import it.micronixnetwork.gaf.util.StringUtil;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsException;

import com.opensymphony.xwork2.util.ValueStack;
import it.micronixnetwork.gaf.service.OrderedSearchResult;
import java.util.ArrayList;

public class ResultListTable extends CrudView {

    private boolean abyCheck;

    protected String iterable;

    private String resultMessage;

    public ResultListTable(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
	super(stack, req, res);
    }

    public void setAbyCheck(String abyCheck) {
	this.abyCheck = "true".equals(abyCheck);
    }

    public void setResultMessage(String resultMessage) {
	this.resultMessage = resultMessage;
    }

    public void setIterable(String iterable) {
	this.iterable = iterable;
    }

    @Override
    public boolean start(Writer out) {
	return true;
    }

    protected void renderBody(Writer writer) throws IOException {
	SearchResult values = null;
	
	String cardId = (String) stack.findValue("cardId");

	List<String> orderby = new ArrayList<String>();

	if (iterable != null) {
	    values = (SearchResult) findValue(iterable);
	}
        
        if(values instanceof OrderedSearchResult){
            orderby = ((OrderedSearchResult)values).getOrderFields();
        }            

	if (values != null) {

	    Map<String, Field> fields = FieldUtil.retriveWorkFields(getPrototypeClass());

	    RoledUser user = (RoledUser) stack.findValue("user");
	    String[] roles = user.getRoles();

	    writer.write("<thead>");
	    writer.write("<tr>");
	    if (abyCheck) {
		writer.write("<th class=\"\" align=\"center\" style=\"\">");
		writer.write("<span id=\"" + getId() + "_sel_all_button\" style=\"\" class=\"" + cardId + "_image_btn "+cardId+"_selall_btn\"><span>Sel</span></span>");
		writer.write("</th>");
	    }
	    int colSpan = 0;
	    for (String field_key : fields.keySet()) {
		Field field = fields.get(field_key);
		ToList sfa = field.getAnnotation(ToList.class);
		if (sfa != null) {
		    List sfaRoles = StringUtil.stringToList(sfa.roles());
		    if (sfa.listed() && checkRole(roles, sfaRoles) && !sfa.hidden()) {
			drawTH(writer, sfa, orderby, field_key);
			colSpan++;
		    }
		}
	    }
	    writer.write("</tr>");
	    writer.write("</thead>");

	    writer.write("<tbody>");
	    for (Object row : values.getResult()) {

		stack.push(row);
		writer.write("<tr class=\"" + getId() + "_resultRow\"");

		Field primaryKey = getPrimaryKeyField(getPrototypeClass());

		if (primaryKey != null) {
		    if (primaryKey.isAnnotationPresent(Id.class)) {
			Object value = ObjectUtil.getFieldValue(primaryKey, row);
			if (value != null) {
			    writer.write(" pk_" + primaryKey.getName() + "=\"" + value.toString() + "\"");
			}
		    }
		}

		writer.write(">");
		if (abyCheck) {
		    writer.write("<td align=\"center\" class=\"\" style=\"width:30px;max-widht:30px\">");
		    String random_id = CodeGenerator.createCode();
		    writer.write("<div class=\"roundedOne\">");
		    writer.write("<input type=\"checkbox\" class=\"" + getId() + "_selectable\" value=\"" + row.toString() + "\" name=\"todelete\" id=\"" + getId() + "_" + random_id
			    + "_del\" style=\"\"/><label for=\"" + getId() + "_" + random_id + "_del\">&nbsp;</label>");
		    writer.write("</div>");
		    writer.write("</td>");
		}
		
		for (String field_key : fields.keySet()) {
			Field field = fields.get(field_key);
			Object fieldValue = FieldUtil.retriveFieldValue(field_key, row);
			Computed comp=field.getAnnotation(Computed.class);
			if(comp!=null && fieldValue==null){
			    String cRule=comp.value();
			    Object toAssign=findValue(cRule);
			    if(toAssign!=null){
				stack.setValue(field_key, toAssign);
			    }  
			}
		}

		for (String field_key : fields.keySet()) {
		    Field field = fields.get(field_key);
		    Object fieldValue = FieldUtil.retriveFieldValue(field_key, row);
		    ToList sfa = field.getAnnotation(ToList.class);
		    FieldStyleDirective fdir = field.getAnnotation(FieldStyleDirective.class);
		    String cellStyle = "";
		    if (fdir != null) {
			cellStyle=fdir.tableCellStyle();
			Object ruleStyle = stack.findValue(fdir.tableCellStyleRule());
			if(ruleStyle!=null){
			    cellStyle+=";"+ruleStyle;
			}
		    }
		    if (sfa != null) {
			List sfaRoles = StringUtil.stringToList(sfa.roles());
			if (sfa.listed() && checkRole(roles, sfaRoles) && !sfa.hidden()) {
			    String rule = sfa.cellRule();
			    String drag = sfa.draggable();
			    boolean down = sfa.downlink();
			    String to_draw = processField(field, fieldValue, rule);
			    writer.write("<td name=\"" + field_key + "\" class=\"" + getId() + " click_cell " + drag);
			    if (fieldValue != null && !fieldValue.toString().trim().equals("")) {
				if (down) {
				    writer.write(" down_link");
				}
				if (!"".equals(drag)) {
				    writer.write(" draggable_cell\" toDrop=\"" + fieldValue);
				}
			    }
			    writer.write("\" style=\"" + cellStyle + "\">");
			    writer.write(to_draw);
			    writer.write("</td>");
			}
		    }
		}

		writer.write("</tr>");
		stack.pop();
	    }
	    if(values.getRecordNumber()==0){
		drawBlankRow(writer,fields,roles);
	    }
	    writer.write("</tbody>");
	}
    }
    
    private void drawBlankRow(Writer writer,Map<String, Field> fields,String[] roles) throws IOException {
	writer.write("<tr style=\"background-color:transparent\">");
	if (abyCheck) {
	    writer.write("<td style=\"width:30px;max-widht:30px\">");
	    writer.write("</td>");
	}

	for (String field_key : fields.keySet()) {
	    Field field = fields.get(field_key);
	    ToList sfa = field.getAnnotation(ToList.class);
	    FieldStyleDirective fdir = field.getAnnotation(FieldStyleDirective.class);
	    String cellStyle = "";
	    if (fdir != null) {
		cellStyle = fdir.tableCellStyle();
	    }
	    if (sfa != null) {
		List sfaRoles = StringUtil.stringToList(sfa.roles());
		if (sfa.listed() && checkRole(roles, sfaRoles) && !sfa.hidden()) {
		    writer.write("<td style=\"" + cellStyle + ";background-color:transparent\">");
		    writer.write("</td>");
		}
	    }
	}

	writer.write("</tr>");
	
    }

    private String processValue(Field field, Object paramValue, String rule) throws IOException {
	StringBuffer html = new StringBuffer();
	String toView = Format.formatValue(paramValue, field, stack);
	String cssClass = "";
	if (!rule.equals("nill")) {
	    Object ognlComputed = findValue(rule);
	    if (ognlComputed != null) {
		if (ognlComputed instanceof LinkedHashMap) {
		    Object toShow = ((LinkedHashMap) ognlComputed).get(paramValue);
		    String clazz = "";
		    if (toShow instanceof List) {
			cssClass = (String) ((List) toShow).get(1);
			toView = (String) ((List) toShow).get(0);
		    } else {
			toView = (String) toShow;
		    }
		} else {
		    toView = Format.formatValue(ognlComputed, field, stack);
		}
	    }
	}
	html.append("<span class=\"" + cssClass + "\"><span>" + toView + "</span></span>");
	return html.toString();
    }

    private String processField(Field field, Object paramValue, String rule) throws IOException {
	StringBuffer html = new StringBuffer();
	if (paramValue instanceof Collection) {
	    html.append("<ul>");
	    Collection iterable = (Collection) paramValue;
	    for (Object obj : iterable) {
		html.append("<li>" + processValue(field, obj, rule) + "</li>");
	    }
	    html.append("</ul>");
	    return html.toString();
	} else {
	    return processValue(field, paramValue, rule);
	}
    }

    private void drawTH(Writer writer, ToList sfa, List<String> orderby, String field_key) throws IOException {
	String cardId = (String) stack.findValue("cardId");
	String tbAlias = (String) stack.findValue("tbAlias");
	if (tbAlias == null)
	    tbAlias = "";
	String label = getText(getPrototypeClass().getSimpleName() + "." + field_key, field_key);
	writer.write("<th name=\"" + field_key + "\">");
	writer.write(label);
	boolean ordered = false;
	for (String ordstr : orderby) {
	    if (ordstr.equals(tbAlias + "." + field_key + " desc")) {
		writer.write("<span class=\"" + cardId + "_order-pin icon-arrow-down");
		if (sfa != null && sfa.ordered()) {
		    writer.write(" " + cardId + "_toorder\" toorder=\"" + field_key + "\" direction=\"asc\">");
		} else {
		    writer.write("\">");
		}
		writer.write("</span>");
		ordered = true;
	    }
	    if (ordstr.equals(tbAlias + "." + field_key + " asc")) {
		writer.write("<span class=\"" + cardId + "_order-pin icon-arrow-up");
		if (sfa != null && sfa.ordered()) {
		    writer.write(" " + cardId + "_toorder\" toorder=\"" + field_key + "\" direction=\"none\">");
		} else {
		    writer.write("\">");
		}
		writer.write("</span>");
		ordered = true;
	    }
	}

	if (!ordered) {
	    if (sfa != null && sfa.ordered()) {
		writer.write("<span class=\"" + cardId + "_order-pin icon-radio-unchecked " + cardId + "_toorder\" toorder=\"" + field_key + "\" direction=\"desc\"></span>");
	    }
	}
	writer.write("</th>");
    }

    private void drawPagination(Writer writer, String[] roles, SearchResult sResult) throws IOException {
	long total = (sResult.getRecordNumber() == null) ? 0 : sResult.getRecordNumber();
	long pageSize = (sResult.getRecordForPage() == null) ? 1000 : sResult.getRecordForPage();
	long actualPage = (sResult.getPage() == null || sResult.getPage() == 0) ? 1 : sResult.getPage();

	long pages = (long) Math.floor((double) total / (double) pageSize);
	long mod = (long) Math.floor((double) total % (double) pageSize);
	if (mod != 0) {
	    pages += 1;
	}
	
	String cardId = (String) stack.findValue("cardId");

	writer.write("<div id=\"" + cardId + "_pagination_zone\"");
	writer.write(" class=\"" + cardId + "_pagination\"");
	writer.write(">");
	writer.write("<table><tr>");

	// informazioni numero totale elementi paginati

	writer.write("<td class=\"" + cardId + "_pagination_msg\">");
	writer.write(getText(resultMessage, resultMessage, new String[] { String.valueOf(total) }));
        if (pages > 1){
            long from=((actualPage-1)*pageSize)+1;
            long to=actualPage<pages?((actualPage)*pageSize):total;
            writer.write("<span class=\"" + cardId + "_fromTo\">");
            writer.write(getText("paginazione.fromto", "- from <b>{0}</b> to <b>{1}</b>", new String[] { String.valueOf(from),String.valueOf(to) }));
            writer.write("</span>");
        }
	writer.write("</td>");

	// Comando di back

	if (pages > 1) {
	    writer.write("<td class=\"" + cardId + "_pagination_arrow\">");
	    writer.write("<button class=\"prec btn btn-mini btn-primary");
	    if (actualPage > 1) {
		writer.write("\" onClick=\"javascript:changePage_" + getId() + "(" + (actualPage - 1) + ");\"");
		writer.write(">");
	    } else {
		writer.write(" disabled\">");
	    }
	    writer.write("<span>" + getText("paginazione.prec", "<") + "</span></button>");

	    writer.write("<button style=\"margin-left:5px\" class=\"succ btn btn-mini btn-primary");
	    if (actualPage < pages) {
		writer.write("\" onClick=\"javascript:changePage_" + getId() + "(" + (actualPage + 1) + ");\"");
		writer.write(">");
	    } else {
		writer.write(" disabled\">");
	    }
	    writer.write("<span>" + getText("paginazione.succ", ">") + "</span></button>");
	    writer.write("</td>");
	}
	writer.write("</tr>");

	writer.write("<tr>");
	// Combo di paginazione
	writer.write("<td style=\"text-align:center;\" colspan=\"2\">");
	if (pages > 1) {
	    writer.write(getText("paginazione.page", "Pagina"));
	    writer.write("<select onchange=\"changePage_" + getId() + "(this.options[this.selectedIndex].value);\">");
	    for (int i = 1; i <= pages; i++) {
		if (i == actualPage) {
		    writer.write("<option value=\"" + i + "\" selected=\"selected\">" + i + "</option>");
		} else {
		    writer.write("<option value=\"" + i + "\">" + i + "</option>");
		}
	    }
	    writer.write("</select>");
	    writer.write(" " + getText("paginazione.page.di", "di") + " " + pages);
	}
	writer.write("</td>");
	writer.write("</tr>");

	writer.write("</table>");
	writer.write("</div>");
    }

    @Override
    public boolean end(Writer writer, String body) {
	String cardId = (String) stack.findValue("cardId");
	try {
	    Class rowType = getPrototypeClass();
	    if (rowType != null) {
		super.end(writer, body, false);
		writer.write("<div id=\"" + getId() + "_list_zone\" class=\""+cardId+"_list_zone\">");
		writer.write("<table");
		writeBasicAttribute(writer);
		writer.write(" cellpadding=\"0\" cellspacing=\"0\"  width=\"100%\">");
		renderBody(writer);
		writer.write("</table>");
		writer.write("</div>");
		RoledUser user = (RoledUser) stack.findValue("user");
		String[] roles = user.getRoles();
		SearchResult sResult = (SearchResult) findValue(iterable);
		drawPagination(writer, roles, sResult);
	    }
	} catch (Exception e) {
	    throw new StrutsException(e);
	} finally {
	    popComponentStack();
	}
	return false;
    }

}
