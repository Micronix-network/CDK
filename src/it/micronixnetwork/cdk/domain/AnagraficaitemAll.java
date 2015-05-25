/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.cdk.domain;

import it.micronixnetwork.application.plugin.crude.annotation.ToList;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
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
    @ToList(filtered = true)
    @ToInput
    @ToView
    @Column(name = "CodiceItem")
    public String id;
    
    @ToList(filtered = true)
    @ToInput
    @ToView
    @Column(name = "DescrizioneItem")
    public String descrizioneItem;
    
    @ToList(filtered = true)
    @ToInput
    @ToView
    @Column(name = "ItemDescription")
    public String itemDescription;
    
    @ToList(filtered = true)
    @ToInput
    @ToView
    @Column(name = "TipoItem")
    public String tipoItem;

    @ToList(filtered = true)
    @ToInput
    @ToView
    @Override
    public String toString() {
        return "[ " + id + " ]";
    }
    
}
