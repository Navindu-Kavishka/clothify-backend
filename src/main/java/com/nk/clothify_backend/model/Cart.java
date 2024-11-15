package com.nk.clothify_backend.model;


import com.nk.clothify_backend.entity.CartItemEntity;
import com.nk.clothify_backend.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {


    private Long id;

    private UserEntity userEntity;

    private Set<CartItemEntity> cartItemEntities = new HashSet<>();

    private Double totalPrice;

    private Integer totalItem;

    private Double totalDiscountedPrice;

    private Double discount;




}
