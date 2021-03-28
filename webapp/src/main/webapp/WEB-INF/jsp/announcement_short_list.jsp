<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>PAW</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lora">
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/Article-Clean.css">
    <link rel="stylesheet" href="assets/css/Article-List.css">
    <link rel="stylesheet" href="assets/css/styles.css">
</head>

<body>
    <ul class="list-group">
        <c:forEach var="announcement" items="${announcements}">

            <li class="list-group-item">
                <div class="column">
                    <div class="row align-items-center">
                        <div class="col">
                            <h5 class="text-primary"><c:out value="${announcement.title}"/></h5>
                        </div>
                        <div class="col text-right">
                            <c:out value="${announcement.uploadDate}"/>
                        </div>
                    </div>

                    <p><c:out value="${announcement.content}"/></p>

                    <div class="text-right">
                        <button class="btn btn-link " type="button">Ver más</button>
                    </div>
                </div>
            </li>

        </c:forEach>
    </ul>

    <script src="assets/assets/js/jquery.min.js"></script>
    <script src="assets/assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>