<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><spring:message code="login"/></title>

    <jsp:include page="common/styles.jsp"/>
</head>

<body class="bg-gradient-primary" style="background: rgb(2,86,138);">
    <div class="container" style="width: 512px;">
        <div class="card shadow-lg o-hidden border-0 my-5">
            <div class="card-body p-0" style="text-align: center;"><img style="margin-top: 20px;width: 200px;" src="assets/img/logo-tran-blue.png">
                <div class="p-5">
                    <form method="POST" action='<c:url value="/login" />'>
                        <h4 class="text-dark mb-4"><spring:message code="welcome"/></h4>
                        <c:if test="${param.error != null}">
                            <div>
                                <p style="color:red">
                                    <spring:message code="login.error"/>
                                    <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
                                        <spring:message code="login.error.reason"/>: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
                                    </c:if>
                                </p>

                            </div>
                        </c:if>
                        <div class="form-group">
                            <input class="form-control" type="email" name="username" placeholder='<spring:message code="login.email"/>'>
                        </div>
                        <div class="form-group">
                            <input class="form-control" type="password" name="password" placeholder='<spring:message code="login.password"/>'>
                        </div>
                        <div class="form-check form-group"><input class="form-check-input" type="checkbox" id="rememberMe" name="rememberme"><label class="form-check-label" for="rememberMe"><spring:message code="login.rememberme"/></label></div>
                        <div class="form-group"><button class="btn btn-primary btn-block" type="submit"><spring:message code="login.login"/></button></div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="common/scripts.jsp"/>
</body>

</html>


