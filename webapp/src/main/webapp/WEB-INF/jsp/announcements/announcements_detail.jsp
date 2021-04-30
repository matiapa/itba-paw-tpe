<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags"%>


<jsp:useBean type="ar.edu.itba.paw.models.Announcement" scope="request" id="announcement"/>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>${announcement.title}</title>

    <jsp:include page="../common/styles.jsp"/>
</head>

<body id="page-top">
    <div id="wrapper">

        <jsp:include page="../common/navbar.jsp"/>

        <div class="d-flex flex-column" id="content-wrapper">
            <div id="content">

                <jsp:include page="../common/header.jsp"/>

                <div class="container-fluid">
                    <div class="col">
                        <div class="row">
                            <a href="javascript:history.back()"><spring:message code="back"/></a>
                        </div>
                        <div class="row">
                            <div id="${announcement.id}" class="card shadow mb-4" style="margin-top: 32px; width: 100%">
                                <div class="card-header py-3">
                                    <h6 class="font-weight-bold m-0"><c:out value="${announcement.title}"/></h6>
                                </div>
                                <div class="col mr-2" style="padding: 16px 24px;">
                                    <p style="padding-top: 0;"><c:out value="${announcement.summary}"/></p>
                                    <p style="padding-top: 0;"><c:out value="${announcement.content}"/></p>
                                    <span class="text-xs">
                                        <c:out value="Publicado por ${announcement.uploader.name} el "/>
                                        <fmt:formatDate type="both" dateStyle = "short" timeStyle = "short" value="${announcement.uploadDate}"/>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>

    </div>

    <jsp:include page="../common/scripts.jsp"/>

</body>

</html>