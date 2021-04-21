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
</head>

<body id="page-top">

    <div id="wrapper">

        <jsp:include page="../common/navbar.jsp"/>

        <div class="d-flex flex-column" id="content-wrapper">
            <div id="content">

                <jsp:include page="../common/header.jsp"/>

                <div class="col-lg-6 col-xl-12 mb-4">

                    <form method="get">
                        <select class="custom-select my-1 mr-sm-2" name="careerId">
                            <option ${selectedCareer == null ? 'selected' : ''} value="">
                                Elegí una carrera...
                            </option>
                            <c:forEach var="career" items="${careers}">
                                <option ${selectedCareer.id == career.id ? 'selected' : ''} value="${career.id}">
                                        ${career.name}
                                </option>
                            </c:forEach>
                        </select>

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
                                    <option ${selectedYear == null ? 'selected' : ''} value="-1">
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
                                    <option ${selectedQuarter == null ? 'selected' : ''} value="-1">
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
                               <input type="submit" class="btn btn-primary" value="Buscar">
                            </div>

                        </div>
                    </form>

                    <c:choose>
                        <c:when test="${chatgroups.size() > 0}">
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
                        <c:otherwise>
                            <div class="text-center">
                                <i class="fa fa-question-circle" style="margin-top: 32px;font-size: 32px;"></i>
                                <p style="margin-top: 16px;">Por favor, elegí una carrera para ver los grupos</p>
                            </div>
                        </c:otherwise>
                    </c:choose>

                </div>

            </div>

            <div class="fab">
                <a href="<c:url value="/chats/create"/>">
                    <i class="material-icons" style="font-size: 32px;color: rgb(255,255,255);">add</i>
                </a>
            </div>

        </div>

    </div>

    <jsp:include page="../common/scripts.jsp"/>

</body>

</html>