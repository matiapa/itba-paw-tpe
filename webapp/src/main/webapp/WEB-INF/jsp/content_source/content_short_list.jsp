<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Content>" scope="request" id="contents"/>

<c:if test= "${empty contents}">
    <a>No hay contenido disponible</a>
</c:if>

<c:if test= "${!empty contents}">
    <c:forEach var="content" items="${contents}">

    <div class="row mb-2">
        <a class="badge badge-pill badge-primary" style="padding: 7px 10px;"
           href="<c:out value='${content.link}'/>">
            <c:out value="${content.name}"/>
        </a>
    </div>

    </c:forEach>
</c:if>

