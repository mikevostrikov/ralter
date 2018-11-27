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

<%@page import="beans.Operations" %>
<%@page import="beans.Documents" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View operation</title>
</head>
<body>
<%@ include file = "/header.jsp" %>
<center>
<h2>View operation</h2>
<%
   Operations theOp = (Operations)session.getAttribute("theOp");
   List doList = (List)session.getAttribute("doList");
   List coList = (List)session.getAttribute("coList");
   List suList = (List)session.getAttribute("suList");
   List eqList = (List)session.getAttribute("eqList");
%>
<h3> <%= theOp.getOpName() %></h3>
<table class="shtable">
    <tr>
        <td>
            <b>ID: </b>
        </td>
        <td>
            <%= theOp.getOpId() %>
        </td>
    </tr>
    <tr>
        <td>
            <b>Name: </b>
        </td>
        <td>
            <%= theOp.getOpName() %>
        </td>
    </tr>
    <tr>
        <td>
            <b>Description: </b>
        </td>
        <td>
            <%= theOp.getOpDescription() %>
        </td>
    </tr>
    <tr>
        <td>
            <b>Time required: </b>
        </td>
        <td>
            <%= theOp.getOpDuration() %>
        </td>
    </tr>
    <tr>
        <td>
            <b>Complexity: </b>
        </td>
        <td>
            <%= theOp.getOpComplexity() %>
        </td>
    </tr>
    <tr>
        <td>
            <b>Comment: </b>
        </td>
        <td>
            <%= theOp.getOpComment() %>
        </td>
    </tr>
    <tr>
        <td>
            <b>Documents: </b>
        </td>
        <td>
            <%
                if (!doList.isEmpty()){
                    Iterator iter1 = doList.iterator();
                    Documents theDoc;
                    for (int i = 0; i<doList.size()-1; i++){
                        theDoc = (Documents)iter1.next();
                        out.print("<a title=\""+theDoc.getDoUrl().trim()+"\" href=\""+response.encodeURL("/Ralter/Resources/Documents?act=show&doId=")+theDoc.getDoId()+"\">"+theDoc.getDoName().trim()+"</a>, ");
                    }
                    theDoc = (Documents)iter1.next();
                    out.print("<a title=\""+theDoc.getDoUrl().trim()+"\" href=\""+response.encodeURL("/Ralter/Resources/Documents?act=show&doId=")+theDoc.getDoId()+"\">"+theDoc.getDoName().trim()+"</a>");
                }
             %>
        </td>
    </tr>
    <tr>
        <td>
            <b>Parts used: </b>
        </td>
        <td>
            <%
                if (!coList.isEmpty()){
                    Iterator iter1 = coList.iterator();
                    Components theComp;
                    for (int i = 0; i<coList.size()-1; i++){
                        theComp = (Components)iter1.next();
                        out.print(theComp.getCoName().trim()+", <br>");
                    }
                    theComp = (Components)iter1.next();
                    out.print(theComp.getCoName().trim());
                }
             %>
        </td>
    </tr>
    <tr>
        <td>
            <b>Supplies used: </b>
        </td>
        <td>
            <%
                if (!suList.isEmpty()){
                    Iterator iter1 = suList.iterator();
                    Supplies theSupl;
                    for (int i = 0; i<suList.size()-1; i++){
                        theSupl = (Supplies)iter1.next();
                        out.print(theSupl.getSuName().trim()+", <br>");
                    }
                    theSupl = (Supplies)iter1.next();
                    out.print(theSupl.getSuName().trim());
                }
             %>
        </td>
    </tr>
    <tr>
        <td>
            <b>Equipment used: </b>
        </td>
        <td>
            <%
                if (!eqList.isEmpty()){
                    Iterator iter1 = eqList.iterator();
                    Equipment theEq;
                    for (int i = 0; i<eqList.size()-1; i++){
                        theEq = (Equipment)iter1.next();
                        out.print(theEq.getEqName().trim()+", <br>");
                    }
                    theEq = (Equipment)iter1.next();
                    out.print(theEq.getEqName().trim());
                }
             %>
        </td>
    </tr>
    <tr>
        <td>
            <b>Previous operation: </b>
        </td>
        <td>
            <%
                if (theOp.getPrevOperation() != null && !theOp.getPrevOperation().isEmpty()) {
                    Iterator it1 = theOp.getPrevOperation().iterator();
                    out.print(((Operations)it1.next()).getOpName());
                }
            %>
        </td>
    </tr>
    <tr>
        <td>
            <b>Next operation: </b>
        </td>
        <td>
            <% if (theOp.getNextOperation() != null) out.print(theOp.getNextOperation().getOpName()); %>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <div align="right">
                <a href="<%= response.encodeURL("/Ralter/Process/Operations?act=main") %>">[&lt;&lt;Back]</a>
                <a href="<%= response.encodeURL("/Ralter/Process/Operations?act=editexisting&opId="+theOp.getOpId()) %>">[Edit]</a></div>
        </td>
    </tr>
</table>
</center>

<%@ include file = "/footer.jsp" %>
</body>
</html>

<%@ include file = "/authfooter.jsp" %>