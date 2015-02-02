package it.micronixnetwork.tcd.domain;

import it.micronixnetwork.application.plugin.crude.annotation.Computed;
import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.GlobalFilter;
import it.micronixnetwork.application.plugin.crude.annotation.Owner;
import it.micronixnetwork.application.plugin.crude.annotation.SearchField;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.gaf.domain.Published;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the LogInvio database table.
 *
 */
@Entity
@Table(name="loginvio")
@GlobalFilter(filter="code=1000")
public class DocLogged implements Serializable,Published {
	private static final long serialVersionUID = 1L;


	public static final int TYPE_INFO = 1;
	public static final int TYPE_WARNING = 2;
	public static final int TYPE_ERROR = 3;


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long id;
	
	@Column(name="time",insertable=false,updatable=false)
	@SearchField(filtered = true)
	@FieldStyleDirective(tableCellStyle="width:70px;background-color:rgba(0,255,0,0.1)")
	@Temporal(TemporalType.DATE)
	public Date date;
	
	@Column(name="time",insertable=false,updatable=false)
    	@Temporal(TemporalType.TIME)
	@SearchField(filtered = false)
    	@FieldStyleDirective(tableCellStyle="text-align:center;width:30px;background-color:rgba(255,255,0,0.1)")
	public Date hour;

	@SearchField(filtered = true)
	public String idDoc;
	
	private String idSubDoc;
	
	@Owner(adminRoles="ROLE_SUPER_ADMIN,ROLE_ADMIN")
	public String idUte;

	@ToView
	@FieldStyleDirective(tableCellStyle="background-color:rgba(255,100,100,0.1)")
	public String message;
	
    	@Temporal(TemporalType.TIMESTAMP)
    	@SearchField(listed=false,defaultOrdered=true,ordered=true,descendant=true)
	public Date time;

	private String source;
	
	 @OneToMany(fetch=FetchType.EAGER)
	 @JoinColumn(name = "parent", referencedColumnName = "idDoc",insertable=false,updatable=false)
	 public Set<_SubDoc> subdocs;
	 
	 @Transient
	 public int calsStatus(){
	    int status=2;
	    if(subdocs.size()==0) return 1; 
	    for (_SubDoc sub : subdocs) {
		if(sub.coda==0 || sub.coda==1 || sub.coda==2) return 0;
		if(sub.coda==5) status=1;
	    }
	    return status;
	 }
	
	@Transient
	@Computed(value="calsStatus()")
	@SearchField(cellRule= "#{\"0\":{'PENDING','pin status_pending'}, " +
		  "\"1\":{'ERROR','pin status_hold'}, " +
		  "\"3\":{'SOSPESO','pin status_suspend'}, " +
		  "\"2\":{'DONE','pin status_done'}}")
	@FieldStyleDirective(tableCellStyle="width:20px")
	public Long stato;

	private Integer code;

	public String tipoDoc;
	
	
	public Integer type;

	public DocLogged() {
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
    
    @Entity
    @Table(name="documenti")
    public static class _SubDoc implements Serializable {
    	private static final long serialVersionUID = 1L;

    	@Id
	public Long id;

	public String parent;
	
	public Integer coda = 0;

	public Boolean sospeso;

    	public _SubDoc() {
    	}
    }

}