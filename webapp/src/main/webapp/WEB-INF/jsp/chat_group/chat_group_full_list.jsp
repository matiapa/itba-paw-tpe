<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<c:forEach var="group" items="${chat_groups}">
    <li class="card mr-4 mb-4 p-3">
        <div class="column">
            <div class="row align-items-center">
                <div class="col">
                    <h5 class="text-primary"><c:out value="${group.name}"/></h5>
                </div>
                <div class="col text-right">
                    <c:out value="${group.id}"/>
                </div>
            </div>

            <div class="text-left">
                <a href="<c:url value="${group.link}"/>"> </a>
            </div>
        </div>
    </li>
</c:forEach>
