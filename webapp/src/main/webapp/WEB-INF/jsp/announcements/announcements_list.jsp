<%@ page import="ar.edu.itba.paw.models.Permission" %>
<%@ page import="ar.edu.itba.paw.models.Entity" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="user" type="ar.edu.itba.paw.models.User" scope="request"/>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><spring:message code="announcement.title"/></title>

    <jsp:include page="../common/styles.jsp"/>

    <link rel="stylesheet" href="<c:url value="/assets/bootstrap/css/bootstrap-select.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/css/overlay.css"/>">
</head>

<body id="page-top">
    <div id="wrapper">

    <jsp:include page="../common/navbar.jsp"/>

    <div class="d-flex flex-column" id="content-wrapper">
    <div id="content">

        <jsp:include page="../common/header.jsp"/>

        <div class="container-fluid">
            <div>
                <div class="row align-items-center">
                    <div class="col"></div>
                    <div class="col col-xl-2 custom-control custom-switch">
                        <input type="checkbox" class="custom-control-input" id="showHiddenSwitch" ${showSeen ? 'checked' : ''}>
                        <label class="custom-control-label" for="showHiddenSwitch"><spring:message code="announcement.seeHidden"/></label>
                    </div>
                    <div class="col col-xl-2">
                        <a href="#popup" data-toggle="modal">
                            <c:if test="<%= user.can(Permission.Action.create, Entity.announcement) %>">
                                <button class="btn btn-primary btn-sm">
                                    <i class="material-icons pull-left">add</i>
                                    <spring:message code="announcement.add"/>
                                </button>
                            </c:if>
                        </a>
                    </div>
                </div>

                <ul class="nav nav-tabs" role="tablist">
                    <li class="nav-item" role="presentation">
                        <a class="nav-link ${filterBy == "general" ? 'active' : ''}" role="tab"
                           href="<c:url value="/announcements?filterBy=general"/>"><spring:message code="generals"/></a>
                    </li>
                    <li class="nav-item" role="presentation">
                        <a class="nav-link ${filterBy == "career" ? 'active' : ''}" role="tab"
                           href="<c:url value="/announcements?filterBy=career"/>"><spring:message code="byCareer"/></a>
                    </li>
                    <li class="nav-item" role="presentation">
                        <a class="nav-link ${filterBy == "course" ? 'active' : ''}" role="tab"
                           href="<c:url value="/announcements?filterBy=course"/>"><spring:message code="byCourse"/></a>
                    </li>
                </ul>

                <div class="tab-content" style="margin-bottom: 10%">

                    <%-- General announcements --%>

                    <div class="tab-pane ${filterBy == "general" ? 'active' : ''}" role="tabpanel" id="tab-1">
                        <div class="col">
                            <c:choose>
                                <c:when test="${announcements.size() > 0}">
                                    <c:forEach var="announcement" items="${announcements}">
                                        <c:set var="announcement" value="${announcement}" scope="request"/>
                                        <jsp:include page="announcement_card.jsp"/>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <div class="text-center mt-5">
                                        <i class="fa fa-question-circle" style="margin-top: 32px;font-size: 32px;"></i>
                                        <p style="margin-top: 16px;"><spring:message code="noContent"/></p>
                                    </div>
                                </c:otherwise>
                            </c:choose>
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
                                        <a class="dropdown-item" href="<c:url value="/announcements?filterBy=career&careerCode=${career.code}"/>">
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
                                <c:when test="${selectedCareer != null && announcements.size() == 0}">
                                    <div class="text-center mt-5">
                                        <i class="fa fa-question-circle" style="margin-top: 32px;font-size: 32px;"></i>
                                        <p style="margin-top: 16px;"><spring:message code="noContent"/></p>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="text-center mt-5">
                                        <i class="fa fa-question-circle" style="margin-top: 32px;font-size: 32px;"></i>
                                        <p style="margin-top: 16px;"><spring:message code="announcement.chooseCareerPlease"/></p>
                                    </div>
                                </c:otherwise>
                            </c:choose>

                        </div>
                    </div>

                    <%-- Course announcements --%>

                    <div class="tab-pane ${filterBy == "course" ? 'active' : ''}" role="tabpanel" id="tab-3">
                        <div class="col-lg-6 col-xl-12 mb-4 mt-4">

                            <form id="courseAnnouncementsFilterForm" action="<c:url value="/announcements"/>" method="get">
                                <input type="text" name="filterBy" value="course" hidden>

                                <div style="border: thin solid black">
                                    <select id="courseIdFilter" name="courseId" class="selectpicker" data-live-search="true"
                                            data-width="100%" title="<spring:message code="chooseCourse"/>">
                                        <c:forEach var="course" items="${courses}">
                                            <option ${course.equals(selectedCourse) ? 'selected' : ''}
                                                    value="${course.id}" data-tokens="${course.name}">${course.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </form>

                            <c:choose>
                                <c:when test="${selectedCourse != null && announcements.size() > 0}">
                                    <c:forEach var="announcement" items="${announcements}">
                                        <c:set var="announcement" value="${announcement}" scope="request"/>
                                        <jsp:include page="announcement_card.jsp"/>
                                    </c:forEach>
                                </c:when>
                                <c:when test="${selectedCourse != null && announcements.size() == 0}">
                                    <div class="text-center mt-5">
                                        <i class="fa fa-question-circle" style="margin-top: 32px;font-size: 32px;"></i>
                                        <p style="margin-top: 16px;"><spring:message code="noContent"/></p>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="text-center mt-5">
                                        <i class="fa fa-question-circle" style="margin-top: 32px;font-size: 32px;"></i>
                                        <p style="margin-top: 16px;"><spring:message code="announcement.chooseCoursePlease"/></p>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <jsp:include page="../common/paginator.jsp"/>

                </div>
            </div>

        </div>

    </div>
    </div>

    </div>

    <jsp:include page="announcement_create.jsp"/>

    <jsp:include page="../common/scripts.jsp"/>

    <c:if test="${showCreateForm}">
        <script>$('#popup').modal('show');</script>
    </c:if>

    <script src="<c:url value="/assets/js/announcements.js"/>"></script>

</body>

</html>