<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Poll>" scope="request" id="polls"/>
<jsp:useBean type="ar.edu.itba.paw.models.HolderEntity" scope="request" id="filterBy"/>


<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>"polls"</title>

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

                        <div align="right">
                            <a href="#popup" data-toggle="modal">
                                <button class="btn btn-primary btn-sm">
                                    <i class="material-icons pull-left">add</i>
                                    <spring:message code="poll.add"/>
                                </button>
                            </a>
                        </div>

                        <ul class="nav nav-tabs" role="tablist">
                            <li class="nav-item" role="presentation">
                                <a class="nav-link ${filterBy == "general" ? 'active' : ''}" role="tab"
                                   href="<c:url value="/polls?filterBy=general"/>"><spring:message code="generals"/></a>
                            </li>
                            <li class="nav-item" role="presentation">
                                <a class="nav-link ${filterBy == "career" ? 'active' : ''}" role="tab"
                                   href="<c:url value="/polls?filterBy=career"/>"><spring:message code="byCareer"/></a>
                            </li>
                            <li class="nav-item" role="presentation">
                                <a class="nav-link ${filterBy == "course" ? "active" : ''}" role="tab"
                                    href="<c:url value="polls?filterBy=course"/>"><spring:message code="byCourse"/></a>
                            </li>
                        </ul>

                        <div class="tab-content">

                            <c:set var="filterFormFields">
                                <div class="col-xl-2">
                                    <select class="form-control" name="type">
                                        <option ${selectedType == null ? 'selected' : ''} value="">
                                            <spring:message code="form.type"/>
                                        </option>
                                        <c:forEach var="type" items="${types}">
                                            <option ${selectedType == type ? 'selected' : ''} value="${type}">
                                                    ${typeTranslate.get(type)}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="col-xl-2">
                                    <select class="form-control" name="state">
                                        <option ${selectedState == null ? 'selected' : ''} value="">
                                            <spring:message code="poll.state"/>
                                        </option>
                                        <c:forEach var="state" items="${states}">
                                            <option ${selectedState == state ? 'selected' : ''} value="${state}">
                                                    ${stateTranslate.get(state)}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="col-xl-2">
                                    <button type="submit" class="btn btn-primary"><spring:message code="filter"/></button>
                                </div>
                            </c:set>

                            <%-- General polls --%>

                            <div class="tab-pane ${filterBy == "general" ? 'active' : ''}" role="tabpanel" id="tab-1">
                                <div class="col">
                                    <form method="get" class="mt-3">
                                        <input hidden type="text" name="filterBy" value="general">
                                        <div class="row align-items-center">
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

                            <%-- Career polls --%>

                            <div class="tab-pane ${filterBy == "career" ? 'active' : ''}" role="tabpanel" id="tab-2">
                                <div class="col-lg-6 col-xl-12 mb-4">

                                    <div class="dropdown">
                                        <button class="btn btn-block dropdown-toggle text-left text-dark bg-white" data-toggle="dropdown"
                                                aria-expanded="false" type="button" style="margin-top: 32px;">
                                            <c:choose>
                                                <c:when test="${selectedCareer!=mull}">
                                                    selectedCareer.name
                                                </c:when>
                                                <c:otherwise>
                                                    <spring:message code="chooseCareer"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </button>
                                        <div class="dropdown-menu">
                                            <c:forEach var="career" items="${careers}">
                                                <a class="dropdown-item" href="<c:url value="/polls?filterBy=career&careerId=${career.id}"/>">
                                                    <c:out value="${career.name}"/>
                                                </a>
                                            </c:forEach>
                                        </div>
                                    </div>

                                    <c:if test="${selectedCareer != null}">
                                        <form name="searchForm" method="get" class="mt-3">
                                            <input hidden type="text" name="filterBy" value="career">
                                            <input hidden type="text" name="careerId" value="${selectedCareer.id}">
                                            <div class="row align-items-center">
                                                    ${filterFormFields}
                                            </div>
                                        </form>
                                    </c:if>

                                    <c:choose>
                                        <c:when test="${selectedCareer != null}">
                                            <c:forEach var="poll" items="${polls}">
                                                <c:set var="poll" value="${poll}" scope="request"/>
                                                <jsp:include page="poll_card.jsp"/>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="text-center"><i class="fa fa-question-circle" style="margin-top: 32px;font-size: 32px;"></i>
                                                <p style="margin-top: 16px;"><spring:message code="poll.chooseCareerPlease"/></p>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                            </div>

                            <!--   Course Polls --->

                            <div class="tab-pane ${filterBy == "course" ? 'active' : ''}" role="tabpanel" id="tab-3">
                                <div class="col">

                                    <form id="coursesPollFilterForm" method="get" class="mt-3">
                                        <input hidden type="text" name="filterBy" value="course">

                                        <div style="border: thin solid grey">
                                            <select id="courseId" name="courseId" class="selectpicker" data-live-search="true"
                                                    title=<spring:message code="chooseCourse"/> data-width="100%">
                                                <c:forEach var="course" items="${courses}">
                                                    <option ${course.equals(selectedCourse) ? 'selected' : ''}
                                                            value="${course.id}" data-tokens="${course.name}">${course.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <c:if test="${selectedCourse != null}">
                                            <div class="row align-items-center mt-3">
                                                    ${filterFormFields}
                                            </div>
                                        </c:if>
                                    </form>

                                    <c:choose>
                                        <c:when test="${selectedCourse != null && polls.size() > 0}">
                                            <c:forEach var="poll" items="${polls}">
                                                <c:set var="poll" value="${poll}" scope="request"/>
                                                <jsp:include page="poll_card.jsp"/>
                                            </c:forEach>
                                        </c:when>
                                        <c:when test="${selectedCourse != null && polls.size() == 0}">
                                            <div class="text-center mt-5">
                                                <i class="fa fa-question-circle" style="margin-top: 32px;font-size: 32px;"></i>
                                                <p style="margin-top: 16px;"><spring:message code="noContent"/></p>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="text-center mt-5">
                                                <i class="fa fa-question-circle" style="margin-top: 32px;font-size: 32px;"></i>
                                                <p style="margin-top: 16px;"><spring:message code="poll.chooseCoursePlease"/></p>
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