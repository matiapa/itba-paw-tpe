<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<div class="modal fade" id="popup">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Agregar encuesta</h4>
            </div>

            <div class="modal-body">
                <c:url value="/polls/create" var="postUrl"/>
                <form:form modelAttribute="createForm" id="createPollForm" method="post" action="${postUrl}">
                    <div class="row">
                        <div class="col">

                            <h5 class="mb-3">Detalles</h5>

                            <div class="form-group">
                                <label>Destino de la encuesta</label>
                                <select id="pollTarget" class="form-control">
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
                                <form:input type="text" path="title" class="form-control"/>
                                <form:errors path="title" cssStyle="color: red" element="p"/>
                            </div>

                            <div class="form-group">
                                <form:label path="description">Descripción</form:label>
                                <form:textarea path="description" cssClass="form-control"/>
                                <form:errors path="description" cssStyle="color: red" element="p"/>
                            </div>

                            <div class="form-group">
                                <form:label path="expiryDate">Fecha de expiración (dd-mm-yyyy)</form:label>
                                <form:input type="date" path="expiryDate" class="form-control"/>
                                <form:errors path="expiryDate" cssStyle="color: red" element="p"/>
                            </div>

                        </div>

                        <div class="col">

                            <h5 class="mb-3">Opciones</h5>

                            <div class="form-group">
                                <form:label path="options[0]">Opción 1</form:label>
                                <form:input type="text" path="options[0]" class="form-control"/>
                                <form:errors path="options[0]" element="p"/>
                            </div>

                            <div class="form-group">
                                <form:label path="options[1]">Opción 2</form:label>
                                <form:input type="text" path="options[1]" class="form-control"/>
                                <form:errors path="options[1]" element="p"/>
                            </div>

                            <div class="form-group">
                                <form:label path="options[2]">Opción 3</form:label>
                                <form:input type="text" path="options[2]" class="form-control"/>
                                <form:errors path="options[2]" element="p"/>
                            </div>

                            <div class="form-group">
                                <form:label path="options[3]">Opción 4</form:label>
                                <form:input type="text" path="options[3]" class="form-control"/>
                                <form:errors path="options[3]" element="p"/>
                            </div>

                            <form:errors path="options" element="p"/>

                        </div>

                    </div>

                    <div>
                        <button id="submitPoll" type="submit" class="btn btn-primary">
                            Enviar
                        </button>
                    </div>
                </form:form>
            </div>

            <div class="overlay" style="width: 798px; height: 549px"></div>
            <div class="spanner">
                <div class="loader"></div>
                <p>Agregando encuesta</p>
            </div>

        </div>
    </div>
</div>