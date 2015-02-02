package it.micronixnetwork.tcd.domain;

import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.Owner;
import it.micronixnetwork.application.plugin.crude.annotation.SearchField;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.annotation.ValidField;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.TextAreaRenderer;
import it.micronixnetwork.gaf.domain.Published;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rubrica")
public class Rubrica implements Serializable, Published {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer id;
	
	@ToView
	@ToInput
	@SearchField(filtered=true)
	public String nome;
	
	@ToView
	@ToInput
	@SearchField(draggable="forAddress")
	@FieldStyleDirective(inputFieldStyle="width:200px")
	@ValidField(empty=false,type="email")
	public String email;
	
	@ToView
	@ToInput
	@TextAreaRenderer(height=100)
	public String descrizione;
	
	@Owner
	public String idUte;
	
	@Override
	public String toString() {
	    if(nome!=null) return nome;
	    return email;
	}
    
}
