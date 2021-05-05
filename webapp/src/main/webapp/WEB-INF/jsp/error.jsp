<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><spring:message code="home"/></title>

    <jsp:include page="common/styles.jsp"/>
</head>

<body id="page-top" class="sidebar-toggled">
<div id="wrapper">


    <nav class="navbar navbar-dark align-items-start sidebar sidebar-dark accordion sidebar-toggled sidebar-dark p-0 toggled" style="background: rgb(2,86,138);">
        <div class="container-fluid d-flex flex-column p-0">

            <a class="navbar-brand d-flex justify-content-center align-items-center sidebar-brand m-0"
               href="<c:url value="/"/>">
                <img src="<c:url value="/assets/img/logo-hor.png"/>" height="50px">
            </a>

            <ul class="nav navbar-nav text-light" id="accordionSidebar">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/"/>">
                        <i class="material-icons">home</i>
                        <span><spring:message code="home"/></span>
                    </a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/courses"/>">
                        <i class="icon-graduation"></i>
                        <span><spring:message code="courses"/></span>
                    </a>
                    <hr class="sidebar-divider">
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/announcements"/>">
                        <i class="material-icons">fiber_new</i>
                        <span><spring:message code="announcement"/></span>
                    </a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/polls"/>">
                        <i class="fas fa-poll"></i>
                        <span><spring:message code="polls"/></span>
                    </a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/chats"/>">
                        <i class="material-icons">people</i>
                        <span><spring:message code="chatGroups"/></span>
                    </a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/contents"/>">
                        <i class="fas fa-book"></i>
                        <span><spring:message code="contents"/></span>
                    </a>
                </li>
            </ul>
        </div>
    </nav>

    <div class="d-flex flex-column" id="content-wrapper">
        <div id="content">
            <div class="container-fluid" style="height: 100%;">
                <div class="col" style="height: 100%;">
                    <div class="row justify-content-center" style="margin-top: 100px">
                        <h1><c:out value="${code}"/></h1>
                    </div>

                    <div class="row justify-content-center mt-3">
                        <h3>¡Ups! <c:out value="${description}"/></h3>
                    </div>

                    <div class="row justify-content-center mt-3">
                        <c:url var="url" value="/"/>
                        <h6><a href="${url}"><spring:message code="backHome"/></a></h6>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="common/scripts.jsp"/>

</body>

</html>