<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Career>" scope="request" id="careers"/>

<c:forEach var="career" items="${careers}">

    <div class="row mb-2">
        <a class="badge badge-pill badge-primary" style="padding: 7px 10px;"
           href="<c:url value="/careers/byId?id=${career.id}"/>">
            <c:out value="${career.name}"/>
        </a>
    </div>

</c:forEach>
