<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="mt-5 row">
    <ul class="row">
        <c:forEach var="career" items="${career}">
            <li class="card mr-4 mb-4 p-3">
                <div class="column">
                    <div class="row align-items-center">
                        <div class="col">
                            <h5 class="text-primary"><c:out value="${career.name}"/></h5>
                        </div>
                        <div class="col text-right">
                            <c:out value="${career.id}"/>
                        </div>
                    </div>
                    <div class="text-right">
                        <a href="/career/detail?id=${career.id}"><c:out value="${career.name}"></a>
                    </div>
                </div>
            </li>
        </c:forEach>
    </ul>
</div>
