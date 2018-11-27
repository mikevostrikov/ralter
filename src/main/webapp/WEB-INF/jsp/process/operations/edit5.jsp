<%-- 
    Document   : editemp
    Created on : 28.03.2009, 22:29:41
    Author     : Mike V
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="java.text.DateFormat"%>
<%@page import="beans.Operations" %>
<%@page import="beans.Supplies" %>


<% Integer ResId = new Integer(0); %>
<%@ include file = "/authheader.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script language="JavaScript" src="<%=response.encodeURL("/Ralter/Process/Operations/utils.js")%>"></script>
        <script language="JavaScript" src="<%=response.encodeURL("/Ralter/Process/Operations/ShuttleA0.js")%>"></script>
        <script language="JavaScript">
            function serializeSelected(){
                for (i = 0; i < document.tblForm.tableColumns.options.length-2; i++){
                    document.tblForm.sel.value = document.tblForm.sel.value+ document.tblForm.tableColumns.options[i].value+',';
                }
                if (document.tblForm.tableColumns.options.length > 1) {document.tblForm.sel.value = document.tblForm.sel.value+ document.tblForm.tableColumns.options[i].value;}
            }
            function fltrClk(){
                document.tblForm.act.value='edit5';
                serializeSelected();
                document.tblForm.submit();
            }

        </script>
<title>Edit operation</title>
</head>
<body>
<%@ include file = "/header.jsp" %>

<center>
<h2>Edit operation</h2>
<h3>Step 5. Assign required materials.</h3>
<%
   Operations theOp = (Operations)session.getAttribute("theOp");
   List suList = (List)session.getAttribute("suList");
   List selectedSuList = (List)session.getAttribute("selectedSuList");
%>
<h4> <%=theOp.getOpName()%></h4>
<form name ="tblForm" method="POST" action="<%=response.encodeURL("/Ralter/Process/Operations") %>">
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
            <input type="text" value="<%= theOp.getOpName() %>" name="OpName" disabled>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <table>
                <tr>
                    <td colspan="3">
                            <input type="text" name="fltrPhrase">
                            <a onclick="fltrClk()" href="#">[Search]</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <select title="Materials available" ondblclick="javascript:_moveItems('tableColumns:leading','tableColumns','tblForm');" name="tableColumns:leading" size="10">
                            <%
                                if(suList != null){for(int i = 0; i<suList.size(); i++){
                                    %>
                                    <option value="<%= ((Supplies)suList.get(i)).getSuId() %>"> <%= ((Supplies)suList.get(i)).getSuName() %></option>
                                    <%
                                }}
                            %>
                            <option value="">_______________</option>
                        </select>
                    </td>
                    <td>
                            <a href="javascript:_moveItems('tableColumns:leading','tableColumns');">->>></a><br>
                            <a href="javascript:_moveItems('tableColumns','tableColumns:leading');"><<<-</a><br>
                    </td>
                    <td>
                            <select title="Materials assigned" ondblclick="javascript:_moveItems('tableColumns','tableColumns:leading','tblForm');" name="tableColumns" size="10" multiple>
                                <%
                                    if(selectedSuList != null){for(int i = 0; i<selectedSuList.size(); i++){
                                        %>
                                        <option value="<%= ((Supplies)selectedSuList.get(i)).getSuId() %>"> <%= ((Supplies)selectedSuList.get(i)).getSuName() %></option>
                                        <%
                                    }}
                                %>
                                <option value="">_______________</option>
                            </select>
                            <input type="hidden" name="sel" value="">
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <a href="javascript:serializeSelected(); document.tblForm.submit()">[Next]</a>
        </td>
        <td align="right">
            <a href="">[Delete]</a>
        </td>
    </tr>
</table>
<input type="hidden"  name="act" value="edit6">
</form>
</center>

<%@ include file = "/footer.jsp" %>
</body>
</html>

<%@ include file = "/authfooter.jsp" %>