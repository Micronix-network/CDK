package it.micronixnetwork.application.plugin.crude.gui.ftl;

import it.micronixnetwork.application.plugin.crude.gui.component.InputObjId;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.freemarker.tags.TagModel;

import com.opensymphony.xwork2.util.ValueStack;


public class InputObjectIdModel extends TagModel{
    
    private static final long serialVersionUID = 1L;
    
    
    public InputObjectIdModel(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        super(stack, req, res);
    }

    protected Component getBean() {
        return new InputObjId(stack, req, res);
    }

}
