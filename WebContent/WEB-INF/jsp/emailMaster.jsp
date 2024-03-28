<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>  
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	// debugger;
	
	$().ready(function() {
		 
		var grid = jQuery("#grid");
		/*
		var getColumnIndexByName = function (grid, columnName) {
            var cm = grid.jqGrid('getGridParam', 'colModel'), i, l = cm.length;
            for (i = 0; i < l; i++) {
                if (cm[i].name === columnName) {
                    return i; // return the index
                }
            }
            return -1;
        };
        */
		var mygrid = grid.jqGrid({
			datatype : "json",
			url : "emailMaster/list.html",
			mtype : "GET",
		    colNames:["<spring:message code='mis.emailMaster.lbl1'/>", // Primary Key hidden
		              "<spring:message code='mis.emailMaster.lbl1'/>",
		              "<spring:message code='mis.emailMaster.lbl2'/>",
		              "<spring:message code='mis.emailMaster.lbl3'/>",
		              "<spring:message code='mis.emailMaster.lbl4'/>",
		              "<spring:message code='mis.emailMaster.lbl5'/>",
		              "<spring:message code='mis.emailMaster.lbl6'/>" ],
		    colModel :[ 
		      {name:'id',            index:'id',            width:10,  sortable: true, sorttype:'int', hidden : true }, 
		      {name:'agentNumber',   index:'agentNumber',   width:14,  sortable: true, editable : true}, 
		      {name:'reinsurerName', index:'reinsurerName', width:20,  sortable: true, editable : true},
		      {name:'from',          index:'from',          width:20,  sortable: true, editable : true}, 
		      {name:'to',            index:'to',            width:20,  sortable: true, editable : true}, 
		      {name:'cc',            index:'cc',            width:20,  sortable: true, editable : true}, 
		      {name:'bcc',           index:'bcc',           width:20,  sortable: true, editable : true}
		    ],
		    pager: '#pager',
		    rowNum: 10,
		    height : 250,
			width : 650,
			sortname: 'id',
			autowidth: true,
			rownumbers: true,
		    sortorder: "asc",
			gridview : true,
		    viewrecords: true,
		    loadonce: true,
		    caption: 'Email Master',
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
		
        $.extend($.jgrid.edit, {
			addCaption : '<spring:message code="mis.emailMaster.div.lbl2" />',
			editCaption : '<spring:message code="mis.emailMaster.div.lbl1" />',
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
			caption : '<spring:message code="mis.emailMaster.div.lbl3" />',
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
			{edit:true,add:true,del:true,search:false,refresh:false,
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
		grid.jqGrid('filterToolbar', { stringResult: true, searchOnEnter: true, defaultSearch: 'cn'});
		
		$("#edit_grid").click(function() {
			var row = grid.jqGrid('getGridParam', 'selrow');
			if (row != null) {
				grid.jqGrid('editGridRow', row, {
					url : "emailMaster/edit.html",
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
							$("#grid").jqGrid('setGridParam', {datatype:'json'}).trigger('reloadGrid', [{page:1}]);
						}

						return [ result.success, errors, null ];
					}
				});
			}
		});
		$("#del_grid").click(function() {
			var row = grid.jqGrid('getGridParam', 'selrow');

			if (row != null) {
				grid.jqGrid('delGridRow', row, {
					url : 'emailMaster/delete.html',
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
							$("#grid").jqGrid('setGridParam', {datatype:'json'}).trigger('reloadGrid', [{page:1}]);
						}
						// only used for adding new records
						var newId = null;

						return [ result.success, errors, newId ];
					}
				});
			}
		});
		$("#add_grid").click(function() {
			grid.jqGrid('editGridRow', 'new', {
				url : "emailMaster/add.html",
				beforeSubmit : function (postdata, formid) {
					postdata.mode = 'add';
					
					return [true, ''];
				},
				afterSubmit : function(response, postdata) {
					var result = eval('(' + response.responseText + ')');
					var errors = "";

					if (!result.success) {
						for ( var i = 0; i < result.message.length; i++) {
							errors += result.message[i] + "<br/>";
						}
					} else {
						$("#msgDialog").text('<spring:message code="success.save" />');
						$("#msgDialog").dialog("open");
						$("#grid").jqGrid('setGridParam', {datatype:'json'}).trigger('reloadGrid', [{page:1}]);
					}
					// only used for adding new records
					var newId = null;

					return [ result.success, errors, newId ];
				}
			});
		});
		
		// Remove Separator
		$("#pager td > span.ui-separator").parent().remove();
		
	});  
</script>
<style type="text/css"> 
    /* fix the size of the pager */
	input.ui-pg-input { width: auto; }
	
	#emailMainDiv{
		width:750px;
		margin-left: 35px;
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
</style>
</head>
<body>
	<div id="emailMainDiv"> 
		<table id="grid"></table> 
		<div id="pager" style="text-align: center;"></div>
	</div> 
</body>
</html>