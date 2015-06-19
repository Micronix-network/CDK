/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.cdk.domain;

import it.micronixnetwork.application.plugin.crude.model.ViewModel;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author kobo
 */
@Entity
@Table(name = "movimenti")

public class MovimentiDetail implements ViewModel {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "IdMovimento")
    public Integer id;
    
    @Column(name = "Prefisso")
    public String prefisso;
    
    @Column(name = "VoceSpesa")
    public String vocespesa;
    
    @Column(name = "IdAzienda")
    public String idAzienda;
    
    @Column(name = "Divisione")
    public String divisione;
    
    @Column(name = "Gruppo")
    public String gruppo;
    
    @Column(name = "Causale")
    public String causale;
    
    @Column(name = "TipoMovimento")
    public String tipoMovimento;
     
    @Column(name = "SottocontoContabile")
    public String sottocontoContabile;
    
    @Column(name = "Contropartita")
    public String contropartita;
    
    @Column(name = "CentroCosto")
    public String centrocosto;
    
    @Column(name = "Commessa")
    public String commessa;
    
    @Column(name = "Partner")
    public String partner;
    
    @Column(name = "FlagCliFor")
    public String flagCliFor;
    
    @Column(name = "ClienteFornitore")
    public String clienteFornitore;
    
    @Column(name = "Genere")
    public String genere;
    
    @Column(name = "Marchio")
    public String marchio;
    
    @Column(name = "Stabilimento")
    public String stabilimento;
    
    @Column(name = "TipoCollezione")
    public String tipoCollezione;
    
    @Column(name = "TipoProdotto")
    public String tipoProdotto;
    
    @Column(name = "Ufficio")
    public String ufficio;
    
    @Column(name = "Stagione")
    public String stagione;
 
    @Column(name = "TipoProduzione")
    public String tipoProduzione;
    
    @Column(name = "DataMovimentoGen")
    @Temporal(TemporalType.DATE)
    public Date dataMovimentoGen;
    
    @Column(name = "NumeroMovimentoGen")
    public Integer numeroMovimentoGen;
    
    @Column(name = "DareAvere")
    public String dareAvere;
    
    @Column(name = "Importo")
    public BigDecimal importo;
    
    @Column(name = "Descrizione")
    public String descrizione;
    
    @Column(name = "DescrizioneSupplementare")
    public String descrizioneSupplementare;
    
    @Column(name = "Utente")
    public String utente;
    
    @Column(name = "Azienda")
    public String azienda;
    
    @Column(name = "NumeroRiga")
    public Integer numeroRiga;
    
    @Column(name = "CambioBase")
    public Double cambioBase;
    
    @Column(name = "Provenienza")
    public String provenienza;
    
    @Column(name = "Cambio")
    public Double cambio;
    
    @Column(name = "Valuta")
    public String valuta;
    
    @Column(name = "Provvisorio")
    public String provvisorio;
    
    @Column(name = "TipoOrigine")
    public String tipoOrigine;
    
    @Column(name = "ImportoEuro")
    public BigDecimal importoEuro;
    
    @Column(name = "importoSegnato")
    public BigDecimal importoSegnato;
    
    @Column(name = "AnnoRiferimento")
    public Integer annoRiferimento;
    
    @Column(name = "MeseEsercizio")
    public Integer meseEsercizio;
    
    @Column(name = "NomeMese")
    public String nomeMese;
    
    
    @Override
    public String toString() {
        return "[ " + id + ": "+descrizione+" ]";
    }
    
}
