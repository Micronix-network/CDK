<#assign targetClassName=targetClass.substring(targetClass.lastIndexOf('.')+1)!''/>
<#assign direct_edit=action.getCardParam('direct_edit')!'false'/>
<style>

</style>

<#import "macro/validation.ftl" as valid>

<script type="text/javascript">
    
    var ${cardId}_chaged_values=false;
	
    function ${cardId}_${targetClassName}_${uiid}_prepareRetriveChild($pkele,child,fieldName){
        var $getChildForm=$('#${cardId}_${uiid}_get_object_child_form');
        clearForm($getChildForm);
        addHiddenToForm($getChildForm,'targetClass',child);
        addHiddenToForm($getChildForm,'targetParent','${targetClass}');
        addHiddenToForm($getChildForm,'childFieldName',fieldName);
        if($pkele!=null){                                       
            ${cardId}_fillPrimarykeyData($pkele,$getChildForm);
        }
    }	
	
    $(document).ready(function(){
        
        if($('#${cardId}_card').width()<806){
            $('#${cardId}_card .${cardId}_cbp-mc-column').width('100%');
        }

        <#if operation='update'>
            $('.${cardId}_view_button').hide();
            $('#${cardId}_${targetClassName}_new_update_button').show();
            $('#${cardId}_${targetClassName}_new_end_button').show();
            $('#${cardId}_${targetClassName}_info_update_button').show();
            $('#${cardId}_${targetClassName}_info_cancel_button').show();
            $('.${cardId}_${uiid}_form_resultRow').hover(function () {$(this).addClass('highlight');}, function () {$(this).removeClass('highlight');});  
        </#if>
        <#if operation='view'>
            $('.${cardId}_view_button').hide();
            $('#${cardId}_${targetClassName}_info_modify_button').show();
            $('#${cardId}_${targetClassName}_info_list_button').show();
        </#if>
		
        <#if operation='insert'>
            $('.${cardId}_insert_button').hide();
            $('#${cardId}_${targetClassName}_new_save_button').show();
            $('#${cardId}_${targetClassName}_new_cancel_button').show();
        </#if>
		
	${cardId}_data_modify=false;

	<@valid.clientValidation/>
	
	// Colorazione alternata righe tabella dei figli
	$('.${cardId}_children_table tbody tr:odd').addClass('${cardId}_odd');
	$('.${cardId}_children_table tbody tr:even').addClass('${cardId}_even');
        
        //Gestione check modifica valori
        $('.${cardId}_input_field').change(function(){
                ${cardId}_chaged_values=true;
        });
        

});		
</script>
<#if target??>
    <div class="${cardId}_form_title" style="">
        <#if operation='update' || operation='view'>
            ${action.getText(targetClassName+".objName")} : ${target} 
        </#if>
        <#if operation='insert'>
            ${action.getText(targetClassName+".objName")}
        </#if>
    </div>
    <#if operation='update'>
        <form id="${cardId}_${targetClassName}_${uiid}_actual_id_form">
            <@crude.inputObjId id="${cardId}_${targetClassName}_${uiid}_id_toupdate" var="updateId" />
        </form>
    </#if>
	
    <@crude.inputForm id="${cardId}_${uiid}_form" var="target" mod="${operation}" className="${cardId}_cbp-mc-form"/>

    <form id="${cardId}_${uiid}_get_object_child_form" method="post" style="display: none">
    </form>

    <#if operation='update' && childNames??>
    <#if uiid='new_ui'>
        <#assign prefix='insert'/>
    <#else>
        <#assign prefix='view'/>	
    </#if>
	
	<#list childNames?keys as childName>
		<#assign child=childNames.get(childName)/>
		<@gaf.div id="${cardId}_${childName}_${uiid}_new_children_action" 
			action="${prefix}NewChild" 
			namespace="crude" 
			loadImage="true" 
			formId="${cardId}_${uiid}_get_object_child_form"
			listen="${cardId}_${childName}_${uiid}_new_child_refresh" 
			startOnLoad="false" 
			className="${cardId}_child_form">
		</@gaf.div>
		
		<@gaf.div id="${cardId}_${childName}_${uiid}_get_children_action" 
			action="${prefix}GetChild" 
			namespace="crude" 
			loadImage="true" 
			formId="${cardId}_${uiid}_get_object_child_form"
			listen="${cardId}_${childName}_${uiid}_get_child_refresh" 
			startOnLoad="false" 
			className="${cardId}_child_form">
		</@gaf.div>
		
		<@gaf.div id="${cardId}_${childName}_${uiid}_delete_children_action" 
			action="${prefix}RemoveChild" 
			namespace="crude" 
			loadImage="spinner-7 normal" 
			formId="${cardId}_${uiid}_get_object_child_form"
			listen="${cardId}_${childName}_${uiid}_delete_child_refresh" 
			startOnLoad="false" 
			style="height:35px;text-align:center;width:100%;overflow:hidden;position:absolute;top:0px;z-index:-1">
		</@gaf.div>
		
	<div id="${cardId}_${childName}_${uiid}_tool_bar" class="${cardId}_form_title"  style="margin-bottom: 4px;height:35px">
            <table style="width:100%">
            <tr>
                <td style="text-align:left;">
                </td>
                <td style="text-align:right;">
                    <button id="${cardId}_${childName}_${uiid}_new_child_button" class="btn btn-small btn-primary" style="">${action.getText(targetClassName+"."+childName+".new.button")}</button>
                </td>
            </tr>
            </table>	
	</div>
	
	<div id="${cardId}_remove_child_confirm_dialog" class="${cardId}_topDownDialog">
            <h3>
                    ?
            </h3>
            <div class="diag_message">${action.getText(targetClassName+"."+childName+".remove.confirm")}</div>
            <div class="${cardId}_form_title"  style="margin-bottom: 4px;height:35px">
            <table style="width:100%">
                <tr>
                    <td style="text-align:left;">
                    <button  id="${cardId}_${childName}_${uiid}_child_calncel_button" class="btn btn-small btn-primary">${action.getText("crude.button.cancel","Cancel")}</button>
                    </td>
                    <td style="text-align:right;">
                    <button id="${cardId}_${childName}_${uiid}_child_apply_button" class="btn btn-small btn-primary">${action.getText("crude.button.delete","Delete")}</button>
                    </td>
                </tr>
            </table>
            </div>
	</div>
	
<script type="text/javascript">	
	
    $('#${cardId}_${childName}_${uiid}_new_child_button').click(function(){
            ${cardId}_show_overlay(true,'${uiid}');
            ${cardId}_${targetClassName}_${uiid}_prepareRetriveChild(null,'${child}','${childName}');
            $('#${cardId}_${childName}_${uiid}_new_children_action').show();
            callEvent("${cardId}_${childName}_${uiid}_new_child_refresh");	
    });

    $('#${cardId}_${childName}_${uiid}_child_calncel_button').click(function(){
            ${cardId}_show_overlay(false,'${uiid}');
            $trg=$('#${cardId}_remove_child_confirm_dialog');
            $trg.animate({top: "-100%"}, 500);
    });

    $('#${cardId}_${childName}_${uiid}_child_apply_button').click(function(){
            ${cardId}_show_overlay(false,'${uiid}');
            $trg=$('#${cardId}_remove_child_confirm_dialog');
            $trg.animate({top: "-100%"}, 500,function(){
                    callEvent("${cardId}_${childName}_${uiid}_delete_child_refresh")
            });

    });
		
    $('.${cardId}_${uiid}_form_resultRow').click(function(){
            ${cardId}_show_overlay(true,'${uiid}');
            ${cardId}_${targetClassName}_${uiid}_prepareRetriveChild($(this),'${child}','${childName}');
            callEvent("${cardId}_${childName}_${uiid}_get_child_refresh");
    });

    $('.${cardId}_${uiid}_form_child_delete').click(function(e){
            e.stopPropagation(); 
            ${cardId}_show_overlay(true,'${uiid}');
            var ele=$(this).parent().parent('tr');
            ${cardId}_${targetClassName}_${uiid}_prepareRetriveChild(ele,'${child}','${childName}');
            var card_width=$("#${cardId}_card").width();
            $trg=$('#${cardId}_remove_child_confirm_dialog');
            var left=(card_width-$trg.width())/2;
            $trg.css('left',left);
            $trg.animate({top: "0"}, 500);
    });
	</script>			
	</#list>
	</#if>
<#else>
    <script type="text/javascript">
      $(document).ready(function(){
    	  $modiButton=$( '#${cardId}-edit-modify-button');
          if ($modiButton)
          {
          	$modiButton.attr('disabled', 'disabled' ).addClass( 'ui-state-disabled' );
          }
        $(".splash").fadeIn(1000);
      });
    </script>
    <div class="message splash" style="margin-top: 50px; display: none">
        <div class="title">
            ${action.getText("crude.form.msg.title")}
        </div>
        <div class="body">
            ${action.getText("crude.form.msg.message")}
        </div>
    </div>	
</#if>

	


