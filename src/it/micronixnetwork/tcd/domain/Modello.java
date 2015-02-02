package it.micronixnetwork.tcd.domain;

import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.SearchField;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.annotation.ValidField;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.AutocompleteRenderer;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.SelectRenderer;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.TextAreaRenderer;
import it.micronixnetwork.gaf.domain.Published;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the Modelli database table.
 * 
 */
@Entity
@Table(name = "modelli")
public class Modello implements Serializable, Published {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String id;
    
    @ToView
    @ToInput
    @FieldStyleDirective(inputFieldStyle="width:300px;font-size:12px")
    @ValidField(empty = false, type = "")
    @SearchField(filtered = true)
    public String descrizione;

    @ToView
    @ToInput
    @FieldStyleDirective(inputFieldStyle="width:300px;font-size:12px")
    public String subject;
    
    @ToView
    @ToInput
    @FieldStyleDirective(inputFieldStyle="width:300px;font-size:12px")
    public String faxSubject;

    @ToInput
    @ValidField(empty = true, type = "")
    @AutocompleteRenderer(viewRule="soggetto.ragioneSociale",jsonQuery="select c.ragioneSociale,c.id From Soggetto c where upper(c.ragioneSociale) like upper(:fieldValue) order by c.ragioneSociale")
    @FieldStyleDirective(inputFieldStyle="width:300px;font-size:12px")
    public Integer idSog;

    @OneToOne
    @JoinColumn(name = "idSog", insertable = false, updatable = false)
    public _Soggetto soggetto;

    @ToInput
    @SelectRenderer(map = "mapByQuery('select distinct t.id,t.descrizione from TipoDocumento t order by 1 desc')", startValue = "{' ',''}")
    @FieldStyleDirective(inputFieldStyle="width:300px;font-size:12px")
    @ValidField(empty = false, type = "")
    @SearchField(filtered = true, hidden=true)
    public Integer idTdoc;

    @OneToOne
    @JoinColumn(name = "idTdoc", insertable = false, updatable = false)
    public _TipoDocumento tipoDocumento;

    @ToView
    @ToInput
    public String attachName;
    
    @ToView
    @ToInput
    @FieldStyleDirective(inputFieldStyle="width:100%;font-size:12px;height:260px")
    @TextAreaRenderer
    public String emailTemplate;

    public Modello() {
    }

    public String getId() {
	return this.id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getEmailTemplate() {
	return this.emailTemplate;
    }

    public void setEmailTemplate(String emailTemplate) {
	this.emailTemplate = emailTemplate;
    }

    public String getFaxSubject() {
	return this.faxSubject;
    }

    public void setFaxSubject(String faxSubject) {
	this.faxSubject = faxSubject;
    }

    public Integer getIdCli() {
	return this.idSog;
    }

    public void setIdCli(Integer idSog) {
	this.idSog = idSog;
    }

    public Integer getIdTdoc() {
	return this.idTdoc;
    }

    public void setIdTdoc(Integer idTdoc) {
	this.idTdoc = idTdoc;
    }

    public String getSubject() {
	return subject;
    }

    public void setSubject(String subject) {
	this.subject = subject;
    }

    public String getAttachName() {
	return attachName;
    }

    public void setAttachName(String attachName) {
	this.attachName = attachName;
    }
    
    @Override
    public String toString() {
	if(tipoDocumento!=null)
	    return tipoDocumento.legacycode;
	return "";
    }

    @Entity
    @Table(name = "soggetti")
    public static class _Soggetto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	public Integer id;

	@ToView
	@FieldStyleDirective(tableCellStyle = "width:auto")
	@SearchField(filtered = true)
	public String ragioneSociale;

	public _Soggetto() {
	}

    }

    @Entity
    @Table(name = "tipidocumento")
    public static class _TipoDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	public String id;

	@FieldStyleDirective(tableCellStyle = "width:200px")
	@ToView
	@SearchField(filtered=false)
	public String descrizione;

	@FieldStyleDirective(tableCellStyle = "width:auto")
	@ToView
	public String legacycode;

	public _TipoDocumento() {
	}
    }

}