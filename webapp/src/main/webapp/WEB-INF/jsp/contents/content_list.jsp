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
                    <div class="col-lg-12 col-xl-12 mb-6">
                        <form id="contentListFilterForm" action="<c:url value='/contents'/>" method="get">

                            <div style="border: thin solid black">
                                <select id="courseId" name="courseId" class="selectpicker" data-live-search="true"
                                        title="Elegí un curso" data-width="100%">
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
                                            <option value="">Tipo de material</option>
                                            <option value="exam">Exámen</option>
                                            <option value="guide">Guía</option>
                                            <option value="resume">Resúmen</option>
                                            <option value="note">Apunte</option>
                                            <option value="other">Otro</option>
                                        </select>
                                    </div>

                                    <div class="col-xl-3">
                                        <label>Desde</label>
                                        <input class="form-control-sm" style="border: thin solid grey" type="date" name="minDate">
                                    </div>

                                    <div class="col-xl-3">
                                        <label>Hasta</label>
                                        <input class="form-control-sm" style="border: thin solid grey" type="date" name="maxDate">
                                    </div>

                                    <div class="col">
                                        <button id="courseSearchBtn" type="submit" class="btn btn-primary ml-3">Filtrar</button>
                                    </div>
                                </div>
                            </c:if>
                        </form>


                        <c:choose>
                            <c:when test="${selectedCourse != null}">
                                <hr class="mb-5 mt-5"/>
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
                                                <td><c:out value="${contentTypeEnumMap.get(content.contentType)}"/></td>
                                                <td><c:out value="${content.uploadDate}"/></td>
                                                <td><img class="rounded-circle mr-2" width="30" height="30" src="<c:url value="/assets/img/avatars/avatar.png"/>">
                                                    <c:out value="${content.submitter.name}"/></td>
                                                <td><a class="btn btn-link" target="_blank" rel="noopener noreferrer" href="<c:url value='${content.link}'/>" role="button">Link</a></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                        <tfoot>
                                        <tr></tr>
                                        </tfoot>
                                    </table>
                                </div>
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
        <a href="#popup" data-toggle="modal">
            <i class="material-icons" style="font-size: 32px;color: rgb(255,255,255);">add</i>
        </a>
    </div>

    <jsp:include page="content_create.jsp"/>

    <jsp:include page="../common/scripts.jsp"/>

    <c:if test="${showCreateForm}">
        <script>$('#popup').modal('show');</script>
    </c:if>

    <script src="<c:url value="/assets/js/content.js"/>"></script>

    <script src="<c:url value="/assets/js/popper.min.js"/>" ></script>
    <script src="<c:url value="/assets/bootstrap/js/bootstrap-select.min.js"/>"></script>
</body>

</html>