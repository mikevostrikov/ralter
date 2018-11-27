<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="pageTitle" value="Employees management"/>
<c:set var="emp" value="${requestScope.VIEW_DATA.content.employee}"/>
<%@ include file = "../../header.jsp" %>

    <center>
        <h2>Edit employee information</h2>
        <h3><c:out value="${emp.emName}"/></h3>
        <form method="POST" action="${requestScope.VIEW_DATA.content.saveEmployeeActionLink}" name ="editBasic">
            <table>
                <tr><td><b>ID: </b></td><td><input type="text" value="${emp.emId}" disabled></td></tr>
                <tr><td><b>Name: </b></td><td><input type="text" value="<c:out value="${emp.emName}"/>" name="${requestScope.VIEW_DATA.content.emNameParam}"></td></tr>
                <tr><td><b>Phone: </b></td><td><input type="text" value="<c:out value="${emp.emPhone}"/>" name="${requestScope.VIEW_DATA.content.emPhoneParam}"></td></tr>
                <tr><td><b>Address: </b></td><td><input type="text" value="<c:out value="${emp.emAddress}"/>" name="${requestScope.VIEW_DATA.content.emAddressParam}"></td></tr>
                <tr><td><b>Position: </b></td><td><input type="text" value="<c:out value="${emp.emPosition}"/>" name="${requestScope.VIEW_DATA.content.emPositionParam}"></td></tr>
                <tr><td><b>Comment: </b></td><td><input type="text" value="<c:out value="${emp.emComment}"/>" name="${requestScope.VIEW_DATA.content.emCommentParam}"></td></tr>
                <tr><td><b>E-mail: </b></td><td><input type="text" value="<c:out value="${emp.emEmail}"/>" name="${requestScope.VIEW_DATA.content.emEmailParam}"></td></tr>
                <tr><td><b>Salary: </b></td><td><input type="text" value="<c:out value="${emp.emSalary}"/>" name="${requestScope.VIEW_DATA.content.emSalaryParam}"></td></tr>
                <tr>
                    <td colspan="2">
                        <a href="${requestScope.VIEW_DATA.content.backActionLink}">[&lt;Back]</a>
                        <a href="javascript:document.editBasic.submit()">[Save]</a>
                        <c:if test="${emp.emId != null}">
                            <input type="hidden" value="${emp.emId}" name="${requestScope.VIEW_DATA.content.emIdParam}">
                            <a href="${requestScope.VIEW_DATA.content.deleteEmployeeActionLink}">[Delete]</a>
                        </c:if>
                    </td>
                </tr>
            </table>
        </form>
    </center>

<%@ include file = "../../footer.jsp" %>