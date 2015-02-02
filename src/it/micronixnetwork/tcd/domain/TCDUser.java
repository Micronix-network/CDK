package it.micronixnetwork.tcd.domain;

import it.micronixnetwork.application.plugin.crude.annotation.Children;
import it.micronixnetwork.application.plugin.crude.annotation.Computed;
import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.GlobalFilter;
import it.micronixnetwork.application.plugin.crude.annotation.Owner;
import it.micronixnetwork.application.plugin.crude.annotation.SearchField;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.annotation.ValidField;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.SelectRenderer;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.TextRenderer;
import it.micronixnetwork.gaf.domain.Published;
import it.micronixnetwork.gaf.domain.RoledUser;

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
import javax.persistence.ManyToOne;
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
@GlobalFilter(filter = "id<>'admin'")
public class TCDUser implements RoledUser, Published {

    public static String DEFAULT_ROLE = "ROLE_USER";
    public final static String APP_ROLE = "APPLICATION_USER";

    private static final long serialVersionUID = 1L;

    @Id
    @Computed(value = "legacycode")
    public String id;
    
    @ToView
    @ToInput
    @SearchField(filtered = true)
    @FieldStyleDirective(tableCellStyle="width:70px;background-color:rgba(100,255,100,0.1)")
    @ValidField(empty = false, type = "")
    public String legacycode;

    @ToView
    @ToInput
    @SearchField(filtered = true)
    @FieldStyleDirective(tableCellStyle="width:70px")
    @ValidField(empty = false, type = "")
    public String username;

    @ToView
    @ToInput
    @SearchField(filtered = true)
    @FieldStyleDirective(tableCellStyle="width:150px",inputFieldStyle="max-width:150px")
    @ValidField(empty = false, type = "")
    public String email;

    @ToView
    @ToInput
    @SearchField(filtered = true)
    @FieldStyleDirective(tableCellStyle="width:150px;")
    @ValidField(empty = false, type = "")
    public String name;
    
    @ToView
    @ToInput
    @ValidField(empty = true, type = "")
    public String printer;


    public String company;

    @ToView
    @ToInput
    @TextRenderer(type="date",initValue="@it.micronixnetwork.gaf.util.DateUtil@getDayDifference(new java.util.Date(),730)")
    @ValidField(empty = false, type = "date")
    @Temporal(TemporalType.DATE)
    public Date expiration_Date;


    @ToView(hidden=true)
    @ToInput(encoded=true)
    @TextRenderer(type = "password")
    @FieldStyleDirective(tableCellStyle="width:50px")
    @ValidField(empty = false, type = "")
    public String password;

    public String phone;

    public String question;

    @Column(name = "question_answer")
    public String questionAnswer;

    @SearchField(filtered = false,cellRule="getText('TCDUser.'+applicaionRole)",roles="ROLE_ADMIN")
    @FieldStyleDirective(tableCellStyle="width:70px")
    @Transient
    public String role;

    @ToView
    @ToInput(roles="ROLE_ADMIN")
    @OneToMany(fetch = FetchType.EAGER, targetEntity = TCDUserAuthority.class,cascade=CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "id",nullable = false)
    @OrderBy("id")
    @Children
    public Set<TCDUserAuthority> authority=new LinkedHashSet<TCDUserAuthority>();

    @CollectionOfElements(fetch = FetchType.EAGER)
    @JoinTable(name = "members_attributes", joinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"))
    @MapKey(columns = @Column(name = "name"))
    @Column(name = "value")
    public Map<String, String> attributes = new HashMap<String, String>();

    @ToView
    @ToInput
    @SearchField(filtered=true,hidden=true)
    @ValidField(empty = false, type = "")
    @SelectRenderer(map = "mapByQuery('select distinct r.id,r.descrizione from Reparto r order by 1 desc')", startValue = "{' ',''}",viewRule="reparto.descrizione")
    @Owner(adminRoles = "ROLE_ADMIN",holdingRule="user.idRep")
    public Integer idRep;

    @Transient
    @SearchField(cellRule="reparto.descrizione",roles="ROLE_ADMIN")
    public String descReparto;

    @ManyToOne
    @JoinColumn(name="idRep", insertable = false, updatable = false)
    private Reparto reparto;

    @ToInput
    @ToView
    @SelectRenderer(map = "#{'Active':'1','Disabled':'0'}",viewRule = "#{\"1\":{'Active','img_green'}, \"0\":{'Disabled','img_red'}}")
    @SearchField(filtered = true,cellRule = "#{\"1\":{'Active','img_green'}, \"0\":{'Disabled','img_red'}}",filterRule="#{'1':'Active','0':'Disabled'}")
    @FieldStyleDirective(tableCellStyle="width:30px;text-align:center")
    @ValidField(empty = false, type = "")
    public Integer enabled;

    public TCDUser() {
    }

    public Reparto getReparto() {
	return reparto;
    }

    public void setReparto(Reparto reparto) {
	this.reparto = reparto;
    }

    public String getId() {
	return this.id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getCompany() {
	return this.company;
    }

    public void setCompany(String company) {
	this.company = company;
    }

    public String getEmail() {
	return this.email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public Integer getEnabled() {
	return this.enabled;
    }

    public void setEnabled(Integer enabled) {
	this.enabled = enabled;
    }

    public Date getExpiration_Date() {
	return this.expiration_Date;
    }

    public void setExpiration_Date(Date expiration_Date) {
	this.expiration_Date = expiration_Date;
    }

    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getPassword() {
	return this.password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getPhone() {
	return this.phone;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    public String getQuestion() {
	return this.question;
    }

    public void setQuestion(String question) {
	this.question = question;
    }

    public String getQuestionAnswer() {
	return this.questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
	this.questionAnswer = questionAnswer;
    }

    public String getUsername() {
	return this.username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    @Transient
    public String[] getRoles() {
	if (authority != null) {
	    String[] roles = new String[authority.size() + 2];
	    roles[0] = DEFAULT_ROLE;
	    roles[1] = APP_ROLE;
	    int i = 2;
	    for (TCDUserAuthority grant : authority) {
		roles[i] = grant.authority;
		i++;
	    }
	    return roles;
	}
	return null;
    }

    @Transient
    public String getUserName() {
	return username;
    }

    @Transient
    @Override
    public String getAttribute(String name) {
	if (attributes != null) {
	    return attributes.get(name);
	}
	return null;
    }

    public Map<String, String> getAttributes() {
	return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
	this.attributes = attributes;
    }

    @Override
    public String getApplicaionRole() {
	String result=null;
	Short prior=0;
	for (TCDUserAuthority grant : authority) {
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

}