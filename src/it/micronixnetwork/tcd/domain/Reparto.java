package it.micronixnetwork.tcd.domain;

import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.SearchField;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.gaf.domain.Published;
import it.micronixnetwork.gaf.util.StringUtil;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

/**
 * The persistent class for the Reparti database table.
 * 
 */
@Entity
@Table(name = "reparti")
public class Reparto implements Serializable, Published {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @ToView
    @ToInput
    @FieldStyleDirective(tableCellStyle = "width:150px")
    @SearchField(filtered = true, ordered = true)
    public String legacycode;

    @ToView
    @ToInput
    @FieldStyleDirective(tableCellStyle = "width:auto")
    @SearchField(filtered = false)
    public String descrizione;

    @ToView
    @ToInput
    public String printer;

    @ToView
    @ToInput
    public String email;

    @ToView
    @ToInput
    public String fax;

    @ToView
    @ToInput
    public String archivio;

    @SearchField(filtered = false)
    @FieldStyleDirective(tableCellStyle = "width:30px;text-align:center;color:blue")
    @ToView
    @Formula("(select count(*) from members where trim(members.idRep)=id)")
    public Integer utenti;

    public Reparto() {
    }

    public String getDescrizione() {
	return this.descrizione;
    }

    public void setDescrizione(String descrizione) {
	this.descrizione = descrizione;
    }

    public String getPrinter() {
	return this.printer;
    }

    public void setPrinter(String printer) {
	this.printer = printer;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getFax() {
	return fax;
    }

    public void setFax(String fax) {
	this.fax = fax;
    }

    public String getArchivio() {
	return archivio;
    }

    public void setArchivio(String archivio) {
	this.archivio = archivio;
    }

    @Override
    public String toString() {
	if (StringUtil.EmptyOrNull(descrizione))
	    return "";
	return descrizione;
    }
}