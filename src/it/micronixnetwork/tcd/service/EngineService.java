package it.micronixnetwork.tcd.service;

import it.micronixnetwork.gaf.exception.ServiceException;
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

import java.util.ArrayList;
import java.util.List;

public interface EngineService {

    void saveDocumento(Documento doc) throws ServiceException;

    Documento getDocumento(Long id) throws ServiceException;

    void removeDocumento(Documento doc) throws ServiceException;

    List<Documento> retriveToSend(String userId, Boolean processed,Boolean suspended) throws ServiceException;

    List<Documento> retriveFromQueue(int queue, String userId, Boolean processed, Boolean supended) throws ServiceException;

    List<Destinatario> retriveDestinatari(TipoDocumento tipoDoc,Soggetto soggetto,Reparto reparto) throws ServiceException;

    TCDUser getUser(String legacycode) throws ServiceException;
    
    Soggetto getSoggetto(String legacycode,String idTipo) throws ServiceException;

    TipoDocumento getTipoDocumento(String legacycode) throws ServiceException;

    void saveAttesaStato(AwaitingStatus as) throws ServiceException;

    List<AwaitingStatus> retriveAttesaStato(String type) throws ServiceException;

    void removeAttesaStato(AwaitingStatus aw) throws ServiceException;


    // gestione routers
    void saveRoute(Route route) throws ServiceException;

    public List<Route> retriveRouters(Documento doc) throws ServiceException;

    public List<Route> retriveRouters(Documento doc, int status) throws ServiceException;
    
    public List<Route> retriveAvailableRouters(Documento doc, int status) throws ServiceException;

    Route getRoute(Integer id) throws ServiceException;

    public void deleteDocumenti(String parent) throws ServiceException;
    
    public Long checkRouteActivity(Documento doc) throws ServiceException;
    
   

}
