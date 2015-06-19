<#import "/template/gaf/macro/properties.ftl" as prop>

<#assign yes_noHash = {'false':'${action.getText("yes.or.no.no", "No")}','true':'${action.getText("yes.or.no.yes", "Yes")}'}/>
<#assign typeHash = {'n':'Tutto in uno','l':'Master','d':'Dettaglio'}/>
<#assign activeHash={'true': '${action.getText("generic.properties.dialog.active", "Active")}', 'false': '${action.getText("generic.properties.dialog.inactive", "Inactive")}'}/>
<#assign activeHash={'-1':'all','5':'5','10':'10', '15':'15', '20':'20', '30':'30', '50':'50', '80':'80', '100':'100'}/> 
<#assign appRoles=allRoles![]/>
<#assign domainCards=domainCardIds![]/>
 
<style type="text/css">
#propstabs-5_${cardId} .CodeMirror-scroll {
	height : 150px;
}

#propstabs-3_${cardId} .CodeMirror-scroll {
	height : 410px;
}
</style> 
 
<script type="text/javascript">
    editor_${cardId}=null;
    var jsEditor1_${cardId}=null;
    var jsEditor2_${cardId}=null;
    var jsEditor3_${cardId}=null;
    
    $(document).ready(function(){
        <@prop.codeMirror var="jsEditor1_${cardId}" areaId="prop_${cardId}_card_actived_event" mode="text/javascript"/>	
        
        var $tabs=$( "#propstabs_${cardId}" ).gaftabs({
            show: function() {
                if(this.attr('id')=='propstabs-3_${cardId}'){
                    <@prop.codeMirror var="jsEditor2_${cardId}" areaId="prop_${cardId}_observed_event" mode="text/javascript"/>
                }
            }
        });
        
        $("#${cardId}_properties_form .multiselect").twosidedmultiselect();
        
        $("#${cardId}_crud_accordion .accordion-section-title").click(function(e) {
            var currentAttrValue = $(this).attr('href');
            if($(e.target).is('#${cardId}_crud_accordion .slide_open')) {
                    ${cardId}_close_accordion_section('${cardId}_crud_accordion');
            }else {
                    ${cardId}_close_accordion_section('${cardId}_crud_accordion');
                    $(this).addClass('slide_open');
                    $('#${cardId}_crud_accordion ' + currentAttrValue).slideDown(300).addClass('slide_open'); 
            }
            e.preventDefault();
	});
    }); 
    
    function ${cardId}_close_accordion_section(id) {
		$('#'+id+' .accordion-section-title').removeClass('slide_open');
		$('#'+id+' .accordion-section-content').slideUp(300).removeClass('slide_open');
	}

    ${cardId}_properties_dialog.prepare_submit=function(){
        $(".multiselect.TakeOver option").attr("selected", "selected");
        if(jsEditor1_${cardId}!=null){jsEditor1_${cardId}.save();}
        if(jsEditor2_${cardId}!=null){jsEditor2_${cardId}.save();}
        if(jsEditor3_${cardId}!=null){jsEditor3_${cardId}.save();}
    }	
</script>

<form id="${cardId}_properties_form">
<input type="hidden" name="domain" value="${cardModel.domain}"/>
<div id="propstabs_${cardId}" style="min-height:300px;">
    <ul class="tabs">
            <li><a href="#propstabs-5_${cardId}">${action.getText('generic.properties.dialog', 'Generic')}</a></li>
            <li><a href="#propstabs-1_${cardId}">CRUD</a></li>
            <li><a href="#propstabs-3_${cardId}">Events</a></li>
            <li><a href="#propstabs-2_${cardId}">Roles</a></li>
    </ul>

    <@prop.commons id="propstabs-5_${cardId}"/>

    <div id="propstabs-1_${cardId}" style="overflow: hidden">
        <div id="${cardId}_crud_accordion" class="accordion">
            <div class="accordion-section">
                <a class="accordion-section-title" href="#accordion-1">Parametri Vista 'Lista'</a>
                <div id="accordion-1" class="accordion-section-content">
                    <@prop.select label="${action.getText('crude.micronixnet.search_on_load', 'Search on load')}" name="search_on_load" value="${action.getCardParam('search_on_load')!'true'}" options=yes_noHash />
                    <@prop.text label="${action.getText('crude.micronixnet.result_limit', 'Limite numero risultati')}" name="result_limit" value="${action.getCardParam('result_limit')!''}" style="width:50px"/>
                    <@prop.select label="${action.getText('crude.micronixnet.aby_filter', 'Abilitazione filtro')}" name="aby_filter" value="${action.getCardParam('aby_filter')!'true'}" options=yes_noHash />
                    <@prop.select label="${action.getText('crude.micronixnet.autoselect', 'Autoselect')}" name="autoselect" value="${action.getCardParam('autoselect')!'false'}" options=yes_noHash />
                    <@prop.select label="${action.getText('crude.micronixnet.rowxpage', 'Righe per pagina')}" name="rowxpage" value="${action.getCardParam('rowxpage')!'15'}" options=activeHash />
                    <@prop.select label="${action.getText('crude.micronixnet.save_and_go', 'Save and go')}" name="save_and_go" value="${action.getCardParam('save_and_go')!'false'}" options=yes_noHash />
                </div>
            </div>
            <div class="accordion-section">
                <a class="accordion-section-title" href="#accordion-2">Parametri Vista 'Dettaglio'</a>
                <div id="accordion-2" class="accordion-section-content">
                    <@prop.select label="${action.getText('crude.micronixnet.directEdtit', 'Edit diretto')}" name="direct_edit" value="${action.getCardParam('direct_edit')!'false'}" options=yes_noHash /> 
                    <@prop.select label="${action.getText('crude.micronixnet.popup.gui', 'Popup GUI')}" name="popup_gui" value="${action.getCardParam('popup_gui')!'false'}" options=yes_noHash />  
                    <@prop.text label="${action.getText('crude.micronixnet.dialog.width', 'Largezza popup')}" name="pop_width" value="${action.getCardParam('pop_width')!''}" style="width:50px"/>
                    <@prop.text label="${action.getText('crude.micronixnet.dialog.height', 'Altezza popup')}" name="pop_height" value="${action.getCardParam('pop_height')!''}" style="width:50px"/>
                </div>
            </div>
            <div class="accordion-section">
                <a class="accordion-section-title" href="#accordion-3">Flag di abilitazione</a>
                <div id="accordion-3" class="accordion-section-content">
                    <@prop.select label="${action.getText('crude.micronixnet.aby_insert', 'Abilitazione creazione')}" name="aby_insert" value="${action.getCardParam('aby_insert')!'false'}" options=yes_noHash />
                    <@prop.select label="${action.getText('crude.micronixnet.aby_edit', 'Abilitazione modifica')}" name="aby_edit" value="${action.getCardParam('aby_edit')!'false'}" options=yes_noHash />
                    <@prop.select label="${action.getText('crude.micronixnet.aby_delete', 'Abilitazione eliminizaione')}" name="aby_delete" value="${action.getCardParam('aby_delete')!'false'}" options=yes_noHash />
                </div>
            </div>
            <@prop.select label="${action.getText('crude.micronixnet.prototype', 'Modello di View')}" name="prototype" value="${action.getCardParam('prototype')!''}" options=publischedObject force=true/>
            <@prop.select label="${action.getText('crude.micronixnet.type', 'Tipo')}" name="gui_type" value="${action.getCardParam('gui_type')!'n'}" options=typeHash />
            <@prop.text label="${action.getText('crude.micronixnet.message_bundle', 'File dei testi')}" name="message_bundle" value="${action.getCardParam('message_bundle')!''}"  style="width:200px"/>
            <@prop.multi_select label="${action.getText('crude.micronixnet.crud_observers', 'Osservatori')}" name="crud_observers" value="${action.getCardParam('crud_observers')!''}" options=domainCards/>
        </div> 
    </div>

    <div id="propstabs-3_${cardId}">
        <@prop.textarea label="${action.getText('crude.micronixnet.events', 'Gestione eventi')}" name="observed_event" value="${action.getCardParam('observed_event')!''}" style="height:400px"/>
    </div>

    <div id="propstabs-2_${cardId}" style="overflow: hidden">
        <@prop.multi_select label="${action.getText('crude.micronixnet.rolesMody', 'Ruoli modifica')}" name="rolesMody" value="${action.getCardParam('rolesMody')!''}" options=appRoles/>
        <@prop.multi_select label="${action.getText('crude.micronixnet.rolesCreate', 'Ruoli creazione')}" name="rolesCreate" value="${action.getCardParam('rolesCreate')!''}" options=appRoles/>
        <@prop.multi_select label="${action.getText('crude.micronixnet.rolesRemove', 'Ruoli rimozione')}" name="rolesRemove" value="${action.getCardParam('rolesRemove')!''}" options=appRoles/>
    </div>
	
</form>



