<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Content>" scope="request" id="contents"/>


<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Apuntes</title>
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
                    <div class="col-lg-12 col-xl-12 mb-6">
                        <form action="<c:url value='/contents'/>" method="get">

                            <select name="courseId" class="selectpicker" data-live-search="true" title="Elegí un Curso">
                                <c:forEach var="course" items="${courses}">
                                    <option ${course.equals(selectedCourse) ? 'selected' : ''}
                                            value="${course.id}" data-tokens="${course.name}">${course.name}</option>
                                </c:forEach>
                            </select>

                            <button id="courseSearchBtn" type="submit" class="btn btn-primary ml-3">Buscar</button>

                            <div class="row align-items-center" style="margin-top: 16px;margin-bottom: 32px;">
                                <div class="col-lg-2 col-xl-2">
                                    <select class="custom-select my-1 mr-sm-2" name="contentType">
                                        <option selected value="">...</option>
                                        <option value="exam">Exámen</option>
                                        <option value="guide">Guía</option>
                                        <option value="resume">Resúmen</option>
                                        <option value="note">Apunte</option>
                                        <option value="other">Otro</option>
                                    </select>
                                </div>

                                <div class="col-lg-4 col-xl-4 text-center"><label>Desde&nbsp;</label><input class="border rounded-0" type="date" name="minDate"></div>

                                <div class="col-lg-4 col-xl-4 text-center"><label>Hasta</label><input class="border rounded-0" type="date" name="maxDate"></div>


                            </div>
                        </form>


                        <c:choose>
                            <c:when test="${selectedCourse != null}">
                                <div class="table-responsive table mt-2" id="dataTable-1" role="grid" aria-describedby="dataTable_info" style="margin-top: 32px;background: #ffffff;">
                                    <table class="table my-0" id="dataTable">
                                        <thead>
                                        <tr>
                                            <th>Descripción</th>
                                            <th>Tipo</th>
                                            <th>Fecha</th>
                                            <th>Autor</th>
                                            <th>Link</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="content" items="${contents}">
                                            <c:set var="content" value="${content}" scope="request"/>
                                            <tr>
                                                <td><c:out value="${content.name}"/></td>
                                                <td><c:out value="${content.contentType}"/></td>
                                                <td><c:out value="${content.uploadDate}"/></td>
                                                <td><img class="rounded-circle mr-2" width="30" height="30" src="assets/img/avatars/avatar.png"><c:out value="${content.submitter.name}"/></td>
                                                <td><a class="btn btn-link" href="<c:url value='${content.link}'/>" role="button">Link</a></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                        <tfoot>
                                        <tr></tr>
                                        </tfoot>
                                    </table>
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
    </div>


    <div class="fab">
        <a href="<c:url value='/contents/create'/>">
            <i class="material-icons" style="font-size: 32px;color: rgb(255,255,255);">add</i>
        </a>
    </div>

    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/chart.min.js"></script>
    <script src="assets/js/bs-init.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.js"></script>
    <script src="assets/js/theme.js"></script>

    <script src="<c:url value='assets/js/contents.js'/>"></script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" ></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>

</body>

</html>