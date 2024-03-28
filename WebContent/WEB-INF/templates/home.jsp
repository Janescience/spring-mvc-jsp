<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="KK_UL_Callback" var="baseUrl"/>

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
%>
<head>
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<META HTTP-EQUIV="EXPIRES" CONTENT="0">
<style type="text/css">

html, body {
    margin: 0;
    padding: 0;
    font-size: 75%;
}

fieldset { padding:0; border:0; margin-top:0px; }

</style>  
<script type="text/javascript">

	$().ready(function() {
		var previousURL = document.referrer;
		if(previousURL.indexOf("loginPopup")){
			window.close();
		}
		
		var userName = "<%=currentUser%>";
		$.ajax({
        	url:"getUserLoginInfo/getUserInfo.html", 
          	type: "GET", 
          	dataType: "json",
          	data: { userName : userName }, 
          	success: function(data) {
          		if (data != null) {
          			var name = data["firstName"] + " " + data["lastName"];
          			var roleDesc = data["roleDescription"];
          			$("#name").text("K . " + name);
          			$("#roleDesc").text("Role : " + roleDesc);
          			
          		}else{
          			$("#name").text("");
          			$("#roleDesc").text("");
          		}
            }
	    });
		
		$("#accordion").accordion({
			autoHeight: false,
			navigation: true
		});
	});
</script>
</head>
<body>
<div id="accordion" style="margin-left: 100px; margin-top: -200px; width: 400px; height: 300px">
<h3><a href="#">Welcome</a></h3>
	<div id="criteriaDialog">
		<fieldset>
			<span id="welcome" style="font-weight: bold; color: #942727"><spring:message code='lbl.title.welcome'/></span>
			</br>
			</br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span id="name" style="font-weight: bold; font-size: 1.0em; color: #942727"></span>
			</br>
			</br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span id="roleDesc" style="font-weight: bold; font-size: 1.0em; color: #942727"></span>
		</fieldset>
	</div>
</div>
</body>
