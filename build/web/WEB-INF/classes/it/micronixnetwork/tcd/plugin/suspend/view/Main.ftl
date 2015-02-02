<#assign rolesRemove=action.getCardParam('rolesRemove')!''/>
<#assign rolesCreate=action.getCardParam('rolesCreate')!''/>
<#assign rolesMody=action.getCardParam('rolesMody')!''/>
<#assign aby_edit_flag=action.getCardParam('aby_edit')!'true'/>
<#assign aby_delete_flag=action.getCardParam('aby_delete')!'true'/>
<#assign aby_insert_flag=action.getCardParam('aby_insert')!'true'/>
<#assign aby_filter_flag=action.getCardParam('aby_filter')!'true'/>
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

<#import "/it/micronixnetwork/application/plugin/crude/view/macro/style.ftl" as style>
<#import "/it/micronixnetwork/application/plugin/crude/view/macro/javascript.ftl" as js>

<style type="text/css">
	<@style.main/>	

	.${cardId}_send_btn{
		speak: none;
		font-style: normal;
		font-weight: normal;
		font-variant: normal;
		text-transform: none;
		text-align: center;
	}

	.${cardId}_send_btn span{
		display:none;
	}

	.${cardId}_send_btn:before{
		font-family: 'gaf';
		font-size: 18px;
		content: "\e02b";
	}
</style>


<#if prototype!=''>
<script type="text/javascript">
var ${cardId}_data_modify=false;
var ${cardId}_openTab=0;
var ${cardId}_send_email_confirm_dialog;

<@js.formatFunctions/>

<@js.general prototype=prototype/>

$(document).ready(function(){

		${cardId}_set_list_message();
		
		//Ridimensionamento pannelli di view e new
    	${cardId}_resizeView();
		${cardId}_resizeNew();
	   
	 	$('#${cardId}_${prototypeName}_info_list_button').click(function(){
			${cardId}_slideToPage($('#${cardId}_${prototypeName}_view_object_pane'),$('#${cardId}_list_slide'), 'left');
    	});
	   
		<@js.filterEvents prototype=prototype/> 	
		
		<#if aby_edit_flag='true' && action.checkRole(user.roles,rolesMody)>
			<@js.objUpdate prototype=prototype directEdit=direct_edit/>
		</#if>
		
		<#if aby_insert_flag='true' && action.checkRole(user.roles,rolesCreate)>
			<@js.objInsert prototype=prototype/>
		</#if>
		
		$('#${cardId}_send_button').click(function(){
			// Controllo presenza checkbox selezionati
			active=false;
		  	$('.${cardId}_${prototypeName}_selectable').each(function(){
		  	  if($(this).attr('checked')=='checked'){
		  		  active=true;
		  	  };
		  	  return !active;
		  	});
		  	if(active){
				${cardId}_show_overlay(true,'search_ui');
				var card_width=$("#${cardId}_card").width();
				valid=true;
				$('.${cardId}_${prototypeName}_selectable').each(function(){
		  	  	  if($(this).attr('checked')=='checked'){
			  		  $tr=$(this).parent().parent().parent('tr');
			  		  $td=$tr.find("[name='to']");
			  		  address=$td.find('span').find('span').text();
			  		  if(address.trim()==''){
			  		  		valid=false;
			  		  }
		  	  	   };
			  	   return valid;
		  		});
		  		if(valid){
					$trg=$('#${cardId}_send_email_confirm_dialog');
					var left=(card_width-$trg.width())/2;
					$trg.css('left',left);
					$trg.animate({top: "0"}, 500);
				}else{
					$trg=$('#${cardId}_send_email_error_dialog');
					var left=(card_width-$trg.width())/2;
					$trg.css('left',left);
					$trg.find('.diag_message').text("Fra gli invii selezionati esistono dei messaggi senza destinatario");
					$trg.animate({top: "0"}, 500);
				}
		  	}
			return false;
		});
	
		$('#${cardId}_${prototypeName}_email_send_cancel_button').click(function(){
			$('#${cardId}_send_email_confirm_dialog').animate({top: "-100%"}, 500,function(){
				${cardId}_show_overlay(false,'search_ui');
			});
		});
		
		$('#${cardId}_${prototypeName}_email_erorr_close_button').click(function(){
			$('#${cardId}_send_email_error_dialog').animate({top: "-100%"}, 500,function(){
				${cardId}_show_overlay(false,'search_ui');
			});
		});
	
		$('#${cardId}_${prototypeName}_email_send_apply_button').click(function(){
			${cardId}_set_list_message("");
			$('#${cardId}_send_email_confirm_dialog').animate({top: "-100%"}, 500,function(){
				${cardId}_show_overlay(false,'search_ui');
				$('#${cardId}_send_email_action').show();
				clearForm($('#${cardId}_send_email_form'));
				addHiddenToForm($('#${cardId}_send_email_form'),'targetClass','${prototype}');
				$('.${cardId}_${prototypeName}_selectable').each(function(){
			  	  if($(this).attr('checked')=='checked'){
			  		${cardId}_fillPrimarykeyData($(this).parent().parent().parent('tr'),$('#${cardId}_send_email_form'));
			  	  };
				});
				callEvent("${cardId}_send_email_refresh");
			});
		});
		
});
</script>

<form id="${cardId}_get_object_form" method="post" style="display: none">
</form>

<form id="${cardId}_view_object_form" method="post" style="display: none">
</form>

<form id="${cardId}_send_email_form" method="post" style="display: none">
</form>

<form id="${cardId}_get_object_child_form" method="post" style="display: none">
</form>

<form id="${cardId}_send_email_child_form" method="post" style="display: none">
</form>

<form id="${cardId}_object_form_post" method="post" style="display: none">
</form>

<form id="${cardId}_show_info_form"></form>

<div id="${cardId}_show_info_load"></div>


<div id="${cardId}_list_slide" class="${cardId}_page center origin" style="height:100%">
	<div id="${cardId}_overlay_search_ui"></div>
	<div class="${cardId}_form_title"  style="margin-bottom: 4px;height:35px;">
	<table style="width:100%">
		<tr>
			<td style="text-align:left;">
				
			</td>
			<td style="text-align:right;">
				<@s.i18n name="${prototype}"> 
				<button id="${cardId}_send_button" class="btn btn-small btn-primary ${cardId}_send_btn" style="width:60px"><span><@s.text name="${prototypeName}.send"/></span></button>
				</@s.i18n>
			</td>
		</tr>
	</table>
	</div>
	<form id="${cardId}_${prototypeName}_form" method="post">
		<@s.i18n name="${prototype}"> 
		<#if aby_filter_flag='true'>
		<@crude.filterForm id="${cardId}_${prototypeName}_filter" resultNumber="${rowxpage}" className="${cardId}_filter_console" prototype="${prototype}"/>
		</#if>
		</@s.i18n>
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
		namespace="suspend" 
		loadImage="true" 
		formId="${cardId}_${prototypeName}_form" 
		listen="${cardId}_${prototypeName}_find_refresh" 
		startOnLoad="${autoStartFind}" 
		reloadTime="600"
		style="margin-left:2px;margin-right:2px">
	</@gaf.div>

	<div id="${cardId}_list_slide_message" style="height:35px;text-align:center;width:100%;overflow:hidden;position:absolute;top:0px;z-index:-1">
	</div>
	
	<@gaf.div id="${cardId}_send_email_action" 
		action="activeSend" 
		namespace="suspend" 
		loadImage="spinner-1 normal" 
		formId="${cardId}_send_email_form" 
		listen="${cardId}_send_email_refresh" 
		startOnLoad="false" 
		style="height:35px;text-align:center;width:100%;overflow:hidden;position:absolute;top:0px;z-index:-1">
	</@gaf.div>
	
	<iframe id="${cardId}_download_file_action" 
		style="display:none;border:none;height:35px;text-align:center;width:100%;overflow:hidden;position:absolute;top:0px;z-index:-1">
	</iframe>
</div>

<div id="${cardId}_${prototypeName}_view_object_pane" class="${cardId}_page right" style="height:100%">
	<div class="${cardId}_overlay_view_ui"></div>
	<div class="${cardId}_form_title"  style="margin-bottom: 4px;height:35px">
		<table style="width:100%">
		<tr>
			<td style="text-align:left;">
			
			<button  id="${cardId}_${prototypeName}_info_list_button" class="btn btn-small btn-primary" >${action.getText("crude.button.list","To List")}</button>
			<#if aby_edit_flag='true' && action.checkRole(user.roles,rolesMody)>
				<button  id="${cardId}_${prototypeName}_info_cancel_button" class="btn btn-small btn-primary" style="display:none">${action.getText("crude.button.cancel","Cancel")}</button>
			</#if>
			</td>
			<td style="text-align:right;">
			<#if aby_edit_flag='true' && action.checkRole(user.roles,rolesMody)>
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

<@s.i18n name="${prototype}">
<div id="${cardId}_send_email_confirm_dialog" class="${cardId}_topDownDialog" style="width:300px">
		<h3>
			?
		</h3>
		<div class="diag_message"><@s.text name="${prototypeName}.send.confirm">${prototypeName}.send.confirm</@s.text></div>
		<div class="${cardId}_form_title"  style="margin-bottom: 4px;height:35px">
		<table style="width:100%">
			<tr>
				<td style="text-align:left;">
				<button id="${cardId}_${prototypeName}_email_send_cancel_button" class="btn btn-small btn-primary">
					<@s.text name="${prototypeName}.cancel">${prototypeName}.cancel</@s.text>
				</button>
				</td>
				<td style="text-align:right;">
				<button id="${cardId}_${prototypeName}_email_send_apply_button" class="btn btn-small btn-primary">
					<@s.text name="${prototypeName}.send">${prototypeName}.send</@s.text>
				</button>
				</td>
			</tr>
		</table>
		</div>
</div>
</@s.i18n>



<@s.i18n name="${prototype}">
<div id="${cardId}_send_email_error_dialog" class="${cardId}_topDownDialog" style="width:300px">
		<h3>
			!
		</h3>
		<div class="diag_message"></div>
		<div class="${cardId}_form_title"  style="margin-bottom: 4px;height:35px">
		<table style="width:100%">
			<tr>
				<td style="text-align:right;">
				<button id="${cardId}_${prototypeName}_email_erorr_close_button" class="btn btn-small btn-primary">
					<@s.text name="${prototypeName}.close">${prototypeName}.close</@s.text>
				</button>
				</td>
			</tr>
		</table>
		</div>
</div>
</@s.i18n>

<#else>
<div style="height:40px">
<h1><@s.text name="generic.properties.card.noconf"/></h1>
</div>
</#if>






