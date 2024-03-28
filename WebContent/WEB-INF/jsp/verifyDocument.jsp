<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>  
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">	
	$().ready(function() {
		
		$("#test1").button().click(function() {
			var _worksheetId = $('#worksheetId').val();
			if(typeof _worksheetId === "undefined" || _worksheetId == "" || _worksheetId == "0"){
				$("#dialogSelectRow").dialog("destroy");
	           	$("#msgDialog").text('<spring:message code="warning.closewhen.worksheetnotexisting" />');
				$("#msgDialog").dialog("open");
			}else{
				doPOSsaveComment('saveAndCloseWithRandom','Y'); 
			}
		});
		$("#test2").button().click(function() {
			
			
		});
		$("#test3").button().click(function() {
			
		});
	});  
</script>
<style type="text/css"> 
    
</style>
</head>
<body>
	
	<button id="test1" >Test1</button>
	<button id="test2" >Test2</button>
	<button id="test3" >Test3</button>
</body>
</html>