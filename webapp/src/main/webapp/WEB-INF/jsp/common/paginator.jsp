<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:if test="${pager != null}">
    <div style="position: absolute; bottom: 0; text-align: center; width: 90%">
        <div style="display: inline-block;">
            <nav aria-label="navigation" style="margin: auto">
                <ul class="pagination justify-content-center">

                    <c:if test="${pager.currPage != 0}">
                        <li class="page-item">
                            <button class="page-link" id="${pager.currPage - 1}">
                                <spring:message code="paginator.prev"/>
                            </button>
                        </li>
                    </c:if>

                    <c:forEach begin="${10*(pager.currPage/10)}" step="1" end="${10*(pager.currPage/10+1)-1}" var="num">
                        <li class="page-item ${num==pager.currPage ? 'active' : ''}">
                            <button class="page-link" id="${num}">
                                <c:out value="${num + 1}"/>
                            </button>
                        </li>
                    </c:forEach>

                    <c:if test="${pager.currPage < pager.lastPage}">
                        <li class="page-item">
                            <button class="page-link" id="${pager.currPage + 1}">
                                <spring:message code="paginator.next"/>
                            </button>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </div>
</c:if>