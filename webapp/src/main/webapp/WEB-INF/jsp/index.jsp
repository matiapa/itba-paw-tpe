<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Announcement>" scope="request" id="announcements"/>
<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Poll>" scope="request" id="polls"/>
<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Course>" scope="request" id="courses"/>


<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Inicio</title>
    <link rel="icon" type="image/png" sizes="311x311" href="/assets/img/logo-tran-white.png">
    <link rel="stylesheet" href="/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i">
    <link rel="stylesheet" href="/assets/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="/assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="/assets/fonts/material-icons.min.css">
    <link rel="stylesheet" href="/assets/fonts/simple-line-icons.min.css">
    <link rel="stylesheet" href="/assets/fonts/fontawesome5-overrides.min.css">
    <link rel="stylesheet" href="/assets/css/buttons.css">
    <link rel="stylesheet" href="/assets/css/cards.css">
    <link rel="stylesheet" href="/assets/css/colors.compiled.css">
    <link rel="stylesheet" href="/assets/css/fab.css">
    <link rel="stylesheet" href="/assets/css/nav-tabs.css">
    <link rel="stylesheet" href="/assets/css/sidebar.css">
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
                                    <h6 class="font-weight-bold m-0">Anuncios más relevantes</h6>
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
                                    <h6 class="font-weight-bold m-0">Encuestas relevantes</h6>
                                </div>

                                <%-- Relevant polls list --%>

                                <ul class="list-group list-group-flush">
                                    <c:forEach var="poll" items="${polls}">

                                        <li class="list-group-item">
                                            <div class="row align-items-center no-gutters">
                                                <div class="col mr-2">
                                                    <h6 class="mb-0"><strong><c:out value="${poll.name}"/></strong></h6>
                                                    <span class="text-xs">
                                                        🕑 Expira el <fmt:formatDate type="both" dateStyle = "short" timeStyle = "short" value="${poll.expiryDate}"/>
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
                                    <h6 class="font-weight-bold m-0">Tus cursos</h6>
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

    <script src="/assets/js/jquery.min.js"></script>
    <script src="/assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.js"></script>
    <script src="/assets/js/theme.js"></script>

</body>

</html>