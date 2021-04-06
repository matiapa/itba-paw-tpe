<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>


<div class="row">

    <div class="col">

        <c:if test="${panels[0] != null}">
            <div class="container" style="height: 150px; overflow: hidden">
                <div class="row align-items-center">
                    <div class="col">
                        <h4>${panels[0].title}</h4>
                    </div>
                    <c:if test="${panels[0].moreLink != null}">
                        <div class="col text-right">
                            <a href="<c:url value="${panels[0].moreLink}"/>">Ver más</a>
                        </div>
                    </c:if>
                </div>

                <div class="col mt-3">
                    <jsp:include page="${panels[0].viewName}"/>
                </div>
            </div>

            <hr>
        </c:if>

        <c:if test="${panels[1] != null}">
            <div class="container" style="height: 150px; overflow: hidden">
                <div class="row">
                    <div class="col">
                        <h4>${panels[1].title}</h4>
                    </div>
                    <c:if test="${panels[1].moreLink != null}">
                        <div class="col text-right">
                            <a href="<c:url value="${panels[1].moreLink}"/>">Ver más</a>
                        </div>
                    </c:if>
                </div>

                <div class="col mt-3">
                    <jsp:include page="${panels[1].viewName}"/>
                </div>
            </div>

            <hr>
        </c:if>

        <c:if test="${panels[2] != null}">
            <div class="container">
                <div class="row">
                    <div class="col">
                        <h4>${panels[2].title}</h4>
                    </div>
                    <c:if test="${panels[2].moreLink != null}">
                        <div class="col text-right">
                            <a href="<c:url value="${panels[2].moreLink}"/>">Ver más</a>
                        </div>
                    </c:if>
                </div>

                <div class="col mt-3">
                    <jsp:include page="${panels[2].viewName}"/>
                </div>
            </div>
        </c:if>

    </div>

    <c:if test="${panels[3] != null}">
        <div class="col-xl-7" style="border-width: 1px; border-color: rgb(229,229,229);
            border-left-style: solid;">
            <div class="row">
                <div class="col">
                    <h4><c:out value="${panels[3].title}"/></h4>
                </div>
            </div>

            <div class="mt-3">
                <jsp:include page="${panels[3].viewName}"/>
            </div>
        </div>
    </c:if>

</div>