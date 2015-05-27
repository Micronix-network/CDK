package it.micronixnetwork.cdk.domain;

import it.micronixnetwork.application.plugin.crude.annotation.FieldStyleDirective;
import it.micronixnetwork.application.plugin.crude.annotation.ToList;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.annotation.ValidField;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.SelectRenderer;
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
    @ToList(filtered=true,filterRule="#{'1':'Sec. 1','2':'Sec. 2','3':'Sec. 3','4':'Sec. 4','5':'Sec. 5'}",cellRule = "#{1:'Sec. 1',2:'Sec. 2',3:'Sec. 3',4:'Sec. 4',5:'Sec. 5'}")
    @FieldStyleDirective(tableCellStyle = "width:80px;background-color:rgba(100,255,100,0.1);text-align:center;font-weight:bold")
    @SelectRenderer(map="#{'1':'Sec. 1','2':'Sec. 2','3':'Sec. 3','4':'Sec. 4','5':'Sec. 5'}")
    @ValidField(type=ValidField.INT_VALIDATION)
    public Integer section;

    @ToView
    @ToInput
    @ToList(ordered=true,filtered=true)
    @ValidField(empty=false)
    public String label;

    @ToView
    @ToInput
    @ToList(filtered=true)
    public String val;

    @ToView
    @ToInput
    @ToList(filtered=true)
    @TextAreaRenderer(height=100)
    public String description;

    @Override
    public String toString() {
        return label;
    }

}
