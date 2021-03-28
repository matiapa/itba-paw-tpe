<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>PAW</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lora">
    <link rel="stylesheet" href="https://itbahub.web.app/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://itbahub.web.app/assets/css/Article-Clean.css">
    <link rel="stylesheet" href="https://itbahub.web.app/assets/css/Article-List.css">
    <link rel="stylesheet" href="https://itbahub.web.app/assets/css/styles.css">
</head>

<body>
<ul class="list-group">
    <c:forEach var="announcement" items="${announcements}">
        <li class="list-group-item">
            <div class="row">
                <div class="col">
                    <h3 class="text-primary"><c:out value="${announcement.title}"/></h3>
                </div>
                <div class="col offset-xl-3"><span>Creation Date</span></div>
                <div class="col-xl-2 offset-xl-1 text-right"><button class="btn btn-link" type="button">View Details</button></div>
            </div>
        </li>
    </c:forEach>
</ul>
<script src="https://itbahub.web.app/assets/assets/js/jquery.min.js"></script>
<script src="https://itbahub.web.app/assets/assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>