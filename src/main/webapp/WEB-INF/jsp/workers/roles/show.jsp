<%-- 
    Document   : showemp
    Created on : 28.03.2009, 22:29:32
    Author     : Mike V
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<% Integer ResId = new Integer(0); %>
<%@ include file = "/authheader.jsp" %>

<%@page import="beans.Users" %>
<%@page import="beans.Webres" %>
<%@page import="beans.Roles" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View role</title>
</head>
<body>
<%@ include file = "/header.jsp" %>
<center>
<h2>View role</h2>
<%
   Roles theRole = (Roles)session.getAttribute("theRole");
   List weList = (List)session.getAttribute("weList");
%>
<h3> <%= theRole.getRoName() %></h3>
<table class="shtable">
    <tr>
        <td>
            <b>ID: </b>
        </td>
        <td>
            <%= theRole.getRoId() %>
        </td>
    </tr>
    <tr>
        <td>
            <b>Role assignees: </b>
        </td>
        <td>
            <%
                if (!theRole.getUserses().isEmpty()){
                    Iterator iter1 = theRole.getUserses().iterator();
                    Users theUs;
                    for (int i = 0; i<theRole.getUserses().size()-1; i++){
                        theUs = (Users)iter1.next();
                        out.print("<a href=\""+response.encodeURL("/Ralter/Workers/Users?act=show&usId=")+theUs.getUsId()+"\">"+theUs.getUsLogin().trim()+"</a>, ");
                    }
                    theUs = (Users)iter1.next();
                    out.print("<a href=\""+response.encodeURL("/Ralter/Workers/Users?act=show&usId=")+theUs.getUsId()+"\">"+theUs.getUsLogin()+"</a>");
                }
             %>
        </td>
    </tr>
    <tr>
        <td>
            <b>Web-resources:</b>
        </td>
        <td>
            <%
                if (!weList.isEmpty()){
                    Iterator iter1 = weList.iterator();
                    Webres theWeb;
                    for (int i = 0; i<weList.size()-1; i++){
                        theWeb = (Webres)iter1.next();
                        out.print("<a title=\""+theWeb.getWeLocation().trim()+"\" href=\""+response.encodeURL("/Ralter/Workers/Webres?act=show&weId=")+theWeb.getWeId()+"\">"+theWeb.getWeName().trim()+"</a>, ");
                    }
                    theWeb = (Webres)iter1.next();
                    out.print("<a title=\""+theWeb.getWeLocation().trim()+"\" href=\""+response.encodeURL("/Ralter/Workers/Webres?act=show&weId=")+theWeb.getWeId()+"\">"+theWeb.getWeName().trim()+"</a>");
                }
             %>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <div align="right">
                <a href="<%= response.encodeURL("/Ralter/Workers/Roles?act=main") %>">[&lt;&lt;Back]</a>
                <a href="<%= response.encodeURL("/Ralter/Workers/Roles?act=existedit&roId="+theRole.getRoId()) %>">[Edit]</a></div>
        </td>
    </tr>
</table>
</center>

<%@ include file = "/footer.jsp" %>
</body>
</html>

<%@ include file = "/authfooter.jsp" %>