package com.nk.clothify_backend.repository;

import com.nk.clothify_backend.entity.CartEntity;
import com.nk.clothify_backend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository <CartEntity,Long> {


    @Query("SELECT c FROM CartEntity c WHERE c.userEntity.id=:userId")
    public CartEntity findByUserId(@Param("userId") Long userId);

}
