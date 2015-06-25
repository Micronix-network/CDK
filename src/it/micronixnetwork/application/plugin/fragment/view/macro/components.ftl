<#macro callBatch testo id batch message="" dir="" showError="false" height="50">
<form id="${cardId}_batch_${id}_form">
    <input type="hidden" name="batchName" value="${batch}">
    <input type="hidden" name="dir" value="${dir}">
    <input type="hidden" name="showError" value="${showError}">
</form>
  
<div style="border: 1px solid #eee;padding:5px">
    <span style="font-size:15px">${testo}</span>
    <button id="${cardId}_exe_batch_${id}" class="btn btn-small btn-primary" style="float:right">Esegui</button>
    <@gaf.div id="${cardId}_batch_${id}" 
              action="runbatch"
              namespace="fragment"
              loadImage="true" 
              formId="${cardId}_batch_${id}_form" 
              listen="${cardId}_batch_${id}_refresh" 
              startOnLoad="false" 
    style="height:${height}px;background-color: #eee;margin-top:4px;clear:both">
    </@gaf.div>
</div>

<div id="${cardId}_${id}_execute_dialog" class="generic_dialog md-modal" style="width:300px;">
    <h3>
    ?
    </h3>
    <div class="diag_message">${message}</div>
    <div class="form_title">
        <table style="width:100%">
            <tr>
                <td style="text-align:left;">
                    <button id="${cardId}_${id}_cancel_button" class="btn btn-small btn-primary image_btn icon-denied">
                        <span style="display:none">
                            Cancel
                        </span>
                    </button>
                </td>
                <td style="text-align:right;">
                    <button id="${cardId}_${id}_apply_button" class="btn btn-small btn-primary image_btn icon-cole-townsend-check">
                        <span style="display:none">
                            OK
                        </span>    
                    </button>
                </td>
            </tr>
        </table>
    </div>
</div>
  
<script type="text/javascript">
    $("#${cardId}_exe_batch_${id}").click(function(e){
      ${cardId}_${id}_execute_dialog.show();
    });
      
    var ${cardId}_${id}_execute_dialog = new (function() {
			
      function init() {
          $('#${cardId}_${id}_cancel_button').click(function(){
              hide();
          });
            
          $('#${cardId}_${id}_apply_button').click(function(){
              try{
                  hide();
                  $('#${cardId}_batch_${id}_content').empty();
                  callEvent("${cardId}_batch_${id}_refresh");
              }catch(error){
                  alert(error);
                  hide();
              }
          });
      };

      this.hide=hide;
      function hide() {
          $('.md-overlay').css('z-index','1000');
          $('.md-overlay').removeClass('md-show');
          $('#${cardId}_${id}_execute_dialog').removeClass('md-show');
      }

      this.show=show;
      function show() {
          $('.md-overlay').css('z-index','3000');
          $('.md-overlay').addClass('md-show');
          $('#${cardId}_${id}_execute_dialog').addClass('md-show');
      }
      init();	
  })(); 
      
</script>
</#macro>
