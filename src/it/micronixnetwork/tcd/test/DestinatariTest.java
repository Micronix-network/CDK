package it.micronixnetwork.tcd.test;

import it.micronixnetwork.gaf.test.TestRunner;
import it.micronixnetwork.tcd.service.CarrierService;
import it.micronixnetwork.tcd.service.EngineService;
import it.micronixnetwork.tcd.service.LogService;

public class DestinatariTest extends TestRunner {

    private CarrierService carrierService = null;
    private EngineService engineService = null;
    private LogService logService = null;

    public void setCarrierService(CarrierService carrierService) {
	this.carrierService = carrierService;
    }

    public void setEngineService(EngineService engineService) {
	this.engineService = engineService;
    }

    public void setLogService(LogService logService) {
	this.logService = logService;
    }

    public DestinatariTest() {
	super();
    }

    @Override
    public boolean runTest() {
	

	return true;
    }

}
