package com.nk.clothify_backend.model;


import com.nk.clothify_backend.entity.CartItemEntity;
import com.nk.clothify_backend.entity.UserEntity;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {


    private Long id;

    private UserEntity userEntity;

    private Set<CartItemEntity> cartItemEntities = new HashSet<>();

    private int totalPrice;

    private int totalItem;

    private int totalDiscountedPrice;

    private int discount;




}
