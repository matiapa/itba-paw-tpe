<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><c:out value="${user.name} ${user.surname}"/></title>

    <jsp:include page="../common/styles.jsp"/>
</head>

<body id="page-top">
    <div id="wrapper">

    <jsp:include page="../common/navbar.jsp"/>

    <div class="d-flex flex-column" id="content-wrapper">
    <div id="content">

        <jsp:include page="../common/header.jsp"/>

        <div class="container-fluid">
            <h3 class="text-dark mb-4"><c:out value="${user.name} ${user.surname}"/></h3>

            <div class="row mb-3">

                <div class="col-lg-4">
                    <div class="card mb-3">
                        <div class="card-body text-center shadow">
                            <c:url value="/assets/img/avatars/avatar.png" var="defaultImage"/>
                            <img class="rounded-circle mb-3 mt-4" id="currentPicture" width="160" height="160"
                                 src="${user.profileImgB64 != null ? user.profileImgB64 : defaultImage}" >
                        </div>
                    </div>
                </div>

                <div class="col-lg-8">
                    <div class="card shadow mb-3">
                        <div class="card-header py-3">
                            <p class="text-white m-0 font-weight-bold"><spring:message code="personalInfo"/></p>
                        </div>
                        <div class="card-body">
                            <form>
                                <div class="form-row">
                                    <div class="col">
                                        <div class="form-group">
                                            <label><strong><spring:message code="userId"/></strong></label>
                                            <p><c:out value="${user.id}"/></p>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="form-group">
                                            <label><strong><spring:message code="email"/></strong></label>
                                            <p><c:out value="${user.email}"/></p>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="col">
                                        <div class="form-group">
                                            <label><strong><spring:message code="career"/></strong></label>
                                            <p><c:out value="${user.name}"/></p>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="form-group">
                                            <label><strong><spring:message code="joinedOn"/></strong></label>
                                            <p><fmt:formatDate type="date" dateStyle="short" value="${user.signupDate}"/></p>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
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