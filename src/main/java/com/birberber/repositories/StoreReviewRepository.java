package com.birberber.repositories;

import com.birberber.domain.store.StoreReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreReviewRepository extends JpaRepository<StoreReview, Long> {

    List<StoreReview> findByStoreIdOrderByCreationTimeDesc(Long storeId);

    long countByStoreId(Long storeId);

    @Query("SELECT COALESCE(AVG(r.rating), 0) FROM StoreReview r WHERE r.store.id = :storeId")
    Double averageRatingByStoreId(@Param("storeId") Long storeId);

    boolean existsByStoreId(Long storeId);
}
