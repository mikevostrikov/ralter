<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="pageTitle" value="Employees management"/>
<c:set var="emp" value="${requestScope.VIEW_DATA.content.employee}"/>

<%@ include file = "../../header.jsp" %>

    <center>
        <h2>View employee</h2>
        <h3><c:out value="${emp.emName}"/></h3>
        <table class="shtable">
            <tr><td><b>ID: </b></td><td><c:out value="${emp.emId}"/></td></tr>
            <tr><td><b>Name: </b></td><td><c:out value="${emp.emName}"/></td></tr>
            <tr><td><b>Tel.: </b></td><td><c:out value="${emp.emPhone}"/></td></tr>
            <tr><td><b>Address: </b></td><td><c:out value="${emp.emAddress}"/></td></tr>
            <tr><td><b>Position: </b></td><td><c:out value="${emp.emPosition}"/></td></tr>
            <tr><td><b>Comment: </b></td><td><c:out value="${emp.emComment}"/></td></tr>
            <tr><td><b>E-mail: </b></td><td><c:out value="${emp.emEmail}"/></td></tr>
            <tr><td><b>Salary: </b></td><td><c:out value="${emp.emSalary}"/></td></tr>
            <tr><td><b>Usernames assigned: </b></td><td><c:out value="${requestScope.VIEW_DATA.content.gluedUsernames}"/></td></tr>
            <tr>
                <td colspan="2">
                    <div align="right">
                        <a href="${requestScope.VIEW_DATA.content.backActionLink}">[&lt;Back]</a>
                        <a href="${requestScope.VIEW_DATA.content.editEmployeeActionLink}">[Edit]</a>
                        <a href="${requestScope.VIEW_DATA.content.deleteEmployeeActionLink}">[Delete]</a>
                    </div>
                </td>
            </tr>
        </table>
    </center>

<%@ include file = "../../footer.jsp" %>