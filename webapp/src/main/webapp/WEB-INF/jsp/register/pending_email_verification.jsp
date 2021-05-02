<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Registro Exitoso</title>

    <jsp:include page="../common/styles.jsp"/>

    <link rel="stylesheet" href="<c:url value="/assets/bootstrap/css/bootstrap-select.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/css/overlay.css"/>"></head>
</head>

<body class="bg-gradient-primary" style="background: rgb(2,86,138);">
<div class="container">
    <div class="card shadow-lg o-hidden border-0 my-5">
        <div class="card-body">
            <h1>¡Listo!</h1>

            <h4 class="mt-3">Antes de poder acceder deberás verificar tu dirección de email</h4>
            <h4>Seguí las instrucciones en el mail que recibirás en los próximos minutos</h4>
        </div>
    </div>
</div>

<jsp:include page="../common/scripts.jsp"/>

</body>

</html>