<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="contents" scope="request" type="java.util.List"/>

<c:forEach var="content" items="${contents}">

    <div class="col mb-2">
        <a class="badge badge-pill badge-primary" style="padding: 7px 10px;"
           href="<c:out value='${content.link}'/>">
            <c:out value="${content.name}"/>
        </a>
    </div>

</c:forEach>