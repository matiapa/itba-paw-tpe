<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Announcement>" scope="request" id="announcements"/>
<%--<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Poll>" scope="request" id="relevantPolls"/>--%>
<%--<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Poll>" scope="request" id="controversialPolls"/>--%>
<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Course>" scope="request" id="courses"/>


<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><spring:message code="ITBAHUB"/></title>

    <jsp:include page="common/styles.jsp"/>
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
                                    <h6 class="font-weight-bold m-0"><spring:message code="mostRelevantAnnounce"/></h6>
                                </div>

                                <%-- Relevant announcement list --%>

                                <ul class="list-group list-group-flush">
                                    <c:choose>
                                        <c:when test="${announcements.size() != 0}">
                                            <c:forEach var="announcement" items="${announcements}">
                                                <li class="list-group-item">
                                                    <div class="row align-items-center no-gutters">
                                                        <div class="col mr-2">
                                                            <h6 class="mb-0"><strong><c:out value="${announcement.title}"/></strong></h6>
                                                            <span><c:out value="${announcement.summary}"/><br></span>
                                                        </div>
                                                        <a href="<c:url value="/announcements/${announcement.id}"/>" class="btn btn-icon stretched-link" type="button">
                                                            <i class="material-icons">keyboard_arrow_right</i>
                                                        </a>
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="list-group-item">
                                                <div class="row align-items-center no-gutters">
                                                    <div class="col mr-2" style="text-align: center;">
                                                        <h6 class="mb-0"><strong><spring:message code="noAnnounces"/></strong></h6>
                                                    </div>
                                                </div>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </ul>

                            </div>
                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="font-weight-bold m-0"><spring:message code="yourcourses"/></h6>
                                </div>

                                <%-- Relevant courses list --%>
                                <ul class="list-group list-group-flush">
                                    <c:choose>
                                    <c:when test="${courses.size() != 0}">
                                        <c:forEach var="course" items="${courses}">
                                            <li class="list-group-item">
                                                <div class="row align-items-center no-gutters">
                                                    <div class="col mr-2">
                                                        <h6 class="mb-0">
                                                            <strong><c:out value="${course.name}"/></strong>
                                                        </h6>
                                                    </div>
                                                    <a href="<c:url value="/courses/${course.id}"/>" class="btn btn-icon stretched-link" type="button">
                                                        <i class="material-icons">keyboard_arrow_right</i>
                                                    </a>
                                                </div>
                                            </li>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="list-group-item">
                                            <div class="row align-items-center no-gutters">
                                                <div class="col mr-2" style="text-align: center;">
                                                    <h6 class="mb-0">
                                                        <strong><spring:message code="noCourses"/></strong>
                                                    </h6>
                                                </div>
                                            </div>
                                        </li>
                                    </c:otherwise>
                                    </c:choose>

                                </ul>

                            </div>
                        </div>

                        <div class="col">
                            <div class="card shadow mb-4">

                                <div class="card-header py-3">
                                    <h6 class="font-weight-bold m-0"><spring:message code="relevantPolls"/></h6>
                                </div>

                                <%-- Relevant polls list --%>

<%--                                <ul class="list-group list-group-flush">--%>
<%--                                    <c:choose>--%>
<%--                                        <c:when test="${polls.size() != 0}">--%>
<%--                                            <c:forEach var="poll" items="${polls}">--%>
<%--                                                <li class="list-group-item">--%>
<%--                                                    <div class="row align-items-center no-gutters">--%>
<%--                                                        <div class="col mr-2">--%>
<%--                                                            <h6 class="mb-0"><strong><c:out value="${poll.name}"/></strong></h6>--%>
<%--                                                            <span class="text-xs">--%>
<%--                                                        <c:if test="${poll.expiryDate != null}">--%>
<%--                                                            <c:if test="${poll.isExpired}">--%>
<%--                                                                <spring:message code="expiredOn"/>--%>
<%--                                                            </c:if>--%>
<%--                                                            <c:if test="${!poll.isExpired}">--%>
<%--                                                                <spring:message code="expireOn"/>--%>
<%--                                                            </c:if>--%>
<%--                                                        </c:if>--%>
<%--                                                        <fmt:formatDate type="both" dateStyle = "short" timeStyle = "short" value="${poll.expiryDate}"/>--%>
<%--                                                    </span>--%>
<%--                                                        </div>--%>
<%--                                                        <a href="<c:url value="/polls/${poll.id}"/>" class="btn btn-icon stretched-link" type="button">--%>
<%--                                                            <i class="material-icons">keyboard_arrow_right</i>--%>
<%--                                                        </a>--%>
<%--                                                    </div>--%>
<%--                                                </li>--%>

<%--                                            </c:forEach>--%>
<%--                                        </c:when>--%>
<%--                                        <c:otherwise>--%>
<%--                                            <li class="list-group-item">--%>
<%--                                                <div class="row align-items-center no-gutters">--%>
<%--                                                    <div class="col mr-2" style="text-align: center;">--%>
<%--                                                        <h6 class="mb-0"><strong><spring:message code="noPolls"/></strong></h6>--%>
<%--                                                    </div>--%>
<%--                                                </div>--%>
<%--                                            </li>--%>

<%--                                        </c:otherwise>--%>
<%--                                    </c:choose>--%>
<%--                                </ul>--%>

                            </div>
                            <div class="card shadow mb-4">

                                <div class="card-header py-3">
                                    <h6 class="font-weight-bold m-0"><spring:message code="controversialPolls"/></h6>
                                </div>

                                <%-- Controversial polls list --%>

                                <!-- <ul class="list-group list-group-flush">
                                    <c:choose>
                                        <c:when test="${controversialPolls.size() != 0}">
                                            <c:forEach var="poll" items="${controversialPolls}">
                                                <li class="list-group-item">
                                                    <div class="row align-items-center no-gutters">
                                                        <div class="col mr-2">
                                                            <h6 class="mb-0"><strong><c:out value="${poll.name}"/></strong></h6>
                                                            <span class="text-xs">
                                                                <c:if test="${poll.expiryDate != null}">
                                                                    <c:if test="${poll.isExpired}">
                                                                        <spring:message code="expiredOn"/>
                                                                    </c:if>
                                                                    <c:if test="${!poll.isExpired}">
                                                                        <spring:message code="expireOn"/>
                                                                    </c:if>
                                                                </c:if>
                                                                <fmt:formatDate type="both" dateStyle = "short" timeStyle = "short" value="${poll.expiryDate}"/>
                                                            </span>
                                                        </div>
                                                        <a href="<c:url value="/polls/${poll.id}"/>" class="btn btn-icon stretched-link" type="button">
                                                            <i class="material-icons">keyboard_arrow_right</i>
                                                        </a>
                                                    </div>
                                                </li>

                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="list-group-item">
                                                <div class="row align-items-center no-gutters">
                                                    <div class="col mr-2" style="text-align: center;">
                                                        <h6 class="mb-0"><strong><spring:message code="noPolls"/></strong></h6>
                                                    </div>
                                                </div>
                                            </li>

                                        </c:otherwise>
                                    </c:choose>
                                </ul> -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="./common/scripts.jsp"/>

</body>
</html>