<%-- 
    Document   : editemp
    Created on : 28.03.2009, 22:29:41
    Author     : Mike V
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="java.text.DateFormat"%>
<%@page import="beans.Operations" %>

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
                document.tblForm.act.value='edit2';
                serializeSelected();
                document.tblForm.submit();
            }

        </script>
<title>Edit production process in place</title>
</head>
<body>
<%@ include file = "/header.jsp" %>

<center>
<h1>Edit production process in place</h1>
<%
   List opList = (List)session.getAttribute("opList");
   List tpOpList = (List)session.getAttribute("tpOpList");
%>
<h2>Edit production process in place</h2>
<form name ="tblForm" method="POST" action="<%=response.encodeURL("/Ralter/Process/Process") %>">
<table>
    <tr>
        <td colspan="2">
            <table>
                <tr>
                    <td>
                        <select title="Operations available" ondblclick="javascript:_moveItems('tableColumns:leading','tableColumns','tblForm');" name="tableColumns:leading" size="10">
                            <%
                                if(opList != null){for(int i = 0; i<opList.size(); i++){
                                    %>
                                    <option value="<%= ((Operations)opList.get(i)).getOpId() %>"> <%= ((Operations)opList.get(i)).getOpName() %></option>
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
                            <select title="Current operations list" ondblclick="javascript:_moveItems('tableColumns','tableColumns:leading','tblForm');" name="tableColumns" size="10" multiple>
                                <%
                                    if(tpOpList != null){for(int i = 0; i<tpOpList.size(); i++){
                                        %>
                                        <option value="<%= ((Operations)tpOpList.get(i)).getOpId() %>"> <%= ((Operations)tpOpList.get(i)).getOpName() %></option>
                                        <%
                                    }}
                                %>
                                <option value="">_______________</option>
                            </select>
                            <input type="hidden" name="sel" value="">
                    </td>
                    <td>
                        <a href="javascript:_orderList(0, 'tableColumns');">Move up</a><br>
                        <a href="javascript:_orderList(1, 'tableColumns');">Move down</a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            *Production process should consists of at least two operations.
        </td>
    </tr>
    <tr>
        <td colspan="2" align="right">
            <a href="javascript:serializeSelected(); document.tblForm.submit()">[Save]</a>
        </td>
    </tr>
</table>
<input type="hidden"  name="act" value="update">
</form>
</center>

<%@ include file = "/footer.jsp" %>
</body>
</html>

<%@ include file = "/authfooter.jsp" %>