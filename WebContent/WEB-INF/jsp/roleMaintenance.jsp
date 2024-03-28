<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script type="text/javascript">
	// debugger;
	
	$().ready(function() {
		
		function checkLenght(value, colname) {
			if (value.length > 50) {
			   return [false, "<spring:message code='validation.length.role.name' arguments='" + colname + "'/>"];
			} else { 
			   return [true, ""];
			}
		}

		var grid = $("#grid");
		/* start jqgrid. */
		grid.jqGrid({
			datatype : "json",
			url : "role/list.html",
			mtype : "GET",
			colNames : [ "<spring:message code='role.lbl.Code'/>", 
			             "<spring:message code='role.lbl.Name'/>", 
			             "<spring:message code='role.lbl.Description'/>" 
			           ],
			colModel : [ { 	
				name : 'id', 
				index : 'id',
				editable : false,
				editoptions : { 
					readonly : true
				},
				hidden : true
			},  {
				name : "name",
				index : "name",
				width : 50,
				sortable : false,
				editable : true,
				edittype: 'text',
				editrules : {
					custom : true,
					custom_func : checkLenght,
					required : true
				},
				editoptions : {
					size: 30,
					maxlength : 50
				},
				formoptions: {
					elmprefix : '(*) ',
					rowpos : 1, 
					colpos : 0
				}
			}, {
				name : "description",
				index : "description",
				width : 50,
				sortable : false,
				editable : true,
				edittype: 'text',
				editrules : {
					custom : true,
					custom_func : checkLenght,
				},
				editoptions : {
					size : 30,
					maxlength: 80
				}
			} ],
			pager : "#pager",
			prmNames: { rows : 'max', search : null },
			rowNum : 10,
			rownumbers : true,
			scrollOffset : 0,
			hoverrows : true,
			sortname : "id",
			sortorder : "desc",
			viewrecords : true,
			height : 230,
			width : 550,
			emptyrecords : "<spring:message code='message.lbl.norecords'/>",
			caption : "<spring:message code='role.lbl.caption'/>",
			loadonce : false,
			editurl: "role/add.html",
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

		$.ajaxSetup({
			cache : false
		});
		
		$.extend($.jgrid.edit, {
			addCaption : "<spring:message code='role.lbl.caption.Add'/>",
		    editCaption : "<spring:message code='role.lbl.caption.Edit'/>",
			bSubmit : "<spring:message code='btn.lbl.save'/>",
			bCancel : "<spring:message code='btn.lbl.cancel'/>",
			bClose : "<spring:message code='btn.lbl.close'/>",
			saveData : '<spring:message code="confirm.save" />',
			bYes : "<spring:message code='btn.lbl.yes'/>",
			bNo : "<spring:message code='btn.lbl.no'/>",
			bExit : "<spring:message code='btn.lbl.cancel'/>",
			width : 400,
			height : "auto",
			savekey : [true, 13],
			closeOnEscape : true,
			closeAfterAdd : true,
			closeAfterEdit : true,
			reloadAfterSubmit : false,
			resize : false,
			top : (($(window).height() - 160) / 2),
			left : (($(window).width() - 400) / 2)
		});
		
		$.extend($.jgrid.del, {
			caption : '<spring:message code="role.lbl.caption.Del" />',
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
		
		$.extend($.jgrid.search, {
			caption: 'Search Role Name',
			Find: 'Search',
			odata : ['equal', 'begins with', 'contains'],
			sopt: ['eq','bw','cn'],
			width: 650
		});
		
		// Set Add Option for add button
		var addOption = {
			beforeShowForm : function(form) {
				//$("#name").addClass('uppercase');
				setTimeout("$('#name').focus();", 100);
			}
		};
		
		// Set Edit Option for eidt button
		var editOption = {
			beforeShowForm : function(form) {
				//hide arrows
				$('#pData').hide();
				$('#nData').hide();
				setTimeout("$('#name').focus();", 100);
			}
		};
		
		// Set Delete Option for delete button
		var deleteOption = {
			alerttext : '<spring:message code="warning.selected.delete" />',
			reloadAfterSubmit : false,
			recreateForm  : true,
			closeAfterDelete : true,
			beforeShowForm : function(form) {
				//hide arrows
				$('#pData').hide();
				$('#nData').hide();
			}
		};
		
		// Set Add Option for add button
		var searchOption = {
			modal: true,
			resize: false,
			closeOnEscape: true,
			beforeShowSearch: function() {
				$("select").attr("disabled", "disabled");
				$("select.selectopts").removeAttr("disabled");
				$("input#jqg1.input-elm").attr("size", "30");
				$('input#jqg1.input-elm').attr('maxLength','50');
				setTimeout("$('input#jqg1.input-elm').focus();", 100);
			},
			onClose: function() {
				$("input#jqg1.input-elm").val('');
				var postdata = grid.jqGrid('getGridParam','postData');
				postdata._search = false;
				postdata.searchField = "";
				postdata.searchOper = "";
				postdata.searchString = "";
			}
		};
		
		// Add option for button in pager-bar
		grid.jqGrid("navGrid", "#pager",  
			{ edit : true, add : true, del : true, reload : true, search : true, view : false }, 
			editOption, 
			addOption,
			deleteOption,
			searchOption
		);
		
		// Add a new data to grid
		$("#add_grid").click(function() {
			
			grid.jqGrid('addGridRow', 'new', {
				url : "role/add.html",
				afterSubmit : function(response, postdata) {
					var result = eval('(' + response.responseText + ')');
					var errors = "";

					if (!result.success) {
						for ( var i = 0; i < result.message.length; i++) {
							errors += result.message[i] + "<br/>";
						}
					} else {
						$("#msgDialog").text('<spring:message code="success.save" />');
						$("#msgDialog").dialog('open');
						
						setTimeout(function() {
			            	grid.setGridParam({ page: grid.getGridParam('lastpage') }).trigger("reloadGrid");
			            }, 100);
					}
					// only used for adding new records
					var newId = null;

					return [ result.success, errors, newId ];
				}
			});
		});
		
		// Edit data from grid
		$("#edit_grid").click(function() {
			var row = grid.jqGrid('getGridParam', 'selrow');
			if (row != null) {
				grid.jqGrid('editGridRow', row, {
					url : "role/edit.html",
					afterSubmit : function(response, postdata) {
						var result = eval('(' + response.responseText + ')');
						var errors = "";

						if (!result.success) {
							for (var i = 0; i < result.message.length; i++) {
								errors += result.message[i] + "<br/>";
							}
						} else {
							$("#msgDialog").text('<spring:message code="success.save" />');
							$("#msgDialog").dialog('open');
							
							setTimeout(function() {
				            	grid.setGridParam({ page: grid.getGridParam('page') }).trigger("reloadGrid");
				            }, 100);
						}

						return [ result.success, errors, null ];
					}
				});
			}
		});
		
		// Delete data from grid
		$("#del_grid").click(function() {
			// Get the currently selected row
			var row = grid.jqGrid('getGridParam', 'selrow');
			// A pop-up dialog will appear to confirm the selected action
			if (row != null) {
				grid.jqGrid('delGridRow', row, {
					url : 'role/delete.html',
					afterSubmit : function(response, postdata) {
						var result = eval('(' + response.responseText + ')');
						var errors = "";

						if (!result.success) {
							for ( var i = 0; i < result.message.length; i++) {
								errors += result.message[i] + "<br/>";
							}
						} else {
							$("#msgDialog").text('<spring:message code="success.delete" />');
							$("#msgDialog").dialog('open');
						}
						// only used for adding new records
						var newId = null;

						return [ result.success, errors, newId ];
					}
				});
			}
		});
	});

</script>
</head>
<html>
<body>
	<!-- Grid Form -->
	<div id="roleDialog" style="margin-left: 130px">
		<table id="grid"></table>
		<div id="pager" style="text-align: center;"></div>
	</div>
</body>
</html>