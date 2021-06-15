<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
  <title><spring:message code="register.validate_email.PageTitle"/></title>

  <jsp:include page="../common/styles.jsp"/>

  <link rel="stylesheet" href="<c:url value="/assets/bootstrap/css/bootstrap-select.min.css"/>">
  <link rel="stylesheet" href="<c:url value="/assets/css/overlay.css"/>"></head>
</head>

<body class="bg-gradient-primary" style="background: rgb(2,86,138);">
<div class="container">
  <div class="card shadow-lg o-hidden border-0 my-5">
    <div class="card-body">

      <c:choose>
        <c:when test="${worked}">
          <h3><spring:message code="register.validate_email.SuccessTitle"/></h3>
          <h5 class="mt-3"><spring:message code="register.validate_email.SuccessMessage"/></h5>
          <a class="mt-3" href='<c:url value="/" />'>
            <h5><spring:message code="register.validate_email.GoToHomeButton"/></h5>
          </a>
        </c:when>
        <c:otherwise>
          <h3><spring:message code="register.validate_email.FailureTitle"/></h3>
          <h5 class="mt-3"><spring:message code="register.validate_email.FailureMessage"/></h5>
        </c:otherwise>
      </c:choose>
      <h3></h3>

    </div>
  </div>
</div>

<jsp:include page="../common/scripts.jsp"/>

<script src="<c:url value="/assets/js/popper.min.js"/>" ></script>
<script src="<c:url value="/assets/bootstrap/js/bootstrap-select.min.js"/>"></script>

</body>

</html>