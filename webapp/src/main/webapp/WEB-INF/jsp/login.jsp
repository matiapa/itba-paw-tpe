<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Inicio de sesión</title>
    <link rel="icon" type="image/png" sizes="311x311" href="/assets/img/logo-tran-white.png">
    <link rel="stylesheet" href="/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i">
    <link rel="stylesheet" href="/assets/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="/assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="/assets/fonts/material-icons.min.css">
    <link rel="stylesheet" href="/assets/fonts/simple-line-icons.min.css">
    <link rel="stylesheet" href="/assets/fonts/fontawesome5-overrides.min.css">
    <link rel="stylesheet" href="/assets/css/buttons.css">
    <link rel="stylesheet" href="/assets/css/cards.css">
    <link rel="stylesheet" href="/assets/css/colors.compiled.css">
    <link rel="stylesheet" href="/assets/css/fab.css">
    <link rel="stylesheet" href="/assets/css/nav-tabs.css">
    <link rel="stylesheet" href="/assets/css/sidebar.css">
</head>

<body class="bg-gradient-primary" style="background: rgb(2,86,138);">
    <div class="container" style="width: 512px;">
        <div class="card shadow-lg o-hidden border-0 my-5">
            <div class="card-body p-0">
                <div class="col-lg-6 col-xl-12" style="text-align: center;"><img style="margin-top: 20px;width: 200px;" src="/assets/img/logo-tran-blue.png">
                    <div class="p-5">
                        <h4 class="text-dark mb-4">¡Bienvenido a ITBAHub!</h4>
                        <p class="text-center">Por favor, continuá con tu cuenta del ITBA</p>
                        <form action="/oauth2/authorization/google">
                            <input class="btn btn-primary" type="submit" value="Login"></button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="/assets/js/jquery.min.js"></script>
    <script src="/assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="/assets/js/chart.min.js"></script>
    <script src="/assets/js/bs-init.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.js"></script>
    <script src="/assets/js/theme.js"></script>
</body>

</html>