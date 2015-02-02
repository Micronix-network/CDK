package it.micronixnetwork.application.plugin.defaultcheckin.domain;

import it.micronixnetwork.gaf.domain.Published;
import it.micronixnetwork.gaf.domain.RoledUser;
import java.io.Serializable;


import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.MapKey;

/**
 * The persistent class for the acc_account database table.
 *
 */
@Entity
@Table(name = "members")
public class DefaultUser implements RoledUser, Serializable {

    public static String DEFAULT_ROLE = "ROLE_USER";
    public final static String APP_ROLE = "APPLICATION_USER";

    private static final long serialVersionUID = 1L;

    @Id
    public String id;
   
    public String username;

    public String email;

    public String name;

    public String company;

    @Temporal(TemporalType.DATE)
    public Date expiration_Date;

    public String password;

    public String phone;

    public String question;

    @Column(name = "question_answer")
    public String questionAnswer;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = DefaultUserAuthority.class,cascade=CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "id",nullable = false)
    @OrderBy("id")
    public Set<DefaultUserAuthority> authority=new LinkedHashSet<DefaultUserAuthority>();

    @CollectionOfElements(fetch = FetchType.EAGER)
    @JoinTable(name = "members_attributes", joinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"))
    @MapKey(columns = @Column(name = "name"))
    @Column(name = "value")
    public Map<String, String> attributes = new HashMap<String, String>();

    public Integer enabled;

    public DefaultUser() {
    }

    @Transient
    public String[] getRoles() {
	if (authority != null) {
	    String[] roles = new String[authority.size() + 2];
	    roles[0] = DEFAULT_ROLE;
	    roles[1] = APP_ROLE;
	    int i = 2;
	    for (DefaultUserAuthority grant : authority) {
		roles[i] = grant.authority;
		i++;
	    }
	    return roles;
	}
	return null;
    }


    @Transient
    @Override
    public String getAttribute(String name) {
	if (attributes != null) {
	    return attributes.get(name);
	}
	return null;
    }

    @Override
    public String getApplicaionRole() {
	String result=null;
	Short prior=0;
	for (DefaultUserAuthority grant : authority) {
		if(grant.priority>prior){
		    result=grant.authority;
		    prior=grant.priority;
		}
	}
	if(result==null){
	    result=APP_ROLE;
	}
	return result.replace("ROLE_", "").toLowerCase();
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Integer getEnabled() {
        return enabled;
    }

}