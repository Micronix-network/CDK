package it.micronixnetwork.application.plugin.crude.service;

import it.micronixnetwork.gaf.domain.RoledUser;
import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.gaf.service.SearchResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CrudeService {
	
	
	/**
	 * Effettua una ricerca su un detrminato oggetto applicano filtri variabili
         * @param user utente che effettua la ricerca
	 * @param cls  classe degli oggetti da ricercare
	 * @param filters filtri da applicare alla ricerca
	 * @param orderBy campi su cui ordinare separati da virgole
	 * @param page pagina da restituire
	 * @param size numero di risuktati per pagina
	 * @return
	 * @throws ServiceException
	 */
	SearchResult find(RoledUser user,Class cls,Map<String, String> filters,String toOrder,Integer page,Integer size,Integer limit) throws ServiceException;
	
	/**
	 * Recupera un oggetto del dominio a partire dall'id
	 * @param id
	 * @return
	 */
	Object get(Class cls,Object id) throws ServiceException;
	
	/**
	 * Inserirsce sul DB i dati di un nuovo oggetto del dominio
	 * @param comm
	 * @return l'id della nuova commessa
	 */
	Object create(RoledUser user,Object obj) throws ServiceException;
	
	/**
	 * Modifica i dati di un oggetto del dominio
	 * @param comm
	 */
	void update(RoledUser user,Class cls,Object obj) throws ServiceException;
	
	/**
	 * Rimuove i dati di stato di una lista di oggetti del dominio
	 * @param cls tipo degli oggetti da romuovere
	 * @param ids la lista delle PK degli oggetti da rimuovere
	 */
	void remove(RoledUser user,Class cls,List ids) throws ServiceException;
	
	/**
	 * Aggiunge un child ad un oggetto
	 * @param parent il tipo del parent
	 * @param parentId la primary key del parent
	 * @param child l'oggetto da aggiungere
	 * @throws ServiceException
	 */
	void addChildren(RoledUser user,Class parent,Object parentId,String fieldName, Object child) throws ServiceException;


}
