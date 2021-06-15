<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>
<jsp:useBean id="user" scope="request" type="ar.edu.itba.paw.models.User"/>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><spring:message code="people"/></title>

    <jsp:include page="../common/styles.jsp"/>

    <link rel="stylesheet" href="<c:url value="/assets/bootstrap/css/bootstrap-select.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/css/overlay.css"/>">
</head>

<body id="page-top">

    <div id="wrapper">

        <jsp:include page="../common/navbar.jsp"/>

        <div class="d-flex flex-column" id="content-wrapper">
            <div id="content">

                <jsp:include page="../common/header.jsp"/>

                <div class="container-fluid">
                    <div class="col-lg-12 col-xl-12 mb-6">

                        <form id="personSearchForm" action="<c:url value='/profile'/>" method="get">

                            <div style="border: thin solid black">
                                <select id="personId" name="personId" class="selectpicker" data-live-search="true"
                                        title=<spring:message code="chooseCourse"/> data-width="100%">
                                    <c:forEach var="person" items="${profiles}">
                                        <option value="${person.id}" data-tokens="${person.name},${person.surname}">
                                                ${person.fullName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                        </form>

                        <div class="text-center mt-5">
                            <i class="fa fa-question-circle" style="margin-top: 32px;font-size: 32px;"></i>
                            <p style="margin-top: 16px;"><spring:message code="profile.list.choosePerson"/></p>
                        </div>

                    </div>

                    </div>
                </div>

            </div>
        </div>

    <jsp:include page="../common/scripts.jsp"/>

    <script src="<c:url value="/assets/js/profile.js"/>"></script>

</body>
</html>