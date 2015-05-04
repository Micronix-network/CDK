package it.micronixnetwork.application.plugin.crude.service;

import it.micronixnetwork.gaf.exception.ServiceException;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.util.ValueStack;


public interface ObjectCreatorService {

    /**
     * Compone un oggetto creandone una nuova istanza partendo dalla classe target e iniszializzandone lo stato con i valori in objState
     * @param target il tipo dell'oggetto
     * @param objState lo stato con cui inizializzare l'oggetto
     * @param stack
     * @return la nuova istanza con lo stato conforma ai parametri passati
     * @throws ServiceException
     */
    Object compose(Class target, Map<String, String> objState,ValueStack stack,boolean followRelation) throws ServiceException;
    
    /**
     * Recupera da clazz il field annotato @Id e ne restituisce un istanza valorizzato con i dati in idsMap 
     * 
     * @param targetClass tipo dell'oggetto del dominio in cui cercare il filed annotato @Id
     * @param idsMap mappa dei valori da assegnare agli attributi del campo chiave
     * @return L'oggetto che rappresenta la chiave primaria di clazz
     */
    public Object createObjectId(Class clazz, Map<String, String> idsMap, ValueStack stack) throws ServiceException;
    
}
