package com.nk.clothify_backend.model;


import com.nk.clothify_backend.entity.CartEntity;
import com.nk.clothify_backend.entity.ProductEntity;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartItem {

    private Long id;

    private CartEntity cartEntity;

    private ProductEntity productEntity;

    private String size;

    private Integer quantity;

    private Integer price;

    private Integer discountedPrice;

    private Long userId;

}
