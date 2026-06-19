<!DOCTYPE html>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="${pageContext.response.locale.language}">
<head>
    <meta charset="utf-8">
    <title>${store.name} - <spring:message code="storeDetail.title.suffix"/></title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <tags:links/>
    <style>
        .slot-btn { min-width: 110px; margin: 4px; }
        .service-card { cursor: pointer; }
        .store-gallery-main {
            width: 100%;
            height: 320px;
            object-fit: cover;
            border-radius: var(--bb-radius);
        }
        .store-gallery-thumb {
            width: 100%;
            height: 72px;
            object-fit: cover;
            border-radius: var(--bb-radius-sm);
            cursor: pointer;
            opacity: 0.7;
            border: 2px solid transparent;
            transition: all 0.2s ease;
        }
        .store-gallery-thumb.active,
        .store-gallery-thumb:hover {
            opacity: 1;
            border-color: var(--primary);
        }
        .store-rating-stars { color: #f59e0b; font-size: 1.1rem; }
        .store-rating-stars .fa-star.empty { color: #cbd5e1; }
        .review-card { border-left: 3px solid var(--primary); }
    </style>
</head>
<body>
<tags:navigation/>

<div class="container-xxl py-5">
    <div class="container">
        <c:if test="${not empty errorMsg}">
            <div class="alert alert-danger">${errorMsg}</div>
        </c:if>

        <div class="mb-4">
            <a href="/stores" class="text-primary"><i class="fa fa-arrow-left me-2"></i><spring:message code="storeDetail.back"/></a>
        </div>

        <!-- Galeri & Özet -->
        <div class="row g-4 mb-4">
            <div class="col-lg-7">
                <c:choose>
                    <c:when test="${not empty profile.photoUrls}">
                        <img id="mainPhoto" src="${profile.photoUrls[0]}" alt="${store.name}" class="store-gallery-main mb-3">
                        <c:if test="${profile.photoUrls.size() > 1}">
                            <div class="row g-2">
                                <c:forEach var="photoUrl" items="${profile.photoUrls}" varStatus="st">
                                    <div class="col-3">
                                        <img src="${photoUrl}" alt=""
                                             class="store-gallery-thumb ${st.index == 0 ? 'active' : ''}"
                                             data-full="${photoUrl}"
                                             onclick="setMainPhoto(this)">
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <div class="store-gallery-main d-flex align-items-center justify-content-center bg-light text-muted">
                            <div class="text-center">
                                <i class="fa fa-image fa-3x mb-2"></i>
                                <p class="mb-0"><spring:message code="storeDetail.noPhotos"/></p>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-lg-5">
                <div class="bb-panel h-100">
                    <h1 class="bb-page-title">${store.name}</h1>
                    <p class="text-muted mb-2">
                        <c:choose>
                            <c:when test="${store.storeType.name() == 'HAIRDRESSER'}"><spring:message code="stores.type.hairdresser"/></c:when>
                            <c:otherwise><spring:message code="stores.type.barber"/></c:otherwise>
                        </c:choose>
                        <c:if test="${not empty store.phoneNumber}"> · ${store.phoneNumber}</c:if>
                    </p>
                    <c:if test="${not empty store.address}">
                        <p class="text-muted small mb-3">
                            <i class="fa fa-map-marker-alt me-1"></i>
                            ${store.address.line1}
                            <c:if test="${not empty store.address.line2}">, ${store.address.line2}</c:if>
                        </p>
                    </c:if>

                    <div class="d-flex align-items-center gap-3 mb-3">
                        <div class="store-rating-stars">
                            <c:forEach begin="1" end="5" var="star">
                                <i class="fa fa-star ${star <= profile.averageRatingRounded ? '' : 'empty'}"></i>
                            </c:forEach>
                        </div>
                        <div>
                            <strong class="text-primary fs-5">
                                <fmt:formatNumber value="${profile.averageRating}" maxFractionDigits="1" minFractionDigits="1"/>
                            </strong>
                            <span class="text-muted small">
                                (<spring:message code="storeDetail.reviewCount" arguments="${profile.reviewCount}"/>)
                            </span>
                        </div>
                    </div>

                    <a href="#booking" class="btn btn-primary w-100 py-3">
                        <spring:message code="storeDetail.bookNow"/> <i class="fa fa-calendar-check ms-2"></i>
                    </a>
                </div>
            </div>
        </div>

        <!-- Hizmetler & Randevu -->
        <div class="row g-4 mb-5" id="booking">
            <div class="col-lg-5">
                <div class="bb-panel">
                    <h5 class="text-primary mb-3"><spring:message code="storeDetail.services"/></h5>
                    <c:forEach var="service" items="${services}">
                        <div class="card bb-card service-card mb-3"
                             data-service-id="${service.id}"
                             data-duration="${service.durationMinutes}">
                            <div class="card-body">
                                <h6 class="text-primary">${service.name}</h6>
                                <p class="mb-0">${service.price} ${service.currency} · ${service.durationMinutes} <spring:message code="common.minutes.short"/></p>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <div class="col-lg-7">
                <div class="bb-panel">
                    <h5 class="text-primary mb-3"><spring:message code="storeDetail.datetime"/></h5>
                    <div class="mb-3">
                        <label class="form-label"><spring:message code="storeDetail.date"/></label>
                        <input type="date" id="appointmentDate" class="form-control">
                    </div>
                    <div id="slotsContainer">
                        <p class="text-muted"><spring:message code="storeDetail.selectFirst"/></p>
                    </div>
                    <form id="bookingForm" action="/appointments/checkout" method="get" class="mt-4 d-none">
                        <p class="text-muted small mb-2"><spring:message code="storeDetail.loginRequired"/></p>
                        <input type="hidden" name="storeId" value="${store.id}">
                        <input type="hidden" name="serviceId" id="serviceIdInput">
                        <input type="hidden" name="date" id="dateInput">
                        <input type="hidden" name="startTime" id="startTimeInput">
                        <button type="submit" class="btn btn-primary w-100 py-3">
                            <spring:message code="storeDetail.checkout"/> <i class="fa fa-arrow-right ms-2"></i>
                        </button>
                    </form>
                </div>
            </div>
        </div>

        <!-- Yorumlar -->
        <div class="bb-panel">
            <h5 class="text-primary mb-4"><spring:message code="storeDetail.reviews"/></h5>
            <c:choose>
                <c:when test="${empty profile.reviews}">
                    <p class="text-muted mb-0"><spring:message code="storeDetail.noReviews"/></p>
                </c:when>
                <c:otherwise>
                    <c:forEach var="review" items="${profile.reviews}">
                        <div class="card bb-card review-card mb-3">
                            <div class="card-body">
                                <div class="d-flex justify-content-between align-items-start mb-2">
                                    <div>
                                        <strong>${review.customerName}</strong>
                                        <div class="store-rating-stars small">
                                            <c:forEach begin="1" end="5" var="star">
                                                <i class="fa fa-star ${star <= review.rating ? '' : 'empty'}"></i>
                                            </c:forEach>
                                        </div>
                                    </div>
                                    <small class="text-muted">${review.dateLabel}</small>
                                </div>
                                <p class="mb-0">${review.comment}</p>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="/ui/js/main.js"></script>
<script>
    function setMainPhoto(thumb) {
        document.getElementById('mainPhoto').src = thumb.dataset.full;
        document.querySelectorAll('.store-gallery-thumb').forEach(t => t.classList.remove('active'));
        thumb.classList.add('active');
    }

    const storeId = ${store.id};
    const i18n = {
        slotsLoading: '<spring:message code="storeDetail.slots.loading" javaScriptEscape="true"/>',
        slotsEmpty: '<spring:message code="storeDetail.slots.empty" javaScriptEscape="true"/>'
    };
    let selectedServiceId = null;

    const dateInput = document.getElementById('appointmentDate');
    const today = new Date().toISOString().split('T')[0];
    dateInput.min = today;
    dateInput.value = today;

    document.querySelectorAll('.service-card').forEach(card => {
        card.addEventListener('click', () => {
            document.querySelectorAll('.service-card').forEach(c => c.classList.remove('selected'));
            card.classList.add('selected');
            selectedServiceId = card.dataset.serviceId;
            loadSlots();
        });
    });

    dateInput.addEventListener('change', loadSlots);

    function loadSlots() {
        if (!selectedServiceId || !dateInput.value) return;
        const container = document.getElementById('slotsContainer');
        container.innerHTML = '<p class="text-muted">' + i18n.slotsLoading + '</p>';

        fetch('/api/stores/' + storeId + '/slots?date=' + dateInput.value + '&serviceId=' + selectedServiceId)
            .then(r => r.json())
            .then(slots => {
                if (!slots.length) {
                    container.innerHTML = '<p class="text-muted">' + i18n.slotsEmpty + '</p>';
                    document.getElementById('bookingForm').classList.add('d-none');
                    return;
                }
                let html = '<div class="d-flex flex-wrap">';
                slots.forEach(slot => {
                    const cls = slot.available ? 'btn-outline-primary slot-btn' : 'btn-secondary slot-btn slot-busy';
                    const disabled = slot.available ? '' : 'disabled';
                    html += '<button type="button" class="btn ' + cls + '" ' + disabled +
                        ' data-start="' + slot.startTime + '">' + slot.startTime + ' - ' + slot.endTime + '</button>';
                });
                html += '</div>';
                container.innerHTML = html;

                container.querySelectorAll('.slot-btn:not(.slot-busy)').forEach(btn => {
                    btn.addEventListener('click', () => {
                        container.querySelectorAll('.slot-btn').forEach(b => b.classList.remove('active'));
                        btn.classList.add('active');
                        document.getElementById('serviceIdInput').value = selectedServiceId;
                        document.getElementById('dateInput').value = dateInput.value;
                        document.getElementById('startTimeInput').value = btn.dataset.start;
                        document.getElementById('bookingForm').classList.remove('d-none');
                    });
                });
            });
    }
</script>
</body>
</html>
