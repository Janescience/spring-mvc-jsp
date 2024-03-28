<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<script type="text/javascript">
<!--//--><![CDATA[//><!--
	 

	function openPage(urlPath) {
		//document.location.replace("<%=request.getContextPath()%>" + "/" + urlPath);
		window.location = "<%=request.getContextPath()%>/" + urlPath + ";jsessionid=" + "<%=request.getSession().getId()%>"; 
	}
	function logout(){
		var r = confirm("ต้องการออกจากระบบ ใช่หรือไม่");
		if (r == true) {
			window.location = "<%=request.getContextPath()%>/j_spring_security_logout";
		}  
		
	}
//--><!]]>
</script> 
 
 	<div id="float_banner" style="display:none">
		<table>
		<tr>
			<td>&nbsp;</td>
			<td>
			<div id="docCheckListDiv">Document Check List</div>
			<div id="docButtonDiv">
				<button id="btnCancel" ><spring:message code="btn.lbl.cancel" /></button>
				<button id="btnSubmit" ><spring:message code="btn.lbl.save" /></button>
			</div>
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr><td colspan=3>&nbsp;</td></tr>
		</table>
	</div>
	
	<ul id="menu">
		 <li>
        	<a href="javascript:openPage('main.html');">หน้าหลัก</a>
        </li>
        <sec:authorize ifAllGranted="ROLE_ADMIN">
	        <li><font color="#666666" >ตั้งค่าระบบ</font>
				<ul>
					<li>
			        	<a href="javascript:openPage('userMaintenance.html');">ข้อมูลผู้ใช้งาน</a>
			        </li>
				</ul>
			</li>
        </sec:authorize>
        
        <sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_CALLBACK">
        	<li>
	        	<a href="javascript:openPage('KK_UL_Callback.html');">UL Callback</a>
	        </li> 
	        <li>
	        	<a href="javascript:openPage('KK_OL_Callback.html');">OL Callback</a>
	        </li> 
        </sec:authorize> 
          
        <sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_STAFF">
        	 
	        <li>
	        	<a href="javascript:openPage('resetPassword.html');">Reset Password</a>
	        </li>
        </sec:authorize>
	    <li>
        	<a href="javascript:logout();">ออกจากระบบ</a>
        </li>
    </ul> 
    
    
<div style="display: none;" id="execute"></div>


<div id="loadingImage"
	style="position: absolute; left: 100px; top: 100px; background: url(<c:url value='/images/loading.gif'/>) 
	no-repeat center; z-index: 100; width: 16px; height: 16px; display: none;">
</div>
