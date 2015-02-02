package it.micronixnetwork.tcd.plugin.suspend.service;

import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.gaf.service.SearchResult;

import java.util.HashMap;
import java.util.List;

public interface SuspendService {
    
	
	/**
	 * Abilita un insieme di comunicazioni all'invio
	 * @param ids la lista delle PK degli oggetti da rimuovere
	 */
	void send(List ids) throws ServiceException;


}
