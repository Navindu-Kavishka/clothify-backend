package com.nk.clothify_backend.repository;

import com.nk.clothify_backend.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<RatingEntity,Long> {


    @Query("SELECT r FROM RatingEntity r WHERE r.productEntity.id=:productId")
    public List<RatingEntity> getAllProductsRating(@Param("productId") Long productId);
}
