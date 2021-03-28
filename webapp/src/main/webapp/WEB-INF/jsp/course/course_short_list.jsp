<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="courses" scope="request" type="java.util.List"/>

<c:forEach var="course" items="${courses}">

    <div class="col mb-2">
        <a class="badge badge-pill badge-primary" style="padding: 7px 10px;"
           href="/courses/detail?id=${course.id}">
            <c:out value="${course.name}"/>
        </a>
    </div>

</c:forEach>