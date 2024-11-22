package com.nk.clothify_backend.repository;

import com.nk.clothify_backend.entity.CartEntity;
import com.nk.clothify_backend.entity.CartItemEntity;
import com.nk.clothify_backend.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItemEntity,Long> {

    //not update
    @Query("SELECT ci FROM CartItemEntity ci WHERE ci.cartEntity=:cartEntity AND ci.productEntity=:productEntity AND ci.size=:size AND ci.userId=:userId")
    public CartItemEntity isCartItemExist(
            @Param("cartEntity")CartEntity cartEntity,
            @Param("productEntity")ProductEntity productEntity,
            @Param("size") String size,
            @Param("userId") Long userId
            );

}
