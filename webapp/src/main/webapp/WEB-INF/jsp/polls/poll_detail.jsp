<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="ar.edu.itba.paw.models.Poll" scope="request" id="poll"/>


<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>new-design</title>
    <link rel="icon" type="image/png" sizes="311x311" href="assets/img/logo-tran-white.png">
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i">
    <link rel="stylesheet" href="assets/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="assets/fonts/material-icons.min.css">
    <link rel="stylesheet" href="assets/fonts/simple-line-icons.min.css">
    <link rel="stylesheet" href="assets/fonts/fontawesome5-overrides.min.css">
    <link rel="stylesheet" href="assets/css/buttons.css">
    <link rel="stylesheet" href="assets/css/cards.css">
    <link rel="stylesheet" href="assets/css/colors.compiled.css">
    <link rel="stylesheet" href="assets/css/fab.css">
    <link rel="stylesheet" href="assets/css/nav-tabs.css">
    <link rel="stylesheet" href="assets/css/sidebar.css">
    <jsp:include page="../common/styles.jsp"/>
</head>


<body>
    <div id="wrapper">
        <jsp:include page="../common/navbar.jsp"/>
        <div class="d-flex flex-column" id="content-wrapper">
            <div id="content">
                <jsp:include page="../common/header.jsp"/>

                    <div class="container-fluid">
                        <div class="col-lg-9 col-xl-12 mb-4">
                            <div class="card shadow mb-4" style="margin-top: 32px;">
                                <div class="card-header py-3">
                                    <h6 class="font-weight-bold m-0"><c:out value="${poll.name}"/></h6>
                                </div>
                                <div class="col mr-2" style="padding-top: 16px;padding-right: 24px;padding-left: 24px;padding-bottom: 16px;">
                                    <p style="padding-top: 0px;"><c:out value="${poll.description}"/></p>
                                    <div class="row">
                                        <c:forEach var="pollOption" items="${poll.options}">
                                            <div class="col">
                                                <div class="form-check"><input class="form-check-input" type="radio" id="formCheck-3"><label class="form-check-label" for="formCheck-1"><c:out value="${pollOption.value}"/></label></div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                                <div class="row align-items-end" style="margin-top: 32px;">
                                    <div class="col"><button class="btn btn-primary" type="button">Votar</button></div>
                                    <div class="col text-right"><span class="text-xs">Publicado por <c:out value="${poll.submittedBy.name}"/> el <c:out value="${poll.creationDate}"/> REVISAR a las 12:30 AM</span></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div id="content-1"></div>
                    <div class="container-fluid">
                        <div class="col-lg-9 col-xl-12 mb-4">
                            <div class="card shadow mb-4" style="margin-top: 32px;">
                                <div class="card-header py-3">
                                    <h6 class="font-weight-bold m-0">Resultados</h6>
                                </div>
                                <div class="col mr-2" style="padding-top: 16px;padding-right: 24px;padding-left: 24px;padding-bottom: 16px;">
                                    <div class="row">
                                        <div class="col"><span>3 Votos : Mejor&nbsp;&nbsp;</span></div>
                                    </div>
                                    <div class="row">
                                        <div class="col"><span>3 Votos : Igual&nbsp;&nbsp;</span></div>
                                    </div>
                                    <div class="row">
                                        <div class="col"><span>3 Votos : Peor&nbsp;&nbsp;</span></div>
                                    </div>
                                    <div class="row align-items-end" style="margin-top: 32px;">
                                        <div class="col text-right"><span class="text-xs">Publicado por Vida Universitaria el 07/04/2021 a las 12:30 AM</span></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="fab">
                <i class="material-icons" style="font-size: 32px;color: rgb(255,255,255);">add</i>
            </div>
        </div>
    </div>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/chart.min.js"></script>
    <script src="assets/js/bs-init.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.js"></script>
    <script src="assets/js/theme.js"></script>
</body>

</html>