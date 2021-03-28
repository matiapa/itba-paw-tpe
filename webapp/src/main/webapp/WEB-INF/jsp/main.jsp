<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>${params.screenTitle}</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/styles.css">
</head>

<body>
<div class="col">

    <div class="row" style="background: rgb(2,86,138);">

        <div class="col align-self-center">
            <ol class="breadcrumb text-center" style="background: transparent;">
                <c:forEach var="breadcrumbItem" items="${params.breadcrumbItems}">

                    <li class="breadcrumb-item">
                        <a href="<c:out value="${breadcrumbItem.url}"/>">
                            <span style="color: rgb(252,252,252);">
                                <c:out value="${breadcrumbItem.title}"/>
                            </span>
                        </a>
                    </li>

                </c:forEach>
            </ol>
        </div>

        <div class="col text-right align-self-center"><img src="assets/img/logo.png" height="32"></div>

    </div>

    <div class="row" style="height: 1200px;padding-top: 10px;">
        <div class="col">

            <div class="container" style="height: 150px;">
                <div class="row align-items-center">
                    <div class="col">
                        <h4>${params.panel1.title}</h4>
                    </div>
                    <c:if test="${params.panel1.moreLink != null}">
                        <div class="col text-right">
                            <a href="${params.panel1.moreLink}">Ver más</a>
                        </div>
                    </c:if>

                    <div class="mt-3">
<%--                        <jsp:include page="${[params.panel1.viewName]}"/>--%>
                    </div>
                </div>
            </div>

            <hr>

            <div class="container" style="height: 150px;">
                <div class="row">
                    <div class="col">
                        <h4>${params.panel2.title}</h4>
                    </div>
                    <c:if test="${params.panel2.moreLink != null}">
                        <div class="col text-right">
                            <a href="${params.panel2.moreLink}">Ver más</a>
                        </div>
                    </c:if>

                    <div class="mt-3">
<%--                        <jsp:include page="${[params.panel2.viewName]}"/>--%>
                    </div>
                </div>
            </div>

            <hr>

            <div class="container" style="height: 300px;">
                <div class="row">
                    <div class="col">
                        <h4>${params.panel3.title}</h4>
                    </div>
                    <c:if test="${params.panel3.moreLink != null}">
                        <div class="col text-right">
                            <a href="${params.panel3.moreLink}">Ver más</a>
                        </div>
                    </c:if>

                    <div class="mt-3">
<%--                        <jsp:include page="${[params.panel3.viewName]}"/>--%>
                    </div>
                </div>
            </div>

        </div>

        <div class="col-xl-7" style="border-width: 1px;border-color: rgb(229,229,229);border-left-style: solid;">
            <div class="row">
                <div class="col">
                    <h4><c:out value="${params.panel4.title}"/></h4>
                </div>
            </div>

            <div class="mt-3">
                <jsp:include page="${params.panel4.viewName}"/>
            </div>
        </div>

    </div>

</div>
<script src="assets/js/jquery.min.js"></script>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>