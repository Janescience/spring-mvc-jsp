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
			url : "KK_StatusMaster/list.html",
			mtype : "GET",
		    colNames:["<spring:message code='kkConfigStatus.lbl1'/>", // Primary Key hidden
		              "<spring:message code='kkConfigStatus.lbl1'/>",
		              "<spring:message code='kkConfigStatus.lbl2'/>",
		              "<spring:message code='kkConfigStatus.lbl3'/>",
		              "<spring:message code='kkConfigStatus.lbl4'/>" ],
		    colModel :[ 
		      {name:'id',   index:'id',   width:55, sortable: true, sorttype:'int', hidden : true }, 
		      {name:'groupCd',   	index:'groupCd', 	width:20,	sortable: true,	editable : true}, 
		      {name:'keyName',		index:'keyName',	width:20, 	sortable: true,	editable : true, editrules: {required: true }, editoptions: {maxlength: 50}}, 
		      {name:'valueCd',      index:'valueCd',	width:20,	sortable: true,	editable : true, editrules: {required: true }, editoptions: {maxlength: 100}}, 
		      {name:'description',	index:'description',width:20,	sortable: false, editable : true, editoptions: {maxlength: 200}} ],
		    pager: '#pager',
		    rowNum: 15,
		    height : 340,
			width : 650,
			sortname: 'keyName',
			autowidth: true,
			rownumbers: true,
		    sortorder: "asc",
			gridview : true,
		    viewrecords: true,
		    loadonce: true,
		    caption: 'KK Status Master',
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
			addCaption : '<spring:message code="kkConfig.div.lbl2" />',
			editCaption : '<spring:message code="kkConfig.div.lbl1" />',
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
			caption : '<spring:message code="kkConfig.div.lbl3" />',
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

			$('#groupCd').attr("readonly", true);
			$('#keyName').attr("readonly", true);
			
			var row = grid.jqGrid('getGridParam', 'selrow');
			if (row != null) {
				grid.jqGrid('editGridRow', row, {
					url : "KK_StatusMaster/edit.html",
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
					url : 'KK_StatusMaster/delete.html',
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
				url : "KK_StatusMaster/add.html",
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
			
			$('#groupCd').val("STATUS_CD");
			$('#groupCd').attr("readonly", true);
			$('#keyName').attr("readonly", false);
			
		});
		 
		// Remove Separator
		$("#pager td > span.ui-separator").parent().remove();
	});  
</script>
<style type="text/css"> 
    /* fix the size of the pager */
	input.ui-pg-input { width: auto; }
	
	#garageMagerDiv{
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
	#map{
		width:600px;
		height:400px;
	}
</style>
</head>
<body>
	<div id="garageMagerDiv"> 
		<table id="grid"></table> 
		<div id="pager" style="text-align: center;"></div>
	</div> 
	
	<div id="garagePictureDialog" style="display:none;">
	<section id="examples" class="section examples-section">
		<div class="image-row">
			<div id="garagePictureList" class="image-set">
				<!-- Image will add here automatic. -->
			</div>
		</div>
	</section>	
	</div>
	
	<div id="iframeDiv" style="display:none;">
		<iframe id="frame" src="" width="90%" height="430" frameBorder="0"></iframe>
	</div>
	
	<!--  <div id="mapGarageDialog" style="display:none;" ></div>  -->
	
	<input type="hidden" id="mapLocationChange" value=""/>
	<div id="map" style="display:none;"></div>
	 
</body>
</html>