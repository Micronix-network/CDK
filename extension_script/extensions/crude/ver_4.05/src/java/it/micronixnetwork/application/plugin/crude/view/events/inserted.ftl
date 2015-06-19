<#assign popup_gui=action.getCardParam('popup_gui')!'false'/>
<#if targetClass??>
<#assign targetClassName=targetClass.substring(targetClass.lastIndexOf('.')+1)!''/>

<@crude.inputObjId id="${cardId}_${targetClassName}_${uiid}_id_toupdate" var="insertedId" />

<script type="text/javascript">
$(document).ready(function(){
	callEvent('${cardId}_${targetClassName}_find_refresh');
	<#if (childNames?size > 0) >
		resetForm('${cardId}_${targetClassName}_update_object_form');
		$('#${cardId}_${targetClassName}_${uiid}_new_obj_action_content').empty();
		$('#${cardId}_${targetClassName}_${uiid}_get_obj_action_content').empty();
		$('#${cardId}_${targetClassName}_${uiid}_update_object_action_content').empty();
		$('#${cardId}_${targetClassName}_${uiid}_update_object_action .div_asinc_content').html('${action.getText("crude.form.rquired")}');
		clearForm($('#${cardId}_get_object_form'));
		addHiddenToForm($('#${cardId}_get_object_form'),'targetClass','${targetClass}');
		${cardId}_fillPrimarykeyData($('#${cardId}_${targetClassName}_${uiid}_id_toupdate'),$('#${cardId}_get_object_form'));
		$('#${cardId}_${targetClassName}_${uiid}_id_toupdate').remove();
		$('#${cardId}_${targetClassName}_${uiid}_new_object_action').hide();
		$('#${cardId}_${targetClassName}_insert_object_action').hide();
		callEvent("${cardId}_${targetClassName}_${uiid}_get_object_refresh");
	<#else>
            <#if popup_gui?boolean>
                ${cardId}_insert_update_dialog.hide();
            <#else>
                ${cardId}_slideToPage($('#${cardId}_${targetClassName}_new_object_pane'),$('#${cardId}_list_slide'), 'left');
            </#if> 
		
	</#if>
});
</script>
</#if>