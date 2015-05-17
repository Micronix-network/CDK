/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.cdk.domain;

import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToList;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.SelectRenderer;
import it.micronixnetwork.application.plugin.crude.model.ViewModel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author kobo
 */
@Entity
@Table(name = "indicatori2")

public class MappingDetail implements ViewModel {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdItem")
    public Integer id;
    
    @Column(name = "VoceSpesa")
    @ToList
    public String voceSpesa;
    
    @ToList(filtered = true,listed = false,filterRule="mapByQuery('select a.descrizione,a.id from MappingDetail$_Azienda a order by a.descrizione asc')")
    @ToInput
    @ToView
    @SelectRenderer(activeOnChange = "sottoconto",map = "mapByQuery('select a.id,a.descrizione from MappingDetail$_Azienda a order by a.descrizione asc')",viewRule = "azienda.descrizione")
    @Column(name = "IdAzienda")
    public Integer idAzienda;
    
    @OneToOne
    @JoinColumn(name = "idAzienda", insertable = false, updatable = false)
    public _Azienda azienda;
   
    @ToList(filtered = true,hidden = true)
    @Column(name = "Item")
    public String item;
    
    @OneToOne
    @JoinColumn(name = "Item", insertable = false, updatable = false)
    public _Item parent;
    
    @ToList
    @Column(name = "Coefficiente")
    public Double coefficiente;
    
    @ToList
    @Column(name = "Prefisso")
    public Character prefisso;
    
    @Column(name = "patch")
    public Integer patch;
    
    @ToList
    @Column(name = "Moltiplicatore")
    public String moltiplicatore;
    
    @ToList
    @Column(name = "Diretto")
    public Boolean diretto;
    
    @ToList
    @Column(name = "Priorita")
    public int priorita;
    
    @ToList(cellRule = "_sottoconto.descrizione")
    @ToInput
    @ToView
    @SelectRenderer(map = "mapByQuery('select s.codice,s.descrizione from MappingDetail$_Sottoconto s order by s.descrizione asc')",viewRule = "_sottoconto.descrizione",dependFrom = "idAzienda")
    @Column(name = "Sottoconto")
    public String sottoconto;
    
    @OneToOne
    @JoinColumns({
        @JoinColumn(name="sottoconto", referencedColumnName="CodiceConto", insertable = false, updatable = false),
        @JoinColumn(name="idAzienda", referencedColumnName="idAzienda", insertable = false, updatable = false)
    })
    public _Sottoconto _sottoconto;
    
    @Column(name = "CentroCostoUfficio")
    public String centroCostoUfficio;
    
    @Column(name = "Consociata")
    public boolean consociata;
    
    @Column(name = "Brand")
    public String brand;
    
    @Column(name = "TipoProdotto")
    public String tipoProdotto;
    
    @Column(name = "Genere")
    public String genere;
    
    @Column(name = "Stagione")
    public String stagione;

    @Override
    public String toString() {
        return "MappingDetail[ idItem=" + id + " ]";
    }
    
    
    
    @Entity
    @Table(name="aziende")
    public static class _Azienda implements Serializable {
    	private static final long serialVersionUID = 1L;

    	@Id
        @Column(name = "IdAzienda")
        public Integer id;

        @ToList(ordered = true,defaultOrdered = true)
    	@Column(name = "Descrizione")
        public String descrizione;

    	public _Azienda() {
    	}
    }
    
    @Entity
    @Table(name = "anagraficaitem")
    public static class _Item implements Serializable {
            
            @Id
            @Column(name = "CodiceItem")
            public String id;
        
            @Column(name = "TipoItem")
            public String tipoItem;
    }
    
    @Entity
    @Table(name = "sottoconti")
    public static class _Sottoconto implements Serializable {
            
            @Id
            @Column(name = "idSottoconto")
            public String id;
        
            @Column(name = "IdAzienda")
            public Integer idAzienda;
            
            @Column(name = "CodiceConto")
            public String codice;
            
            @Column(name = "DescrizioneConto")
            public String descrizione;
    }
    
}
