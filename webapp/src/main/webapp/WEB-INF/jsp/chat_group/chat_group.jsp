<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>

    <div class="col mb-2" style="padding: 10px">
        <h1><c:out value="${chat_group.name}"/></h1>
        <a class="badge badge-pill badge-primary" style="padding: 7px 10px;"
           target="_blank" href="<c:url value="${chat_group.link}"/>">
            <c:out value="${chat_group.link}"/>
        </a>
    </div>
