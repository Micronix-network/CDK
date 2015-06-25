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
import it.micronixnetwork.application.plugin.crude.annotation.ValidField;
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
@Table(name = "anagraficalayout")

public class Anagraficalayout implements ViewModel {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @ToList(filtered = true)
    @ToView
    @ToInput
    @FieldStyleDirective(tableCellStyle = "width:50px;text-align:center")
    @ValidField(empty = false, maxsize = 2)
    @Column(name = "CodiceLayout")
    public String id;
   
    @ToList(filtered = true)
    @ToInput
    @ValidField(empty = false)
    @FieldStyleDirective(inputFieldStyle = "width:330px",tableCellStyle = "width:110px;text-align:left")
    @Column(name = "DescrizioneLayout")
    public String descrizioneLayout;
    
    @ToList(filtered = true)
    @ToInput
    @ValidField(empty = false)
    @FieldStyleDirective(inputFieldStyle = "width:330px",tableCellStyle = "width:110px;text-align:left")
    @Column(name = "LayoutDescription")
    public String layoutDescription;


    @Override
    public String toString() {
        return id;
    }
    
}
