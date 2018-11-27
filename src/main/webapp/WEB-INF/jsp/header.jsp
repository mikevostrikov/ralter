<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html style="height: 100%;">
    <head>
        <link REL="stylesheet" TYPE="text/css" href="${requestScope.VIEW_DATA.appBaseUri}/style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>
            <c:out value="${pageTitle}"/>
        </title>
    </head>
    <body style="height: 100%; margin: 0;">
        <table border="0" style="height: 100%; width: 100%; max-width: 1000pt;" align="center">
            <tr><td height="70" colspan="2"><img src="${requestScope.VIEW_DATA.appBaseUri}/pics/logo.jpg"></td></tr>
            <tr>
                <td align="left" height="20" colspan="2">
                    <table width="100%" border="0" cellspacing="0">
                       <tr>
                           <td align="left" bgcolor="#006699">
                              <font color="white">
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                User: <c:out value="${requestScope.VIEW_DATA.header.user.usLogin}"/>&nbsp;
                                Role: <c:out value="${requestScope.VIEW_DATA.header.user.role.roName}"/>&nbsp;
                              </font>
                           </td>
                           <td width="80" bgcolor="#006699">
                              <a href="${requestScope.VIEW_DATA.header.signOutLink}" style="color: white;">Sign out</a>
                           </td>
                       </tr>
                    </table>
                </td>
            </tr>
            <c:if test="${requestScope.VIEW_DATA.header.errorMsg != null}">
                <tr height="30px">
                    <td colspan="2" bgcolor="#AA0000" style="color: #FFFFDA">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${requestScope.VIEW_DATA.header.errorMsg}</td>
                </tr>
            </c:if>
            <tr>
               <td class="navleft" width="150pt">
                   <%@ include file = "navigation.jsp" %>
               </td>
               <td valign="top" align="left" width="850pt">
