<!DOCTYPE html>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="${pageContext.response.locale.language}">

<head>
    <meta charset="utf-8">
    <title><spring:message code="profile.title"/></title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <tags:links/>
</head>

<body>
    <tags:navigation/>

    <div class="container-xxl py-5">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-6 wow fadeIn" data-wow-delay="0.1s">
                    <div class="bb-panel p-5">
                        <span class="bb-badge py-1 px-4 mb-3 d-inline-block"><spring:message code="profile.badge"/></span>
                        <h2 class="bb-page-title mb-4"><spring:message code="profile.heading"/></h2>
                        <c:url value="/my-account/update-profile" var="updateProfileUrl" />
                        <form:form action="${updateProfileUrl}" method="POST" modelAttribute="updateProfileForm">
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <div class="form-floating">
                                        <input type="text" class="form-control" id="name" name="firstName" value="${updateProfileForm.firstName}">
                                        <label for="name"><spring:message code="profile.firstName"/></label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-floating">
                                        <input type="text" class="form-control" id="lastName" name="lastName" value="${updateProfileForm.lastName}">
                                        <label for="lastName"><spring:message code="profile.lastName"/></label>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-floating">
                                        <input type="text" class="form-control" id="email" name="email" value="${updateProfileForm.email}">
                                        <label for="email"><spring:message code="profile.email"/></label>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-floating">
                                        <input type="text" class="form-control" id="phone" name="phoneNumber" value="${updateProfileForm.phoneNumber}">
                                        <label for="phone"><spring:message code="profile.phone"/></label>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <button class="btn btn-primary w-100 py-3" type="submit"><spring:message code="profile.submit"/></button>
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
