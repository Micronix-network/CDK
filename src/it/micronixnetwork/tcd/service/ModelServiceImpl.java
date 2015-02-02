package it.micronixnetwork.tcd.service;

import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.gaf.service.hibernate.HibernateSupport;
import it.micronixnetwork.tcd.domain.Modello;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Throwable.class)
public class ModelServiceImpl extends HibernateSupport implements
		ModelService {


	@Override
	public Modello getModello(Integer idTdoc, Integer idSog)
			throws ServiceException {

		String hql = "From Modello m where m.idTdoc=:idTdoc ";

		if (idSog!=null)
		    hql+="AND m.idSog=:idSog)";

		Query query = createQuery(hql);

		query.setInteger("idTdoc", idTdoc);

		if (idSog!=null)
		    query.setInteger("idSog", idSog);

		Modello m = (Modello) query.uniqueResult();

		// se non ho quello specifico cerco quello generale
		if (m == null){
			hql = "From Modello m where m.idTdoc=:idTdoc AND m.idSog=null";

			query = createQuery(hql);

			query.setInteger("idTdoc", idTdoc);

			m = (Modello) query.uniqueResult();

		}
		return m ;

	}

}