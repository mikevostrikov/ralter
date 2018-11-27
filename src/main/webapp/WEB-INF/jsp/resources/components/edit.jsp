<%-- 
    Document   : editemp
    Created on : 28.03.2009, 22:29:41
    Author     : Mike V
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="java.text.DateFormat"%>
<%@page import="beans.Components" %>


<% Integer ResId = new Integer(0); %>
<%@ include file = "/authheader.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<script type="text/javascript" src="<%=response.encodeURL("/Ralter/Resources/Components/calendarDateInput.js")%>">
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit component</title>
</head>
<body>
<%@ include file = "/header.jsp" %>

<center>
<h2>Edit component</h2>
<%
   Components theComp = (Components)session.getAttribute("theComp");
%>
<h3> <%=theComp.getCoName()%></h3>
<form method="POST" action="<%=response.encodeURL("/Ralter/Resources/Components") %>" name ="editBasic">
<table>
    <tr>
        <td>
            <b>ID: </b>
        </td>
        <td>
            <input type="text" value="<%= theComp.getCoId() %>" name="CoId" disabled>
        </td>
    </tr>
    <tr>
        <td>
            <b>Name: </b>
        </td>
        <td>
            <input type="text" value="<%= theComp.getCoName() %>" name="CoName">
        </td>
    </tr>
    <tr>
        <td>
            <b>Stock: </b>
        </td>
        <td>
            <input type="text" value="<%= theComp.getCoQuantity() %>" name="CoQuantity">
        </td>
    </tr>
    <tr>
        <td>
            <b>Producer: </b>
        </td>
        <td>
            <input type="text" value="<%= theComp.getCoManufacturer() %>" name="CoManufacturer">
        </td>
    </tr>
    <tr>
        <td>
            <b>Supply date: </b>
        </td>
        <td>
            <%
                DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.CANADA);
            %>
            <script>DateInput('CoDate', true, 'DD/MM/YYYY', '<%= df.format(theComp.getCoDate()) %>')</script>
        </td>
    </tr>
    <tr>
        <td>
            <a href="javascript:document.editBasic.submit()">[Save]</a>
        </td>
        <td>
            <a href="javascript:document.location.href='<%=response.encodeURL("/Ralter/Resources/Components?act=delete&coId="+theComp.getCoId()) %>'">[Delete]</a>
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