package it.micronixnetwork.application.plugin.defaultcheckin.domain;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "members_authorities")
public class DefaultUserAuthority implements Serializable{
private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        public Integer id;
	
	@Transient
	public String userId;
	
        public String authority;
	
        public Short priority;
	
	public DefaultUserAuthority() {
		super();
	}
	
	@Override
	    public String toString() {
		if(authority.equals("ROLE_ADMIN")) return "Admin";
		return "Configurator";
	    }


}

