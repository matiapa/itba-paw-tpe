<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean type="ar.edu.itba.paw.models.Poll" scope="request" id="poll"/>


<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><c:out value="${poll.name}"/></title>

    <jsp:include page="../common/styles.jsp"/>
</head>

<body>

    <div id="wrapper">
        <jsp:include page="../common/navbar.jsp"/>

        <div class="d-flex flex-column" id="content-wrapper">
            <div id="content">
                <jsp:include page="../common/header.jsp"/>

                <div class="container-fluid">
                    <div class="col-lg-9 col-xl-12 mb-4">
                        <c:if test="${!poll.isExpired}">
                            <div class="card shadow mb-4" style="margin-top: 32px;">

                            <div class="card-header py-3">
                                <h6 class="font-weight-bold m-0"><c:out value="${poll.name}"/></h6>
                            </div>

                            <form action='<c:url value="/polls/${poll.id}/vote"/>' method="POST">

                                <div class="col mr-2" style="padding-top: 16px;padding-right: 24px;padding-left: 24px;padding-bottom: 16px;">
                                    <p style="padding-top: 0px;"><c:out value="${poll.description}"/></p>
                                    <div class="row">
                                        <c:forEach var="option" items="${poll.options}">
                                            <div class="col">
                                                <div class="form-check">
                                                    <input class="form-check-input" name="option"
                                                       type="radio" id="poll-option-${option.id}" value="${option.id}">
                                                    <label class="form-check-label" for="poll-option-${option.id}">
                                                        <c:out value="${option.value}"/>
                                                    </label>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>

                                <div class="row align-items-end" style="margin: 10px 10px 10px;">
                                    <c:if test="${!hasVoted}">
                                        <div class="col">
                                            <button class="btn btn-primary" type="submit">
                                                <spring:message code="vote"/>
                                            </button>
                                        </div>
                                    </c:if>
                                    <c:if test="${hasVoted}">
                                        <div class="col">
                                            <button class="btn btn-primary" type="submit" disabled>
                                                <spring:message code="voted"/>
                                            </button>
                                        </div>
                                    </c:if>
                                    <div class="col text-right">
                                    <span class="text-xs">
                                        <c:if test="${poll.submittedBy != null}">
                                            <spring:message code="publishedBy" arguments="${poll.submittedBy.name}, ${creationFormat.format(poll.creationDate)}"/>
                                        </c:if>
                                        <c:if test="${poll.submittedBy == null}">
                                            <c:out value="Creado el ${creationFormat.format(poll.creationDate)}."/>
                                        </c:if>
                                        <!-- <c:if test="${poll.expiryDate != null}">Finaliza ${expiryFormat.format(poll.expiryDate)}</c:if> -->
                                    </span>
                                    </div>
                                </div>
                            </form>
                        </div>
                        </c:if>
                    </div>
                </div>

                <div id="content-1"></div>

                <div class="container-fluid">
                    <div class="col-lg-9 col-xl-12 mb-4">
                        <div class="card shadow mb-4" style="margin-top: 32px;">

                            <div class="card-header py-3">
                                <h6 class="font-weight-bold m-0"><spring:message code="results"/></h6>
                            </div>

                            <div class="col mr-2" style="padding-top: 16px;padding-right: 24px;padding-left: 24px;padding-bottom: 16px;">
                                <c:choose>
                                    <c:when test="${votes.size() > 0}">
                                        <c:forEach var="vote" items="${votes}">
                                            <div class="col">
                                                <div class="row">
                                                    <div class="col">
                                                        <span>
                                                            <c:out value="${vote.value}"/>
                                                            <spring:message code="votes"/> :
                                                            <c:out value="${vote.key.value}"/>
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${poll.isExpired}">
                                                <spring:message code="poll.noVotesAndExpired"/>
                                            </c:when>
                                            <c:otherwise>
                                                <spring:message code="poll.noVotes"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>

                                <div class="row align-items-end" style="margin-top: 32px;">
                                    <div class="col text-right">
                                        <span class="text-xs"></span>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>

            </div>
        </div>

    </div>

    <script src="<c:url value="/assets/js/polls.js"/>"></script>

</body>

</html>