<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Poll>" scope="request" id="polls"/>

<c:if test="${empty polls}">No hay encuestas abiertas en este momento</c:if>
<c:forEach var="poll" items="${polls}">
    <div class="card" style="margin: 10px;">
        <div class="card-body">
            <h4 class="card-title">
                <a href="<c:url value="/polls/byId?id=${poll.id}"/>">
                    <c:out value="${poll.name}"/>
                </a>
            </h4>
            <c:if test="${poll.description != null}"><h6 class="text-muted card-subtitle mb-2"><br><c:out value="${poll.description}"/><br><br></h6></c:if>
            <form>
                <ul class="list-group list-group-flush">
                    <c:forEach var="option" items="${poll.options}">
                        <li class="list-group-item">
                            <div class="form-check"><input class="form-check-input" type="radio" id="poll-option-${option.id}" name="option"><label class="form-check-label" for="poll-option-${option.id}">${option.value}</label></div>
                        </li>
                    </c:forEach>
                </ul><button class="btn btn-primary poll-headers" type="button" style="margin-left: 20px;margin-top: 15px;">Enviar</button></form>
        </div>
    </div>
</c:forEach>