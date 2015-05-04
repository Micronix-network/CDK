package it.micronixnetwork.cdk.domain;

import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.SearchField;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.annotation.ValidField;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.TextAreaRenderer;
import it.micronixnetwork.application.plugin.crude.model.ViewModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gaf_configs")
public class GafConfigCRUD implements ViewModel{


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer id;

	@ToView
	@ToInput
	@SearchField(filtered=true,filterRule="mapByQuery('select distinct gc.section,gc.section from GafConfigCrud gc order by gc.section asc')")
	@FieldStyleDirective(tableCellStyle = "width:80px;background-color:rgba(100,255,100,0.1);text-align:center;font-weight:bold")
	@ValidField(type=ValidField.INT_VALIDATION)
	public Integer section;

	@ToView
	@ToInput
	@SearchField(ordered=true,filtered=true)
	@ValidField(empty=false)
	public String label;

	@ToView
	@ToInput
	@SearchField(filtered=true)
	public String val;

	@ToView
	@ToInput
	@SearchField(filtered=true)
	@TextAreaRenderer(height=100)
	public String description;
	
	@Override
	public String toString() {
	    return label;
	}

}
