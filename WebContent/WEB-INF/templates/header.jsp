<%@page import="java.lang.management.ManagementFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<table width="1024" bgcolor="#ffffff" align="center" border="0"
	cellpadding="0" cellspacing="0" height="15">
	<tr>
		<td height="20" rowspan="1" valign="top"
			style="text-align: right; border: 0px;" colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td height="70" width="200" rowspan="1" valign="top">&nbsp;&nbsp;
				<img alt="Generali Thailand" src="<c:url value='/images/coBrand.png'/>" width="236" height="50" border="0"  />
		</td>
		<td align="right" valign="bottom">
			<font size="3" style="font-family: Trebuchet MS, sans-serif"> 
			<b><spring:message code='lbl.company.name.caption'/>&nbsp;</b>
			</font>
		</td>
	</tr>
	<tr>
		<td height="27" valign="middle" style="background-color: #993333"
			colspan="2">
			<font size="3" color="#FFFFFF"
			style="font-family: Trebuchet MS, sans-serif">
			<b>&nbsp;<spring:message code='lbl.title.caption'/></b>
			</font>
		</td>
	</tr>
	<tr>
		<td bgcolor="#000000" colspan="2"><font size="1"
			color="yellow"><b>&nbsp;<spring:message code='lbl.env.caption'/></b>
		</font>
		</td>
	</tr>
</table>