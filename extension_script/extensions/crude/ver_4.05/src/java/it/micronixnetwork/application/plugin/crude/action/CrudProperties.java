package it.micronixnetwork.application.plugin.crude.action;

import it.micronixnetwork.application.plugin.crude.model.ViewModel;
import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.struts2.action.RetriveProperties;
import java.util.ArrayList;

public class CrudProperties extends RetriveProperties {

    private static final long serialVersionUID = 8592998471231040082L;
    
    private ArrayList<String> publischedObjects;

    public ArrayList<String> getPublischedObject() {
        return publischedObjects;
    }
    
    @Override
    protected String exe() throws ApplicationException {
        String result= super.exe();
        publischedObjects=new ArrayList<String>();
	for (String clazzName : getDomainObjects()) {
	    try {
		Class clazz=Thread.currentThread().getContextClassLoader().loadClass(clazzName);
		if(ViewModel.class.isAssignableFrom(clazz)){
		    publischedObjects.add(clazzName);
		}
	    } catch (ClassNotFoundException e) {
		debug(e);
	    }
	}
        return result;
    }
}
