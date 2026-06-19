<!DOCTYPE html>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="${pageContext.response.locale.language}">
<head>
    <meta charset="utf-8">
    <title><spring:message code="checkout.title"/></title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <tags:links/>
</head>
<body>
<tags:navigation/>

<div class="container-xxl py-5">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="bb-panel p-5">
                    <h3 class="text-primary mb-4"><i class="fa fa-credit-card me-2"></i><spring:message code="checkout.heading"/></h3>

                    <div class="bb-checkout-summary p-4 mb-4">
                        <h5 class="text-primary">${store.name}</h5>
                        <p class="mb-1"><spring:message code="checkout.service"/>: <strong>${service.name}</strong></p>
                        <p class="mb-1"><spring:message code="checkout.time"/>: <strong>${slotLabel}</strong></p>
                        <p class="mb-0"><spring:message code="checkout.amount"/>:
                            <strong>
                                <c:choose>
                                    <c:when test="${not empty service.price}">${service.price.value}</c:when>
                                    <c:otherwise>0</c:otherwise>
                                </c:choose>
                                TRY
                            </strong>
                        </p>
                    </div>

                    <form action="/appointments/pay" method="post">
                        <c:if test="${not empty appointment}">
                            <input type="hidden" name="appointmentId" value="${appointment.id}">
                        </c:if>
                        <input type="hidden" name="storeId" value="${bookingForm.storeId}">
                        <input type="hidden" name="serviceId" value="${bookingForm.serviceId}">
                        <input type="hidden" name="date" value="${bookingForm.date}">
                        <input type="hidden" name="startTime" value="${bookingForm.startTime}">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                        <div class="mb-3">
                            <label class="form-label"><spring:message code="checkout.cardHolder"/></label>
                            <input type="text" name="cardHolder" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label"><spring:message code="checkout.cardNumber"/></label>
                            <input type="text" name="cardNumber" class="form-control"
                                   placeholder="4242 4242 4242 4242" maxlength="19" required>
                        </div>
                        <div class="row">
                            <div class="col-6 mb-3">
                                <label class="form-label"><spring:message code="checkout.cardExpiry"/></label>
                                <input type="text" name="cardExpiry" class="form-control"
                                       placeholder="AA/YY" required>
                            </div>
                            <div class="col-6 mb-3">
                                <label class="form-label"><spring:message code="checkout.cardCvv"/></label>
                                <input type="text" name="cardCvv" class="form-control"
                                       maxlength="4" required>
                            </div>
                        </div>
                        <p class="text-muted small"><spring:message code="checkout.demoNote"/></p>
                        <button type="submit" class="btn btn-primary w-100 py-3">
                            <spring:message code="checkout.submit"/>
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="/ui/js/main.js"></script>
</body>
</html>
