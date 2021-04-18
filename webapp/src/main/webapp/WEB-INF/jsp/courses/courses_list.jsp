<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Career>" scope="request" id="careers"/>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Cursos</title>
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
</head>

<body id="page-top">
    <div id="wrapper">
        <jsp:include page="../common/navbar.jsp"/>

        <div class="container-fluid">
            <div>
                <ul class="nav nav-tabs" role="tablist">
                    <c:forEach var="career" items="${careers}">
                        <c:set var="career" value="${career}" scope="request"/>
                        <li class="nav-item" role="presentation">
                            <a class="nav-link" role="tab" href="<c:url value='/courses?careerId=${career.id}'/>">
                                <c:out value="${career.name}"/>
                            </a>
                        </li>
                    </c:forEach>

                </ul>
                <div class="tab-content">
                    <div class="tab-pane active" role="tabpanel" id="tab-1">
                        <div role="tablist" id="accordion-1" style="margin-top: 32px;">


                             <c:forEach var="CourseList" items="${careerCourses}">

                                <div class="card">
                                    <div class="card-header" role="tab">
                                        <h5 class="mb-0"><a data-toggle="collapse" aria-expanded="true" aria-controls="accordion-1 .item-${CourseList.key}" href="#accordion-1 .item-${CourseList.key}">Año <c:out value="${CourseList.key}"/></a></h5>
                                    </div>
                                    <div class="collapse show item-${CourseList.key}" role="tabpanel" data-parent="#accordion-1">

                                        <div class="card-body">
                                            <div class="table-responsive">
                                                <table class="table">
                                                    <thead>
                                                        <tr>
                                                            <th>Código</th>
                                                            <th>Nombre</th>
                                                            <th>Cuatrimestre</th>
                                                            <th>Créditos</th>
                                                            <th>Link</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="course" items="${CourseList.value}">
                                                            <tr>
                                                                <td><c:out value="${course.id}"/></td>
                                                                <td><c:out value="${course.name}"/></td>
                                                                <td><c:out value="${course.semester}"/></td>
                                                                <td><c:out value="${course.credits}"/></td>
                                                                <td><button class="btn btn-link" type="button">Abrir</button></td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                             </c:forEach>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="content-1"></div>
    </div>
    </div>
    </div>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/chart.min.js"></script>
    <script src="assets/js/bs-init.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.js"></script>
    <script src="assets/js/theme.js"></script>
</body>

</html>