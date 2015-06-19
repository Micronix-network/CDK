<#import "../macro/javascript.ftl" as js>

<#if targetClass!=''>
    <#assign targetClassName=targetClass.substring(targetClass.lastIndexOf('.')+1)!''/>
</#if>
<#assign crud_observers=action.getCardParam('crud_observers')!''/>

<script type="text/javascript">
$(document).ready(function(){		 
    <#if crud_observers!=''>
        <@js.notify_observer crud_observers=crud_observers event_name='object_deleted'/>
    </#if>
    callEvent('${cardId}_${targetClassName}_find_refresh');
    setTimeout(function(){
        $('#${cardId}_event_message').fadeOut('1000',function(){
            $('#${cardId}_remove_object_action').hide();
            ${cardId}_set_list_message();
        });
    }, 2000);
});
</script>
<span id="${cardId}_event_message" class="${cardId}_event_message animated flash">${action.getText(targetClassName+".removed.message")}</span>


