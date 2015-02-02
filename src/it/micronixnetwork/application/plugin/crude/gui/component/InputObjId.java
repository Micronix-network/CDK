package it.micronixnetwork.application.plugin.crude.gui.component;

import it.micronixnetwork.application.plugin.crude.helper.Format;

import java.io.Writer;
import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsException;

import com.opensymphony.xwork2.util.ValueStack;

public class InputObjId extends CrudView {


    public InputObjId(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
	super(stack,req,res);
    }

    public boolean end(Writer writer, String body) {
	try {
		super.end(writer, body, false);
		writer.write("<input");
		writeBasicAttribute(writer);
		Object objId = (Object) stack.findValue(getVar());
		writer.write(" type=\"hidden\"");
		if (objId != null) {
		    if (Format.SCALAR.contains(objId.getClass())) {
			writer.write(" pk_id=\"" + objId.toString() + "\"");
		    } else {
			Field[] fields = objId.getClass().getDeclaredFields();
			String cardId = (String) stack.findValue("cardId");
			for (Field field : fields) {
			    if (field.getModifiers() == 1) {
				Object value = null;
				try {
				    value = field.get(objId);
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				}
				if (value != null) {
				    writer.write(" pk_" + field.getName() + "=\"" + value.toString() + "\"");
				}
			    }
			}
		    }
		}
		writer.write(">");
		writer.write("</input>");
	} catch (Exception e) {
		throw new StrutsException(e);
	} finally {
		popComponentStack();
	}
	return false;
}

}
