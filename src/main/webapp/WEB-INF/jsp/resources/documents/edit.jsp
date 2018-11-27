<%-- 
    Document   : edit
    Created on : 29.03.2009, 1:44:27
    Author     : Mike V
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="beans.Documents" %>

<% Integer ResId = new Integer(0); %>

<%@ include file = "/authheader.jsp" %>

<%
Documents theDoc = (Documents)session.getAttribute("theDoc");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
    <head>
        <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit document</title>
    </head>
    <body>
<%@ include file = "/header.jsp" %>
        <center>
            <h2>Edit document</h2>
        <form name="editBasic" action="<%=response.encodeURL("/Ralter/Resources/Documents") %>" method="POST" enctype="multipart/form-data">
            <input type="hidden" name="act" value="update">
            <table>
                <tr>
                    <td>
                        Id:
                    </td>
                    <td>
                        <input type="text" disabled size="50" value="<%= theDoc.getDoId() %>">
                    </td>
                </tr>
                <tr>
                    <td>
                        Name:
                    </td>
                    <td>
                        <input type="text" name="DoName" size="50" value="<%= theDoc.getDoName() %>">
                    </td>
                </tr>
                <tr>
                    <td>
                        File (in case of <br> new version upload):
                    </td>
                    <td>
                        <input type="File" name="File" size="50">
                    </td>
                </tr>
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
