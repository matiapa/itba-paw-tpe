<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean type="ar.edu.itba.paw.models.Poll" scope="request" id="poll"/>
<jsp:useBean type="java.text.DateFormat" scope="request" id="creationFormat"/>
<jsp:useBean type="java.text.DateFormat" scope="request" id="expiryFormat"/>

<div style="margin-left: 20px;">
    <h1 class="text-primary"><c:out value="${poll.name}"/></h1>
    <p><c:out value="${poll.description}"/></p>
    <p style="font-size: 14px;"><em>
        <c:if test="${poll.submittedBy != null}">
            <c:out value="Publicado por ${poll.submittedBy.name} el ${creationFormat.format(poll.creationDate)}."/>
        </c:if>
        <c:if test="${poll.submittedBy == null}">
            <c:out value="Creado el ${creationFormat.format(poll.creationDate)}."/>
        </c:if>
        <c:if test="${poll.expiryDate != null}">Finaliza ${expiryFormat.format(poll.expiryDate)}</c:if>
    </em></p>
</div>
<form>
    <ul class="list-group" style="margin-left: 20px;">
        <c:forEach var="option" items="${poll.options}">
            <li class="list-group-item">
                <div class="form-check"><input class="form-check-input" type="radio" id="poll-option-${option.id}" name="option"><label class="form-check-label" for="poll-option-${option.id}">${option.value}</label></div>
            </li>
        </c:forEach>
    </ul>
    <button class="btn btn-primary poll-headers" type="button" style="margin-left: 20px;margin-top: 15px;">Enviar</button>
</form>