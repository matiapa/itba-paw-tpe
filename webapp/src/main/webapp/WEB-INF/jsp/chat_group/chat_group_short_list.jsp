<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.ChatGroup>" scope="request" id="chat_groups"/>

<c:forEach var="group" items="${chat_groups}">
    <div class="row mb-2">
        <a class="badge badge-pill badge-primary" style="padding: 7px 10px;"
        href="<c:url value="${group.link}"/>">
            <c:out value="${group.name} (${group.creationDate})"/>
        </a>
    </div>
</c:forEach>