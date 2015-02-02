package it.micronixnetwork.tcd.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the Soggetti database table.
 *
 */
@Entity
@Table(name="awaitingstatus")
public class AwaitingStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String type;

	private String reference;

	private Integer retryCount = 0;

	private String parent;
	private Integer idRouter = 0;
	private Long idDocument = 0l;

	@Temporal(TemporalType.TIMESTAMP)
	public Date timestamp;

	public AwaitingStatus() {
	}

	public Integer getId() {
	    return id;
	}

	public void setId(Integer id) {
	    this.id = id;
	}

	public String getType() {
	    return type;
	}

	public void setType(String type) {
	    this.type = type;
	}

	public String getReference() {
	    return reference;
	}

	public void setReference(String reference) {
	    this.reference = reference;
	}

	public Integer getRetryCount() {
	    return retryCount;
	}

	public void setRetryCount(Integer retryCount) {
	    this.retryCount = retryCount;
	}

	public void incRetryCount(){
		this.retryCount++;
	}

	public String getParent() {
	    return parent;
	}

	public void setParent(String parent) {
	    this.parent = parent;
	}

	public Integer getIdRouter() {
	    return idRouter;
	}

	public void setIdRouter(Integer idRouter) {
	    this.idRouter = idRouter;
	}

	public Long getIdDocument() {
	    return idDocument;
	}

	public void setIdDocument(Long idDocument) {
	    this.idDocument = idDocument;
	}

	public Date getTimestamp() {
	    return timestamp;
	}

	public void setTimestamp(Date timestamp) {
	    this.timestamp = timestamp;
	}





}