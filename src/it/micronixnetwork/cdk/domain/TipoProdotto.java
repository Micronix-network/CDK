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
import it.micronixnetwork.application.plugin.crude.model.AutoCreate;
import it.micronixnetwork.application.plugin.crude.model.ViewModel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author kobo
 */
@Entity
@Table(name = "tipoprodotto")
public class TipoProdotto implements ViewModel,AutoCreate {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer id;
    
    @Column(name = "idACG")
    @ToView
    @ToInput
    @SelectRenderer(map = "mapByQuery('select t.id,t.nome from TipoProdotto$_TipoProdottoACG t order by t.nome asc')",viewRule = "_tipoProdottoAcg.nome",startValue = "{' ',''}")
    public String idacg;
    
    @OneToOne
    @JoinColumn(name = "idACG", insertable = false, updatable = false)
    public _TipoProdottoACG _tipoProdottoAcg;
 
    @ToView
    @ToInput
    @SelectRenderer(map = "mapByQuery('select t.id,t.nome from TipoProdotto$_TipoProdottoNAV t order by t.nome asc')",viewRule = "_tipoProdottoNav.nome",startValue = "{' ',''}")
    @Column(name = "idNav")
    public String idnav;
    
    @OneToOne
    @JoinColumn(name = "idNav", insertable = false, updatable = false)
    public _TipoProdottoNAV _tipoProdottoNav;
    
    @ToList
    @ToInput
    @ToView
    @Column(name = "Descrizione")
    public String descrizione;

    @Override
    public void initializeState() {
        idacg=null;
        idnav=null;
        descrizione="NEW";
    } 

    @Override
    public String toString() {
        return descrizione;
    }
    
    @Entity
    @Table(name="tipoprodottoacg")
    public static class _TipoProdottoACG implements Serializable {
    	private static final long serialVersionUID = 1L;

    	@Id
        @Column(name = "IdProdotto")
        public String id;
        
        @Column(name = "NomeProdotto")
        public String nome;

    	public _TipoProdottoACG() {
    	}
    }
    
     
    @Entity
    @Table(name="tipoprodottonav")
    public static class _TipoProdottoNAV implements Serializable {
    	private static final long serialVersionUID = 1L;

    	@Id
        @Column(name = "IdProdotto")
        public String id;
        
        @Column(name = "NomeProdotto")
        public String nome;

    	public _TipoProdottoNAV() {
    	}
    }
    
}
