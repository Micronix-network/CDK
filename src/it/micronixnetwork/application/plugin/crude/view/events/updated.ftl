<#if targetClass??>
<#assign targetClassName=targetClass.substring(targetClass.lastIndexOf('.')+1)!''/>
<#assign direct_edit=action.getCardParam('direct_edit')!'false'/>
<#assign crud_observers=action.getCardParam('crud_observers')!''/>
<#assign type_flag=action.getCardParam('gui_type')!'n'/>
<#assign popup_gui=action.getCardParam('popup_gui')!'false'/>
<#import "/template/gaf/macro/events.ftl" as events>
<script type="text/javascript">
$(document).ready(function(){	
    <#if type_flag=='n'>
    callEvent('${cardId}_${targetClassName}_find_refresh');
    </#if>
    setTimeout(function(){
         $('.${cardId}_event_message').hide();
         ${cardId}_set_list_message();
    }, 2000);

    <#if uiid='view_ui'>
        $('#${cardId}_${targetClassName}_view_ui_info_obj_action_content').empty();
        $('#${cardId}_${targetClassName}_view_ui_info_obj_action').show();
        $("#${cardId}_view_ui_form").empty();
        $('#${cardId}_${targetClassName}_view_ui_get_obj_action').hide();
        <#if direct_edit='false'>
                callEvent("${cardId}_${targetClassName}_view_ui_info_object_refresh");		
        <#else>
            <#if popup_gui?boolean>
                ${cardId}_view_update_dialog.hide();
            <#else>
                ${cardId}_slideToPage($('#${cardId}_${targetClassName}_view_object_pane'),$('#${cardId}_list_slide'), 'left');
            </#if>    
            ${cardId}_set_list_message('<span class="${cardId}_event_message animated flash">${action.getText("crude.update.success.message")}</span>');
        </#if>		
    </#if>

    <#if crud_observers!=''>
        <@events.notify_observer observers=crud_observers event_name='obj_updated' param=updatedId/>
    </#if>
});
</script>
<span class="${cardId}_event_message animated flash">${action.getText("crude.update.success.message")}</span>
</#if>
