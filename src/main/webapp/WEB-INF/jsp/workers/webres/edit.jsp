<%-- 
    Document   : edit
    Created on : 29.03.2009, 1:44:27
    Author     : Mike V
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="beans.Webres" %>

<% Integer ResId = new Integer(0); %>

<%@ include file = "/authheader.jsp" %>

<%
Webres theWeb = (Webres)session.getAttribute("theWeb");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
    <head>
        <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Web-resource edit</title>
    </head>
    <body>
<%@ include file = "/header.jsp" %>
        <center>
            <h2>Web-resource edit</h2>
        <form name="editBasic" action="<%=response.encodeURL("/Ralter/Workers/Webres") %>" method="POST">
            <table class="etable">
                <tr>
                    <td>
                        Id:
                    </td>
                    <td>
                        <input type="text" name="weId" value="<%=theWeb.getWeId()%>" disabled>
                    </td>
                </tr>
                <tr>
                    <td>
                        Name:
                    </td>
                    <td>
                        <input type="text" name="weName" value="<%= theWeb.getWeName() %>">
                    </td>
                </tr>
                <tr>
                    <td>
                        Location:
                    </td>
                    <td>
                        <input type="text" name="weLocation" value="<%= theWeb.getWeLocation() %>">
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="right">
                        <input type="hidden" name="act" value="update">
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
