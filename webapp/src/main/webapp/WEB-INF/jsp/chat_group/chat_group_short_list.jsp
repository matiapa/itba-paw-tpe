<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<c:forEach var="group" items="${chat_groups}">
    <div class="row mb-2">
        <a class="badge badge-pill badge-primary" style="padding: 7px 10px;"
        href="<c:url value="/groups/byId?id=${group.id}"/>">
<<<<<<< HEAD
            <c:out value="${group.name}"/>
=======
            <c:out value="${group.name} (${group.creationDate})"/>
>>>>>>> 53452a3e116c105bdbef6d5a67d2e969cde81c8a
        </a>
    </div>
</c:forEach>