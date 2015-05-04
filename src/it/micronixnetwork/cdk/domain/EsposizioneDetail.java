/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.cdk.domain;

import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
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
public class EsposizioneDetail implements ViewModel {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IdLayout")
    public Integer id;
    
    @ToView
    @ToInput
    @Column(name = "CodiceLayout")
    public String codiceLayout;
    
    @ToView
    @ToInput
    @Column(name = "CodiceOggetto")
    public String codiceOggetto;
    
    @ToView
    @ToInput
    @Column(name = "Sequenza")
    public Integer sequenza;
    
    @ToView
    @ToInput
    @FieldStyleDirective(inputFieldStyle="width:300px;font-size:12px")
    @Column(name = "Descrizione")
    
    public String descrizione;
    
    @ToView
    @ToInput
    @Column(name = "Andamento")
    public Integer andamento;
    
    @ToView
    @ToInput
    @Column(name = "Risalto")
    public String risalto;
    
    @ToView
    @ToInput
    @Column(name = "Indentazione")
    public Integer indentazione;


    @Override
    public String toString() {
        return descrizione;
    }
    
}
