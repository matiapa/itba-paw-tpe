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
    <title><c:out value="${course.name}"/></title>

    <jsp:include page="../common/styles.jsp"/>
</head>

<body>
<div id="wrapper">
    <jsp:include page="../common/navbar.jsp"/>
    <div class="d-flex flex-column" id="content-wrapper">
        <div id="content">
            <jsp:include page="../common/header.jsp"/>
            <div class="container-fluid">
                <div class="d-flex justify-content-end bd-highlight mb-3" align="right">
                    <c:url var="urlComments" value="/courses/${course.id}/comments"/>
                    <a class="btn btn-primary btn-sm" href="${urlComments}" role="button">
                        <i class="far fa-comment"></i>
                        <spring:message code="course.Comments"/>
                    </a>
                    <c:if test="${!course.favedBy.contains(user)}">
                        <c:url var="url" value="/courses/${course.id}/fav"/>
                        <form action="${url}" method="post">
                            <button class="btn btn-primary btn-sm">
                                <i class="material-icons pull-left">favorite</i>
                                <spring:message code="course.addFavourites"/>
                            </button>
                        </form>
                    </c:if>
                    <c:if test="${course.favedBy.contains(user)}">
                        <c:url var="url" value="/courses/${course.id}/unfav"/>
                        <form action="${url}" method="post">
                            <button class="btn btn-danger btn-sm">
                                <i class="material-icons pull-left">delete</i>
                                <spring:message code="course.removeFavourites"/>
                            </button>
                        </form>
                    </c:if>
                </div>
                <div class="row">
                    <div class="col-lg-6 mb-4">
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="font-weight-bold m-0"><spring:message code="course.announcements"/></h6>
                            </div>
                            <ul class="list-group list-group-flush">
                                <c:if test="${announcements.size() == 0}">
                                    <li class="list-group-item">
                                        <spring:message code="noAnnounces"/>
                                    </li>
                                </c:if>
                                <c:forEach var="announcement" items="${announcements}">
                                    <li class="list-group-item">
                                        <div class="row align-items-center no-gutters">
                                            <div class="col mr-2">
                                                <h6 class="mb-0"><strong><c:out value="${announcement.title}"/></strong></h6>
                                                <span><c:out value="${announcement.summary}"/><br></span>
                                            </div>
                                            <div class="col-auto">
                                                <a href="<c:url value="/announcements/${announcement.id}"/>">
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
                                <h6 class="font-weight-bold m-0"><spring:message code="polls"/></h6>
                            </div>

<%--                            <c:if test="${polls.size() == 0}">--%>
<%--                                <li class="list-group-item">--%>
<%--                                    <spring:message code="noPolls"/>--%>
<%--                                </li>--%>
<%--                            </c:if>--%>
<%--                            --%>
<%--                            <c:forEach var="poll" items="${polls}">--%>
<%--                                <ul class="list-group list-group-flush">--%>
<%--                                    <li class="list-group-item">--%>
<%--                                        <div class="row align-items-center no-gutters">--%>
<%--                                            <div class="col mr-2">--%>
<%--                                                <h6 class="mb-0"><strong><c:out value="${poll.name}"/></strong></h6>--%>
<%--                                                <span class="text-xs"><c:out value="${poll.description}"/></span></div>--%>
<%--                                            <div class="col-auto">--%>
<%--                                                <a href="<c:url value="/polls/${poll.id}"/>">--%>
<%--                                                    <i class="material-icons">keyboard_arrow_right</i>--%>
<%--                                                </a>--%>
<%--                                            </div>--%>
<%--                                        </div>--%>
<%--                                    </li>--%>
<%--                                </ul>--%>
<%--                            </c:forEach>--%>
                        </div>

                    </div>

                </div>
                <div class="row">
                    <div class="col-lg-6 col-xl-12 offset-xl-0 mb-4">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">
                                <div class="table-responsive table mt-2" id="dataTable-1" role="grid" aria-describedby="dataTable_info" style="margin-top: 32px;background: #ffffff;">
                                    <table class="table my-0" id="dataTable">
                                        <thead>
                                        <tr>
                                            <th><spring:message code="form.title"/></th>
                                            <th><spring:message code="form.type"/></th>
                                            <th><spring:message code="date"/></th>
                                            <th><spring:message code="author"/></th>
                                            <th><spring:message code="form.link"/></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="content" items="${contents}">
                                            <c:set var="content" value="${content}" scope="request"/>
                                            <tr>
                                                <c:url var="url" value="/contents/${content.id}"/>
                                                <td><a href="${url}"><c:out value="${content.name}"/></a></td>
                                                <td><spring:message code="enum.contenttype.${content.contentType}"/></td>
                                                <td><c:out value="${content.uploadDate}"/></td>
                                                <td>
                                                    <c:set var="owner" value="${contentOwners.get(content.id)}"/>
                                                    <c:if test="${owner}">
                                                        <c:url var="profileUrl" value="/profile/${owner.email}"/>
                                                        <a href="${profileUrl}"><c:out value="${owner.fullName}"/></a>
                                                    </c:if>
                                                </td>

                                                <td>
                                                    <a class="btn btn-link" target="_blank" rel="noopener noreferrer"
                                                       href="<c:url value='${content.link}'/>" role="button">
                                                        <spring:message code="form.link"/>
                                                    </a>
                                                </td>

                                                <c:if test="${canDeleteContent}">
                                                    <td>
                                                        <c:url var="url" value="/contents/${content.id}/delete"/>
                                                        <form action="${url}" method="post">
                                                            <button type="submit" class="btn btn-icon" style="color:red">
                                                                <i class="material-icons">delete</i>
                                                            </button>
                                                        </form>
                                                    </td>
                                                </c:if>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                        <tfoot>
                                        <tr></tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/scripts.jsp"/>

</body>

</html>