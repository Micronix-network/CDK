<#if targetClass!=''>
	<#assign targetClassName=targetClass.substring(targetClass.lastIndexOf('.')+1)!''/>
</#if>

<script type="text/javascript">	
	setTimeout(function(){
		parent.${cardId}_set_list_message();
	  }, 2000);
	parent.${cardId}_set_list_error_message("${action.getText(targetClassName+".download_error.message")} File non presente</@s.text>");
</script>


