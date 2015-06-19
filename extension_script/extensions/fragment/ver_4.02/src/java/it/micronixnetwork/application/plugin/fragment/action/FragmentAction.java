package it.micronixnetwork.application.plugin.fragment.action;

import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.struts2.action.CardAction;

public class FragmentAction extends CardAction{
   
	public String getFragment(){
	    String fragment=getCardParam("fragment", false);
	    return fragment!=null?fragment:"index";
	}
    
    	protected String exe() throws ApplicationException {
		return SUCCESS;
	}
    	
}
