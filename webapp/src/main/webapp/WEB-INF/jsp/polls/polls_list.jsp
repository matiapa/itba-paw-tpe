<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Poll>" scope="request" id="polls"/>
<jsp:useBean type="ar.edu.itba.paw.models.HolderEntity" scope="request" id="filterBy"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Encuestas</title>
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
                               href="<c:url value="/polls?filterBy=general"/>">Todos</a>
                        </li>
                        <li class="nav-item" role="presentation">
                            <a class="nav-link ${filterBy == "career" ? 'active' : ''}" role="tab"
                               href="<c:url value="/polls?filterBy=career"/>">Por carrera</a>
                        </li>
                        <li class="nav-item" role="presentation">
                            <a class="nav-link ${filterBy == "course" ? "active" : ''}" role="tab"
                                href="<c:url value="polls?filterBy=course"/>">Por curso</a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <%-- General polls --%>
                        <div class="tab-pane ${filterBy == "general" ? 'active' : ''}" role="tabpanel" id="tab-1">
                            <div class="col">
                                <div class="card-group" style="display: flex; flex-direction: column">
                                    <c:forEach var="poll" items="${polls}">
                                        <c:set var="poll" value="${poll}" scope="request"/>
                                        <jsp:include page="poll_card.jsp"/>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                        <!--   Course Polls --->
                        <div class="tab-pane ${filterBy == "course" ? 'active' : ''}" role="tabpanel" id="tab-3">
                            <div class="col-lg-6 col-xl-12 mb-4">
                                <div class="dropdown">
                                    <button class="btn btn-block dropdown-toggle text-left text-dark bg-white" data-toggle="dropdown"
                                            aria-expanded="false" type="button" style="margin-top: 32px;">
                                        ${selectedCourse!=null ? selectedCourse.name : 'Elegí un curso'}
                                    </button>
                                    <div class="dropdown-menu">
                                        <c:forEach var="course" items="${courses}">
                                            <a class="dropdown-item" href="<c:url value="/polls?filterBy=course&courseId=${course.id}"/>">
                                                <c:out value="${course.name}"/>
                                            </a>
                                        </c:forEach>
                                    </div>
                                </div>
                                <c:choose>
                                    <c:when test="${selectedCourse != null}">
                                        <c:forEach var="poll" items="${polls}">
                                            <c:set var="poll" value="${poll}" scope="request"/>
                                            <jsp:include page="poll_card.jsp"/>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="text-center"><i class="fa fa-question-circle" style="margin-top: 32px;font-size: 32px;"></i>
                                            <p style="margin-top: 16px;">Por favor, elegí un curso para ver las encuestas</p>
                                        </div>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                        </div>

                        <%-- Career polls --%>
                        <div class="tab-pane ${filterBy == "career" ? 'active' : ''}" role="tabpanel" id="tab-2">
                            <div class="col-lg-6 col-xl-12 mb-4">
                                <div class="dropdown">
                                    <button class="btn btn-block dropdown-toggle text-left text-dark bg-white" data-toggle="dropdown"
                                            aria-expanded="false" type="button" style="margin-top: 32px;">
                                        ${selectedCareer!=null ? selectedCareer.name : 'Elegí una carrera'}
                                    </button>
                                    <div class="dropdown-menu">
                                        <c:forEach var="career" items="${careers}">
                                            <a class="dropdown-item" href="<c:url value="/polls?filterBy=career&careerId=${career.id}"/>">
                                                <c:out value="${career.name}"/>
                                            </a>
                                        </c:forEach>
                                    </div>
                                </div>

                                <c:choose>
                                    <c:when test="${selectedCareer != null}">
                                        <c:forEach var="poll" items="${polls}">
                                            <c:set var="poll" value="${poll}" scope="request"/>
                                            <jsp:include page="poll_card.jsp"/>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="text-center"><i class="fa fa-question-circle" style="margin-top: 32px;font-size: 32px;"></i>
                                            <p style="margin-top: 16px;">Por favor, elegí una carrera para ver las encuestas</p>
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
    <a href="#popup" data-toggle="modal">
        <i class="material-icons" style="font-size: 32px;color: rgb(255,255,255);">add</i>
    </a>
</div>
<div class="modal fade" id="popup">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Agregar encuesta</h4>
            </div>

            <div class="modal-body">
                <c:url value="/polls" var="postPoll"/>
                <form:form modelAttribute="pollForm" id="poll_form" method="post" action="${postPoll}">
                    <form:label path="pollName">Titulo</form:label>
                    <form:input type="text" path="pollName" class="form-control"/>
                    <form:errors path="pollName" element="p"/>
                    <form:label path="pollCareer">Carrera de la encuesta</form:label>
                    <form:select path="pollCareer" class="form-control">
                        <c:forEach var="career" items="${careers}">
                            <form:option value="${career.id}"><c:out value="${career.name}"/></form:option>
                        </c:forEach>
                        <form:errors path="pollCareer" element="div"/>
                    </form:select>
                    <form:select path="pollCourse">
                        <c:forEach var="course" items="${courses}">
                            <form:option value="${course.id}"><c:out value="${course.name}"/></form:option>
                        </c:forEach>
                    </form:select>
                    <form:label path="pollExpiryDate">Fecha de Expiracion</form:label>
                    <form:input type="text" path="pollExpiryDate" class="form-control"/>
                    <form:errors path="pollExpiryDate" element="p"/>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                        <button type="submit" class="btn btn-primary">Agregar</button>
                    </div>
                </form:form>
            </div>

        </div>
    </div>
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