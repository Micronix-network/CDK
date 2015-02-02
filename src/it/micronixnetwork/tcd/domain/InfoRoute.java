package it.micronixnetwork.tcd.domain;

import it.micronixnetwork.application.plugin.crude.annotation.Children;
import it.micronixnetwork.application.plugin.crude.annotation.DownloadRule;
import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.GlobalFilter;
import it.micronixnetwork.application.plugin.crude.annotation.Owner;
import it.micronixnetwork.application.plugin.crude.annotation.SearchField;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.TextRenderer;
import it.micronixnetwork.gaf.domain.Published;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the Processati database table.
 *
 */
@Entity
@Table(name="routers")
@GlobalFilter(filter = "tipo<>'ARC'")
public class InfoRoute implements Serializable, Published {
	private static final long serialVersionUID = 1L;

	public static final String ROUTETO_PPRINT = "PRINT";
	public static final String ROUTETO_EMAIL = "EMAIL";
	public static final String ROUTETO_FAX = "FAX";
	public static final String ROUTETO_ARC = "ARC";

	public static final int STATUS_PENDING = 0; // in attesa di elaborazione
	public static final int STATUS_DONE = 1; // elaborato
	public static final int STATUS_NOTNEED = 2; // azione non richiesta
	public static final int STATUS_RETRY = 3; // in attesa di un nuovo tentativo
	public static final int STATUS_HOLD = 4; // sospeso per troppi errori

	public static final int STATUS_WAITFEEDBACK = 5; // in attsa di esito (Fax)


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer id;

	public Long idDocumento = 0l;
	
	@OneToOne
	@JoinColumn(name = "idDocumento", insertable = false, updatable = false)
	public _Documento documento;

	@SearchField(listed=true,filtered=true ,cellRule= "#{\"EMAIL\":{'EMAIL','status-type icon-mail'}, " +
		  "\"FAX\":{'FAX','status-type icon-phone2'}, " +
		  "\"ARC\":{'ARC','status-type icon-data'}}",
		  filterRule="#{'EMAIL':'Email','FAX':'Fax'}")
	@FieldStyleDirective(tableCellStyle="text-align:center;width:40px")
	public String tipo;

	@Column(name="routeFrom")
	@SearchField(filtered=true,roles="ROLE_ADMIN,ROLE_SUPER_ADMIN")
	public String from;

	@SearchField(filtered=true)
	@Column(name="routeTo")
	@ToView
	@TextRenderer(type="text",droppableFrom="forAddress")
	@FieldStyleDirective(inputFieldStyle="width:300px;font-size:12px")
	public String to;

	@ToView
	@TextRenderer(type="text",droppableFrom="forAddress")
	@FieldStyleDirective(inputFieldStyle="width:300px;font-size:12px")
	public String cc;

	@ToView
	@ToInput
	@FieldStyleDirective(inputFieldStyle="width:300px;font-size:12px")
	public String bcc;

	@ToView
	@ToInput
	@SearchField()
	@FieldStyleDirective(inputFieldStyle="width:400px;font-size:12px")
	public String subject;
	
	@SearchField(cellRule="attachName!=''?attachName+'.pdf':''",downlink=true)
	@DownloadRule(downloadRulePath="documento.nomefile",fileName="attachName!=''?attachName+'.pdf':''")
	@FieldStyleDirective(tableCellStyle="width:180px;background-color:rgba(255,100,100,0.1)")
	public String attachName;

	@SearchField(filtered=true,cellRule= "#{\"0\":{'PENDING','pin status_pending'}, " +
		  "\"1\":{'DONE','pin status_done'}, " +
		  "\"2\":{'NOTNEED','pin status_notneed'}, " +
		  "\"3\":{'RETRY','pin status_retry'}, " +
		  "\"4\":{'HOLD','pin status_hold'}}",
		  filterRule="#{'0':'In attesa','1':'Eseguite','3':'Tentativi multipli','4':'Errore'}")
	@FieldStyleDirective(tableCellStyle="width:30px")
	public Integer status = 0;
	

	public Integer errorCount = 0;

	@Owner(holdingRule="user.id",adminRoles="ROLE_SUPER_ADMIN,ROLE_ADMIN")
	public String userId;

	

	public InfoRoute() {
	}

	@Override
	public String toString() {
		if(to!=null){
		    return to;
		}
		return "";
	}
	
	@Entity
	    @Table(name="documenti")
	    public static class _Documento implements Serializable {
	    	private static final long serialVersionUID = 1L;
	    	
	    	@Id
		private Long id;

	    	public String nomefile;
	    	
	    	@SearchField(listed=true,filtered=true,defaultOrdered=true,descendant=true,ordered=true)
	    	@FieldStyleDirective(tableCellStyle="width:75px;background-color:rgba(100,255,100,0.1);filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#11FF0000,endColorstr=#11FF0000);")
	    	@Temporal(TemporalType.DATE)
		public Date dataIns;
	    	
	    	@Column(name="dataIns",insertable=false,updatable=false)
	    	@SearchField(listed=true)
	    	@Temporal(TemporalType.TIME)
	    	@FieldStyleDirective(tableCellStyle="text-align:center;width:40px")
		public Date time;
	    	
	    	public String parent;
	    	
	    	@ToView
	        @OneToMany(fetch=FetchType.EAGER)
	        @JoinColumn(name = "idDoc", referencedColumnName = "parent",insertable = false, updatable = false)
	        @OrderBy(value="idDoc")
	        @Children
	        public Set<LogInvio> logs=new LinkedHashSet<LogInvio>();

	    	@SearchField(listed=false)
	    	public Boolean sospeso;

	    	public Boolean completato;

	    	public String codiceUtente;

	    	public String codiceTipoDocumento = "unknow";

	    	public Long filetimestamp = 0l;

	    	public _Documento() {
	    	}
	 
	    }



}