package it.micronixnetwork.tcd.domain;

import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.Owner;
import it.micronixnetwork.application.plugin.crude.annotation.SearchField;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.annotation.ValidField;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.ContributorsRenderer;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.TextRenderer;
import it.micronixnetwork.gaf.domain.Published;
import it.micronixnetwork.gaf.util.StringUtil;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the Destinatari database table.
 *
 */
@Entity
@Table(name = "destinatari")
public class InterDestinatario implements Serializable, Published {
    private static final long serialVersionUID = 1L;
    
    public InterDestinatario() {
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer id;
    
    @OneToOne
    @JoinColumn(name = "idSog", insertable = false, updatable = false)
    public _Soggetto soggetto;
    
    @ToView
    @SearchField(filtered = false,cellRule="nome+' '+cognome")
    @FieldStyleDirective(tableCellStyle="width:100px",labelFieldStyle="background-color:rgb(167, 231, 218);width:100px")
    @ValidField(empty = false, type = "")
    public String nome;
    
    @ToView
    @SearchField(filtered = true,listed=false)
    @FieldStyleDirective(tableCellStyle="width:100px",labelFieldStyle="background-color:rgb(167, 231, 218);width:100px")
    @ValidField(empty = false, type = "")
    public String cognome;
    
    @ToView
    @SearchField(filtered = true,draggable="forAddress")
    @FieldStyleDirective(tableCellStyle="",labelFieldStyle="background-color:#E5A3A3;width:100px")
    @ValidField(empty = false, type = "email")
    public String email;

    @ToView
    @ValidField(empty = true, type = "")
    @TextRenderer(viewRule="soggetto.descrizione")
    @FieldStyleDirective(tableCellStyle="",labelFieldStyle="background-color:#E5A3A3;width:100px")
    //@SearchField(filtered = true,hidden = true)
    //@SelectRenderer(map = "mapByQuery('select distinct c.id,c.ragioneSociale from Soggetto c order by 1 desc')", startValue = "{' ',''}",viewRule="soggetto.ragioneSociale")
    public Integer idSog;
    
   


    @Owner(adminRoles = "ROLE_ADMIN",holdingRule="user.idRep")
    public Integer idRep;

    @ToView
    @FieldStyleDirective(group=2)
    @OneToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "desttdoc", joinColumns = { @JoinColumn(name = "idDest") }, inverseJoinColumns = { @JoinColumn(name = "idTdoc") })
    @ContributorsRenderer
    public Set<TipoDocumento> tipiDocumento;

    public String getCognome() {
	return this.cognome;
    }

    public void setCognome(String cognome) {
	this.cognome = cognome;
    }

    public String getEmail() {
	return this.email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getNome() {
	return this.nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public void setTipiDocumento(Set<TipoDocumento> tipiDocumento) {
	this.tipiDocumento = tipiDocumento;
    }

   
    @Entity
    @Table(name="soggetti")
    public static class _Soggetto implements Serializable {
    	private static final long serialVersionUID = 1L;

    	@Id
    	public Integer id;

    	@FieldStyleDirective(tableCellStyle="width:auto")
    	@SearchField(filtered = false)
    	public String descrizione;

    	public _Soggetto() {
    	}
 
    }
   
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        if(!StringUtil.EmptyOrNull(nome) && !StringUtil.EmptyOrNull(cognome)){
            return nome+" "+cognome;
        }
        return email;
    }

}