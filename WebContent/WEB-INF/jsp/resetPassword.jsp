<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:url value="KKDocumentControl" var="baseUrl"/>

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
%>

<script type="text/javascript">
	// debugger;
	
	 $(function(){
		
		 function verifyPassword(password, confirmPassword) {
				var formid = $("#resetBox");
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
		 
		 
		$("#btnResetPass").button().click(function() {
			var user_name = $.trim($("input#user_name").val());
			var newPassword = $.trim($("input#newPassword").val());
			var confirmPassword = $.trim($("input#confirmPassword_txt").val());
			var obj = { userName : user_name, password : newPassword };
			$.ajax({
		    	url: "user/resetPasswordByUser.html",
		        type: "POST",
		        data: JSON.stringify(obj),
		        dataType: "json",
		        contentType: "application/json; charset=utf-8",
		        beforeSend: function(data) {
		        	var result = verifyPassword(newPassword, confirmPassword);
		        	//alert(result);
		        	return result;
		        },
		        success: function(response) {
					$("#resetPasswordDialog").dialog("destroy");
					$("#msgDialog").text('<spring:message code="success.resetPassword" />');
					$("#msgDialog").dialog("open");
		        }
			});
		});
	    
		
		$("#accordion").accordion({
			autoHeight: false,
			navigation: true
		});
		
	});  
</script>
 
<style type="text/css"> 

	#resetBox{
		
	}
	
</style>
</head>
<body>  

<div id="accordion" style="margin-left: 100px; margin-top: -200px; width: 420px; height: 300px">
<h3><a href="#">Reset Password</a></h3>
	<div id="criteriaDialog">
		<fieldset>
			<div id="resetBox">
				<div id="resetMessage"></div>
				<div>
					<input type="hidden" id="user_name" value="<%=currentUser%>" />
					<table>
						<tr id="FormError" style="display: none;">
							<td class="ui-state-error" colspan="2"></td>
						</tr>
						<tr><td colspan="2"><B>Current user : </B><%=currentUser%></td></tr>
						<tr>
							<td>New Password : (*)</td>
							<td><input type="password" id="newPassword" size="16" maxlength="16" required="required"/></td>
						</tr>
						<tr>
							<td>Confirm Password : (*) </td>
							<td><input type="password" id="confirmPassword_txt" size="16" maxlength="16" required="required"/></td>
						</tr>
						<tr>
							<td colspan="2" align="center"><button id="btnResetPass" ><spring:message code="btn.lbl.resetPassword" /></button></td>
						</tr>
					</table>
				 </div>
			 </div>
		</fieldset>
	</div>
</div>

	
</body>
</html>