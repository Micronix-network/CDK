package it.micronixnetwork.application.plugin.crude.gui.ftl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.TagLibrary;

import com.opensymphony.xwork2.util.ValueStack;

public class CRUDETagLibrary implements TagLibrary{
  
    public Object getFreemarkerModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new CRUDEModels(stack, req, res);
    }
     
    public List getVelocityDirectiveClasses() {
        return null;
    }
     
    

}
