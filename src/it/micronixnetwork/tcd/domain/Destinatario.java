package it.micronixnetwork.tcd.domain;

import it.micronixnetwork.application.plugin.crude.annotation.Children;
import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.Owner;
import it.micronixnetwork.application.plugin.crude.annotation.SearchField;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.annotation.ValidField;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.AutocompleteRenderer;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.ContributorsRenderer;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.SelectRenderer;
import it.micronixnetwork.application.plugin.crude.model.Traceable;
import it.micronixnetwork.gaf.domain.Published;
import it.micronixnetwork.gaf.util.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the Destinatari database table.
 *
 */
@Entity
@Table(name = "destinatari")
public class Destinatario extends Traceable implements Serializable, Published {
    private static final long serialVersionUID = 1L;

    public static enum TRASPORT {
	EMAIL, FAX,NONE
    };
    
    public Destinatario() {
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;
    
    @ToView
    @ToInput
    @FieldStyleDirective(tableCellStyle = "width:80px")
    @SearchField(filtered = true,ordered=true)
    private String legacycode;
    
    @ToView
    @ToInput
    @SearchField(filtered = false,cellRule="nome+' '+cognome",ordered = true)
    @FieldStyleDirective(tableCellStyle="width:100px")
    @ValidField(empty = false, type = "")
    public String nome;
    
    @ToView
    @ToInput
    @SearchField(filtered = true,listed=false)
    @FieldStyleDirective(tableCellStyle="width:100px")
    @ValidField(empty = false, type = "")
    public String cognome;
    
    @ToView
    @ToInput
    @SearchField(filtered = false,listed=false)
    @FieldStyleDirective(tableCellStyle="width:100px")
    @ValidField(empty = true, type = "")
    public String email;

    @ToView
    @ToInput
    @SearchField(filtered = false,listed=false)
    @FieldStyleDirective(tableCellStyle="width:50px")
    @ValidField(empty = true, type = "")
    public String fax;
    
    @SearchField(cellRule="'- '+(email!=null?email:'')+'<br>'+(fax!=null?'- '+fax:'')")
    @FieldStyleDirective(tableCellStyle="width:190px")
    @Transient
    public String emailFax;
   
    @ToView
    @ToInput(roles="ROLE_ADMIN,ROLE_CONFIGURATOR") 
    @OneToMany(fetch=FetchType.EAGER, targetEntity = Eccezione.class,cascade=CascadeType.ALL)
    @JoinColumn(name = "idDest", referencedColumnName = "id",nullable = false)
    @OrderBy(value="id")
    @Children
    public Set<Eccezione> eccezioni=new LinkedHashSet<Eccezione>();

    @ToView
    @ToInput
    @ValidField(empty = true, type = "")
    @SearchField(filtered = true,hidden = true)
    @AutocompleteRenderer(viewRule="soggetto.descrizione",jsonQuery="select c.descrizione,c.id From Soggetto c where upper(c.descrizione) like upper(:fieldValue) order by c.descrizione")
    public Integer idSog;
    
    @OneToOne
    @JoinColumn(name = "idSog", insertable = false, updatable = false)
    public _Soggetto soggetto;

    @ToView
    @ToInput
    @ValidField(empty = false, type = "")
    @SelectRenderer(map = "mapByQuery('select distinct r.id,r.descrizione from Reparto r order by 1 desc')", startValue = "{' ',''}",viewRule="reparto.descrizione")
    @Owner(adminRoles = "ROLE_ADMIN",holdingRule="user.idRep")
    public Integer idRep;
    
    @OneToOne
    @JoinColumn(name = "idRep", insertable = false, updatable = false)
    public _Reparto reparto;

    @ToView
    @ToInput
    @FieldStyleDirective(group=2)
    @SearchField(filtered = true,filterRule="mapByQuery('select t.descrizione,t.id from TipoDocumento t order by t.descrizione asc')")
    @OneToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "desttdoc", joinColumns = { @JoinColumn(name = "idDest") }, inverseJoinColumns = { @JoinColumn(name = "idTdoc") })
    @ContributorsRenderer
    public Set<TipoDocumento> tipiDocumento;

    @Transient
    public TRASPORT[] getTrasportType(TipoDocumento tipo) {
	ArrayList<TRASPORT> result = new ArrayList<TRASPORT>();
	if (tipo.toEmail){
	    result.add(TRASPORT.EMAIL);
	}
	if (tipo.toFax){
	    result.add(TRASPORT.FAX);
	}
	
	if (eccezioni != null) {
	    for (Eccezione ecc : eccezioni) {
		if (ecc.idTdoc.equals(tipo.id)) {
		    if (ecc.emailActive) {
			result.remove(TRASPORT.EMAIL);
			result.add(TRASPORT.EMAIL);
		    } else{
			result.remove(TRASPORT.EMAIL);
		    }
		    if (ecc.faxActive) {
			result.remove(TRASPORT.FAX);
			result.add(TRASPORT.FAX);
		    } else{
			result.remove(TRASPORT.FAX);
		    }
		    }
		}
	}
	return result.toArray(new TRASPORT[result.size()]);
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
    
    @Entity
    @Table(name="reparti")
    public static class _Reparto implements Serializable {
    	private static final long serialVersionUID = 1L;

    	@Id
    	public Integer id;

    	@FieldStyleDirective(tableCellStyle="width:auto")
    	@SearchField(filtered = true,roles="ROLE_ADMIN",filterRule="mapByQuery('select distinct r.descrizione,r.descrizione from Reparto r order by 1 desc')")
    	public String descrizione;

    	public _Reparto() {
    	}
    }
    
    @Override
    public String toString() {
        if(!StringUtil.EmptyOrNull(nome) && !StringUtil.EmptyOrNull(cognome)){
            return nome+" "+cognome;
        }
        return email;
    }

}