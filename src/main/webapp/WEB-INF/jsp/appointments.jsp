<!DOCTYPE html>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="${pageContext.response.locale.language}">
<head>
    <meta charset="utf-8">
    <title><spring:message code="appointments.title"/></title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <tags:links/>
</head>
<body>
<tags:navigation/>

<div class="container-xxl py-5">
    <div class="container">
        <div class="text-center mb-4">
            <span class="bb-badge py-1 px-4 mb-3 d-inline-block"><spring:message code="appointments.badge"/></span>
            <h1 class="bb-page-title"><spring:message code="appointments.heading"/></h1>
        </div>

        <c:if test="${not empty successMsg}">
            <div class="alert alert-success">${successMsg}</div>
        </c:if>
        <c:if test="${not empty errorMsg}">
            <div class="alert alert-danger">${errorMsg}</div>
        </c:if>

        <div class="row justify-content-center">
            <div class="col-lg-8">
                <c:choose>
                    <c:when test="${empty appointments}">
                        <div class="bb-panel p-5 text-center">
                            <p class="text-muted mb-3"><spring:message code="appointments.empty"/></p>
                            <a href="/stores" class="btn btn-primary"><spring:message code="appointments.findStores"/></a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="appt" items="${appointments}">
                            <div class="card bb-card mb-3">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-start">
                                        <div>
                                            <h5 class="text-primary">${appt.storeName}</h5>
                                            <p class="mb-1">${appt.serviceName}</p>
                                            <p class="mb-1">
                                                <i class="fa fa-clock me-2"></i>
                                                ${appt.startLabel} - ${appt.endLabel}
                                            </p>
                                            <c:if test="${appt.amount > 0}">
                                                <small class="text-muted">
                                                    <spring:message code="appointments.payment"/>: ${appt.amount} TRY
                                                    <c:if test="${not empty appt.transactionId}">
                                                        · ${appt.transactionId}
                                                    </c:if>
                                                </small>
                                            </c:if>
                                        </div>
                                        <span class="badge bg-${appt.status == 'CONFIRMED' ? 'success' : 'warning'}">
                                            <spring:message code="appointments.status.${appt.status}"/>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="/ui/js/main.js"></script>
</body>
</html>
