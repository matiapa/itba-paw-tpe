<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="ar.edu.itba.paw.models.Announcement" scope="request" id="announcement"/>

<div ${announcement.seen ? 'hidden' : ''} name="${announcement.id}" class="card shadow mb-4" style="margin-top: 32px;">
    <div hidden>${announcement.seen}</div>
    <div class="card-header py-3">
        <h6 class="font-weight-bold m-0"><c:out value="${announcement.title}"/></h6>
    </div>
    <div class="col mr-2" style="padding: 16px 24px;">
        <div class="row align-items-center">
            <div class="col">
                <p style="padding-top: 0;"><c:out value="${announcement.summary}"/></p>
                <p style="padding-top: 0;"><c:out value="${announcement.content}"/></p>
                <span class="text-xs">
                    <c:url var="profileUrl" value="/profile?id=${announcement.uploader.id}"/>
                    Publicado por <a href="${profileUrl}"><c:out value="${announcement.uploader.name}"/></a> el
                    <fmt:formatDate type="both" dateStyle = "short" timeStyle = "short" value="${announcement.uploadDate}"/>
                </span>
            </div>
            <div class="col-auto">
                <c:if test="${!announcement.seen}">
                    <button class="btn btn-icon" type="button" onclick="hideAnnouncement(${announcement.id})">
                        <i class="material-icons">done</i>
                    </button>
                </c:if>
            </div>
        </div>
    </div>
</div>