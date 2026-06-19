package com.birberber.controllers.api;

import com.birberber.domain.store.Store;
import com.birberber.dto.NearbyStoreDto;
import com.birberber.dto.ServiceDto;
import com.birberber.dto.TimeSlotDto;
import com.birberber.repositories.ProductRepository;
import com.birberber.services.store.AvailabilityService;
import com.birberber.services.store.NearbyStoreService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreApiController {

    private final NearbyStoreService nearbyStoreService;
    private final AvailabilityService availabilityService;
    private final ProductRepository productRepository;

    public StoreApiController(
            NearbyStoreService nearbyStoreService,
            AvailabilityService availabilityService,
            ProductRepository productRepository) {
        this.nearbyStoreService = nearbyStoreService;
        this.availabilityService = availabilityService;
        this.productRepository = productRepository;
    }

    @GetMapping("/nearby")
    public List<NearbyStoreDto> nearby(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "5") double radiusKm) {
        return nearbyStoreService.findNearby(lat, lng, radiusKm);
    }

    @GetMapping("/{storeId}/services")
    public List<ServiceDto> services(@PathVariable Long storeId) {
        Store store = nearbyStoreService.findById(storeId);
        return availabilityService.getServices(store);
    }

    @GetMapping("/{storeId}/slots")
    public List<TimeSlotDto> slots(
            @PathVariable Long storeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam Long serviceId) {
        Store store = nearbyStoreService.findById(storeId);
        int duration = productRepository.findById(serviceId)
                .map(product -> product.getDurationMinutes())
                .orElse(60);
        return availabilityService.getSlots(store, date, duration);
    }
}
