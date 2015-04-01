/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.cdk.domain;

import it.micronixnetwork.application.plugin.crude.annotation.SearchField;
import it.micronixnetwork.gaf.domain.Published;
import java.io.Serializable;
import javax.persistence.Basic;
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

public class AziendeList implements Published{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "IdAzienda")
    public Integer id;
    
    @SearchField
    @Basic(optional = false)
    @Column(name = "CodiceAzienda")
    public String codiceAzienda;
    
    @SearchField
    @Basic(optional = false)
    @Column(name = "SistemaInformativo")
    public String sistemaInformativo;
    
    @SearchField
    @Column(name = "IndirizzoIp")
    public String indirizzoIp;
    
    @SearchField
    @Column(name = "UtenteConnessione")
    public String utenteConnessione;
    
    @SearchField
    @Column(name = "PasswordConnessione")
    public String passwordConnessione;
    
    @SearchField
    @Column(name = "TipoConnessione")
    public String tipoConnessione;
    
    @SearchField
    @Basic(optional = false)
    @Column(name = "IpLocale")
    public String ipLocale;
    
    @SearchField
    @Basic(optional = false)
    @Column(name = "UtenteDB")
    public String utenteDB;
    
    @SearchField
    @Basic(optional = false)
    @Column(name = "PasswordDB")
    public String passwordDB;
    
    @SearchField
    @Column(name = "NomeDB")
    public String nomeDB;
    
    @SearchField
    @Column(name = "ProfiloConnessione")
    public String profiloConnessione;
    
    @SearchField
    @Column(name = "NomeLibreria")
    public String nomeLibreria;
    
    @SearchField
    @Column(name = "NomeFonteDati")
    public String nomeFonteDati;
    
    @SearchField
    @Column(name = "CartellaFileSystem")
    public String cartellaFileSystem;
    
    @SearchField
    @Column(name = "PasswordAutorizzativa")
    public String passwordAutorizzativa;
    
    @SearchField
    @Column(name = "ValutaBase")
    public String valutaBase;

    public AziendeList() {
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AziendeList)) {
            return false;
        }
        AziendeList other = (AziendeList) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.micronixnetwork.consweb.domain.Aziende[ idAzienda=" + id + " ]";
    }
    
}
