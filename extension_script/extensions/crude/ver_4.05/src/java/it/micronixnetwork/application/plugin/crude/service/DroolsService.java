package it.micronixnetwork.application.plugin.crude.service;

import it.micronixnetwork.gaf.exception.ServiceException;

import java.util.List;

import org.drools.runtime.StatefulKnowledgeSession;

public interface DroolsService {
    
    StatefulKnowledgeSession createSession(List<String> rules) throws ServiceException;
    
    void addFactsToSession(StatefulKnowledgeSession session,List<Object> facts) throws ServiceException;
    
    void fireRules(StatefulKnowledgeSession session) throws ServiceException;

}
