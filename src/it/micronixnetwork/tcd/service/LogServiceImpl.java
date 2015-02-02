package it.micronixnetwork.tcd.service;

import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.gaf.service.hibernate.HibernateSupport;
import it.micronixnetwork.tcd.domain.LogInvio;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Throwable.class)
public class LogServiceImpl extends HibernateSupport implements LogService {

    @Override
    public List<LogInvio> find() throws ServiceException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Object insertLog(LogInvio log) throws ServiceException {
		if (log == null)
			return null;
		try {
			getCurrentSession().saveOrUpdate(log);
		} catch (HibernateException ex) {
			throw new ServiceException(ex);
		}
	return null;
    }

    @Override
    public void logMessage(String idUte, String idDoc, String idSubDoc, String tipoDoc, String source, Integer type, Integer code, String message) throws ServiceException {
	LogInvio li = new LogInvio();
	li.setIdUte(idUte);
	li.setTime(new Date());
	li.setIdDoc(idDoc);
	li.setIdSubDoc(idSubDoc);
	li.setTipoDoc(tipoDoc);
	li.setSource(source);
	li.setType(type);
	li.setCode(code);
	li.setMessage(message);
	try {
	    getCurrentSession().saveOrUpdate(li);
	} catch (HibernateException ex) {
	throw new ServiceException(ex);
	}
	
    }

}
