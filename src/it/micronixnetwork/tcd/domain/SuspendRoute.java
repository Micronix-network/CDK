package it.micronixnetwork.tcd.domain;

import it.micronixnetwork.application.plugin.crude.annotation.DownloadRule;
import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.Owner;
import it.micronixnetwork.application.plugin.crude.annotation.SearchField;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.annotation.ValidField;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.TextAreaRenderer;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.TextRenderer;
import it.micronixnetwork.gaf.domain.Published;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the Processati database table.
 *
 */
@Entity
@Table(name="routers")
public class SuspendRoute implements Serializable, Published {
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

	public Long idDocumento;
	
	@OneToOne
	@JoinColumn(name = "idDocumento", insertable = false, updatable = false)
	public _Documento documento;

	@SearchField(fixValue="EMAIL",listed=false)
	public String tipo;

	@Column(name="routeFrom")
	@SearchField(filtered=true,roles="ROLE_ADMIN,ROLE_SUPER_ADMIN")
	public String from;

	@SearchField(filtered=true)
	@Column(name="routeTo")
	@ToView
	@ToInput
	@TextRenderer(type="text",droppableFrom="forAddress")
	@ValidField(empty=false,type=ValidField.EMAIL_VALIDATION)
	@FieldStyleDirective(inputFieldStyle="width:300px;font-size:12px")
	public String to;

	@ToView
	@ToInput
	@TextRenderer(type="text",droppableFrom="forAddress")
	@ValidField(empty=true,type=ValidField.EMAIL_VALIDATION)
	@FieldStyleDirective(inputFieldStyle="width:300px;font-size:12px")
	public String cc;

	@ToView
	@ToInput
	@TextRenderer(type="text",droppableFrom="forAddress")
	@ValidField(empty=true,type=ValidField.EMAIL_VALIDATION)
	@FieldStyleDirective(inputFieldStyle="width:300px;font-size:12px")
	public String bcc;

	@ToView
	@ToInput
	@SearchField()
	@FieldStyleDirective(inputFieldStyle="width:400px;font-size:12px")
	public String subject;

	@ToView
	@ToInput
	@FieldStyleDirective(inputFieldStyle="width:100%;font-size:12px;height:260px")
	@TextAreaRenderer
	public String body;

	@SearchField(fixValue="6",listed=false)
	public Integer status = 0;

	public Integer errorCount = 0;

	
	@Owner(holdingRule="user.id",adminRoles="ROLE_SUPER_ADMIN,ROLE_ADMIN")
	public String userId = "";

	@SearchField(cellRule="attachName!=''?attachName:''",downlink=true)
	@DownloadRule(downloadRulePath="documento.nomefile",fileName="attachName!=''?attachName:''")
	@FieldStyleDirective(tableCellStyle="width:180px;background-color:rgba(255,100,100,0.1)")
	public String attachName;

	public SuspendRoute() {
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
	    	
	    }
	
	


}