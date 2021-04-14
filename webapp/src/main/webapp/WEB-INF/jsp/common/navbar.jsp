<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<nav class="navbar navbar-dark align-items-start sidebar sidebar-dark accordion sidebar-toggled sidebar-dark p-0 toggled" style="background: rgb(2,86,138);">

    <div class="container-fluid d-flex flex-column p-0"><a class="navbar-brand d-flex justify-content-center align-items-center sidebar-brand m-0" href="#"><img src="assets/img/logo-hor.png" height="50px"></a>
        <ul class="nav navbar-nav text-light" id="accordionSidebar">
            <li class="nav-item">
                <a class="nav-link active" href="<c:url value="/"/>">
                    <i class="material-icons">home</i>
                    <span>Inicio</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/courses"/>">
                    <i class="icon-graduation"></i>
                    <span>Cursos</span>
                </a>
                <hr class="sidebar-divider">
            </li>

            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/announcements"/>">
                    <i class="material-icons">fiber_new</i>
                    <span>Anuncios</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/polls"/>">
                    <i class="fas fa-poll"></i>
                    <span>Encuestas</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/chats"/>">
                    <i class="material-icons">people</i>
                    <span>Grupos de chat</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/contents"/>">
                    <i class="fas fa-book"></i>
                    <span>Apuntes</span>
                </a>
            </li>
        </ul>
        <div class="text-center d-none d-md-inline"></div>
    </div>

</nav>