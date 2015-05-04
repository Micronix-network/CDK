/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.cdk.domain;

import it.micronixnetwork.application.plugin.crude.model.ViewModel;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
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
@Table(name = "cambi")
public class Cambi implements ViewModel {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    public Integer id;
    @Column(name = "valuta")
    public String valuta;
    @Column(name = "anno")
    public Integer anno;
    @Column(name = "mese")
    public Integer mese;
    @Column(name = "cambiobs")
    public BigDecimal cambiobs;
    @Column(name = "cambiopl")
    public BigDecimal cambiopl;

    @Override
    public String toString() {
        return "it.micronixnetwork.cdk.domain.Cambi[ id=" + id + " ]";
    }
    
}
