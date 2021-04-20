<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>


<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Apuntes</title>

    <jsp:include page="../common/styles.jsp"/>

    <link rel="stylesheet" href="<c:url value="/assets/css/overlay.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/bootstrap/css/bootstrap-select.min.css"/>">
</head>

<body id="page-top">
    <div id="wrapper">

        <jsp:include page="../common/navbar.jsp"/>

        <div class="d-flex flex-column" id="content-wrapper">
            <div id="content">

                <jsp:include page="../common/header.jsp"/>

                <div class="container-fluid">
                    <form:form modelAttribute="createForm" method="post">


                        <div class="form-group">

                            <form:select path="courseId" name="courseId" class="selectpicker" data-live-search="true" title="Elegí un Curso">
                                <c:forEach var="course" items="${courses}">
                                    <option ${course.equals(selectedCourse) ? 'selected' : ''}
                                            value="${course.id}" data-tokens="${course.name}">${course.name}</option>
                                </c:forEach>
                            </form:select>
                            <form:errors path="courseId" cssClass="invalid-feedback" element="div"/>
                        </div>



                        <div class="form-group">
                            <form:label path="name">Título: </form:label>
                            <form:input type="text" path="name" cssClass="form-control"/>
                            <form:errors path="name" cssClass="invalid-feedback" element="div"/>
                        </div>

                        <div class="form-group">
                            <form:label path="link">Link:</form:label>
                            <form:input type="text" path="link" cssClass="form-control"/>
                            <form:errors path="link" cssClass="invalid-feedback" element="div"/>
                        </div>

                        <div class="form-group">
                            <form:label path="description">Breve descripción: </form:label>
                            <form:textarea path="description" cssClass="form-control"/>
                            <form:errors path="description" cssClass="invalid-feedback" element="div"/>
                        </div>

                        <div class="form-group">
                            <form:label path="contentType">Tipo: </form:label>
                            <form:select path="contentType" class="custom-select my-1 mr-sm-2" name="contentType">
                                <option selected value="">Tipo</option>
                                <option value="exam">Exámen</option>
                                <option value="guide">Guía</option>
                                <option value="resume">Resúmen</option>
                                <option value="note">Apunte</option>
                                <option value="other">Otro</option>
                            </form:select>
                            <form:errors path="contentType" cssClass="invalid-feedback" element="div"/>
                        </div>

                        <div class="form-group">
                            <form:label path="contentDate">Fecha del Apunte: (Opcional) </form:label>
                            <input class="border rounded-0" path="contentDate" type="date" name="contentDate">
                            <form:errors path="contentDate" cssClass="invalid-feedback" element="div"/>
                        </div>

                        <div>
                            <button id="submitContent" type="submit" class="btn btn-primary ml-3">Enviar</button>
                        </div>
                    </form:form>
                </div>

            </div>

            <div class="overlay"></div>
            <div class="spanner">
                <div class="loader"></div>
                <p>Agregando contenido</p>
            </div>
        </div>


    </div>

    <jsp:include page="../common/scripts.jsp"/>

    <script src="<c:url value="/assets/js/content.js"/>"></script>

    <script src="<c:url value="/assets/js/jquery.easing.js"/>"></script>
    <script src="<c:url value="/assets/js/popper.min.js"/>" ></script>
    <script src="<c:url value="/assets/bootstrap/js/bootstrap-select.min.js"/>"></script>
</body>

</html>