/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.cdk.domain;


import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToList;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.annotation.ValidField;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.HiddenRenderer;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.SelectRenderer;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.TextRenderer;
import it.micronixnetwork.application.plugin.crude.model.ViewModel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author kobo
 */
@Entity
@Table(name = "indicatori2")

public class MappingDerivatiDetail implements ViewModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdItem")
    public Integer id;

    @ToList(filtered = true, listed = false, filterRule = "mapByQuery('select a.id,a.descrizione from MappingDerivatiDetail$_Azienda a order by a.descrizione asc')")
    @ToInput
    @ToView
    @ValidField(empty = false, type = "")
    @SelectRenderer(map = "mapByQuery('select a.id,a.descrizione from MappingDerivatiDetail$_Azienda a order by a.descrizione asc')", viewRule = "azienda.descrizione", startValue = "{' ',''}")
    @Column(name = "IdAzienda")
    public Integer idAzienda;
    
    @OneToOne
    @JoinColumn(name = "idAzienda", insertable = false, updatable = false)
    public _Azienda azienda;

    @ToList(cellRule = "_origine.descrizione")
    @ToInput
    @ToView
    @SelectRenderer(map = "mapByQuery('select i.id,i.descrizione from MappingDerivatiDetail$_Item i order by i.descrizione asc')", viewRule = "_origine.descrizione")
    @Column(name = "VoceSpesa")
    public String origine;
    
    @ManyToOne
    @JoinColumn(name = "VoceSpesa", insertable = false, updatable = false)
    public _Item _origine;


    @ToList(filtered = true, hidden = true)
    @ToInput
    @HiddenRenderer
    @Column(name = "Item")
    public String item;

    
    @Column(name = "Moltiplicatore")
    public String moltiplicatore;
    
    @Column(name = "Diretto")
    public Boolean diretto;
    
    @ToList(cellRule = "getText('{0,number,percent}',{coefficiente})")
    @ToInput
    @ToView
    @ValidField(empty = false, type = ValidField.FLOAT_VALIDATION)
    @TextRenderer(viewRule = "getText('{0,number,percent}',{coefficiente})",type =TextRenderer.REAL_TYPE )
    @Column(name = "Coefficiente")
    public Double coefficiente;
    
    @ToInput
    @ToView
    @ValidField(empty = false, type = ValidField.INT_VALIDATION)
    @TextRenderer(type =TextRenderer.INT_TYPE )
    @Column(name = "Priorita")
    public Integer priorita;
    
    @ToInput
    @ToView
    @ValidField(empty = false, type = ValidField.INT_VALIDATION)
    @TextRenderer(type =TextRenderer.INT_TYPE )
    @Column(name = "patch")
    public Integer segnaposto;

    @Override
    public String toString() {
        return "MappingDetail[ idItem=" + id + " ]";
    }
    
    
    /**
     * Tabelle di decodifica
     */
    
    
    @Entity
    @Table(name = "aziende")
    public static class _Azienda implements Serializable {

        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "IdAzienda")
        public Integer id;

        @ToList(ordered = true, defaultOrdered = true)
        @Column(name = "Descrizione")
        public String descrizione;

        @Column(name = "SistemaInformativo")
        public String si;

    }
    
    @OneToOne
    @JoinColumn(name = "Item", insertable = false, updatable = false)
    public _Item parent;

    @Entity
    @Table(name = "anagraficaitem")
    public static class _Item implements Serializable {

        @Id
        @Column(name = "CodiceItem")
        public String id;

        @Column(name = "TipoItem")
        public String tipoItem;
        
        @Column(name = "DescrizioneItem")
        public String descrizione;
    }
    

}
