<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.ChatGroup>" scope="request" id="chatgroups"/>
<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Career>" scope="request" id="careers"/>
<jsp:useBean type="ar.edu.itba.paw.models.ChatGroup.ChatPlatform[]" scope="request" id="platforms"/>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Grupos de chat</title>

    <jsp:include page="../common/styles.jsp"/>

    <link rel="stylesheet" href="<c:url value="/assets/css/overlay.css"/>"></head>
</head>

<body id="page-top">

    <div id="wrapper">

        <jsp:include page="../common/navbar.jsp"/>

        <div class="d-flex flex-column" id="content-wrapper">
            <div id="content">

                <jsp:include page="../common/header.jsp"/>

                <div class="container-fluid">
                    <div class="col-lg-6 col-xl-12 mb-4">

                        <div class="mb-3" align="right">
                            <a href="#popup" data-toggle="modal">
                                <button class="btn btn-primary btn-sm">
                                    <i class="material-icons pull-left">add</i>
                                    Agregar grupo
                                </button>
                            </a>
                        </div>

                        <form method="get" id="chatListFilterForm">
                            <select class="custom-select my-1 mr-sm-2" name="careerCode" id="careerCode">
                                <option ${selectedCareer == null ? 'selected' : ''} value="">
                                    Elegí una carrera...
                                </option>
                                <c:forEach var="career" items="${careers}">
                                    <option ${selectedCareer.code.equals(career.code) ? 'selected' : ''} value="${career.code}">
                                            ${career.name}
                                    </option>
                                </c:forEach>
                            </select>

                            <c:if test="${selectedCareer != null}">
                                <div class="row align-items-center" style="margin-top: 16px;margin-bottom: 32px;">

                                    <div class="col-xl-2">
                                        <select class="custom-select my-1 mr-sm-2" name="platform">
                                            <option ${selectedPlatform == null ? 'selected' : ''} value="">
                                                Plataforma
                                            </option>
                                            <c:forEach var="platform" items="${platforms}">
                                                <option ${selectedPlatform == platform ? 'selected' : ''} value="${platform}">
                                                        ${platformsTranslate.get(platform)}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="col-xl-1">
                                        <select class="custom-select my-1 mr-sm-2" name="year">
                                            <option ${selectedYear == null ? 'selected' : ''} value="">
                                                Año
                                            </option>
                                            <c:forEach var="year" items="${years}">
                                                <option ${selectedYear == year ? 'selected' : ''} value="${year}">
                                                        ${year}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="col-xl-2">
                                        <select class="custom-select my-1 mr-sm-2" name="quarter">
                                            <option ${selectedQuarter == null ? 'selected' : ''} value="">
                                                Cuatrimestre
                                            </option>
                                            <c:forEach var="quarter" items="${quarters}">
                                                <option ${selectedQuarter == quarter ? 'selected' : ''} value="${quarter}">
                                                        ${quartersTranslate.get(quarter)}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="col-xl-2">
                                        <input type="submit" class="btn btn-primary" value="Filtrar">
                                    </div>

                                </div>
                            </c:if>
                        </form>

                        <c:choose>
                            <c:when test="${selectedCareer != null && chatgroups.size() > 0}">
                                <div class="table-responsive table mt-2" id="dataTable-1" role="grid"
                                     aria-describedby="dataTable_info" style="margin-top: 32px;background: #ffffff;">
                                    <table class="table my-0" id="dataTable">
                                        <thead>
                                            <tr>
                                                <th>Plataforma</th>
                                                <th>Año</th>
                                                <th>Cuatrimestre</th>
                                                <th>Link</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="chatgroup" items="${chatgroups}">
                                                <tr>
                                                    <td>${chatgroup.name}</td>
                                                    <td>${chatgroup.creationYear}</td>
                                                    <td>${chatgroup.creationQuarter}</td>
                                                    <td>
                                                        <a class="btn btn-link" href='<c:out value="${chatgroup.link}"/>' target="_blank" rel="noopener noreferrer">Abrir</button>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                        <tfoot>
                                            <tr></tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </c:when>
                            <c:when test="${selectedCareer != null && chatgroups.size() == 0}">
                                <div class="text-center mt-5">
                                    <i class="fa fa-question-circle" style="margin-top: 32px;font-size: 32px;"></i>
                                    <p style="margin-top: 16px;">Ups, no hay nada por acá</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="text-center mt-5">
                                    <i class="fa fa-question-circle" style="margin-top: 32px;font-size: 32px;"></i>
                                    <p style="margin-top: 16px;">Por favor, elegí una carrera para ver los grupos</p>
                                </div>
                            </c:otherwise>
                        </c:choose>

                    </div>
                    <div style=" position: absolute; bottom: 0; width: 100%;">
                        <nav aria-label="navigation" style="margin: auto">
                            <ul class="pagination justify-content-center">
                                <c:choose>
                                    <c:when test="${pager.page > 0}">
                                        <li class="page-item"><a class="page-link" href="/chats?page=${pager.page - 1}">Previous</a></li>
                                    </c:when>
                                </c:choose>
                                <c:forEach begin="1" step="1" end="${pager.size / pager.limit}" var="num">
                                    <li class="page-item">
                                        <a class="page-link" href="<c:url value="/chats?page=${num - 1}"/>">
                                            <c:out value="${num}"/>
                                        </a>
                                    </li>
                                </c:forEach>
                                <c:choose>
                                    <c:when test="${pager.page + 1 < (pager.size / pager.limit)}">
                                        <li class="page-item"><a class="page-link" href="?page=${pager.page + 1}">Next</a></li>
                                    </c:when>
                                </c:choose>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>

        </div>

    </div>

    <jsp:include page="chat_create.jsp"/>

    <jsp:include page="../common/scripts.jsp"/>

    <c:if test="${showCreateForm}">
        <script>$('#popup').modal('show');</script>
    </c:if>

    <script src="<c:url value="/assets/js/chat.js"/>"></script>

</body>

</html>