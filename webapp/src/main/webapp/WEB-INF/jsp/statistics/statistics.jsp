<%@ page import="ar.edu.itba.paw.models.Entity" %>
<%@ page import="ar.edu.itba.paw.models.Career" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="java.util.Map<ar.edu.itba.paw.models.Entity, java.lang.Integer>" scope="request" id="newContributions"/>

<jsp:useBean type="java.util.Map<ar.edu.itba.paw.models.Career, java.lang.Integer>" scope="request" id="contributionsByCareer"/>
<jsp:useBean type="java.util.Map<java.util.Date, java.lang.Integer>" scope="request" id="contributionsByDate"/>

<jsp:useBean type="java.util.List<java.util.Map.Entry<ar.edu.itba.paw.models.User, java.lang.Integer>>" scope="request" id="topUsersByContributions"/>
<jsp:useBean type="java.util.List<java.util.Map.Entry<ar.edu.itba.paw.models.Course, java.lang.Integer>>" scope="request" id="topCoursesByContributions"/>


<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Dashboard - Brand</title>

    <jsp:include page="../common/styles.jsp"/>
</head>

<body id="page-top">
    <div id="wrapper">

    <jsp:include page="../common/navbar.jsp"/>

    <div class="d-flex flex-column" id="content-wrapper">
    <div id="content">

    <jsp:include page="../common/header.jsp"/>

    <div class="container-fluid">

        <div class="d-sm-flex justify-content-between align-items-center mb-4">
            <h3 class="text-dark mb-0">Estadísticas de contribuciones</h3>
        </div>

        <div class="row">

            <div class="col-md-6 col-lg-3 col-xl-3 mb-4">
                <div class="card shadow border-left-primary py-2">
                    <div class="card-body" style="padding-top: 5px;padding-bottom: 5px;">
                        <div class="row align-items-center no-gutters">
                            <div class="col mr-2">
                                <div class="text-uppercase text-primary font-weight-bold text-xs mb-1">
                                    <span>NUEVAS ENCUESTAS</span>
                                </div>
                                <div class="text-dark font-weight-bold h5 mb-0">
                                    <span><%= newContributions.get(Entity.POLL) %></span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-6 col-lg-3 col-xl-3 mb-4">
                <div class="card shadow border-left-success py-2">
                    <div class="card-body" style="padding-top: 5px;padding-bottom: 5px;">
                        <div class="row align-items-center no-gutters">
                            <div class="col mr-2">
                                <div class="text-uppercase text-success font-weight-bold text-xs mb-1">
                                    <span>NUEVOS ANUNCIOS</span>
                                </div>
                                <div class="text-dark font-weight-bold h5 mb-0">
                                    <span><%= newContributions.get(Entity.ANNOUNCEMENT) %></span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-6 col-lg-3 col-xl-3 mb-4">
                <div class="card shadow border-left-info py-2">
                    <div class="card-body" style="padding-top: 5px;padding-bottom: 5px;">
                        <div class="row align-items-center no-gutters">
                            <div class="col mr-2">
                                <div class="text-uppercase text-info font-weight-bold text-xs mb-1">
                                    <span>NUEVOS GRUPOS</span>
                                </div>
                                <div class="row no-gutters align-items-center">
                                    <div class="col-auto">
                                        <div class="text-dark font-weight-bold h5 mb-0 mr-3">
                                            <span><%= newContributions.get(Entity.CHAT_GROUP) %></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-6 col-lg-3 col-xl-3 mb-4">
                <div class="card shadow border-left-warning py-2">
                    <div class="card-body" style="padding-top: 5px;padding-bottom: 5px;">
                        <div class="row align-items-center no-gutters">
                            <div class="col mr-2">
                                <div class="text-uppercase text-warning font-weight-bold text-xs mb-1">
                                    <span>NUEVOS APUNTES</span>
                                </div>
                                <div class="text-dark font-weight-bold h5 mb-0">
                                    <span><%= newContributions.get(Entity.COURSE_CONTENT) %></span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <div class="row">

            <div class="col-lg-7 col-xl-8">
                <div class="card shadow mb-4">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <h6 class="text-white font-weight-bold m-0">Contribuciones por fecha</h6>
                    </div>
                    <div class="card-body">
                        <canvas id="contributionsByDateChart"></canvas>
                    </div>
                </div>
            </div>

            <div class="col-lg-5 col-xl-4">
                <div class="card shadow mb-4">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <h6 class="text-white font-weight-bold m-0">Carreras con más contribuciones</h6>
                    </div>
                    <div class="card-body">
                        <canvas id="contributionsByCareerChart"></canvas>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">

            <div class="col-lg-6 mb-4">
            <div class="card shadow mb-4">
                <div class="card-header py-3">
                    <h6 class="text-white font-weight-bold m-0">Usuarios con más contribuciones</h6>
                </div>
                <ul class="list-group list-group-flush">
                    <c:forEach var="contribution" items="${topUsersByContributions}">
                        <li class="list-group-item">
                            <div class="row align-items-center no-gutters">
                                <div class="col mr-2">
                                    <h6 class="mb-0">
                                        <strong>${contribution.key.name}</strong>
                                    </h6>
                                    <span class="text-xs">
                                        ${contribution.value} contribuciones
                                    </span>
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
                    <h6 class="text-white font-weight-bold m-0">Cursos con más contribuciones</h6>
                </div>
                <ul class="list-group list-group-flush">
                    <c:forEach var="contribution" items="${topCoursesByContributions}">
                        <li class="list-group-item">
                            <div class="row align-items-center no-gutters">
                                <div class="col mr-2">
                                    <h6 class="mb-0">
                                        <strong>${contribution.key.name}</strong>
                                    </h6>
                                    <span class="text-xs">
                                        ${contribution.value} contribuciones
                                    </span>
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

    <jsp:include page="../common/scripts.jsp"/>

    <script src="https://cdn.jsdelivr.net/npm/chart.js@3.2.1/dist/chart.min.js"></script>

<%--    <script src="assets/js/chart.min.js"></script>--%>

    <script>
        new Chart(
            document.getElementById('contributionsByDateChart'),
            {
                type: 'line',
                data: {
                    labels: <%= contributionsByDate.keySet() %>,
                    datasets: [{
                        label: 'Contribuciones',
                        data: <%= contributionsByDate.values() %>,
                        borderColor: '#4E73DF'
                    }]
                }
            }
        );

        new Chart(
            document.getElementById('contributionsByCareerChart'),
            {
                type: 'pie',
                data: {
                    labels: <%= contributionsByCareer.keySet() %>,
                    datasets: [{
                        label: 'Contribuciones',
                        data: <%= contributionsByCareer.values() %>,
                        backgroundColor: [
                            'rgb(75,192,192)', 'rgb(54,162,235)', 'rgb(255,99,132)', 'rgb(255,159,64)', 'rgb(255,205,86)'
                        ]
                    }]
                }
            }
        );
    </script>

</body>

</html>