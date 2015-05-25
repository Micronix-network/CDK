/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.cdk.domain;

import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.Group1Directive;
import it.micronixnetwork.application.plugin.crude.annotation.Group2Directive;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.SelectRenderer;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.TextRenderer;
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
@Group1Directive(style="")
@Group2Directive(title="Gruppo.field.2",style="border:solid 1px #aaa;padding:5px;height:280px")

public class AziendaDetail implements ViewModel{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "IdAzienda")
    public Integer id;
    
    @ToView
    @ToInput
    @Column(name = "CodiceAzienda")
    public String codiceAzienda;
    
    @ToView
    @ToInput
    @Column(name = "Descrizione")
    public String descrizione;
    
    @ToView
    @ToInput
    @SelectRenderer(map = "#{'NAV':'NAV','ACG':'ACG','XLS':'XLS'}")
    @Column(name = "SistemaInformativo")
    public String sistemaInformativo;
    
    @ToView
    @Column(name = "IndirizzoIp")
    public String indirizzoIp;
    
    @ToView
    @ToInput
    @Column(name = "UtenteConnessione")
    public String utenteConnessione;
    
    @ToView(masked = true)
    @ToInput
    @Column(name = "PasswordConnessione")
    @TextRenderer(type = "password")
    public String passwordConnessione;
    
    @ToView
    @ToInput
    @Column(name = "TipoConnessione")
    public String tipoConnessione;
    
    @ToView
    @ToInput
    @Column(name = "IpLocale")
    public String ipLocale;
    
    @ToView
    @ToInput
    @Column(name = "UtenteDB")
    public String utenteDB;
    
    @ToView(masked = true)
    @ToInput
    @Column(name = "PasswordDB")
    @TextRenderer(type = "password")
    public String passwordDB;
    
    @ToView
    @ToInput
    @Column(name = "NomeDB")
    @FieldStyleDirective(group=2)
    public String nomeDB;
    
    @ToView
    @ToInput
    @Column(name = "ProfiloConnessione")
    @FieldStyleDirective(group=2)
    public String profiloConnessione;
    
    @ToView
    @ToInput
    @Column(name = "NomeLibreria")
    @FieldStyleDirective(group=2)
    public String nomeLibreria;
    
    @ToView
    @ToInput
    @Column(name = "NomeFonteDati")
    @FieldStyleDirective(group=2)
    public String nomeFonteDati;
    
    @ToView
    @ToInput
    @Column(name = "CartellaFileSystem")
    @FieldStyleDirective(group=2)
    public String cartellaFileSystem;
    
    @ToView
    @ToInput
    @Column(name = "PasswordAutorizzativa")
    @FieldStyleDirective(group=2)
    public String passwordAutorizzativa;
    
    @ToView
    @ToInput
    @Column(name = "ValutaBase")
    @FieldStyleDirective(group=2)
    public String valutaBase;

    @Override
    public String toString() {
        return codiceAzienda+" ["+descrizione+"]";
    }
    
}
