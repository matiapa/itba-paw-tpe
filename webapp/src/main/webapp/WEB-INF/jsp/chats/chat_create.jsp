<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean type="java.util.List<ar.edu.itba.paw.models.Career>" scope="request" id="careers"/>


<div class="modal fade" id="popup">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Agregar grupo de chat</h4>
            </div>

            <div class="modal-body">
                <c:url value="/chats/create" var="postUrl"/>
                <form:form modelAttribute="createForm" action="${postUrl}" method="post">

                    <div class="form-group">
                        <form:label path="name">Nombre del grupo</form:label>
                        <form:input type="text" path="name" class="form-control"/>
                        <form:errors path="name" cssStyle="color: red" element="p"/>
                    </div>

                    <div class="form-group">
                        <form:label path="careerId">Carrera del grupo</form:label>

                        <form:select path="careerId" class="form-control">
                            <option value="">Elegí una carrera...</option>
                            <c:forEach var="career" items="${careers}">
                                <form:option value="${career.id}">
                                    <c:out value="${career.name}"/>
                                </form:option>
                            </c:forEach>
                        </form:select>

                        <form:errors path="careerId" cssStyle="color: red" element="p"/>
                    </div>

                    <div class="form-group">
                        <form:label path="creationDate">Fecha de creación del grupo</form:label>
                        <form:input type="date" path="creationDate" class="form-control"/>
                        <form:errors path="creationDate" cssStyle="color: red" element="p"/>
                    </div>

                    <div class="form-group">
                        <form:label path="platform">Plataforma del grupo</form:label>

                        <form:select path="platform" class="form-control">
                            <c:forEach var="platform" items="${platforms}">
                                <option value="">Elegí una plataforma...</option>
                                <form:option value="${platform}">
                                    <c:out value="${platformsTranslate.get(platform)}"/>
                                </form:option>
                            </c:forEach>
                        </form:select>

                        <form:errors path="platform" cssStyle="color: red" element="p"/>
                    </div>

                    <div class="form-group">
                        <form:label path="link">Link al grupo</form:label>
                        <form:input type="text" path="link" class="form-control"/>
                        <form:errors path="link" cssStyle="color: red" element="p"/>
                    </div>

                    <div>
                        <button id="submitChatGroup" type="submit" class="btn btn-primary">Agregar</button>
                    </div>
                </form:form>
            </div>

            <div class="overlay" style="width: 798px; height: 549px"></div>
            <div class="spanner">
                <div class="loader"></div>
                <p>Agregando chat</p>
            </div>

        </div>
    </div>
</div>