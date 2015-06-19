package it.micronixnetwork.application.plugin.fragment.action;

import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.struts2.action.CardAction;
import it.micronixnetwork.gaf.struts2.action.ShellAction;

public class BatchAction extends ShellAction {

    private String batchName;

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String exe() throws ApplicationException {
        setPath(getApplicationPath()+"/WEB-INF/view/batch/"+batchName);
        return super.exe();
    }
}
