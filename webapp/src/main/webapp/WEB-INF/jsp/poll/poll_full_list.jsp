<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:forEach var="poll" items="${polls}">
    <div class="card" style="margin: 10px;">
        <div class="card-body">
            <h4 class="card-title"><c:out value="${poll.name}"/></h4>
            <h6 class="text-muted card-subtitle mb-2"><br><c:out value="${poll.description}"/><br><br></h6>
            <form>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">
                        <div class="form-check"><input class="form-check-input" type="radio" id="<c:out value='poll-${poll.id}-option-1'/>" name="option"><label class="form-check-label" for="<c:out value='poll-${poll.id}-option-1'/>">Option 1</label></div>
                    </li>
                    <li class="list-group-item">
                        <div class="form-check"><input class="form-check-input" type="radio" id="<c:out value='poll-${poll.id}-option-2'/>" name="option"><label class="form-check-label" for="<c:out value='poll-${poll.id}-option-2'/>">Option 2</label></div>
                    </li>
                    <li class="list-group-item">
                        <div class="form-check"><input class="form-check-input" type="radio" id="<c:out value='poll-${poll.id}-option-3'/>" name="option"><label class="form-check-label" for="<c:out value='poll-${poll.id}-option-3'/>">Option 3</label></div>
                    </li>
                </ul><button class="btn btn-primary poll-headers" type="button" style="margin-left: 20px;margin-top: 15px;">Enviar</button></form>
        </div>
    </div>
</c:forEach>