<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.ChatGroup>" scope="request" id="chat_groups"/>

<c:forEach var="group" items="${chat_groups}">
    <li class="card mr-4 mb-4 p-3">
        <div class="column">
            <div class="row">
                <div class="col">
                    <h5 class="text-primary"><c:out value="${group.name}"/></h5>
                </div>
                <div class="col text-right">
                    <c:out value="Creado el ${group.creationDate}"/>
                </div>
            </div>

            <div class="row align-items-end mt-3">
                <div class="col text-left">
                    <img
                        alt="<c:url value="https://api.qrserver.com/v1/create-qr-code/?size=100x100&data=${group.link}"/>"
                        src="<c:url value="https://api.qrserver.com/v1/create-qr-code/?size=100x100&data=${group.link}"/>"
                    />
                </div>
                <div class="col text-right">
                    <a href="<c:url value="${group.link}"/>">Ir al grupo</a>
                </div>
            </div>

        </div>
    </li>
</c:forEach>
