<%@ page import="ar.edu.itba.paw.models.Permission" %>
<%@ page import="ar.edu.itba.paw.models.Entity" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean type="ar.edu.itba.paw.models.Poll" scope="request" id="poll"/>
<jsp:useBean id="user" type="ar.edu.itba.paw.models.User" scope="request"/>

<div class="card shadow mb-4" style="margin-top: 32px;">
            <div class="card-header py-3">
                <h6 class="font-weight-bold m-0"><c:out value="${poll.name}"/></h6>
            </div>
            <div class="card-body">
                <div class="row align-items-center no-gutters">
                    <div class="col mr-2">
                        <h6 class="mb-0"><strong><c:out value="${poll.name}"/></strong></h6>
                        <p><c:out value="${poll.description}"/></p>
                        <span class="text-xs">
                            <c:if test="${poll.expiryDate != null}">
                                <fmt:formatDate type="both" dateStyle = "short" timeStyle = "short"
                                    value="${poll.expiryDate}" var="expiryDateFormatted"/>
                                <c:choose>
                                    <c:when test="${poll.isExpired}">
                                        <spring:message code="expiredOn" arguments="${expiryDateFormatted}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <spring:message code="expireOn" arguments="${expiryDateFormatted}"/>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </span>
                    </div>
                    <div class="col col-md-auto p-1">
                        <c:if test="${canDeletePoll}">
                            <c:url var="url" value="/polls/${poll.id}/delete"/>
                            <form action="${url}" method="post">
                                <button type="submit" class="btn btn-icon"
                                    data-toggle="tooltip" title='<spring:message code="poll.delete"/>'>
                                    <i class="material-icons">delete</i>
                                </button>
                            </form>
                        </c:if>
                    </div>

                    <div class="col col-md-auto p-1">
                        <a href='<c:url value="/polls/${poll.id}"/>' class="btn btn-icon" type="button"
                            data-toggle="tooltip" title='<spring:message code="poll.view"/>'>
                            <i class="material-icons">keyboard_arrow_right</i>
                        </a>
                    </div>

                </div>
            </div>
</div>
