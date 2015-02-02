package it.micronixnetwork.tcd.domain;

import it.micronixnetwork.gaf.util.REXTokenizer;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the Processati database table.
 *
 */
@Entity
@Table(name="documenti")
public class Documento implements Serializable {
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
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataIns;

	private String parent;

	private Integer coda = 0;

	private String nomefile;

	private Boolean sospeso;

	private Boolean completato = new Boolean(false);

	private String codiceUtente;

	private String codiceTipoDocumento = "unknow";

	private Long filetimestamp = 0l;

	// dati dal tag di pagina
	private String numeroDocumento = "unknow";
	private String dataDocumento = "00000000";
	private String codiceSoggetto = "unknow";
	private String codiceFatturazione = "unknow";
	private String codiceAgente = "unknow";
	private String codiceCorriere = "unknow";

	public Documento() {
	}


	public String replaceVarS(String text){

		String ret = "";

		REXTokenizer rt = new REXTokenizer(text, "\\$\\{[^\\{]*\\}", false); // \$\{[^\{]*\}
		if (rt.hasNext()) {
			String varName = (String) rt.next();
			if ("${numDocFS}".equals(varName)){

				ret = text.replace(varName, replaceForFs(this.numeroDocumento));

			}else if("${numDoc}".equals(varName)){

				ret = text.replace(varName, this.numeroDocumento);

			}else if("${dataDoc}".equals(varName)){

				ret = text.replace(varName, this.dataDocumento);

			}else if("${codCli}".equals(varName)){

				ret = text.replace(varName, this.codiceSoggetto);

			}else if("${codAge}".equals(varName)){

				ret = text.replace(varName, this.codiceAgente);

			}else if("${docID}".equals(varName)){

				ret = text.replace(varName, ""+this.id);

			}else if("${annoDoc}".equals(varName)){
				if (this.dataDocumento != null && this.dataDocumento.length()==8)
					ret = text.replace(varName, this.dataDocumento.substring(0, 4));

			}else if("${meseDoc}".equals(varName)){
				if (this.dataDocumento != null && this.dataDocumento.length()==8)
					ret = text.replace(varName, this.dataDocumento.substring(4, 6));

			}else if("${giornoDoc}".equals(varName)){
				if (this.dataDocumento != null && this.dataDocumento.length()==8)
					ret = text.replace(varName, this.dataDocumento.substring(6, 8));

			}else if("${tipoDoc}".equals(varName)){
				ret = text.replace(varName, this.codiceTipoDocumento);

			}else if("".equals(varName)){

			}else if("".equals(varName)){

			}else if("".equals(varName)){

			}else {
				ret = text.replace(varName, "!! Invalid "+ varName.replace("${", "").replace("}", "") +" !!");
			}
			return replaceVarS(ret);

		}else {

			return text;
		}

	}

	private String replaceForFs(String in){
		return in.replace("/", "-").replace(":", "-").replace("\\", "-");
	}

	public String getIdSubDoc(){
		return this.nomefile.substring(1+Math.max(nomefile.lastIndexOf("/"),nomefile.lastIndexOf("\\")));
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataIns() {
		return this.dataIns;
	}

	public void setDataIns(Timestamp dataIns) {
		this.dataIns = dataIns;
	}

	public String getNomefile() {
		return this.nomefile;
	}

	public void setNomefile(String nomefile) {
		this.nomefile = nomefile;
	}

	public Boolean getSospeso() {
		return this.sospeso;
	}

	public void setSospeso(Boolean sospeso) {
		this.sospeso = sospeso;
	}

	public Boolean getCompletato() {
		return this.completato;
	}

	public void setCompletato(Boolean completato) {
		this.completato = completato;
	}

	public Long getFiletimestamp() {
		return filetimestamp;
	}

	public void setFiletimestamp(Long filetimestamp) {
		this.filetimestamp = filetimestamp;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(String dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public String getCodiceSoggetto() {
		return codiceSoggetto;
	}

	public void setCodiceSoggetto(String codiceSoggetto) {
		this.codiceSoggetto = codiceSoggetto;
	}

	public String getCodiceFatturazione() {
		return codiceFatturazione;
	}

	public void setCodiceFatturazione(String codiceFatturazione) {
		this.codiceFatturazione = codiceFatturazione;
	}

	public String getCodiceAgente() {
		return codiceAgente;
	}

	public void setCodiceAgente(String codiceAgente) {
		this.codiceAgente = codiceAgente;
	}

	public String getCodiceCorriere() {
		return codiceCorriere;
	}

	public void setCodiceCorriere(String codiceCorriere) {
		this.codiceCorriere = codiceCorriere;
	}


	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public int getCoda() {
		return coda;
	}

	public void setCoda(int coda) {
		this.coda = coda;
	}


	public String getCodiceUtente() {
	    return codiceUtente;
	}


	public void setCodiceUtente(String codiceUtente) {
	    this.codiceUtente = codiceUtente;
	}


	public String getCodiceTipoDocumento() {
	    return codiceTipoDocumento;
	}


	public void setCodiceTipoDocumento(String codiceTipoDocumento) {
	    this.codiceTipoDocumento = codiceTipoDocumento;
	}



}