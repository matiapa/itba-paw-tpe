<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="ar.edu.itba.paw.models.Course" scope="request" id="course"/>

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
            <div class="row">
                <div class="col-lg-6 mb-4">
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="font-weight-bold m-0">Anuncios recientes</h6>
                        </div>
                        <ul class="list-group list-group-flush">
                            <c:forEach var="announcement" items="${announcements}">
                                <li class="list-group-item">
                                    <div class="row align-items-center no-gutters">
                                        <div class="col mr-2">
                                            <h6 class="mb-0"><strong><c:out value="${announcement.title}"/></strong></h6>
                                            <span><c:out value="${announcement.summary}"/><br></span>
                                        </div>
                                        <div class="col-auto">
                                            <a href="<c:url value="/announcements/detail?id=${announcement.id}"/>">
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
                            <h6 class="font-weight-bold m-0">Encuestas</h6>
                        </div>
                        <c:forEach var="poll" items="${polls}">
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">
                                    <div class="row align-items-center no-gutters">
                                        <div class="col mr-2">
                                            <h6 class="mb-0"><strong><c:out value="${poll.name}"/></strong></h6>
                                            <span class="text-xs"><c:out value="${poll.description}"/></span></div>
                                        <div class="col-auto">
                                            <a href="<c:url value="/polls/detail?id=${poll.id}"/>">
                                                <i class="material-icons">keyboard_arrow_right</i>
                                            </a>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-6 col-xl-12 offset-xl-0 mb-4">
                    <div class="card shadow mb-4">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">
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
                                                <td>
                                                    <img class="rounded-circle mr-2" width="30" height="30"
                                                         src="<c:url value="/assets/img/avatars/avatar.png"/>">
                                                    <c:out value="${content.submitter.name}"/>
                                                 </td>
                                                <td><a class="btn btn-link" href="<c:url value='${content.link}'/>" role="button">Link</a></td>
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
    </div>

    <jsp:include page="../common/scripts.jsp"/>

</body>

</html>