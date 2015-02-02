<script type="text/javascript">
$(document).ready(function(){
	$('#${cardId}_engine_button').click(function(){
		<#if engineActive>
			<#assign action_name = 'stopEngine'/>
		<#else>
			<#assign action_name = 'startEngine'/>
	    </#if>
		jQuery.ajax({
 			type: "POST",
 			url: "${action.calcAction(action_name, 'enginemonitor', null)}",
 			data: "cardiId=${cardId}",
 			success: function(msg){
 				if(msg=='success'){
 	 				callEvent("${cardId}_check_active_refresh");
 	 			}
 			}
 		});
 		return false;
	});
	
	$('#${cardId}_refresh_button').click(function(){
		callEvent("${cardId}_check_active_refresh");
		return false;
	});
});
</script>

<table style="width:100%;height:100%">
<tr>
	<td style="width:200px">
	<div id="${cardId}_info_engine">
		<p>	
			<label>Power:</label> <#if (terminated && shutdown)><span class="${cardId}_status_red"/><#else><span class="${cardId}_status_green"/></#if>
		</p>
		<p>	
			<label>Health:</label> <#if quequeSize=3><span class="${cardId}_status_green"/><#else><span class="${cardId}_status_red"/></#if>
		</p>
		<p>	
			<label>Completed Task:</label> <span>${completedTask?string}</span>
		</p>
	</div>	
	</td>
	<td style="text-align:center" class="<#if engineActive>eng_active<#else>eng_inactive</#if>">
	<span><#if engineActive>Active<#else>STOPPED</#if></span>
	</td>
</tr>
</table>

<button id="${cardId}_engine_button" style="width:48%;height:65px;margin-top:5px;float:left" class="btn btn-primary"><#if engineActive>STOP<#else>START</#if></button>
<button id="${cardId}_refresh_button" style="width:50%;height:30px;margin-top:5px;margin-left:5px;float:left" class="btn btn-primary">REFRESH</button>
<button id="${cardId}_shutdown_button" style="width:50%;height:30px;margin-top:5px;margin-left:5px;float:left;display:none" class="btn btn-primary">POWER-OFF</button>