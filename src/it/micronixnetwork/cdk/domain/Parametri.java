/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.cdk.domain;

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
@Table(name = "parametri")

public class Parametri implements ViewModel {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "Id")
    public Integer id;
    @Column(name = "Descrizione")
    public String descrizione;
    @Column(name = "Valore")
    public String valore;
    @Column(name = "Tipo")
    public String tipo;

    @Override
    public String toString() {
        return "[ " + descrizione + " ]";
    }
    
}
