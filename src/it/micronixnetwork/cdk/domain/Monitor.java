/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.cdk.domain;

import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.ToList;
import it.micronixnetwork.application.plugin.crude.model.ViewModel;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author kobo
 */
@Entity
@Table(name = "batchMonitor")
public class Monitor implements ViewModel {
    
    
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @ToList(defaultOrdered = true,descendant = true)
    @FieldStyleDirective(tableCellStyle = "text-align:center;width:50px")
    @Basic(optional = false)
    @Column(name="Progressivo")
    public Integer id;
    
    @ToList(filtered = true)
    @FieldStyleDirective(tableCellStyle = "text-align:center")
    public String nomejob;
    
    @ToList
    @FieldStyleDirective(tableCellStyle = "width:110px;text-align:center")
    @Temporal(TemporalType.TIMESTAMP)
    public Date dataUltimoAgg;
    
    @ToList
    @FieldStyleDirective(tableCellStyle = "width:110px;text-align:center")
    @Temporal(TemporalType.TIMESTAMP)
    public Date dataFine;
    
    @ToList(filtered = true,filterRule = "#{99:'Corretti',98:'Falliti'}")
    @FieldStyleDirective(tableCellStyleRule = "'text-indent: -9999px;width:30px;background-color:'+(stato==99?'rgba(0,255,0,0.2)':stato==98?'rgba(255,0,0,0.2)':'rgba(255,255,0,0.2)')")
    public Integer stato;
    
    @ToList
    @FieldStyleDirective(tableCellStyle = "text-align:left")
    public String messaggio;
    
 
    public Integer idJob;

    public Monitor() {
    }

    public Monitor(Integer progressivo) {
        this.id = progressivo;
    }

    public Monitor(Integer progressivo, int anno) {
        this.id = progressivo;
    }

    @Override
    public String toString() {
        return "di=" + id + " job= "+nomejob+" stato="+stato;
    }
    
}
