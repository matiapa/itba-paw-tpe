<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>




<c:forEach var="content" items="${contents}">

    <li class="card mr-4 mb-4 p-3">
        <div class="column">
            <div class="row align-items-center">
                <div class="col">
                    <h5 class="text-primary"><c:out value="${content.name}"/></h5>
                </div>
            </div>

            <div class="row align-items-center mt-1">
                <div class="col">
                    <c:out value='${content.description}'/>
                </div>
                <div class="col text-right">
                    <div class="text-right">
                        <a href="<c:out value='${content.link}'/>">
                            Ir a la fuente
                        </a>
                    </div>
                </div>

            </div>
        </div>
    </li>

</c:forEach>