<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- Navbar Start -->
<nav class="navbar navbar-expand-lg bb-navbar navbar-light sticky-top py-lg-0 px-lg-4 wow fadeIn" data-wow-delay="0.1s">
    <a href="/" class="navbar-brand ms-2 ms-lg-0">
        <span class="bb-logo"><i class="fa fa-cut me-2"></i><spring:message code="app.name"/></span>
    </a>
    <button type="button" class="navbar-toggler me-3" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarCollapse">
        <div class="navbar-nav ms-auto p-3 p-lg-0">
            <a href="/" class="nav-item nav-link"><spring:message code="nav.home"/></a>
            <a href="/stores" class="nav-item nav-link"><spring:message code="nav.stores"/></a>
            <c:if test="${not empty pageContext.request.remoteUser}">
                <a href="/appointments" class="nav-item nav-link"><spring:message code="nav.appointments"/></a>
            </c:if>
        </div>
        <div class="d-flex align-items-center gap-1 ms-lg-2">
            <a href="?lang=tr" class="btn btn-sm btn-link text-decoration-none px-2">TR</a>
            <span class="text-muted">|</span>
            <a href="?lang=en" class="btn btn-sm btn-link text-decoration-none px-2">EN</a>
        </div>
        <c:choose>
            <c:when test="${empty pageContext.request.remoteUser}">
                <a href="/login" class="btn btn-primary py-2 px-4 ms-lg-3 d-none d-lg-inline-block"><spring:message code="nav.login"/></a>
                <a href="/register" class="btn btn-outline-primary py-2 px-4 ms-lg-2 d-none d-lg-inline-block"><spring:message code="nav.register"/></a>
            </c:when>
            <c:otherwise>
                <div class="nav-item dropdown ms-lg-2">
                    <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><spring:message code="nav.account"/></a>
                    <div class="dropdown-menu dropdown-menu-end m-0">
                        <a href="/my-account/profile" class="dropdown-item"><spring:message code="nav.profile"/></a>
                        <a href="/my-account/password" class="dropdown-item"><spring:message code="nav.password"/></a>
                    </div>
                </div>
                <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
                    <a href="/admin" class="btn btn-outline-primary py-2 px-4 ms-lg-2 d-none d-lg-inline-block"><spring:message code="nav.admin"/></a>
                </c:if>
                <a href="/logout" class="btn btn-primary py-2 px-4 ms-lg-2 d-none d-lg-inline-block"><spring:message code="nav.logout"/></a>
            </c:otherwise>
        </c:choose>
    </div>
</nav>
<!-- Navbar End -->
