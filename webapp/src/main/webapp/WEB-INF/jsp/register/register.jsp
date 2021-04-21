<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Registro</title>
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
    <div class="container">
            <div class="card shadow-lg o-hidden border-0 my-5">
                <div class="card-body p-0">
                    <c:url value="/register" var="postFormUrl"/>
                    <form:form modelAttribute="UserForm" action="${postFormUrl}" method="post">
                        <div class="row">
                            <div class="col-lg-5 d-none d-lg-flex">

                                <div class="p-5">
                                    <div class="text-center">
                                        <h4 class="text-dark mb-4">Tu informacion</h4>
                                    </div>

                                    <div class="row">
                                        <div class="col-sm-6 mb-3 mb-sm-0">
                                            <form:input class="form-control form-control-user" type="text" id="exampleFirstName" placeholder="Nombre" path="name" value="${user.name}" disabled="true"/>
                                        </div>
                                        <div class="col-sm-6">
                                            <form:input class="form-control form-control-user" type="text" id="exampleLastName" placeholder="Apellido" path="surname" value="${user.surname}" disabled="true"/>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <form:input class="form-control form-control-user" type="email" id="exampleInputEmail"  placeholder="Email" path="email" value="${user.email}" disabled="true"/>
                                        </div>
                                    </div>
                                    <form:input class="form-control form-control-user" type="text" id="exampleLegajo" placeholder="Legajo" path="id" value="${user.id}" disabled="true"/>
                                    <input class="form-control form-control-user" type="text" id="exampleCareerName" placeholder="Carrera" value="${careerName}" disabled/>

                                    <div class="text-center"></div>
                                    <div class="text-center"></div>
                                </div>
                            </div>
                            <div class="col-lg-7">
                                <div class="p-5" style="border-left: 1px solid rgb(232,233,240) ;">
                                    <div class="text-center">
                                        <h4 class="text-dark mb-4">Materias que cursas</h4>
                                    </div>


                                </div>
                            </div>
                        </div>
                        <input class="btn btn-primary" type="submit" value="Submit">
                    </form:form>
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