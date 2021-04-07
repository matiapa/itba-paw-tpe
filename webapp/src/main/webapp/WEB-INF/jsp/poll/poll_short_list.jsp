<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<c:if test="${empty polls}">No hay encuestas abiertas en este momento</c:if>
<ul class="list-group">
    <c:forEach var="poll" items="${polls}">
        <li class="list-group-item poll-short">
            <form>
                <h1><a href="/polls/byId?id=${poll.id}"><c:out value="${poll.name}"/></a></h1>
                <div class="form-row align-items-end">
                    <div class="col">
                        <ul class="list-group">
                            <c:forEach var="option" items="${poll.options}">
                                <li class="list-group-item">
                                    <div class="form-check"><input class="form-check-input" type="radio" id="poll-option-${option.id}" name="option"><label class="form-check-label" for="poll-option-${option.id}">${option.value}</label></div>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="col text-right"><button class="btn btn-primary" type="button">Enviar</button></div>
                </div>
            </form>
        </li>
    </c:forEach>
</ul>