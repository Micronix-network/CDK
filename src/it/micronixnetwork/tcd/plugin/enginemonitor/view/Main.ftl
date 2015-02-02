<#assign external_file = action.getCardParam('external_file')!'false'/>

<style>
.fragment_error{
		background-color: rgba(255, 0, 0, 0.05);
		min-height:100px;
		font-size: 14px;
		margin-left:5px;
		margin-right:5px;
		color: #333;
		padding-left: 5px;
		padding-right: 5px;
		padding-bottom: 5px;
		padding-top: 5px;
		z-index: 1000;
		-moz-border-radius: 4px;
		-webkit-border-radius: 4px;
		border-radius: 4px;
		-moz-box-shadow: inset 0 0 3px #aaa;
		-webkit-box-shadow: inset 0 0 3px #aaa;
		box-shadow: inner 0 0 3px #aaa;
}

.eng_active{
		background-color: rgba(100, 255, 100,1);
		min-height:100px;
		font-size: 20px;
		margin-left:5px;
		margin-right:5px;
		color: #333;
		padding-left: 5px;
		padding-right: 5px;
		padding-bottom: 5px;
		padding-top: 5px;
		z-index: 1000;
		-moz-border-radius: 20px;
		-webkit-border-radius: 20px;
		border-radius: 20px;
		-moz-box-shadow: inset 0 0 3px #aaa;
		-webkit-box-shadow: inset 0 0 3px #aaa;
		box-shadow: inner 0 0 3px #aaa;
}

.eng_inactive{
		background-color: rgba(255, 100, 100,1);
		min-height:100px;
		font-size: 20px;
		margin-left:5px;
		margin-right:5px;
		color: #333;
		padding-left: 5px;
		padding-right: 5px;
		padding-bottom: 5px;
		padding-top: 5px;
		z-index: 1000;
		-moz-border-radius: 20px;
		-webkit-border-radius: 20px;
		border-radius: 20px;
		-moz-box-shadow: inset 0 0 3px #aaa;
		-webkit-box-shadow: inset 0 0 3px #aaa;
		box-shadow: inner 0 0 3px #aaa;
}

#${cardId}_info_engine {
	padding-left: 5px;
	font-size:12px;
}

#${cardId}_info_engine p{
	margin-top:4px;
	margin-bottom:4px;
}

#${cardId}_info_engine label{
	width: 130px;
	height: 100%;
	display: inline-block;
	text-align: right;
	margin-right: 10px;
	border: none;
	padding:3px;
	background-color: #eee;
	color: #333;
	z-index: 1000;
	font-weight:bold;
	-moz-border-radius: 10px 0 0 10px;
	-webkit-border-radius: 10px 0 0 10px;
	border-radius: 10px 0 0 10px;
}

#${cardId}_info_engine span{
	line-height:18px;
}

.${cardId}_status_green{
		position: relative;
		background-color: rgba(100, 255, 100,1);
		height:16px;
		width:16px;
		top:4px;
		display:inline-block;
		-moz-border-radius: 10px;
		-webkit-border-radius: 10px;
		border-radius: 10px;
}

.${cardId}_status_red{
		position: relative;
		background-color: rgba(255, 100, 100,1);
		height:16px;
		width:16px;
		top:4px;
		display:inline-block;
		-moz-border-radius: 10px;
		-webkit-border-radius: 10px;
		border-radius: 10px;
}
</style>

<#attempt>
<form id="${cardId}_form_semaphore">
<@gaf.div id="${cardId}_semaphore" 
		action="check_active"
		loadImage="spinner-7 normal" 
		namespace="enginemonitor"  
		formId="${cardId}_form_semaphore" 
		listen="${cardId}_check_active_refresh" 
		startOnLoad="true" 
		reloadTime="60"
		style="margin-left:2px;margin-right:2px">
</@gaf.div>
<#recover>		
	  	<h1>Error: Engine Monitor</h1>
	  	<div class="fragment_error">${.error}</div>
	</div>
</#attempt>


