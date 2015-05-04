<#import "/template/gaf/macro/properties.ftl" as prop>

<#assign yes_noHash = {'false':'${action.getText("yes.or.no.no", "No")}','true':'${action.getText("yes.or.no.yes", "Yes")}'}/>
<#assign activeHash={'true': '${action.getText("generic.properties.dialog.active", "Active")}', 'false': '${action.getText("generic.properties.dialog.inactive", "Inactive")}'}/>
<#assign activeHash={'5':'5','10':'10', '15':'15', '20':'20', '30':'30', '50':'50', '80':'80', '100':'100'}/> 
<#assign appRoles=allRoles![]/>
 
<style type="text/css">
#propstabs-5_${cardId} .CodeMirror-scroll {
	height : 150px;
}

#propstabs-3_${cardId} .CodeMirror-scroll {
	height : 190px;
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
					<@prop.codeMirror var="jsEditor2_${cardId}" areaId="prop_${cardId}_row_click_event" mode="text/javascript"/>
					<@prop.codeMirror var="jsEditor3_${cardId}" areaId="prop_${cardId}_toList_click_event" mode="text/javascript"/>
					}
				}
			});
			$("#${cardId}_properties_form .multiselect").twosidedmultiselect();
	    }); 
		
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
	<@prop.select label="${action.getText('crude.micronixnet.prototype', 'Prototipo')}" name="prototype" value="${action.getCardParam('prototype')!''}" options=domainObjects force=true/>
	<@prop.text label="${action.getText('crude.micronixnet.result_limit', 'Limite numero risultati')}" name="result_limit" value="${action.getCardParam('result_limit')!''}" style="width:50px"/>
	<@prop.select label="${action.getText('crude.micronixnet.aby_insert', 'Abilitazione creazione')}" name="aby_insert" value="${action.getCardParam('aby_insert')!'false'}" options=yes_noHash />
	<@prop.select label="${action.getText('crude.micronixnet.aby_edit', 'Abilitazione modifica')}" name="aby_edit" value="${action.getCardParam('aby_edit')!'false'}" options=yes_noHash />
	<@prop.select label="${action.getText('crude.micronixnet.aby_delete', 'Abilitazione eliminizaione')}" name="aby_delete" value="${action.getCardParam('aby_delete')!'false'}" options=yes_noHash />
	<@prop.select label="${action.getText('crude.micronixnet.aby_filter', 'Abilitazione filtro')}" name="aby_filter" value="${action.getCardParam('aby_filter')!'true'}" options=yes_noHash />
	<@prop.select label="${action.getText('crude.micronixnet.only_list', 'Solo lista')}" name="only_list" value="${action.getCardParam('only_list')!'false'}" options=yes_noHash />
	<@prop.select label="${action.getText('crude.micronixnet.autoselect', 'Autoselect')}" name="autoselect" value="${action.getCardParam('autoselect')!'false'}" options=yes_noHash />
	<@prop.select label="${action.getText('crude.micronixnet.rowxpage', 'Righe per pagina')}" name="rowxpage" value="${action.getCardParam('rowxpage')!'15'}" options=activeHash />
	<@prop.select label="${action.getText('crude.micronixnet.directEdtit', 'Edit diretto')}" name="direct_edit" value="${action.getCardParam('direct_edit')!'false'}" options=yes_noHash />
	</div>
	
	<div id="propstabs-3_${cardId}" style="overflow: hidden">
	<@prop.textarea label="${action.getText('crude.micronixnet.rowclick', 'On row click')}" name="row_click_event" value="${action.getCardParam('row_click_event')!''}" style="height:100px"/>
	<@prop.textarea label="${action.getText('crude.micronixnet.toListClick', 'On toList click')}" name="toList_click_event" value="${action.getCardParam('toList_click_event')!''}" style="height:100px"/>
	</div>
	
	<div id="propstabs-2_${cardId}" style="overflow: hidden">
	<@prop.multi_select label="${action.getText('crude.micronixnet.rolesMody', 'Ruoli modifica')}" name="rolesMody" value="${action.getCardParam('rolesMody')!''}" options=appRoles/>
	<@prop.multi_select label="${action.getText('crude.micronixnet.rolesCreate', 'Ruoli creazione')}" name="rolesCreate" value="${action.getCardParam('rolesCreate')!''}" options=appRoles/>
	<@prop.multi_select label="${action.getText('crude.micronixnet.rolesRemove', 'Ruoli rimozione')}" name="rolesRemove" value="${action.getCardParam('rolesRemove')!''}" options=appRoles/>
	</div>
	
</form>



