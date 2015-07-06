/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.cdk.domain;

import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToList;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.SelectRenderer;
import it.micronixnetwork.application.plugin.crude.model.ViewModel;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author kobo
 */
@Entity
@Table(name = "aziendeconsolidato")

public class Aziendeconsolidato implements ViewModel {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    public Integer id;
    
    @ToList
    @ToView
    @Column(name = "Codice")
    public String codice;
    
    @ToList
    @ToView
    @Column(name = "DescrizioneNav")
    public String descrizioneNav;
    
    @ToList
    @ToView
    @Column(name = "DescrizioneACG")
    public String descrizioneACG;
    
    @ToList
    @ToView
    @ToInput
    @FieldStyleDirective(tableCellStyle = "width:50px;text-align:center")
    @SelectRenderer(map="#{'1':'Si','0':'No'}")
    @Column(name = "FlagConsolidato")
    public Integer flagConsolidato;
    

    public Aziendeconsolidato() {
    }

    public Aziendeconsolidato(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return codice;
    }

    
    
}
