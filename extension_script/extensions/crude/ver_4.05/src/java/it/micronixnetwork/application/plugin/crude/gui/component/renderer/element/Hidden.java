package it.micronixnetwork.application.plugin.crude.gui.component.renderer.element;

import it.micronixnetwork.application.plugin.crude.gui.component.renderer.FieldRenderer;

import java.io.IOException;
import java.lang.reflect.Field;

import com.opensymphony.xwork2.util.ValueStack;

public class Hidden extends FieldRenderer {

    public Hidden(Class targetClass, String fieldName, Field field) {
	super(targetClass, fieldName, field);
    }

    @Override
    public StringBuffer renderInput(ValueStack stack,Object fieldValue) throws IOException{
	StringBuffer html=new StringBuffer();
	html.append("<input type=\"hidden\" name=\"objState['" + fieldName + "']\" value=\"" + fieldValue+ "\">");
	return html;
    }

    @Override
    public StringBuffer renderView(ValueStack stack,Object fieldValue) throws IOException {
	return new StringBuffer();
    }

    
    @Override
    public StringBuffer renderCell(ValueStack stack,Object fieldValue) throws IOException {
	return new StringBuffer();
    }


   


}
