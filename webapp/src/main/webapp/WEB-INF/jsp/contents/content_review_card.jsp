<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="ar.edu.itba.paw.models.ContentReview" scope="request" id="review"/>
<jsp:useBean type="ar.edu.itba.paw.models.User" scope="request" id="user"/>


<div id="review${review.id}" class="card shadow mb-4" style="width: 100%">
    <div class="card-body">
        <div class="row align-items-center">
            <div class="col">
                <p style="padding-top: 0; "><c:out value="${review.review}"/></p>
                <span class="text-xs">
                    <fmt:formatDate type="both" dateStyle = "short" timeStyle = "short" value="${review.uploadDate}" var="uploadDateFormatted"/>
                    <c:url var="profileUrl" value="/profile/${review.uploader.id}"/>
                    <spring:message code="publishedBy" htmlEscape="false" arguments=
                            '<a href="${profileUrl}">${fn:escapeXml(review.uploader.name)}</a>,
                        ${uploadDateFormatted}'
                    />
                </span>
            </div>
        </div>
    </div>
</div>