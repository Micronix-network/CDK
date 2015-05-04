/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.cdk.domain;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kobo
 */
@Entity
@Table(name = "indicatori2")

public class Indicatori2 implements ViewModel {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IdItem")
    public Integer id;
     
    @Column(name = "Origine")
    public String origine;
    
    @Column(name = "Item")
    public String item;
    
    @Column(name = "Coefficiente")
    public Double coefficiente;
    
    @Column(name = "Prefisso")
    public Character prefisso;
    
    @Column(name = "Tipo")
    public Character tipo;
    
    @Column(name = "patch")
    public Integer patch;
   
    @Column(name = "IdAzienda")
    public Integer idAzienda;
    
    @Column(name = "Moltiplicatore")
    public String moltiplicatore;
    
    @Column(name = "Diretto")
    public Boolean diretto;
    
    @Column(name = "Priorita")
    public Integer priorita;

    @Override
    public String toString() {
        return "[ " + id + " ]";
    }
    
}
