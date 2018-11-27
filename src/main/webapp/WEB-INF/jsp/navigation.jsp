<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table>
    <c:forEach var="resource" items="${requestScope.VIEW_DATA.navigation.controllersLinks}">
        <tr>
            <td>
                <a class="navlink" href='${resource.value}'><img border="0" src="${requestScope.VIEW_DATA.appBaseUri}/pics/arrow.gif">
                    ${resource.key}
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
