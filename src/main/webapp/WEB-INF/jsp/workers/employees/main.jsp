<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="pageTitle" value="Employees management"/>
<%@ include file = "../../header.jsp" %>

    <center><h2>Employees list</h2></center>
    <table class="mtable" align="center" border="1">
        <tr><th>Name</th><th>Phone number</th><th>Position</th><th>Salary</th><th></th></tr>
        <c:forEach items="${requestScope.VIEW_DATA.content.employees}" var="emp">
            <tr>
                <td><c:out value="${emp.emName}"/></td>
                <td><c:out value="${emp.emPhone}"/></td>
                <td><c:out value="${emp.emPosition}"/></td>
                <td><c:out value="${emp.emSalary}"/></td>
                <td>
                    <a href="${requestScope.VIEW_DATA.content.deleteEmployeeActionLinksMap[emp]}">
                        <img src="${requestScope.VIEW_DATA.content.appBaseUri}/pics/del.jpg" title="Delete">
                    </a>
                    <a href="${requestScope.VIEW_DATA.content.editEmployeeActionLinksMap[emp]}">
                        <img src="${requestScope.VIEW_DATA.content.appBaseUri}/pics/edit.jpg" title="Edit">
                    </a>
                    <a href="${requestScope.VIEW_DATA.content.viewEmployeeActionLinksMap[emp]}">
                        <img src="${requestScope.VIEW_DATA.content.appBaseUri}/pics/show.jpg" title="View">
                    </a>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="5" align="right">
                <a href="javascript:document.location.href='<c:out value="${requestScope.VIEW_DATA.content.addEmployeeActionLink}"></c:out>'">[Add an employee]</a>
            </td>
        </tr>
    </table>

<%@ include file = "../../footer.jsp" %>
