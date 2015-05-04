<#if targetParent?? && targetClass?? && childFieldName??>
<#assign targetName=targetClass.substring(targetClass.lastIndexOf('.')+1)!''/>
<#assign targetParentName=targetParent.substring(targetParent.lastIndexOf('.')+1)!''/>

<#import "macro/validation.ftl" as valid>

<script type="text/javascript">

$(document).ready(function(){	
	
	var card_width=$("#${cardId}_card").width();
	
	<#if operation='update'>
            $("#${cardId}_${targetName}_${uiid}_child_update_button").show();
            var $trg=$('#${cardId}_${childFieldName}_${uiid}_get_children_action');
	</#if>
	<#if operation='insert'>
            $("#${cardId}_${targetName}_${uiid}_child_save_button").show();
            var $trg=$('#${cardId}_${childFieldName}_${uiid}_new_children_action');
	</#if>
	
	var left=(card_width-$trg.width())/2;
            $trg.css('left',left);
            $trg.animate({top: "0"}, 500);

	<@valid.clientValidation formId="${uiid}_child"/>	

	//Inserimento PK padre nella form di inserimento del child i dati sono reperibili tramite la funzione ${cardId}_${targetParentName}_fillPrimarykeyData
	${cardId}_fillPrimarykeyData($('#${cardId}_${targetParentName}_${uiid}_id_toupdate'),$('#${cardId}_${uiid}_child_form'));

	//Nascondo la toolbar
	$('#${cardId}_${targetName}_view_main_object_form_tool_bar').hide();
	
	$('#${cardId}_${targetName}_${uiid}_child_save_button').click(function(){
            $('#${cardId}_${targetName}_${uiid}_child_insert_object_action').show();
            clearForm($("#${cardId}_object_form_post"));
            copyForm($("#${cardId}_object_form_post"), $("#${cardId}_${uiid}_child_form"));
            addHiddenToForm($('#${cardId}_object_form_post'),'targetClass','${targetClass}');
            addHiddenToForm($('#${cardId}_object_form_post'),'targetParent','${targetParent}');
            addHiddenToForm($('#${cardId}_object_form_post'),'childFieldName','${childFieldName}');
            callEvent("${cardId}_${targetName}_${uiid}_child_insert_object_refresh");
        });
		
	$('#${cardId}_${targetName}_${uiid}_child_update_button').click(function(){
            $('#${cardId}_${targetName}_${uiid}_child_update_object_action').show();
            clearForm($("#${cardId}_object_form_post"));
            copyForm($("#${cardId}_object_form_post"), $("#${cardId}_${uiid}_child_form"));
            addHiddenToForm($('#${cardId}_object_form_post'),'targetClass','${targetClass}');
            addHiddenToForm($('#${cardId}_object_form_post'),'targetParent','${targetParent}');
            addHiddenToForm($('#${cardId}_object_form_post'),'childFieldName','${childFieldName}');
            callEvent("${cardId}_${targetName}_${uiid}_child_update_object_refresh");
	});	
	
	$('#${cardId}_${targetName}_${uiid}_child_calncel_button').click(function(){
            ${cardId}_show_overlay(false,'${uiid}');
            $trg.animate({top: "-100%"}, 500,function(){
                    $trg.find('.div_asinc_content').empty();
            });
            $('#${cardId}_${targetName}_view_main_object_form_tool_bar').show();
	});
});
</script>

<#if target??>
<div>
    <h3>
        <#if operation='update'>
            ${action.getText(targetName+".objName")} : ${target}
        </#if>
        <#if operation='insert'>
            ${action.getText(targetName+".objName")}
        </#if>
    </h3>

    <@crude.inputForm id="${cardId}_${uiid}_child_form" var="target" mod="${operation}" className="${cardId}_cbp-mc-form"/>

    <#if uiid='new_ui'>
        <#assign prefix='insert'/>
    <#else>
        <#assign prefix='view'/>	
    </#if>

    <@gaf.div id="${cardId}_${targetName}_${uiid}_child_insert_object_action" 
        action="${prefix}CreateChild" 
        namespace="crude" 
        loadImage="spinner-7 normal" 
        formId="${cardId}_object_form_post" 
        listen="${cardId}_${targetName}_${uiid}_child_insert_object_refresh" 
        startOnLoad="false" 
        style="height:35px;text-align:center;width:100%;overflow:hidden;position:absolute;bottom:0px;display:none;z-index:-1">
    </@gaf.div>
    <@gaf.div id="${cardId}_${targetName}_${uiid}_child_update_object_action" 
        action="${prefix}UpdateChild" 
        namespace="crude" 
        loadImage="spinner-7 normal" 
        formId="${cardId}_object_form_post" 
        listen="${cardId}_${targetName}_${uiid}_child_update_object_refresh" 
        startOnLoad="false" 
        style="height:35px;text-align:center;width:100%;overflow:hidden;position:absolute;bottom:0px;display:none;z-index:-1">
    </@gaf.div>
    <div class="${cardId}_form_title"  style="margin-bottom: 0px;height:35px">
        <table style="width:100%">
            <tr>
                <td style="text-align:left;">
                    <button  id="${cardId}_${targetName}_${uiid}_child_calncel_button" class="btn btn-small btn-primary">${action.getText("crude.button.cancel","Cancel")}</button>
                </td>
                <td style="text-align:right;">
                    <button id="${cardId}_${targetName}_${uiid}_child_save_button" class="btn btn-small btn-primary" style="display:none">${action.getText("crude.button.save","Save")}</button>
                    <button id="${cardId}_${targetName}_${uiid}_child_update_button" class="btn btn-small btn-primary" style="display:none">${action.getText("crude.button.update","Update")}</button>
                </td>
            </tr>
        </table>
    </div>
</div>
	

</#if>
</#if>



