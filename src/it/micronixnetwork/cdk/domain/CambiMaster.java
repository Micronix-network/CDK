/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.cdk.domain;

import it.micronixnetwork.application.plugin.crude.annotation.ToList;
import it.micronixnetwork.application.plugin.crude.model.AutoCreate;
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
public class CambiMaster implements ViewModel,AutoCreate {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer id;
    
    @ToList
    @Column(name = "valuta")
    public String valuta;
    
    @ToList
    @Column(name = "anno")
    public Integer anno;
    
    @ToList
    @Column(name = "mese")
    public Integer mese;

    @Override
    public String toString() {
        return "[ " + valuta + " ]";
    }

    @Override
    public void initializeState() {
        valuta="NEW";
    }

}
