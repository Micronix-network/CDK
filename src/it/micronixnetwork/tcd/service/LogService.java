package it.micronixnetwork.tcd.service;

import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.tcd.domain.LogInvio;

import java.util.List;

public interface LogService {
    
    List<LogInvio> find() throws ServiceException;
    
    Object insertLog(LogInvio log) throws ServiceException;
    
    void logMessage(String idUte, String idDoc, String idSubDoc, String tipoDoc, String source, Integer type, Integer code, String message) throws ServiceException;

}
