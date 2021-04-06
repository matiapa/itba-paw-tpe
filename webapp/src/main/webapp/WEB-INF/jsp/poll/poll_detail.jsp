<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="margin-left: 20px;">
    <h1 class="text-primary"><c:out value="${poll.name}"/></h1>
    <p><c:out value="${poll.description}"/></p>
    <p style="font-size: 14px;"><em><c:out value="Publicado por ${poll.submittedBy.name} el 01/01/1970. Finaliza en 48 horas"/></em></p>
</div>
<form>
    <ul class="list-group">
        <li class="list-group-item">
            <div class="form-check"><input class="form-check-input" type="radio" id="poll-radio1" name="option"><label class="form-check-label" for="poll-radio1">Option 1</label></div>
        </li>
        <li class="list-group-item">
            <div class="form-check"><input class="form-check-input" type="radio" id="poll-radio-2" name="option"><label class="form-check-label" for="poll-radio2">Option 2</label></div>
        </li>
        <li class="list-group-item">
            <div class="form-check"><input class="form-check-input" type="radio" id="poll-radio-3" name="option"><label class="form-check-label" for="poll-radio3">Option 3</label></div>
        </li>
    </ul>
    <button class="btn btn-primary poll-headers" type="button" style="margin-left: 20px;margin-top: 15px;">Enviar</button>
</form>