<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="formm" uri="http://www.springframework.org/tags/form" %>

<jsp:useBean type="java.lang.Boolean" scope="request" id="emailTaken"/>



<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Registro</title>

    <jsp:include page="../common/styles.jsp"/>

    <link rel="stylesheet" href="<c:url value="/assets/bootstrap/css/bootstrap-select.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/css/overlay.css"/>">
</head>

<body class="bg-gradient-primary" style="background: rgb(2,86,138);">
    <div class="container">
            <div class="card shadow-lg border-0 my-5">
                <div class="card-body p-0">
                    <c:url value="/register" var="postFormUrl"/>

                    <form:form modelAttribute="UserForm" action="${postFormUrl}" method="post">
                        <div class="col">
                            <div class="row">

                                <div class="col-lg-5 d-none d-lg-flex">

                                    <div class="p-5">
                                        <div class="text-center">
                                            <h4 class="text-dark mb-4"><spring:message code="yourInfo"/></h4>
                                        </div>

                                        <div class="row mt-3">
                                            <div class="col-sm-6 mb-3 mb-sm-0">
                                                <spring:message code="name" var="namePlaceholder"/>
                                                <form:input class="form-control form-control-user" type="text" placeholder='${namePlaceholder}' path="name"/>
                                                <form:errors path="name" cssStyle="color: red" element="p"/>
                                            </div>
                                            <div class="col-sm-6">
                                                <spring:message code="lastname" var="lastnamePlaceholder"/>
                                                <form:input class="form-control form-control-user" type="text" placeholder="${lastnamePlaceholder}" path="surname" />
                                                <form:errors path="surname" cssStyle="color: red" element="p"/>
                                            </div>
                                        </div>

                                        <div class="row mt-3">
                                            <div class="col-sm-12">
                                                <spring:message code="email" var="emailPlaceholder"/>
                                                <form:input class="form-control form-control-user" type="email" placeholder="${emailPlaceholder}" path="email" />
                                                <form:errors path="email" cssStyle="color: red" element="p"/>
                                                <c:if test="${emailTaken}">
                                                    <h6 Style="color: red" ><spring:message code="emailTaken"/></h6>
                                                </c:if>

                                            </div>
                                        </div>

                                        <div class="row mt-3">
                                            <div class="col-sm-12">
                                                <spring:message code="password" var="passwordPlaceholder"/>
                                                <form:input class="form-control form-control-user" type="password" placeholder="${passwordPlaceholder}" path="password"/>
                                                <form:errors path="password" cssStyle="color: red" element="p"/>
                                            </div>
                                        </div>

                                        <div class="row mt-3">
                                            <div class="col-sm-12">
                                                <spring:message code="userId" var="userIdPlaceholder"/>
                                                <form:input class="form-control form-control-user" type="number" placeholder="${userIdPlaceholder}" path="id"/>
                                                <form:errors path="id" cssStyle="color: red" element="p"/>
                                                <c:if test="${idTaken}">
                                                    <h6 Style="color: red" ><spring:message code="idTaken"/></h6>
                                                </c:if>
                                            </div>
                                        </div>

                                        <div class="row mt-3">
                                            <div class="col-sm-12">

                                                <form:label path="careerCode"  name="career"><spring:message code="careerName"/></form:label>
                                                <spring:message code="chooseCareer" var="chooseCareerMessage"/>
                                                <form:select path= "careerCode" class="selectpicker" data-live-search="true" title="${chooseCareerMessage}">
                                                    <c:if test="${selectedCareer.present}">
                                                        <option selected value="${selectedCareer.get().code}">${selectedCareer.get().name}</option>
                                                    </c:if>
                                                    <c:forEach var="career" items="${careerList}">
                                                        <option ${career.equals(selectedCareer) ? 'selected' : ''}
                                                                path="career" value="${career.code}" data-tokens="${career.name}">${career.name}</option>
                                                    </c:forEach>
                                                </form:select>
                                                <form:errors path="careerCode" cssStyle="color: red" element="p"/>

                                            </div>
                                        </div>

                                        <div class="text-center"></div>
                                        <div class="text-center"></div>
                                    </div>
                                </div>

                                <div class="col-lg-7">
                                    <div class="p-5" style="border-left: 1px solid rgb(232,233,240) ;">
                                        <div class="text-center">
                                            <h4 class="text-dark mb-4"><spring:message code="studySubjects"/></h4>
                                        </div>

                                        <div class="text-left">
                                            <h6 class="text-dark mb-4"><spring:message code="studySubjectsHelper"/></h6>
                                        </div>

                                        <c:forEach var ="i" begin="0" end ="4">
                                            <div class="row">
                                                <div class="form-group">
                                                    <form:label path="courses[${i}]"  name="courses[${i}]"><spring:message code="Courses"/></form:label>
                                                    <spring:message code="chooseCourse" var="chooseCourseMessage"/>
                                                    <form:select path= "courses[${i}]" class="selectpicker" data-live-search="true" title='${chooseCourseMessage}'>
                                                        <c:if test="${selectedCourses.size()>i}">
                                                            <option selected value="${selectedCourses.get(i).id}">${selectedCourses.get(i).name}</option>
                                                        </c:if>
                                                        <c:forEach var="course" items="${courseList}">
                                                            <option ${course.equals(selectedCourse) ? 'selected' : ''}
                                                                    path="courses[${i}]" value="${course.id}" data-tokens="${course.name}">${course.name}</option>
                                                        </c:forEach>
                                                    </form:select>
                                                    <form:errors path="courses[${i}]" cssStyle="color: red" element="p"/>
                                                </div>
                                            </div>
                                        </c:forEach>
                                        <form:errors path="courses" cssStyle="color: red" element="p"/>
                                    </div>
                                </div>

                            </div>

                            <div class="row mt-5 mb-3 pl-5 pr-5">
                                <input class="btn btn-primary btn-block" type="submit" value="<spring:message code="register"/>">
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>

        <jsp:include page="../common/scripts.jsp"/>

</body>

</html>