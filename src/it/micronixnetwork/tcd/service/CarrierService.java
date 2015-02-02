package it.micronixnetwork.tcd.service;

import it.micronixnetwork.tcd.service.exception.EmailException;
import it.micronixnetwork.tcd.service.exception.FaxException;

public interface CarrierService {
    
    Object sendFax() throws FaxException;
    
    Object sendEmail() throws EmailException;
}
