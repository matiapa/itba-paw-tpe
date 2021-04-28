<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>


<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Announcement>" scope="request" id="announcements"/>
<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Poll>" scope="request" id="polls"/>
<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Course>" scope="request" id="courses"/>


<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Inicio</title>

    <jsp:include page="common/styles.jsp"/>
</head>

<body id="page-top" class="sidebar-toggled">
    <div id="wrapper">

        <jsp:include page="common/navbar.jsp"/>

        <div class="d-flex flex-column" id="content-wrapper">
            <div id="content">

                <jsp:include page="common/header.jsp"/>

                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-6 mb-4">
                            <div class="card shadow mb-4">

                                <div class="card-header py-3">
                                    <h6 class="font-weight-bold m-0">"mostRelevantAnnounce"</h6>
                                </div>

                                <%-- Relevant announcement list --%>

                                <ul class="list-group list-group-flush">
                                    <c:forEach var="announcement" items="${announcements}">

                                        <li class="list-group-item">
                                            <div class="row align-items-center no-gutters">
                                                <div class="col mr-2">
                                                    <h6 class="mb-0"><strong><c:out value="${announcement.title}"/></strong></h6>
                                                    <span><c:out value="${announcement.summary}"/><br></span></div>
                                                <div class="col-auto">
                                                    <a href="<c:url value="/announcements/detail?id=${announcement.id}"/>" class="btn btn-icon" type="button">
                                                        <i class="material-icons">keyboard_arrow_right</i>
                                                    </a>
                                                </div>
                                            </div>
                                        </li>

                                    </c:forEach>
                                </ul>

                            </div>
                        </div>

                        <div class="col">
                            <div class="card shadow mb-4">

                                <div class="card-header py-3">
                                    <h6 class="font-weight-bold m-0">"relevantPolls"</h6>
                                </div>

                                <%-- Relevant polls list --%>

                                <ul class="list-group list-group-flush">
                                    <c:forEach var="poll" items="${polls}">

                                        <li class="list-group-item">
                                            <div class="row align-items-center no-gutters">
                                                <div class="col mr-2">
                                                    <h6 class="mb-0"><strong><c:out value="${poll.name}"/></strong></h6>
                                                    <span class="text-xs">
                                                        ${poll.isExpired ? "expiredOn" : "expireOn"}
                                                        <fmt:formatDate type="both" dateStyle = "short" timeStyle = "short" value="${poll.expiryDate}"/>
                                                    </span>
                                                </div>
                                                <div class="col-auto">
                                                    <a href="<c:url value="/polls/detail?id=${poll.id}"/>" class="btn btn-icon" type="button">
                                                        <i class="material-icons">keyboard_arrow_right</i>
                                                    </a>
                                                </div>
                                            </div>
                                        </li>

                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-lg-6 mb-4">
                            <div class="card shadow mb-4">

                                <div class="card-header py-3">
                                    <h6 class="font-weight-bold m-0">"yourCourses"</h6>
                                </div>

                                <%-- Relevant courses list --%>

                                <ul class="list-group list-group-flush">
                                    <c:forEach var="course" items="${courses}">

                                        <li class="list-group-item">
                                            <div class="row align-items-center no-gutters">
                                                <div class="col mr-2">
                                                    <h6 class="mb-0">
                                                        <strong><c:out value="${course.name}"/></strong>
                                                    </h6>
                                                </div>
                                                <div class="col-auto">
                                                    <a href="<c:url value="/courses/detail?id=${course.id}"/>" class="btn btn-icon" type="button">
                                                        <i class="material-icons">keyboard_arrow_right</i>
                                                    </a>
                                                </div>
                                            </div>
                                        </li>

                                    </c:forEach>
                                </ul>

                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <jsp:include page="common/scripts.jsp"/>

</body>

</html>