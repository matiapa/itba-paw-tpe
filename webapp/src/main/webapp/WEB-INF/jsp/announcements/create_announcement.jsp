<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Career>" scope="request" id="careers"/>
<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Course>" scope="request" id="courses"/>


<div class="modal fade" id="popup">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Agregar anuncio</h4>
            </div>

            <div class="modal-body">
                <form:form id="createAnnouncementForm" modelAttribute="createForm" method="post">

                    <div class="form-group">
                        <label>Destino del anuncio</label>
                        <select id="announcementTarget" class="form-control">
                            <option value="general">La facultad en general</option>
                            <option value="career">Una carrera específica</option>
                            <option value="course">Un curso específico</option>
                        </select>
                    </div>

                    <div hidden id="careerTarget" class="form-group">
                        <form:label path="careerId">Carrera de destino</form:label>
                        <form:select path="careerId" class="form-control">
                            <form:option value="">Elegí una carrera...</form:option>
                            <c:forEach var="career" items="${careers}">
                                <form:option value="${career.id}"><c:out value="${career.name}"/></form:option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="careerId" element="div"/>
                    </div>

                    <div hidden id="courseTarget" class="form-group">
                        <form:label path="courseId">Curso de destino</form:label>
                        <form:select path="courseId" class="form-control selectpicker" data-live-search="true">
                            <form:option value="">Elegí un curso...</form:option>
                            <c:forEach var="course" items="${courses}">
                                <form:option value="${course.id}"><c:out value="${course.name}"/></form:option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="courseId" element="div"/>
                    </div>

                    <div class="form-group">
                        <form:label path="title">Título</form:label>
                        <form:input type="text" path="title" cssClass="form-control"/>
                        <form:errors path="title" cssClass="invalid-feedback" element="div"/>
                    </div>

                    <div class="form-group">
                        <form:label path="summary">Breve descripción</form:label>
                        <form:input type="text" path="summary" cssClass="form-control"/>
                        <form:errors path="summary" cssClass="invalid-feedback" element="div"/>
                    </div>

                    <div class="form-group">
                        <form:label path="content">Contenido</form:label>
                        <form:textarea path="content" cssClass="form-control"/>
                        <form:errors path="content" cssClass="invalid-feedback" element="div"/>
                    </div>

                    <div>
                        <button id="submitAnnouncement" type="submit" class="btn btn-primary">
                            Enviar
                        </button>
                    </div>
                </form:form>

            </div>

            <div class="overlay" style="width: 798px; height: 509px"></div>
            <div class="spanner">
                <div class="loader"></div>
                <p>Agregando anuncio</p>
            </div>

        </div>
    </div>
</div>