<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Career>" scope="request" id="careers"/>
<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Course>" scope="request" id="courses"/>


<div class="modal fade" id="popup">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">"announcement.add"</h4>
            </div>

            <div class="modal-body">
                <c:url value="/announcements/create" var="postUrl"/>
                <form:form id="createAnnouncementForm" modelAttribute="createForm" action="${postUrl}" method="post">

                    <div class="form-group">
                        <label>"announcement.destination"</label>
                        <select id="announcementTarget" class="form-control">
                            <option value="general">"announcement.generalDestination"</option>
                            <option value="career">"announcement.careerDestination"</option>
                            <option value="course">"announcement.courseDestination"</option>
                        </select>
                    </div>

                    <div hidden id="careerTarget" class="form-group">
                        <form:label path="careerId">"announcement.targetCareer"</form:label>
                        <form:select path="careerId" class="form-control">
                            <form:option value="">"chooseCareer"</form:option>
                            <c:forEach var="career" items="${careers}">
                                <form:option value="${career.id}"><c:out value="${career.name}"/></form:option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="careerId" element="div"/>
                    </div>

                    <div hidden id="courseTarget" class="form-group">
                        <form:label path="courseId">"announcement.targetCourse"</form:label>
                        <form:select path="courseId" class="form-control selectpicker" data-live-search="true">
                            <form:option value="">"announcement.chooseCourse"</form:option>
                            <c:forEach var="course" items="${courses}">
                                <form:option value="${course.id}"><c:out value="${course.name}"/></form:option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="courseId" element="div"/>
                    </div>

                    <div class="form-group">
                        <form:label path="title">"announcement.formTitle"</form:label>
                        <form:input type="text" path="title" cssClass="form-control"/>
                        <form:errors path="title" cssStyle="color: red" element="p"/>
                    </div>

                    <div class="form-group">
                        <form:label path="summary">"announcement.formDescription"</form:label>
                        <form:input type="text" path="summary" cssClass="form-control"/>
                        <form:errors path="summary" cssStyle="color: red" element="p"/>
                    </div>

                    <div class="form-group">
                        <form:label path="content">"announcement.formContent"</form:label>
                        <form:textarea path="content" cssClass="form-control"/>
                        <form:errors path="content" cssStyle="color: red" element="p"/>
                    </div>

                    <div>
                        <button id="submitAnnouncement" type="submit" class="btn btn-primary">
                            "announcement.formSendButton"
                        </button>
                    </div>
                </form:form>

            </div>

            <div class="overlay" style="width: 798px; height: 509px"></div>
            <div class="spanner">
                <div class="loader"></div>
                <p>"announcement.adding"</p>
            </div>

        </div>
    </div>
</div>