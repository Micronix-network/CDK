<#import "/template/gaf/macro/properties.ftl" as prop>

<#assign yes_noHash = {'false':'${action.getText("yes.or.no.no", "No")}','true':'${action.getText("yes.or.no.yes", "Yes")}'}/>

<#assign external_file = action.getCardParam('external_file')!'false'/>


<style type="text/css">
#propstabs-4_${cardId} .CodeMirror-scroll {
	height : 65px;
}

#propstabs-3_${cardId} .CodeMirror-scroll {
	height : 330px;
}
</style>

<script type="text/javascript">
var jsEditor1_${cardId}=null;
var htmlEditor_${cardId}=null;
$(document).ready(function(){
	<@prop.codeMirror var="jsEditor1_${cardId}" areaId="prop_${cardId}_card_actived_event" mode="text/javascript"/>	
	var $tabs_${cardId}=$( "#propstabs_${cardId}" ).gaftabs({
		show: function() {
			if(this.attr('id')=='propstabs-3_${cardId}'){
				<@prop.codeMirror var="htmlEditor_${cardId}" areaId="prop_${cardId}_code" mode="text/html"/>	
			}	
		}
	});
	
	$("#${cardId}_properties_form [name=\"props['external_file']\"]").change(function(e){
			if($(this).val()=='true'){
				$("#${cardId}_external_file").show('fast');
				$("#${cardId}_freemarker_editor").hide('fast');
			}else{
				$("#${cardId}_external_file").hide('fast');
				$("#${cardId}_freemarker_editor").show('fast');
				htmlEditor_${cardId}.refresh();
			}
		});
});


${cardId}_properties_dialog.prepare_submit=function(){
			if(jsEditor1_${cardId}!=null){jsEditor1_${cardId}.save();}
			if(htmlEditor_${cardId}!=null){htmlEditor_${cardId}.save();}
}
		
</script>

<#if external_file='true'>
  	<#assign show_file_prop='normal'/>
  	<#assign show_editor_prop='none'/>
<#else>
	<#assign show_file_prop='none'/>
  	<#assign show_editor_prop='normal'/> 	
</#if>

<form id="${cardId}_properties_form">
<input type="hidden" name="domain" value="${cardModel.domain}"/>
<div id="propstabs_${cardId}" style="min-height:300px">
	<ul class='tabs'>
		<li><a href="#propstabs-5_${cardId}">${action.getText('generic.properties.dialog', 'Generic')}</a></li>
		<li><a href="#propstabs-3_${cardId}">Fragment</a></li>
	</ul>
	<@prop.commons id="propstabs-5_${cardId}"/>
	<div id="propstabs-3_${cardId}" style="overflow:hidden;">
	<@prop.select label="${action.getText('fragment.micronixnet.external_file', 'External file')}" name="external_file" value="${action.getCardParam('external_file')!'false'}" options=yes_noHash />
	<div id="${cardId}_external_file" style="display:${show_file_prop}">
		<@prop.select label="${action.getText('fragment.micronixnet.fragment', 'Fragment to load')}" name="fragment" value="${action.getCardParam('fragment')!''}" options=fragments/>
	</div>
	<div id="${cardId}_freemarker_editor" style="display:${show_editor_prop}">
		<@prop.textarea label="${action.getText('helloworld.micronixnet.code', 'Code')}" name="code" value="${action.getCardParam('code')!''}" style="height:330px"/>
	</div>
	</div>
</div>
</form>







