/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.cdk.domain;

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
@Table(name = "anagraficaitem")

public class AnagraficaitemDetail implements ViewModel {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "CodiceItem")
    public String id;
    
    @Column(name = "DescrizioneItem")
    public String descrizioneItem;
    @Column(name = "ItemDescription")
    public String itemDescription;
    
    @Column(name = "TipoItem")
    public String tipoItem;

    @Override
    public String toString() {
        return "[ " + id + " ]";
    }
    
}
