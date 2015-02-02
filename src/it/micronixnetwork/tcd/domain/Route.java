package it.micronixnetwork.tcd.domain;

import java.beans.Transient;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the Processati database table.
 *
 */
@Entity
@Table(name="routers")
public class Route implements Serializable {
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
	public static final int STATUS_SUSPEND = 6; // route sospesa


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private Long idDocumento = 0l;

	private String tipo;

	@Column(name="routeFrom")
	private String from;

	@Column(name="routeTo")
	private String to;

	private String cc;

	private String bcc;

	private String subject;

	private String body;

	private Integer status = 0;

	private Integer errorCount = 0;

	private String userId = "";

	private String attachName = "";
	
	private Integer consumed=0;

	public Route() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFrom() {
		return from;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String sobject) {
		this.subject = sobject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Transient
	public int incErrorCount(){
		this.errorCount++;
		return errorCount;
	}


	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}
	
	public void setConsumed(Integer consumed) {
	    this.consumed = consumed;
	}
	
	public Integer getConsumed() {
	    return consumed;
	}

	@Override
	public String toString() {
		return "Route [id=" + id + ", idDocumento=" + idDocumento + ", tipo="
				+ tipo + ", from=" + from + ", to=" + to + ", cc=" + cc
				+ ", bcc=" + bcc + ", sobject=" + subject + ", body=" + body
				+ ", status=" + status + ", errorCount=" + errorCount + "]";
	}

	


}