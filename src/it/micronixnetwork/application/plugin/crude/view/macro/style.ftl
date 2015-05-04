<#macro main cardId="${cardId}">		


.${cardId}_child_form{
	position:absolute;
	background-color:#fffffe;
	box-shadow: 0px 2px 3px  #333333;
	-webkit-box-shadow: 0px 2px 3px  #333333;
	-moz-box-shadow: 0px 2px 3px  #333333;
	top: -100%;
	left:50%;
	width: 50%;
	max-width: 630px;
	min-width: 500px;
	height: auto;
	z-index: 2000;
}

.${cardId}_topDownDialog{
	position:absolute;
	background-color:#fffffe;
	box-shadow: 0px 2px 3px  #333333;
	-webkit-box-shadow: 0px 2px 3px  #333333;
	-moz-box-shadow: 0px 2px 3px  #333333;
	top: -100%;
	left:50%;
	width: 50%;
	max-width: 630px;
	height: auto;
	z-index: 2000;
}

.${cardId}_child_form h3,.${cardId}_topDownDialog h3{
	-moz-box-shadow: inset 0 0 3px #bbb;
	-webkit-box-shadow: inset 0 0 3px #bbb;
	box-shadow: inner 0 0 3px #bbb;
	margin: 0;
	padding: 5px;
	text-align: center;
	font-size: 18px;
	font-weight: 300;
	opacity: 0.8;
	background: #eee;
	border-radius: 3px 3px 0 0;
}

.${cardId}_gbtable.dataTable input[type="checkbox"]{
	font-size:12px;
}

.${cardId}_topDownDialog .diag_message{
	text-align:center;
	font-size: 16px;
	font-weight: 300;
	line-height: 30px;
	color:#aaa;
}

.${cardId}_cell_drag{
background-color:#0099ff;
-moz-box-shadow: 0px 5px 40px #333;
-webkit-box-shadow: 0px 5px 40px #333;
box-shadow: 0px 5px 40px #333;
-moz-border-radius: 4px;
-webkit-border-radius: 4px;
border-radius: 4px;
padding:4px;
}

.${cardId}_page {
    position: absolute;
    left: 0;
    width: 100%;
    -webkit-transition: all 0.4s ease-in-out;
	-o-transition: all 0.4s ease-in-out;
	-moz-transition: all 0.4s ease-in-out;
	transition: all 0.4s ease-in-out;
}
 
.${cardId}_page.left {
    -webkit-transform: translate3d(-100%, 0, 0);
    -o-transform: translate3d(-100%, 0, 0);
    -moz-transform: translate3d(-100%, 0, 0);
    transform: translate3d(-100%, 0, 0);
    -ms-transform:translate(-100%, 0);
}
 
.${cardId}_page.center {
}
 
.${cardId}_page.right {
    -webkit-transform: translate3d(100%, 0, 0);
    -o-transform: translate3d(100%, 0, 0);
    -moz-transform: translate3d(100%, 0, 0);
    transform: translate3d(100%, 0, 0);
    -ms-transform: translate(100%, 0);
}

.${cardId}_page.origin{
	-webkit-transform: translate3d(0, 0, 0);
	-o-transform: translate3d(0, 0, 0);
	-moz-transform: translate3d(0, 0, 0);
    transform: translate3d(0, 0, 0);
    -ms-transform: translate(0, 0);
}
 
.${cardId}_page.transition {
    -webkit-transition-duration: .25s;
    -o-transition-duration: .25s;
    -moz-transition-duration: .25s;
    transition-duration: .25s;
}

.${cardId}_filter_console{
        color : #666;
        height:35px;
}

.${cardId}_filter_box{
	margin-left: 3px;
}

.${cardId}_filter_box label{
	font-size:11px;
	font-weight:bold;
	display: block;
	text-transform: capitalize;
}

.${cardId}_filter_console input{
	border: none;
	border-top: 1px solid #ddd;
	border-left: 1px solid #ddd;
	border-bottom: 1px solid #ddd;
	border-right: 1px solid #ddd;
	-moz-border-radius: 0px;
  	-webkit-border-radius: 0px;
 	border-radius: 0px;
    padding: 3px;
    background: white;
    height:20px;
}

.${cardId}_filter_console select{
    height:20px;
}

.${cardId}_pagination {
	background: #CFE5F7;
	border: none;
	margin-top: 0px;
	text-align: center;
	color: #333;
	z-index: 1000;
}

.${cardId}_pagination table {
	width: 100%;
	margin: 0px;
	border: 0px;
}

.${cardId}_pagination table tr{
	border-bottom:1px solid #FFF;
}

.${cardId}_pagination .${cardId}_pagination_msg{
	text-align: left;
}

.${cardId}_pagination table td {
	font-size: 12px;
	height:22px;
}

.${cardId}_pagination_msg {
	width: 150px;
}

.${cardId}_pagination_arrow {
	width: 150px;
	text-align: right;
}

.${cardId}_pagination button{
	height:18px;
	line-height:18px;
}

.${cardId}_pagination .succ span,.${cardId}_pagination .prec span{
display:none;
}


.${cardId}_pagination .succ:before{
	font-family: 'gaf';
	font-size: 12px;
	content: "\e037";
}

.${cardId}_pagination .prec:before{
	font-family: 'gaf';
	font-size: 12px;
	content: "\e036";
}

.${cardId}_pagination select{
	background-color:white;
	height:20px;
	padding-top :2px;
	padding-bottom :2px;
}

.${cardId}_form_title {
	border: none;
	margin-top: 0px;
	margin-bottom: 5px;
	margin-left: 0px;
	margin-right: 0px;
	-webkit-margin-before: 0px;
	-webkit-margin-after: 5px;
	-webkit-margin-start: 0px;
	-webkit-margin-end: 0px;
	text-align: center;
	font-size: 18px;
	background-color: rgba(255, 255, 255, 0.01);
	color: #111;
	padding-left: 5px;
	padding-right: 5px;
	padding-bottom: 2px;
	padding-top: 2px;
	border-bottom: 1px solid rgba(0, 0, 255, 0.05);
	-moz-border-radius: 4px;
	-webkit-border-radius: 4px;
	border-radius: 4px;
	min-height:35px;
	line-height: 26px;
}

.${cardId}_order-pin{
	margin-left:3px;
}

.${cardId}_toorder{
	cursor: pointer;
	font-family:gaf;
}

.${cardId}_gbtable {
	font-size: 11px;
	font-weight: 400;
	color: #444;
}

.${cardId}_gbtable thead th {
	text-transform: capitalize;
	padding: 2px;
	border-bottom: 1px solid #0099ff;
	height:25px;
}

.${cardId}_gbtable  tbody td {
	height: 25px;
	overflow-x:hidden;
	padding-left:2px;
	padding-right:2px;
}

.${cardId}_gbtable tbody tr.highlight{
	background-color: rgba(56,121,217,.5);
	color:white;
}

.${cardId}_gbtable tbody tr.selected_row{
	background-color: #3879D9;
	color:white;
}

.${cardId}_event_message{
	font-size:15px;
	color:#0099ff;
	line-height:35px;
}

.${cardId}_event_error{
	font-size:15px;
	color:#ff1000;
	line-height:35px;
}

.${cardId}_children_table{
	margin-top: 10px;
	margin-left: auto;
	margin-right: auto;
	margin-bottom: 10px;
	width: 95%;
}

.${cardId}_children_table td {
	border-bottom: 1px solid #0099ff;
	padding-top: 3px;
	padding-bottom: 3px;
	height:29px;
}

.${cardId}_children_table th{
   padding: 4px;
   font-size: 14px;
   font-weight: 300;
   border-bottom: 1px solid;
   color: #333;
   text-align: left;
}

.${cardId}_cbp-mc-form {
    position: relative;
    margin-left:auto;
    margin-right:auto;
    width:100%;
    padding:5px;
}

.${cardId}_cbp-mc-form:before, 
.${cardId}_cbp-mc-form:after { 
    content: " "; display: table; 
}
 
.${cardId}_cbp-mc-form:after { 
    clear: both; 
}
 
.${cardId}_cbp-mc-column {
    float: left;
    padding 5px;
}

.${cardId}_cbp-mc-form p{
    margin-bottom:0;
    margin-top:7px;
    height:25px;
}

.${cardId}_cbp-mc-form input:focus,
.${cardId}_cbp-mc-form select:focus,
.${cardId}_cbp-mc-form textarea:focus,
.${cardId}_cbp-mc-form label:active + input,
.${cardId}_cbp-mc-form label:active + textarea {
    outline: none;
    border: 1px solid #10689a;
}
 
.${cardId}_cbp-mc-form select:focus {
    outline: none;
}

.${cardId}_crud_field{
	width:100%;
}

.${cardId}_data_label {
	float: left;
	width: 150px;
	height : 100%;
	display: block;
	text-align: right;
	margin-right: 10px;
	padding: 4px;
	font-size: 12px;
	border: none;
	background-color: #eee;
	color: #333;
	z-index: 1000;
	-moz-border-radius: 10px 0 0 10px;
	-webkit-border-radius: 10px 0 0 10px;
	border-radius: 10px 0 0 10px;
	
}

.${cardId}_full_label {
	float: none;
	width: auto;
	margin-right: 0px;
	margin-top: 5px;
	text-align: center;
	-moz-border-radius: 10px 10px 0  0;
	-webkit-border-radius: 10px 10px 0  0;
	border-radius: 10px 10px 0  0;
}

.${cardId}_right_input_area {
	width: 100%;
	height: 100px;
	padding: 2px;
	background-color: white;
	border: 1px solid #09f;
	resize:none
}

.${cardId}_right_input_area[readonly="readonly"], .${cardId}_right_input_area[readonly],
.${cardId}_right_input_area[readonly="readonly"]:focus, .${cardId}_right_input_area[readonly]:focus {
	border: 1px solid #ddd;
}


.${cardId}_right_input_row {
	margin: 0;
	border: 1px solid #09f ;
	padding: 4px;
}

input[disabled].${cardId}_right_input_row{
	border: none ;
	border-bottom: 1px solid #ddd ;
}

.${cardId}_right_view_row {
margin: 0;
border:none;
border-bottom: 1px solid #eee;
padding: 4px;
display : inline-block;
min-height:23px;
}

.${cardId}_right_input_select {
	-webkit-appearance: none;
	border: 1px solid #09f ;
	margin: 0px;
	height: 23px;
}

#${cardId}_overlay_search_ui {
	position: absolute;
	width: 100%;
	height: 100%;
	visibility: hidden;
	top: 0;
	left: 0;
	z-index: 1000;
	opacity: 0;
	background: rgba(0,0,0,0.2);
	transition: all 0.3s;
}

#${cardId}_overlay_view_ui {
	position: absolute;
	width: 100%;
	height: 100%;
	visibility: hidden;
	top: 0;
	left: 0;
	z-index: 1000;
	opacity: 0;
	background: rgba(0,0,0,0.2);
	transition: all 0.3s;
}

#${cardId}_overlay_new_ui {
	position: absolute;
	width: 100%;
	height: 100%;
	visibility: hidden;
	top: 0;
	left: 0;
	z-index: 1000;
	opacity: 0;
	background: rgba(0,0,0,0.2);
	transition: all 0.3s;
}

.${cardId}_search_btn{
	speak: none;
	font-style: normal;
	font-weight: normal;
	font-variant: normal;
	text-transform: none;
	line-height: 1.4;
	text-align: center;
}

.${cardId}_search_btn span{
display:none;
}


.${cardId}_search_btn:before{
	font-family: 'gaf';
	font-size: 15px;
	content: "\e64b";
}


.${cardId}_reset_btn{
	speak: none;
	font-style: normal;
	font-weight: normal;
	font-variant: normal;
	text-transform: none;
	line-height: 1.4;
	text-align: center;
}

.${cardId}_reset_btn span{
display:none;
}

.${cardId}_reset_btn:before{
	font-family: 'gaf';
	font-size: 15px;
	content: "\e648";
}

.${cardId}_delete_btn{
	speak: none;
	font-style: normal;
	font-weight: normal;
	font-variant: normal;
	text-transform: none;
	line-height: 1.4;
	text-align: center;
}

.${cardId}_delete_btn span{
display:none;
}

.${cardId}_delete_btn:before{
	font-family: 'gaf';
	font-size: 15px;
	content: "\e01a";
}

.${cardId}_add_btn{
	speak: none;
	font-style: normal;
	font-weight: normal;
	font-variant: normal;
	text-transform: none;
	line-height: 1.4;
	text-align: center;
}

.${cardId}_add_btn span{
display:none;
}

.${cardId}_add_btn:before{
	font-family: 'gaf';
	font-size: 15px;
	content: "\e61b";
}

.${cardId}_selall_btn{
	speak: none;
	font-style: normal;
	font-weight: normal;
	font-variant: normal;
	text-transform: none;
	line-height: 1.4;
	text-align: center;
	cursor:pointer;
}

.${cardId}_selall_btn span{
display:none;
}

.${cardId}_selall_btn:before{
	font-family: 'gaf';
	font-size: 15px;
	content: "\e61e";
}


.${cardId}_list_zone{
border-bottom: 1px solid #0099ff;

}

.${cardId}_child_form h4 {
-moz-box-shadow: inset 0 0 3px #bbb;
-webkit-box-shadow: inset 0 0 3px #bbb;
box-shadow: inner 0 0 3px #bbb;
margin: 0;
padding: 5px;
text-align: center;
font-size: 14px;
font-weight: 300;
color: #888;
background: #eee;
border-radius: 3px 3px 0 0;
}


</#macro>	



