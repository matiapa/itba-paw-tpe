<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Course>" scope="request" id="courses"/>
<jsp:useBean id="profile" scope="request" type="ar.edu.itba.paw.models.User"/>

<div class="modal fade" id="popup">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="profile.rates.add.title"/></h4>
            </div>

            <div class="modal-body">
                <c:url value="/profile/${profile.id}/rate" var="postUrl"/>
                <form:form id="createReviewForm" modelAttribute="createForm" action="${postUrl}" method="post">

                    <div class="form-group">
                        <form:label path="courseId"><spring:message code="profile.rates.add.course"/></form:label>
                        <form:select path="courseId" class="form-control selectpicker" data-live-search="true">
                            <form:option value=""><spring:message code="chooseCourse"/></form:option>
                            <c:forEach var="course" items="${courses}">
                                <form:option value="${course.id}"><c:out value="${course.name}"/></form:option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="courseId" element="div"/>
                    </div>

                    <div class="form-group">
                        <form:label path="comment"><spring:message code="profile.rates.add.comment"/></form:label>
                        <form:input type="text" path="comment" cssClass="form-control"/>
                        <form:errors path="comment" cssStyle="color: red" element="p"/>
                    </div>

                    <div class="form-group">
                        <form:label path="behaviour"><spring:message code="profile.rates.add.behaviour"/></form:label>
                        <form:input type="text" path="behaviour" cssClass="form-control"/>
                        <form:errors path="behaviour" cssStyle="color: red" element="p"/>
                    </div>

                    <div class="form-group">
                        <form:label path="skill"><spring:message code="profile.rates.add.skill"/></form:label>
                        <form:input type="text" path="skill" cssClass="form-control"/>
                        <form:errors path="skill" cssStyle="color: red" element="p"/>
                    </div>

                    <div>
                        <button id="submitAnnouncement" type="submit" class="btn btn-primary">
                            <spring:message code="profile.rates.add.send"/>
                        </button>
                    </div>
                </form:form>

            </div>

            <div class="overlay" style="width: 798px; height: 509px"></div>
            <div class="spanner">
                <div class="loader"></div>
                <p><spring:message code="announcement.adding"/></p>
            </div>

        </div>
    </div>
</div>