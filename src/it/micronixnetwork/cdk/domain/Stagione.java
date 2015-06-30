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
@Table(name = "stagioni")
public class Stagione implements ViewModel,AutoCreate {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer id;
    
    @Column(name = "idacg")
    @ToView
    @ToInput
    @SelectRenderer(map = "mapByQuery('select s.id,s.nome from Stagione$_StagioneACG s order by s.nome asc')",viewRule = "_stagioneAcg.nome")
    public String idacg;
    
    @OneToOne
    @JoinColumn(name = "idacg", insertable = false, updatable = false)
    public _StagioneACG _stagioneAcg;
 
    @ToView
    @ToInput
    @SelectRenderer(map = "mapByQuery('select s.id,s.nome from Stagione$_StagioneNAV s order by s.nome asc')",viewRule = "_stagioneNav.nome")
    @Column(name = "idnav")
    public String idnav;
    
    @OneToOne
    @JoinColumn(name = "idnav", insertable = false, updatable = false)
    public _StagioneNAV _stagioneNav;
    
    @ToList
    @ToInput
    @ToView
    @Column(name = "descrizione")
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
    @Table(name="stagioniacg")
    public static class _StagioneACG implements Serializable {
    	private static final long serialVersionUID = 1L;

    	@Id
        @Column(name = "IdStagione")
        public String id;
        
        @Column(name = "NomeStagione")
        public String nome;

    	public _StagioneACG() {
    	}
    }
    
     
    @Entity
    @Table(name="stagioninav")
    public static class _StagioneNAV implements Serializable {
    	private static final long serialVersionUID = 1L;

    	@Id
        @Column(name = "IdStagione")
        public String id;
        
        @Column(name = "NomeStagione")
        public String nome;

    	public _StagioneNAV() {
    	}
    }
    
}
