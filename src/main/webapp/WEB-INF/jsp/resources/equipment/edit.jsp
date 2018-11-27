<%-- 
    Document   : edit
    Created on : 29.03.2009, 1:44:27
    Author     : Mike V
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="beans.Equipment" %>

<% Integer ResId = new Integer(0); %>

<%@ include file = "/authheader.jsp" %>

<%
Equipment theEq = (Equipment)session.getAttribute("theEq");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
    <head>
        <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit equipment</title>
    </head>
    <body>
<%@ include file = "/header.jsp" %>
        <center>
            <h2>Edit equipment</h2>
        <form name="editBasic" action="<%=response.encodeURL("/Ralter/Resources/Equipment") %>" method="POST">
            <table>
                <tr>
                    <td>
                        Id:
                    </td>
                    <td>
                        <input type="text" name="EqId" value="<%=theEq.getEqId()%>" disabled>
                    </td>
                </tr>
                <tr>
                    <td>
                        Name:
                    </td>
                    <td>
                        <input type="text" name="EqName" value="<%= theEq.getEqName() %>">
                    </td>
                </tr>
                <tr>
                    <td>
                        Producer:
                    </td>
                    <td>
                        <input type="text" name="EqManufacturer" value="<%= theEq.getEqManufacturer() %>">
                    </td>
                </tr>
                <tr>
                    <td>
                        Comment:
                    </td>
                    <td>
                        <input type="text" name="EqComment" value="<%= theEq.getEqComment() %>">
                    </td>
                </tr>
            <input type="hidden" name="act" value="update">
                <tr>
                    <td align="right" colspan="2">
                        <a href="javascript: document.editBasic.submit()">[Save]</a>
                    </td>
                </tr>
            </table>
        </form>
        </center>
<%@ include file = "/footer.jsp" %>

    </body>
</html>

<%@ include file = "/authfooter.jsp" %>
