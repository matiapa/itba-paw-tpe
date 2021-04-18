<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="ar.edu.itba.paw.models.ChatGroup" scope="request" id="chat"/>

<div class="card shadow mb-4" style="margin-top: 32px;">
    <div class="card-header py-3">
        <h6 class="font-weight-bold m-0"><c:out value="${chat.name}"/></h6>
    </div>
    <div class="row">
        <div class="col mr-2" style="padding: 16px 24px;">
            <a href=<c:out value="${chat.link}"/> target="_blank"> Ir al grupo</a>
        </div>
        <div class="col mr-10" style=" padding: 16px 24px; text-align: right">
            <h6><c:out value="Creado el: ${chat.creationDate}"/></h6>
        </div>
    </div>

</div>
