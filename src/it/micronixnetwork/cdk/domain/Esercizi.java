/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.cdk.domain;

import it.micronixnetwork.application.plugin.crude.model.ViewModel;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kobo
 */
@Entity
@Table(name = "esercizi")

public class Esercizi implements ViewModel {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public  Integer id;
    
    @Column(name = "IdAzienda")
    public int idAzienda;
   
    @Column(name = "Esercizio")
    public int esercizio;
   
    @Column(name = "Classificazione")
    public String classificazione;
   
    @Column(name = "Quota")
    public String quota;
    

    @Override
    public String toString() {
        return "it.micronixnetwork.cdk.domain.Esercizi[ eserciziPK=" + id + " ]";
    }
    
}
