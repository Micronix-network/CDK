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

/**
 * The persistent class for the TipiDocumento database table.
 * 
 */
@Entity
@Table(name = "tipidocumento")
public class TipoDocumento implements Serializable, Published {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @ToView
    @ToInput
    @FieldStyleDirective(tableCellStyle = "width:150px")
    @SearchField(filtered = true, ordered = true)
    public String legacycode;

    @ToView
    @ToInput
    @SearchField(filtered = true)
    public String descrizione;
    
    
    @ToView
    @ToInput
    @SelectRenderer(map="mapByQuery('select distinct r.id,r.descrizione from TipoSoggetto r')",viewRule="tiposoggetto.descrizione")
    @SearchField(cellRule="tiposoggetto.descrizione")
    @FieldStyleDirective(tableCellStyle="text-align:center;width:130px")
    public String idTSog;
    
    @ManyToOne
    @JoinColumn(name="idTSog", insertable = false, updatable = false)
    public TipoSoggetto tiposoggetto;

    @ToView
    @ToInput
    @SelectRenderer(map = "#{'Active':'true','Disabled':'false'}",viewRule="#{\"true\":{'Active','img_green'}, \"false\":{'Disabled','img_red'}}")
    @SearchField(filtered = true, cellRule = "#{\"true\":{'Active','img_green'}, \"false\":{'Disabled','img_red'}}",filterRule="#{'true':'Active','false':'Disabled'}")
    @FieldStyleDirective(tableCellStyle = "width:30px;text-align:center", group = 2)
    public Boolean toFax;

    @ToView
    @ToInput
    @SelectRenderer(map = "#{'Active':'true','Disabled':'false'}",viewRule="#{\"true\":{'Active','img_green'}, \"false\":{'Disabled','img_red'}}")
    @SearchField(filtered = true, cellRule = "#{\"true\":{'Active','img_green'}, \"false\":{'Disabled','img_red'}}",filterRule="#{'true':'Active','false':'Disabled'}")
    @FieldStyleDirective(tableCellStyle = "width:30px;text-align:center", group = 2)
    public Boolean toEmail;

    @ToView
    @ToInput
    @SelectRenderer(map = "#{'Active':'true','Disabled':'false'}",viewRule="#{\"true\":{'Active','img_green'}, \"false\":{'Disabled','img_red'}}")
    @SearchField(filtered = true, cellRule = "#{\"true\":{'Active','img_green'}, \"false\":{'Disabled','img_red'}}",filterRule="#{'true':'Active','false':'Disabled'}")
    @FieldStyleDirective(tableCellStyle = "width:30px;text-align:center", group = 2)
    public Boolean toArchive;

    @ToView
    @ToInput
    @SelectRenderer(map = "#{'Active':'true','Disabled':'false'}",viewRule="#{\"true\":{'Active','img_green'}, \"false\":{'Disabled','img_red'}}")
    @SearchField(filtered = true, cellRule = "#{\"true\":{'Active','img_green'}, \"false\":{'Disabled','img_red'}}",filterRule="#{'true':'Active','false':'Disabled'}")
    @FieldStyleDirective(tableCellStyle = "width:30px;text-align:center", group = 2)
    public Boolean toPrinter;

    private String tagREGexp = "<TCDTAG>(.*)</TCDTAG>"; // Default

    public TipoDocumento() {
    }


    public String getTagREGexp() {
	if (tagREGexp == null)
	    this.tagREGexp = "<TCDTAG>(.*)</TCDTAG>";
	return tagREGexp;
    }

    public void setTagREGexp(String tagREGexp) {
	if (tagREGexp == null)
	    return;
	this.tagREGexp = tagREGexp;
    }

    @Override
    public String toString() {
	if (StringUtil.EmptyOrNull(descrizione))
	    return legacycode;
	return descrizione;
    }

}