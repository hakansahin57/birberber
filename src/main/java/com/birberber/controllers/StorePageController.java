package com.birberber.controllers;

import com.birberber.constants.Constants;
import com.birberber.domain.store.Store;
import com.birberber.dto.ServiceDto;
import com.birberber.dto.StoreProfileDto;
import com.birberber.services.store.AvailabilityService;
import com.birberber.services.store.NearbyStoreService;
import com.birberber.services.store.StoreProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/stores")
public class StorePageController {

    private final NearbyStoreService nearbyStoreService;
    private final AvailabilityService availabilityService;
    private final StoreProfileService storeProfileService;

    public StorePageController(
            NearbyStoreService nearbyStoreService,
            AvailabilityService availabilityService,
            StoreProfileService storeProfileService) {
        this.nearbyStoreService = nearbyStoreService;
        this.availabilityService = availabilityService;
        this.storeProfileService = storeProfileService;
    }

    @GetMapping
    public String listStores() {
        return Constants.STORES_PAGE;
    }

    @GetMapping("/{storeId}")
    public String storeDetail(@PathVariable Long storeId, Model model) {
        Store store = nearbyStoreService.findById(storeId);
        List<ServiceDto> services = availabilityService.getServices(store);
        StoreProfileDto profile = storeProfileService.getProfile(storeId);
        model.addAttribute("store", store);
        model.addAttribute("services", services);
        model.addAttribute("profile", profile);
        return Constants.STORE_DETAIL_PAGE;
    }
}
