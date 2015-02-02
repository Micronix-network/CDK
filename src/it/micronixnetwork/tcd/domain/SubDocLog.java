package it.micronixnetwork.tcd.domain;

import it.micronixnetwork.application.plugin.crude.annotation.Computed;
import it.micronixnetwork.application.plugin.crude.annotation.DownloadRule;
import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.SearchField;
import it.micronixnetwork.gaf.domain.Published;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the Processati database table.
 *
 */
@Entity
@Table(name="documenti")
public class SubDocLog implements Serializable,Published {
	private static final long serialVersionUID = 1L;

	public static final int STATUS_PENDING = 0; // in attesa di elaborazione
	public static final int STATUS_DONE = 1; // elaborato
	public static final int STATUS_NOTNEED = 2; // azione non richiesta
	public static final int STATUS_RETRY = 3; // in attesa di un nuovo tentativo
	public static final int STATUS_HOLD = 4; // sospeso per troppi errori

	public static final int QUEUE_SPLITTER = 0; // da splittare (default) non si dovrebbe mai trovare qui
	public static final int QUEUE_COMPOSER = 1; // pronto per i composer (recupero dei dati di invio)
	public static final int QUEUE_ROUTER = 2;   // pronto per essere spedito
	public static final int QUEUE_DONE = 3;    // pronto per essere cancellato
	public static final int QUEUE_TRASH = 4;    // pronto per essere cancellato
	public static final int QUEUE_ERROR = 5;    // documento in errore!

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@FieldStyleDirective(tableCellStyle="width:100px")
	@SearchField
	public Date dataIns;

	@SearchField(filtered = true,hidden = true,defaultValue="**")
	public String parent;

	private String codiceUtente;

	public String codiceTipoDocumento = "unknow";
	
	@OneToOne
	@JoinColumn(name = "codiceTipoDocumento", insertable = false, updatable = false)
	public _TipoDocumento tipoDocumento;
	
	//@SearchField(filtered = false, cellRule = "#{\"0\":{'Definizione','img_def'},\"1\":{'Composizione','img_comp'},\"2\":{'Spedizione','img_lav'},\"3\":{'Eseguito','img_ese'},\"4\":{'Cestino','img_cest'},\"5\":{'Errore','img_err'}}")
	//@FieldStyleDirective(tableCellStyle = "width:60px;text-align:center")
	public Integer coda = 0;
	
	//@SearchField(filtered = false, cellRule = "#{\"false\":{'Active','img_green'}, \"true\":{'Disabled','img_red'}}")
	//@FieldStyleDirective(tableCellStyle = "width:30px;text-align:center")
	public Boolean sospeso;
	
	
	//@SearchField(filtered = false, cellRule = "#{\"true\":{'Active','img_green'}, \"false\":{'Disabled','img_red'}}")
	//@FieldStyleDirective(tableCellStyle = "width:30px;text-align:center")
	public Boolean completato = new Boolean(false);

	private Long filetimestamp = 0l;

	// dati dal tag di pagina
	private String numeroDocumento = "unknow";
	private String dataDocumento = "unknow";
	
	private String codiceFatturazione = "unknow";
	private String codiceAgente = "unknow";
	private String codiceCorriere = "unknow";
	
	@SearchField
	@FieldStyleDirective(tableCellStyle="width:100px;text-align:center")
	public String codiceSoggetto;
	
	@SearchField(cellRule="nomefile.substring(nomefile.lastIndexOf('/')+1)",downlink=true)
	@FieldStyleDirective(tableCellStyle="width:200px;text-align:center")
	@DownloadRule(downloadRulePath="nomefile",fileName="nomefile.substring(nomefile.lastIndexOf('/')+1)")
	public String nomefile;
	
	@Transient
	@Computed(value="coda")
	@SearchField(filtered = false, cellRule = "#{\"0\":{'Definizione','pin status_pending'},\"1\":{'Composizione','pin status_pending'},\"2\":{'Spedizione','pin status_pending'},\"3\":{'Eseguito','pin status_done'},\"4\":{'Cestino','pin status_done'},\"5\":{'Errore','pin status_hold'},\"10\":{'Sospeso','pin status_suspend'}}")
	@FieldStyleDirective(tableCellStyle = "width:30px;text-align:center")
	public String stato;

	public SubDocLog() {
	}
	
	@Entity
	@Table(name="tipidocumento")
	    public static class _TipoDocumento implements Serializable {
	    	private static final long serialVersionUID = 1L;

	    	@FieldStyleDirective(tableCellStyle="width:auto;text-align:center")
	    	@SearchField
	    	public String descrizione;
	    	
	    	@Id
	    	public String legacycode;

	    	public _TipoDocumento() {
	    	}
	    }

}