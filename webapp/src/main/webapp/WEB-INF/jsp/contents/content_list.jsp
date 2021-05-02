<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><spring:message code="contents"/></title>

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
                    <div class="col-lg-12 col-xl-12 mb-6">
                        <div class="mb-4" align="right">
                            <a href="#popup" data-toggle="modal">
                                <button class="btn btn-primary btn-sm">
                                    <i class="material-icons pull-left">add</i>
                                    <spring:message code="content.add"/>
                                </button>
                            </a>
                        </div>

                        <form id="contentListFilterForm" action="<c:url value='/contents'/>" method="get">

                            <div style="border: thin solid black">
                                <select id="courseId" name="courseId" class="selectpicker" data-live-search="true"
                                        title=<spring:message code="chooseCourse"/> data-width="100%">
                                    <c:forEach var="course" items="${courses}">
                                        <option ${course.equals(selectedCourse) ? 'selected' : ''}
                                                value="${course.id}" data-tokens="${course.name}">${course.name}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <c:if test="${selectedCourse != null}">
                                <div class="row align-items-center" style="margin-top: 16px;margin-bottom: 32px;">
                                    <div class="col-xl-2">
                                        <select class="form-control-sm" style="border: thin solid grey"
                                                name="contentType">
                                            <option value=""><spring:message code="form.type"/></option>
                                            <option value="exam"><spring:message code="exam"/></option>
                                            <option value="guide"><spring:message code="guide"/></option>
                                            <option value="resume"><spring:message code="resume"/></option>
                                            <option value="note"><spring:message code="note"/></option>
                                            <option value="other"><spring:message code="other"/></option>
                                        </select>
                                    </div>

                                    <div class="col-xl-3">
                                        <label><spring:message code="since"/></label>
                                        <input class="form-control-sm" style="border: thin solid grey" type="date" name="minDate">
                                    </div>

                                    <div class="col-xl-3">
                                        <label><spring:message code="until"/></label>
                                        <input class="form-control-sm" style="border: thin solid grey" type="date" name="maxDate">
                                    </div>

                                    <div class="col">
                                        <button id="courseSearchBtn" type="submit" class="btn btn-primary ml-3"><spring:message code="filter"/></button>
                                    </div>
                                </div>
                            </c:if>
                        </form>


                        <c:choose>
                            <c:when test="${selectedCourse != null && contents.size() > 0}">
                                <hr class="mb-5 mt-5"/>
                                <div class="table-responsive table mt-2" id="dataTable-1" role="grid" aria-describedby="dataTable_info" style="margin-top: 32px;background: #ffffff;">
                                    <table class="table my-0" id="dataTable">
                                        <thead>
                                        <tr>
                                            <th><spring:message code="form.description"/></th>
                                            <th><spring:message code="form.type"/></th>
                                            <th><spring:message code="date"/></th>
                                            <th><spring:message code="author"/></th>
                                            <th><spring:message code="form.link"/></th>

                                            <c:if test="${canDelete}">
                                                <th></th>
                                            </c:if>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="content" items="${contents}">
                                            <c:set var="content" value="${content}" scope="request"/>
                                            <tr>
                                                <td><c:out value="${content.name}"/></td>
                                                <td><spring:message code="enum.contenttype.${content.contentType}"/></td>
                                                <td><c:out value="${content.uploadDate}"/></td>
                                                <td>
                                                    <c:url var="profileUrl" value="/profile/${content.submitter.id}"/>
                                                    <a href="${profileUrl}"><c:out value="${content.submitter.name}"/></a>
                                                </td>

                                                <td>
                                                    <a class="btn btn-link" target="_blank" rel="noopener noreferrer"
                                                        href="<c:url value='${content.link}'/>" role="button">
                                                            <spring:message code="form.link"/>
                                                    </a>
                                                </td>

                                                <c:if test="${canDelete}">
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
                            </c:when>

                            <c:when test="${selectedCourse != null && contents.size() == 0}">
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

                    <jsp:include page="../common/paginator.jsp"/>

                    </div>
                </div>

            </div>
        </div>

    <jsp:include page="content_create.jsp"/>

    <jsp:include page="../common/scripts.jsp"/>

    <c:if test="${showCreateForm}">
        <script>$('#popup').modal('show');</script>
    </c:if>

    <script src="<c:url value="/assets/js/content.js"/>"></script>

</body>
</html>