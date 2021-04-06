<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<ul class="list-group">
    <c:forEach var="poll" items="${polls}">
        <li class="list-group-item poll-short">
            <form>
                <div class="form-row">
                    <div class="col">
                        <h1><c:out value="${poll.name}"/></h1>
                    </div>
                    <div class="col d-flex justify-content-end"><button class="btn btn-primary" type="button">Enviar</button></div>
                </div>
                <ul class="list-group">
                    <li class="list-group-item">
                        <div class="form-check"><input class="form-check-input" type="radio" id="<c:out value='poll-${poll.id}-option-1'/>" name="option"><label class="form-check-label" for="<c:out value='poll-${poll.id}-option-1'/>">Option 1</label></div>
                    </li>
                    <li class="list-group-item">
                        <div class="form-check"><input class="form-check-input" type="radio" id="<c:out value='poll-${poll.id}-option-2'/>" name="option"><label class="form-check-label" for="<c:out value='poll-${poll.id}-option-2'/>">Option 2</label></div>
                    </li>
                    <li class="list-group-item">
                        <div class="form-check"><input class="form-check-input" type="radio" id="<c:out value='poll-${poll.id}-option-3'/>" name="option"><label class="form-check-label" for="<c:out value='poll-${poll.id}-option-3'/>">Option 3</label></div>
                    </li>
                </ul>
            </form>
        </li>
    </c:forEach>
</ul>