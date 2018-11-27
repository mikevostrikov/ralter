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

<%@page import="beans.Supplies" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View supply</title>
</head>
<body>
<%@ include file = "/header.jsp" %>
<center>
<h2>View supply</h2>
<%
   Supplies theSup = (Supplies)session.getAttribute("theSup");
%>
<h3> <%= theSup.getSuName() %></h3>
<table class="shtable">
    <tr>
        <td>
            <b>ID: </b>
        </td>
        <td>
            <%= theSup.getSuId() %>
        </td>
    </tr>
    <tr>
        <td>
            <b>Name: </b>
        </td>
        <td>
            <%= theSup.getSuName() %>
        </td>
    </tr>
    <tr>
        <td>
            <b>Stock: </b>
        </td>
        <td>
            <%= theSup.getSuQuantity()+" "+theSup.getSuMeasure() %>
        </td>
    </tr>
    <tr>
        <td>
            <b>Producer: </b>
        </td>
        <td>
            <%= theSup.getSuManufacturer() %>
        </td>
    </tr>
    <tr>
        <td>
            <b>Price per unit: </b>
        </td>
        <td>
            <%= theSup.getSuPrice() %>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <div align="right">
                <a href="<%= response.encodeURL("/Ralter/Resources/Supplies?act=main") %>">[&lt;&lt;Back]</a>
                <a href="<%= response.encodeURL("/Ralter/Resources/Supplies?act=edit&suId="+theSup.getSuId()) %>">[Edit]</a></div>
        </td>
    </tr>
</table>
</center>

<%@ include file = "/footer.jsp" %>
</body>
</html>

<%@ include file = "/authfooter.jsp" %>