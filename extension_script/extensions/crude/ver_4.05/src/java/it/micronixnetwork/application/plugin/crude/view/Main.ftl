<#assign rolesRemove=action.getCardParam('rolesRemove')!''/>
<#assign rolesCreate=action.getCardParam('rolesCreate')!''/>
<#assign rolesMody=action.getCardParam('rolesMody')!''/>
<#assign aby_edit_flag=action.getCardParam('aby_edit')!'true'/>
<#assign aby_delete_flag=action.getCardParam('aby_delete')!'true'/>
<#assign aby_insert_flag=action.getCardParam('aby_insert')!'true'/>
<#assign aby_filter_flag=action.getCardParam('aby_filter')!'true'/>
<#assign gui_type=action.getCardParam('gui_type')!'n'/>
<#assign save_and_go=action.getCardParam('save_and_go')!'false'/>
<#assign prototype=action.getCardParam('prototype')!''/>
<#assign direct_edit=action.getCardParam('direct_edit')!'false'/>
<#assign crud_observers=action.getCardParam('crud_observers')!''/>
<#assign popup_gui=action.getCardParam('popup_gui')!'false'/>

<#if prototype!=''>
    <#assign prototypeName=prototype.substring(prototype.lastIndexOf('.')+1)!''/>
</#if>
<#if gui_type=='l'>
    <#assign save_and_go='true'/>
</#if>
<#assign search_on_load=action.getCardParam('search_on_load')!'true'/>
<#assign rowxpage=action.getCardParam('rowxpage')!'30'/>
<#assign js_observed_event=action.getCardParam('observed_event')!""/>

<#assign list_view=gui_type!='d'/>
<#assign entity_view=gui_type!='l'/>

<#import "macro/style.ftl" as style>
<#import "macro/javascript.ftl" as js>
<#import "macro/components.ftl" as comp>

<style type="text/css">
    <@style.main/>	
</style>

<#if prototype!=''>
<script type="text/javascript">
var ${cardId}_data_modify=false;
var ${cardId}_openTab=0;
var ${cardId}_remove_object_confirm_dialog;
var ${cardId}_${prototypeName}_table;
var ${cardId}_row_selected;

<@js.formatFunctions/>
<@js.general prototype=prototype/>

//Gestione della notifica di un evento da parte di una CARD osservata
function ${cardId}_controller_event(rise,param,name){
    <#if gui_type=='d'>
    if(name=="listrow_click" ){
        //Il parametro è l'oggetto jquery della riga selezionata
        var id=param.attr('pk_id');
        var direct_edit=${direct_edit};
        ${cardId}_load_detail(id,direct_edit);
    }
    </#if>
    <#if gui_type=='l'>
    if(name=="obj_updated" ){
        //Il parametro è l'id dell'oggetto modificato
        ${cardId}_listRefresh();
    }
    </#if>
    try{${js_observed_event}}catch(err){
        alert(err);
    }
    return false;
}

function ${cardId}_show_info(field,value,className,uiid){
    clearForm($('#${cardId}_show_info_form'));
    addHiddenToForm($('#${cardId}_show_info_form'),"cardId",'${cardId}');
    addHiddenToForm($('#${cardId}_show_info_form'),"fieldName",field);
    addHiddenToForm($('#${cardId}_show_info_form'),"fieldValue",value);
    addHiddenToForm($('#${cardId}_show_info_form'),"className",className);
    if(uiid=='new_ui'){
        $("#${cardId}_show_info_load").load('${action.calcAction("insertFieldInfo","crude",null)}',$('#${cardId}_show_info_form').serializeArray());
    }else{
        $("#${cardId}_show_info_load").load('${action.calcAction("viewFieldInfo","crude",null)}',$('#${cardId}_show_info_form').serializeArray());
    }
}

$(document).ready(function(){

    ${cardId}_set_list_message();
    
    //Ridimensionamento pannelli di view e new
    ${cardId}_resizeView();
	${cardId}_resizeNew();
		   
    <@js.filterEvents prototype=prototype/> 			
});
</script>

<form id="${cardId}_get_object_form" method="post" style="display: none"></form>
<form id="${cardId}_view_object_form" method="post" style="display: none"></form>
<form id="${cardId}_remove_object_form" method="post" style="display: none"></form>
<form id="${cardId}_get_object_child_form" method="post" style="display: none"></form>
<form id="${cardId}_remove_object_child_form" method="post" style="display: none"></form>
<form id="${cardId}_object_form_post" method="post" style="display: none"></form>
<form id="${cardId}_show_info_form"></form>

<div id="${cardId}_show_info_load"></div>

<div id="${cardId}_overlay_crude_ui"></div>

<#-- LIST VIEW-->
<#if list_view>
    <@comp.list/>
</#if>

<#-- VIEW_UPDATE VIEW-->
<#if entity_view>
    <#if popup_gui?boolean>
        <@comp.view_update_dialog roleModi=action.checkRole(user.roles,rolesMody)/>
    <#else>
        <@comp.view_update roleModi=action.checkRole(user.roles,rolesMody)/>
    </#if>
</#if>

<#-- INSERT_UPDATE VIEW-->
<#if aby_insert_flag?boolean && action.checkRole(user.roles,rolesCreate)>
    <#if popup_gui?boolean>
        <@comp.insert_update_dialog/>
    <#else>
        <@comp.insert_update/>
    </#if>
    
</#if>

<#if popup_gui?boolean>
<@comp.confirm_dialog_popup id="modified_object" message="${action.getText('crude.update.modified')}"/>
<#else>
<@comp.confirm_dialog id="modified_object" message="${action.getText('crude.update.modified')}"/>
</#if>
<script type="text/javascript">
    function ${cardId}_modified_object_confirm_dialog_apply(){
        $("#${cardId}_view_ui_form").empty();
        $('#${cardId}_${prototypeName}_view_ui_info_obj_action').show();
        $('#${cardId}_${prototypeName}_view_ui_get_obj_action').hide();
        <#if direct_edit="false">
            callEvent("${cardId}_${prototypeName}_view_ui_info_object_refresh");
            $('#${cardId}_${prototypeName}_view_ui_update_object_action .div_asinc_content').html('');
        <#else>
            <#if popup_gui?boolean>
                ${cardId}_view_update_dialog.hide();
            <#else>
                ${cardId}_slideToPage($('#${cardId}_${prototypeName}_view_object_pane'),$('#${cardId}_list_slide'), 'left');
            </#if>
        </#if>
    }
</script>

<@comp.confirm_dialog id="remove_selected_objects" message="${action.getText('crude.remove.confirm')}"/>
<script type="text/javascript">
    function ${cardId}_remove_selected_objects_confirm_dialog_apply(){
        ${cardId}_set_list_message("");   
        $('#${cardId}_remove_object_action').show();
        clearForm($('#${cardId}_remove_object_form'));
        addHiddenToForm($('#${cardId}_remove_object_form'),'targetClass','${prototype}');
        $('.${cardId}_${prototypeName}_selectable').each(function(){
          if($(this).attr('checked')=='checked'){
                ${cardId}_fillPrimarykeyData($(this).parent().parent().parent('tr'),$('#${cardId}_remove_object_form'));
          };
        });
        callEvent("${cardId}_remove_object_refresh");
    }
</script>    
<#else>
<div style="height:40px">
<h1>${action.getText("generic.properties.card.noconf")}</h1>
</div>
</#if>