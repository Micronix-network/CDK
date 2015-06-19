<#if targetClass??>
<#assign targetClassName=targetClass.substring(targetClass.lastIndexOf('.')+1)!''/>


<script type="text/javascript">
$(document).ready(function(){	
	
	$("#${cardId}_${uiid}_form").find("input").each(function(){
		$(this).css("background-color","transparent");
	});
	$("#${cardId}_${uiid}_form").find("textarea").each(function(){
		$(this).css("background-color","transparent");
	});
	$("#${cardId}_${uiid}_form").find("select").each(function(){
		$(this).css("background-color","transparent");
	});
	
	<#list fieldErrors.keySet() as f_name>
	$("#${cardId}_${uiid}_form [name=\"objState['${f_name}']\"]").css("background-color","#e5a0a0");
	$("#${cardId}_${uiid}_form [name=\"objState['${f_name}']\"]").focus();
	var msg="";
	<#list fieldErrors.get(f_name) as ferror>
	msg+="${ferror}";	
	</#list>
	$("#${cardId}_${uiid}_form [name=\"objState['${f_name}']\"]").tooltipster('destroy');
	var ${f_name}_tip=$("#${cardId}_${uiid}_form [name=\"objState['${f_name}']\"]").tooltipster({
		offsetY : 7,
		timer : 4000,
		theme : '.tooltipster-shadow'
	});
    ${f_name}_tip.tooltipster('update',msg);
    ${f_name}_tip.tooltipster('show');
	</#list>
	
	setTimeout(function(){
	     $('#${cardId}_event_error_message').hide();
	}, 2000);
	
	
});
</script>

<#list actionErrors as aerror>
    <span id="${cardId}_event_error_message" class="${cardId}_event_error animated flash">${aerror}</span>
</#list>

</#if>


