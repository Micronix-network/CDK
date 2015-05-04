package it.micronixnetwork.application.plugin.crude.model;

import java.util.HashMap;
import java.util.Map;

public class DroolsFormMap extends HashMap<String, String> {

	private static final long serialVersionUID = 1L;

	public DroolsFormMap() {
		super();
	}

	public DroolsFormMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public DroolsFormMap(int initialCapacity) {
		super(initialCapacity);
	}

	public DroolsFormMap(Map<? extends String, ? extends String> m) {
		super(m);
	}
	
	

}
