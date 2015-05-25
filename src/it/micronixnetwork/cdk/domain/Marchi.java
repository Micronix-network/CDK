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
@Table(name = "marchi")
public class Marchi implements ViewModel,AutoCreate {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer id;
    
    @Column(name = "idacg")
    @ToView
    @ToInput
    @SelectRenderer(map = "mapByQuery('select m.id,m.id from Marchi$_MarchioACG m order by m.nome asc')",viewRule = "_marchioAcg.nome")
    public String idacg;
    
    @OneToOne
    @JoinColumn(name = "idacg", insertable = false, updatable = false)
    public _MarchioACG _marchioAcg;
 
    @ToView
    @ToInput
    @SelectRenderer(map = "mapByQuery('select m.id,m.nome from Marchi$_MarchioNAV m order by m.nome asc')",viewRule = "_marchioNav.nome")
    @Column(name = "idnav")
    public String idnav;
    
    @OneToOne
    @JoinColumn(name = "idnav", insertable = false, updatable = false)
    public _MarchioNAV _marchioNav;
    
    @ToList
    @ToInput
    @ToView
    @Column(name = "descrizione")
    public String descrizione;

    @Override
    public void initializeState() {
        idacg="020";
        idnav="99";
        descrizione="NEW";
    } 
    
    
    @Entity
    @Table(name="marchiacg")
    public static class _MarchioACG implements Serializable {
    	private static final long serialVersionUID = 1L;

    	@Id
        @Column(name = "IdMarchio")
        public String id;
        
        @Column(name = "NomeMarchio")
        public String nome;

    	public _MarchioACG() {
    	}
    }
    
     
    @Entity
    @Table(name="marchinav")
    public static class _MarchioNAV implements Serializable {
    	private static final long serialVersionUID = 1L;

    	@Id
        @Column(name = "IdMarchio")
        public String id;
        
        @Column(name = "NomeMarchio")
        public String nome;

    	public _MarchioNAV() {
    	}
    }
    
}
