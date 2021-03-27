<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>${params.screenTitle}</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://itbahub.web.app/assets/css/styles.css">
</head>

<body>
<div class="col">
    <div class="row" style="background: rgb(2,86,138);">
        <div class="col align-self-center">
            <ol class="breadcrumb text-center" style="background: transparent;">
                <li class="breadcrumb-item"><a href="#"><span style="color: rgb(252,252,252);">Home</span></a></li>
                <li class="breadcrumb-item"><a href="#"><span style="color: rgb(255,255,255);">Library</span></a></li>
                <li class="breadcrumb-item"><a href="#"><span style="color: rgb(255,255,255);">Data</span></a></li>
            </ol>
        </div>
        <div class="col text-right align-self-center"><img src="https://itbahub.web.app/assets/img/logo.png" height="32"></div>
    </div>
    <div class="row" style="height: 1200px;padding-top: 10px;">
        <div class="col">
            <div class="container" style="height: 150px;">
                <div class="row">
                    <h4>${params.panel1Title}</h4>
                    <div class="col offset-xl-8"><a href="#">Ver más</a></div>
                </div>
            </div>
            <hr>
            <div class="container" style="height: 150px;">
                <div class="row">
                    <h4>${params.panel2Title}</h4>
                    <div class="col offset-xl-8"><a href="#">Ver más</a></div>
                </div>
            </div>
            <hr>
            <div class="container" style="height: 300px;">
                <div class="row">
                    <h4>${params.pollsTitle}</h4>
                    <div class="col offset-xl-8"><a href="#">Ver más</a></div>
                </div>
            </div>
        </div>
        <div class="col-xl-7" style="border-width: 1px;border-color: rgb(229,229,229);border-left-style: solid;">
            <h4><c:out value="${params.announcementsTitle}"/></h4>
            <jsp:include page="poll_full_list.jsp"/>
        </div>
    </div>
</div>
<script src="https://itbahub.web.app/assets/js/jquery.min.js"></script>
<script src="https://itbahub.web.app/assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>