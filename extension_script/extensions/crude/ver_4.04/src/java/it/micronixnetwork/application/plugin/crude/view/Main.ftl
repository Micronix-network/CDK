<#assign rolesRemove=action.getCardParam('rolesRemove')!''/>
<#assign rolesCreate=action.getCardParam('rolesCreate')!''/>
<#assign rolesMody=action.getCardParam('rolesMody')!''/>
<#assign aby_edit_flag=action.getCardParam('aby_edit')!'true'/>
<#assign aby_delete_flag=action.getCardParam('aby_delete')!'true'/>
<#assign aby_insert_flag=action.getCardParam('aby_insert')!'true'/>
<#assign aby_filter_flag=action.getCardParam('aby_filter')!'true'/>
<#assign only_list_flag=action.getCardParam('only_list')!'false'/>
<#assign save_and_go=action.getCardParam('save_and_go')!'false'/>
<#assign only_detail_flag=action.getCardParam('only_detail')!'false'/>
<#assign crud_controller=action.getCardParam('crud_controller')!''/>
<#assign prototype=action.getCardParam('prototype')!''/>
<#assign direct_edit=action.getCardParam('direct_edit')!'false'/>
<#if prototype!=''>
    <#assign prototypeName=prototype.substring(prototype.lastIndexOf('.')+1)!''/>
</#if>
<#assign view_type_flag=action.getCardParam('view_type_flag')!'search'/>
<#assign rowxpage=action.getCardParam('rowxpage')!'15'/>
<#if view_type_flag='search'>
    <#assign autoStartFind='true'/>
<#else>
    <#assign autoStartFind='false'/>
</#if>
<#assign js_toList_click=action.getCardParam('toList_click_event')!""/>

<#assign list_view=only_detail_flag=='false'/>
<#assign entity_view=only_list_flag=='false' || only_detail_flag!='true'/>

<#import "macro/style.ftl" as style>
<#import "macro/javascript.ftl" as js>

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
	
function ${cardId}_on_toList_click($row){
    try{${js_toList_click};}catch(err){}
}

//Gestione dell'evento click sulla riga di crude MASTER
<#if only_detail_flag=='true'>
function ${cardId}_controller_event($row){
    clearForm($('#${cardId}_view_object_form'));
    addHiddenToForm($('#${cardId}_view_object_form'),'targetClass','${prototype}');
    ${cardId}_fillPrimarykeyData($row,$('#${cardId}_view_object_form'));
    try{
    ${cardId}_slideToPage($('#${cardId}_list_slide'),$('#${cardId}_${prototypeName}_view_object_pane'), 'right');
    $('#${cardId}_${prototypeName}_view_ui_get_obj_action').show();
    <#if direct_edit='false'>
          $('#${cardId}_${prototypeName}_view_ui_info_obj_action').show();
          $('#${cardId}_${prototypeName}_view_ui_get_obj_action').hide();
          callEvent("${cardId}_${prototypeName}_view_ui_info_object_refresh");
    <#else>
          $('#${cardId}_${prototypeName}_view_ui_info_obj_action').hide();
          $('#${cardId}_${prototypeName}_view_ui_get_obj_action').show();
          $('#${cardId}_${prototypeName}_view_ui_update_object_action_content').html('<span class="${cardId}_event_message animated flash">${action.getText("crude.form.rquired")}</span>');
          callEvent("${cardId}_${prototypeName}_view_ui_get_object_refresh");
    </#if>
    }catch(err){};
    return false;
}
</#if>

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

function ${cardId}_contentRefresh(){
    callEvent('${cardId}_${prototypeName}_find_refresh');
    return false;
}

$(document).ready(function(){

    ${cardId}_set_list_message();
    
    //Ridimensionamento pannelli di view e new
    ${cardId}_resizeView();
	${cardId}_resizeNew();
		
    $('#${cardId}_${prototypeName}_info_list_button').click(function(){
        ${cardId}_slideToPage($('#${cardId}_${prototypeName}_view_object_pane'),$('#${cardId}_list_slide'), 'left');
        ${cardId}_on_toList_click($(this));
    });
	   
    <@js.filterEvents prototype=prototype/> 	

<#if aby_edit_flag='true' && action.checkRole(user.roles,rolesMody)>
    <@js.objUpdate prototype=prototype directEdit=direct_edit/>
</#if>

<#if aby_insert_flag='true' && action.checkRole(user.roles,rolesCreate)>
    <@js.objInsert prototype=prototype save_and_go=(save_and_go=='true')/>
</#if>

<#if aby_delete_flag='true' && action.checkRole(user.roles,rolesRemove)>	
    <@js.objDelete prototype=prototype/>	
</#if>
		
});
</script>

<form id="${cardId}_get_object_form" method="post" style="display: none">
</form>

<form id="${cardId}_view_object_form" method="post" style="display: none">
</form>

<form id="${cardId}_remove_object_form" method="post" style="display: none">
</form>

<form id="${cardId}_get_object_child_form" method="post" style="display: none">
</form>

<form id="${cardId}_remove_object_child_form" method="post" style="display: none">
</form>

<form id="${cardId}_object_form_post" method="post" style="display: none">
</form>

<form id="${cardId}_show_info_form"></form>

<div id="${cardId}_show_info_load"></div>

<#if list_view>
<div id="${cardId}_list_slide" class="${cardId}_page center origin" style="height:100%">
    <div id="${cardId}_overlay_search_ui"></div>
    <div class="${cardId}_form_title"  style="margin-bottom: 4px;height:35px;">
    <table style="width:100%">
        <tr>
            <td style="text-align:left;">
            <#if aby_delete_flag='true'  && action.checkRole(user.roles,rolesRemove)>
                    <button id="${cardId}_delete_button" class="btn btn-small btn-primary ${cardId}_delete_btn" style="" disabled><span>${action.getText("crude.button.delete","Delete")}</span></button>
            </#if>
            </td>
            <td style="text-align:right;">
            <#if aby_insert_flag='true' && action.checkRole(user.roles,rolesCreate)>
                    <button id="${cardId}_create_button" class="btn btn-small btn-primary ${cardId}_add_btn" style=""><span>${action.getText("crude.button.create","Create new")}</span></button>
            </#if>
            </td>
        </tr>
    </table>
    </div>
    <form id="${cardId}_${prototypeName}_form" method="post">
        <#if aby_filter_flag='true'>
        <@crude.filterForm id="${cardId}_${prototypeName}_filter" resultNumber="${rowxpage}" className="${cardId}_filter_console" prototype="${prototype}"/>
        </#if>
        <input type="hidden" name="targetClass" value="${prototype}"/>
        <input type="hidden" name="page"/>
        <input type="hidden" name="size" value="${rowxpage}"/>
        <input type="hidden" name="toOrder"/>
        <input type="hidden" name="inDelete" value="false"/>
        <input type="hidden" name="inCreate" value="false"/>
        <input type="hidden" name="inUpdate" value="false"/>
    </form>
    <@gaf.div id="${cardId}_${prototypeName}_result_table" 
        action="find"
        namespace="crude" 
        loadImage="true" 
        formId="${cardId}_${prototypeName}_form" 
        listen="${cardId}_${prototypeName}_find_refresh" 
        startOnLoad="${autoStartFind}" 
        style="margin-left:2px;margin-right:2px">
    </@gaf.div>

    <div id="${cardId}_list_slide_message" style="height:35px;text-align:center;width:100%;overflow:hidden;position:absolute;top:0px;z-index:-1">
    </div>

    <@gaf.div id="${cardId}_remove_object_action" 
        action="remove" 
        namespace="crude" 
        loadImage="spinner-1 normal" 
        formId="${cardId}_remove_object_form" 
        listen="${cardId}_remove_object_refresh" 
        startOnLoad="false" 
        style="height:35px;text-align:center;width:100%;overflow:hidden;position:absolute;top:0px;z-index:-1">
    </@gaf.div>

    <iframe id="${cardId}_download_file_action" 
            style="display:none;border:none;height:35px;text-align:center;width:100%;overflow:hidden;position:absolute;top:0px;z-index:-1">
    </iframe>
</div>
</#if>

<#if entity_view>

<#if only_detail_flag=='true'>
    <#assign origin_class="origin"/>
<#else>
    <#assign origin_class=""/>
</#if>
<div id="${cardId}_${prototypeName}_view_object_pane" class="${cardId}_page right ${origin_class}" style="height:100%;">
    <div id="${cardId}_overlay_view_ui"></div>
    <div class="${cardId}_form_title"  style="margin-bottom: 4px;height:35px">
        <table style="width:100%">
            <tr>
                <td style="text-align:left;">
                <#if only_detail_flag=='false'>            
                    <button  id="${cardId}_${prototypeName}_info_list_button" class="btn btn-small btn-primary" >${action.getText("crude.button.list","To List")}</button>
                </#if>
                <#if aby_edit_flag=='true' && action.checkRole(user.roles,rolesMody)>
                    <button  id="${cardId}_${prototypeName}_info_cancel_button" class="btn btn-small btn-primary" style="display:none">${action.getText("crude.button.cancel","Cancel")}</button>
                </#if>
                </td>
                <td style="text-align:right;">
                <#if aby_edit_flag=='true' && action.checkRole(user.roles,rolesMody)>
                    <button id="${cardId}_${prototypeName}_info_update_button" class="btn btn-small btn-primary" style="display:none">${action.getText("crude.button.update","Update")}</button>
                    <button id="${cardId}_${prototypeName}_info_modify_button" class="btn btn-small btn-primary" style="display:none">${action.getText("crude.button.modify","Modify")}</button>
                </#if>
                </td>
            </tr>
        </table>
    </div>
    <div id="${cardId}_${prototypeName}_view_ui_scroll" style="overflow-y:auto">
    <@gaf.div id="${cardId}_${prototypeName}_view_ui_info_obj_action" 
        action="viewInfo"
        namespace="crude"
        loadImage="true" 
        formId="${cardId}_view_object_form" 
        listen="${cardId}_${prototypeName}_view_ui_info_object_refresh" 
        startOnLoad="false" 
        style="min-height:200px">
    </@gaf.div>	
    <@gaf.div id="${cardId}_${prototypeName}_view_ui_get_obj_action" 
        action="viewGet"
        namespace="crude"
        loadImage="true" 
        formId="${cardId}_view_object_form" 
        listen="${cardId}_${prototypeName}_view_ui_get_object_refresh" 
        startOnLoad="false" 
        style="min-height:200px">
    </@gaf.div>
    </div>
    <#if aby_edit_flag='true' && action.checkRole(user.roles,rolesMody)>
        <@gaf.div id="${cardId}_${prototypeName}_view_ui_update_object_action" 
            action="viewUpdate"
            namespace="crude" 
            loadImage="spinner-7 normal" 
            formId="${cardId}_object_form_post" 
            listen="${cardId}_${prototypeName}_view_ui_update_object_refresh" 
            startOnLoad="false" 
            style="height:35px;text-align:center;width:100%;overflow:hidden;position:absolute;top:0px;z-index:-1">
        </@gaf.div>
    </#if>
</div>

<#if aby_insert_flag='true' && action.checkRole(user.roles,rolesCreate)>
<div id="${cardId}_${prototypeName}_new_object_pane" class="${cardId}_page right" style="height:100%">
    <div id="${cardId}_overlay_new_ui"></div>
    <div class="${cardId}_form_title"  style="margin-bottom: 4px;height:35px">
        <table style="width:100%">
            <tr>
                <td style="text-align:left;">
                <button id="${cardId}_${prototypeName}_new_cancel_button" class="btn btn-small btn-primary">${action.getText("crude.button.cancel","Cancel")}</button>
                <button id="${cardId}_${prototypeName}_new_end_button" class="btn btn-small btn-primary" style="display:none">${action.getText("crude.button.finish","Finish")}</button>
                </td>
                <td style="text-align:right;">
                <button id="${cardId}_${prototypeName}_new_save_button" class="btn btn-small btn-primary">${action.getText("crude.button.save","Save")}</button>
                <button id="${cardId}_${prototypeName}_new_update_button" class="btn btn-small btn-primary" style="display:none">${action.getText("crude.button.update","Update")}</button>
                </td>
            </tr>
        </table>
    </div>
    <div id="${cardId}_${prototypeName}_new_ui_scroll" style="overflow-y:auto">
        <@gaf.div id="${cardId}_${prototypeName}_new_ui_new_object_action" 
            action="insertNew" 
            namespace="crude" 
            loadImage="true" 
            formId="${cardId}_get_object_form"
            listen="${cardId}_${prototypeName}_new_ui_new_object_refresh" 
            startOnLoad="false" 
            style=";min-height:200px">
        </@gaf.div>

        <@gaf.div id="${cardId}_${prototypeName}_new_ui_get_obj_action" 
            action="insertGet"
            namespace="crude"
            loadImage="true" 
            formId="${cardId}_get_object_form" 
            listen="${cardId}_${prototypeName}_new_ui_get_object_refresh" 
            startOnLoad="false" 
            style="width:100%;">
        </@gaf.div>
    </div>
    <@gaf.div id="${cardId}_${prototypeName}_new_ui_update_object_action" 
        action="insertUpdate"
        namespace="crude" 
        loadImage="spinner-7 normal" 
        formId="${cardId}_object_form_post" 
        listen="${cardId}_${prototypeName}_new_ui_update_object_refresh" 
        startOnLoad="false" 
        style="height:35px;text-align:center;width:100%;overflow:hidden;position:absolute;top:0px;z-index:-1">
    </@gaf.div>

    <@gaf.div id="${cardId}_${prototypeName}_new_ui_insert_object_action" 
        action="insertCreate" 
        namespace="crude" 
        loadImage="spinner-7 normal" 
        formId="${cardId}_object_form_post" 
        listen="${cardId}_${prototypeName}_new_ui_insert_object_refresh" 
        startOnLoad="false" 
        style="height:35px;text-align:center;width:100%;overflow:hidden;position:absolute;top:0px;z-index:-1">
        ${action.getText("crude.form.rquired")}
    </@gaf.div>
	
    <div id="${cardId}_${prototypeName}_update_confirm_dialog" style="display:none">
        ${action.getText("crude.update.confirm")}
    </div>
</div>
</#if>
</#if>

<div id="${cardId}_remove_object_confirm_dialog" class="${cardId}_topDownDialog" style="width:300px">
    <h3>
    ?
    </h3>
    <div class="diag_message" style="line-height: 50px;">${action.getText(prototypeName+".remove.confirm")}</div>
    <div class="${cardId}_form_title"  style="margin-bottom: 4px;height:35px">
    <table style="width:100%">
        <tr>
            <td style="text-align:left;">
            <button id="${cardId}_${prototypeName}_object_delete_cancel_button" class="btn btn-small btn-primary">
                ${action.getText("crude.button.cancel")}
            </button>
            </td>
            <td style="text-align:right;">
            <button id="${cardId}_${prototypeName}_object_delete_apply_button" class="btn btn-small btn-primary">
                ${action.getText("crude.button.delete")}
            </button>
            </td>
        </tr>
    </table>
    </div>
</div>

<#else>
<div style="height:40px">
<h1>${action.getText("generic.properties.card.noconf")}</h1>
</div>
</#if>