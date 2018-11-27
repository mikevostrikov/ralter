<%-- 
    Document   : editemp
    Created on : 28.03.2009, 22:29:41
    Author     : Mike V
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="java.text.DateFormat"%>
<%@page import="beans.Supplies" %>


<% Integer ResId = new Integer(0); %>
<%@ include file = "/authheader.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<script type="text/javascript" src="<%=response.encodeURL("/Ralter/Resources/Supplies/calendarDateInput.js")%>">
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit supply</title>
</head>
<body>
<%@ include file = "/header.jsp" %>

<center>
<h2>Edit supply</h2>
<%
   Supplies theSup = (Supplies)session.getAttribute("theSup");
%>
<h3> <%=theSup.getSuName()%></h3>
<form method="POST" action="<%=response.encodeURL("/Ralter/Resources/Supplies") %>" name ="editBasic">
<table>
    <tr>
        <td>
            <b>ID: </b>
        </td>
        <td>
            <input type="text" value="<%= theSup.getSuId() %>" name="SuId" disabled>
        </td>
    </tr>
    <tr>
        <td>
            <b>Name: </b>
        </td>
        <td>
            <input type="text" value="<%= theSup.getSuName() %>" name="SuName">
        </td>
    </tr>
    <tr>
        <td>
            <b>Stock: </b>
        </td>
        <td>
            <input type="text" value="<%= theSup.getSuQuantity() %>" name="SuQuantity">
        </td>
    </tr>
    <tr>
        <td>
            <b>Measurement unit: </b>
        </td>
        <td>
            <input type="text" value="<%= theSup.getSuMeasure() %>" name="SuMeasure">
        </td>
    </tr>
    <tr>
        <td>
            <b>Producer: </b>
        </td>
        <td>
            <input type="text" value="<%= theSup.getSuManufacturer() %>" name="SuManufacturer">
        </td>
    </tr>
    <tr>
        <td>
            <b>Price per unit: </b>
        </td>
        <td>
            <input type="text" value="<%= theSup.getSuPrice() %>" name="SuPrice">
        </td>
    </tr>
    <tr>
        <td>
            <a href="javascript:document.editBasic.submit()">[Save]</a>
        </td>
        <td>
            <a href="javascript:document.location.href='<%=response.encodeURL("/Ralter/Resources/Supplies?act=delete&suId="+theSup.getSuId()) %>'">[Delete]</a>
        </td>
    </tr>
</table>
<input type="hidden" value="update" name="act">
</form>
</center>

<%@ include file = "/footer.jsp" %>
</body>
</html>

<%@ include file = "/authfooter.jsp" %>