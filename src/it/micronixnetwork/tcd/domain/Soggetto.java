package it.micronixnetwork.tcd.domain;

import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.SearchField;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.SelectRenderer;
import it.micronixnetwork.gaf.domain.Published;
import it.micronixnetwork.gaf.util.StringUtil;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the Soggettti database table.
 *
 */
@Entity
@Table(name = "soggetti")
public class Soggetto implements Serializable, Published {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer id;
    
    @ToView
    @ToInput
    @FieldStyleDirective(tableCellStyle = "width:60px;text-align:center;background-color:rgba(255, 187, 66, .2)")
    @SearchField(filtered = true,ordered=true)
    public String legacycode;
    
    @ToView
    @ToInput
    @SelectRenderer(map="mapByQuery('select distinct r.id,r.descrizione from TipoSoggetto r')",viewRule="tiposoggetto.descrizione")
    @SearchField(filtered = true,ordered=true,filterRule="mapByQuery('select distinct r.descrizione,r.id from TipoSoggetto r')")
    @FieldStyleDirective(tableCellStyle = "width:60px;text-align:center")
    public String idTSog;

    @ToView
    @ToInput
    @FieldStyleDirective(tableCellStyle = "width:150px")
    @SearchField(filtered = true,ordered=true)
    private String ragioneSociale;

    @ToView
    @ToInput
    @FieldStyleDirective(tableCellStyle = "width:auto;background-color:rgba(255, 187, 66, .2)")
    @SearchField(filtered = true,ordered=true)
    public String descrizione;
    
   
    
    @ManyToOne
    @JoinColumn(name="idTSog", insertable = false, updatable = false)
    public TipoSoggetto tiposoggetto;

    @ToView
    @ToInput
    public String email;

    @ToView
    @ToInput
    public String fax;
    

    @Transient
    @SearchField(filtered = false,cellRule="count('it.micronixnetwork.tcd.domain.Destinatario','idSog=:id',#{'id':id})")
    @FieldStyleDirective(tableCellStyle="width:30px;text-align:center;color:blue")
    public Integer destinatari;

    public Soggetto() {
    }

    @Override
    public String toString() {
            if(StringUtil.EmptyOrNull(descrizione)) return "";
            return descrizione;
    }
}