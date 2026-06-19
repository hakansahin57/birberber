<!DOCTYPE html>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="${pageContext.response.locale.language}">
<head>
    <meta charset="utf-8">
    <title><spring:message code="stores.title"/></title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <tags:links/>
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"/>
    <style>
        #map { height: 420px; }
        .store-card { cursor: pointer; }
        .radius-control {
            background: var(--bb-surface);
            border: 1px solid var(--bb-border);
            border-radius: var(--bb-radius);
            padding: 1.25rem 1.5rem;
            box-shadow: var(--bb-shadow);
        }
        .radius-value {
            font-size: 1.75rem;
            font-weight: 800;
            color: var(--primary);
            min-width: 4rem;
            text-align: center;
        }
        .radius-presets .btn { min-width: 3.5rem; }
        .radius-presets .btn.active {
            background: var(--primary) !important;
            border-color: var(--primary) !important;
            color: #fff !important;
        }
        input[type=range].radius-slider { accent-color: #0d9488; height: 6px; }
        .no-results-hint {
            background: #fffbeb;
            border: 1px solid #fde68a;
            border-radius: 12px;
            padding: 1rem;
            color: #92400e;
        }
    </style>
</head>
<body>
<tags:navigation/>

<div class="container-xxl py-5">
    <div class="container">
        <div class="text-center mb-4">
            <span class="bb-badge py-1 px-4 mb-3 d-inline-block"><spring:message code="stores.badge"/></span>
            <h1 class="bb-page-title" id="pageTitle"><spring:message code="stores.heading"/></h1>
            <p class="text-muted" id="locationStatus"><spring:message code="stores.location.loading"/></p>
        </div>

        <div class="radius-control mb-4">
            <div class="row align-items-center g-3">
                <div class="col-md-auto d-flex align-items-center gap-3">
                    <button type="button" class="btn btn-outline-primary btn-sm" id="radiusDecrease"
                            title="<spring:message code='stores.radius.decrease'/>">
                        <i class="fa fa-minus"></i>
                    </button>
                    <div>
                        <div class="radius-value"><span id="radiusValue">5</span> <spring:message code="common.km"/></div>
                        <small class="text-muted d-block text-center"><spring:message code="stores.radius.label"/></small>
                    </div>
                    <button type="button" class="btn btn-outline-primary btn-sm" id="radiusIncrease"
                            title="<spring:message code='stores.radius.increase'/>">
                        <i class="fa fa-plus"></i>
                    </button>
                </div>
                <div class="col-md">
                    <input type="range" class="form-range radius-slider w-100" id="radiusSlider"
                           min="1" max="50" step="1" value="5">
                    <div class="d-flex justify-content-between">
                        <small class="text-muted">1 <spring:message code="common.km"/></small>
                        <small class="text-muted">50 <spring:message code="common.km"/></small>
                    </div>
                </div>
                <div class="col-md-auto">
                    <div class="btn-group radius-presets" role="group">
                        <button type="button" class="btn btn-outline-primary btn-sm radius-preset active" data-radius="5">5</button>
                        <button type="button" class="btn btn-outline-primary btn-sm radius-preset" data-radius="10">10</button>
                        <button type="button" class="btn btn-outline-primary btn-sm radius-preset" data-radius="15">15</button>
                        <button type="button" class="btn btn-outline-primary btn-sm radius-preset" data-radius="25">25</button>
                        <button type="button" class="btn btn-outline-primary btn-sm radius-preset" data-radius="50">50</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="row g-4">
            <div class="col-lg-7">
                <div id="map"></div>
            </div>
            <div class="col-lg-5">
                <div class="bb-panel" style="max-height: 420px; overflow-y: auto;">
                    <h5 class="text-primary mb-1"><i class="fa fa-list me-2"></i><spring:message code="stores.list.title"/></h5>
                    <small class="text-muted d-block mb-3" id="resultSummary"><spring:message code="common.loading"/></small>
                    <div id="storeList">
                        <p class="text-muted"><spring:message code="stores.list.loading"/></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
<script src="/ui/js/main.js"></script>
<script>
    const i18n = {
        headingWithRadius: '<spring:message code="stores.heading.withRadius" javaScriptEscape="true"/>',
        locationTracking: '<spring:message code="stores.location.tracking" javaScriptEscape="true"/>',
        locationDenied: '<spring:message code="stores.location.denied" javaScriptEscape="true"/>',
        locationUnsupported: '<spring:message code="stores.location.unsupported" javaScriptEscape="true"/>',
        searching: '<spring:message code="stores.searching" javaScriptEscape="true"/>',
        loadError: '<spring:message code="stores.load.error" javaScriptEscape="true"/>',
        summary: '<spring:message code="stores.summary" javaScriptEscape="true"/>',
        typeBarber: '<spring:message code="stores.type.barber" javaScriptEscape="true"/>',
        typeHairdresser: '<spring:message code="stores.type.hairdresser" javaScriptEscape="true"/>',
        book: '<spring:message code="stores.book" javaScriptEscape="true"/>',
        yourLocation: '<spring:message code="stores.map.yourLocation" javaScriptEscape="true"/>',
        emptyTitle: '<spring:message code="stores.empty.title" javaScriptEscape="true"/>',
        emptyHint: '<spring:message code="stores.empty.hint" javaScriptEscape="true"/>',
        emptyExpand: '<spring:message code="stores.empty.expand" javaScriptEscape="true"/>',
        emptyMaxRadius: '<spring:message code="stores.empty.maxRadius" javaScriptEscape="true"/>',
        km: '<spring:message code="common.km" javaScriptEscape="true"/>'
    };

    function formatMsg(template) {
        const args = Array.prototype.slice.call(arguments, 1);
        return template.replace(/\{(\d+)\}/g, (_, i) => args[parseInt(i, 10)] ?? '');
    }

    const DEFAULT_LAT = 41.0082;
    const DEFAULT_LNG = 28.9784;
    const MIN_RADIUS = 1;
    const MAX_RADIUS = 50;

    let map, userMarker, radiusCircle, storeMarkers = [];
    let currentLat = DEFAULT_LAT;
    let currentLng = DEFAULT_LNG;
    let currentRadiusKm = 5;

    const radiusSlider = document.getElementById('radiusSlider');
    const radiusValueEl = document.getElementById('radiusValue');
    const pageTitleEl = document.getElementById('pageTitle');
    const resultSummaryEl = document.getElementById('resultSummary');

    function setRadius(km, reload) {
        currentRadiusKm = Math.max(MIN_RADIUS, Math.min(MAX_RADIUS, km));
        radiusSlider.value = currentRadiusKm;
        radiusValueEl.textContent = currentRadiusKm;
        pageTitleEl.textContent = formatMsg(i18n.headingWithRadius, currentRadiusKm);

        document.querySelectorAll('.radius-preset').forEach(btn => {
            btn.classList.toggle('active', parseInt(btn.dataset.radius, 10) === currentRadiusKm);
        });

        updateRadiusCircle();
        if (reload && map) loadStores();
    }

    function updateRadiusCircle() {
        if (!map) return;
        if (radiusCircle) map.removeLayer(radiusCircle);
        radiusCircle = L.circle([currentLat, currentLng], {
            radius: currentRadiusKm * 1000,
            color: '#0d9488',
            fillColor: '#0d9488',
            fillOpacity: 0.08,
            weight: 2
        }).addTo(map);
        map.fitBounds(radiusCircle.getBounds(), { padding: [24, 24] });
    }

    function initMap(lat, lng) {
        currentLat = lat;
        currentLng = lng;
        if (map) {
            if (userMarker) userMarker.setLatLng([lat, lng]);
            updateRadiusCircle();
            loadStores();
            return;
        }
        map = L.map('map').setView([lat, lng], 13);
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; OpenStreetMap'
        }).addTo(map);
        userMarker = L.marker([lat, lng], { title: i18n.yourLocation })
            .addTo(map)
            .bindPopup('<b>' + i18n.yourLocation + '</b>');
        updateRadiusCircle();
        loadStores();
    }

    function loadStores() {
        resultSummaryEl.textContent = i18n.searching;
        fetch('/api/stores/nearby?lat=' + currentLat + '&lng=' + currentLng + '&radiusKm=' + currentRadiusKm)
            .then(r => r.json())
            .then(stores => renderStores(stores))
            .catch(() => {
                document.getElementById('storeList').innerHTML = '<p class="text-danger">' + i18n.loadError + '</p>';
                resultSummaryEl.textContent = '';
            });
    }

    function renderStores(stores) {
        storeMarkers.forEach(m => map.removeLayer(m));
        storeMarkers = [];
        const listEl = document.getElementById('storeList');
        resultSummaryEl.textContent = formatMsg(i18n.summary, stores.length, currentRadiusKm);

        if (!stores.length) {
            const nextRadius = Math.min(currentRadiusKm + 5, MAX_RADIUS);
            listEl.innerHTML =
                '<div class="no-results-hint">' +
                '<strong>' + formatMsg(i18n.emptyTitle, currentRadiusKm) + '</strong>' +
                '<p class="mb-2 mt-2 small">' + i18n.emptyHint + '</p>' +
                (nextRadius > currentRadiusKm
                    ? '<button type="button" class="btn btn-primary btn-sm" id="expandRadiusBtn">' +
                      formatMsg(i18n.emptyExpand, nextRadius) + '</button>'
                    : '<span class="small">' + i18n.emptyMaxRadius + '</span>') +
                '</div>';
            const expandBtn = document.getElementById('expandRadiusBtn');
            if (expandBtn) expandBtn.addEventListener('click', () => setRadius(nextRadius, true));
            return;
        }

        let html = '';
        stores.forEach(store => {
            const typeLabel = store.storeType === 'HAIRDRESSER' ? i18n.typeHairdresser : i18n.typeBarber;
            const badgeClass = store.storeType === 'HAIRDRESSER' ? 'badge-hairdresser' : 'badge-barber';
            html += '<div class="card bb-card store-card mb-3" data-store-id="' + store.id + '" ' +
                'data-lat="' + store.latitude + '" data-lng="' + store.longitude + '">' +
                '<div class="card-body">' +
                '<div class="d-flex justify-content-between align-items-start">' +
                '<h6 class="text-primary mb-1">' + store.name + '</h6>' +
                '<span class="badge ' + badgeClass + '">' + typeLabel + '</span></div>' +
                '<small class="text-muted d-block mb-2">' + store.addressLine + '</small>' +
                '<small><i class="fa fa-map-marker-alt me-1"></i>' + store.distanceKm.toFixed(1) + ' ' + i18n.km + '</small>' +
                '<a href="/stores/' + store.id + '" class="btn btn-primary btn-sm mt-2 w-100">' + i18n.book + '</a>' +
                '</div></div>';
            const marker = L.marker([store.latitude, store.longitude])
                .addTo(map)
                .bindPopup('<b>' + store.name + '</b><br>' + store.distanceKm.toFixed(1) + ' ' + i18n.km);
            marker.on('click', () => highlightStore(store.id));
            storeMarkers.push(marker);
        });
        listEl.innerHTML = html;
        document.querySelectorAll('.store-card').forEach(card => {
            card.addEventListener('click', () => {
                highlightStore(card.dataset.storeId);
                map.setView([parseFloat(card.dataset.lat), parseFloat(card.dataset.lng)], 15);
            });
        });
    }

    function highlightStore(storeId) {
        document.querySelectorAll('.store-card').forEach(c => c.classList.remove('active'));
        const card = document.querySelector('[data-store-id="' + storeId + '"]');
        if (card) card.classList.add('active');
    }

    radiusSlider.addEventListener('input', () => setRadius(parseInt(radiusSlider.value, 10), false));
    radiusSlider.addEventListener('change', () => setRadius(parseInt(radiusSlider.value, 10), true));
    document.getElementById('radiusDecrease').addEventListener('click', () => setRadius(currentRadiusKm - 1, true));
    document.getElementById('radiusIncrease').addEventListener('click', () => setRadius(currentRadiusKm + 1, true));
    document.querySelectorAll('.radius-preset').forEach(btn => {
        btn.addEventListener('click', () => setRadius(parseInt(btn.dataset.radius, 10), true));
    });

    function startTracking() {
        if (!navigator.geolocation) {
            document.getElementById('locationStatus').textContent = i18n.locationUnsupported;
            initMap(DEFAULT_LAT, DEFAULT_LNG);
            return;
        }
        navigator.geolocation.watchPosition(
            pos => {
                currentLat = pos.coords.latitude;
                currentLng = pos.coords.longitude;
                document.getElementById('locationStatus').textContent =
                    formatMsg(i18n.locationTracking, currentRadiusKm);
                initMap(currentLat, currentLng);
            },
            () => {
                document.getElementById('locationStatus').textContent = i18n.locationDenied;
                initMap(DEFAULT_LAT, DEFAULT_LNG);
            },
            { enableHighAccuracy: true, maximumAge: 10000, timeout: 15000 }
        );
    }

    setRadius(5, false);
    startTracking();
</script>
</body>
</html>
