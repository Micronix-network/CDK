/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.cdk.domain;

import it.micronixnetwork.application.plugin.crude.annotation.SearchField;
import it.micronixnetwork.application.plugin.crude.model.AutoCreate;
import it.micronixnetwork.application.plugin.crude.model.ViewModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author kobo
 */
@Entity
@Table(name = "esposizione")
public class EsposizioneMaster implements ViewModel, AutoCreate {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IdLayout")
    public Integer id;
    
    @SearchField
    @Column(name = "CodiceLayout")
    public String codiceLayout;
    
    @Column(name = "CodiceOggetto")
    public String codiceOggetto;
    
    @SearchField(filtered = true)
    @Column(name = "Descrizione")
    public String descrizione;
    
    @Column(name = "Indentazione")
    public Integer indentazione;

    public EsposizioneMaster() {
    }

    public EsposizioneMaster(String codiceLayout, String codiceOggetto, int indentazione) {
        this.codiceLayout = codiceLayout;
        this.codiceOggetto = codiceOggetto;
        this.indentazione = indentazione;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public void initializeState() {
        this.codiceLayout="";
        this.codiceOggetto = "NONE";
        this.indentazione = 0;
    }
    
}
