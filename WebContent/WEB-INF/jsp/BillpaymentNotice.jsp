<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>  
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	// debugger;
	
	$().ready(function() {
 
		var grid = jQuery("#grid");
		
		//var paramTest = "?idtest="+ $("#p_appno").val() ;
		
		var getColumnIndexByName = function (grid, columnName) {
            var cm = grid.jqGrid('getGridParam', 'colModel'), i, l = cm.length;
            for (i = 0; i < l; i++) {
                if (cm[i].name === columnName) {
                    return i; // return the index
                }
            }
            return -1;
        };
        
        var getUrlParameter = function getUrlParameter(sParam) {
            var sPageURL = decodeURIComponent(window.location.search.substring(1)),
                sURLVariables = sPageURL.split('&'),
                sParameterName,
                i;

            for (i = 0; i < sURLVariables.length; i++) {
                sParameterName = sURLVariables[i].split('=');

                if (sParameterName[0] === sParam) {
                    return sParameterName[1] === undefined ? true : sParameterName[1];
                }
            }
        };
        
        var idtest = getUrlParameter('idtest');
        
		var mygrid = grid.jqGrid({
			
		    datatype : "json",
			url : "BillpaymentNotice/list.html?idtest="+idtest,
			mtype : "GET",
		    colNames:["<spring:message code='kkDocument.lbl1'/>", // Primary Key hidden
		              //"<spring:message code='kkDocument.lbl1'/>", // Doc Type (hidden)
		              "<spring:message code='kkDocument.lbl1'/>",
		              //"<spring:message code='kkDocument.lbl13'/>",
		              //"<spring:message code='kkDocument.lbl2'/>",
		              "<spring:message code='kkDocument.lbl14'/>",
		              "<spring:message code='kkDocument.lbl3'/>",
		              "<spring:message code='kkDocument.lbl4'/>",
		              "<spring:message code='kkDocument.lbl5'/>",
		              "<spring:message code='kkDocument.lbl6'/>",
		              "<spring:message code='kkDocument.lbl7'/>",
		              "<spring:message code='kkDocument.lbl8'/>",
		              "<spring:message code='kkDocument.lbl9'/>",
		              "<spring:message code='kkDocument.lbl10'/>",
		              "<spring:message code='kkDocument.lbl11'/>",
		              "<spring:message code='kkDocument.lbl12'/>",
		              "<spring:message code='kkDocument.lbl15'/>" ],
		    colModel :[ 
		      {name:'id',   index:'id',   width:5, sortable: true, sorttype:'int', hidden : true }, 
		      //{name:'docType',  		index:'docType',			width:10,	sortable: true,	editable : false ,hidden : true },
		      {name:'appNo',  			index:'appNo',				width:30,	sortable: true,	editable : false },
		      //{name:'status',			index:'status',				width:25,	sortable: true, editable : false ,stype: 'select', searchoptions:{ dataInit: function(elem) {$(elem).height(20) } ,sopt:['eq'], value: ":All;NEW:NEW;In Progress:In Progress" } },//, search:true, stype:'text',  searchoptions: { defaultValue: "NEW" }},	
		      //{name:'productType',  	index:'productType',		width:10,	sortable: true,	editable : false ,stype: 'select', searchoptions:{ dataInit: function(elem) {$(elem).height(20) } ,sopt:['eq'], value: ":All;OL:OL;NL:NL;UL:UL" }},//, searchoptions: { defaultValue: "OL" }},
		      {name:'kkRefNo',  		index:'kkRefNo',			width:30,	sortable: true,	editable : false },
		      {name:'branchStr',  		index:'branchStr',			width:30,	sortable: true,	editable : false },
		      {name:'createdDateStr',  	index:'createdDateStr',		width:40,	sortable: true,	editable : false },
		      {name:'customerFirstName',index:'customerFirstName',	width:30,	sortable: true,	editable : false },
		      {name:'customerLastname', index:'customerLastname',	width:30,	sortable: true,	editable : false },
		      {name:'customerIdCard',	index:'customerIdCard',		width:35,	sortable: true, editable : false },
		      {name:'sumAssured',		index:'sumAssured',			width:30,	sortable: true, editable : false ,align: 'right',formatter:'number',formatoptions:{thousandsSeparator: ',' ,decimalPlaces: 0, defaultValue: '0'}}, 
		      {name:'premium',			index:'premium',			width:30,	sortable: true, editable : false ,align: 'right',formatter:'number',formatoptions:{thousandsSeparator: ',' ,decimalPlaces: 0, defaultValue: '0'}},
		      {name:'productName',		index:'productName',		width:35,	sortable: true, editable : false },
		      {name:'agentCode',		index:'agentCode',			width:20,	sortable: true, editable : false },
		      {name:'agentName',		index:'agentName',			width:65,	sortable: true, editable : false },
		      {name:'email',		index:'email',			width:65,	sortable: true, editable : false }
		      //{name:'appCuStatus',		index:'appCuStatus',			width:65,	sortable: true, editable : false },	
		      //{name:'status',		index:'status',			width:65,	sortable: true, editable : false }
		    ],
		    pager: '#pager',
		    rowNum: 15,
		    height : 340,
			width : 1100,
			autoencode: false,
			//sortname: 'id',
			autowidth: false,
			rownumbers: true,
		   // sortorder: "desc",
			gridview : true,
		    viewrecords: true,
		    loadonce: true,
		    loadui: 'disable',
		    caption: 'Send Billpayment Notification',
		    //editurl: "garageMaster/add.html",
		    jsonReader : {
				root : "rows",
				page : "page",
			    records : function(result) {
			    	//Total number of records
			      	return result.records;
			    },
				total : function(result) {
			      	//Total number of pages
			      	return Math.ceil(result.records / result.max);
			    },
				repeatitems : false,
				cell : "cell",
				id : "id"
			},
		    loadComplete : function(){
		    	
		    },
			gridComplete : function() {
				$(".ui-pg-input").numeric();
				if ($(".ui-pg-input").val() == 0) {
					$(".ui-pg-input").attr("disabled", "disabled");
				} else {
					$(".ui-pg-input").removeAttr("disabled", "disabled");
				}
			},
			onSelectRow : function(id) {
				$("#" + id).removeClass("selected");
			}
		  }); 
	 	
		$.extend($.jgrid.edit, {
			addCaption : '<spring:message code="kkDocument.div.lbl2" />',
			editCaption : '<spring:message code="kkDocument.div.lbl1" />',
		    msg : '<spring:message code="confirm.save" />',
		    bSubmit : '<spring:message code="btn.lbl.save" />',
		    bCancel : '<spring:message code="btn.lbl.cancel" />',
			closeOnEscape : true,
			resize : false,
			closeAfterEdit : true,
			closeAfterAdd : true,
			width : 400,
			height : "auto",
			top : (($(window).height() - 150) / 2),
			left : (($(window).width() - 300) / 2)
		});
		$.extend($.jgrid.del, {
			caption : '<spring:message code="kkDocument.div.lbl3" />',
		    msg : '<spring:message code="confirm.delete" />',
		    bSubmit : '<spring:message code="btn.lbl.del" />',
		    bCancel : '<spring:message code="btn.lbl.cancel" />',
			closeOnEscape : true,
			resize : false,
			width : 400,
			height : "auto",
			top : (($(window).height() - 150) / 2),
			left : (($(window).width() - 300) / 2)
		}); 
		
		grid.jqGrid('navGrid','#pager',
			{edit:false,add:false,del:false,search:false,refresh:false,
			 edittext:'Edit',addtext:'Add',deltext:'Delete'
			}			
		);
		grid.jqGrid('navButtonAdd',"#pager",{caption:"Toggle",title:"Toggle Search Toolbar", buttonicon :'ui-icon-pin-s',
			onClickButton:function(){
				mygrid[0].toggleToolbar();
			} 
		});
		/*
		grid.jqGrid('navButtonAdd',"#pager",{caption:"Clear",title:"Clear Search",buttonicon :'ui-icon-refresh',
			onClickButton:function(){
				mygrid[0].clearToolbar();
			} 
		});
		*/
		grid.jqGrid('filterToolbar', { stringResult: true, searchOnEnter: true, defaultSearch: 'cn' });
		  
		grid.jqGrid('navButtonAdd',"#pager",{
			caption:"Send BillNotice",
			title:"Send Email to Agency this policy able to print Billpayment.",
			position : "first",
			buttonicon :'ui-icon-folder-open',
			onClickButton:function(){
				 
				var row = grid.jqGrid('getGridParam', 'selrow');
				if (row != null) {
					var _id         = grid.jqGrid ('getCell', row, 'id');
					var _appNo      = grid.jqGrid ('getCell', row, 'appNo');
					
					var _InsureName = grid.jqGrid ('getCell', row, 'customerFirstName') + " " + grid.jqGrid ('getCell', row, 'customerLastname');	
					var _premium    = grid.jqGrid ('getCell', row, 'premium');	
					var _productName = grid.jqGrid ('getCell', row, 'productName');			
					var _email = grid.jqGrid ('getCell', row, 'email');					
					
					var sendData = { 
						id:_id
	                }
					
					$("#sendMailTo").html(_email);
					$("#appnoDisplay").html(_appNo);

					$("#updateKey").val(_appNo);
					
					$("#inputPolicyDiv").show();
					$("#kkDocumentDiv").hide();
					$("#searchBillDiv").hide();
					
				}else{
					$("#msgDialog").text('Please, select row');
					$("#msgDialog").dialog('open');					
				}
				 
			} 
		});

		$("#dialog-form").dialog({
            modal: true,
            autoOpen: false,
            height: 150,
            width: 350,
            buttons: {
                "ตกลง": function() {
                    location.reload();
                }
            },
		});
		
		$("#dialog-alert").dialog({
            modal: true,
            autoOpen: false,
            height: 150,
            width: 350,
            buttons: {
                "ตกลง": function() {
                    //location.reload();
                	$("#dialog-alert").dialog("close");
                }
            },
		});
		 
		// Remove Separator
		$("#pager td > span.ui-separator").parent().remove();
		
		$("#btnSearchBill").button().click(function() {
			var idtestParam = $("#idtest").val();
			window.location.replace("BillpaymentNotice.html?idtest="+idtestParam);
		});	
		
		$("#inputPolicyDiv").accordion({
			autoHeight: false,
			navigation: true
		});
		
		$("#btnSendMail").button().click(function() {
			var policyNumberTxt = $("#policyNumberTxt").val();
			if( policyNumberTxt == "" ){
				$("#alertMessage").html('Policy Number ห้ามเป็นค่าว่าง');
				$("#dialog-alert").dialog("open");
			    e.preventDefault();
			}
			
			var row = grid.jqGrid('getGridParam', 'selrow');
			if (row != null) {
				var appno = $.trim($("input#updateKey").val());
				var policyno = $.trim($("input#policyNumberTxt").val());
				var _agentName = grid.jqGrid ('getCell', row, 'agentName');	
				var _branchStr = grid.jqGrid ('getCell', row, 'branchStr');		
				var _InsureName = grid.jqGrid ('getCell', row, 'customerFirstName') + " " + grid.jqGrid ('getCell', row, 'customerLastname');		
				var _email = grid.jqGrid ('getCell', row, 'email');				
				
				//alert(_agentName + "/" + _branchStr + "/" + _InsureName)
				
				var obj = { appNo : appno, policyNo : policyno, agentName : _agentName,
							branchStr : _branchStr, customerFirstName : _InsureName, staffEmail : _email
						  };
				
				$.ajax({
			    	url: "BillpaymentNotice/sendBillpaymentNotice.html",
			        type: "POST",
			        data: JSON.stringify(obj),
			        dataType: "json",
			        contentType: "application/json; charset=utf-8",
			        beforeSend: function(data) {
			        	//var result = verifyPassword(newPassword, confirmPassword);
			        	//alert(result);
			        	//return result;
			        },
			        async: false,
			        success: function(response) {
			        	 
			        	if( response.success == true ){
			        		//$("#resetPasswordDialog").dialog("destroy");
							//$("#msgDialog").text('แจ้งผลการพิจรณาของหมายเลข AppNo '+ response.message +' เรียบร้อยแล้ว'  );
							//$("#msgDialog").dialog("open");
							
							$("#responseMessage").html('แจ้งผลการพิจรณาของหมายเลข AppNo '+ response.message +' เรียบร้อยแล้ว');
							$("#dialog-form").dialog("open");
						    e.preventDefault();
						    
			    			//window.location.replace("BillpaymentNotice.html?idtest="+response.message);
			        	}
			        	//alert(response.message); 
			        	//alert(response.success); 
			        	
						//$("#resetPasswordDialog").dialog("destroy");
						//$("#msgDialog").text('<spring:message code="success.resetPassword" /> ${appNo}'  );
						//$("#msgDialog").dialog("open");
						
			        }
				});
			
			}
			
		});
	});  
</script>
 
<style type="text/css"> 
    /* fix the size of the pager */
	input.ui-pg-input { width: auto; }
	 
	#kkDocumentDiv{
		width:750px;
		margin-left: 35px;
		overflow-x: auto;
	}
	/*jqgrid search toolbar  font size*/
	.ui-jqgrid  .ui-search-toolbar .ui-search-table {
	    font-size: 0.8em;
	}
	.ui-jqgrid .ui-search-toolbar input,
	.ui-jqgrid .ui-search-toolbar select {
	    font-size: 0.8em;
	}
	
	.ui-jqgrid tr.jqgrow td {
		white-space: normal !important;
		height:auto;
		vertical-align:text-top;
		padding-top:2px;
	}
	.ui-jqgrid .ui-jqgrid-htable th div {
		height:auto;
		overflow:hidden;
		padding-right:4px;
		padding-top:2px;
		position:relative;
		vertical-align:text-top;
		white-space:normal !important;
	}
	.ui-jqgrid .ui-jqgrid-bdiv {
	  position: relative; 
	  margin: 0em; 
	  padding:0; 
	  /*overflow: auto;*/ 
	  overflow-x:hidden; 
	  overflow-y:auto;
	  text-align:left;
	}
	#map{
		width:600px;
		height:400px;
	}
	.docStatusCombobox{
		width:110px;
	}
	.docTextBox{
		width:180px;
	}  
	#custDetail,#searchBillDiv{
		margin-left:20px;
		width:750px;
		/*border:1px solid #FF0000;*/
	}
	  
	  
</style>
</head>
<body>
	
	<div id="searchBillDiv">
		<input type="text" id="idtest" name="idtest" value="" placeholder="Input App Number"/>
		<input type="hidden" id="max" name="max" value="10"/>
		<input type="hidden" id="page" name="page" value="1"/>
		<input type="button" id="btnSearchBill" value="Search">
	</div>
	
	<div id="kkDocumentDiv"> 
		<table id="grid"></table> 
		<div id="pager" style="text-align: center;"></div>
	</div> 
	 
	<div id="dialog-alert" title="Message Alert">
	    <p id="alertMessage" class="validateTips"></p>
	</div>  
	<div id="dialog-form" title="Message Alert">
	    <p id="responseMessage" class="validateTips"></p>
	</div>  
	
 <div id="inputPolicyDiv" style="display:none; margin-left: 100px; margin-top: -200px; width: 420px; height: 300px">
	<h3><a href="#">Send Email Notification</a></h3>
		<div id="criteriaDialog">
			<fieldset>
				<div id="resetBox">
					<div id="resetMessage"></div>
					<div>
						<input type="hidden" id="updateKey" name="updateKey" value=""/>
						<table>
							<tr id="FormError" style="display: none;">
								<td class="ui-state-error" colspan="2"></td>
							</tr>
							<tr><td><B>App Number : </B></td><td><div id="appnoDisplay"></div></td></tr>
							<tr><td><B>Send To : </B></td><td><div id="sendMailTo"></div></td></tr>
							<tr>
								<td><B>Policy Number : (*)</B></td>
								<td><input type="text" id="policyNumberTxt" size="8" maxlength="8" required="required"/></td>
							</tr>
							<tr>
								<td colspan="2" align="center"><button id="btnSendMail" >Submit</button></td>
							</tr>
						</table>
					 </div>
				 </div>
			</fieldset>
		</div>
	</div>
	
</body>
</html>