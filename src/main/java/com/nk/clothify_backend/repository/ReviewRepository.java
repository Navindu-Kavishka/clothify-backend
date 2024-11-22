package com.nk.clothify_backend.repository;

import com.nk.clothify_backend.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//checked

public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {

    @Query("SELECT r FROM ReviewEntity r WHERE r.productEntity.id=:productId")
    public List<ReviewEntity> getAllProductsReview (@Param("productId") Long productId);
}
