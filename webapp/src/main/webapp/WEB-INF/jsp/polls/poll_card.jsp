<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="ar.edu.itba.paw.models.Poll" scope="request" id="poll"/>
<jsp:useBean type="java.lang.Boolean" scope="request" id="canDelete"/>

<div class="card shadow mb-4" style="margin-top: 32px;">
    <div class="card-header py-3">
        <h6 class="font-weight-bold m-0"><c:out value="${poll.name}"/></h6>
    </div>
    <div class="card-body">
        <div class="row align-items-center no-gutters">
            <div class="col mr-2">
                <h6 class="mb-0"><strong><c:out value="${poll.name}"/></strong></h6>
                <p><c:out value="${poll.description}"/></p>
                <span class="text-xs">
                    <c:if test="${poll.expiryDate != null}">
                        ${poll.isExpired ? "🕑 Expiró el ": "🕑 Expira el "}
                        <fmt:formatDate type="both" dateStyle = "short" timeStyle = "short" value="${poll.expiryDate}"/>
                    </c:if>
                </span>
            </div>
            <div class="col-auto">
                <c:if test="${canDelete}">
                    <c:url var="url" value="/polls/${poll.id}/delete"/>
                    <form action="${url}" method="post">
                        <button type="submit" class="btn btn-icon" style="color:red">
                            <i class="material-icons">delete</i>
                        </button>
                    </form>
                </c:if>
            </div>
            <div class="col-auto">
                <a href="<c:url value="/polls/detail?id=${poll.id}"/>" class="btn btn-icon" type="button">
                    <i class="material-icons">keyboard_arrow_right</i>
                </a>
            </div>
        </div>
    </div>
</div>
