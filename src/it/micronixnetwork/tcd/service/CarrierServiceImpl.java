package it.micronixnetwork.tcd.service;

import it.micronixnetwork.gaf.service.hibernate.HibernateSupport;
import it.micronixnetwork.tcd.service.exception.EmailException;
import it.micronixnetwork.tcd.service.exception.FaxException;

import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Throwable.class)
public class CarrierServiceImpl extends HibernateSupport implements CarrierService {

    @Override
    public Object sendFax() throws FaxException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Object sendEmail() throws EmailException {
	// TODO Auto-generated method stub
	return null;
    }

}
