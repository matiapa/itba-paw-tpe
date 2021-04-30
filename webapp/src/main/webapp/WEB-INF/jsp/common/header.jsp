<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="ar.edu.itba.paw.models.User" scope="request" id="user"/>

<nav class="navbar navbar-light navbar-expand bg-white shadow mb-4 topbar static-top">
    <div class="container-fluid">
        <button class="btn btn-link d-md-none rounded-circle mr-3" id="sidebarToggleTop-1" type="button"><i class="fas fa-bars"></i></button>


        <ul class="nav navbar-nav flex-nowrap ml-auto">

            <%-- Notifications --%>

<%--            <li class="nav-item dropdown no-arrow mx-1">--%>
<%--                <div class="nav-item dropdown no-arrow"><a class="dropdown-toggle nav-link" data-toggle="dropdown" aria-expanded="false" href="#"><span class="badge badge-danger badge-counter" style="margin-right: 5px;">2</span><i class="fas fa-bell fa-fw"></i></a>--%>
<%--                    <div class="dropdown-menu dropdown-menu-right dropdown-list dropdown-menu-right animated--grow-in">--%>
<%--                        <h6 class="dropdown-header" style="background: rgb(2,86,138);border-style: none;">Notificaciones</h6>--%>

<%--                        <a class="d-flex align-items-center dropdown-item" href="#">--%>
<%--                            <div class="mr-3">--%>
<%--                                <div class="bg-primary icon-circle"><i class="fas fa-file-alt text-white"></i></div>--%>
<%--                            </div>--%>
<%--                            <div><span class="small text-gray-500">December 12, 2019</span>--%>
<%--                                <p>Se agregó un documento al curso Proyecto de Aplicaciones Web</p>--%>
<%--                            </div>--%>
<%--                        </a>--%>

<%--                        <a class="text-center dropdown-item small text-gray-500" href="#">Ver todas las notificaciones</a>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </li>--%>

            <%-- Profile dropdown --%>

            <li class="nav-item dropdown no-arrow">
                <div class="nav-item dropdown no-arrow">
                    <a class="dropdown-toggle nav-link" data-toggle="dropdown" aria-expanded="false" href="#">
                        <span class="d-none d-lg-inline mr-2 text-gray-600 small">
                            <c:out value="${user.name}"/>
                        </span>
                        <c:url value="/assets/img/avatars/avatar.png" var="defaultImage"/>
                        <img class="border rounded-circle img-profile"
                             src="${user.profileImgB64 != null ? user.profileImgB64 : defaultImage}">
                    </a>
                    <div class="dropdown-menu shadow dropdown-menu-right animated--grow-in">
                        <a class="dropdown-item" href="<c:url value="/profile/own"/>">
                            <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i> Perfil
                        </a>
                        <div class="dropdown-divider"></div>
                        <form action='<c:url value="/signout"/>' method="POST">
                            <label class="dropdown-item">
                                <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i> Salir
                                <input type="submit" hidden>
                            </label>
                        </form>
                        </a>
                    </div>
                </div>
            </li>

        </ul>

    </div>
</nav>