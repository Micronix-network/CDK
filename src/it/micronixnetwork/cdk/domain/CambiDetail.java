/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.cdk.domain;

import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.model.ViewModel;
import java.math.BigDecimal;
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
@Table(name = "cambi")
public class CambiDetail implements ViewModel {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    public Integer id;
    
    @ToView
    @ToInput
    @Column(name = "valuta")
    public String valuta;
    
    @ToView
    @ToInput
    @Column(name = "anno")
    public Integer anno;
    
    @ToView
    @ToInput
    @Column(name = "mese")
    public Integer mese;
    
    @ToView
    @ToInput
    @Column(name = "cambiobs")
    public BigDecimal cambiobs;
    
    @ToView
    @ToInput
    @Column(name = "cambiopl")
    public BigDecimal cambiopl;

    @Override
    public String toString() {
        return "[ " + valuta + " ]";
    }
    
}
