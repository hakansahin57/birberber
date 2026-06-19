<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- Footer Start -->
<div class="container-fluid bg-secondary text-light footer mt-5 pt-5 wow fadeIn" data-wow-delay="0.1s">
    <div class="container py-5">
        <div class="row g-5">
            <div class="col-lg-4 col-md-6">
                <h4 class="text-uppercase mb-4"><spring:message code="footer.contact"/></h4>
                <div class="d-flex align-items-center mb-2">
                    <div class="btn-square bg-dark flex-shrink-0 me-3">
                        <span class="fa fa-map-marker-alt text-primary"></span>
                    </div>
                    <span>İstanbul, Türkiye</span>
                </div>
                <div class="d-flex align-items-center mb-2">
                    <div class="btn-square bg-dark flex-shrink-0 me-3">
                        <span class="fa fa-phone-alt text-primary"></span>
                    </div>
                    <span>+90 555 000 0000</span>
                </div>
                <div class="d-flex align-items-center">
                    <div class="btn-square bg-dark flex-shrink-0 me-3">
                        <span class="fa fa-envelope-open text-primary"></span>
                    </div>
                    <span>info@birberber.com</span>
                </div>
            </div>
            <div class="col-lg-4 col-md-6">
                <h4 class="text-uppercase mb-4"><spring:message code="footer.quickLinks"/></h4>
                <a class="btn btn-link" href="/"><spring:message code="footer.about"/></a>
                <a class="btn btn-link" href="/stores"><spring:message code="footer.services"/></a>
                <a class="btn btn-link" href="/register"><spring:message code="nav.register"/></a>
                <a class="btn btn-link" href="/login"><spring:message code="footer.support"/></a>
            </div>
            <div class="col-lg-4 col-md-6">
                <h4 class="text-uppercase mb-4"><spring:message code="footer.newsletter"/></h4>
                <div class="position-relative mb-4">
                    <input class="form-control border-0 w-100 py-3 ps-4 pe-5" type="text"
                           placeholder="<spring:message code='footer.emailPlaceholder'/>">
                    <button type="button" class="btn btn-primary py-2 position-absolute top-0 end-0 mt-2 me-2">
                        <spring:message code="footer.signup"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="copyright">
            <div class="row">
                <div class="col-md-6 text-center text-md-start mb-3 mb-md-0">
                    &copy; <a class="border-bottom" href="/"><spring:message code="app.name"/></a>,
                    <spring:message code="home.footer.rights"/>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Footer End -->
<a href="#" class="btn btn-primary btn-lg-square back-to-top" title="<spring:message code='common.backToTop'/>">
    <i class="bi bi-arrow-up"></i>
</a>
