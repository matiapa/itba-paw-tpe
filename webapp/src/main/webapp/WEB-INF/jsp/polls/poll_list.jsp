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

    <jsp:include page="../common/styles.jsp"/>

    <link rel="stylesheet" href="<c:url value="/assets/bootstrap/css/bootstrap-select.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/css/overlay.css"/>"></head>
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
                                   href="<c:url value="/polls?filterBy=general"/>">Generales</a>
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

                            <c:set var="filterFormFields">
                                <div class="col-xl-2">
                                    <select class="custom-select my-1 mr-sm-2" name="type">
                                        <option ${selectedType == null ? 'selected' : ''} value="">
                                            Tipo
                                        </option>
                                        <c:forEach var="type" items="${types}">
                                            <option ${selectedType == type ? 'selected' : ''} value="${type}">
                                                    ${typeTranslate.get(type)}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="col-xl-2">
                                    <select class="custom-select my-1 mr-sm-2" name="state">
                                        <option ${selectedState == null ? 'selected' : ''} value="">
                                            Estado
                                        </option>
                                        <c:forEach var="state" items="${states}">
                                            <option ${selectedState == state ? 'selected' : ''} value="${state}">
                                                    ${stateTranslate.get(state)}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="col-xl-2">
                                    <button type="submit" class="btn btn-primary">Buscar</button>
                                </div>
                            </c:set>

                            <%-- General polls --%>

                            <div class="tab-pane ${filterBy == "general" ? 'active' : ''}" role="tabpanel" id="tab-1">
                                <div class="col">
                                    <form name="searchForm" method="get" class="mt-3">
                                        <input hidden type="text" name="filterBy" value="general">
                                        <div class="row">
                                            ${filterFormFields}
                                        </div>
                                    </form>

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

                                    <form name="searchForm" method="get" class="mt-3">
                                        <input hidden type="text" name="filterBy" value="course">
                                        <input hidden type="text" name="courseId" value="${selectedCourse.id}">
                                        <div class="row">
                                            ${filterFormFields}
                                        </div>
                                    </form>

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

                                    <form name="searchForm" method="get" class="mt-3">
                                        <input hidden type="text" name="filterBy" value="career">
                                        <input hidden type="text" name="careerId" value="${selectedCareer.id}">
                                        <div class="row">
                                            ${filterFormFields}
                                        </div>
                                    </form>

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

    <jsp:include page="poll_create.jsp"/>

    <jsp:include page="../common/scripts.jsp"/>

    <c:if test="${showCreateForm}">
        <script>$('#popup').modal('show');</script>
    </c:if>

    <script src="<c:url value="/assets/js/polls.js"/>"></script>

    <script src="<c:url value="/assets/js/popper.min.js"/>" ></script>
    <script src="<c:url value="/assets/bootstrap/js/bootstrap-select.min.js"/>"></script>

</body>

</html>