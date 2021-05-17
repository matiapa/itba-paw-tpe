<%@ page import="ar.edu.itba.paw.models.Permission" %>
<%@ page import="ar.edu.itba.paw.models.Entity" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="ar.edu.itba.paw.models.Announcement" scope="request" id="announcement"/>
<jsp:useBean type="ar.edu.itba.paw.models.User" scope="request" id="user"/>


<div id="announce${announcement.id}" class="card shadow mb-4" style="margin-top: 32px;">
    <div class="card-header py-3">
        <h6 class="font-weight-bold m-0"><c:out value="${announcement.title}"/></h6>
    </div>
    <div class="card-body">
        <div class="row align-items-center">
            <div class="col">
                <p style="padding-top: 0;"><c:out value="${announcement.summary}"/></p>
                <span class="text-xs">
                    <fmt:formatDate type="both" dateStyle = "short" timeStyle = "short" value="${announcement.uploadDate}" var="uploadDateFormatted"/>
                    <c:url var="profileUrl" value="/profile/${announcement.uploader.id}"/>
                    <spring:message code="publishedBy" htmlEscape="false" arguments=
                        '<a href="${profileUrl}">${fn:escapeXml(announcement.uploader.name)}</a>,
                        ${uploadDateFormatted}'
                    />
                </span>
            </div>

            <div class="col col-md-auto p-1">
                <c:if test="<%= user.can(Permission.Action.delete, Entity.announcement) %>">
                    <c:url var="url" value="/announcements/${announcement.id}/delete"/>
                    <form action="${url}" method="post">
                       <button type="submit" class="btn btn-icon"
                           data-toggle="tooltip" title="<spring:message code="announcement.delete"/>">
                           <i class="material-icons">delete</i>
                       </button>
                    </form>
                </c:if>
            </div>

            <div class="col col-md-auto p-1">
                <c:url var="url" value="/announcements/${announcement.id}/seen"/>
                <form action="${url}" method="post">
<%--                    disabled="${announcement.seen}"--%>
                    <button type="submit" class="btn btn-icon" ${announcement.seen ? 'disabled' : ''}
                            data-toggle="tooltip" title="<spring:message code="announcement.checkAsSeen"/>">
                        <i class="material-icons">done</i>
                    </button>
                </form>
            </div>

            <div class="col col-md-auto p-1">
                <a href="<c:url value="/announcements/${announcement.id}"/>" class="btn btn-icon" type="button"
                   data-toggle="tooltip" title="<spring:message code="announcement.view"/>">
                    <i class="material-icons">keyboard_arrow_right</i>
                </a>
            </div>
        </div>
    </div>
</div>