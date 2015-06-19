package it.micronixnetwork.application.plugin.crude.gui.ftl;

import it.micronixnetwork.application.plugin.crude.gui.component.InputObjId;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.util.ValueStack;

public class CRUDEModels {
    
    protected ValueStack stack;
    protected HttpServletRequest req;
    protected HttpServletResponse res;
    
    private FilterFormModel filterForm;
    
    private ResultListModel resultList;
    
    private InputFormModel inputForm;
    
    private InputObjectIdModel inputObjId;
     
   
    public CRUDEModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res){
        this.stack = stack;
        this.req = req;
        this.res = res;
    }
    
    public FilterFormModel getFilterForm() {
        if (filterForm == null) {
            filterForm = new FilterFormModel(stack, req, res);
        }
     
        return filterForm;
    }
    
    public ResultListModel getResultList() {
	if (resultList == null) {
	    resultList = new ResultListModel(stack, req, res);
        }
	return resultList;
    }
    
    
    public InputFormModel getInputForm() {
	if (inputForm == null) {
	    inputForm = new InputFormModel(stack, req, res);
        }
	return inputForm;
    }
    
    public InputObjectIdModel getInputObjId() {
	if (inputObjId == null) {
	    inputObjId = new InputObjectIdModel(stack, req, res);
        }
	return inputObjId;
    }
    

}
