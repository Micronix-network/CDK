<#if targetClass?? && targetParent?? && childFieldName??>
<#assign targetClassName=targetClass.substring(targetClass.lastIndexOf('.')+1)!''/>
<#assign targetParentName=targetParent.substring(targetParent.lastIndexOf('.')+1)!''/>

<script type="text/javascript">
$(document).ready(function(){
	${cardId}_show_overlay(false,'${uiid}');
	var slide=$('#${cardId}_${childFieldName}_${uiid}_get_children_action').height();
	$('#${cardId}_${childFieldName}_${uiid}_get_children_action').animate({top: -slide}, 500,
	function(){
		$('#${cardId}_${targetClassName}_tool_bar').show();
		callEvent("${cardId}_${targetParentName}_${uiid}_get_object_refresh");
	});
	callEvent('${cardId}_${targetParentName}_find_refresh');
});
</script>
</#if>