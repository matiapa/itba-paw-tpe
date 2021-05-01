<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Inicio</title>

    <jsp:include page="common/styles.jsp"/>
</head>

<body id="page-top" class="sidebar-toggled">
<div id="wrapper">

    <jsp:include page="common/navbar.jsp"/>

    <div class="d-flex flex-column" id="content-wrapper">
        <div id="content">
            <div class="container-fluid" style="height: 100%;">
                <div class="col" style="height: 100%;">
                    <div class="row justify-content-center" style="margin-top: 100px">
                        <h1><c:out value="${code}"/></h1>
                    </div>

                    <div class="row justify-content-center mt-3">
                        <h3>¡Ups! <c:out value="${description}"/></h3>
                    </div>

                    <div class="row justify-content-center mt-3">
                        <c:url var="url" value="/"/>
                        <h6><a href="${url}">Volver al inicio</a></h6>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="common/scripts.jsp"/>

</body>

</html>