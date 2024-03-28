<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>  
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	// debugger;
	
	$().ready(function() {
 
		$( "#tabs" ).tabs();
		 
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
        
        var custNameSearch = getUrlParameter('custNameSearch');
        var searchStatus =  getUrlParameter('searchStatus');
        
		var mygrid = grid.jqGrid({
			
		    datatype : "json",
			url : "KK_UL_Callback/list.html?custNameSearch="+custNameSearch+"&searchStatus="+searchStatus,
			mtype : "GET",
		    colNames:["<spring:message code='kkDocument.lbl1'/>", // Primary Key hidden
		              "วันที่",
		              "ชื่อลูกค้า",
		              "เบอร์โทรศัพท์",
		              "สถานะ",
		              "รหัสพนักงาน",
		              "ชื่อพนักงาน",
		              "เลขที่ใบอนุญาตนายหน้าประกันชีวิต",
		              "เลขที่ใบอนุญาตผู้แนะนําการลงทุน ",
		              "สาขา",
		              "แบบประกัน",
		              "plancode",  //Hidden
		              "เบี้ยประกัน",
		              "SumInsured" ,  // Hidden,
		              //"<spring:message code='kkDocument.lbl7'/>",
		              //"<spring:message code='kkDocument.lbl8'/>",
		              //"<spring:message code='kkDocument.lbl9'/>",
		              
		              "age",   // Hidden
		              "gender",   // Hidden
		              "Note",   // Hidden
		              "noteFromBranch",   // Hidden
		              "q1",   // Hidden
		              "q2",   // Hidden
		              "q3",   // Hidden
		              "q4",   // Hidden
		              "q5",   // Hidden
		              "q6",   // Hidden
		              "q7",   // Hidden
		              "q8",   // Hidden
		              "q9",   // Hidden
		              "q10",   // Hidden
		              "frontEndFee",   // Hidden
		              "coiFee",   // Hidden
		              "sisno",   // Hidden
		              "AppNo",   
		              "KKRefNo", // Hidden  
		              "percentSelectList",   // Hidden
		              "riskSelectList",   // Hidden
		              "เบี้ยประกัน",
		              "firstReviewTimestamp", // HIDDEN
		              "sisDate", // HIDDEN
		              "multipleSA" //HIDDEN
		              ],
		    colModel :[ 
		      {name:'id',   index:'id',   width:5, sortable: true, sorttype:'int', hidden : true }, 
		      {name:'createdDate',  	index:'createdDate',		width:15,	sorttype: 'date',datefmt: 'd/m/yyyy', sortable: true,	editable : false },
		      {name:'customerName',		index:'customerName',		width:25,	sortable: true, editable : false },
		      //{name:'productType',  	index:'productType',		width:10,	sortable: true,	editable : false ,stype: 'select', searchoptions:{ dataInit: function(elem) {$(elem).height(20) } ,sopt:['eq'], value: ":All;OL:OL;NL:NL;UL:UL" }},//, searchoptions: { defaultValue: "OL" }},
		      {name:'mobileNo',  		index:'mobileNo',			width:15,	sortable: true,	editable : false },
		      {name:'status',  	index:'status',		width:15,	sortable: true,	editable : false ,stype: 'select', searchoptions:{ dataInit: function(elem) {$(elem).height(20) } ,sopt:['eq'], value: ":All;ยืนยันข้อมูล:ยืนยันข้อมูล;รอตัดสินใจ:รอตัดสินใจ;ลูกค้ายกเลิก:ลูกค้ายกเลิก;New:New" }},//, searchoptions: { defaultValue: "OL" }},
		      {name:'agentCode',  		index:'agentCode',			width:15,	sortable: true,	editable : false },
		      {name:'agentName',  		index:'agentName',			width:20,	sortable: true,	editable : false },
		      {name:'agentLicenseCode',		index:'agentLicenseCode',			width:15,	sortable: true,	editable : false , hidden : true },
		      {name:'agentConsultantCode',	index:'agentConsultantCode',		width:15,	sortable: true,	editable : false , hidden : true },
		      
		      {name:'branchName',  		index:'branchName',			width:20,	sortable: true,	editable : false },
		      {name:'planName',index:'planName',	width:20,	sortable: true,	editable : false },
		      {name:'planCode',index:'planCode',	width:20,	sortable: true,	editable : false, hidden : true },
		      {name:'premium', index:'premium',	width:18,	sortable: true,	sorttype:'int' ,editable : false },
		      {name:'sumInsured', index:'sumInsured',	width:18,	sortable: true,	sorttype:'int' ,editable : false, hidden : true },
		      //{name:'customerName',	index:'customerName',		width:35,	sortable: true, editable : false },
		      
		      {name:'age',		index:'age',			width:10,	sortable: true, editable : false , hidden : true},
		      {name:'gender',		index:'gender',			width:10,	sortable: true, editable : false , hidden : true},
		      
		      {name:'kkNote',		index:'kkNote',			width:10,	sortable: true, editable : false , hidden : true},
		      {name:'noteFromBranch',		index:'noteFromBranch',			width:10,	sortable: true, editable : false , hidden : true},
		      {name:'q1',		index:'q1',			width:10,	sortable: true, editable : false , hidden : true},
		      {name:'q2',		index:'q2',			width:10,	sortable: true, editable : false , hidden : true},
		      {name:'q3',		index:'q3',			width:10,	sortable: true, editable : false , hidden : true},
		      {name:'q4',		index:'q4',			width:10,	sortable: true, editable : false , hidden : true},
		      {name:'q5',		index:'q5',			width:10,	sortable: true, editable : false , hidden : true},
		      {name:'q6',		index:'q6',			width:10,	sortable: true, editable : false , hidden : true},
		      {name:'q7',		index:'q7',			width:10,	sortable: true, editable : false , hidden : true},
		      {name:'q8',		index:'q8',			width:10,	sortable: true, editable : false , hidden : true},
		      {name:'q9',		index:'q9',			width:10,	sortable: true, editable : false , hidden : true},
		      {name:'q10',		index:'q10',			width:10,	sortable: true, editable : false , hidden : true},
		      {name:'frontEndFee',		index:'frontEndFee',			width:20,	sortable: true, editable : false , hidden : true  },
		      {name:'coiFee',		index:'coiFee',			width:20,	sortable: true, editable : false , hidden : true },
		      {name:'sisno',		index:'sisno',			width:20,	sortable: true, editable : false , hidden : true },
		      {name:'appNo',		index:'appNo',			width:30,	sortable: true, editable : false },
		      {name:'kkRefNo',		index:'kkRefNo',	    width:20,	sortable: true, editable : false , hidden : true },
		      {name:'fundSelectList',		index:'fundSelectList',			width:20,	sortable: true, editable : false , hidden : true },
		      {name:'percentSelectList',		index:'percentSelectList',			width:20,	sortable: true, editable : false , hidden : true },
		      {name:'riskSelectList',		index:'riskSelectList',			width:20,	sortable: true, editable : false , hidden : true },
		      {name:'firstReviewTimestamp',index:'firstReviewTimestamp',width:15,	sortable: true, editable : false, hidden : true  }	,
		      //{name:'appCuStatus',		index:'appCuStatus',			width:65,	sortable: true, editable : false },	
		      //{name:'status',		index:'status',			width:65,	sortable: true, editable : false },
		      {name:'sisDate',	index:'sisDate',width:15,	sortable: true, editable : false, hidden : true  }	,
		      {name:'multipleSA',	index:'multipleSA',width:15,	sortable: true, editable : false, hidden : true  }	,
		      
		    ],
		    pager: '#pager',
		    rowNum: 15,
		    height : 340,
			width : 850,
			autoencode: false,
			//sortname: 'id',
			autowidth: false,
			rownumbers: true,
		   // sortorder: "desc",
			gridview : true,
		    viewrecords: true,
		    loadonce: true,
		    loadui: 'disable',
		    caption: 'KKP UL Callback.',
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
			caption:"Action",
			title:"Select document for action.",
			position : "first",
			buttonicon :'ui-icon-folder-open',
			onClickButton:function(){
				 
				var row = grid.jqGrid('getGridParam', 'selrow');
				if (row != null) {
					
					var _id          	= grid.jqGrid ('getCell', row, 'id');
					var idHidden = " <input type='hidden' id='myId' value='"+_id+"' /> ";
					
					var _createdDate	= grid.jqGrid ('getCell', row, 'createdDate');
					var _customerName	= grid.jqGrid ('getCell', row, 'customerName');
					var _mobileNo       = grid.jqGrid ('getCell', row, 'mobileNo');
					var _status 		= grid.jqGrid ('getCell', row, 'status');
					var _agentCode 	 	= grid.jqGrid ('getCell', row, 'agentCode');
					var _agentName  	= grid.jqGrid ('getCell', row, 'agentName');
					var _agentLicenseCode  	= grid.jqGrid ('getCell', row, 'agentLicenseCode');
					var _agentConsultantCode  	= grid.jqGrid ('getCell', row, 'agentConsultantCode');
					
					var _branchName    	= grid.jqGrid ('getCell', row, 'branchName');	
					var _planCode  		= grid.jqGrid ('getCell', row, 'planCode');		
					var _planName  		= grid.jqGrid ('getCell', row, 'planName');	
					var _premium 		= grid.jqGrid ('getCell', row, 'premium');	
					var _sumInsured 		= grid.jqGrid ('getCell', row, 'sumInsured');			

					var _sisno 			= grid.jqGrid ('getCell', row, 'sisno');
					var _kkNote 		= grid.jqGrid ('getCell', row, 'kkNote');
					var _noteFromBranch = grid.jqGrid ('getCell', row, 'noteFromBranch');
					var _age 			= grid.jqGrid ('getCell', row, 'age');
					var _gender 		= grid.jqGrid ('getCell', row, 'gender');	
					var _frontEndFee 	= grid.jqGrid ('getCell', row, 'frontEndFee');	
					var _coiFee 		= grid.jqGrid ('getCell', row, 'coiFee');	 
					 
					var _firstReviewTimestamp 		= grid.jqGrid ('getCell', row, 'firstReviewTimestamp');	 
					
					var _q1 		= grid.jqGrid ('getCell', row, 'q1');	
					var _q2 		= grid.jqGrid ('getCell', row, 'q2');
					var _q3 		= grid.jqGrid ('getCell', row, 'q3');
					var _q4 		= grid.jqGrid ('getCell', row, 'q4');
					var _q5 		= grid.jqGrid ('getCell', row, 'q5');
					var _q6 		= grid.jqGrid ('getCell', row, 'q6');
					var _q7 		= grid.jqGrid ('getCell', row, 'q7');
					//var _q8 		= grid.jqGrid ('getCell', row, 'q8');
					//var _q9 		= grid.jqGrid ('getCell', row, 'q9');
					//var _q10 		= grid.jqGrid ('getCell', row, 'q10'); 
					
					var _sisDate 	= grid.jqGrid ('getCell', row, 'sisDate');	 
					var _multipleSA	= grid.jqGrid ('getCell', row, 'multipleSA');
					
					var sendData = { 
						id:_id
	                }
					
					$("#createdDate").html(_createdDate);
					$("#customerName").html(_customerName);
					$("#mobileNo").html(_mobileNo);
					$("#agentCode").html(_agentCode);
					$("#agentName").html(_agentName);
					$("#agentLicenseCode").html(_agentLicenseCode);
					$("#agentConsultantCode").html(_agentConsultantCode);
					$("#branchName").html(_branchName);
					//$("#planCode").html(_planCode);
					$("#planCode").html(_planName);
					$("#customerAge").html(_age + " ปี");
					$("#premium").html(_premium.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,") + " บาท");
					$("#sumInsured").html(_sumInsured.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,") + " บาท");
					$("#myNote").val(_kkNote);
					$("#myNoteFromBranch").val(_noteFromBranch);
					$("#coiFee").html(_coiFee.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,"));
					$("#frontEndFee").html(_frontEndFee.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,") + " บาท ");
					 
					$("#firstReview").html(_firstReviewTimestamp);
					
					$('input[id=radios_01d'+_q1).attr('checked','checked');
					$('input[id=radios_02d'+_q2).attr('checked','checked');
					$('input[id=radios_03d'+_q3).attr('checked','checked');
					$('input[id=radios_04d'+_q4).attr('checked','checked');
					$('input[id=radios_05d'+_q5).attr('checked','checked');
					$('input[id=radios_06d'+_q6).attr('checked','checked');
					$('input[id=radios_07d'+_q7).attr('checked','checked');
					//$('input[id=radios_08d'+_q8).attr('checked','checked');
					//$('input[id=radios_09d'+_q9).attr('checked','checked');
					//$('input[id=radios_010d'+_q10).attr('checked','checked');
					
					$("#sisDate").html(_sisDate);
					
					var paramObj = {  sisno :_sisno };
					
					var getFundListResult = $.ajax({
				    	url: "KK_UL_Callback/getFundList.html",
				        type: "POST",
				        data: JSON.stringify(paramObj),
				        async:false, 
				        dataType: "json",
				        contentType: "application/json; charset=utf-8",
				        beforeSend: function(data) {
				        	
				        },
				        success: function(response) {
				        	 
				        }
					}).responseText;
					$("#fundList").html( idHidden + "<table id='fundListTable' class='table'><thead><tr><th>ชื่อกองทุน</th><th>ร้อยละ</th><th>ความเสี่ยง</th></tr><thead><tbody>" + getFundListResult + "</tbody></table>" );
			 
					$("#inputPolicyDiv").show();
					$("#kkDocumentDiv").hide();
					$("#searchBillDiv").hide();
					console.log("=========>"+ _planCode);
					if( _planCode == "UL04" ){
						$(".hideFrontEndFeeForUL04").hide();
					}else{
						$(".hideFrontEndFeeForUL04").show();
					}
					
					if (_planCode == 'UR07'){
						$("#multipleLabel").show();
						$("#multipleSA").html( parseInt(_multipleSA) + "&nbsp;<B>เท่า</B>");
					}else{
						$("#multipleLabel").hide();
					}
					
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
		
		$("#btnSearchCallback").button().click(function() {
			//var custNameSearch = $("#custNameSearch").val();
			var searchStatus = $("#searchStatus").val();
			//console.log("====>"+searchStatus);
			window.location.replace("KK_UL_Callback.html?custNameSearch="+custNameSearch+"&searchStatus="+searchStatus);
		});	
		
		$("#btnPrintDoc").button().click(function() {
			
			if(!$("#exportStartDate").parsley().isValid()) {
				alert("รูปแบบวันที่ from ผิด");
				return;
			}
			if(!$("#exportEndDate").parsley().isValid()) {
				alert("รูปแบบวันที่ to ผิด");
				return;
			}
			if($("#exportEndDate").val().trim() != "" && $("#exportStartDate").val().trim() == "") {
				alert("กรุณากรอกวันที่ from");
				return;
			}
			
			var _url = '';
			 
			_url = '<%=request.getContextPath()%>/exportCallbackReport/exportCallbackReport.html';
			
			var startExportDate = $("#exportStartDate").val();
			var endExportDate = $("#exportEndDate").val();
			var urlParams = [];
			if(startExportDate) {
				urlParams.push("dateFrom="+startExportDate);
			}
			if(endExportDate) {
				urlParams.push("dateTo="+endExportDate);
			}
			if(urlParams.length > 0) {
				_url += "?"+urlParams.join("&");
			}
			window.location.href = _url;
			
		});	
		
		$("#btnNextTab").button().click(function() {
			$('a[href="#tabs-1"]').click();
		});
		
		function validateRadioCheck(action){
			$('tr#FormError').hide();
			/* User Requirement มี 10 คำถาม ไม่มีการเพิ่มลดคำถาม ณ.ตอนที่ให้ Requirement */
			/* ตอนนี้เปลี่ยน Requirement มี 7 คำถาม แล้ววววว  */

			//for(i = 1; i < 11; i++){
			for(i = 1; i < 8; i++){
				if( $('input[name=radios_0'+i+'d]:checked', '#myForm').val() === undefined ){
					$('tr#FormError').show();
					return false;
				}
				
				/* ทีแรกว่าจะเช็ค ห้ามเลือก No ถ้ากดปุ่ม ยืนยันข้อมูล ตอนนี้เอาออกแล้ว....2018-06-05 */
				/*
				if( action === 'confirm' && $('input[name=radios_0'+i+'d]:checked', '#myForm').val() === "2" ){
					$('tr#FormError').show();
					return false;
				}
				*/
						
			}
			return true;
			
		}
		
		$("#btnCallbackSuccess").button().click(function() {
			var _id      = $("#myId").val();
			var _myNote  = $("#myNote").val();
			var _myNoteFromBranch  = $("#myNoteFromBranch").val();
			
			var i = 1;
			var _q1  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			var _q2  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			var _q3  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			var _q4  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			var _q5  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			var _q6  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			var _q7  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			//var _q8  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			//var _q9  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			//var _q10  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			
			var paramObj = { id: _id , status:"Y", kkNote: _myNote, noteFromBranch: _myNoteFromBranch,
					q1:_q1, q2:_q2, q3:_q3, q4:_q4, q5:_q5, q6:_q6, q7:_q7
					//, q8:_q8, q9:_q9, q10:_q10  
			};
			
			var confirmCb = $.ajax({
		    	url: "KK_UL_Callback/userConfirm.html",
		        type: "POST",
		        data: JSON.stringify(paramObj),
		        async:false, 
		        //dataType: "json",
		        contentType: "application/json; charset=utf-8",
		        beforeSend: function(data) {
		        	var result = validateRadioCheck("confirm");
		        
		        	return result;
		        },
		        success: function(response) {
					$("#responseMessage").html('ยืนยันข้อมูล เรียบร้อยแล้ว');
					$("#dialog-form").dialog("open");
				    //e.preventDefault();
				    console.log($("#dialog-form"));
		        }
			}).responseText;

		    
		});

		$("#btnCallbackWait").button().click(function() {
			 
			var _id          	= $("#myId").val();
			var _myNote  = $("#myNote").val();
			var _myNoteFromBranch  = $("#myNoteFromBranch").val();
			
			var i = 1;
			var _q1  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			var _q2  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			var _q3  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			var _q4  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			var _q5  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			var _q6  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			var _q7  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			//var _q8  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			//var _q9  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			//var _q10  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			
			var paramObj = { id: _id , status:"W", kkNote: _myNote, noteFromBranch: _myNoteFromBranch,
					q1:_q1, q2:_q2, q3:_q3, q4:_q4, q5:_q5, q6:_q6, q7:_q7
					//, q8:_q8, q9:_q9, q10:_q10  
			};
			
			var confirmCb = $.ajax({
		    	url: "KK_UL_Callback/userConfirm.html",
		        type: "POST",
		        data: JSON.stringify(paramObj),
		        async:false, 
		        //dataType: "json",
		        contentType: "application/json; charset=utf-8",
		        beforeSend: function(data) {
		        	var result = validateRadioCheck("wait");
		        	//alert(result);
		        	return result;
		        },
		        success: function(response) {
		        	$("#responseMessage").html('เปลี่ยนสถานะเป็น "ลูกค้ารอตัดสินใจ" เรียบร้อยแล้ว');
					$("#dialog-form").dialog("open");    
				    //e.preventDefault();	 
		        }
			}).responseText;

			 
		});	
		$("#btnCallbackReject").button().click(function() {
			 
			var _id          	= $("#myId").val();
			var _myNote  = $("#myNote").val();
			var _myNoteFromBranch  = $("#myNoteFromBranch").val();
			
			var i = 1;
			var _q1  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			var _q2  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			var _q3  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			var _q4  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			var _q5  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			var _q6  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			var _q7  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			//var _q8  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			//var _q9  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			//var _q10  = $('input[name=radios_0'+i+'d]:checked', '#myForm').val(); i++;
			
			var paramObj = { id: _id , status:"R", kkNote: _myNote, noteFromBranch: _myNoteFromBranch ,
					q1:_q1, q2:_q2, q3:_q3, q4:_q4, q5:_q5, q6:_q6, q7:_q7
					//, q8:_q8, q9:_q9, q10:_q10 
			};
			
			var confirmCb = $.ajax({
		    	url: "KK_UL_Callback/userConfirm.html",
		        type: "POST",
		        data: JSON.stringify(paramObj),
		        async:false, 
		       // dataType: "json",
		        contentType: "application/json; charset=utf-8",
		        beforeSend: function(data) {
		        	var result = validateRadioCheck("reject");
		        	//alert(result);
		        	return result;
		        },
		        success: function(response) {
		        	$("#responseMessage").html('ยกเลิกข้อมูล เรียบร้อยแล้ว');
					$("#dialog-form").dialog("open");
				    //e.preventDefault();
		        }
			}).responseText;

			
			 
			 
		});	
		
		/*
		$("#inputPolicyDiv").accordion({
			autoHeight: false,
			navigation: true
		});
		*/
		
		$("#btnReject").button().click(function() {
			var rejectType = $("#rejectType").val();
			if( rejectType == "" ){
				$("#alertMessage").html('กรุณาเลือกประเภทการปฏิเสธเอกสาร');
				$("#dialog-alert").dialog("open");
			    e.preventDefault();
			}
			
			var row = grid.jqGrid('getGridParam', 'selrow');
			if (row != null) {
				var _appno = $.trim($("input#updateKey").val());
				var policyno = $.trim($("input#policyNumberTxt").val());
				var _agentName = grid.jqGrid ('getCell', row, 'agentName');	
				var _branchStr = grid.jqGrid ('getCell', row, 'branchStr');		
				var _InsureName = grid.jqGrid ('getCell', row, 'customerFirstName') + " " + grid.jqGrid ('getCell', row, 'customerLastname');		
				var _email = grid.jqGrid ('getCell', row, 'email');		

				var _id = grid.jqGrid ('getCell', row, 'id');	
				
				//alert(_agentName + "/" + _branchStr + "/" + _InsureName)
				
				var obj = { appNo : _appno, policyNo : policyno, agentName : _agentName,
							branchStr : _branchStr, customerFirstName : _InsureName, staffEmail : _email, status : rejectType,
							id:_id
						  };
				 
				
				var sendRejectByAppnoResult = $.ajax({
			    	url: "RejectByAppno/sendRejectByAppno.html",
			        type: "POST",
			        data: JSON.stringify(obj),
			        async:false, 
			      //  dataType: "json",
			        contentType: "application/json; charset=utf-8",
			        beforeSend: function(data) {
			        	
			        },
			        success: function(response) {
			        	/*
			        	$("#float_banner").hide();
			        	$("#menu").show();
			        	$("#responseMessage").html("Update Document Log Already.");
						$("#dialog-form").dialog("open");
					    e.preventDefault();
					    */
			        }
				}).responseText;
				
				if(sendRejectByAppnoResult == "success"){
					$("#responseMessage").html('ปฏิเสธ ('+rejectType+')เอกสารหมายเลข AppNo '+ _appno +' เรียบร้อยแล้ว');
					$("#dialog-form").dialog("open");
				    e.preventDefault();
				}else if(sendRejectByAppnoResult == "success001"){
					$("#responseMessage").html('เอกสารหมายเลข AppNo '+ _appno +' เรียบร้อยแล้ว');
					$("#dialog-form").dialog("open");
				    e.preventDefault();
				}else if(sendRejectByAppnoResult == "error001"){
					// error001 : Save document when status 'Not Receieved' all object
					$("#responseMessage").html("Error: Cannot insert webservice data.");
					$("#dialog-form").dialog("open");
				    e.preventDefault();
				}else if(sendRejectByAppnoResult == "error002"){
					$("#responseMessage").html("Error: Send Email Error.");
					$("#dialog-form").dialog("open");
				    e.preventDefault();	
				}else{
					$("#responseMessage").html("Error: "+sendRejectByAppnoResult);
					$("#dialog-form").dialog("open");
				    e.preventDefault();
				}
				
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
		width:150px;
	}
	.docTextBox{
		width:180px;
	}  
	#custDetail,#searchBillDiv{
		margin-left:20px;
		width:750px;
		/*border:1px solid #FF0000;*/
	}
	.myButtonCenter{
		text-align: center;
		/*border: 3px solid green;*/
	}
	
	.right {
	  float: right;
	}
	.docDateTextBox{
		width:100px;
	}  
	  
	  
</style>
</head>
<body>
	
	<div id="searchBillDiv">
	 <!-- 
		<input class="docStatusCombobox" type="text" id="custNameSearch" name="custNameSearch" value="" placeholder="Input Customer Name"/>
	 --> 
		<select class="docStatusCombobox" id="searchStatus" name="searchStatus">
			<option value="">All Status</option>
			<option value="Y">ยืนยันข้อมูล</option>
			<option value="W">รอตัดสินใจ</option>
			<option value="R">ลูกค้ายกเลิก</option>
		</select>
		
		<input type="hidden" id="max" name="max" value="10"/>
		<input type="hidden" id="page" name="page" value="1"/>
		<input type="button" id="btnSearchCallback" value="Search"> 
		
		<div class="right">
		Approved Date From:
		<input class="docDateTextBox" type="text" id="exportStartDate" name="exportStartDate" value="" pattern="^([1-9]|0[1-9]|1[0-9]|2[0-9]|3[0-1])\/(0[1-9]|1[0-2])\/\d{4}$" />
		To:
		<input class="docDateTextBox" type="text" id="exportEndDate" name="exportEndDate" value="" pattern="^([1-9]|0[1-9]|1[0-9]|2[0-9]|3[0-1])\/(0[1-9]|1[0-2])\/\d{4}$" />
		<input type="button" id="btnPrintDoc" value="Export Report">
		</div>
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
	

 <div id="inputPolicyDiv" style="display:none; margin-left: 50px; margin-top: -200px; width: 720px; height: 300px">

    <div id="tabs">
        <ul>
            <li>
                <a href="#tabs-2">CallBack</a>
            </li>
            <li>
                <a href="#tabs-1">Question</a>
            </li>
        </ul>
        <div id="tabs-1">
            <div>
            <form id="myForm">
            
				<table>
				    <tr id="FormError" style="display: none;">
				        <td class="ui-state-error" colspan="3">กรุณาตอบคำถามให้ครบ</td>
				    </tr>
				    <tr>
				        <td>
				            <B>1) </B>
				        </td>
				        <td>&nbsp;&nbsp;</td>
				        <td>
				            <div id="cl_q1">Q1.</div>
				        </td>
				    </tr>
				    <tr>
				        <td colspan="2">&nbsp;</td>
				        <td>
				            <div id="cl_q1">
				            	<input type="radio" name="radios_01d" id="radios_01d1" value="1">&nbsp;Yes &nbsp;&nbsp;
				            	<input type="radio" name="radios_01d" id="radios_01d2" value="2">&nbsp;No &nbsp;&nbsp;
				            	<input type="radio" name="radios_01d" id="radios_01d3" value="3">&nbsp;None
				            </div>
				        </td>
				    </tr>
<!-- ********************************************************************************************************************************** -->
				    <tr>
				        <td>
				            <B>2) </B>
				        </td>
				        <td>&nbsp;&nbsp;</td>
				        <td>
				            <div id="cl_q2">Q2.</div>
				        </td>
				    </tr>
				    <tr>
				        <td colspan="2">&nbsp;</td>
				        <td>
				            <div id="cl_q2">
				            	<input type="radio" name="radios_02d" id="radios_02d1" value="1">&nbsp;Yes &nbsp;&nbsp;
				            	<input type="radio" name="radios_02d" id="radios_02d2" value="2">&nbsp;No &nbsp;&nbsp;
				            	<input type="radio" name="radios_02d" id="radios_02d3" value="3">&nbsp;None
				            </div>
				        </td>
				    </tr>
<!-- ********************************************************************************************************************************** -->				    
				    <tr>
				        <td>
				            <B>3) </B>
				        </td>
				        <td>&nbsp;&nbsp;</td>
				        <td>
				            <div id="cl_q3">Q3.</div>
				        </td>
				    </tr>
				    <tr>
				        <td colspan="2">&nbsp;</td>
				        <td>
				            <div id="cl_q3">
				            	<input type="radio" name="radios_03d" id="radios_03d1" value="1">&nbsp;Yes &nbsp;&nbsp;
				            	<input type="radio" name="radios_03d" id="radios_03d2" value="2">&nbsp;No &nbsp;&nbsp;
				            	<input type="radio" name="radios_03d" id="radios_03d3" value="3">&nbsp;None
				            </div>
				        </td>
				    </tr>
<!-- ********************************************************************************************************************************** -->				    
				    <tr>
				        <td>
				            <B>4) </B>
				        </td>
				        <td>&nbsp;&nbsp;</td>
				        <td>
				            <div id="cl_q4">Q4.</div>
				        </td>
				    </tr>
				    <tr>
				        <td colspan="2">&nbsp;</td>
				        <td>
				            <div id="cl_q4">
				            	<input type="radio" name="radios_04d" id="radios_04d1" value="1">&nbsp;Yes &nbsp;&nbsp;
				            	<input type="radio" name="radios_04d" id="radios_04d2" value="2">&nbsp;No &nbsp;&nbsp;
				            	<input type="radio" name="radios_04d" id="radios_04d3" value="3">&nbsp;None
				            </div>
				        </td>
				    </tr>
<!-- ********************************************************************************************************************************** -->		    
				    <tr>
				        <td>
				            <B>5) </B>
				        </td>
				        <td>&nbsp;&nbsp;</td>
				        <td>
				            <div id="cl_q5">Q5.</div>
				        </td>
				    </tr>
				    <tr>
				        <td colspan="2">&nbsp;</td>
				        <td>
				            <div id="cl_q5">
				            	<input type="radio" name="radios_05d" id="radios_05d1" value="1">&nbsp;Yes &nbsp;&nbsp;
				            	<input type="radio" name="radios_05d" id="radios_05d2" value="2">&nbsp;No &nbsp;&nbsp;
				            	<input type="radio" name="radios_05d" id="radios_05d3" value="3">&nbsp;None
				            </div>
				        </td>
				    </tr>
<!-- ********************************************************************************************************************************** -->				    
				    <tr>
				        <td>
				            <B>6) </B>
				        </td>
				        <td>&nbsp;&nbsp;</td>
				        <td>
				            <div id="cl_q6">Q6.</div>
				        </td>
				    </tr>
				    <tr>
				        <td colspan="2">&nbsp;</td>
				        <td>
				            <div id="cl_q6">
				            	<input type="radio" name="radios_06d" id="radios_06d1" value="1">&nbsp;Yes &nbsp;&nbsp;
				            	<input type="radio" name="radios_06d" id="radios_06d2" value="2">&nbsp;No &nbsp;&nbsp;
				            	<input type="radio" name="radios_06d" id="radios_06d3" value="3">&nbsp;None
				            </div>
				        </td>
				    </tr>
<!-- ********************************************************************************************************************************** -->				    
				    <tr>
				        <td>
				            <B>7) </B>
				        </td>
				        <td>&nbsp;&nbsp;</td>
				        <td>
				            <div id="cl_q7">Q7.</div>
				        </td>
				    </tr>
				    <tr>
				        <td colspan="2">&nbsp;</td>
				        <td>
				            <div id="cl_q7">
				            	<input type="radio" name="radios_07d" id="radios_07d1" value="1">&nbsp;Yes &nbsp;&nbsp;
				            	<input type="radio" name="radios_07d" id="radios_07d2" value="2">&nbsp;No &nbsp;&nbsp;
				            	<input type="radio" name="radios_07d" id="radios_07d3" value="3">&nbsp;None
				            </div>
				        </td>
				    </tr>
<!-- ********************************************************************************************************************************** -->				
<!--    
				    <tr>
				        <td>
				            <B>8) </B>
				        </td>
				        <td>&nbsp;&nbsp;</td>
				        <td>
				            <div id="cl_q8">Q8.</div>
				        </td>
				    </tr>
				    <tr>
				        <td colspan="2">&nbsp;</td>
				        <td>
				            <div id="cl_q8">
				            	<input type="radio" name="radios_08d" id="radios_08d1" value="1">&nbsp;Yes &nbsp;&nbsp;
				            	<input type="radio" name="radios_08d" id="radios_08d2" value="2">&nbsp;No &nbsp;&nbsp;
				            	<input type="radio" name="radios_08d" id="radios_08d3" value="3">&nbsp;None
				            </div>
				        </td>
				    </tr>
-->					    
<!-- ********************************************************************************************************************************** -->
<!--				    
				    <tr>
				        <td>
				            <B>9) </B>
				        </td>
				        <td>&nbsp;&nbsp;</td>
				        <td>
				            <div id="cl_q9">Q9.</div>
				        </td>
				    </tr>
				    <tr>
				        <td colspan="2">&nbsp;</td>
				        <td>
				            <div id="cl_q9">
				            	<input type="radio" name="radios_09d" id="radios_09d1" value="1">&nbsp;Yes &nbsp;&nbsp;
				            	<input type="radio" name="radios_09d" id="radios_09d2" value="2">&nbsp;No &nbsp;&nbsp;
				            	<input type="radio" name="radios_09d" id="radios_09d3" value="3">&nbsp;None
				            </div>
				        </td>
				    </tr>
-->					    
<!-- ********************************************************************************************************************************** -->
<!--				    
				    <tr>
				        <td>
				            <B>10) </B>
				        </td>
				        <td>&nbsp;&nbsp;</td>
				        <td>
				            <div id="cl_q10">Q10.</div>
				        </td>
				    </tr>
				    <tr>
				        <td colspan="2">&nbsp;</td>
				        <td>
				            <div id="cl_q10">
				            	<input type="radio" name="radios_010d" id="radios_010d1" value="1">&nbsp;Yes &nbsp;&nbsp;
				            	<input type="radio" name="radios_010d" id="radios_010d2" value="2">&nbsp;No &nbsp;&nbsp;
				            	<input type="radio" name="radios_010d" id="radios_010d3" value="3">&nbsp;None
				            </div>
				        </td>
				    </tr>
-->					    
<!-- ********************************************************************************************************************************** -->	    
				    <tr>
				        <td colspan=3 " align="center ">
						    <br>
						   
					    </td>
				    </tr>
				</table>
			</form>				
            </div>
            <div class="myButtonCenter" >
				<button id="btnCallbackSuccess">ยืนยันข้อมูล</button>
			    <button id="btnCallbackWait">รอตัดสินใจ</button>
			    <button id="btnCallbackReject">ลูกค้ายกเลิก</button>
			</div>
        </div>
        <div id="tabs-2">
            <div id="criteriaDialog">
                <fieldset>
                    <div id="resetBox">
                        <div id="resetMessage"></div>
                        <div>
                            <input type="hidden" id="updateKey" name="updateKey" value="" />
                            <table>
                                <tr id="FormError" style="display: none;">
                                    <td class="ui-state-error" colspan="7"></td>
                                </tr>
                                <tr>
                                    <td>
                                        <B>วันที่ : </B>
                                    </td>
                                    <td colspan="2">
                                        <div id="createdDate"></div>
                                    </td>
                                    <td width="30px">&nbsp;</td>
                                    <td>
                                        <B>รหัสพนักงาน : </B>
                                    </td>
                                    <td colspan="2">
                                        <div id="agentCode"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <B>ชื่อลูกค้า : </B>
                                    </td>
                                    <td colspan="2">
                                        <div id="customerName"></div>
                                    </td>
                                    <td>&nbsp;</td>                                    
                                    <td>
                                        <B>ชื่อนามสกุล พนักงาน : </B>
                                    </td>
                                    <td colspan="2">
                                        <div id="agentName"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <B>อายุ : </B>
                                    </td>
                                    <td colspan="2">
                                        <div id="customerAge"></div>
                                    </td>
                                    <td>&nbsp;</td>                                    
                                    <td>
                                        <B>เลขที่ใบอนุญาตนายหน้าประกันชีวิต : </B>
                                    </td>
                                    <td colspan="2">
                                        <div id="agentLicenseCode"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <B>&nbsp;</B>
                                    </td>
                                    <td colspan="2">
                                        <div id=""></div>
                                    </td>
                                    <td>&nbsp;</td>                                    
                                    <td>
                                        <B>เลขที่ใบอนุญาตผู้แนะนําการลงทุน : </B>
                                    </td>
                                    <td colspan="2">
                                        <div id="agentConsultantCode"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <B>เบอร์โทรศัพท์ : </B>
                                    </td>
                                    <td colspan="2">
                                        <div id="mobileNo"></div>
                                    </td>
                                    <td>&nbsp;</td>                                    
                                    <td>
                                        <B>สาขา : </B>
                                    </td>
                                    <td colspan="2">
                                        <div id="branchName"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <B>แบบประกัน : </B>
                                    </td>
                                    <td colspan="2">
                                        <div id="planCode"></div>
                                    </td>                                     
                                    <td>&nbsp;</td>                                    
                                    <td>
                                        <B>วันที่ Callback ครั้งแรก : </B>
                                    </td>
                                    <td colspan="2">
                                        <div id="firstReview"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <B>เบี้ยประกัน : </B>
                                    </td>
                                    <td colspan="2">
                                        <div id="premium"></div>
                                    </td>
                                    <td>&nbsp;</td>
                                    <td>
                                       <B>ทุนประกัน : </B>
                                    </td>
                                    <td colspan="2">
                                        <div id="sumInsured"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <B>วันที่ขาย : </B>
                                    </td>
                                    <td colspan="2">
                                        <div id="sisDate"></div>
                                    </td>
                                    <td>&nbsp;</td>
                                    <td>
                                       <div id="multipleLabel"><B>จำนวนเท่าความคุ้มครอง</B></div>
                                    </td>
                                    <td colspan="2">
                                        <div id="multipleSA"></div>
                                        
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <B>กองทุน : </B>
                                    </td>
                                    <td colspan="6">
                                        <div id="fundList"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="hideFrontEndFeeForUL04">
                                        <B>ค่าธรรมเนียม Front End : </B>
                                    </td>
                                    <td class="hideFrontEndFeeForUL04" colspan="2">
                                        <div id="frontEndFee"></div>
                                    </td>
                                    <td class="hideFrontEndFeeForUL04" >&nbsp;</td>
                                    <td>
                                        <B>ค่าธรรมเนียม COI : </B>
                                    </td>
                                    <td colspan="2">
                                        <div id="coiFee"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="7">
                                        <B>Note : </B>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="7">
                                        <textarea id="myNote" style="width:600px; height:40px;">
                                    
                                        </textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="7">
                                        <B>ผลตอบกลับจากสาขา : </B>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="7">
                                        <textarea id="myNoteFromBranch" style="width:600px; height:40px;">
                                    
                                        </textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="7" align="right">
                                        <br>
                                        <button id="btnNextTab">ถัดไป</button> 
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </fieldset>
            </div>
        </div>
    </div>
</div>
	
</body>
</html>