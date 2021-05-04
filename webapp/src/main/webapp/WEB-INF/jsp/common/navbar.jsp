<%@ page import="ar.edu.itba.paw.models.Permission" %>
<%@ page import="ar.edu.itba.paw.models.Entity" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>
<jsp:useBean id="user" scope="request" type="ar.edu.itba.paw.models.User"/>

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

            <c:if test="<%= user.can(Permission.Action.read, Entity.statistic) %>">
                <li class="nav-item">
                    <hr class="sidebar-divider">
                    <a class="nav-link" href="<c:url value="/admin/statistics"/>">
                        <i class="fas fa-chart-bar"></i>
                        <span><spring:message code="statistics"/></span>
                    </a>
                </li>
            </c:if>
        </ul>
        <div class="text-center d-none d-md-inline"></div>
    </div>

</nav>