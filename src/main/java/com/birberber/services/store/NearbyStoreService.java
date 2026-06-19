package com.birberber.services.store;

import com.birberber.domain.address.Address;
import com.birberber.domain.store.Store;
import com.birberber.dto.NearbyStoreDto;
import com.birberber.repositories.StoreRepository;
import com.birberber.util.GeoUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NearbyStoreService {

    private final StoreRepository storeRepository;

    public NearbyStoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<NearbyStoreDto> findNearby(double latitude, double longitude, double radiusKm) {
        return storeRepository.findAll().stream()
                .filter(store -> hasCoordinates(store))
                .map(store -> toDto(store, latitude, longitude))
                .filter(dto -> dto.getDistanceKm() <= radiusKm)
                .sorted(Comparator.comparingDouble(NearbyStoreDto::getDistanceKm))
                .collect(Collectors.toList());
    }

    public Store findById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("error.store.not.found"));
    }

    private boolean hasCoordinates(Store store) {
        Address address = store.getAddress();
        return address != null && address.getLatitude() != null && address.getLongitude() != null;
    }

    private NearbyStoreDto toDto(Store store, double userLat, double userLng) {
        Address address = store.getAddress();
        NearbyStoreDto dto = new NearbyStoreDto();
        dto.setId(store.getId());
        dto.setName(store.getName());
        dto.setStoreType(store.getStoreType() != null ? store.getStoreType().name() : "BARBER");
        dto.setLatitude(address.getLatitude());
        dto.setLongitude(address.getLongitude());
        dto.setDistanceKm(GeoUtils.distanceKm(userLat, userLng, address.getLatitude(), address.getLongitude()));
        dto.setAddressLine(buildAddressLine(address));
        dto.setPhoneNumber(store.getPhoneNumber());
        return dto;
    }

    private String buildAddressLine(Address address) {
        StringBuilder sb = new StringBuilder();
        if (address.getLine1() != null) {
            sb.append(address.getLine1());
        }
        if (address.getLine2() != null) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(address.getLine2());
        }
        if (address.getCity() != null) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(address.getCity().getName());
        }
        return sb.toString();
    }
}
