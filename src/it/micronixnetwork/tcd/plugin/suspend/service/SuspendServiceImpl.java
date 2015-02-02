package it.micronixnetwork.tcd.plugin.suspend.service;

import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.gaf.service.hibernate.HibernateSupport;

import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("unchecked")
@Transactional(rollbackFor = Throwable.class)
public class SuspendServiceImpl extends HibernateSupport implements SuspendService {

    @Override
    public void send(List ids) throws ServiceException {
	//Recupero i documenti riferiti da ids;
	if(ids!=null && ids.size()>0){
        	String hql="update Route r set r.status=0 where r.id in (:ids)";
        	Query q=createQuery(hql);
        	q.setParameterList("ids", ids);
        	q.executeUpdate();
	}
    }

  
}
