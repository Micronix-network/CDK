package it.micronixnetwork.tcd.domain;

import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.SearchField;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.SelectRenderer;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.TextAreaRenderer;
import it.micronixnetwork.gaf.domain.Published;
import it.micronixnetwork.gaf.util.StringUtil;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the TipiDocumento database table.
 * 
 */
@Entity
@Table(name = "tipidocumento")
public class TipoDocumentoList implements Serializable, Published {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @ToView
    @FieldStyleDirective(tableCellStyle = "width:60px")
    @SearchField(filtered = true, ordered = true)
    public String legacycode;

    @ToView
    @SelectRenderer(viewRule="#{\"true\":{'Active','img_green'}, \"false\":{'Disabled','img_red'}}")
    @FieldStyleDirective(tableCellStyle = "width:30px;text-align:center")
    public Boolean toFax;

    @ToView
    @SelectRenderer(viewRule="#{\"true\":{'Active','img_green'}, \"false\":{'Disabled','img_red'}}")
    @FieldStyleDirective(tableCellStyle = "width:30px;text-align:center")
    public Boolean toEmail;

    @ToView
    @SelectRenderer(viewRule="#{\"true\":{'Active','img_green'}, \"false\":{'Disabled','img_red'}}")
    @FieldStyleDirective(tableCellStyle = "width:30px;text-align:center")
    public Boolean toArchive;

    @ToView
    @SelectRenderer(viewRule="#{\"true\":{'Active','img_green'}, \"false\":{'Disabled','img_red'}}")
    @FieldStyleDirective(tableCellStyle = "width:30px;text-align:center")
    public Boolean toPrinter;
    
    @ToView
    @ToInput
    @SearchField(filtered = true)
    @TextAreaRenderer(height=70)
    @FieldStyleDirective(tableCellStyle = "width:150px")
    public String descrizione;

    public TipoDocumentoList() {
    }

    public String getDescrizione() {
	return this.descrizione;
    }

    public void setDescrizione(String descrizione) {
	this.descrizione = descrizione;
    }

    public void setToEmail(Boolean toEmail) {
	this.toEmail = toEmail;
    }

    public void setToFax(Boolean toFax) {
	this.toFax = toFax;
    }

    public Boolean getToEmail() {
	return toEmail;
    }

    public Boolean getToFax() {
	return toFax;
    }

    public void setToArchive(Boolean toArchive) {
	this.toArchive = toArchive;
    }

    public void setToPrinter(Boolean toPrinter) {
	this.toPrinter = toPrinter;
    }

    public Boolean getToArchive() {
	return toArchive;
    }

    public Boolean getToPrinter() {
	return toPrinter;
    }
    
    @Override
    public String toString() {
	if (StringUtil.EmptyOrNull(descrizione))
	    return legacycode;
	return descrizione;
    }

}