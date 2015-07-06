/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.cdk.domain;

import it.micronixnetwork.application.plugin.crude.annotation.ToList;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.annotation.ValidField;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.SelectRenderer;
import it.micronixnetwork.application.plugin.crude.model.ViewModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author kobo
 */
@Entity
@Table(name = "anagraficaitem")

public class AnagraficaitemAll implements ViewModel {
    private static final long serialVersionUID = 1L;
    
    @Id
    @ToList(filtered = true,fullTextSearch = false)
    @ToInput
    @ToView
    @Column(name = "CodiceItem")
    @ValidField(empty = false)
    public String id;
    
    @ToList(filtered = true,fullTextSearch = false)
    @ToInput
    @ToView
    @Column(name = "DescrizioneItem")
    @ValidField(empty = false)
    public String descrizioneItem;
    
    @ToList(filtered = true,fullTextSearch = false)
    @ToInput
    @ToView
    @Column(name = "ItemDescription")
    public String itemDescription;
    
    @ToList(filtered = true,filterRule = "#{'B':'Base','D':'Derivato'}")
    @ToInput
    @ToView
    @SelectRenderer(map = "#{'B':'Base','D':'Derivato'}")
    @Column(name = "TipoItem")
    @ValidField(empty = false)
    public String tipoItem;

  
    public String toString() {
        return "[ " + id + " ]";
    }
    
}
