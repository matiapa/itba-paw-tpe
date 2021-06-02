<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<jsp:useBean type="ar.edu.itba.paw.models.Content" scope="request" id="content"/>
<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.ContentReview>" scope="request" id="reviews"/>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>${content.name}</title>

    <jsp:include page="../common/styles.jsp"/>
</head>

<body id="page-top">
    <div id="wrapper">

        <jsp:include page="../common/navbar.jsp"/>

        <div class="d-flex flex-column" id="content-wrapper">
            <div id="content">

                <jsp:include page="../common/header.jsp"/>

                <div class="container-fluid">
                    <div class="col">
                        <div class="row">
                            <div id="${content.id}" class="card shadow mb-4" style="margin-top: 32px; width: 100%">
                                <div class="card-header py-3">
                                    <h6 class="font-weight-bold m-0">
                                        <a href="${content.link}"><c:out value="${content.name}"/></a>
                                    </h6>
                                </div>
                                <div class="col mr-2" style="padding: 16px 24px;">
                                    <c:choose>
                                        <c:when test="${content.description==null}">
                                            <p style="padding-top: 0;"><spring:message code="content.noDescription"/></p>
                                        </c:when>
                                        <c:otherwise>
                                            <p style="padding-top: 0;"><c:out value="${content.description}"/></p>
                                        </c:otherwise>
                                    </c:choose>

                                    <c:if test="${content.ownerMail!=null}">
                                        <p><spring:message code="content.ownerMailHelper"/> <c:out value="${content.ownerMail}"/></p>
                                    </c:if>
                                    <c:if test="${content.contentDate!=null}">
                                        <p><spring:message code="content.contentDateHelper"/> <c:out value="${content.contentDate}"/></p>
                                    </c:if>
                                    <span class="text-xs">
                                        <fmt:formatDate type="both" dateStyle = "short" timeStyle = "short" value="${content.uploadDate}" var="uploadDateFormatted"/>
                                        <c:url var="profileUrl" value="/profile/${content.uploader.id}"/>
                                        <spring:message code="publishedBy" htmlEscape="false" arguments=
                                            '<a href="${profileUrl}">${fn:escapeXml(content.uploader.name)}</a>,
                                            ${uploadDateFormatted}'/>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="container-fluid">
                    <div class="col">
                        <div class="row">
                            <div class="card-header py-3">
                                <h6 class="font-weight-bold m-0">
                                    Reviews
                                </h6>
                            </div>
                            <div id="comment_input" class="card shadow mb-4" style="width: 100%">
                                <c:url value="/contents/${content.id}" var="postFormUrl"/>
                                <form:form modelAttribute="ContentReviewForm" action="${postFormUrl}" method="post">
                                    <div class="form-group">
                                        <spring:message code="content.contentReviewHelper" var="TextAreaHelper"/>
                                        <form:textarea path="reviewBody" class="form-control"  placeholder="${TextAreaHelper}" aria-label="With textarea"/>
                                        <form:errors path="reviewBody" cssStyle="color: red" element="p"/>
                                    </div>
                                    <button type="submit" class="btn btn-primary"><spring:message code="content.contentReviewSubmitButtonText"/></button>
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>





                <c:forEach var="review" items="${reviews}">
                    <c:set var="review" value="${review}" scope="request"/>
                    <div class="container-fluid">
                        <div class="col">
                            <div class="row">
                                <jsp:include page="content_review_card.jsp"/>
                            </div>
                        </div>
                    </div>
                </c:forEach>

                <jsp:include page="../common/paginator.jsp"/>

            </div>
        </div>

    </div>

    <jsp:include page="../common/scripts.jsp"/>


    <script src="<c:url value="/assets/js/content.js"/>"></script>

</body>

</html>