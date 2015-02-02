package it.micronixnetwork.tcd.service;

import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.gaf.service.hibernate.HibernateSupport;
import it.micronixnetwork.gaf.util.Smtp;
import it.micronixnetwork.gaf.util.StringUtil;
import it.micronixnetwork.tcd.domain.AwaitingStatus;
import it.micronixnetwork.tcd.domain.Soggetto;
import it.micronixnetwork.tcd.domain.Destinatario;
import it.micronixnetwork.tcd.domain.Documento;
import it.micronixnetwork.tcd.domain.Reparto;
import it.micronixnetwork.tcd.domain.Route;
import it.micronixnetwork.tcd.domain.TCDUser;
import it.micronixnetwork.tcd.domain.TipoDocumento;
import it.micronixnetwork.tcd.service.exception.EngineException;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Throwable.class)
public class EngineServiceImpl extends HibernateSupport implements EngineService {

    @Override
    public void saveDocumento(Documento doc) throws ServiceException {
	if (doc == null)
	    return;
	getCurrentSession().saveOrUpdate(doc);
    }

    public List<Destinatario> retriveDestinatari(TipoDocumento tipoDoc, Soggetto soggetto, Reparto reparto) throws ServiceException {
	List<Destinatario> result = new ArrayList<Destinatario>();

	if (tipoDoc == null || soggetto == null || reparto == null)
	    throw new EngineException("Incomplete input data");

	String hql = "From Destinatario d where d.idSog=:idcli and d.idRep=:idrep";

	Query query = createQuery(hql);

	query.setInteger("idcli", soggetto.id);
	query.setInteger("idrep", reparto.id);

	List<Destinatario> destinatari = (List<Destinatario>) query.list();

	if (destinatari.size() > 0) {
	    for (Destinatario dest : destinatari) {
		for (TipoDocumento tdoc : dest.tipiDocumento) {
		    if (tipoDoc.id.equals(tdoc.id)) {
			result.add(dest);
			break;
		    }
		}
	    }

	}
	if (result.size() == 0) {
	    // leggo i dati dal soggetto
	    Destinatario d = new Destinatario();
	    d.email=soggetto.email;
	    d.fax=soggetto.fax;
	    result.add(d);
	}

	return result;
    }

    @Override
    public Documento getDocumento(Long id) throws ServiceException {
	if (id == null)
	    return null;
	try {
	    return (Documento) getCurrentSession().get(Documento.class, id);
	} catch (HibernateException ex) {
	    throw new ServiceException(ex);
	}
    }

    @Override
    public void removeDocumento(Documento doc) throws ServiceException {
	if (doc == null)
	    return;
	try {
	    getCurrentSession().delete(doc);
	} catch (HibernateException ex) {
	    throw new ServiceException(ex);
	}
    }

    @Override
    public List<Documento> retriveToSend(String userId, Boolean completed, Boolean suspended) throws ServiceException {
	String hql = "From Documento p where";
	if (userId != null) {
	    hql += " p.idUte=:userId and";
	}

	hql += " p.completato=:completato and p.sospeso=:sospeso order by p.dataIns";

	Query query = createQuery(hql);

	if (userId != null) {
	    query.setString("userId", userId);
	}

	query.setBoolean("completato", completed);

	query.setBoolean("sospeso", suspended);

	return (List<Documento>) query.list();
    }

    @Override
    public TCDUser getUser(String legacycode) throws ServiceException {
	if (legacycode == null)
	    return null;
	String hql = "From TCDUser where legacycode=:legacy";
	Query query = createQuery(hql);
	query.setString("legacy", legacycode);
	return (TCDUser) query.uniqueResult();
    }

    @Override
    public TipoDocumento getTipoDocumento(String legacycode) throws ServiceException {
	if (legacycode == null)
	    return null;
	String hql = "From TipoDocumento where legacycode=:legacy";
	Query query = createQuery(hql);
	query.setString("legacy", legacycode);
	return (TipoDocumento) query.uniqueResult();
    }

    public Soggetto getSoggetto(String legacycode, String idTipo) throws ServiceException {
	if (legacycode == null)
	    return null;
	String hql = "From Soggetto where legacycode=:legacy";
	if (!StringUtil.EmptyOrNull(idTipo)) {
	    hql += " and idTSog=:idTipo";
	}
	Query query = createQuery(hql);
	query.setString("legacy", legacycode);
	if (!StringUtil.EmptyOrNull(idTipo)) {
	    query.setString("idTipo", idTipo);
	}
	return (Soggetto) query.uniqueResult();
    }

    @Override
    public void saveRoute(Route route) throws ServiceException {
	if (route == null)
	    return;
	try {
	    getCurrentSession().saveOrUpdate(route);
	} catch (HibernateException ex) {
	    throw new ServiceException(ex);
	}
    }

    @Override
    public List<Route> retriveRouters(Documento doc) throws ServiceException {

	if (doc == null)
	    throw new EngineException("Incomplete input data");

	String hql = "From Route r where r.idDocumento=:iddoc";

	Query query = createQuery(hql);

	query.setLong("iddoc", doc.getId());

	return (List<Route>) query.list();

    }

    @Override
    public List<Documento> retriveFromQueue(int queue, String userId, Boolean processed, Boolean suspended) throws ServiceException {

	String hql = "From Documento d where";
	if (userId != null) {
	    hql += " d.idUte=:userId and";
	}

	hql += " d.coda=:coda and completato=:processato";

	if (suspended != null) {
	    hql += " and d.sospeso=:suspended";
	}

	hql += " order by d.dataIns";

	Query query = createQuery(hql);

	if (userId != null) {
	    query.setString("userId", userId);
	}

	if (suspended != null) {
	    query.setBoolean("suspended", suspended);
	}

	query.setInteger("coda", queue);

	query.setBoolean("processato", processed);

	return (List<Documento>) query.list();
    }

    @Override
    public List<Route> retriveRouters(Documento doc, int status) throws ServiceException {
	if (doc == null)
	    throw new EngineException("Incomplete input data");

	String hql = "From Route r where r.idDocumento=:iddoc and r.status=:status";

	Query query = createQuery(hql);

	query.setLong("iddoc", doc.getId());
	query.setInteger("status", status);

	return (List<Route>) query.list();

    }
    
    @Override
    public List<Route> retriveAvailableRouters(Documento doc, int status) throws ServiceException {
	if (doc == null)
	    throw new EngineException("Incomplete input data");

	String hql = "From Route r where r.idDocumento=:iddoc and r.status=:status and r.consumed!=1";

	Query query = createQuery(hql);
	
	//query.setLockMode("r", LockMode.UPGRADE);

	query.setLong("iddoc", doc.getId());
	query.setInteger("status", status);
	
	
	List<Route> result=(List<Route>)query.list();
	
	for (Route route : result) {
	    route.setConsumed(1);
	    getCurrentSession().update(route);
	}

	return result;
    }
    
    public Long checkRouteActivity(Documento doc) throws ServiceException {
	if (doc == null)
	    throw new EngineException("Incomplete input data");

	String hql = "Select count(r) From Route r where r.idDocumento=:iddoc and (r.status in("+Route.STATUS_PENDING+","+Route.STATUS_RETRY+","+Route.STATUS_WAITFEEDBACK+"))";

	Query query = createQuery(hql);

	query.setLong("iddoc", doc.getId());

	return (Long)query.uniqueResult();
    }

    @Override
    public void deleteDocumenti(String parent) throws ServiceException {

	if (parent == null || parent.length() == 0)
	    throw new EngineException("Incomplete input data");

	String hql = "Delete from Documento d where d.parent=:parent";
	Query query = createQuery(hql);
	query.setString("parent", parent);
	query.executeUpdate();

    }

    @Override
    public void saveAttesaStato(AwaitingStatus as) throws ServiceException {
	if (as == null)
	    return;
	try {
	    getCurrentSession().saveOrUpdate(as);
	} catch (HibernateException ex) {
	    throw new ServiceException(ex);
	}
    }

    @Override
    public List<AwaitingStatus> retriveAttesaStato(String type) throws ServiceException {

	if (type == null)
	    throw new EngineException("Incomplete input data");

	String hql = "From AwaitingStatus a where a.type=:type";

	Query query = createQuery(hql);

	query.setString("type", type);

	return (List<AwaitingStatus>) query.list();
    }

    @Override
    public void removeAttesaStato(AwaitingStatus aw) throws ServiceException {
	if (aw == null)
	    return;

	try {
	    getCurrentSession().delete(aw);
	} catch (HibernateException ex) {
	    throw new ServiceException(ex);
	}
    }

    @Override
    public Route getRoute(Integer id) throws ServiceException {
	if (id == null)
	    return null;
	try {
	    return (Route) getCurrentSession().get(Route.class, id);
	} catch (HibernateException ex) {
	    throw new ServiceException(ex);
	}
    }


}
