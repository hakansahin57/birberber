<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Navbar Start -->
<nav class="navbar navbar-expand-lg bg-secondary navbar-dark sticky-top py-lg-0 px-lg-5 wow fadeIn" data-wow-delay="0.1s">
    <a href="/" class="navbar-brand ms-4 ms-lg-0">
        <h1 class="mb-0 text-primary"><i class="fa fa-cut me-3"></i>birberber</h1>
    </a>
    <button type="button" class="navbar-toggler me-4" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarCollapse">
        <div class="navbar-nav ms-auto p-4 p-lg-0">
            <a href="/" class="nav-item nav-link active">Home</a>
        </div>
        <c:choose>
            <c:when test="${empty pageContext.request.remoteUser}">
                <a href="/login" class="btn btn-primary rounded-0 py-2 px-lg-4 d-none d-lg-block">Log In<i class="fa fa-arrow-right ms-3"></i></a>
                <a href="/register" class="btn btn-primary rounded-0 py-2 px-lg-4 d-none d-lg-block">Register<i class="fa fa-arrow-right ms-3"></i></a>
            </c:when>
            <c:otherwise>
                <div class="nav-item dropdown">
                    <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">Pages</a>
                    <div class="dropdown-menu m-0">
                        <a href="/my-account/profile" class="dropdown-item">Profile</a>
                        <a href="team.html" class="dropdown-item">Our Barber</a>
                        <a href="open.html" class="dropdown-item">Working Hours</a>
                        <a href="testimonial.html" class="dropdown-item">Testimonial</a>
                        <a href="404.html" class="dropdown-item">404 Page</a>
                    </div>
                </div>
                <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
                    <a href="/admin" class="btn btn-primary rounded-0 py-2 px-lg-4 d-none d-lg-block">Admin<i class="fa fa-arrow-right ms-3"></i></a>
                </c:if>
                <a href="/logout" class="btn btn-primary rounded-0 py-2 px-lg-4 d-none d-lg-block">Logout<i class="fa fa-arrow-right ms-3"></i></a>
            </c:otherwise>
        </c:choose>

    </div>
</nav>
<!-- Navbar End -->