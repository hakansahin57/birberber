<!DOCTYPE html>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="${pageContext.response.locale.language}">

<head>
    <meta charset="utf-8">
    <title><spring:message code="password.title"/></title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <tags:links/>
</head>

<body>
    <tags:navigation/>

    <div class="container-xxl py-5">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-5 wow fadeIn" data-wow-delay="0.1s">
                    <div class="bb-panel p-5">
                        <span class="bb-badge py-1 px-4 mb-3 d-inline-block"><spring:message code="password.badge"/></span>
                        <h2 class="bb-page-title mb-4"><spring:message code="password.heading"/></h2>
                        <c:url value="/my-account/update-password" var="updatePasswordUrl" />
                        <form:form action="${updatePasswordUrl}" method="POST" modelAttribute="updatePasswordForm">
                            <div class="row g-3">
                                <div class="col-12">
                                    <label class="form-label" for="currentPassword"><spring:message code="password.current"/></label>
                                    <input type="password" class="form-control" id="currentPassword" name="currentPassword">
                                </div>
                                <div class="col-12">
                                    <label class="form-label" for="newPassword"><spring:message code="password.new"/></label>
                                    <input type="password" class="form-control" id="newPassword" name="newPassword">
                                </div>
                                <div class="col-12">
                                    <label class="form-label" for="newPasswordCheck"><spring:message code="password.confirm"/></label>
                                    <input type="password" class="form-control" id="newPasswordCheck" name="newPasswordCheck">
                                </div>
                                <div class="col-12">
                                    <button class="btn btn-primary w-100 py-3" type="submit"><spring:message code="password.submit"/></button>
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
    <script src="/ui/js/main.js"></script>
</body>
</html>
