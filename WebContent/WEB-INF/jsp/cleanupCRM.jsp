<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="KKDC" var="baseUrl"/>

<script type="text/javascript">

	$().ready(function() {
		
		var grid = $("#grid");		
		/* start jqgrid. */
		grid.jqGrid({
			datatype : "json",
			url : "/${baseUrl}/crm/cleanList.html",
			mtype : "GET",
	        colNames: [ "<spring:message code='cleanup.lbl.fileName'/>", 
			            "<spring:message code='cleanup.lbl.uploadDate'/>", 
			            "<spring:message code='cleanup.lbl.uploadBy'/>", 
			            "<spring:message code='cleanup.lbl.status'/>" 
			          ], 
	        colModel: [{
	        			   name: "fileName", 
	        			   index: "fileName", 
	        			   width: 100, 
	        			   sortable: false, 
	        			   search: false, 
	        			   editable: false
	        		   },  
	                   {
	        			   name: "lastUploadDate", 
	        			   index: "lastUploadDate", 
	        			   width: 50, 
	        			   sortable: false, 
	        			   search: false, 
	        			   editable: false
	        		   },  
	                   {
	        			   name: "uploadedBy", 
	        			   index: "uploadedBy", 
	        			   width: 100, 
	        			   sortable: false, 
	        			   search: false, 
	        			   editable: false
	        		   },  
	                   {
	        			   name: "status", 
	        			   index: "status", 
	        			   width: 50, 
	        			   sortable: false, 
	        			   search: false, 
	        			   editable: false
	        		   }],
			pager: "#pager",
			prmNames: { rows : 'max', search : null },
			rowNum : 10,
			rownumbers : true,
			scrollOffset : 0,
	        hoverrows: true,
	        sortname: "id", 
	        sortorder: "desc", 
	        viewrecords : true, 
	        multiselect : true,
	        multiboxonly : true,
			height : 221,
			width : 600,
			emptyrecords : "<spring:message code='message.lbl.norecords'/>",
			caption : "<spring:message code='cleanup.lbl.caption'/>",
			loadonce : false,
			editurl : "clientArray",
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
				id : "id" + "uploadedBy"
			},
			gridComplete : function() {
				$(".ui-pg-input").numeric();
				if ($(".ui-pg-input").val() == 0) {
					$(".ui-pg-input").attr("disabled", "disabled");
				} else {
					$(".ui-pg-input").removeAttr("disabled", "disabled");
				}
			},
            onSelectRow: function(id) { 
				$("#" + id).removeClass("selected");
            }
		});
		
		$.ajaxSetup({
			cache : false
		});
		
		// Add option for button in pager-bar
		grid.jqGrid("navGrid", "#pager",  
			{ edit : false, add : false, del : false, reload : true, search : false, view : false }, 
			{}, 
			{}, 
			{}
		).jqGrid("navButtonAdd", '#pager', { 
				caption : "",
		    	buttonicon : "ui-icon-trash", 
		    	position : "last", 
		    	title : '<spring:message code="cleanup.lbl.caption.Del" />', 
		    	cursor : "pointer",
		    	onClickButton: deleteRow
			}
		);
		
		function deleteRow() {
			// Get the currently selected row
			var rows = grid.jqGrid('getGridParam','selarrrow');
			// A pop-up dialog will appear to confirm the selected action
			if (rows != null && (rows.length > 0)) {
				grid.jqGrid('delGridRow', rows, {
			  		url: '/${baseUrl}/crm/delete.html', 
			      	recreateForm: true,
			        reloadAfterSubmit:false,
			        closeAfterDelete: true,
			        caption: '<spring:message code="cleanup.lbl.caption.Del" />',
			        msg: '<spring:message code="confirm.clean" />',
			        bSubmit: '<spring:message code="cleanup.lbl.caption.Del" />',
			        bCancel: '<spring:message code="btn.lbl.cancel"/>',
			        closeOnEscape : true,
					resize : false,
					width : 300,
					top : (($(window).height() - 150) / 2),
					left : (($(window).width() - 300) / 2),
			  		ajaxDelOptions: { contentType: "application/json" },
					serializeDelData: function(postdata) {
						var fileNames = [];
		                for (var i = 0, il = rows.length; i < il; i++) {
		                	var fileName = grid.jqGrid('getCell', rows[i], 'fileName');
		                	fileNames.push(fileName);
		                }
					    return JSON.stringify(fileNames);
					},
			     	beforeShowForm: function(form) {
				        //hide arrows
				        $('#pData').hide();  
				        $('#nData').hide();  
			        },
			        afterSubmit : function(response, postdata) { 
						var result = eval('(' + response.responseText + ')');
			       		var errors = "";
			        
			            if (!result.success) {
				        	for (var i = 0; i < result.message.length; i++) {
				         		errors +=  result.message[i] + "";
				        	}
			            }  else {
			            	$("#msgDialog").text('<spring:message code="success.clean" />');
							$("#msgDialog").dialog('open');
						}			                    
			       		return [result.success, errors, null];
			      	}
				});
			} else {
				$('#warningDialog').text('<spring:message code="warning.selected.clean" />');
				$('#warningDialog').dialog('open');
			}
		}
	});
</script>

</head>
<body>
	<!-- Grid Form -->
	<div id="agentDialog" style="margin-left: 120px">
		<table id="grid"></table>
		<div id="pager" style="text-align: center;"></div>
	</div>
</body>
</html>