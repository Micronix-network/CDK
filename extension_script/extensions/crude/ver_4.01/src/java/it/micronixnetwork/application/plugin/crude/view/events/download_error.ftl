<#if targetClass!=''>
	<#assign targetClassName=targetClass.substring(targetClass.lastIndexOf('.')+1)!''/>
</#if>
<@s.i18n name="${targetClass}">	
<script type="text/javascript">	
	setTimeout(function(){
		parent.${cardId}_set_list_message();
	  }, 2000);
	parent.${cardId}_set_list_error_message("<@s.text name="${targetClassName}.download_error.message">File non presente</@s.text>");
</script>
</@s.i18n>

