<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Inicio de sesión</title>

    <jsp:include page="common/styles.jsp"/>
</head>

<body class="bg-gradient-primary" style="background: rgb(2,86,138);">
    <div class="container" style="width: 512px;">
        <div class="card shadow-lg o-hidden border-0 my-5">
            <div class="card-body p-0">
                <div class="col-lg-6 col-xl-12" style="text-align: center;"><img style="margin-top: 20px;width: 200px;" src="<c:url value="/assets/img/logo-tran-blue.png"/>">
                    <div class="p-5">
                        <h4 class="text-dark mb-4">¡Bienvenido a ITBAHub!</h4>
                        <p class="text-center">Por favor, continuá con tu cuenta del ITBA</p>
                        <form action="<c:url value="/oauth2/authorization/google"/>">
                            <input class="btn btn-primary" type="submit" value="Login"></button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="common/scripts.jsp"/>
</body>

</html>