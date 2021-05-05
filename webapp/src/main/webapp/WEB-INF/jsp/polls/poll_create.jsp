<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>

<div class="modal fade" id="popup">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="poll.add"/></h4>
            </div>

            <div class="modal-body">
                <c:url value="/polls" var="postUrl"/>
                <form:form modelAttribute="createForm" id="createPollForm" method="post" action="${postUrl}">
                    <div class="row">
                        <div class="col">

                            <h5 class="mb-3"><spring:message code="details"/></h5>

                            <div class="form-group">
                                <label><spring:message code="poll.destination"/></label>
                                <select id="pollTarget" class="form-control">
                                    <option value="general"><spring:message code="generalDestination"/></option>
                                    <option value="career"><spring:message code="careerDestination"/></option>
                                    <option value="course"><spring:message code="courseDestination"/></option>
                                </select>
                            </div>
                            
                            <div hidden id="careerTarget" class="form-group">
                                <form:label path="careerCode"><spring:message code="targetCareer"/></form:label>
                                <form:select path="careerCode" class="form-control">
                                    <form:option value=""><spring:message code="chooseCareer"/></form:option>
                                    <c:forEach var="career" items="${careers}">
                                        <form:option value="${career.code}"><c:out value="${career.name}"/></form:option>
                                    </c:forEach>
                                </form:select>
                                <form:errors path="careerCode" element="div"/>
                            </div>

                            <div hidden id="courseTarget" class="form-group">
                                <form:label path="courseId"><spring:message code="targetCourse"/></form:label>
                                <form:select path="courseId" class="form-control selectpicker" data-live-search="true">
                                    <form:option value=""><spring:message code="chooseCourse"/></form:option>
                                    <c:forEach var="course" items="${courses}">
                                        <form:option value="${course.id}"><c:out value="${course.name}"/></form:option>
                                    </c:forEach>
                                </form:select>
                                <form:errors path="courseId" element="div"/>
                            </div>

                            <div class="form-group">
                                <form:label path="title"><spring:message code="form.title"/></form:label>
                                <form:input type="text" path="title" class="form-control"/>
                                <form:errors path="title" cssStyle="color: red" element="p"/>
                            </div>

                            <div class="form-group">
                                <form:label path="description"><spring:message code="form.description"/></form:label>
                                <form:textarea path="description" cssClass="form-control"/>
                                <form:errors path="description" cssStyle="color: red" element="p"/>
                            </div>

                            <div class="form-group">
                                <form:label path="expiryDate"><spring:message code="expiryDate"/></form:label>
                                <form:input type="date" path="expiryDate" class="form-control"/>
                                <form:errors path="expiryDate" cssStyle="color: red" element="p"/>
                            </div>

                        </div>

                        <div class="col" id="optionsCol">

                            <h5 class="mb-3"><spring:message code="options"/></h5>

                            <div class="form-group">
                                <form:label path="options[0]"><spring:message code="option1"/></form:label>
                                <form:input type="text" path="options[0]" class="form-control"/>
                                <form:errors path="options[0]" element="p"/>
                            </div>

                            <div class="form-group">
                                <form:label path="options[1]"><spring:message code="option2"/></form:label>
                                <form:input type="text" path="options[1]" class="form-control"/>
                                <form:errors path="options[1]" element="p"/>
                            </div>

                            <div class="form-group">
                                <form:label path="options[2]"><spring:message code="option3"/></form:label>
                                <form:input type="text" path="options[2]" class="form-control"/>
                                <form:errors path="options[2]" element="p"/>
                            </div>

                            <div class="form-group">
                                <form:label path="options[3]"><spring:message code="option4"/></form:label>
                                <form:input type="text" path="options[3]" class="form-control"/>
                                <form:errors path="options[3]" element="p"/>
                            </div>

                            <form:errors path="options" element="p"/>

                        </div>

                    </div>

                    <div>
                        <button id="submitPoll" type="submit" class="btn btn-primary">
                            <spring:message code="form.sendButton"/>
                        </button>
                    </div>
                </form:form>
            </div>

            <div class="overlay" style="width: 798px; height: 549px"></div>
            <div class="spanner">
                <div class="loader"></div>
                <p><spring:message code="poll.adding"/></p>
            </div>

        </div>
    </div>
</div>