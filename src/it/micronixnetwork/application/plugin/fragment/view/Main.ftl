<#assign external_file = action.getCardParam('external_file')!'false'/>
<#assign js_observed_event=action.getCardParam('observed_event')!""/>

<#import "macro/components.ftl" as comp>

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
</style>

<script type="text/javascript">
//Gestione della notifica di un evento da parte di una CARD osservata
function ${cardId}_controller_event(rise,param,name){
    try{${js_observed_event}}catch(err){
        alert(err);
    }
    return false;
}    
</script>   

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
<div class="fragment_error">${.error}</div>
</div>
</#attempt>


