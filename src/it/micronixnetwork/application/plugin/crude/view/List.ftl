<#assign rolesRemove=action.getCardParam('rolesRemove')!''/>
<#assign aby_delete_flag=action.getCardParam('aby_delete')!'true'/>	
<#assign aby_edit_flag=action.getCardParam('aby_edit')!'true'/>
<#assign type_flag=action.getCardParam('gui_type')!'n'/>
<#assign crud_observers=action.getCardParam('crud_observers')!''/>
<#assign autoselect_flag=action.getCardParam('autoselect')!'false'/>
<#assign direct_edit=action.getCardParam('direct_edit')!'false'/>	
<#assign targetClassName=targetClass.substring(targetClass.lastIndexOf('.')+1)!''/>
<#assign popup_gui=action.getCardParam('popup_gui')!'false'/>

<#import "/template/gaf/macro/events.ftl" as events>

<style>
.highlight{
	background-color: #3879D9;
	color:white;
}
	
#${cardId}_${targetClassName}.${cardId}_gbtable .hide_column {
    display: none;
}
</style>

<script type="text/javascript">	

// Funzione per il cambio pagina
function changePage_${cardId}_${targetClassName}(page) {
    $('#${cardId}_${targetClassName}_form [name="page"]').val(page);
    callEvent('${cardId}_${targetClassName}_find_refresh');
};

function ${cardId}_helperCellDrag(evt,ui){
    var clone=$(ui).clone();
    clone.addClass('${cardId}_cell_drag');
    return clone.appendTo('body').show();
}
	
function ${cardId}_manageDeleteButton(){
    active=false;
    $('.${cardId}_${targetClassName}_selectable').each(function(){
      if($(this).attr('checked')=='checked'){
              active=true;
      };
      return !active;
    });
    if(active){
        $('#${cardId}_delete_button').removeAttr('disabled', 'disabled' );
    }else{
        $('#${cardId}_delete_button').attr('disabled', 'disabled' );
    }	
}


//Marca la riga della search list come selezionata
function ${cardId}_setSelected($row){
    $('.${cardId}_${targetClassName}_resultRow').removeClass('selected_row');
    $row.addClass('selected_row');	
    ${cardId}_row_selected=$row;
}

$(document).ready(function(){

    ${cardId}_manageDeleteButton();

    //Blocco selezione testo nelle righe della tabella
    $('#${cardId}_${targetClassName}.${cardId}_gbtable')
            .attr('unselectable', 'on')
            .css('user-select', 'none')
            .css('MozUserSelect', 'none')
            .on('selectstart', false)
            .on('mousedown', false);


    //Calcolo altezza widget
    var title_h=$('#${cardId}_list_slide .${cardId}_form_title').outerHeight(true);
    var filter_form_h=$('#${cardId}_${targetClassName}_form').outerHeight(true);
    var wdgHeader_h=$('#${cardId}_card .card-header').outerHeight(true)+6;
    var header_h=30;
    var footer_h=45
    var delta=title_h+filter_form_h+header_h+footer_h+wdgHeader_h;
    var list_h=$('#${cardId}_card').innerHeight()-delta;

    $('#${cardId}_${targetClassName}_list_zone').height(list_h+header_h);	

    try{	
        ${cardId}_${targetClassName}_table=$('#${cardId}_${targetClassName}.${cardId}_gbtable').dataTable( {
            "sScrollY": list_h,
            "bPaginate": false,
            "bScrollCollapse": true,
            "bFilter": false,
            "bSort": false,
            "bInfo": false,
        });
    }catch(err){}

    //Controllo visualizzazione check di riga
    var inDelete=$('#${cardId}_${targetClassName}_form [name="inDelete"]').val();
    var inCreate=$('#${cardId}_${targetClassName}_form [name="inCreate"]').val();

    if(inDelete=='true'){
            $('#${cardId}_${targetClassName}.${cardId}_gbtable .hide_column').show();
    }else{
            $('#${cardId}_create_button').show();
    }

    //Gestione evidenziazione riga al passaggio del mouse
    $('.${cardId}_${targetClassName}_resultRow').hover(function () {$(this).addClass('highlight');}, function () {$(this).removeClass('highlight');});


    //Click check di selezione
    $('.roundedOne').click(function(e){
        e.stopPropagation(); 
    });


    $('.${cardId}_${targetClassName}_selectable').change(function(e){
        ${cardId}_manageDeleteButton();
    });

    $('#${cardId}_${targetClassName}_list_zone table thead th span.${cardId}_toorder').click(function (){
       var toorder=$(this).attr("toorder");
       var direction=$(this).attr("direction");
       var actual=$('#${cardId}_${targetClassName}_form [name="toOrder"]').val();
       var actual_a = actual.split(",");
       var order_list=new Array();
       order_list=[toorder+" "+direction];
       for (var i = 0; i < actual_a.length; ++i){
            if(actual_a[i].trim()!=""){
                var ordcmd=actual_a[i].split(" ");
                if(toorder!=ordcmd[0]){
                        order_list.push(ordcmd[0]+" "+ordcmd[1]);
                }
            }
       }
       $('#${cardId}_${targetClassName}_form [name="toOrder"]').val(order_list.join());
       callEvent('${cardId}_${targetClassName}_find_refresh');
    });

    <#--Click bottone seleziona tutti-->
    $('#${cardId}_${targetClassName}_sel_all_button').click(function(){
              $('.${cardId}_${targetClassName}_selectable').trigger('click');
              return false;
    });

    <#--Click riga tabella che porta all'interfaccia di update viene inserito solo nel caso 
        di crud di tipo "Tutto in uno"/n -->
    <#if type_flag=='n'>		
    $('.${cardId}_${targetClassName}_resultRow').click(function(){
        ${cardId}_setSelected($(this));   
        <#if (direct_edit?boolean && !aby_edit_flag?boolean)>
        <#else>
            clearForm($('#${cardId}_view_object_form'));
            clearForm($('#${cardId}_view_ui_form'));
            addHiddenToForm($('#${cardId}_view_object_form'),'targetClass','${targetClass}');
            addHiddenToForm($('#${cardId}_view_object_form'),"idObj['id']",$(this).attr('pk_id'));
            try{
            <#if popup_gui?boolean>
                ${cardId}_view_update_dialog.show();
            <#else>    
                ${cardId}_slideToPage($('#${cardId}_list_slide'),$('#${cardId}_${targetClassName}_view_object_pane'), 'right');
            </#if>
            <#if direct_edit?boolean>
                    $('#${cardId}_${targetClassName}_view_ui_info_obj_action').hide();
                    $('#${cardId}_${targetClassName}_view_ui_get_obj_action').show();
                    $('#${cardId}_${targetClassName}_view_ui_update_object_action_content').html('<span class="${cardId}_event_message animated flash">${action.getText("crude.form.rquired")}</span>');
                    callEvent("${cardId}_${targetClassName}_view_ui_get_object_refresh");   
            <#else>
                $('#${cardId}_${targetClassName}_view_ui_info_obj_action').show();
                $('#${cardId}_${targetClassName}_view_ui_get_obj_action').hide();
                callEvent("${cardId}_${targetClassName}_view_ui_info_object_refresh"); 
            </#if>
            }catch(err){console.log(err)};
        </#if>
        return false;
    });
    </#if>

    //Gestione del click sulla riga nel caso esistano observer
    <#if crud_observers!=''>		
    $('.${cardId}_${targetClassName}_resultRow').click(function(){
        ${cardId}_setSelected($(this));
        <@events.notify_observer observers=crud_observers event_name='listrow_click'/>
        return false;
    });
    </#if>

    //Gestione del drag
    $('#${cardId}_${targetClassName} .draggable_cell').draggable({ 
        helper: function (evt) {
                    try{	
                        return ${cardId}_helperCellDrag(evt, this);
                    }catch(err){}
                },
        start: function(evt,ui){},
        stop : function(evt,ui){},	 
    });

    //Getione download
    $('#${cardId}_${targetClassName} .down_link span span').click(function(e){
        e.preventDefault(); 
        var $td=$(this).parent().parent();
        console.log($td);
        var fname=$td.attr("name");
        var id=$td.parent("tr").attr("pk_id");

        var targetClass='${targetClass}';
        var url = "${action.calcAction("download","crude",null)}?idObj['id']="+id+"&targetClass="+targetClass+"&fieldName="+fname+"&cardId=${cardId}";
    $('#${cardId}_download_file_action').attr('src',url);
        return false;
    });	


    //Controllo per la visualizzazione dei bottoni per la selezione e la rimozione dei record
    if($('.${cardId}_${targetClassName}').length>0){
            $('#${cardId}_${targetClassName}_sel_all_button').fadeIn('fast');
              $('#${cardId}_${targetClassName}_del_object_button').fadeIn('fast');
      } 

    <#if type_flag='l' && autoselect_flag='true'>
        if(${cardId}_row_selected==null){
            $('.${cardId}_${targetClassName}_resultRow:first').trigger("click");
        }else{
            var rowToClick=$('#${cardId}_${targetClassName}.${cardId}_gbtable tr[pk_id="'+${cardId}_row_selected.attr('pk_id')+'"]');
            if(rowToClick.length>0){
                rowToClick.trigger('click');
            }else{
                $('.${cardId}_${targetClassName}_resultRow:first').trigger("click");
            }
        }
    </#if>  
});

</script>
<#if aby_delete_flag='true' && action.checkRole(user.roles,rolesRemove)>
<#assign abyCheck='true'/>	
<#else>
<#assign abyCheck='false'/>	
</#if>
<form id="${cardId}_list_form"></form>
<@crude.resultList
	id="${cardId}_${targetClassName}" 
	abyCheck="${abyCheck}"
	className="${cardId}_gbtable ${cardId}_gbtable1" 
	iterable="searchResult" 
	resultMessage="${targetClassName}.result.message"
	prototype="${targetClass}"
	style="width:100%;clear:both">
</@crude.resultList>
