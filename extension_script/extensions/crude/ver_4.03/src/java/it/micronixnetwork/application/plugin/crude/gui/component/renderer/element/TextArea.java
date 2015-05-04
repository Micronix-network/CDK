package it.micronixnetwork.application.plugin.crude.gui.component.renderer.element;

import it.micronixnetwork.application.plugin.crude.annotation.renderer.TextAreaRenderer;
import it.micronixnetwork.application.plugin.crude.gui.component.renderer.FieldRenderer;

import java.io.IOException;
import java.lang.reflect.Field;

import com.opensymphony.xwork2.util.ValueStack;

public class TextArea extends FieldRenderer {
    
    private final TextAreaRenderer textArea;

    public TextArea(Class targetClass, String fieldName, Field field) {
	super(targetClass, fieldName, field);
	this.textArea=field.getAnnotation(TextAreaRenderer.class);
    }

    @Override
    public StringBuffer renderInput(ValueStack stack,Object fieldValue) throws IOException {
	StringBuffer html=new StringBuffer();
	int height = textArea.height();
	html.append(writeLabel(""+getCardId(stack)+"_full_label",stack,true));
	html.append("<textarea class=\"tooltip "+getCardId(stack)+"_right_input_area\" style=\"height: " + height + "px;"+getFieldStyle(field)+"\" name=\"objState['" + fieldName + "']\">" + (fieldValue != null ? fieldValue : "") + "</textarea>");
	return html;
    }
    
    public StringBuffer renderView(ValueStack stack,Object fieldValue) throws IOException{
	StringBuffer html=new StringBuffer();
	int height = textArea.height();
	html.append(writeLabel(""+getCardId(stack)+"_full_label",stack,false));
	html.append("<textarea readonly class=\"tooltip "+getCardId(stack)+"_right_input_area\" style=\"height: " + height + "px;"+getFieldStyle(field)+"\" name=\"objState['" + fieldName + "']\">" + (fieldValue != null ? fieldValue : "") + "</textarea>");
	return html;
    }

    @Override
    public StringBuffer renderCell(ValueStack stack,Object fieldValue) throws IOException {
	StringBuffer html=new StringBuffer();
	int height = textArea.height();
	html.append("<textarea readonly class=\"tooltip "+getCardId(stack)+"_right_input_area\" style=\"height: " + height + "px;"+getFieldStyle(field)+"\" name=\"objState['" + fieldName + "']\">" + (fieldValue != null ? fieldValue : "") + "</textarea>");
	return html;
    }

}
