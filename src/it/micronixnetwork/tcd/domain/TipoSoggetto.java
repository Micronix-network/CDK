package it.micronixnetwork.tcd.domain;

import it.micronixnetwork.gaf.util.StringUtil;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the TipiDocumento database table.
 * 
 */
@Entity
@Table(name = "tipisog")
public class TipoSoggetto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    public String id;

    public String descrizione;

    @Override
    public String toString() {
	if (StringUtil.EmptyOrNull(descrizione))
	    return id;
	return descrizione;
    }

}