package com.birberber.repositories;

import com.birberber.domain.store.StorePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StorePhotoRepository extends JpaRepository<StorePhoto, Long> {

    List<StorePhoto> findByStoreIdOrderByDisplayOrderAsc(Long storeId);

    long countByStoreId(Long storeId);

    boolean existsByStoreId(Long storeId);
}
