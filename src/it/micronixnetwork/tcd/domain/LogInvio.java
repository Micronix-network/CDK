package it.micronixnetwork.tcd.domain;

import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.SearchField;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.gaf.domain.Published;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the LogInvio database table.
 *
 */
@Entity
@Table(name="loginvio")
public class LogInvio implements Serializable,Published {
	private static final long serialVersionUID = 1L;


	public static final int TYPE_INFO = 1;
	public static final int TYPE_WARNING = 2;
	public static final int TYPE_ERROR = 100000;
	public static final int TYPE_START = 0;
	public static final int TYPE_HAPPY_END = 1000;


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long id;

	@SearchField(filtered = true,hidden = true,defaultValue="**")
	public String idDoc;
	
	private String idSubDoc;
	
	@SearchField
	@Column(name="time",insertable=false,updatable=false)
	@FieldStyleDirective(tableCellStyle="width:60px")
	@Temporal(TemporalType.DATE)
	public Date date;
	
	@SearchField
	@Column(name="time",insertable=false,updatable=false)
    	@Temporal(TemporalType.TIME)
    	@FieldStyleDirective(tableCellStyle="text-align:center;width:40px;background-color:rgba(0,255,0,0.1)")
	public Date hour;
	
	@SearchField
    	public String source;

	public String idUte;

	@SearchField(filtered=true)
	@FieldStyleDirective(tableCellStyleRule="(type==100000?'color:red':'')")
	public String message;
	
    	@Temporal(TemporalType.TIMESTAMP)
	public Date time;

	@ToView
	@SearchField(filtered = true)
	private Integer type;

	private Integer code;

	public String tipoDoc;

	public LogInvio() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}

	public String getIdSubDoc() {
		return idSubDoc;
	}

	public void setIdSubDoc(String idSubDoc) {
		this.idSubDoc = idSubDoc;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getIdUte() {
		return this.idUte;
	}

	public void setIdUte(String idUte) {
		this.idUte = idUte;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

    @Entity
    @Table(name = "members")
    public static class _TCDUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	public String id;

	@FieldStyleDirective(tableCellStyle = "width:auto")
	@SearchField(filtered = true)
	public String username;

	public _TCDUser() {
	}

    }

}