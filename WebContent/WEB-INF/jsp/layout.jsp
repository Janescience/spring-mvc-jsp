<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="KKDC" var="baseUrl"/>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="<c:url value='/images/gt.ico' />" />
<link rel="icon" href="<c:url value='/images/gt.gif' />" type="image/gif" />
<style type="text/css" media="screen">
	@import url("<c:url value='/styles/styles.css' />");
	@import url("<c:url value='/styles/ui.jqgrid.css' />");
	@import url("<c:url value='/styles/menuStyle.css' />");
	
	@import url("<c:url value='/script/bootstrap/bootstrap.css' />");
	@import url("<c:url value='/script/farbtastic/farbtastic.css' />");
	@import url("<c:url value='/styles/freshereditor.css' />"); 
	
	@import url("<c:url value='/styles/gt-theme/jquery-ui.1.11.0.css' />");
	
	@import url("<c:url value='/styles/lightbox.css' />");	
	
	@import url("<c:url value='/styles/keyboard.css' />");

/* remove to drop table button of table header */
.ui-jqgrid .ui-jqgrid-titlebar {
	position: static;
}

.ui-widget {
	font-size: 12px;
}

.uppercase {
    text-transform: uppercase;
}

.custom-dialog .ui-widget {
	width: 240px; 
	height: auto; 
	overflow-x: hidden; 
	overflow-y: hidden;
	z-index: 950; 
	position: absolute; 
	padding: 0em;
	vertical-align: middle;
	text-align: center;
	display: table;
}

/* css for timepicker */
.ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }
.ui-timepicker-div dl { text-align: left; }
.ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; }
.ui-timepicker-div dl dd { margin: 0 10px 10px 65px; }
.ui-timepicker-div td { font-size: 90%; }
.ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; }

.ui-timepicker-rtl{ direction: rtl; }
.ui-timepicker-rtl dl { text-align: right; }
.ui-timepicker-rtl dl dd { margin: 0 65px 10px 10px; }
/* 
.ui-dialog { position: absolute; padding: .2em; width: 300px; overflow: hidden; z-index: 1000; } */
</style>
<!-- 
<script type="text/javascript" src="<c:url value='/script/jquery-1.7.2.js' />"></script>
<script type="text/javascript" src="<c:url value='/script/jquery-ui-1.8.16.custom.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/script/plugins/jquery.numeric.js' />"></script>
<script type="text/javascript" src="<c:url value='/script/plugins/jquery.contextmenu.js' />"></script>
<script type="text/javascript" src="<c:url value='/script/plugins/jquery.combobox.js' />"></script>
<script type="text/javascript" src="<c:url value='/script/i18n/grid.locale-en.js' />"></script>
<script type="text/javascript" src="<c:url value='/script/jquery.jqGrid.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/script/jquery-ui-timepicker-addon.js' />"></script>
<script type="text/javascript" src="<c:url value='/script/jquery.cookie.js' />"></script>
<script type="text/javascript" src="<c:url value='/script/json2.js' />"></script>
<script type="text/javascript" src="<c:url value='/script/jquery.popupWindow.js' />"></script>
 -->
 <!-- test comment -->
<script  src="<c:url value='/script/jquery-1.11.0.min.js' />"></script>
<script src="<c:url value='/script/jquery-ui-1.11.0.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/script/plugins/jquery.numeric.js' />"></script>
<script type="text/javascript" src="<c:url value='/script/plugins/jquery.contextmenu.js' />"></script>
<script type="text/javascript" src="<c:url value='/script/plugins/jquery.combobox.js' />"></script>
<script type="text/javascript" src="<c:url value='/script/i18n/grid.locale-en.js' />"></script> 
<script type="text/javascript" src="<c:url value='/script/jquery.jqGrid.js'/>"></script> <!-- ver 4.5.0-->




<!-- For Rich Text Format Editor -->
<!--
<script type="text/javascript" src="<c:url value='script/shortcut.js' />"></script>
<script type="text/javascript" src="<c:url value='script/farbtastic/farbtastic.js' />"></script>
<script type="text/javascript" src="<c:url value='script/bootstrap/bootstrap-dropdown.js' />"></script>
<script type="text/javascript" src="<c:url value='script/freshereditor.min.js' />"></script>
<script type="text/javascript" src="<c:url value='script/lightbox-2.6.min.js' />"></script>
-->
<!-- For Rich Text Format Editor -->

<!-- Google Map -->
<!-- <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=true"></script> -->

<!-- Keyboard -->
<script type="text/javascript" src="script/jquery.keyboard.js"></script>

<!-- Validator -->
<script type="text/javascript" src="script/parsley.js"></script>

<title><tiles:getAsString name="title" /></title>
<script type="text/javascript">
	// debugger;
	
	$().ready(function() {
		jQuery.fn.center = function () {
		      this.css("top", $(window).height() / 2 - this.height() / 2 + "px");
		      this.css("left", $(window).width() / 2 - this.width() / 2  + "px");
		      return this;
		};
		$("#msgDialog").dialog({
			title : "<spring:message code='message.lbl.caption' />",
			resizable : false,
			modal : true,
			autoOpen : false,
			height : "140",
			width : "350",
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
		
		$("#warningDialog").dialog({
			title : "<spring:message code='warning.lbl.caption' />",
			resizable : false,
			modal : true,
			autoOpen : false,
			height : "auto",
			width : "350",
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
		
		$("#errorDialog").dialog({
			title : "<spring:message code='error.lbl.caption' />",
			resizable : false,
			modal : true,
			autoOpen : false,
			height : "140",
			width : "350",
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
		
		$("#msgDialog").removeClass("ui-dialog-content ui-widget-content");
		$("#msgDialog").addClass("custom-dialog");
		$("#msgDialog").center();
		
		$("#warningDialog").removeClass("ui-dialog-content ui-widget-content");
		$("#warningDialog").addClass("custom-dialog");
		$("#warningDialog").center();
		
		$("#errorDialog").removeClass("ui-dialog-content ui-widget-content");
		$("#errorDialog").addClass("custom-dialog");
		$("#errorDialog").center();
	});
</script>
</head>
<body style="background-image: url(<c:url value="/images/bg.jpg"/>);">
	<div id="msgDialog" style="display: none;"></div>
	<div id="warningDialog" style="display: none;"></div>
	<div id="errorDialog" style="display: none;"></div>
	<table width="1024" border="0" cellspacing="0" cellpadding="0"
		class="table_border" align="center">
		<tr>
			<td colspan="2"><tiles:insertAttribute name="header" /></td>
		</tr>
		<tr>
			<td width="200" height="520" valign="top" bgcolor="#d3d3d3" class="indent">
				<tiles:insertAttribute name="menu" />
			</td>
			<td width="824">
			<sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_OWNER,ROLE_GUEST,ROLE_CALLBACK">
					<tiles:insertAttribute name="body" />
			</sec:authorize>
			</td>
		</tr>
		<tr>
			<td colspan="2"><tiles:insertAttribute name="footer" /></td>
		</tr>
	</table>
</body>
</html>