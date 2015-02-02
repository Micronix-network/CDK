package it.micronixnetwork.tcd.domain;

import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.SearchField;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.annotation.ValidField;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.SelectRenderer;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "members_authorities")
public class TCDUserAuthority implements Serializable{
private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        public Integer id;
	
	@Transient
	public String userId;
	
        @ToInput
	@SearchField(filtered=false,cellRule = "#{'ROLE_ADMIN':'Admin','ROLE_CONFIGURATOR':'Configurator'}")
	@ToView
	@SelectRenderer(map = "#{'Admin':'ROLE_ADMIN','Configurator':'ROLE_CONFIGURATOR'}",viewRule="#{'ROLE_ADMIN':'Admin','ROLE_CONFIGURATOR':'Configurator'}")
	@ValidField(empty=false,type="")
        public String authority;
	
        @ToInput
	@SearchField(filtered=false,cellRule = "#{\"1\":'Primary',\"0\":'Secondary'}")
	@FieldStyleDirective(tableCellStyle="width:150px")
	@ToView
	@SelectRenderer(map = "#{'Primary':1,'Secondary':0}",viewRule="#{\"1\":'Primary',\"0\":'Secondary'}")
	@ValidField(empty=false,type="")
        public Short priority;
	
	public TCDUserAuthority() {
		super();
	}
	
	@Override
	    public String toString() {
		if(authority.equals("ROLE_ADMIN")) return "Admin";
		return "Configurator";
	    }


}

