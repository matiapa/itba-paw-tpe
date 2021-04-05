<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">

    <link rel="stylesheet" href="<c:url value="/assets/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/css/styles.css"/>">

    <title>${title}</title>

</head>

<body>

    <div class="col">

        <div class="row" style="background: rgb(2,86,138);">

            <div class="col align-self-center">
                <ol class="breadcrumb text-center" style="background: transparent;">
                    <c:forEach var="breadcrumbItem" items="${navigationHistory}">

                        <li class="breadcrumb-item">
                            <a href="<c:url value="${breadcrumbItem.url}"/>">
                                <span style="color: rgb(252,252,252);">
                                    <c:out value="${breadcrumbItem.title}"/>
                                </span>
                            </a>
                        </li>

                    </c:forEach>
                </ol>
            </div>

            <div class="col text-right align-self-center">
                <img src="<c:url value="/assets/img/logo.png"/>" height="32">
            </div>

        </div>

        <jsp:include page="${contentViewName}"/>

    </div>

    <script src="<c:url value="/assets/js/jquery.min.js"/>"></script>
    <script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js"/>"></script>

</body>

</html>