package it.micronixnetwork.application.plugin.crude.action;

import it.micronixnetwork.gaf.exception.ApplicationException;

/**
 * Command per la modifica di un oggetto sul DB
 *
 * @author kobo
 *
 */
public class Update extends EditAction {

    private static final long serialVersionUID = 1L;

    public Update() {
        operation = OP_UPDATE;
    }

    @Override
    protected String doIt(Class target) throws ApplicationException {

        Object obj = compose(target, getObjState(), false);

        if (obj != null) {
            if (!rulesControl(target, obj)) {
                return INPUT;
            }
            getCrudeService().update(getUser(), obj.getClass(), obj);
        }

        return SUCCESS;
    }

    @Override
    protected boolean checkRole() {
        return checkRole(getUser().getRoles(), getModifyRoles());
    }

}
