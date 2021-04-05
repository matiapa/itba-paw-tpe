<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<ul class="list-group">
    <li class="list-group-item">
        <div class="column">

            <div class="row align-items-center">
                <div class="col">
                    <h5 class="text-primary"><c:out value="${announcement.title}"/></h5>
                </div>
                <div class="col text-right">
                    <c:out value="${announcement.uploadDate}"/>
                </div>
            </div>

            <p><c:out value="${announcement.summary}"/></p>

            <p><c:out value="${announcement.content}"/></p>

            <div class="row align-items-center font-weight-light mt-4">
                <div class="col">
                    <c:out value="Publicado por ${announcement.uploader.name}"/>
                </div>
                <c:if test="${announcement.expiryDate != null}">
                    <div class="col text-right">
                        <c:out value="Este anuncio expirará el ${announcement.expiryDate}"/>
                    </div>
                </c:if>
            </div>



        </div>
    </li>
</ul>