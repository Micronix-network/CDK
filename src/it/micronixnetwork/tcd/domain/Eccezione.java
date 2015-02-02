package it.micronixnetwork.tcd.domain;

import it.micronixnetwork.application.plugin.crude.annotation.AsincInfo;
import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.Group1Directive;
import it.micronixnetwork.application.plugin.crude.annotation.Group2Directive;
import it.micronixnetwork.application.plugin.crude.annotation.SearchField;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.annotation.ValidField;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.SelectRenderer;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.YesNoRenderer;
import it.micronixnetwork.gaf.util.StringUtil;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the Configurazioni database table.
 *
 */
@Entity
@Table(name="eccezioni")
@Group1Directive(style="width:100%")
@Group2Directive(title="Eccezione.default.tipodocumento",style="width:100%;background-color:rgb(250, 242, 237);margin-top:5px")
public class Eccezione implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer id;
	
	
	@ToInput
	@SelectRenderer(map = "mapByQuery('select distinct t.id,t.descrizione from TipoDocumento t order by 1 desc')", startValue = "{' ',''}")
	@ValidField(empty = false, type = "")
	@AsincInfo(mappedObject = "Eccezione$_TipoDocumento", targetField="tipoDocumento")
	public Integer idTdoc;
	
	@ToInput
	@ToView
	@YesNoRenderer(viewRule = "#{\"true\":{'Active','img_green'}, \"false\":{'Disabled','img_red'}}")
	@SearchField(filtered = true,cellRule = "#{\"true\":{'Active','img_green'}, \"false\":{'Disabled','img_red'}}")
	@FieldStyleDirective(tableCellStyle="width:30px")
	@ValidField(empty = false, type = "")
	@Column(name="email_active")
	public Boolean emailActive;

	@ToInput
	@ToView
	@YesNoRenderer(viewRule ="#{\"true\":{'Active','img_green'}, \"false\":{'Disabled','img_red'}}")
	@SearchField(filtered = true,cellRule = "#{\"true\":{'Active','img_green'}, \"false\":{'Disabled','img_red'}}")
	@FieldStyleDirective(tableCellStyle="width:30px")
	@ValidField(empty = false, type = "")
	@Column(name="fax_active")
	public Boolean faxActive;
	
	@OneToOne
	@JoinColumn(name = "idTdoc", insertable = false, updatable = false)
	public _TipoDocumento tipoDocumento;

	
	
	//@ForeignKey(name="id")
	@Transient
	public String idDest;
	
	public Eccezione() {
	}
	
	@Override
	public String toString() {
	    if(tipoDocumento!=null){
		if(!StringUtil.EmptyOrNull(tipoDocumento.descrizione)){
		    return tipoDocumento.descrizione;
		}else{
		    return tipoDocumento.legacycode;
		}
	    };
	    return "";
	}
	
	@Entity
	@Table(name="tipidocumento")
	    public static class _TipoDocumento implements Serializable {
	    	private static final long serialVersionUID = 1L;

	    	@Id
	    	public Integer id;

	    	@FieldStyleDirective(tableCellStyle="width:auto")
	    	@ToView
	    	public String descrizione;
	    	
	    	@ToInput(active=false)
	    	@FieldStyleDirective(tableCellStyle="width:auto",group=2)
	    	@ToView
	    	public String legacycode;
	    	
	    	@ToInput(active=false)
	    	@YesNoRenderer(viewRule = "#{\"true\":{'Active','img_green'}, \"false\":{'Disabled','img_red'}}")
	        @FieldStyleDirective(tableCellStyle = "width:30px;text-align:center",group=2)
	        public Boolean toFax;

	    	@ToInput(active=false)
	    	@YesNoRenderer(viewRule = "#{\"true\":{'Active','img_green'}, \"false\":{'Disabled','img_red'}}")
	        @FieldStyleDirective(tableCellStyle = "width:30px;text-align:center",group=2)
	        public Boolean toEmail;


	    	public _TipoDocumento() {
	    	}
	    }
	
	
}