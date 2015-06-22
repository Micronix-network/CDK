/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.cdk.domain;

import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToList;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.annotation.ValidField;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.HiddenRenderer;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.SelectRenderer;
import it.micronixnetwork.application.plugin.crude.model.ViewModel;
import java.io.Serializable;
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
@Table(name = "esposizione")
public class EsposizioneDetail implements ViewModel {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IdLayout")
    public Integer id;
    
    @ToList(filtered = true, hidden = true)
    @ToView
    @ToInput
    @HiddenRenderer
    @Column(name = "CodiceLayout")
    public String codiceLayout;
    
    @ToList(cellRule = "codiceOggetto")
    @ToView
    @ToInput
    @FieldStyleDirective(tableCellStyle = "width:90px;text-align:center")
    @SelectRenderer(map = "mapByQuery('select i.id,i.descrizione from EsposizioneDetail$_Item i')",append=true)
    @Column(name = "CodiceOggetto")
    public String codiceOggetto;
    
    @ToList(filtered = true)
    @ToView
    @ToInput
    @FieldStyleDirective(inputFieldStyle="width:300px;font-size:12px")
    @Column(name = "Descrizione")
    public String descrizione;
    
    @ToView
    @ToInput
    @Column(name = "Sequenza")
    @FieldStyleDirective(tableCellStyle = "width:90px;text-align:center")
    @ToList
    @ValidField(type = ValidField.INT_VALIDATION)
    public Integer sequenza;
    
    @ToView
    @ToInput
    @Column(name = "Andamento")
    @FieldStyleDirective(tableCellStyle = "width:30px;text-align:center")
    @SelectRenderer(map = "#{'0':'A','1':'B','2':'C','3':'D','4':'E'}")
    @ToList
    @ValidField(type = ValidField.INT_VALIDATION)
    public Integer andamento;
    
    @ToView
    @ToInput
    @Column(name = "Indentazione")
    @FieldStyleDirective(tableCellStyle = "width:30px;text-align:center")
    @SelectRenderer(map = "#{'0':'Nessuna','1':'1 TAB','2':'2 TAB','3':'3 TAB','4':'4 TAB'}")
    @ToList
    @ValidField(type = ValidField.INT_VALIDATION)
    public Integer indentazione;
    
    @ToView
    @ToInput
    @FieldStyleDirective(tableCellStyleRule = "'text-indent: -9999px;width:30px;background-color:'+(risalto==1?'rgba(0,255,0,0.2)':risalto==2?'rgba(0,0,255,0.2)':risalto==3?'rgba(255,255,0,0.2)':risalto==4?'rgba(255,0,0,0.2)':'none')")
    @SelectRenderer(map = "#{'0':'Nessuno','1':'Verde','2':'Blu','3':'Giallo','4':'Rosso'}")
    @ToList
    @Column(name = "Risalto")
    public String risalto;


    @Override
    public String toString() {
        return codiceLayout;
    }
    
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
