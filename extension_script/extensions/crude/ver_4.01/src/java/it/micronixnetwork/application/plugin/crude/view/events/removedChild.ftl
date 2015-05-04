<#if targetClass?? && targetParent??>
<#assign targetClassName=targetClass.substring(targetClass.lastIndexOf('.')+1)!''/>
<#assign targetParentName=targetParent.substring(targetParent.lastIndexOf('.')+1)!''/>
<script type="text/javascript">
$(document).ready(function(){	
	callEvent('${cardId}_${targetParentName}_find_refresh');	
	callEvent("${cardId}_${targetParentName}_${uiid}_get_object_refresh");
});
</script>
</#if>

