<!DOCTYPE html>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="${pageContext.response.locale.language}">

<head>
    <meta charset="utf-8">
    <title><spring:message code="register.title"/></title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <tags:links/>
</head>

<body>
    <div id="spinner" class="show position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
        <div class="spinner-grow" style="width: 3rem; height: 3rem;" role="status">
            <span class="sr-only"><spring:message code="common.loading"/></span>
        </div>
    </div>

    <tags:navigation/>

    <div class="container-xxl py-5">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-5 wow fadeIn" data-wow-delay="0.1s">
                    <div class="bb-panel p-5">
                        <span class="bb-badge py-1 px-4 mb-3 d-inline-block"><spring:message code="register.badge"/></span>
                        <h2 class="bb-page-title mb-4"><spring:message code="register.heading"/></h2>
                        <c:url value="/register" var="registerUrl" />
                        <form:form action="${registerUrl}" method="POST" modelAttribute="registerForm">
                            <div class="row g-3">
                                <div class="col-12">
                                    <label class="form-label" for="firstName"><spring:message code="register.firstName"/></label>
                                    <input type="text" class="form-control" id="firstName" name="firstName">
                                </div>
                                <div class="col-12">
                                    <label class="form-label" for="lastName"><spring:message code="register.lastName"/></label>
                                    <input type="text" class="form-control" id="lastName" name="lastName">
                                </div>
                                <div class="col-12">
                                    <label class="form-label" for="email"><spring:message code="register.email"/></label>
                                    <input type="text" class="form-control" id="email" name="email">
                                </div>
                                <div class="col-12">
                                    <label class="form-label" for="password"><spring:message code="register.password"/></label>
                                    <input type="password" class="form-control" id="password" name="password">
                                </div>
                                <div class="col-12">
                                    <button class="btn btn-primary w-100 py-3" type="submit"><spring:message code="register.submit"/></button>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <tags:footer/>

    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="/ui/lib/wow/wow.min.js"></script>
    <script src="/ui/lib/easing/easing.min.js"></script>
    <script src="/ui/lib/waypoints/waypoints.min.js"></script>
    <script src="/ui/lib/owlcarousel/owl.carousel.min.js"></script>
    <script src="/ui/js/main.js"></script>
</body>
</html>
