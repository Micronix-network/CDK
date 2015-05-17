/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.cdk.domain;

import it.micronixnetwork.application.plugin.crude.annotation.ToList;
import it.micronixnetwork.application.plugin.crude.model.AutoCreate;
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
@Table(name = "aziende")
public class AziendaMaster implements ViewModel, AutoCreate {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IdAzienda")
    public Integer id;

    @ToList
    @Column(name = "CodiceAzienda")
    public String codiceAzienda;
    
    @ToList(ordered = true,descendant = false,defaultOrdered = true)
    @Column(name = "Descrizione")
    public String descrizione;

    @Column(name = "SistemaInformativo")
    public String sistemaInformativo;

    @Column(name = "IpLocale")
    public String ipLocale;

    @Column(name = "UtenteDB")
    public String utenteDB;

    @Column(name = "PasswordDB")
    public String passwordDB;

    public AziendaMaster() {
    }

    @Override
    public String toString() {
        return "Azienda [ idAzienda=" + id + " ]";
    }

    @Override
    public void initializeState() {
        codiceAzienda = "0";
        ipLocale = "127.0.0.1";
        sistemaInformativo = "NAV";
        passwordDB = "";
        utenteDB = "none";
    }

}
