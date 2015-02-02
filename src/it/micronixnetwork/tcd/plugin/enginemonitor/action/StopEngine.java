package it.micronixnetwork.tcd.plugin.enginemonitor.action;

import it.micronixnetwork.gaf.exception.ApplicationException;

public class StopEngine extends MonitorAction {
    
    protected String exe() throws ApplicationException {
	stopStopEngine();
	message="success";
	return SUCCESS;
    }

}
