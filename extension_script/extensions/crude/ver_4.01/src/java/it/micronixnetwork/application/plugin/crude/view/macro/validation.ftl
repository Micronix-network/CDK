<#macro clientValidation formId="${uiid}" cardId="${cardId}">		

		$( "#${cardId}_${formId}_form .date_input").datepicker({
			changeMonth: true,
			changeYear: true,
			numberOfMonths: 1,
			onSelect: function( selectedDate ) {
			}
		});
		
		$("#${cardId}_${formId}_form .right_input_multiselect").twosidedmultiselect();

		
		$('#${cardId}_${formId}_form .input_currency').each(function(){
	  		value=formatCurrency($(this).val(),'.', ',',2,true);
	  		$(this).val(value);
	  	});
		
		$('#${cardId}_${formId}_form .input_perc').each(function(){
	  		value=formatCurrency($(this).val(),'.', ',',true);
	  		$(this).val(value);
	  	});
		
		$('#${cardId}_${formId}_form .input_currency').change(function(){
			numValue=unformatNumber( $(this).val(),'.', ',');
			value=formatCurrency(numValue,'.', ',',2,true);
	  		$(this).val(value);
	  	});
		
		$('#${cardId}_${formId}_form input,textarea').change(function(e) {
			${cardId}_data_modify=true;
		});
		
		$('#${cardId}_${formId}_form input,select').keydown(function(e) {
			var e = window.event || e;
			var keyUnicode = e.charCode || e.keyCode;
			if (e !== undefined) {
				switch (keyUnicode) {
					case 13: {// enter
						var fields = $(this).parents('form:eq(0),body').find('button,input,textarea,select');
				        var index = fields.index( this );
				        if ( index > -1 && ( index + 1 ) < fields.length ) {
				            fields.eq( index + 1 ).focus();
				        }
				        return false;
						break;
					} 		
				}
			}
		});

		$('#${cardId}_${formId}_form .input_currency').keyup(function(e) {
			if(${cardId}_checkKey(e)){
				${cardId}_inputCurrencyFormat($(this),e);
			}
		});	
		
		$('#${cardId}_${formId}_form .input_perc').keyup(function(e) {
			if(${cardId}_checkKey(e)){
				inputPercFormat($(this),e);
			}
		});	
		
		$('#${cardId}_${formId}_form .input_integer').keyup(function(e) {
			if(${cardId}_checkKey(e)){
				${cardId}_inputIntegerFormat($(this),e);
			}
		});
		
		$('#${cardId}_${formId}_form .input_real').keyup(function(e) {
			if(${cardId}_checkKey(e)){
				${cardId}_inputRealFormat($(this),e);
			}
		});
		
		$('#${cardId}_${formId}_form .date_input').keyup(function(e) {
			if(${cardId}_checkKey(e)){
				${cardId}_inputDateFormat($(this),e);
			}
		});
		
		$('#${cardId}_${formId}_form .input_currency, .input_perc, .input_integer, .input_real').keydown(function(e) {
			var e = window.event || e;
			var keyUnicode = e.charCode || e.keyCode;
			if (e !== undefined) {
				switch (keyUnicode) {
					case 78: { return false;break;} // N (Opera 9.63+ maps the "." from the number key section to the "N" key too!) (See: http://unixpapa.com/js/key.html search for ". Del")
					case 110: { return false;break;} // . number block (Opera 9.63+ maps the "." from the number block to the "N" key (78) !!!)
					case 190: { return false;break;} //.
					case 188: {
						if($(this).val().indexOf(',')!=-1){
						return false;
						};
						break;
					} // , il separatore decimale
					default: {
					}
						
				}
			}
		});	
		
</#macro>	



