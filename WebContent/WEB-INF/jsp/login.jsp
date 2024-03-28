<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<link rel="shortcut icon" href="<c:url value='/images/gt.ico' />" />
<link rel="icon" href="<c:url value='/images/gt.gif' />" type="image/gif" />
<title><spring:message code='lbl.title.caption'/></title>
<style type="text/css" media="screen">
@import url("<c:url value='/styles/styles.css' />");
@import url("<c:url value='/styles/gt-theme/jquery-ui-1.8.16.custom.css' />");

#p { white-space: pre }
.ui-widget { font-size: 0.8em; }
.ui-button { margin-left: -1px; }
.ui-button-icon-only .ui-button-text { padding: 0.35em; } 
.ui-autocomplete-input { margin: 0; padding: 0.48em 0 0.47em 0.45em; }
label, input { display : inline-block; }
input.text { padding : 0.2em; }
.ui-dialog .ui-state-error { padding: 0.3em; }
.validateTips { 
	border: 0.1px solid transparent; 
	padding: 0.3em; 
	font-size: 0.83em;
	font-family: "verdana", "arial", "helvetica", "sans-serif"; 
	color: red;
}

</style>
<script type="text/javascript" src="<c:url value='/script/jquery-1.6.4.js' />"></script>
<script type="text/javascript" src="<c:url value='/script/jquery-ui-1.8.16.custom.min.js' />"></script>

<script type="text/javascript">
	$().ready(function() {
		
		var verticalCenter = Math.floor(window.innerHeight/2);

		var username = $("#username"),
		password = $("#password"),
		allFields = $( [] ).add(username).add(password);
		
		function updateTips(t) {
			$('tr#FormError > td.ui-state-error').html(t);
    		$('tr#FormError').show();
		}
		
		function checkNULL(o, fieldName) {
			if (o.val().length == 0 || o.val() === "") {
				o.addClass( "ui-state-error" );
				o.focus();
				updateTips("<spring:message code='required' arguments='" + fieldName + "'/>");
				return false;
			} else {
				return true;
			}
		}
		
		$("#loginDialog").dialog({
			title : "Login",
			resizable : false,
			draggable : false,
			closeOnEscape : false,
			width : "350",
			height : "100%",
			position : [verticalCenter, 180],
			open : function(event, ui) { 
				$(".ui-dialog-titlebar-close").hide();
				$(this).css('overflow','hidden');
				$(this).css('height','auto');
			}
		});
		
		$('#loginForm').submit(function() {
			var bValid = true;
			allFields.removeClass("ui-state-error");
		
			bValid = bValid && checkNULL(username, "User Name");
			bValid = bValid && checkNULL(password, "Password");
			
			if (bValid) {
				return true;
			} else {
				return false;
			}
		});
		
		$('input').keypress(function(e) {
	        if(e.which == 13) {
	           	$(this).blur();
	            $('input:submit').focus().click();
	        }
	    });
		
		$('input:reset').click(function() {
			allFields.removeClass("ui-state-error");
			username.focus();
			document.location.replace("/KK_UL_Callback/index.html");
		});
		
		$("input:submit, button, input:reset", "#loginDialog").button();
	});
</script>

</head>

<body>
	<%@include file="../templates/header.jsp" %>
	<div id="loginDialog" align="center" class="ui-widget">
		<form name="f" id="loginForm" action="<c:url value='KK_UL_Callback/j_spring_security_check'/>" method="post">
			<table style="width: 310px;">
				<%-- this form-login-page form is also used as the form-error-page to ask for a login again. --%>
				<tr id="FormError" style="display: none;">
					<td class="ui-state-error" colspan="2"></td>
				</tr>
				<c:if test="${ not empty param.error }">
					<script type="text/javascript">
						$().ready(function() {
							
							$('tr#FormError > td.ui-state-error')
				    		.html("<c:out value='${ SPRING_SECURITY_LAST_EXCEPTION.message }' />");
				    		$('tr#FormError').show();
						});
					</script>
				</c:if>
				<tr>
					<td>User Name:</td>
					<td><input type='text' name='j_username' id="username" value='<c:if test="${not empty param.error}"><c:out value="${ SPRING_SECURITY_LAST_USERNAME }"/></c:if>' /> 
					<!-- <input type='text' name='j_username' id='username' value='pon'/>-->
					</td>
				</tr>
				<tr>
					<td>Password:</td>
					<td> <input type='password' name='j_password' id="password" /> 
					<!--<input type='password' name='j_password' id="password" value="pon"/>-->
					</td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<input name="submit" type="submit" value="Login" />&nbsp;
						<input name="reset" type="reset" id="reset" /> 
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="footer" align="center" style="margin-top: 300px;">
		<%@include file="../templates/footer.jsp" %>
	</div>
</body>
</html>