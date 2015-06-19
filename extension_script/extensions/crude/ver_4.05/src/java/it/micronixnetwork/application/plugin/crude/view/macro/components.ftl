<#macro view_update roleModi=true cardId="${cardId}">
<#assign direct_edit=action.getCardParam('direct_edit')!'false'/>
<#assign prototype=action.getCardParam('prototype')!''/>
<#assign aby_edit_flag=action.getCardParam('aby_edit')!'true'/>
<#assign gui_type=action.getCardParam('gui_type')!'n'/>
<#assign crud_observers=action.getCardParam('crud_observers')!''/>
<#if prototype!=''>
    <#assign prototypeName=prototype.substring(prototype.lastIndexOf('.')+1)!''/>
</#if>
<#if gui_type=='d'>
    <#assign origin_class="origin"/>
<#else>
    <#assign origin_class=""/>
</#if>
<#if prototypeName??>
<div id="${cardId}_${prototypeName}_view_object_pane" class="${cardId}_page right ${origin_class}" style="height:100%;">
    <div class="${cardId}_form_title"  style="margin-bottom: 4px;height:35px">
        <table style="width:100%">
            <tr>
                <td style="text-align:left;">
                <#if gui_type=='n'>            
                    <button  id="${cardId}_${prototypeName}_info_list_button" class="${cardId}_view_button btn btn-small btn-primary ${cardId}_image_btn icon-arrow-left-alt1" style="display:none"><span>${action.getText("crude.button.list","To List")}</span></button>
                </#if>
                <#if aby_edit_flag=='true' && roleModi>
                    <button  id="${cardId}_${prototypeName}_info_cancel_button" class="${cardId}_view_button btn btn-small btn-primary ${cardId}_image_btn icon-arrow-left-alt1" style="display:none"><span>${action.getText("crude.button.cancel","Cancel")}</span></button>
                </#if>
                </td>
                <td style="text-align:right;">
                <#if aby_edit_flag=='true' && roleModi>
                    <button id="${cardId}_${prototypeName}_info_update_button" class="${cardId}_view_button btn btn-small btn-primary ${cardId}_image_btn icon-cole-townsend-check" style="display:none"><span>${action.getText("crude.button.update","Update")}</span></button>
                    <button id="${cardId}_${prototypeName}_info_modify_button" class="${cardId}_view_button btn btn-small btn-primary ${cardId}_image_btn icon-pencil" style="display:none"><span>${action.getText("crude.button.modify","Modify")}</span></button>
                </#if>
                </td>
            </tr>
        </table>
    </div>
    <div id="${cardId}_${prototypeName}_view_ui_scroll" style="overflow-y:auto">
        <@gaf.div id="${cardId}_${prototypeName}_view_ui_info_obj_action" 
            action="viewInfo"
            namespace="crude"
            loadImage="true" 
            formId="${cardId}_view_object_form" 
            listen="${cardId}_${prototypeName}_view_ui_info_object_refresh" 
            startOnLoad="false" 
            style="min-height:200px">
        </@gaf.div>	
        <@gaf.div id="${cardId}_${prototypeName}_view_ui_get_obj_action" 
            action="viewGet"
            namespace="crude"
            loadImage="true" 
            formId="${cardId}_view_object_form" 
            listen="${cardId}_${prototypeName}_view_ui_get_object_refresh" 
            startOnLoad="false" 
            style="min-height:200px">
        </@gaf.div>
    </div>
    <#if aby_edit_flag='true' && roleModi>
        <@gaf.div id="${cardId}_${prototypeName}_view_ui_update_object_action" 
            action="viewUpdate"
            namespace="crude" 
            loadImage="spinner-7 normal" 
            formId="${cardId}_object_form_post" 
            listen="${cardId}_${prototypeName}_view_ui_update_object_refresh" 
            startOnLoad="false" 
            style="height:35px;text-align:center;width:100%;overflow:hidden;position:absolute;top:0px;z-index:-1">
        </@gaf.div>
    </#if>
</div>
<script type="text/javascript">
    $('#${cardId}_${prototypeName}_info_list_button').click(function(){
        ${cardId}_slideToPage($('#${cardId}_${prototypeName}_view_object_pane'),$('#${cardId}_list_slide'), 'left');
        <#if crud_observers!=''>		 
            <@js.notify_observer crud_observers=crud_observers event_name='back_to_list'/>
        </#if>
    });
    
    $('#${cardId}_${prototypeName}_info_cancel_button').click(function(){
        if(${cardId}_chaged_values){
            ${cardId}_modified_object_confirm_dialog.show();
        }else{
            $('#${cardId}_${prototypeName}_view_ui_info_obj_action').show();
            $("#${cardId}_view_ui_form").empty();
            $('#${cardId}_${prototypeName}_view_ui_get_obj_action').hide();
            <#if direct_edit="false">
                callEvent("${cardId}_${prototypeName}_view_ui_info_object_refresh");
                $('#${cardId}_${prototypeName}_view_ui_update_object_action .div_asinc_content').html('');
            <#else>
                ${cardId}_slideToPage($('#${cardId}_${prototypeName}_view_object_pane'),$('#${cardId}_list_slide'), 'left');
            </#if>
        }
    });
	
    $('#${cardId}_${prototypeName}_info_modify_button').click(function(){
        $('#${cardId}_${prototypeName}_view_ui_info_obj_action').hide();
        $('#${cardId}_${prototypeName}_view_ui_info_obj_action_content').empty();
        $('#${cardId}_${prototypeName}_view_ui_get_obj_action').show();
        $('#${cardId}_${prototypeName}_view_ui_update_object_action').show();
        $('#${cardId}_${prototypeName}_view_ui_update_object_action .div_asinc_content').html('<span class="${cardId}_event_message animated flash">${action.getText("crude.form.rquired")}</span>');
        callEvent("${cardId}_${prototypeName}_view_ui_get_object_refresh");
    });
		
    $('#${cardId}_${prototypeName}_info_update_button').click(function(){
        clearForm($("#${cardId}_object_form_post"));
        $("#${cardId}_view_main_object_form .right_input_multiselect.TakeOver option").attr("selected", "selected");
        copyForm($("#${cardId}_object_form_post"), $("#${cardId}_view_ui_form"));
        addHiddenToForm($('#${cardId}_object_form_post'),'targetClass','${prototype}');
        callEvent("${cardId}_${prototypeName}_view_ui_update_object_refresh");
    });
</script>    
</#if>
</#macro>

<#macro insert_update cardId="${cardId}">
<#assign prototype=action.getCardParam('prototype')!''/>
<#if prototype!=''>
    <#assign prototypeName=prototype.substring(prototype.lastIndexOf('.')+1)!''/>
</#if>
<#if prototypeName??>
<div id="${cardId}_${prototypeName}_new_object_pane" class="${cardId}_page right" style="height:100%">
    <div class="${cardId}_form_title"  style="margin-bottom: 4px;height:35px">
        <table style="width:100%">
            <tr>
                <td style="text-align:left;">
                <button id="${cardId}_${prototypeName}_new_cancel_button" class="${cardId}_insert_button btn btn-small btn-primary ${cardId}_image_btn icon-arrow-left-alt1" style="display:none"><span>${action.getText("crude.button.cancel","Cancel")}</span></button>
                </td>
                <td style="text-align:right;">
                <button id="${cardId}_${prototypeName}_new_save_button" class="${cardId}_insert_button btn btn-small btn-primary ${cardId}_image_btn icon-cole-townsend-check" style="display:none"><span>${action.getText("crude.button.save","Save")}</span></button>
                </td>
            </tr>
        </table>
    </div>
    <div id="${cardId}_${prototypeName}_new_ui_scroll" style="overflow-y:auto">
        <@gaf.div id="${cardId}_${prototypeName}_new_ui_new_object_action" 
            action="insertNew" 
            namespace="crude" 
            loadImage="true" 
            formId="${cardId}_get_object_form"
            listen="${cardId}_${prototypeName}_new_ui_new_object_refresh" 
            startOnLoad="false" 
            style=";min-height:200px">
        </@gaf.div>
    </div>
    <@gaf.div id="${cardId}_${prototypeName}_new_ui_insert_object_action" 
        action="insertCreate" 
        namespace="crude" 
        loadImage="spinner-7 normal" 
        formId="${cardId}_object_form_post" 
        listen="${cardId}_${prototypeName}_new_ui_insert_object_refresh" 
        startOnLoad="false" 
        style="height:35px;text-align:center;width:100%;overflow:hidden;position:absolute;top:0px;z-index:-1">
        ${action.getText("crude.form.rquired")}
    </@gaf.div>
</div>
<script type="text/javascript">
    $('#${cardId}_${prototypeName}_new_cancel_button').click(function(){
        ${cardId}_slideToPage($('#${cardId}_${prototypeName}_new_object_pane'),$('#${cardId}_list_slide'), 'left');
    });
		
    $('#${cardId}_${prototypeName}_new_save_button').click(function(){
        $('#${cardId}_${prototypeName}_new_ui_insert_object_action').show();
        clearForm($("#${cardId}_object_form_post"));
        copyForm($("#${cardId}_object_form_post"), $("#${cardId}_new_ui_form"));
        addHiddenToForm($('#${cardId}_object_form_post'),'targetClass','${prototype}');
        callEvent("${cardId}_${prototypeName}_new_ui_insert_object_refresh");
    });
</script>    
</#if>
</#macro>

<#macro list cardId="${cardId}">
<#assign aby_delete_flag=action.getCardParam('aby_delete')!'true'/>
<#assign aby_insert_flag=action.getCardParam('aby_insert')!'true'/>
<#assign rolesRemove=action.getCardParam('rolesRemove')!''/>
<#assign rolesCreate=action.getCardParam('rolesCreate')!''/>
<#assign rolesMody=action.getCardParam('rolesMody')!''/>
<#assign aby_filter_flag=action.getCardParam('aby_filter')!'true'/>
<#assign gui_type=action.getCardParam('gui_type')!'n'/>
<#assign save_and_go=action.getCardParam('save_and_go')!'false'/>
<#assign rowxpage=action.getCardParam('rowxpage')!'30'/>
<#assign prototype=action.getCardParam('prototype')!''/>
<#assign search_on_load=action.getCardParam('search_on_load')!'true'/>
<#assign popup_gui=action.getCardParam('popup_gui')!'false'/>
<#if prototype!=''>
    <#assign prototypeName=prototype.substring(prototype.lastIndexOf('.')+1)!''/>
</#if>
<#if gui_type=='l'>
    <#assign save_and_go='true'/>
</#if>
<#if prototypeName??>
<div id="${cardId}_list_slide" class="${cardId}_page center origin" style="height:100%">
    
    <div class="${cardId}_form_title"  style="margin-bottom: 4px;height:35px;">
        <table style="width:100%">
            <tr>
                <td style="text-align:left;">
                <#if aby_delete_flag='true'  && action.checkRole(user.roles,rolesRemove)>
                    <button id="${cardId}_delete_button" class="btn btn-small btn-primary ${cardId}_image_btn icon-trash" style="" disabled><span>${action.getText("crude.button.delete","Delete")}</span></button>
                </#if>
                </td>
                <td style="text-align:right;">
                <#if aby_insert_flag='true' && action.checkRole(user.roles,rolesCreate)>
                    <button id="${cardId}_create_button" class="btn btn-small btn-primary ${cardId}_image_btn icon-plus-alt" style=""><span>${action.getText("crude.button.create","Create new")}</span></button>
                </#if>
                </td>
            </tr>
        </table>
    </div>
    <form id="${cardId}_${prototypeName}_form" method="post" style="border-bottom: 1px solid #0099ff;padding :2px;">
        <#if aby_filter_flag='true'>
        <@crude.filterForm id="${cardId}_${prototypeName}_filter" className="${cardId}_filter_console" prototype="${prototype}"/>
        </#if>
        <input type="hidden" name="targetClass" value="${prototype}"/>
        <input type="hidden" name="page"/>
        <input type="hidden" name="size" value="${rowxpage}"/>
        <input type="hidden" name="toOrder"/>
        <input type="hidden" name="inDelete" value="false"/>
        <input type="hidden" name="inCreate" value="false"/>
        <input type="hidden" name="inUpdate" value="false"/>
    </form>
    <@gaf.div id="${cardId}_${prototypeName}_result_table" 
        action="find"
        namespace="crude" 
        loadImage="true" 
        formId="${cardId}_${prototypeName}_form" 
        listen="${cardId}_${prototypeName}_find_refresh" 
        startOnLoad="${search_on_load}" 
        style="margin-left:2px;margin-right:2px">
    </@gaf.div>

    <div id="${cardId}_list_slide_message" style="height:35px;text-align:center;width:100%;overflow:hidden;position:absolute;top:0px;z-index:-1">
    </div>

    <@gaf.div id="${cardId}_remove_object_action" 
        action="remove" 
        namespace="crude" 
        loadImage="spinner-1 normal" 
        formId="${cardId}_remove_object_form" 
        listen="${cardId}_remove_object_refresh" 
        startOnLoad="false" 
        style="height:35px;text-align:center;width:100%;overflow:hidden;position:absolute;top:0px;z-index:-1">
    </@gaf.div>

    <iframe id="${cardId}_download_file_action" 
            style="display:none;border:none;height:35px;text-align:center;width:100%;overflow:hidden;position:absolute;top:0px;z-index:-1">
    </iframe>
</div>
</#if>
<script type="text/javascript">
<#if aby_insert_flag='true' && action.checkRole(user.roles,rolesCreate)>
    <#if save_and_go?boolean>
    $('#${cardId}_create_button').click(function(){
        clearForm($('#${cardId}_get_object_form'));
        copyForm($("#${cardId}_get_object_form"), $("#${cardId}_${prototypeName}_form"));
        callEvent("${cardId}_${prototypeName}_new_ui_new_object_refresh");
        });
    <#else>
    $('#${cardId}_create_button').click(function(){
        $('#${cardId}_${prototypeName}_new_ui_new_object_action').show();
        clearForm($('#${cardId}_get_object_form'));
        clearForm($('#${cardId}_new_ui_form'));
        copyForm($("#${cardId}_get_object_form"), $("#${cardId}_${prototypeName}_form"));
        $('#${cardId}_${prototypeName}_new_ui_insert_object_action_content').empty();
        $('#${cardId}_${prototypeName}_new_ui_insert_object_action .div_asinc_content').html('<span class="${cardId}_event_message animated flash">${action.getText("crude.form.rquired")}</span>');
        callEvent("${cardId}_${prototypeName}_new_ui_new_object_refresh");
        <#if popup_gui?boolean>
            ${cardId}_insert_update_dialog.show();
        <#else>    
            ${cardId}_slideToPage($('#${cardId}_list_slide'),$('#${cardId}_${prototypeName}_new_object_pane'), 'right');
        </#if>
        $('#${cardId}_${prototypeName}_form [name="inCreate"]').val('true');
    });
    </#if>
</#if>
<#if aby_delete_flag='true' && action.checkRole(user.roles,rolesRemove)>    
    $('#${cardId}_delete_button').click(function(){
        // Controllo presenza checkbox selezionati
        active=false;
        $('.${cardId}_${prototypeName}_selectable').each(function(){
          if($(this).attr('checked')=='checked'){
                  active=true;
          };
          return !active;
        });
        if(active){
            ${cardId}_remove_selected_objects_confirm_dialog.show();
        }
        return false;
    });
</#if>    

${cardId}_card.listRefresh= function(){
    callEvent('${cardId}_${prototypeName}_find_refresh');
}

${cardId}_card.setFilter= function(name,val){
    $('#${cardId}_filter_'+name).val(val);
}

${cardId}_card.setTitle= function(message){
    crud2_set_list_message(message);
}

</script>
</#macro>



<#macro view_update_dialog roleModi=true cardId="${cardId}">
    <#assign direct_edit=action.getCardParam('direct_edit')!'false'/>
    <#assign pop_width=action.getCardParam('pop_width')!'550'/>
    <#assign pop_height=action.getCardParam('pop_height')!'400'/>
    <#assign prototype=action.getCardParam('prototype')!''/>
    <#assign aby_edit_flag=action.getCardParam('aby_edit')!'true'/>
    <#assign gui_type=action.getCardParam('gui_type')!'n'/>
    <#assign crud_observers=action.getCardParam('crud_observers')!''/>
    <#if prototype!=''>
        <#assign prototypeName=prototype.substring(prototype.lastIndexOf('.')+1)!''/>
    </#if>
    <#if gui_type=='d'>
        <#assign origin_class="origin"/>
    <#else>
        <#assign origin_class=""/>
    </#if>

    <#if pop_height==''>
        <#assign pop_height='400'/>
    </#if>

    <#if pop_width==''>
        <#assign pop_width='550'/>
    </#if>
    

    <#if prototypeName??>
    <div id="${cardId}_view_update_dialog" class="md-modal md-effect-1">
        <div class="md-content" style="width:${pop_width}px;height:${pop_height}px;overflow: hidden;">
            <div id="${cardId}_${prototypeName}_dialog_view_ui_scroll" style="overflow-y:auto;height:${pop_height?number-65}px">
                <@gaf.div id="${cardId}_${prototypeName}_view_ui_info_obj_action" 
                    action="viewInfo"
                    namespace="crude"
                    loadImage="true" 
                    formId="${cardId}_view_object_form" 
                    listen="${cardId}_${prototypeName}_view_ui_info_object_refresh" 
                    startOnLoad="false" 
                    style="min-height:200px">
                </@gaf.div>	
                <@gaf.div id="${cardId}_${prototypeName}_view_ui_get_obj_action" 
                    action="viewGet"
                    namespace="crude"
                    loadImage="true" 
                    formId="${cardId}_view_object_form" 
                    listen="${cardId}_${prototypeName}_view_ui_get_object_refresh" 
                    startOnLoad="false" 
                    style="min-height:200px">
                </@gaf.div>
            </div>
            <#if aby_edit_flag='true' && roleModi>
            <@gaf.div id="${cardId}_${prototypeName}_view_ui_update_object_action" 
                action="viewUpdate"
                namespace="crude" 
                loadImage="spinner-7 normal" 
                formId="${cardId}_object_form_post" 
                listen="${cardId}_${prototypeName}_view_ui_update_object_refresh" 
                startOnLoad="false" 
                style="height: 35px;text-align: center;width: 100%;overflow: hidden;position: absolute;bottom: 35px;z-index: -1;border: 1px solid #eee;padding: 0px;">
            </@gaf.div>
            </#if>
            <div class="md-button-bar">
                <table style="width:100%">
                    <tr>
                        <td style="text-align:left;">
                        <#if gui_type=='n'>            
                            <button  id="${cardId}_${prototypeName}_info_list_button" class="${cardId}_view_button btn btn-small btn-primary ${cardId}_image_btn icon-arrow-left-alt1" style="display:none"><span>${action.getText("crude.button.list","To List")}</span></button>
                        </#if>
                        <#if aby_edit_flag=='true' && roleModi>
                            <button  id="${cardId}_${prototypeName}_info_cancel_button" class="${cardId}_view_button btn btn-small btn-primary ${cardId}_image_btn icon-arrow-left-alt1" style="display:none"><span>${action.getText("crude.button.cancel","Cancel")}</span></button>
                        </#if>
                        </td>
                        <td style="text-align:right;">
                        <#if aby_edit_flag=='true' && roleModi>
                            <button id="${cardId}_${prototypeName}_info_update_button" class="${cardId}_view_button btn btn-small btn-primary ${cardId}_image_btn icon-cole-townsend-check" style="display:none"><span>${action.getText("crude.button.update","Update")}</span></button>
                            <button id="${cardId}_${prototypeName}_info_modify_button" class="${cardId}_view_button btn btn-small btn-primary ${cardId}_image_btn icon-pencil" style="display:none"><span>${action.getText("crude.button.modify","Modify")}</span></button>
                        </#if>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <script type="text/javascript">
    var ${cardId}_view_update_dialog = new (function() {

        function init() {
            $('#${cardId}_${prototypeName}_info_list_button').click(function(){
                hide();
                <#if crud_observers!=''>		 
                    <@js.notify_observer crud_observers=crud_observers event_name='back_to_list'/>
                </#if>
            });
    
            $('#${cardId}_${prototypeName}_info_cancel_button').click(function(){
                if(${cardId}_chaged_values){
                    ${cardId}_modified_object_confirm_dialog.show();
                }else{
                    $('#${cardId}_${prototypeName}_view_ui_info_obj_action').show();
                    $("#${cardId}_view_ui_form").empty();
                    $('#${cardId}_${prototypeName}_view_ui_get_obj_action').hide();
                    <#if direct_edit="false">
                        callEvent("${cardId}_${prototypeName}_view_ui_info_object_refresh");
                        $('#${cardId}_${prototypeName}_view_ui_update_object_action .div_asinc_content').html('');
                    <#else>
                        hide();
                    </#if>
                }
            });
	
            $('#${cardId}_${prototypeName}_info_modify_button').click(function(){
                $('#${cardId}_${prototypeName}_view_ui_info_obj_action').hide();
                $('#${cardId}_${prototypeName}_view_ui_info_obj_action_content').empty();
                $('#${cardId}_${prototypeName}_view_ui_get_obj_action').show();
                $('#${cardId}_${prototypeName}_view_ui_update_object_action').show();
                $('#${cardId}_${prototypeName}_view_ui_update_object_action .div_asinc_content').html('<span class="${cardId}_event_message animated flash">${action.getText("crude.form.rquired")}</span>');
                callEvent("${cardId}_${prototypeName}_view_ui_get_object_refresh");
            });
		
            $('#${cardId}_${prototypeName}_info_update_button').click(function(){
                clearForm($("#${cardId}_object_form_post"));
                $("#${cardId}_view_main_object_form .right_input_multiselect.TakeOver option").attr("selected", "selected");
                copyForm($("#${cardId}_object_form_post"), $("#${cardId}_view_ui_form"));
                addHiddenToForm($('#${cardId}_object_form_post'),'targetClass','${prototype}');
                callEvent("${cardId}_${prototypeName}_view_ui_update_object_refresh");
            });
        };

        this.hide=hide;
        function hide() {
            $('#${cardId}_view_update_dialog').removeClass('md-show' );
            $('.md-overlay').removeClass('md-show');
        }

        this.show=show;
        function show() {
            $('#${cardId}_view_update_dialog').addClass('md-show');
            $('.md-overlay').addClass('md-show');
        }

        init();	
    })();
    </script>
    </#if>
</#macro>


<#macro insert_update_dialog cardId="${cardId}">
<#assign prototype=action.getCardParam('prototype')!''/>
<#assign pop_width=action.getCardParam('pop_width')!'550'/>
<#assign pop_height=action.getCardParam('pop_height')!'400'/>
<#if prototype!=''>
    <#assign prototypeName=prototype.substring(prototype.lastIndexOf('.')+1)!''/>
</#if>

<#if pop_height==''>
        <#assign pop_height='400'/>
    </#if>

<#if pop_width==''>
    <#assign pop_width='550'/>
</#if>

<#if prototypeName??>
<div id="${cardId}_insert_update_dialog" class="md-modal md-effect-1">
    <div class="md-content" style="width:${pop_width}px;height:${pop_height}px;overflow: hidden;">
    <div id="${cardId}_${prototypeName}_dialog_new_ui_scroll" style="overflow-y:auto;height:${pop_height?number-65}px">
        <@gaf.div id="${cardId}_${prototypeName}_new_ui_new_object_action" 
            action="insertNew" 
            namespace="crude" 
            loadImage="true" 
            formId="${cardId}_get_object_form"
            listen="${cardId}_${prototypeName}_new_ui_new_object_refresh" 
            startOnLoad="false" 
            style=";min-height:200px">
        </@gaf.div>
    </div>
    <@gaf.div id="${cardId}_${prototypeName}_new_ui_insert_object_action" 
        action="insertCreate" 
        namespace="crude" 
        loadImage="spinner-7 normal" 
        formId="${cardId}_object_form_post" 
        listen="${cardId}_${prototypeName}_new_ui_insert_object_refresh" 
        startOnLoad="false" 
        style="height: 35px;text-align: center;width: 100%;overflow: hidden;position: absolute;bottom: 35px;z-index: -1;border: 1px solid #eee;padding: 0px;">
        ${action.getText("crude.form.rquired")}
    </@gaf.div>
    <div class="md-button-bar">
        <table style="width:100%">
            <tr>
                <td style="text-align:left;">
                <button id="${cardId}_${prototypeName}_new_cancel_button" class="${cardId}_insert_button btn btn-small btn-primary ${cardId}_image_btn icon-arrow-left-alt1" style="display:none"><span>${action.getText("crude.button.cancel","Cancel")}</span></button>
                </td>
                <td style="text-align:right;">
                <button id="${cardId}_${prototypeName}_new_save_button" class="${cardId}_insert_button btn btn-small btn-primary ${cardId}_image_btn icon-cole-townsend-check" style="display:none"><span>${action.getText("crude.button.save","Save")}</span></button>
                </td>
            </tr>
        </table>
    </div>
</div>
</div> 
<script type="text/javascript">
    var ${cardId}_insert_update_dialog = new (function() {

        function init() {
            $('#${cardId}_${prototypeName}_new_cancel_button').click(function(){
                hide();
            });
		
            $('#${cardId}_${prototypeName}_new_save_button').click(function(){
                $('#${cardId}_${prototypeName}_new_ui_insert_object_action').show();
                clearForm($("#${cardId}_object_form_post"));
                copyForm($("#${cardId}_object_form_post"), $("#${cardId}_new_ui_form"));
                addHiddenToForm($('#${cardId}_object_form_post'),'targetClass','${prototype}');
                callEvent("${cardId}_${prototypeName}_new_ui_insert_object_refresh");
            });
        };

        this.hide=hide;
        function hide() {
            $('#${cardId}_insert_update_dialog').removeClass('md-show' );
            $('.md-overlay').removeClass('md-show');
        }

        this.show=show;
        function show() {
            $('#${cardId}_insert_update_dialog').addClass('md-show');
            $('.md-overlay').addClass('md-show');
        }

        init();	
    })();
    </script>  
</#if>
</#macro>


<#macro confirm_dialog id message cardId="${cardId}">
<div id="${cardId}_${id}_confirm_dialog" class="${cardId}_Dialog ${cardId}_topDownDialog" style="position:fixed:left:50%;width:300px;-webkit-transform: translateX(-50%);-moz-transform: translateX(-50%);-ms-transform: translateX(-50%);transform: translateX(-50%)">
    <h3>
    ?
    </h3>
    <div class="diag_message">${message}</div>
    <div class="form_title">
    <table style="width:100%">
        <tr>
            <td style="text-align:left;">
            <button id="${cardId}_${id}_cancel_button" class="btn btn-small btn-primary ${cardId}_image_btn icon-denied">
                <span>
                    ${action.getText("crude.button.cancel")}
                </span>
            </button>
            </td>
            <td style="text-align:right;">
            <button id="${cardId}_${id}_apply_button" class="btn btn-small btn-primary ${cardId}_image_btn icon-cole-townsend-check">
                <span>
                    ${action.getText("crude.button.delete")}
                </span>    
            </button>
            </td>
        </tr>
    </table>
    </div>
</div>
<script type="text/javascript">
    var ${cardId}_${id}_confirm_dialog = new (function() {
			
        function init() {
            $('#${cardId}_${id}_cancel_button').click(function(){
                hide();
            });
            
            $('#${cardId}_${id}_apply_button').click(function(){
                try{
                    hide();
                    ${cardId}_${id}_confirm_dialog_apply();
                }catch(error){
                    alert(error);
                    hide();
                }
            });
            
        };

        this.hide=hide;
        function hide() {
            $('#${cardId}_${id}_confirm_dialog').animate({top: "-100%"}, 500,function(){
                $('#${cardId}_overlay_crude_ui').css('visibility','hidden');
                $('#${cardId}_overlay_crude_ui').css('opacity','0');
            });
        }

        this.show=show;
        function show() {
            $('#${cardId}_overlay_crude_ui').css('visibility','visible');
            $('#${cardId}_overlay_crude_ui').css('opacity','1');
            $trg=$('#${cardId}_${id}_confirm_dialog');
            $trg.animate({top: "0"}, 500);
        }
        init();	
    })(); 
</script>
</#macro>


<#macro confirm_dialog_popup id message cardId="${cardId}">
<div id="${cardId}_${id}_confirm_dialog" class="${cardId}_Dialog md-modal" style="width:300px;">
    <h3>
    ?
    </h3>
    <div class="diag_message">${message}</div>
    <div class="form_title">
    <table style="width:100%">
        <tr>
            <td style="text-align:left;">
            <button id="${cardId}_${id}_cancel_button" class="btn btn-small btn-primary ${cardId}_image_btn icon-denied">
                <span>
                    ${action.getText("crude.button.cancel")}
                </span>
            </button>
            </td>
            <td style="text-align:right;">
            <button id="${cardId}_${id}_apply_button" class="btn btn-small btn-primary ${cardId}_image_btn icon-cole-townsend-check">
                <span>
                    ${action.getText("crude.button.delete")}
                </span>    
            </button>
            </td>
        </tr>
    </table>
    </div>
</div>
<script type="text/javascript">
    var ${cardId}_${id}_confirm_dialog = new (function() {
			
        function init() {
            $('#${cardId}_${id}_cancel_button').click(function(){
                hide();
            });
            
            $('#${cardId}_${id}_apply_button').click(function(){
                try{
                    hide();
                    ${cardId}_${id}_confirm_dialog_apply();
                }catch(error){
                    alert(error);
                    hide();
                }
            });
        };

        this.hide=hide;
        function hide() {
            $('.md-overlay').css('z-index','1000');
            $('#${cardId}_${id}_confirm_dialog').removeClass('md-show');
        }

        this.show=show;
        function show() {
            $('.md-overlay').css('z-index','3000');
            $('#${cardId}_${id}_confirm_dialog').addClass('md-show');
        }
        init();	
    })(); 
</script>
</#macro>
