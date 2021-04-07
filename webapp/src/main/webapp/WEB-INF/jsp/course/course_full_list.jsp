<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Course>" scope="request" id="courses"/>

<c:forEach var="course" items="${courses}">

    <li class="card mr-4 mb-4 p-3">
        <div class="column">
            <div class="row align-items-center">
                <div class="col">
                    <h5 class="text-primary"><c:out value="${course.name}"/></h5>
                </div>
                <div class="col text-right">
                    <c:out value="${course.id}"/>
                </div>
            </div>

            <p>Descripción</p>

            <div class="text-right">
                <a href="<c:url value="/courses/byId?id=${course.id}"/>">
                    Ver detalles
                </a>
            </div>
        </div>
    </li>

</c:forEach>