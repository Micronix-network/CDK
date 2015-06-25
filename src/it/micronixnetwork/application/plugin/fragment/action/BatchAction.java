package it.micronixnetwork.application.plugin.fragment.action;

import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.struts2.action.CardAction;
import it.micronixnetwork.gaf.struts2.action.ShellAction;
import it.micronixnetwork.gaf.util.StringUtil;

public class BatchAction extends ShellAction {

    private String batchName;
    private String dir;
    private Boolean showError;

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public void setShowError(Boolean showError) {
        this.showError = showError;
    }

    public Boolean getShowError() {
        return showError;
    }
    
    
    
    public String exe() throws ApplicationException {
        if(StringUtil.EmptyOrNull(dir)){
            setPath(getApplicationPath()+"/WEB-INF/view/batch/"+batchName);
        }else{
            setPath(dir+"/"+batchName);
        }
        return super.exe();
    }
}
