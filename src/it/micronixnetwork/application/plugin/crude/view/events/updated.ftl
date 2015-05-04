<#if targetClass??>
<#assign targetClassName=targetClass.substring(targetClass.lastIndexOf('.')+1)!''/>
<#assign direct_edit=action.getCardParam('direct_edit')!'false'/>
<#assign crud_observers=action.getCardParam('crud_observers')!''/>
<script type="text/javascript">
$(document).ready(function(){		
	callEvent('${cardId}_${targetClassName}_find_refresh');
	setTimeout(function(){
	     $('#${cardId}_event_message').hide();
	     ${cardId}_set_list_message();
	}, 2000);
	
	<#if uiid='view_ui'>
		$('#${cardId}_${targetClassName}_view_ui_info_obj_action_content').empty();
		$('#${cardId}_${targetClassName}_view_ui_info_obj_action').show();
		$('#${cardId}_${targetClassName}_view_ui_get_obj_action_content').empty();
		<#if direct_edit='false'>
			callEvent("${cardId}_${targetClassName}_view_ui_info_object_refresh");		
		<#else>
			${cardId}_slideToPage($('#${cardId}_${targetClassName}_view_object_pane'),$('#${cardId}_list_slide'), 'left');
			${cardId}_set_list_message('<span id="${cardId}_event_message" class="event_message animated flash">${action.getText("crude.update.success.message")}</span>');
		</#if>		
	</#if>
        
        <#if crud_observers!=''>
            <@js.notify_observer crud_observers=crud_observers event_name='obj_updated'/>
        </#if>

	
});
</script>
<span id="${cardId}_event_message" class="${cardId}_event_message animated flash">${action.getText("crude.update.success.message")}</span>
</#if>
