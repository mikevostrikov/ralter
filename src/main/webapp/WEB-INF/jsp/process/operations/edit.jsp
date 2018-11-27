<%-- 
    Document   : editemp
    Created on : 28.03.2009, 22:29:41
    Author     : Mike V
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="java.text.DateFormat"%>
<%@page import="beans.Operations" %>
<%@page import="beans.Documents" %>
<%@page import="beans.Doop" %>


<% Integer ResId = new Integer(0); %>
<%@ include file = "/authheader.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<script type="text/javascript" src="<%=response.encodeURL("/Ralter/Process/Operations/calendarDateInput.js")%>">
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit operation</title>
</head>
<body>
<%@ include file = "/header.jsp" %>

<center>
<h2>Edit operation</h2>
<h3>Step 1. General information</h3>
<%
   Operations theOp = (Operations)session.getAttribute("theOp");
   List doList = (List)session.getAttribute("doList");
   List doRelList = (List)session.getAttribute("doRelList");
%>
<h4> <%=theOp.getOpName()%></h4>
<form method="POST" action="<%=response.encodeURL("/Ralter/Process/Operations") %>" name ="editBasic">
<table>
    <tr>
        <td>
            <b>ID: </b>
        </td>
        <td>
            <input type="text" value="<%= theOp.getOpId() %>" disabled>
        </td>
    </tr>
    <tr>
        <td>
            <b>Name: </b>
        </td>
        <td>
            <input type="text" value="<%= theOp.getOpName() %>" name="OpName">
        </td>
    </tr>
    <tr>
        <td>
            <b>Description: </b>
        </td>
        <td>
            <input type="text" value="<%= theOp.getOpDescription() %>" name="OpDescription">
        </td>
    </tr>
    <tr>
        <td>
            <b>Time required: </b>
        </td>
        <td>
            <input type="text" value="<%= theOp.getOpDuration() %>" name="OpDuration">&nbsp;min
        </td>
    </tr>
    <tr>
        <td>
            <b>Complexity: </b>
        </td>
        <td>
            <input type="text" value="<%= theOp.getOpComplexity() %>" name="OpComplexity">
        </td>
    </tr>
    <tr>
        <td>
            <b>Comment: </b>
        </td>
        <td>
            <input type="text" value="<%= theOp.getOpComment() %>" name="OpComment">
        </td>
    </tr>
        <tr>
        <td>
            <b>Documents: </b>
        </td>
        <td>
            <select name="doIds[]" size="5" multiple>
                <%
                    if (doList != null){
                        Documents theDoc;
                        // Show documents list marking those which were previously selected
                        for (int i = 0; i < doList.size(); i++) {
                            theDoc = (Documents)doList.get(i);
                            if (doRelList!=null && doRelList.contains(theDoc)){
                                out.println("<option value=\""+theDoc.getDoId()+"\" selected>"+theDoc.getDoName()+"</option>");
                            } else {
                                out.println("<option value=\""+theDoc.getDoId()+"\">"+theDoc.getDoName()+"</option>");
                            }
                        }
                     }
                %>
            </select>
        </td>
    </tr>

    <tr>
        <td>
            <a href="javascript:document.editBasic.submit()">[Next]</a>
        </td>
        <td>
            <a href="javascript:document.location.href='<%=response.encodeURL("/Ralter/Process/Operations?act=delete&opId="+theOp.getOpId()) %>'">[Remove]</a>
        </td>
    </tr>
</table>
<input type="hidden"  name="act" value="update1">
</form>
</center>

<%@ include file = "/footer.jsp" %>
</body>
</html>

<%@ include file = "/authfooter.jsp" %>