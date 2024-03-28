<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>  
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	// debugger;
	
	$().ready(function() {

		var grid = jQuery("#grid");
		
		var getColumnIndexByName = function (grid, columnName) {
            var cm = grid.jqGrid('getGridParam', 'colModel'), i, l = cm.length;
            for (i = 0; i < l; i++) {
                if (cm[i].name === columnName) {
                    return i; // return the index
                }
            }
            return -1;
        };
        
		var mygrid = grid.jqGrid({
			
		    datatype : "json",
			url : "KK_Document/listHistory.html",
			mtype : "GET",
		    colNames:["<spring:message code='kkDocument.lbl1'/>", // Primary Key hidden
		              "<spring:message code='kkDocument.lbl1'/>", // Doc Type (hidden)
		              "<spring:message code='kkDocument.lbl1'/>",
		              "<spring:message code='kkDocument.lbl13'/>",
		              "<spring:message code='kkDocument.lbl2'/>",
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
		              "<spring:message code='kkDocument.lbl13'/>",
		              "<spring:message code='kkDocument.lbl13'/>",
		              "<spring:message code='kkDocument.lbl13'/>",
		              "<spring:message code='kkDocument.lbl13'/>" ],
		    colModel :[ 
		      {name:'id',   index:'id',   width:5, sortable: true, sorttype:'int', hidden : true }, 
		      {name:'docType',  		index:'docType',			width:10,	sortable: true,	editable : false ,hidden : true },
		      {name:'appNo',  			index:'appNo',				width:30,	sortable: true,	editable : false },
		      {name:'status',			index:'status',				width:25,	sortable: true, editable : false ,stype: 'select', searchoptions:{ dataInit: function(elem) {$(elem).height(20) } ,sopt:['eq'], value: ":All;NEW:NEW;Completed:Completed;Follow Up:Follow Up;In Progress:In Progress;Waiting:Waiting;Rejected:Rejected;Rejected-COF:Rejected-COF;Rejected-SA_Prem:Rejected-SA_Prem" } },//, search:true, stype:'text',  searchoptions: { defaultValue: "NEW" }},	
		      {name:'productType',  	index:'productType',		width:10,	sortable: true,	editable : false ,stype: 'select', searchoptions:{ dataInit: function(elem) {$(elem).height(20) } ,sopt:['eq'], value: ":All;OL:OL;NL:NL;UL:UL" }},//, searchoptions: { defaultValue: "OL" }},
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
		      {name:'location',			index:'location',			width:5,	sortable: true,	editable : false, hidden : true },	
		      {name:'sisId',			index:'sisId',				width:5,	sortable: true,	editable : false, hidden : true },	
		      {name:'fileServer',		index:'fileServer',			width:5,	sortable: true,	editable : false, hidden : true },	
		      {name:'staffEmail',		index:'staffEmail',			width:5,	sortable: true,	editable : false, hidden : true }
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
		    caption: 'Documents History',
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
		grid.jqGrid('navButtonAdd',"#pager",{caption:"Clear",title:"Clear Search",buttonicon :'ui-icon-refresh',
			onClickButton:function(){
				mygrid[0].clearToolbar();
			} 
		}); 
		grid.jqGrid('filterToolbar', { stringResult: true, searchOnEnter: true, defaultSearch: 'cn' });
		  
		grid.jqGrid('navButtonAdd',"#pager",{
			caption:"View History",
			title:"View History Document",
			position : "first",
			buttonicon :'ui-icon-folder-open',
			onClickButton:function(){
				 
				var row = grid.jqGrid('getGridParam', 'selrow');
				if (row != null) {
					var _id         = grid.jqGrid ('getCell', row, 'id');
					var _docType    = grid.jqGrid ('getCell', row, 'id');
					var _appNo      = grid.jqGrid ('getCell', row, 'appNo');
					var _location   = grid.jqGrid ('getCell', row, 'location');
					var _fileServer = grid.jqGrid ('getCell', row, 'fileServer');						
					
					var sendData = { 
						id:_id
	                }
					/*
					var getDocCheckList = $.ajax({
				    	url: "KK_Document/getDocCheckList.html",
				        type: "POST",
				        data: JSON.stringify(sendData),
				        async:false, 
				        dataType: "json",
				        contentType: "application/json; charset=UTF-8",
				        beforeSend: function(xhr) {
				        	xhr.setRequestHeader('Accept', "text/html; charset=utf-8");
				        },
				        success: function(response) {
				        	
				        }
					}).responseText;
					$("#docCheckListDiv").html(getDocCheckList);
				
					var returnSomething = $.ajax({
				    	url: "KK_Document/verifyDocument.html",
				        type: "POST",
				        data: JSON.stringify(sendData),
				        async:false, 
				        dataType: "json",
				        contentType: "application/json; charset=utf-8",
				        beforeSend: function(data) {
				        	
				        },
				        success: function(response) {
				        	
				        }
					}).responseText;
					*/

					/*
					if( ! (returnSomething.indexOf("success") > -1)){
						//alert("==>"+returnSomething);
						
						$("#responseMessage").html(returnSomething);
						$("#dialog-form").dialog("open");
					    e.preventDefault();
					    
					}else{
					*/
					var docParam = { appNo:_appNo };
					var returnAllDoc = $.ajax({
				    	url: "KK_Document/getAllDocument.html",
				        type: "POST",
				        data: JSON.stringify(docParam),
				        async:false, 
				        dataType: "json",
				        contentType: "application/json; charset=utf-8",
				        beforeSend: function(data) {
				        	
				        },
				        success: function(response) {
				        	
				        }
					}).responseText;
					
						var docServer = _fileServer;
						var temp = new Array();
						temp = returnAllDoc.split(",");
						
						$("#verifyDocumentDiv").show();
						var showAllPdfHtml = "";
						for(i = 0; i<temp.length; i++){
							
							showAllPdfHtml += "<iframe id='fred"+(i+1)+"' style='border:1px solid #666CCC' title='PDF in an i-Frame' src='"+docServer + temp[i]+"' frameborder='1' scrolling='auto' height='500px' width='95%'' ></iframe>";
							
							//$("#fred"+(i+1)).attr('src', docServer + temp[i]);
						}

						$("#pdfRenderer").html(showAllPdfHtml);
						
						$("#kkDocumentDiv").hide();
						//$("#float_banner").show();
						//$("#menu").hide();
						$("."+_docType).show();
					//}
					
					
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
		
		 
		// Remove Separator
		$("#pager td > span.ui-separator").parent().remove();
		    
	    
		/*
		$("#btnCancel").button().click(function() {
			
			var row = grid.jqGrid('getGridParam', 'selrow');
			if (row != null) {
				var _id      = grid.jqGrid ('getCell', row, 'id');
				var sendData = { 
					id:_id,
					status:"NEW" //cancel verify , program will change status to NEW
                }
				var cancelVerify = $.ajax({
			    	url: "KK_Document/cancelVerify.html",
			        type: "POST",
			        data: JSON.stringify(sendData),
			        async:false, 
			        dataType: "json",
			        contentType: "application/json; charset=utf-8",
			        beforeSend: function(data) {
			        	
			        },
			        success: function(response) {
			        	
			        }
				}).responseText;
				
				location.reload();
				
			}else{
				$("#msgDialog").text('Error : Row not select, Please contact admin.');
				$("#msgDialog").dialog('open');					
			}
			
		});
		$("#btnSubmit").button().click(function() {
			var row = grid.jqGrid('getGridParam', 'selrow');
			if (row != null) {
				var _id      = grid.jqGrid ('getCell', row, 'id');
				var _appNo      = grid.jqGrid ('getCell', row, 'appNo');
				var _docType = grid.jqGrid ('getCell', row, 'id');
				var _email   = grid.jqGrid ('getCell', row, 'staffEmail');
				var _remarkArr = $("input[id='"+_docType+"remark']").map(function(){return $(this).val();}).get();
				var _comboboxArr = $("select[id='"+_docType+"combobox']").map(function(){return $(this).val();}).get();
				
				//alert(_id + "/" + _docType + "/" + _remarkArr + "/" + _comboboxArr);
				
				var sendData = { 
						id:_id,
						appNo:_appNo,
						staffEmail:_email,
						docRemark:_remarkArr.toString(),
						docLogStatus:_comboboxArr.toString()
	                }
				var updateKKDocStatus = $.ajax({
			    	url: "KK_Document/updateKKDocStatus.html",
			        type: "POST",
			        data: JSON.stringify(sendData),
			        async:false, 
			        dataType: "json",
			        contentType: "application/json; charset=utf-8",
			        beforeSend: function(data) {
			        	
			        },
			        success: function(response) {
			        	$("#float_banner").hide();
			        	$("#menu").show();
			        	$("#responseMessage").html("Update Document Log Already.");
						$("#dialog-form").dialog("open");
					    e.preventDefault();
			        }
				}).responseText;
				
				$("#float_banner").hide();
				$("#menu").show();
				
				if(updateKKDocStatus == "success"){
					$("#responseMessage").html("Update Document Log Already.");
					$("#dialog-form").dialog("open");
				    e.preventDefault();
				}else{
					$("#responseMessage").html("Error : error update document log status.");
					$("#dialog-form").dialog("open");
				    e.preventDefault();
				}
				
			}else{
				$("#msgDialog").text('Error : Row not select, Please contact admin.');
				$("#msgDialog").dialog('open');					
			}
		});
		*/
		   
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
	  
</style>
</head>
<body>
	<div id="kkDocumentDiv"> 
		<table id="grid"></table> 
		<div id="pager" style="text-align: center;"></div>
	</div> 
	 
	<div id="dialog-form" title="Message Alert">
	    <p id="responseMessage" class="validateTips"></p>
	</div> 
	
	<div id="verifyDocumentDiv" style="display:none">
		<div id="pdfRenderer">
			<iframe id="fred1" style="border:1px solid #666CCC" title="PDF in an i-Frame" src="" frameborder="1" scrolling="auto" height="500px" width="95%" ></iframe>
		</div>					
	</div>
 
	
</body>
</html>