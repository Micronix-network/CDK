<#if targetClass!=''>
	<#assign targetClassName=targetClass.substring(targetClass.lastIndexOf('.')+1)!''/>
</#if>
<script type="text/javascript">
$(document).ready(function(){		
	callEvent('${cardId}_${targetClassName}_find_refresh');
	callEvent('red_pin_refresh');
	
	setTimeout(function(){
		  $('#${cardId}_event_message').fadeOut('1000',function(){
		  		$('#${cardId}_send_email_action').hide();
		  		${cardId}_set_list_message();
		  });
	  }, 2000);
	
});
</script>
<@s.i18n name="${targetClass}">	
<span id="${cardId}_event_message" class="${cardId}_event_message animated flash"><@s.text name="${targetClassName}.sent.message"/></span>
</@s.i18n>

