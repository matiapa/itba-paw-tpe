<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Career>" scope="request" id="careers"/>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Crear chat</title>

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
                            <form:label path="name">Nombre del grupo</form:label>
                            <form:input type="text" path="name" class="form-control"/>
                            <form:errors path="name" cssClass="invalid-feedback" element="p"/>
                        </div>

                        <div class="form-group">
                            <form:label path="careerId">Carrera del grupo</form:label>

                            <form:select path="careerId" class="form-control">
                                <option value="">Elegí una carrera...</option>
                                <c:forEach var="career" items="${careers}">
                                    <form:option value="${career.id}">
                                        <c:out value="${career.name}"/>
                                    </form:option>
                                </c:forEach>
                            </form:select>

                            <form:errors path="careerId" cssClass="invalid-feedback" element="div"/>
                        </div>

                        <div class="form-group">
                            <form:label path="creationDate">Fecha de creación (dd-mm-yyyy)</form:label>
                            <form:input type="text" path="creationDate" class="form-control"/>
                            <form:errors path="creationDate" cssClass="invalid-feedback" element="p"/>
                        </div>

                        <div class="form-group">
                            <form:label path="platform">Plataforma del grupo</form:label>

                            <form:select path="platform" class="form-control">
                                <c:forEach var="platform" items="${platforms}">
                                    <option value="">Elegí una plataforma...</option>
                                    <form:option value="${platform}">
                                        <c:out value="${platformsTranslate.get(platform)}"/>
                                    </form:option>
                                </c:forEach>
                            </form:select>

                            <form:errors path="platform" cssClass="invalid-feedback" element="div"/>
                        </div>

                        <div class="form-group">
                            <form:label path="link">Link al grupo</form:label>
                            <form:input type="text" path="link" class="form-control"/>
                            <form:errors path="link" cssClass="invalid-feedback" element="p"/>
                        </div>


                        <div>
                            <button id="submitChatGroup" type="submit" class="btn btn-primary">Agregar</button>
                        </div>
                    </form:form>
                </div>

            </div>

            <div class="overlay"></div>
            <div class="spanner">
                <div class="loader"></div>
                <p>Agregando grupo de chat</p>
            </div>

        </div>

    </div>

    <jsp:include page="../common/scripts.jsp"/>

    <script src="<c:url value="/assets/js/chat.js"/>"></script>

    <script src="<c:url value="/assets/js/popper.min.js"/>" ></script>
    <script src="<c:url value="/assets/bootstrap/js/bootstrap-select.min.js"/>"></script>

</body>

</html>