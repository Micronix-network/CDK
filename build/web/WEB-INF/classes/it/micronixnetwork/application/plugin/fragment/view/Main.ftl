<#assign external_file = action.getCardParam('external_file')!'false'/>

<style>
.${cardId}_fragment_error{
		background-color: rgba(255, 0, 0, 0.05);
                overflow-x: scroll;  
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

</style>

<#attempt>
<#if external_file='true'>
	<#if fragment??>
		<#include "/WEB-INF/view/fragments/${fragment}.ftl"/>
	</#if>
<#else>
	<#if action.getCardParam('code')??>
		<#assign templateSource = action.getCardParam('code')>
		<#assign inlineTemplate = templateSource?interpret>
		<@inlineTemplate/>	 
	</#if> 
</#if> 
<#recover>		
	  	<h1>Error: fragment loading</h1>
	  	<div class="${cardId}_fragment_error">${.error}</div>
	</div>
</#attempt>


