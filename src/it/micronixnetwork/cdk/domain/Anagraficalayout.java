/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.cdk.domain;

import it.micronixnetwork.application.plugin.crude.annotation.SearchField;
import it.micronixnetwork.application.plugin.crude.model.ViewModel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author kobo
 */
@Entity
@Table(name = "anagraficalayout")

public class Anagraficalayout implements ViewModel {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @SearchField(filtered = true)
    @Column(name = "CodiceLayout")
    public String id;
   
    @SearchField(filtered = true)
    @Column(name = "DescrizioneLayout")
    public String descrizioneLayout;
    
    
    @Column(name = "LayoutDescription")
    public String layoutDescription;


    @Override
    public String toString() {
        return "[ " + id + " ]";
    }
    
}
