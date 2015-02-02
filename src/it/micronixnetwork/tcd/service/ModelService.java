package it.micronixnetwork.tcd.service;

import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.tcd.domain.Modello;

public interface ModelService {

    Modello getModello(Integer idTdoc, Integer idSog) throws ServiceException;

}
