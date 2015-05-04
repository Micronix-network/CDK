<#if targetClass??>
<#assign targetClassName=targetClass.substring(targetClass.lastIndexOf('.')+1)!''/>

<@crude.inputObjId id="${cardId}_${targetClassName}_${uiid}_id_toupdate" var="insertedId" />

<script type="text/javascript">
$(document).ready(function(){
	callEvent('${cardId}_${targetClassName}_find_refresh');
});
</script>
</#if>