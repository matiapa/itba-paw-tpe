<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Announcement>" scope="request" id="announcements"/>

<ul class="list-group">
    <c:forEach var="announcement" items="${announcements}">

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

                <div class="text-right">
                    <a href="<c:url value="/announcements/byId?id=${announcement.id}"/>">Ver más</a>
                </div>
            </div>
        </li>

    </c:forEach>
</ul>