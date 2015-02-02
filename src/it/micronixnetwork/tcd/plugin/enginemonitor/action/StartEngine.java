package it.micronixnetwork.tcd.plugin.enginemonitor.action;

import it.micronixnetwork.gaf.exception.ApplicationException;

public class StartEngine extends MonitorAction {
    
    protected String exe() throws ApplicationException {
	startEngine();
	message="success";
	return SUCCESS;
    }

}
