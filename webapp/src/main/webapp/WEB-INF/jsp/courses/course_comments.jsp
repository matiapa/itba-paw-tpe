<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<jsp:useBean type="ar.edu.itba.paw.models.Course" scope="request" id="course"/>
<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.CourseReview>" scope="request" id="reviews"/>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>${course.name}</title>

    <jsp:include page="../common/styles.jsp"/>
</head>

<body id="page-top">
    <div id="wrapper">

        <jsp:include page="../common/navbar.jsp"/>

        <div class="d-flex flex-column" id="content-wrapper">
            <div id="content">

                <jsp:include page="../common/header.jsp"/>

                <div class="container-fluid" style="width: 100%">
                    <div class="row">
                        <div id="${course.id}" class="card shadow mb-4" style="margin-top: 32px; width: 100%">
                            <div class="card-header py-3">
                                <h6 class="font-weight-bold m-0">
                                    <c:out value="${course.name}"/>
                                </h6>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="card-header py-3">
                            <h6 class="font-weight-bold m-0">
                                <spring:message code="content.contentReviewHeader"/>
                            </h6>
                        </div>
                    </div>
                    <div class="row" style="width: 100%; margin-bottom: 32px">
                        <c:url value="/courses/${course.id}/comments" var="postFormUrl"/>
                        <form:form modelAttribute="ContentReviewForm" action="${postFormUrl}" method="post" style="width: 100%">
                            <div class="form-group" style="width: 100%">
                                <spring:message code="content.contentReviewHelper" var="TextAreaHelper"/>
                                <form:textarea path="reviewBody" class="form-control"  placeholder="${TextAreaHelper}" aria-label="With textarea"/>
                                <form:errors path="reviewBody" cssStyle="color: red" element="p"/>
                            </div>
                            <button type="submit" class="btn btn-primary"><spring:message code="content.contentReviewSubmitButtonText"/></button>
                        </form:form>
                    </div>

                    <c:forEach var="review" items="${reviews}">
                        <c:set var="review" value="${review}" scope="request"/>
                        <div class="row">
                            <jsp:include page="course_review_card.jsp"/>
                        </div>
                    </c:forEach>
                </div>


                <jsp:include page="../common/paginator.jsp"/>

            </div>
        </div>


    </div>

    <jsp:include page="../common/scripts.jsp"/>


    <script src="<c:url value="/assets/js/content.js"/>"></script>

</body>

</html>