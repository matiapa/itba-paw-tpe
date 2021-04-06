<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<c:forEach var="group" items="${chat_groups}">
    <div class="col mb-2">
        <a class="badge badge-pill badge-primary" style="padding: 7px 10px;"
        href="<c:url value="/groups/byId?id=${group.id}"/>">
            <c:out value="${group.name} (${group.creationDate})"/>
        </a>
    </div>
</c:forEach>