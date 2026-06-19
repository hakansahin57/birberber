package com.birberber.services.store;

import com.birberber.dto.StoreProfileDto;
import com.birberber.dto.StoreReviewDto;
import com.birberber.repositories.StorePhotoRepository;
import com.birberber.repositories.StoreReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreProfileService {

    private final StorePhotoRepository storePhotoRepository;
    private final StoreReviewRepository storeReviewRepository;

    public StoreProfileService(
            StorePhotoRepository storePhotoRepository,
            StoreReviewRepository storeReviewRepository) {
        this.storePhotoRepository = storePhotoRepository;
        this.storeReviewRepository = storeReviewRepository;
    }

    public StoreProfileDto getProfile(Long storeId) {
        StoreProfileDto profile = new StoreProfileDto();
        profile.setPhotoUrls(storePhotoRepository.findByStoreIdOrderByDisplayOrderAsc(storeId)
                .stream()
                .map(photo -> photo.getUrl())
                .toList());
        profile.setReviewCount(storeReviewRepository.countByStoreId(storeId));
        Double avg = storeReviewRepository.averageRatingByStoreId(storeId);
        profile.setAverageRating(avg != null ? avg : 0);
        profile.setReviews(storeReviewRepository.findByStoreIdOrderByCreationTimeDesc(storeId)
                .stream()
                .map(StoreReviewDto::from)
                .toList());
        return profile;
    }
}
