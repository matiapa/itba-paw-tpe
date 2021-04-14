<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Announcement>" scope="request" id="announcements"/>
<jsp:useBean type="ar.edu.itba.paw.models.HolderEntity" scope="request" id="filterBy"/>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Anuncios</title>
    <link rel="icon" type="image/png" sizes="311x311" href="assets/img/logo-tran-white.png">
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i">
    <link rel="stylesheet" href="assets/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="assets/fonts/material-icons.min.css">
    <link rel="stylesheet" href="assets/fonts/simple-line-icons.min.css">
    <link rel="stylesheet" href="assets/fonts/fontawesome5-overrides.min.css">
    <link rel="stylesheet" href="assets/css/buttons.css">
    <link rel="stylesheet" href="assets/css/cards.css">
    <link rel="stylesheet" href="assets/css/colors.compiled.css">
    <link rel="stylesheet" href="assets/css/fab.css">
    <link rel="stylesheet" href="assets/css/nav-tabs.css">
    <link rel="stylesheet" href="assets/css/sidebar.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
</head>

<body id="page-top">
    <div id="wrapper">

        <jsp:include page="../common/navbar.jsp"/>

        <div class="d-flex flex-column" id="content-wrapper">
            <div id="content">

                <jsp:include page="../common/header.jsp"/>

                <div class="container-fluid">
                    <div>
                        <ul class="nav nav-tabs" role="tablist">
                            <li class="nav-item" role="presentation">
                                <a class="nav-link ${filterBy == "general" ? 'active' : ''}" role="tab"
                                   href="<c:url value="/announcements?filterBy=general"/>">Generales</a>
                            </li>
                            <li class="nav-item" role="presentation">
                                <a class="nav-link ${filterBy == "career" ? 'active' : ''}" role="tab"
                                   href="<c:url value="/announcements?filterBy=career"/>">Por carrera</a>
                            </li>
                            <li class="nav-item" role="presentation">
                                <a class="nav-link ${filterBy == "course" ? 'active' : ''}" role="tab"
                                   href="<c:url value="/announcements?filterBy=course"/>">Por curso</a>
                            </li>
                        </ul>

                        <div class="tab-content">

                            <%-- General announcements --%>

                            <div class="tab-pane ${filterBy == "general" ? 'active' : ''}" role="tabpanel" id="tab-1">
                                <div class="col">
                                    <div class="card-group">
                                        <c:forEach var="announcement" items="${announcements}">
                                            <c:set var="announcement" value="${announcement}" scope="request"/>
                                            <jsp:include page="announcement_card.jsp"/>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>

                            <%-- Career announcements --%>

                            <div class="tab-pane ${filterBy == "career" ? 'active' : ''}" role="tabpanel" id="tab-2">
                                <div class="col-lg-6 col-xl-12 mb-4">

                                    <div class="dropdown">
                                        <button class="btn btn-block dropdown-toggle text-left text-dark bg-white" data-toggle="dropdown"
                                          aria-expanded="false" type="button" style="margin-top: 32px;">
                                            ${selectedCareer!=null ? selectedCareer.name : 'Elegí una carrera'}
                                        </button>
                                        <div class="dropdown-menu">
                                            <c:forEach var="career" items="${careers}">
                                                <a class="dropdown-item" href="<c:url value="/announcements?filterBy=career&careerId=${career.id}"/>">
                                                    <c:out value="${career.name}"/>
                                                </a>
                                            </c:forEach>
                                        </div>
                                    </div>

                                    <c:choose>
                                        <c:when test="${selectedCareer != null}">
                                            <c:forEach var="announcement" items="${announcements}">
                                                <c:set var="announcement" value="${announcement}" scope="request"/>
                                                <jsp:include page="announcement_card.jsp"/>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="text-center"><i class="fa fa-question-circle" style="margin-top: 32px;font-size: 32px;"></i>
                                                <p style="margin-top: 16px;">Por favor, elegí una carrera para ver los anuncios</p>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                            </div>

                            <%-- Course announcements --%>

                            <div class="tab-pane ${filterBy == "course" ? 'active' : ''}" role="tabpanel" id="tab-3">
                                <div class="col-lg-6 col-xl-12 mb-4 mt-4">

                                    <form action="<c:url value="/announcements"/>" method="get">
                                        <input type="text" name="filterBy" value="course" hidden>
                                        <select name="courseId" class="selectpicker" data-live-search="true" title="Elegí una carrera" data-width="75%">
                                            <c:forEach var="course" items="${courses}">
                                                <option ${course.equals(selectedCourse) ? 'selected' : ''}
                                                    value="${course.id}" data-tokens="${course.name}">${course.name}</option>
                                            </c:forEach>
                                        </select>
                                        <button type="submit" class="btn btn-primary ml-3">Buscar</button>
                                    </form>

                                    <c:choose>
                                        <c:when test="${selectedCourse != null}">
                                            <c:forEach var="announcement" items="${announcements}">
                                                <c:set var="announcement" value="${announcement}" scope="request"/>
                                                <jsp:include page="announcement_card.jsp"/>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="text-center"><i class="fa fa-question-circle" style="margin-top: 32px;font-size: 32px;"></i>
                                                <p style="margin-top: 16px;">Por favor, elegí un curso para ver los anuncios</p>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>

                        </div>
                    </div>

                </div>
                <div id="content-1"></div>
            </div>
        </div>

    </div>

    <div class="fab">
        <i class="material-icons" style="font-size: 32px;color: rgb(255,255,255);">add</i>
    </div>

    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/chart.min.js"></script>
    <script src="assets/js/bs-init.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.js"></script>
    <script src="assets/js/theme.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>

</body>

</html>