<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Announcement>" scope="request" id="announcements"/>
<jsp:useBean type="ar.edu.itba.paw.models.HolderEntity" scope="request" id="filterBy"/>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Anuncios</title>

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
                                <input type="checkbox" class="custom-control-input" id="showHiddenSwitch">
                                <label class="custom-control-label" for="showHiddenSwitch">Ver anuncios ocultos</label>
                            </div>
                            <div class="col col-xl-2">
                                <a href="#popup" data-toggle="modal">
                                    <button class="btn btn-primary btn-sm">
                                        <i class="material-icons pull-left">add</i>
                                        Agregar anuncio
                                    </button>
                                </a>
                            </div>
                        </div>

                        <ul class="nav nav-tabs" role="tablist">
                            <li class="nav-item" role="presentation">
                                <a class="nav-link ${filterBy == "general" ? 'active' : ''}" role="tab"
                                   href="<c:url value="/announcements?filterBy=general"/>">Generales</a>
                            </li>
                            <li class="nav-item" role="presentation">
                                <a class="nav-link ${filterBy == "career" ? 'active' : ''}" role="tab"
                                   href="<c:url value="/announcements?filterBy=career"/>">Por carrera</a>
                            </li>
                            <li class="nav-item" role="presentation">
                                <a class="nav-link ${filterBy == "course" ? 'active' : ''}" role="tab"
                                   href="<c:url value="/announcements?filterBy=course"/>">Por curso</a>
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
                                                <p style="margin-top: 16px;">Ups, no hay nada por acá</p>
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
                                                <p style="margin-top: 16px;">Ups, no hay nada por acá</p>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="text-center mt-5">
                                                <i class="fa fa-question-circle" style="margin-top: 32px;font-size: 32px;"></i>
                                                <p style="margin-top: 16px;">Por favor, elegí una carrera para ver los anuncios</p>
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
                                            <select required id="courseId" name="courseId" class="selectpicker" data-live-search="true"
                                                    data-width="100%" title="Elegí un curso">
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
                                                <p style="margin-top: 16px;">Ups, no hay nada por acá</p>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="text-center mt-5">
                                                <i class="fa fa-question-circle" style="margin-top: 32px;font-size: 32px;"></i>
                                                <p style="margin-top: 16px;">Por favor, elegí un curso para ver los anuncios</p>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div style=" position: absolute; bottom: 0; width: 100%;">
                                <nav aria-label="navigation" style="margin: auto">
                                    <ul class="pagination justify-content-center">
                                        <c:choose>
                                            <c:when test="${pager.page != 0}">
                                                <li class="page-item"><a class="page-link" href="/announcements?page=${pager.page - 1}">Previous</a></li>
                                            </c:when>
                                        </c:choose>
                                        <c:forEach begin="1" step="1" end="${pager.size / pager.limit + 1}" var="num">
                                            <li class="page-item">
                                                <a class="page-link" href="<c:url value="/announcements?page=${num - 1}"/>">
                                                <c:out value="${num}"/>
                                                </a>
                                            </li>
                                        </c:forEach>
                                        <c:choose>
                                            <c:when test="${pager.page + 1 < (pager.size / pager.limit)}">
                                                <li class="page-item"><a class="page-link" href="/announcements?page=${pager.page + 1}">Next</a></li>
                                            </c:when>
                                        </c:choose>
                                    </ul>
                                </nav>
                            </div>
                        </div>
                    </div>

                </div>

            </div>
        </div>

    </div>

    <div hidden id="errorToast" class="toast" data-autohide="false"
         style="position: fixed; top: 0; right: 0; background-color: orange; color: white">
        <div class="toast-header">
            <strong class="mr-auto">Ups!</strong>
            <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="toast-body">
            Mensaje de error
        </div>
    </div>

<%--    <div class="fab">--%>
<%--        <a href="#popup" data-toggle="modal">--%>
<%--            <i class="material-icons" style="font-size: 32px;color: rgb(255,255,255);">add</i>--%>
<%--        </a>--%>
<%--    </div>--%>

    <jsp:include page="announcement_create.jsp"/>

    <jsp:include page="../common/scripts.jsp"/>

    <c:if test="${showCreateForm}">
        <script>$('#popup').modal('show');</script>
    </c:if>

    <script src="<c:url value="/assets/js/announcements.js"/>"></script>

    <script src="<c:url value="/assets/js/popper.min.js"/>" ></script>
    <script src="<c:url value="/assets/bootstrap/js/bootstrap-select.min.js"/>"></script>

</body>

</html>