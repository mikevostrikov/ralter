<%-- 
    Document   : editemp
    Created on : 28.03.2009, 22:29:41
    Author     : Mike V
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="java.text.DateFormat"%>
<%@page import="beans.Operations" %>
<%@page import="beans.Log" %>

<% Integer ResId = new Integer(0); %>
<%@ include file = "/authheader.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Operation execution report</title>
</head>
<body>
<%@ include file = "/header.jsp" %>

<%
beans.Log theLog = (beans.Log)session.getAttribute("theLog");
Operations oper = (Operations)session.getAttribute("oper");

%>

<center>
<h2>Operation execution report</h2>
<form name ="basicForm" method="POST" action="<%=response.encodeURL("/Ralter/Production/Todo") %>">
<table>
    <tr>
        <td>
            Product passport:
        </td>
        <td>
                <%= theLog.getProducts().getPrPassport() %>
        </td>
    </tr>
    <tr>
        <td>
            Operation:
        </td>
        <td>
            <%= oper.getOpName() %>
        </td>
    </tr>
    <tr>
        <td>
            Comment:
        </td>
        <td>
            <TEXTAREA name="LoComment"></TEXTAREA>
        </td>
    </tr>
    <tr>
        <td>
            Was flaw discovered?:
        </td>
        <td>
            <INPUT type="radio" name="LoErr" value="F" CHECKED>No<BR>
            <INPUT type="radio" name="LoErr" value="T">Yes<BR>
        </td>
    </tr>
    <tr>
        <td align="right" colspan="2">
            <a href="javascript: document.basicForm.submit()">[Executed]</a>
        </td>
    </tr>
</table>
<input type="hidden"  name="act" value="update">
</form>
</center>

<%@ include file = "/footer.jsp" %>
</body>
</html>

<%@ include file = "/authfooter.jsp" %>