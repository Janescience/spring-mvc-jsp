<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:url value="KKDC" var="baseUrl"/>

<script type="text/javascript">
	// debugger;
	
	$().ready(function() {
		
		var roleList = $.ajax({
			url:"user/roleList.html", 
			async:false, 
			success:function() {
				
			}
		}).responseText;
		
		var statusList = $.ajax({
			url:"user/statusList.html", 
			async:false, 
			success:function() {
				
			}
		}).responseText;
		
		function checkLenght(value, colname) {
			var length = value.length;
			if (length < 8) {
			   return [false, "<spring:message code='validation.length.userName' arguments='" + colname + "'/>"];
			} else { 
			   return [true, ""];
			}
		}
		
		function verifyPasswordAdd(value, colname, mode) {
			var rowid = grid.jqGrid('getGridParam', 'selrow');
			if (rowid == null) {
				if (value.length < 8 || value.length > 15) {
					return [false, "<spring:message code='validation.length.password' arguments='" + colname + "'/>"];
				}
				
				if (!(value.match(/([a-zA-Z])/) && value.match(/([0-9])/))) {
					return [false, "<spring:message code='validation.rule.password' arguments='" + colname + "'/>"];
				}
			}
			
			return [true, ""];
		}
		
		function verifyConfirmPasswordAdd(value, colname, mode) {
			
			verifyPasswordAdd(value, colname);

			var rowid = grid.jqGrid('getGridParam', 'selrow');
			if (rowid == null) {
				if ($("#password").val() !== value) {
					return [false, "<spring:message code='validation.mismath.password' />"];
				}
			}
			return [true, ""];
		}
		
		function verifyPassword(password, confirmPassword) {
			var formid = $("#FrmResetPassword");
			if (password == null || password.length == 0) {
				$('tr#FormError > td.ui-state-error', formid)
				.html("<spring:message code='required' arguments='Password'/>");
				$('tr#FormError').show();
				$('#newPassword').focus();
				return false;
			}
			
			if (password.length < 8 || password.length > 15) {
				$('tr#FormError > td.ui-state-error', formid)
				.html("<spring:message code='validation.length.password' arguments='Password'/>");
				$('tr#FormError').show();
				$('#newPassword').focus();
				return false;
			}
	    	
	    	if(!(password.match(/([a-zA-Z])/) && password.match(/([0-9])/))) {

	    		$('tr#FormError > td.ui-state-error', formid)
				.html("<spring:message code='validation.rule.password' arguments='Password'/>");
				$('tr#FormError').show();
				$('#confirmPassword_txt').val('');
				$('#newPassword').focus();

                return false;
			};
			
			if (confirmPassword == null || confirmPassword.length == 0) {
				$('tr#FormError > td.ui-state-error', formid)
				.html("<spring:message code='required' arguments='Confirm Password'/>");
				$('tr#FormError').show();
				$('#confirmPassword_txt').focus();
				return false;
			}
			
			if (confirmPassword.length < 8 || confirmPassword.length > 15) {
				$('tr#FormError > td.ui-state-error', formid)
				.html("<spring:message code='validation.length.password' arguments='Confirm Password'/>");
				$('tr#FormError').show();
				$('#confirmPassword_txt').focus();
				return false;
			}
			
	    	if (password !== confirmPassword) {
	    		$('tr#FormError > td.ui-state-error', formid)
	    		.html("<spring:message code='validation.mismath.password' />");
	    		$('tr#FormError').show();
				$('#confirmPassword_txt').val('');
				$('#confirmPassword_txt').focus();
	    		return false; 
	    	}
	    	
	    	return true;
		}
		
		getColumnIndexByName = function(columnName) {
	        var cm = grid.jqGrid('getGridParam', 'colModel');
	        for (var i = 0; i < cm.length; i++) {
	            if (cm[i].name === columnName) {
	                return i; // return the index
	            }
	        }
	        return -1;
	    };
		
		function multiCheckElem(value, options) {
		    // for each checkbox in the list build the input element set the initial "checked" status
		    var ctl = '';
		    var ckboxAry = options.list.split(';');
		    
		    for (var i in ckboxAry) {
		       	var items = ckboxAry[i];
			   	var item = items.split(':');
		       	ctl += '<input type="checkbox" ';

		       	if (value === item[0]) {
		          	ctl += 'checked="checked" ';
		       	}
		       	ctl += 'value="' + item[0] + '"> ' + item[1] + '</input><br />&nbsp;';
		    }
		    return ctl;
		}

		function multiCheckVal(elem, action, val) {
		   	var items = '';
		   	
		   	if (action == 'get')  {
			   	var roleArr = [];
	            $.each($("input[name='role']:checked"), function(){
	            	roleArr.push($(this).val());
	            });
	            items = roleArr.join(",");
		   	}
		   	else{
		   		// Clear All checkBox before assign checked.
		   		$("#tr_role").find('input[name="role"]').prop("checked", false);
		   		
		   		// reassign/refresh checked status after save.
		   		var ckboxAry = val.split(',');
		   		$("#tr_role").find('input[name="role"][value=' + ckboxAry.join('], [value=') + ']').prop("checked", true);		
		   	}
		   	/*
		   	if (action == 'get')  {
		       // for each input element if it's checked, add it to the list of items 
		       for (var i in elem) {
		          if (elem[i].tagName == 'INPUT' && elem[i].checked) {
		             items += elem[i].value + ',';
		          }
		       }

		       // items contains a comma delimited list that is returned as the result of the element
		       items = items.replace(/,$/, '');
		    } else {
		    	// Clear All checkBox before assign checked.
		       	for (var i in elem) {
	          		if (elem[i].tagName == 'INPUT') {
		                elem[i].checked = false;
					}
				}
		       	// for each input element based on the input value, set the checked status
		       	var ckboxAry = val.split(',');
	        	for (var m in ckboxAry) {
	   				var item = ckboxAry[m];
		       		for (var i in elem) {
		          		if (elem[i].tagName == 'INPUT') {
			             	if (item == elem[i].value) {
			                	elem[i].checked = true;
			             	}
						}
					}
		       	}
		    }
		   	*/
		    return items;
		}

		var grid = $("#grid");
		/* start jqgrid. */
		grid.jqGrid({
			datatype:"json",
			url:"user/list.html",
			mtype:"GET",
			colNames:[ "<spring:message code='user.lbl.ID'/>", 
				       "<spring:message code='user.lbl.userName'/>", 
				       "<spring:message code='user.lbl.password'/>", 
				       "<spring:message code='user.lbl.confirmPassword'/>", 
				       "<spring:message code='user.lbl.firstName'/>", 
				       "<spring:message code='user.lbl.lastName'/>", 
				       "<spring:message code='user.lbl.email'/>", 
				       "<spring:message code='user.lbl.role'/>",
				       "<spring:message code='user.lbl.status'/>" ],
			colModel:[ { 
					name:"id", 
					index:"id", 
					width:20, 
					key:true,
					hidden:true,
					sortable:false,
					editable:false, 
					edittype:'text',
					editrules:{ edithidden:true }
				}, { 
					name:"userName", 
					index:"userName", 
					width:30, 
					sortable:false,
					editable:true, 
					edittype:'text',
					editrules:{ 
						custom : true,
						custom_func : checkLenght,
						required : true 
					},
					editoptions : { size: 9, maxlength : 15 }
				}, { 
					name:"password", 
					index:"password", 
					width:30, 
					hidden:true,
					sortable:false,
					editable:true, 
					edittype:'password',
					editrules:{ 
						edithidden:true,
						custom : true,
						custom_func : verifyPasswordAdd,
						required : true 
					},
					editoptions : { size: 17, maxlength : 15 }
				}, { 
					name:"confirmPassword", 
					index:"confirmPassword", 
					width:30, 
					hidden:true,
					sortable:false,
					editable:true, 
					edittype:'password',
					editrules:{ 
						edithidden:true,
						custom : true,
						custom_func : verifyConfirmPasswordAdd,
						required : true 
					},
					editoptions : { size: 17, maxlength : 15 }
				}, { 
					name:"firstName", 
					index:"firstName", 
					width:50, 
					sortable:false,
				    editable:true, 
				    edittype:'text',
					editoptions : { size: 50, maxlength : 50 }
				}, { 
					name:"lastName", 
					index:"lastName", 
					width:50, 
					sortable:false,
				    editable:true, 
				    edittype:'text',
					editoptions : { size: 50, maxlength : 50 }
				}, { 
					name:"email", 
					index:"email", 
					width:80, 
					sortable:false,
				    editable:true, 
				    edittype:'text',
					editoptions : { size: 50, maxlength : 50 },
					editrules : { email:true, required:false }
				}, { 
					name:"role", 
					index:"role", 
					width:30, 
					hidden:true,
					sortable:false,
					editable:true, 
					edittype:'custom',
					editrules:{ edithidden:true },
					editoptions:{ 
						custom_element:multiCheckElem, 
						custom_value:multiCheckVal, 
						list:roleList 
					}
				}, { 
					name:"status.name", 
					index:"status.name",
					width:30, 
					sortable:false,
					editable:true, 
					edittype:'select',
					editoptions: {
						value:statusList
					}
				} ],
			pager:"#pager",
			prmNames:{ rows:'max', search:null },
			rowNum: 15,
		    height : 340,
			width : 700,
			rownumbers:true,
			scrollOffset:0,
			hoverrows:true,
			sortname:"userId",
			sortorder:"desc",
			viewrecords:true, 
			emptyrecords:"<spring:message code='message.lbl.norecords'/>",
			caption:"<spring:message code='user.lbl.caption'/>",
			loadonce:false,
			editurl:"clientArray",
			jsonReader:{
				root:"rows",
				page:"page",
			    records:function(result) {
			    	//Total number of records
			      	return result.records;
			    },
				total:function(result) {
			      	//Total number of pages
			      	return Math.ceil(result.records / result.max);
			    },
				repeatitems:false,
				cell:"cell",
				id:"id"
			},
			gridComplete:function() {
				$(".ui-pg-input").numeric();
				if ($(".ui-pg-input").val() == 0) {
					$(".ui-pg-input").attr("disabled", "disabled");
				} else {
					$(".ui-pg-input").removeAttr("disabled", "disabled");
				}
			},
			onSelectRow:function(id) {
				$("#" + id).removeClass("selected");
			}
		});

		$.ajaxSetup({
			cache:false
		});
		
		// Add a new data to grid
		$.extend($.jgrid.edit, {
			addCaption : "<spring:message code='user.lbl.caption.Add'/>",
		    editCaption : "<spring:message code='user.lbl.caption.Edit'/>",
			bSubmit : "<spring:message code='btn.lbl.save'/>",
			bCancel : "<spring:message code='btn.lbl.cancel'/>",
			bClose : "<spring:message code='btn.lbl.close'/>",
			saveData : '<spring:message code="confirm.save" />',
			bYes : "<spring:message code='btn.lbl.yes'/>",
			bNo : "<spring:message code='btn.lbl.no'/>",
			bExit : "<spring:message code='btn.lbl.cancel'/>",
			width : 420,
			height : "auto",
			savekey : [true, 13],
			closeOnEscape : true,
			closeAfterAdd : true,
			closeAfterEdit : true,
			reloadAfterSubmit : true,
			resize : false,
			top : (($(window).height() - 160) / 2),
			left : (($(window).width() - 400) / 2)
		});
		
		$.extend($.jgrid.del, {
			caption : '<spring:message code="user.lbl.caption.Del" />',
		    msg : '<spring:message code="confirm.delete" />',
		    bSubmit : '<spring:message code="btn.lbl.del" />',
		    bCancel : '<spring:message code="btn.lbl.cancel" />',
			closeOnEscape : true,
			reloadAfterSubmit : true,
			resize : false,
			width : 400,
			top : (($(window).height() - 150) / 2),
			left : (($(window).width() - 300) / 2)
		});
		
		// Set Add Option for add button
		var addOption = {
			beforeShowForm : function(form) {
				$("#userName").removeAttr("disabled");
				$("#tr_password").show();
				$("#tr_confirmPassword").show();
				$('input:checkbox').removeAttr('checked');
				setTimeout("$('#userName').focus();", 100);
				$('tr > td.CaptionTD', form).html('<spring:message code="user.lbl.status"/>' + ' (*)');
				$('tr#tr_userName > td.CaptionTD', form).html('<spring:message code="user.lbl.userName"/>' + ' (*)');
				$('tr#tr_password > td.CaptionTD', form).html('<spring:message code="user.lbl.password"/>' + ' (*)');
				$('tr#tr_confirmPassword > td.CaptionTD', form).html('<spring:message code="user.lbl.confirmPassword"/>' + ' (*)');
				$('tr#tr_firstName > td.CaptionTD', form).html('<spring:message code="user.lbl.firstName"/>' + ' (*)');
				$('tr#tr_lastName > td.CaptionTD', form).html('<spring:message code="user.lbl.lastName"/>' + ' (*)');
				$('tr#tr_email > td.CaptionTD', form).html('<spring:message code="user.lbl.email"/>');
				$('tr#tr_role > td.CaptionTD', form).html('<spring:message code="user.lbl.role"/>' + ' (*)');
			}
		};
		
		// Set Edit Option for eidt button
		var editOption = {
			alerttext : '<spring:message code="warning.selected.edit" />',
			beforeShowForm : function(form) {
				$("#tr_password").hide();
				$("#tr_confirmPassword").hide();
				$("#userName").attr("disabled", "disabled");
				setTimeout("$('#firstName').focus();", 100);
				$('tr > td.CaptionTD', form).html('<spring:message code="user.lbl.status"/>' + ' (*)');
				$('tr#tr_userName > td.CaptionTD', form).html('<spring:message code="user.lbl.userName"/>' + ' (*)');
				$('tr#tr_password > td.CaptionTD', form).html('<spring:message code="user.lbl.password"/>' + ' (*)');
				$('tr#tr_confirmPassword > td.CaptionTD', form).html('<spring:message code="user.lbl.confirmPassword"/>' + ' (*)');
				$('tr#tr_firstName > td.CaptionTD', form).html('<spring:message code="user.lbl.firstName"/>' + ' (*)');
				$('tr#tr_lastName > td.CaptionTD', form).html('<spring:message code="user.lbl.lastName"/>' + ' (*)');
				$('tr#tr_email > td.CaptionTD', form).html('<spring:message code="user.lbl.email"/>');
				$('tr#tr_role > td.CaptionTD', form).html('<spring:message code="user.lbl.role"/>' + ' (*)');
			}
		};
		
		// Set Delete Option for delete button
		var deleteOption = {
			alerttext : '<spring:message code="warning.selected.delete" />',
			recreateForm  : true,
			reloadAfterSubmit : false,
			closeAfterDelete : true,
			beforeShowForm : function(form) {
				//hide arrows
				$('#pData').hide();
				$('#nData').hide();
			}
		};
		
		// Add option for button in pager-bar
		grid.jqGrid("navGrid", "#pager",  
			{ edit : true, add : true, del : true, reload : true, search : false, view : false }, 
			editOption, 
			addOption, 
			deleteOption
		).jqGrid("navButtonAdd", '#pager', { 
				caption : "",
		    	buttonicon : "ui-icon-key", 
		    	position : "first", 
		    	title : 'Reset Password', 
		    	cursor : "pointer",
		    	onClickButton: resetPassword
			}
		);
		
		// Add a new data to grid
		$("#add_grid").click(function() {
			grid.jqGrid('editGridRow', 'new', {
				url : "user/add.html",
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
			var rowid = grid.jqGrid('getGridParam', 'selrow');
			if (rowid != null) {
				grid.jqGrid('editGridRow', rowid, {
					url : "user/edit.html",
					beforeCheckValues : function (postdata, formid, mode) {
						
					},
					beforeSubmit : function (postdata, formid) {
						postdata.mode = 'edit';
						
						return [true, ''];
					},
					afterSubmit : function(response, postdata) {
						var result = eval('(' + response.responseText + ')');
						var errors = "";

						if (!result.success) {
							for (var i = 0; i < result.message.length; i++) {
								errors += result.message[i] + "<br/>";
							}
						} else {
							$("#msgDialog").text('<spring:message code="success.save" />');
							$("#msgDialog").dialog("open");
							
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
			var rowid = grid.jqGrid('getGridParam', 'selrow');
			// A pop-up dialog will appear to confirm the selected action
			if (rowid != null) {
				grid.jqGrid('delGridRow', rowid, {
					url : 'user/delete.html',
					beforeSubmit : function (postdata, formid) {
						postdata.mode = 'del';
						
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
							$("#msgDialog").text('<spring:message code="success.delete" />');
							$("#msgDialog").dialog("open");
						}
						// only used for adding new records
						var newId = null;

						return [ result.success, errors, newId ];
					}
				});
			}
		});
		
		function resetPassword() {
			// Get the currently selected row
			var rowid = grid.jqGrid('getGridParam','selrow');
			// A pop-up dialog will appear to confirm the selected action
			if (rowid != null) {
				$("#resetPasswordDialog").dialog({
					title : "<spring:message code='user.lbl.caption.resetPassword' />",
					resizable : false,
					closeOnEscape: true,
					height : "100%",
					width : "300",
					modal : true,
					buttons : {
						"<spring:message code='btn.lbl.resetPassword' />" : function() {
							var newPassword = $.trim($("input#newPassword").val());
							var confirmPassword = $.trim($("input#confirmPassword_txt").val());
							var obj = { id : rowid, password : newPassword };
							$.ajax({
						    	url: "user/resetPassword.html",
						        type: "POST",
						        data: JSON.stringify(obj),
						        dataType: "json",
						        contentType: "application/json; charset=utf-8",
						        beforeSend: function(data) {
						        	return verifyPassword(newPassword, confirmPassword);
						        },
						        success: function(response) {
									$("#resetPasswordDialog").dialog("destroy");
									$("#msgDialog").text('<spring:message code="success.resetPassword" />');
									$("#msgDialog").dialog("open");
						        }
							});
						},
						"<spring:message code='btn.lbl.cancel' />" : function() {
							$(this).dialog("close");
						}
					},
					open : function(event, ui) {
						$('body').css('overflow','hidden');
						$(this).css('height','auto');
						$('.ui-widget-overlay').css('width','100%');
						
			        	$("#newPassword").val('');
			        	$("#confirmPassword_txt").val('');
			    		$('tr#FormError').hide();
						   
						$('.ui-dialog-buttonpane').find('button:contains("Reset Password")')
					    .removeClass('ui-button')
					    .removeClass('ui-button-text-only')
					    .removeClass('ui-widget')
					    .addClass('fm-button')
					    .addClass('fm-button-icon-left')
					    .prepend('<span class="ui-icon ui-icon-key"></span>')
					    .css('width','140')
					    .css('height','25');
						
						$('.ui-dialog-buttonpane').find('button:contains("Cancel")')
					    .removeClass('ui-button')
					    .removeClass('ui-button-text-only')
					    .removeClass('ui-widget')
					    .addClass('fm-button')
					    .addClass('fm-button-icon-left')
					    .prepend('<span class="ui-icon ui-icon-close"></span>')
					    .css('width','65')
					    .css('height','25');
					}, 
			    	close : function(event, ui) {
			    		$('body').css('overflow','auto'); 
			    	}
				});
			} else {
				$("#dialogSelectRow").removeClass("ui-dialog-content ui-widget-content");
				$("#dialogSelectRow").addClass("custom-dialog");
            	$("#dialogSelectRow").text('<spring:message code="warning.selected" />');
				$("#dialogSelectRow").dialog({
					title : "<spring:message code='warning.lbl.caption' />",
					resizable : false,
					height : "auto",
					width : "350",
					modal : true,
					buttons : {
						"<spring:message code='btn.lbl.ok' />" : function() {
							$(this).dialog("close");
						}
					},
					open : function(event, ui) {
						$('body').css('overflow','hidden');
						$('.ui-widget-overlay').css('width','100%');
					}, 
			    	close : function(event, ui) {
			    		$('body').css('overflow','auto'); 
			    	} 
				});
			}
		}
	});

</script>
<style type="text/css"> 
    /* fix the size of the pager */
	input.ui-pg-input { width: auto; }
	
	#userDialog{
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
<html>
<body>
	<!-- Grid Form -->
	<div id="userDialog" style="margin-left:35px;width:750px;">
		<table id="grid"></table>
		<div id="pager" style="text-align:center;"></div>
	</div>
	<div id="dialogSelectRow" title="Warning" style="display:none" ></div>
	<div id="resetPasswordDialog" style="display:none;" >
		<form name="FormPost" id="FrmResetPassword" class="FormGrid">
			<table>
				<tr id="FormError" style="display: none;">
					<td class="ui-state-error" colspan="2"></td>
				</tr>
				<tr>
					<td align="left"><label><spring:message code="user.lbl.newPassword" /> (*)</label></td>
					<td align="left"><input id="newPassword" type="password" size="16" required="required"></td>
				</tr>
				<tr>
					<td align="left"><label><spring:message code="user.lbl.confirmPassword" /> (*)</label></td>
					<td align="left"><input id="confirmPassword_txt" type="password" size="16" required="required"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
