<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><spring:message code="chat.groups"/></title>

    <jsp:include page="../common/styles.jsp"/>

    <link rel="stylesheet" href="<c:url value="/assets/css/overlay.css"/>"></head>
</head>

<body id="page-top">

    <div id="wrapper">

        <jsp:include page="../common/navbar.jsp"/>

        <div class="d-flex flex-column" id="content-wrapper">
            <div id="content">

                <jsp:include page="../common/header.jsp"/>

                <div class="container-fluid">
                    <div class="col-lg-6 col-xl-12 mb-4">

                        <div class="mb-3" align="right">
                            <a href="#popup" data-toggle="modal">
                                <button class="btn btn-primary btn-sm">
                                    <i class="material-icons pull-left">add</i>
                                    <spring:message code="chat.add"/>
                                </button>
                            </a>
                        </div>

                        <form method="get" id="chatListFilterForm">
                            <select class="custom-select my-1 mr-sm-2" name="careerCode" id="careerCode">
                                <option ${selectedCareer == null ? 'selected' : ''} value="">
                                    <spring:message code="chooseCareer"/>
                                </option>
                                <c:forEach var="career" items="${careers}">
                                    <option ${selectedCareer.code.equals(career.code) ? 'selected' : ''} value="${career.code}">
                                            ${career.name}
                                    </option>
                                </c:forEach>
                            </select>

                            <c:if test="${selectedCareer != null}">
                                <div class="row align-items-center" style="margin-top: 16px;margin-bottom: 32px;">

                                    <div class="col-xl-2">
                                        <select class="custom-select my-1 mr-sm-2" name="platform">
                                            <option ${selectedPlatform == null ? 'selected' : ''} value="">
                                                <spring:message code="chat.platform"/>
                                            </option>
                                            <c:forEach var="platform" items="${platforms}">
                                                <option ${selectedPlatform == platform ? 'selected' : ''} value="${platform}">
                                                    <spring:message code="enum.platform.${platform.name()}"/>
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="col-xl-1">
                                        <select class="custom-select my-1 mr-sm-2" name="year">
                                            <option ${selectedYear == null ? 'selected' : ''} value="">
                                                <spring:message code="year"/>
                                            </option>
                                            <c:forEach var="year" items="${years}">
                                                <option ${selectedYear == year ? 'selected' : ''} value="${year}">
                                                        ${year}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="col-xl-2">
                                        <select class="custom-select my-1 mr-sm-2" name="quarter">
                                            <option ${selectedQuarter == null ? 'selected' : ''} value="">
                                                <spring:message code="quarter"/>
                                            </option>
                                            <c:forEach var="quarter" items="${quarters}">
                                                <option ${selectedQuarter == quarter ? 'selected' : ''} value="${quarter}">
                                                    <spring:message code="enum.quarter.${quarter}"/>
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="col-xl-2">
                                        <input type="submit" class="btn btn-primary" value=<spring:message code="filter"/>>
                                    </div>

                                </div>
                            </c:if>
                        </form>

                        <c:choose>
                            <c:when test="${selectedCareer != null && chatgroups.size() > 0}">
                                <div class="table-responsive table mt-2" id="dataTable-1" role="grid"
                                     aria-describedby="dataTable_info" style="margin-top: 32px;background: #ffffff;">
                                    <table class="table my-0" id="dataTable">
                                        <thead>
                                        <tr>
                                            <th><spring:message code="chat.platform"/></th>
                                            <th><spring:message code="year"/></th>
                                            <th><spring:message code="quarter"/></th>
                                            <th><spring:message code="chat.link"/></th>
                                            <c:if test="${canDelete}">
                                                <th><spring:message code="chat.AdminOptions"/></th>
                                            </c:if>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="chatgroup" items="${chatgroups}">
                                            <tr>
                                                <td>${chatgroup.name}</td>
                                                <td>${chatgroup.creationYear}</td>
                                                <td>${chatgroup.creationQuarter}</td>
                                                <td>
                                                    <a class="btn btn-link" href='<c:out value="${chatgroup.link}"/>' target="_blank" rel="noopener noreferrer">Abrir</a>
                                                </td>
                                                <c:if test="${canDelete}">
                                                    <td>
                                                        <c:url var="url" value="/chats/${chatgroup.id}/delete"/>
                                                        <form action="${url}" method="post">
                                                            <button type="submit" class="btn btn-icon" style="color:red">
                                                                <i class="material-icons">delete</i>
                                                            </button>
                                                        </form>

                                                    </td>
                                                </c:if>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                        <tfoot>
                                        <tr></tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </c:when>

                            <c:when test="${selectedCareer != null && chatgroups.size() == 0}">
                                <div class="text-center mt-5">
                                    <i class="fa fa-question-circle" style="margin-top: 32px;font-size: 32px;"></i>
                                    <p style="margin-top: 16px;"><spring:message code="noContent"/></p>
                                </div>
                            </c:when>

                            <c:otherwise>
                                <div class="text-center mt-5">
                                    <i class="fa fa-question-circle" style="margin-top: 32px;font-size: 32px;"></i>
                                    <p style="margin-top: 16px;"><spring:message code="chat.chooseCareerPlease"/></p>
                                </div>
                            </c:otherwise>
                        </c:choose>

                    </div>

                    <jsp:include page="../common/paginator.jsp"/>
                </div>
            </div>

        </div>

    </div>

    <jsp:include page="chat_create.jsp"/>

    <jsp:include page="../common/scripts.jsp"/>

    <c:if test="${showCreateForm}">
        <script>$('#popup').modal('show');</script>
    </c:if>

    <script src="<c:url value="/assets/js/chat.js"/>"></script>

</body>

</html>