<#import "/template/gaf/macro/properties.ftl" as prop>

<#assign yes_noHash = {'false':'${action.getText("yes.or.no.no", "No")}','true':'${action.getText("yes.or.no.yes", "Yes")}'}/>

<style type="text/css">

</style>

<script type="text/javascript">
var jsEditor1_${cardId}=null;
$(document).ready(function(){
	<@prop.codeMirror var="jsEditor1_${cardId}" areaId="prop_${cardId}_card_actived_event" mode="text/javascript"/>	
	var $tabs_${cardId}=$( "#propstabs_${cardId}" ).gaftabs({
		show: function() {
			
		}
	});
	$("#${cardId}_properties_form .multiselect").twosidedmultiselect();
});


${cardId}_properties_dialog.prepare_submit=function(){
	$(".multiselect.TakeOver option").attr("selected", "selected");
	if(jsEditor1_${cardId}!=null){jsEditor1_${cardId}.save();}
}
		
</script>

<form id="${cardId}_properties_form">
<input type="hidden" name="domain" value="${cardModel.domain}"/>
<div id="propstabs_${cardId}" style="min-height:300px">
	<ul class='tabs'>
		<li><a href="#general_${cardId}">${action.getText('generic.properties.dialog', 'Generic')}</a></li>
		<li><a href="#roles_${cardId}">Roles</a></li>
	</ul>
	<@prop.commons id="general_${cardId}"/>
	
	<div id="roles_${cardId}" style="overflow: hidden">
	<@prop.multi_select label="${action.getText('crude.micronixnet.rolesMody', 'Ruoli modifica')}" name="rolesMody" value="${action.getCardParam('rolesMody')!''}" options=allRoles/>
	<@prop.multi_select label="${action.getText('crude.micronixnet.rolesCreate', 'Ruoli creazione')}" name="rolesCreate" value="${action.getCardParam('rolesCreate')!''}" options=allRoles/>
	<@prop.multi_select label="${action.getText('crude.micronixnet.rolesRemove', 'Ruoli rimozione')}" name="rolesRemove" value="${action.getCardParam('rolesRemove')!''}" options=allRoles/>
	</div>
	
</div>
</form>







