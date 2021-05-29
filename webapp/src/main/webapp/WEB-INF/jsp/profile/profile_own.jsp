<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Tu Perfil</title>

    <jsp:include page="../common/styles.jsp"/>
</head>

<body id="page-top">
    <div id="wrapper">

    <jsp:include page="../common/navbar.jsp"/>

    <div class="d-flex flex-column" id="content-wrapper">
    <div id="content">

        <jsp:include page="../common/header.jsp"/>

        <div class="container-fluid">
            <h3 class="text-dark mb-4"><spring:message code="yourProfile"/></h3>

            <div class="row mb-3">

                <div class="col-lg-4">
                    <div class="card mb-3">
                        <div class="card-body text-center shadow">
                            <c:url value="/assets/img/avatars/avatar.png" var="defaultImage"/>
                            <img class="rounded-circle mb-3 mt-4" id="currentPicture" width="160" height="160"
                                 src="${user.profilePicture != null ? user.profilePicture : defaultImage}" >
                            <c:url value="/profile/own/image" var="postFormUrl"/>
                            <form:form method="post" action="${postFormUrl}" enctype="multipart/form-data">
                                <div class="custom-file">
                                    <input type="file" class="custom-file-input" accept="image/*"
                                           name="newPicture" id="newPicture">
                                    <label class="custom-file-label" for="newPicture"><spring:message code="chooseNewPicture"/></label>
                                </div>
                                <div class="row justify-content-center mt-3">
                                    <div id="submitNewPicture" hidden>
                                        <button class="btn btn-success btn-sm" type="submit"><spring:message code="confirm"/></button>
                                    </div>
                                    <div id="cancelNewPicture" hidden class="ml-3">
                                        <button class="btn btn-danger btn-sm" type="button"><spring:message code="cancel"/></button>
                                    </div>
                                </div>
                            </form:form>
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
                                            <input class="form-control" type="text"
                                               value="<c:out value="${user.id}"/>" disabled="true">
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="form-group">
                                            <label><strong><spring:message code="email"/></strong></label>
                                            <input class="form-control" type="email"
                                               value="<c:out value="${user.email}"/>" disabled="true">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="col">
                                        <div class="form-group">
                                            <label><strong><spring:message code="name"/></strong></label>
                                            <input class="form-control" type="text"
                                               value="<c:out value="${user.name}"/>" disabled="true">
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="form-group">
                                            <label><strong><spring:message code="lastname"/></strong></label>
                                            <input class="form-control" type="text"
                                               value="<c:out value="${user.surname}"/>" disabled="true">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <span><i class="material-icons" style="vertical-align: middle;">help</i></span>
                                    <spring:message code="mailSupport"/>
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