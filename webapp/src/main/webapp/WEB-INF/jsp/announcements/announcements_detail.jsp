<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="ar.edu.itba.paw.models.Announcement" scope="request" id="announcement"/>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>${announcement.title}</title>
    <link rel="icon" type="image/png" sizes="311x311" href="<c:url value="/assets/img/logo-tran-white.png"/>">
    <link rel="stylesheet" href="<c:url value="/assets/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i">
    <link rel="stylesheet" href="<c:url value="/assets/fonts/fontawesome-all.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/fonts/font-awesome.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/fonts/material-icons.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/fonts/simple-line-icons.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/fonts/fontawesome5-overrides.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/css/buttons.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/css/cards.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/css/colors.compiled.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/css/fab.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/css/nav-tabs.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/css/sidebar.css"/>">
</head>

<body id="page-top">
    <div id="wrapper">

        <jsp:include page="../common/navbar.jsp"/>

        <div class="d-flex flex-column" id="content-wrapper">
            <div id="content">

                <jsp:include page="../common/header.jsp"/>

                <div class="container-fluid">
                    <div class="col">
                        <div class="row">
                            <a href="javascript:history.back()">Volver</a>
                        </div>
                        <div class="row">
                            <div id="${announcement.id}" class="card shadow mb-4" style="margin-top: 32px; width: 100%">
                                <div class="card-header py-3">
                                    <h6 class="font-weight-bold m-0"><c:out value="${announcement.title}"/></h6>
                                </div>
                                <div class="col mr-2" style="padding: 16px 24px;">
                                    <p style="padding-top: 0;"><c:out value="${announcement.summary}"/></p>
                                    <p style="padding-top: 0;"><c:out value="${announcement.content}"/></p>
                                    <span class="text-xs">
                                        <c:out value="Publicado por ${announcement.uploader.name} el "/>
                                        <fmt:formatDate type="both" dateStyle = "short" timeStyle = "short" value="${announcement.uploadDate}"/>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>

    </div>

    <script src="<c:url value="/assets/js/jquery.min.js"/>"></script>
    <script src="<c:url value="/assets/js/theme.js"/>"></script>
    <script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js"/>"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" ></script>

</body>

</html>