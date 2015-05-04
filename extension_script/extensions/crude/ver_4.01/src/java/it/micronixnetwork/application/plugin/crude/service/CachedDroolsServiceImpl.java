package it.micronixnetwork.application.plugin.crude.service;

import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.gaf.service.hibernate.HibernateSupport;

import java.util.Collection;
import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class CachedDroolsServiceImpl extends HibernateSupport implements DroolsService {


    @Override
    public StatefulKnowledgeSession createSession(List<String> rules) throws ServiceException {
	if (rules == null || rules.size() == 0) {
	    throw new ServiceException("rules list is void");
	}
	KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
	KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
	for (String ruleFile : rules) {
	    debug("Loading file: " + ruleFile);
	    kbuilder.add(ResourceFactory.newFileResource(ruleFile), ResourceType.DRL);
	    if(kbuilder.hasErrors()){
		throw new ServiceException(kbuilder.getErrors().toString());
	    }
	}
	Collection<KnowledgePackage> pkgs = kbuilder.getKnowledgePackages();
	debug("Definited "+pkgs.size()+" KnowledgePackages");
	kbase.addKnowledgePackages(pkgs);
	StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
	debug("created session: " + ksession.getId());
	return ksession;
    }

    @Override
    public void addFactsToSession(StatefulKnowledgeSession session, List<Object> facts) throws ServiceException {
	if (session == null)
	    throw new ServiceException("No knowoledge session present");
	if (facts == null || facts.size() == 0) {
	    throw new ServiceException("facts list is void");
	}
	for (Object fact : facts) {
	    debug("Inserting fact: " + fact + " in session: " + session.getId());
	    session.insert(fact);
	}
    }

    @Override
    public void fireRules(StatefulKnowledgeSession session) throws ServiceException {
	if (session == null)
	    throw new ServiceException("No knowoledge session present");
	debug("Fire rules for session: " + session.getId() + " width: " + session.getKnowledgeBase().getKnowledgePackages().size()+ " packages and: "+session.getFactCount()+" facts");
	try {
	    session.fireAllRules();
	} catch (Exception ex) {
	    error("Rule errors", ex);
	}
	debug("Rules for session: "+session.getId()+" end run");
	session.dispose();
    }

}
