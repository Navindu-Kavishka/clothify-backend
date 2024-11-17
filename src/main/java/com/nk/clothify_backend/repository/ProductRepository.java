package com.nk.clothify_backend.repository;

import com.nk.clothify_backend.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

    @Query(
            "SELECT p FROM ProductEntity p " +
                    "WHERE (p.categoryEntity.name = :category OR :category = '') " + // Reference the correct field for category
                    "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice)) " +
                    "AND (:minDiscount IS NULL OR p.discountPercentage >= :minDiscount) " +
                    "ORDER BY " +
                    "CASE WHEN :sort = 'price_low' THEN p.discountedPrice END ASC, " +
                    "CASE WHEN :sort = 'price_high' THEN p.discountedPrice END DESC"
    )
    public List<ProductEntity> filterProducts(@Param("category") String category,
                                              @Param("minPrice") Integer minPrice,
                                              @Param("maxPrice") Integer maxPrice,
                                              @Param("minDiscount") Integer minDiscount,
                                              @Param("sort") String sort);


}
