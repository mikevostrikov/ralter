<%-- 
    Document   : showemp
    Created on : 28.03.2009, 22:29:32
    Author     : Mike V
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="java.text.DateFormat"%>
<% Integer ResId = new Integer(0); %>
<%@ include file = "/authheader.jsp" %>

<%@page import="beans.Components" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View component</title>
</head>
<body>
<%@ include file = "/header.jsp" %>
<center>
<h2>View component</h2>
<%
   Components theComp = (Components)session.getAttribute("theComp");
%>
<h3> <%= theComp.getCoName() %></h3>
<table class="shtable">
    <tr>
        <td>
            <b>ID: </b>
        </td>
        <td>
            <%= theComp.getCoId() %>
        </td>
    </tr>
    <tr>
        <td>
            <b>Name: </b>
        </td>
        <td>
            <%= theComp.getCoName() %>
        </td>
    </tr>
    <tr>
        <td>
            <b>Stock: </b>
        </td>
        <td>
            <%= theComp.getCoQuantity() %>
        </td>
    </tr>
    <tr>
        <td>
            <b>Producer: </b>
        </td>
        <td>
            <%= theComp.getCoManufacturer() %>
        </td>
    </tr>
    <tr>
        <td>
            <b>Production date: </b>
        </td>
        <td>
            <% DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.CANADA); %>
            <%= df.format(theComp.getCoDate()) %>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <div align="right">
                <a href="<%= response.encodeURL("/Ralter/Resources/Components?act=main") %>">[&lt;&lt;Back]</a>
                <a href="<%= response.encodeURL("/Ralter/Resources/Components?act=edit&coId="+theComp.getCoId()) %>">[Edit]</a></div>
        </td>
    </tr>
</table>
</center>

<%@ include file = "/footer.jsp" %>
</body>
</html>

<%@ include file = "/authfooter.jsp" %>