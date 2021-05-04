<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<div class="modal fade" id="popup">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="content.add"/></h4>
            </div>

            <div class="modal-body">
                <c:url value="/contents" var="postUrl"/>
                <form:form modelAttribute="createForm" action="${postUrl}" method="post">

                    <div class="form-group">
                        <form:label path="courseId"><spring:message code="targetCourse"/></form:label>
                        <form:select path="courseId" class="form-control selectpicker" data-live-search="true">
                            <form:option value=""><spring:message code="chooseCourse"/></form:option>
                            <c:forEach var="course" items="${courses}">
                                <form:option value="${course.id}"><c:out value="${course.name}"/></form:option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="courseId" cssStyle="color: red" element="div"/>
                    </div>

                    <div class="form-group">
                        <form:label path="name"><spring:message code="form.title"/></form:label>
                        <form:input type="text" path="name" cssClass="form-control"/>
                        <form:errors path="name" cssStyle="color: red" element="p"/>
                    </div>

                    <div class="form-group">
                        <form:label path="link"><spring:message code="form.link"/></form:label>
                        <form:input type="text" path="link" cssClass="form-control"/>
                        <form:errors path="link" cssStyle="color: red" element="p"/>
                    </div>

                    <div class="form-group">
                        <form:label path="description"><spring:message code="form.description"/></form:label>
                        <form:textarea path="description" cssClass="form-control"/>
                        <form:errors path="description" cssStyle="color: red" element="p"/>
                    </div>

                    <div class="form-group">
                        <form:label path="contentType"><spring:message code="form.type"/></form:label>
                        <form:select path="contentType" class="custom-select my-1 mr-sm-2" name="contentType">
                            <form:option value=""><spring:message code="form.type"/></form:option>
                            <form:option value="exam"><spring:message code="exam"/></form:option>
                            <form:option value="guide"><spring:message code="guide"/></form:option>
                            <form:option value="resume"><spring:message code="resume"/></form:option>
                            <form:option value="note"><spring:message code="note"/></form:option>
                            <form:option value="other"><spring:message code="other"/></form:option>
                        </form:select>
                        <form:errors path="contentType" cssStyle="color: red" element="p"/>
                    </div>

                    <div class="form-group">
                        <form:label path="contentDate"><spring:message code="noteDate"/></form:label>
                        <form:input class="border rounded-0" path="contentDate" type="date" name="contentDate"/>
                        <form:errors path="contentDate" cssStyle="color: red" element="p"/>
                    </div>

                    <div>
                        <button id="submitContent" type="submit" class="btn btn-primary ml-3"><spring:message code="form.sendButton"/></button>
                    </div>
                </form:form>
            </div>

            <div class="overlay" style="width: 798px; height: 549px"></div>
            <div class="spanner">
                <div class="loader"></div>
                <p><spring:message code="content.adding"/></p>
            </div>

        </div>
    </div>
</div>