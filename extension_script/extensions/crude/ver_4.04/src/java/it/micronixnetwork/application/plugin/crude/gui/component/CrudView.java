package it.micronixnetwork.application.plugin.crude.gui.component;

import it.micronixnetwork.gaf.struts2.gui.component.GAFGuiComponent;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.util.ValueStack;

public abstract class CrudView extends GAFGuiComponent {

    private String prototype;
    private Class prototypeClass;
    
    public void setPrototype(String prototype) {
	this.prototype = prototype;
    }

    public String getPrototype() {
	return prototype;
    }

    public String getPrototypeSimpleName() {
	if (prototype == null)
	    return "";
	return prototype.substring(prototype.lastIndexOf('.') + 1);
    }

    protected Class getPrototypeClass() {
	if (prototype == null)
	    return null;
	if (prototypeClass != null)
	    return prototypeClass;
	try {
	    if (factory != null) {
		prototypeClass = factory.getClassInstance(prototype);
	    }
	} catch (Exception e) {
	    error("prototype is void or not assignable", e);
	}
	return prototypeClass;
    }

    protected final ObjectFactory factory;

    public CrudView(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
	super(stack, req, res);
	if (stack != null) {
	    factory = (ObjectFactory) stack.findValue("factory");
	} else {
	    factory = null;
	}
    }
    
    protected void writeBasicAttribute(Writer writer) throws IOException{
	if (getId() != null) {
		writer.write(" id=\"" + getId() + "\"");
	}
	if (getStyle() != null) {
		writer.write(" style=\"" + getStyle() + "\"");
	}
	if (getClassName() != null) {
		writer.write(" class=\"" + getClassName() + "\"");
	}
}
 
    protected void writeOption(Writer writer, Object label, Object value, Object actual) throws IOException {
	if (value != null && label != null) {
	    writer.write("<option value='");
	    writer.write(value.toString());
	    writer.write("'");
	    if (actual != null && actual.toString().trim().equals(value.toString().trim())) {
		writer.write(" selected");
	    }
	    writer.write(">");
	    writer.write(getText(getPrototypeSimpleName() + "." + label, "'" + label.toString() + "'"));
	    writer.write("</option>");
	}
    }

    protected Field getPrimaryKeyField(Class type) {
	if (type == null)
	    return null;
	Field[] fields = type.getDeclaredFields();
	for (Field field : fields) {
	    if (field.isAnnotationPresent(Id.class)) {
		return field;
	    }
	}
	return null;
    }

    

}
