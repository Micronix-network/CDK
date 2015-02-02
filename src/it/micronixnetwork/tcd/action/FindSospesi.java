package it.micronixnetwork.tcd.action;

import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.struts2.action.CardAction;
import it.micronixnetwork.gaf.util.StringUtil;
import it.micronixnetwork.tcd.domain.TCDUser;

public class FindSospesi extends CardAction {

    private static final long serialVersionUID = 1L;

    private String jsCode = "";

    public String getJsCode() {
	return jsCode;
    }

    @Override
    protected String exe() throws ApplicationException {

	TCDUser ute = (TCDUser) getUser();

	String hql = "select count(sos) from Route sos where sos.status=6";

	if (ute != null) {
	    String[] userRoles = ute.getRoles();
	    if(!StringUtil.checkStringExistenz(userRoles, "ROLE_SUPER_ADMIN,ROLE_ADMIN")){
		hql+=" and sos.userId='"+ute.getId()+"'";
	    }
	    Long value = (Long) executeHQLQuery(hql, true);
	    jsCode = "<script type=\"text/javascript\">";
	    if (value > 0) {
		jsCode += "$('.red_pin').show().text('" + value + "');";
	    }
	    jsCode += "</script>";

	}

	return SUCCESS;
    }

}
