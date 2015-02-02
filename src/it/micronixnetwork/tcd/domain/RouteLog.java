package it.micronixnetwork.tcd.domain;

import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.Owner;
import it.micronixnetwork.application.plugin.crude.annotation.SearchField;
import it.micronixnetwork.gaf.domain.Published;

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
public class RouteLog implements Serializable, Published {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer id;

	@SearchField(filtered = true,hidden = true,defaultValue="-1")
	public Long idDocumento = 0l;

	@SearchField(cellRule= "#{\"EMAIL\":{'EMAIL','status-type icon-mail'}, " +
		  "\"FAX\":{'FAX','status-type icon-phone2'}, " +
		  "\"ARC\":{'ARC','status-type icon-data'}}",
		  filterRule="#{'EMAIL':'Email','FAX':'Fax'}")
	@FieldStyleDirective(tableCellStyle="text-align:center;width:40px")
	public String tipo;

	@Column(name="routeFrom")
	@SearchField(roles="ROLE_ADMIN,ROLE_SUPER_ADMIN")
	public String from;

	@SearchField
	@Column(name="routeTo")
	@FieldStyleDirective(inputFieldStyle="width:300px;font-size:12px")
	public String to;

	public String cc;

	public String bcc;

	@SearchField
	@FieldStyleDirective(inputFieldStyle="width:400px;font-size:12px")
	public String subject;
	
	@SearchField(cellRule="attachName!=''?attachName:''")
	@FieldStyleDirective(tableCellStyle="width:180px;background-color:rgba(255,100,100,0.1)")
	public String attachName;

	@SearchField(cellRule= "#{\"0\":{'PENDING','pin status_pending'}, " +
		  "\"1\":{'DONE','pin status_done'}, " +
		  "\"2\":{'NOTNEED','pin status_notneed'}, " +
		  "\"3\":{'RETRY','pin status_retry'}, " +
		  "\"5\":{'WAIT','pin status_wait'}, " +
		  "\"6\":{'WAIT','pin status_suspend'}, " +
		  "\"4\":{'HOLD','pin status_hold'}}",
		  filterRule="#{'0':'In attesa','1':'Eseguite','3':'Tentativi multipli','4':'Errore'}")
	@FieldStyleDirective(tableCellStyle="width:30px")
	public Integer status = 0;
	

	public Integer errorCount = 0;

	@Owner(holdingRule="user.id",adminRoles="ROLE_SUPER_ADMIN,ROLE_ADMIN")
	public String userId;

	

	public RouteLog() {
	}

	@Override
	public String toString() {
		if(to!=null){
		    return to;
		}
		return "";
	}



}