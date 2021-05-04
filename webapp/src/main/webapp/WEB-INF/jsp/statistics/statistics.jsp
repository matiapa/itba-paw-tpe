<%@ page import="ar.edu.itba.paw.models.Entity" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>


<jsp:useBean type="java.util.Map<ar.edu.itba.paw.models.Entity, java.lang.Integer>" scope="request" id="newContributions"/>

<jsp:useBean type="java.util.Map<ar.edu.itba.paw.models.Career, java.lang.Integer>" scope="request" id="contributionsByCareer"/>

<jsp:useBean type="java.util.List<java.lang.String>" scope="request" id="contributionsByDateDates"/>
<jsp:useBean type="java.util.List<java.lang.Integer>" scope="request" id="contributionsByDateContribs"/>

<jsp:useBean type="java.util.List<java.util.Map.Entry<ar.edu.itba.paw.models.User, java.lang.Integer>>" scope="request" id="topUsersByContributions"/>
<jsp:useBean type="java.util.List<java.util.Map.Entry<ar.edu.itba.paw.models.Course, java.lang.Integer>>" scope="request" id="topCoursesByContributions"/>


<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><spring:message code="statistics.title"/></title>

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
            <h3 class="text-dark mb-0">
                <spring:message code="statistics.contributions.title"/>
            </h3>
        </div>

        <div class="row">

            <div class="col-md-6 col-lg-3 col-xl-3 mb-4">
                <div class="card shadow border-left-primary py-2">
                    <div class="card-body" style="padding-top: 5px;padding-bottom: 5px;">
                        <div class="row align-items-center no-gutters">
                            <div class="col mr-2">
                                <div class="text-uppercase text-primary font-weight-bold text-xs mb-1">
                                    <span><spring:message code="statistics.contributions.new.polls"/></span>
                                </div>
                                <div class="text-dark font-weight-bold h5 mb-0">
                                    <span><%= newContributions.get(Entity.poll) %></span>
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
                                    <span><spring:message code="statistics.contributions.new.announces"/></span>
                                </div>
                                <div class="text-dark font-weight-bold h5 mb-0">
                                    <span><%= newContributions.get(Entity.announcement) %></span>
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
                                    <span><spring:message code="statistics.contributions.new.groups"/></span>
                                </div>
                                <div class="row no-gutters align-items-center">
                                    <div class="col-auto">
                                        <div class="text-dark font-weight-bold h5 mb-0 mr-3">
                                            <span><%= newContributions.get(Entity.chat_group) %></span>
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
                                    <span><spring:message code="statistics.contributions.new.course_content"/></span>
                                </div>
                                <div class="text-dark font-weight-bold h5 mb-0">
                                    <span><%= newContributions.get(Entity.course_content) %></span>
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
                        <h6 class="text-white font-weight-bold m-0">
                            <spring:message code="statistics.contributions.by_date"/>
                        </h6>
                    </div>
                    <div class="card-body">
                        <canvas id="contributionsByDateChart"></canvas>
                    </div>
                </div>
            </div>

            <div class="col-lg-5 col-xl-4">
                <div class="card shadow mb-4">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <h6 class="text-white font-weight-bold m-0">
                            <spring:message code="statistics.contributions.top_careers"/>
                        </h6>
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
                    <h6 class="text-white font-weight-bold m-0">
                        <spring:message code="statistics.contributions.top_users"/>
                    </h6>
                </div>
                <ul class="list-group list-group-flush">
                    <c:forEach var="contribution" items="${topUsersByContributions}">
                        <li class="list-group-item">
                            <div class="row align-items-center no-gutters">
                                <div class="col mr-2">
                                    <h6 class="mb-0">
                                        <strong>${contribution.key.name} ${contribution.key.surname}</strong>
                                    </h6>
                                    <span class="text-xs">
                                        <spring:message code="statistics.contributions.contributions"
                                            arguments="${contribution.value}"/>
                                    </span>
                                </div>
                                <div class="col-auto">
                                    <c:url var="url" value="/profile/${contribution.key.id}"/>
                                    <a href="${url}" class="btn btn-icon" type="button">
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
                    <h6 class="text-white font-weight-bold m-0">
                        <spring:message code="statistics.contributions.top_courses"/>
                    </h6>
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
                                        <spring:message code="statistics.contributions.contributions"
                                                arguments="${contribution.value}"/>
                                    </span>
                                </div>
                                <div class="col-auto">
                                    <c:url var="url" value="/courses/${contribution.key.id}"/>
                                    <a href="${url}" class="btn btn-icon" type="button">
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

    <jsp:include page="../common/scripts.jsp"/>

    <script src="https://cdn.jsdelivr.net/npm/chart.js@3.2.1/dist/chart.min.js"></script>

<%--    <script src="assets/js/chart.min.js"></script>--%>

    <script>
        console.log(<%= contributionsByDateDates.toString() %>)

        new Chart(
            document.getElementById('contributionsByDateChart'),
            {
                type: 'line',
                data: {
                    labels: <%= contributionsByDateDates.toString() %>,
                    datasets: [{
                        label: 'Contribuciones',
                        data: <%= contributionsByDateContribs %>,
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