<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Registro</title>

    <jsp:include page="../common/styles.jsp"/>

    <link rel="stylesheet" href="<c:url value="/assets/bootstrap/css/bootstrap-select.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/css/overlay.css"/>"></head>
</head>

<body class="bg-gradient-primary" style="background: rgb(2,86,138);">
    <div class="container">
            <div class="card shadow-lg o-hidden border-0 my-5">
                <div class="card-body p-0">
                    <c:url value="/register" var="postFormUrl"/>

                    <form:form modelAttribute="UserForm" action="${postFormUrl}" method="post">
                        <div class="col">
                            <div class="row">

                                <div class="col-lg-5 d-none d-lg-flex">

                                    <div class="p-5">
                                        <div class="text-center">
                                            <h4 class="text-dark mb-4">Tu información</h4>
                                        </div>

                                        <div class="row mt-3">
                                            <div class="col-sm-6 mb-3 mb-sm-0">
                                                <form:input class="form-control form-control-user" type="text" id="exampleFirstName" placeholder="Nombre" path="name" value="${user.name}" />
                                                <form:errors path="name" cssStyle="color: red" element="p"/>
                                            </div>
                                            <div class="col-sm-6">
                                                <form:input class="form-control form-control-user" type="text" id="exampleLastName" placeholder="Apellido" path="surname" value="${user.surname}" />
                                                <form:errors path="surname" cssStyle="color: red" element="p"/>
                                            </div>
                                        </div>

                                        <div class="row mt-3">
                                            <div class="col-sm-12">
                                                <form:input class="form-control form-control-user" type="email" id="exampleInputEmail"  placeholder="Email" path="email" value="${user.email}" />
                                                <form:errors path="email" cssStyle="color: red" element="p"/>
                                            </div>
                                        </div>

                                        <div class="row mt-3">
                                            <div class="col-sm-12">
                                                <form:input class="form-control form-control-user" type="password" id="examplePassword" placeholder="Password" path="password" value="${user.password}"  />
                                                <form:errors path="password" cssStyle="color: red" element="p"/>
                                            </div>
                                        </div>

                                        <div class="row mt-3">
                                            <div class="col-sm-12">
                                                <form:input class="form-control form-control-user" type="text" id="exampleLegajo" placeholder="Legajo" path="id" value="${user.id}" />
                                                <form:errors path="id" cssStyle="color: red" element="p"/>
                                            </div>
                                        </div>

                                        <div class="row mt-3">
                                            <div class="col-sm-12">

                                                <form:label path="careerCode"  name="career">Nombre Carrera: </form:label>
                                                <form:select path= "careerCode" class="selectpicker" data-live-search="true" title="Elegí una Carrera">
                                                    <c:forEach var="career" items="${careerList}">
                                                        <option ${career.equals(selectedCareer) ? 'selected' : ''}
                                                                path="career" value="${career.code}" data-tokens="${career.name}">${career.name}</option>
                                                    </c:forEach>
                                                </form:select>
                                                <form:errors path="careerCode" cssStyle="color: red" element="p"/>

                                            </div>
                                        </div>

                                        <div class="text-center"></div>
                                        <div class="text-center"></div>
                                    </div>
                                </div>

                                <div class="col-lg-7">
                                    <div class="p-5" style="border-left: 1px solid rgb(232,233,240) ;">
                                        <div class="text-center">
                                            <h4 class="text-dark mb-4">Materias que cursás</h4>
                                        </div>

                                        <c:forEach var ="i" begin="0" end ="4">
                                            <div class="row">
                                                <div class="form-group">
                                                    <form:label path="courses[${i}]"  name="courses[${i}]">Nombre Materia: </form:label>
                                                    <form:select path= "courses[${i}]" class="selectpicker" data-live-search="true" title="Elegí un curso">
                                                        <c:forEach var="course" items="${courseList}">
                                                            <option ${course.equals(selectedCourse) ? 'selected' : ''}
                                                                    path="courses[${i}]" value="${course.id}" data-tokens="${course.name}">${course.name}</option>
                                                        </c:forEach>
                                                    </form:select>
                                                    <form:errors path="courses[${i}]" cssStyle="color: red" element="p"/>
                                                </div>
                                            </div>
                                        </c:forEach>
                                        <form:errors path="courses" cssStyle="color: red" element="p"/>
                                    </div>
                                </div>

                            </div>

                            <div class="row mt-5 mb-3 pl-5 pr-5">
                                <input class="btn btn-primary btn-block" type="submit" value="Registrarse">
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>

        <jsp:include page="../common/scripts.jsp"/>

        <script src="<c:url value="/assets/js/popper.min.js"/>" ></script>
        <script src="<c:url value="/assets/bootstrap/js/bootstrap-select.min.js"/>"></script>

</body>

</html>