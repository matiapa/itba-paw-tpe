<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Career>" scope="request" id="careers"/>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Cursos</title>

    <jsp:include page="../common/styles.jsp"/>
</head>

<body id="page-top">
    <div id="wrapper">
        <jsp:include page="../common/navbar.jsp"/>

        <div class="d-flex flex-column" id="content-wrapper">
            <div id="content">

                <jsp:include page="../common/header.jsp"/>

                <div class="container-fluid">
                    <div>
                        <ul class="nav nav-tabs" role="tablist">
                            <c:forEach var="career" items="${careers}">
                                <c:set var="career" value="${career}" scope="request"/>
                                <li class="nav-item" role="presentation">
                                    <a class="nav-link" role="tab" href="<c:url value='/courses?careerCode=${career.code}'/>">
                                        <c:out value="${career.name}"/>
                                    </a>
                                </li>
                            </c:forEach>

                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" role="tabpanel" id="tab-1">
                                <div role="tablist" id="accordion-1" style="margin-top: 32px;">
                                    <c:choose>
                                        <c:when test="${selectedCareer != null}">
                                            <c:forEach var="CourseList" items="${careerCourses}">
                                                <div class="card">
                                                    <div class="card-header" role="tab">
                                                        <h5 class="mb-0"><a data-toggle="collapse" aria-expanded="true" aria-controls="accordion-1 .item-${CourseList.key}" href="#accordion-1 .item-${CourseList.key}">Año <c:out value="${CourseList.key}"/></a></h5>
                                                    </div>
                                                    <div class="collapse show item-${CourseList.key}" role="tabpanel" data-parent="#accordion-1">

                                                        <div class="card-body">
                                                            <div class="table-responsive">
                                                                <table class="table">
                                                                    <thead>
                                                                        <tr>
                                                                            <th>Código</th>
                                                                            <th>Nombre</th>
                                                                            <th>Cuatrimestre</th>
                                                                            <th>Créditos</th>
                                                                            <th>Link</th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                        <c:forEach var="course" items="${CourseList.value}">
                                                                            <tr>
                                                                                <td><c:out value="${course.id}"/></td>
                                                                                <td><c:out value="${course.name}"/></td>
                                                                                <td><c:out value="${course.semester}"/></td>
                                                                                <td><c:out value="${course.credits}"/></td>
                                                                                <td><a class="btn btn-link" type="button"
                                                                                       href="<c:url value='/courses/detail?id=${course.id}'/>"
                                                                                >Abrir</a></td>
                                                                            </tr>
                                                                        </c:forEach>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="text-center"><i class="fa fa-question-circle" style="margin-top: 32px;font-size: 32px;"></i>
                                                <p style="margin-top: 16px;">Por favor, elegí una carrera para ver los Cursos</p>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <div id="content-1"></div>
    </div>

    <jsp:include page="../common/scripts.jsp"/>
</body>

</html>