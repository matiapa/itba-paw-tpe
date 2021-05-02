<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:if test="${pager != null}">
    <div style="text-align: center;">
        <div style="position: absolute; bottom: 0; display: inline-block;">
            <nav aria-label="navigation" style="margin: auto">
                <ul class="pagination justify-content-center">
                    <c:if test="${pager.page != 0}">
                        <li class="page-item">
                            <button class="page-link" id="${pager.page - 1}">
                                <spring:message code="paginator.prev"/>
                            </button>
                        </li>
                    </c:if>
                    <c:forEach begin="1" step="1" end="${pager.size / pager.limit + 1}" var="num">
                        <li class="page-item">
                            <button class="page-link" id="${num - 1}">
                                <c:out value="${num}"/>
                            </button>
                        </li>
                    </c:forEach>
                    <c:if test="${pager.page + 1 < (pager.size / pager.limit)}">
                        <li class="page-item">
                            <button class="page-link" id="${pager.page + 1}">
                                <spring:message code="paginator.next"/>
                            </button>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </div>
</c:if>