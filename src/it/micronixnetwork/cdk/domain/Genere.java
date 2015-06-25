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
@Table(name = "generi")
public class Genere implements ViewModel,AutoCreate {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer id;
    
    @Column(name = "idACG")
    @ToView
    @ToInput
    @SelectRenderer(map = "mapByQuery('select g.id,g.nome from Genere$_GenereACG g order by g.nome asc')",viewRule = "_marchioAcg.nome",startValue = "{' ',''}")
    public String idacg;
    
    @OneToOne
    @JoinColumn(name = "idACG", insertable = false, updatable = false)
    public _GenereACG _genereAcg;
 
    @ToView
    @ToInput
    @SelectRenderer(map = "mapByQuery('select g.id,g.nome from Genere$_GenereNAV g order by g.nome asc')",viewRule = "_marchioNav.nome",startValue = "{' ',''}")
    @Column(name = "idNav")
    public String idnav;
    
    @OneToOne
    @JoinColumn(name = "idNav", insertable = false, updatable = false)
    public _GenereNAV _genereNav;
    
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
    @Table(name="generiacg")
    public static class _GenereACG implements Serializable {
    	private static final long serialVersionUID = 1L;

    	@Id
        @Column(name = "IdGenere")
        public String id;
        
        @Column(name = "NomeGenere")
        public String nome;

    	public _GenereACG() {
    	}
    }
    
     
    @Entity
    @Table(name="generinav")
    public static class _GenereNAV implements Serializable {
    	private static final long serialVersionUID = 1L;

    	@Id
        @Column(name = "IdGenere")
        public String id;
        
        @Column(name = "NomeGenere")
        public String nome;

    	public _GenereNAV() {
    	}
    }
    
}
