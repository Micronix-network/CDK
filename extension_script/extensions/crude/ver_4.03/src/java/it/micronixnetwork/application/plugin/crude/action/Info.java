package it.micronixnetwork.application.plugin.crude.action;

import it.micronixnetwork.application.plugin.crude.action.Get;
import it.micronixnetwork.gaf.domain.RoledUser;
import it.micronixnetwork.gaf.exception.ActionException;
import it.micronixnetwork.gaf.exception.ApplicationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Info extends Get {

	private static final long serialVersionUID = 1L;
	
	public Info() {
	    operation=OP_VIEW;
	}
	
}
