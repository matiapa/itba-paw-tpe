<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Career>" scope="request" id="careers"/>
<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Course>" scope="request" id="courses"/>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Anuncios</title>

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
                            <form:label path="title">Título: </form:label>
                            <form:input type="text" path="title" cssClass="form-control"/>
                            <form:errors path="title" cssClass="invalid-feedback" element="div"/>
                        </div>

                        <div class="form-group">
                            <form:label path="summary">Breve descripción:</form:label>
                            <form:input type="text" path="summary" cssClass="form-control"/>
                            <form:errors path="summary" cssClass="invalid-feedback" element="div"/>
                        </div>

                        <div class="form-group">
                            <form:label path="content">Contenido: </form:label>
                            <form:textarea path="content" cssClass="form-control"/>
                            <form:errors path="content" cssClass="invalid-feedback" element="div"/>
                        </div>

                        <div>
                            <button id="submitAnnouncement" type="submit" class="btn btn-primary ml-3">Enviar</button>
                        </div>
                    </form:form>
                </div>

            </div>

            <div class="overlay"></div>
            <div class="spanner">
                <div class="loader"></div>
                <p>Agregando anuncio</p>
            </div>
        </div>


    </div>

    <jsp:include page="../common/scripts.jsp"/>

    <script src="<c:url value="/assets/js/announcements.js"/>"></script>

    <script src="<c:url value="/assets/js/popper.min.js"/>" ></script>
    <script src="<c:url value="/assets/bootstrap/js/bootstrap-select.min.js"/>"></script>

</body>

</html>