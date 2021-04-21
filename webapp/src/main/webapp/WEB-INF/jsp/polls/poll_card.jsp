<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="ar.edu.itba.paw.models.Poll" scope="request" id="poll"/>

<div class="card shadow mb-4" style="margin-top: 32px;">
    <div class="card-header py-3">
        <h6 class="font-weight-bold m-0"><c:out value="${poll.name}"/></h6>
    </div>
    <div class="row">
        <div class="col mr-2" style="padding: 16px 24px;">
            <p><c:out value="${poll.description}"/></p>
        </div>
        <div class="col mr-10" style=" padding: 16px 24px; text-align: right;">
            <h6><c:out value="Caduca el: ${poll.expiryDate}"/></h6>
            <a href="<c:url value="/polls/detail?id=${poll.id}"/>" class="btn btn-icon" type="button">
                <i class="material-icons">keyboard_arrow_right</i>
            </a>
        </div>
    </div>

</div>
