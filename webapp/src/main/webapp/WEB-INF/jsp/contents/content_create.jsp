<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<div class="modal fade" id="popup">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Agregar contenido</h4>
            </div>

            <div class="modal-body">
                <c:url value="/contents/create" var="postUrl"/>
                <form:form modelAttribute="createForm" action="${postUrl}" method="post">

                    <div class="form-group">
                        <form:label path="courseId">Curso de destino</form:label>
                        <form:select path="courseId" class="form-control selectpicker" data-live-search="true">
                            <form:option value="">Elegí un curso...</form:option>
                            <c:forEach var="course" items="${courses}">
                                <form:option value="${course.id}"><c:out value="${course.name}"/></form:option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="courseId" cssStyle="color: red" element="div"/>
                    </div>

                    <div class="form-group">
                        <form:label path="name">Título: </form:label>
                        <form:input type="text" path="name" cssClass="form-control"/>
                        <form:errors path="name" cssStyle="color: red" element="p"/>
                    </div>

                    <div class="form-group">
                        <form:label path="link">Link:</form:label>
                        <form:input type="text" path="link" cssClass="form-control"/>
                        <form:errors path="link" cssStyle="color: red" element="p"/>
                    </div>

                    <div class="form-group">
                        <form:label path="description">Breve descripción: </form:label>
                        <form:textarea path="description" cssClass="form-control"/>
                        <form:errors path="description" cssStyle="color: red" element="p"/>
                    </div>

                    <div class="form-group">
                        <form:label path="contentType">Tipo: </form:label>
                        <form:select path="contentType" class="custom-select my-1 mr-sm-2" name="contentType">
                            <form:option value="">Tipo</form:option>
                            <form:option value="exam">Exámen</form:option>
                            <form:option value="guide">Guía</form:option>
                            <form:option value="resume">Resúmen</form:option>
                            <form:option value="note">Apunte</form:option>
                            <form:option value="other">Otro</form:option>
                        </form:select>
                        <form:errors path="contentType" cssStyle="color: red" element="p"/>
                    </div>

                    <div class="form-group">
                        <form:label path="contentDate">Fecha del Apunte: (Opcional) </form:label>
                        <form:input class="border rounded-0" path="contentDate" type="date" name="contentDate"/>
                        <form:errors path="contentDate" cssStyle="color: red" element="p"/>
                    </div>

                    <div>
                        <button id="submitContent" type="submit" class="btn btn-primary ml-3">Enviar</button>
                    </div>
                </form:form>
            </div>

            <div class="overlay" style="width: 798px; height: 549px"></div>
            <div class="spanner">
                <div class="loader"></div>
                <p>Agregando contenido</p>
            </div>

        </div>
    </div>
</div>